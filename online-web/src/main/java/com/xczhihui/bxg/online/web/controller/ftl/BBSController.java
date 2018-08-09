package com.xczhihui.bxg.online.web.controller.ftl;

import static com.xczhihui.bxg.online.web.support.sensitive.SensitivewordFilter.MAX_MATCH_TYPE;
import static com.xczhihui.common.util.bean.ResponseObject.newErrorResponseObject;
import static com.xczhihui.common.util.bean.ResponseObject.newSuccessResponseObject;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.base.utils.UserLoginUtil;
import com.xczhihui.bxg.online.web.body.bbs.PostBody;
import com.xczhihui.bxg.online.web.body.bbs.ReplyBody;
import com.xczhihui.bxg.online.web.support.sensitive.SensitivewordFilter;
import com.xczhihui.bxg.online.web.utils.HtmlUtil;
import com.xczhihui.bxg.online.web.utils.HttpUtil;
import com.xczhihui.common.support.domain.Attachment;
import com.xczhihui.common.support.domain.BxgUser;
import com.xczhihui.common.support.service.AttachmentCenterService;
import com.xczhihui.common.util.IStringUtil;
import com.xczhihui.common.util.JsonUtil;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.medical.bbs.model.Label;
import com.xczhihui.medical.bbs.model.Post;
import com.xczhihui.medical.bbs.model.Reply;
import com.xczhihui.medical.bbs.service.IPostService;
import com.xczhihui.medical.bbs.vo.PostVO;

/**
 * @author hejiwei
 */
@Controller
@RequestMapping("bbs")
public class BBSController extends AbstractFtlController {

    private static final String POST_SENSITIVE_EMAIL_TEMPLATE = "熊猫中医BBS帖子发现疑似违规内容:</br>标题：{0}</br>" +
            "用户ID：{1}</br>内容：{2}<br>内容中包含敏感词的个数为：{3}。包含：{4}</br>内容被和谐后，变为：{5}";
    private static final String REPLY_SENSITIVE_EMAIL_TEMPLATE = "熊猫中医BBS帖子回复发现疑似违规内容:</br>帖子id：{0}</br>" +
            "用户ID：{1}</br>内容：{2}<br>内容中包含敏感词的个数为：{3}。包含：{4}</br>内容被和谐后，变为：{5}";

