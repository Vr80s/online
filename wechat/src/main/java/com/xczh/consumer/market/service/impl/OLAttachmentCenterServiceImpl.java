package com.xczh.consumer.market.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xczhihui.common.support.domain.Attachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczh.consumer.market.service.OLAttachmentCenterService;
import com.xczhihui.common.support.service.AttachmentCenterService;

@Service
public class OLAttachmentCenterServiceImpl implements OLAttachmentCenterService {
    private static final Logger logger = LoggerFactory.getLogger(OLAttachmentCenterServiceImpl.class);

    @Autowired
    private AttachmentCenterService attachmentCenterService;

    @Override
    public String upload(String createUserId, String projectName,
                         String fileName, String contentType, byte[] fileData,
                         String fileType, String ticket) {
        try {
            String headImgPath = attachmentCenterService.upload(createUserId, projectName, fileName, contentType, fileData, fileType);
            Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            Attachment a = g.fromJson(headImgPath, Attachment.class);
            return a.getUrl();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
