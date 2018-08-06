package com.xczhihui.bxg.online.web.controller.vhallyun;


import static com.xczhihui.common.util.redis.key.RedisCacheKey.VHALLYUN_BAN_KEY;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xczhihui.bxg.online.web.base.utils.UserLoginUtil;
import com.xczhihui.bxg.online.web.body.vhall.VhallCallbackBody;
import com.xczhihui.bxg.online.web.controller.AbstractController;
import com.xczhihui.bxg.online.web.service.CourseService;
import com.xczhihui.common.support.domain.Attachment;
import com.xczhihui.common.support.domain.BxgUser;
import com.xczhihui.common.support.service.AttachmentCenterService;
import com.xczhihui.common.support.service.AttachmentType;
import com.xczhihui.common.support.service.CacheService;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.util.bean.VhallMessageParamsVo;
import com.xczhihui.common.util.enums.PlayBackType;
import com.xczhihui.common.util.vhallyun.BaseService;
import com.xczhihui.common.util.vhallyun.DocumentService;
import com.xczhihui.common.util.vhallyun.MessageService;
import com.xczhihui.common.util.vhallyun.VhallUtil;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.medical.anchor.service.IAnchorInfoService;

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

    @RequestMapping(value = "documentId", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject createDocument(@RequestParam("document") MultipartFile file) throws Exception {
        String userId = getUserId();
        Attachment attachment = attachmentCenterService.addAttachment(
                userId, AttachmentType.KCENTER,
                file.getOriginalFilename(),
                file.getBytes(), file.getContentType());
        String documentId = DocumentService.create(attachment.getUrl());
        if (StringUtils.isNotBlank(documentId)) {
            anchorInfoService.addDocument(userId, documentId, attachment.getOrgFileName());
        }
        return ResponseObject.newSuccessResponseObject(documentId);
    }

    @RequestMapping(value = "document", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject list() {
        return ResponseObject.newSuccessResponseObject(anchorInfoService.listDocument(getUserId()));
    }

    @RequestMapping(value = "callback", method = RequestMethod.POST)
    @ResponseBody
    public String callback(@RequestBody VhallCallbackBody vhallCallbackBody) throws Exception {
    	
    	LOGGER.info("into 点播生成回调 callback ");
    	LOGGER.info("vhallCallbackBody："+vhallCallbackBody.toString());
    	
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
           courseService.updatePlayBackStatusAndSendVahllYunMessageByRecordId(recordId,status);
        }
        return "success";
    }

    @RequestMapping(value = "message", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject getMessages(@RequestParam(name = "channelId") String channelId, @RequestParam(defaultValue = "0") int pos) throws Exception {
        VhallMessageParamsVo vmpv = new VhallMessageParamsVo();
        vmpv.setChannel_id(channelId);
        vmpv.setPos(String.valueOf(pos));
        vmpv.setType(String.valueOf(2));
        return ResponseObject.newSuccessResponseObject(MessageService.getMessageList(vmpv));
    }

    @RequestMapping(value = "message", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject sendMessage(@RequestParam String body, @RequestParam String channelId) throws Exception {
        MessageService.sendMessage("CustomBroadcast", body, channelId);
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
    
    
    @RequestMapping(value = "vhallYunSendMessage", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject customSendMessage(String body,String channel_id) throws Exception {
        BxgUser loginUser = UserLoginUtil.getLoginUser();
        JSONObject jsonObject =  (JSONObject) JSON.parse(body);
        if(jsonObject.get("type")!=null && jsonObject.get("type").toString().equals("1")) {
            Boolean isShutup =  cacheService.sismenber(VHALLYUN_BAN_KEY + channel_id, loginUser.getId());
            if(!isShutup) {
                return ResponseObject.newErrorResponseObject("你被禁言了");
            } 
        } 
        return ResponseObject.newSuccessResponseObject(MessageService.sendMessage(MessageService.CustomBroadcast,body,channel_id));
    }
    
}