package com.xczhihui.headline.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.common.support.domain.Attachment;
import com.xczhihui.bxg.common.support.service.AttachmentCenterService;
import com.xczhihui.bxg.common.support.service.AttachmentType;
import com.xczhihui.bxg.common.util.DateUtil;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.controller.AbstractController;
import com.xczhihui.bxg.online.common.domain.MedicalWritings;
import com.xczhihui.headline.service.ArticleService;
import com.xczhihui.headline.service.WritingService;
import com.xczhihui.headline.vo.ArticleVo;
import com.xczhihui.headline.vo.WritingVo;
import com.xczhihui.support.shiro.ManagerUserUtil;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;

/**
 * 著作管理
 *
 * @author yxd
 */

@Controller
@RequestMapping("headline/writing")
public class WritingsController extends AbstractController {
    protected final static String HEADLINE_PATH_PREFIX = "/headline/";
    private static final Pattern IMG_PATTERN = Pattern.compile("<img\\s+(?:[^>]*)src\\s*=\\s*([^>]+)",
            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

    @Autowired
    private ArticleService articleService;

    @Autowired
    private WritingService writingService;

    @Autowired
    private AttachmentCenterService att;

    @Value("${online.web.url}")
    private String weburl;

    @RequestMapping(value = "index")
    public String index(HttpServletRequest request) {
        return HEADLINE_PATH_PREFIX + "/writingList";
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public TableVo articles(TableVo tableVo) {
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);

        WritingVo searchVo = new WritingVo();

        Group title = groups.findByName("search_title");
        if (title != null) {
            searchVo.setTitle(title.getPropertyValue1().toString());
        }
        Group status = groups.findByName("statusSearch");
        if (status != null) {
            searchVo.setStatus(Integer.parseInt(status.getPropertyValue1()
                    .toString()));
        }
        Group startTime = groups.findByName("startTime");
        if (startTime != null) {
            searchVo.setStartTime(DateUtil.parseDate(startTime
                    .getPropertyValue1().toString(), "yyyy-MM-dd"));
        }
        Group stopTime = groups.findByName("stopTime");
        if (stopTime != null) {
            searchVo.setStopTime(DateUtil.parseDate(stopTime
                    .getPropertyValue1().toString(), "yyyy-MM-dd"));
        }

        Page<MedicalWritings> page = writingService.findCoursePage(searchVo,
                currentPage, pageSize);

        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;
    }

    @RequestMapping(value = "toAdd")
    public String toAdd(HttpServletRequest request) {
        request.setAttribute("weburl", weburl);
        return HEADLINE_PATH_PREFIX + "/writingAdd";
    }

    /**
     * 添加
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject add(HttpServletRequest request, WritingVo writingVo) {
        String content = writingVo.getContent();
        content = Base64ToimageURL(content);

        writingVo.setUserId(ManagerUserUtil.getId());
        writingVo.setContent(content);
        // 添加著作 文章
        writingService.addWriting(writingVo);

        return ResponseObject.newSuccessResponseObject("操作成功！");
    }

    /**
     * 预览
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "addPre", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject addPre(HttpServletRequest request, ArticleVo articleVo) {
        String content = articleVo.getContent();
        content = Base64ToimageURL(content);
        articleVo.setContent(content);
        articleVo.setUserId(ManagerUserUtil.getId());
        articleService.addPreArticle(articleVo);
        articleService.addArticleTag(articleVo);
        return ResponseObject.newSuccessResponseObject(articleVo.getId());
    }

    /**
     * 修改
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject update(HttpServletRequest request, WritingVo writingVo) {
        String content = writingVo.getContent();
        content = Base64ToimageURL(content);
        writingVo.setUserId(ManagerUserUtil.getId());
        // articleService.updateWriting(writingVo);
        writingService.updateWriting(writingVo);

        return ResponseObject.newSuccessResponseObject("操作成功！");
    }

    public String Base64ToimageURL(String content) {

        Matcher matcher = IMG_PATTERN.matcher(content);
        while (matcher.find()) {
            String group = matcher.group(1);
            if (StringUtils.hasText(group)) {
                group = group.replaceAll("\"", "");
                // 存在base64编码的数据，才进行64Toimage转换以及上传
                if (group.split("base64,").length > 1) {
                    String str = group.split("base64,")[1];
                    byte[] b = org.apache.commons.codec.binary.Base64
                            .decodeBase64(str);
                    Attachment a = att.addAttachment(ManagerUserUtil.getId(),
                            AttachmentType.ONLINE, "1.png", b, "image/png");
                    content = content.replace(group, a.getUrl());
                }

            }
        }
        return content;
    }

    @RequestMapping(value = "toEdit")
    public String toEdit(HttpServletRequest request, String id) {

        WritingVo writingVo = writingService.findWritingById(id);

        ArticleVo article = articleService.findArticleById(Integer
                .parseInt(writingVo.getArticleId()));
        writingVo.setImgPath(article.getImgPath());
        writingVo.setContent(article.getContent());

        request.setAttribute("writing", writingVo);
        request.setAttribute("weburl", weburl);

        return HEADLINE_PATH_PREFIX + "/writingEdit";
    }

    /**
     * Description：增加或者修改关联的信息
     *
     * @param id
     * @param fieldId
     * @return ResponseObject
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    @RequestMapping(value = "updateMedicalDoctorWritings", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateMedicalDoctorWritings(String id,
                                                      String[] doctorId) {
        ResponseObject responseObject = new ResponseObject();
        writingService.updateMedicalDoctorWritings(id, doctorId);
        responseObject.setSuccess(true);
        responseObject.setResultObject("医师配置成功！");
        return responseObject;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "deletes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletes(String ids) {
        if (ids != null) {
            String[] idArr = ids.split(",");
            writingService.deletes(idArr);
        }
        return ResponseObject.newSuccessResponseObject("操作成功！");
    }

    /**
     * 修改状态(禁用or启用)
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateStatus(String id) {
        writingService.updateStatus(id);
        return ResponseObject.newSuccessResponseObject("操作成功！");
    }

    /**
     * 推荐
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "recommend", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject recommend(Integer id) {
        articleService.updateRecommend(id);
        return ResponseObject.newSuccessResponseObject("操作成功！");
    }
}