    @Autowired
    private IPostService postService;
    @Autowired
    private AttachmentCenterService attachmentCenterService;
    @Autowired
    private SensitivewordFilter sensitivewordFilter;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(@RequestParam(required = false) String type, @RequestParam(required = false) Integer labelId,
                              @RequestParam(defaultValue = "1") int page, HttpServletResponse response) {
        List<Label> labels = postService.getLabels();
        ModelAndView modelAndView = new ModelAndView("bbs/index");
        Page<PostVO> list = postService.list(page, labelId, type);
        list.getRecords().forEach(post -> post.setContent(HtmlUtil.getTextFromHtml(post.getContent())));
        modelAndView.addObject("posts", list);
        int labelSize = labels.size();
        modelAndView.addObject("top3Labels", labelSize >= 3 ? labels.subList(0, 3) : labels);
        modelAndView.addObject("otherLabels", labelSize > 3 ? labels.subList(3, labelSize >= 6 ? 6 : labelSize) : new ArrayList<>());
        modelAndView.addObject("hots", postService.listHot());
        modelAndView.addObject("type", type);
        modelAndView.addObject("labelId", labelId);

        HttpUtil.disableHttpWebCache(response);
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject save(@RequestBody @Valid PostBody postBody, HttpServletRequest request) {
        String userId = getCurrentUser().getId();
        if (postService.isBlacklist(userId)) {
            return newErrorResponseObject("您已被拉入黑名单");
        }
        if (postService.isGags(userId)) {
            return newErrorResponseObject("您已被禁言");
        }
        Post post = postBody.build(userId);
        post.setTitle(handlePostSensitiveWord(post, post.getTitle()));
        post.setContent(handlePostSensitiveWord(post, post.getContent()));
        postService.savePost(post);
        return newSuccessResponseObject();
    }

    @RequestMapping(value = "reply", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject reply(@RequestBody @Valid ReplyBody replyBody, HttpServletRequest request) {
        String userId = getCurrentUser().getId();
        if (postService.isBlacklist(userId)) {
            return newErrorResponseObject("您已被拉入黑名单");
        }
        if (postService.isGags(userId)) {
            return newErrorResponseObject("您已被禁言");
        }
        Reply reply = replyBody.build(getCurrentUser().getId());
        reply.setContent(handleReplySensitiveWord(reply, reply.getContent()));
        postService.addReply(reply);
        return newSuccessResponseObject();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ModelAndView view(HttpServletResponse response, @PathVariable Integer id, @RequestParam(defaultValue = "1") int page, HttpServletRequest request) throws ServletException, IOException {
        ModelAndView modelAndView = new ModelAndView("bbs/detail");
        BxgUser loginUser = UserLoginUtil.getLoginUser();
        postService.addBrowseRecord(id, loginUser == null ? null : loginUser.getId());
        PostVO postVO = postService.get(id);
        if (postVO == null) {
            return to404(request,response);
        }
        modelAndView.addObject("post", postVO);
        modelAndView.addObject("replies", postService.listByPostId(id, page));
        modelAndView.addObject("description", IStringUtil.getTop100Char(HtmlUtil.getTextFromHtml(postVO.getContent())));
        modelAndView.addObject("isAlive", loginUser != null);
        return modelAndView;
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public ModelAndView add() {
        List<Label> labels = postService.getLabels();
        ModelAndView modelAndView = new ModelAndView("bbs/add");
        modelAndView.addObject("labels", labels);
        return modelAndView;
    }

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadImage(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
        String upload = attachmentCenterService.upload(getCurrentUser().getId(), "online", file.getOriginalFilename(), "image", file.getBytes(), "1");
        Attachment attachment = JsonUtil.getBaseGson().fromJson(upload, Attachment.class);
        Map<String, Object> uploadResult = new HashMap<>(3);
        uploadResult.put("code", attachment.getError());
        uploadResult.put("msg", attachment.getMessage());
        Map<String, Object> data = new HashMap<>(2);
        data.put("src", attachment.getUrl());
        data.put("title", attachment.getFileName());
        uploadResult.put("data", data);
        return uploadResult;
    }

    @RequestMapping(value = "user", method = RequestMethod.GET)
    public ModelAndView user(@RequestParam(defaultValue = "1") int page, HttpServletRequest request) {
        OnlineUser onlineUser = getCurrentUser();
        return new ModelAndView("bbs/user").addObject("user", onlineUser);
    }

    @RequestMapping(value = "myPosts", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject myPosts(@RequestParam(defaultValue = "0") int page, HttpServletRequest request) {
        String userId = getCurrentUser().getId();
        return ResponseObject.newSuccessResponseObject(postService.listMyPosts(userId, page));
    }

    @RequestMapping(value = "myReplies", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject myReplies(@RequestParam(defaultValue = "0") int page, HttpServletRequest request) {
        String userId = getCurrentUser().getId();
        return ResponseObject.newSuccessResponseObject(postService.listMyReplies(userId, page));
    }

    private String handlePostSensitiveWord(Post post, String text) {
        Set<String> sensitiveWords = sensitivewordFilter.getSensitiveWord(text, MAX_MATCH_TYPE);
        if (!sensitiveWords.isEmpty()) {
            text = sensitivewordFilter.replaceSensitiveWord(text, MAX_MATCH_TYPE, "*");
            String emailText = MessageFormat.format(POST_SENSITIVE_EMAIL_TEMPLATE, post.getTitle(), post.getUserId(), post.getContent(),
                    sensitiveWords.size(), StringUtils.join(sensitiveWords.toArray(), "|"), text);
            try {
                sensitivewordFilter.sendMail(MAX_MATCH_TYPE, emailText);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        return text;
    }

    private String handleReplySensitiveWord(Reply reply, String text) {
        Set<String> sensitiveWords = sensitivewordFilter.getSensitiveWord(text, MAX_MATCH_TYPE);
        if (!sensitiveWords.isEmpty()) {
            text = sensitivewordFilter.replaceSensitiveWord(text, MAX_MATCH_TYPE, "*");
            String emailText = MessageFormat.format(REPLY_SENSITIVE_EMAIL_TEMPLATE, reply.getPostId(), reply.getUserId(), reply.getContent(),
                    sensitiveWords.size(), StringUtils.join(sensitiveWords.toArray(), "|"), text);
            try {
                sensitivewordFilter.sendMail(MAX_MATCH_TYPE, emailText);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        return text;
    }
}
