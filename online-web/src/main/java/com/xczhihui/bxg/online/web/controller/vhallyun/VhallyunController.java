package com.xczhihui.bxg.online.web.controller.vhallyun;


import static com.xczhihui.common.util.redis.key.RedisCacheKey.VHALLYUN_BAN_KEY;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.base.utils.UserLoginUtil;
import com.xczhihui.bxg.online.web.body.vhall.VhallCallbackBody;
import com.xczhihui.bxg.online.web.controller.AbstractController;
import com.xczhihui.bxg.online.web.service.OnlineUserCenterService;
import com.xczhihui.common.support.domain.Attachment;
import com.xczhihui.common.support.service.AttachmentCenterService;
import com.xczhihui.common.support.service.AttachmentType;
import com.xczhihui.common.support.service.CacheService;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.util.bean.VhallMessageParamsVo;
import com.xczhihui.common.util.enums.VhallCustomMessageType;
import com.xczhihui.common.util.vhallyun.*;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.medical.anchor.service.IAnchorInfoService;
import com.xczhihui.user.center.service.UserCenterService;

/**
 * 微吼云
 *
 * @author hejiwei
 */
@RequestMapping("vhallyun")
@Controller
public class VhallyunController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(VhallyunController.class);

    @Autowired
    private AttachmentCenterService attachmentCenterService;
    @Autowired
    private IAnchorInfoService anchorInfoService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private ICourseService courseService;
    @Autowired
    private OnlineUserCenterService onlineUserCenterService;
    @Autowired
    private UserCenterService userCenterService;

    @RequestMapping(value = "publishStream/accessToken", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject getPublishStreamAccessToken(@RequestParam String roomId) throws Exception {
        return ResponseObject.newSuccessResponseObject(BaseService.createAccessToken4Live(getUserId(), roomId, null));
    }

    @RequestMapping(value = "chat/accessToken", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject getChatAccessToken(@RequestParam String channelId) throws Exception {
        return ResponseObject.newSuccessResponseObject(BaseService.createAccessToken4Live(getUserId(), null, channelId));
    }

    @RequestMapping(value = "vhallYunToken", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject getAccessToken(@RequestParam String roomId, @RequestParam String channelId) throws Exception {
        return ResponseObject.newSuccessResponseObject(BaseService.createAccessToken4Live(getUserId(), roomId, channelId));
    }

    @RequestMapping(value = "documentId", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject createDocument(@RequestParam("document") MultipartFile file) throws Exception {
        String userId = getUserId();
        Attachment attachment = attachmentCenterService.addAttachment(
                userId, AttachmentType.KCENTER,
                file.getOriginalFilename().replace(" ", "-"),
                file.getBytes(), file.getContentType());
        String documentId = DocumentService.create(attachment.getUrl());
        if (StringUtils.isNotBlank(documentId)) {
            anchorInfoService.addDocument(userId, documentId, attachment.getOrgFileName());
        }
        return ResponseObject.newSuccessResponseObject(ImmutableMap.of("documentId", documentId, "filename", attachment.getOrgFileName(), "createTime", new Date()));
    }

    @RequestMapping(value = "document", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject list() {
        return ResponseObject.newSuccessResponseObject(anchorInfoService.listDocument(getUserId()));
    }

    @RequestMapping(value = "document/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseObject deleteDoc(@PathVariable String id) {
        anchorInfoService.deleteDocument(id);
        return ResponseObject.newSuccessResponseObject();
    }

    @RequestMapping(value = "callback")
    @ResponseBody
    public String callback(@RequestBody VhallCallbackBody vhallCallbackBody) throws Exception {

        LOGGER.info("into 点播生成回调 callback ");
        LOGGER.info("vhallCallbackBody：" + vhallCallbackBody.toString());

        String signature = vhallCallbackBody.getSignature();
        if (StringUtils.isBlank(signature)) {
            LOGGER.error("callback error, signature is blank.");
            return "fail";
        }
        String callbackSign = VhallUtil.getCallbackSign(vhallCallbackBody.getParams());
        if (!signature.equals(callbackSign)) {
            LOGGER.error("callback error, signature invalid. oldSign:{} newSign:{}", signature, callbackSign);
            LOGGER.error("vhallCallbackBody: {}", vhallCallbackBody);
            return "fail";
        }

        if (vhallCallbackBody.isTransOverEvent()) {
            String documentId = vhallCallbackBody.getDocumentId();
            Integer status = vhallCallbackBody.getStatus();
            anchorInfoService.updateDocumentStatus(documentId, status);
        }

        /**
         * 回放生成成功
         */
        if (vhallCallbackBody.isCreatedEvent()) {
            String recordId = vhallCallbackBody.getRecordId();
            Integer status = vhallCallbackBody.getStatus();

            //更改回放状态
            courseService.updatePlayBackStatusAndSendVahllYunMessageByRecordId(recordId, status);
        }
        return "success";
    }

    @RequestMapping(value = "message", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject getMessages(VhallMessageParamsVo vmpv) throws Exception {
        return ResponseObject.newSuccessResponseObject(MessageService.getMessageList(vmpv));
    }

    @RequestMapping(value = "message", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject sendMessage(@RequestParam String body, @RequestParam String channelId) throws Exception {
        MessageService.sendMessage("CustomBroadcast", body, channelId);
        return ResponseObject.newSuccessResponseObject();
    }

    @RequestMapping(value = "userInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject initUserInfo() {
        onlineUserCenterService.updateVhallYunInfo();
        return ResponseObject.newSuccessResponseObject();
    }

    @RequestMapping(value = "ban/{channelId}/{userId}/{status}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateBanStatus(@PathVariable boolean status, @PathVariable String channelId, @PathVariable String userId) {
        //禁言
        if (status) {
            cacheService.sadd(VHALLYUN_BAN_KEY + channelId, userId);
        } else {
            cacheService.srem(VHALLYUN_BAN_KEY + channelId, userId);
        }
        return ResponseObject.newSuccessResponseObject();
    }

    @RequestMapping(value = "banStatus", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject getBanStatus(@RequestParam String channelId, @RequestParam String accountId) {
        return ResponseObject.newSuccessResponseObject(cacheService.sismenber(VHALLYUN_BAN_KEY + channelId, accountId));
    }

    @RequestMapping(value = "roomJoinStudent", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject listStudent(@RequestParam String channelId, @RequestParam int pos, @RequestParam int limit) {
        List<String> userIds = RoomService.listOnlineUsers(channelId, pos, limit);
        if (userIds != null && !userIds.isEmpty()) {
            List<Map<String, Object>> users = userCenterService.findByIds(userIds);
            Map<String, Map<String, Object>> userMap = users.stream().collect(Collectors.toMap(user -> user.get("id").toString(), user -> user));
            return ResponseObject.newSuccessResponseObject(userIds.stream().filter(userMap::containsKey).map(userMap::get)
                    .peek(user -> user.put("ban", cacheService.sismenber(VHALLYUN_BAN_KEY + channelId, user.get("id").toString())))
                    .collect(Collectors.toList()));
        }
        return ResponseObject.newSuccessResponseObject(Collections.EMPTY_LIST);
    }

    @RequestMapping(value = "vhallYunSendMessage", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject customSendMessage(String body, String channel_id) throws Exception {
        OnlineUser ou = (OnlineUser) UserLoginUtil.getLoginUser();

        JSONObject jsonObject = (JSONObject) JSON.parse(body);
        if (jsonObject.get("type") != null && Integer.parseInt(jsonObject.get("type").toString()) == VhallCustomMessageType.CHAT_MESSAGE.getCode()) {
            Boolean isShutup = cacheService.sismenber(VHALLYUN_BAN_KEY + channel_id, ou.getId());
            if (isShutup) {
                return ResponseObject.newErrorResponseObject("你被禁言了");
            }
            //后台自动添加这几个参数
            JSONObject message = (JSONObject) jsonObject.get("message");
            
            message.put("userId", message.get("userId")!=null ? message.get("userId") : ou.getId());
            message.put("headImg", message.get("headImg")!=null ? message.get("headImg") : ou.getSmallHeadPhoto());
            message.put("username", message.get("username")!=null ? message.get("username") : ou.getName());
            
            jsonObject.put("message", message);
        }
        return ResponseObject.newSuccessResponseObject(MessageService.sendMessage(MessageService.CustomBroadcast, jsonObject.toJSONString(), channel_id));
    }
}
