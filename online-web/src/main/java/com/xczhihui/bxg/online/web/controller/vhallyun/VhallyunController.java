package com.xczhihui.bxg.online.web.controller.vhallyun;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xczhihui.bxg.online.web.controller.AbstractController;
import com.xczhihui.common.support.domain.Attachment;
import com.xczhihui.common.support.service.AttachmentCenterService;
import com.xczhihui.common.support.service.AttachmentType;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.util.vhallyun.BaseService;
import com.xczhihui.common.util.vhallyun.DocumentService;
import com.xczhihui.medical.anchor.service.IAnchorInfoService;

/**
 * 微吼云
 *
 * @author hejiwei
 */
@RequestMapping("vhallyun")
@Controller
public class VhallyunController extends AbstractController {
    @Autowired
    private AttachmentCenterService attachmentCenterService;
    @Autowired
    private IAnchorInfoService anchorInfoService;

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
}
