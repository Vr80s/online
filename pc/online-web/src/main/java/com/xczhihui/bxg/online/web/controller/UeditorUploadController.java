package com.xczhihui.bxg.online.web.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.xczhihui.common.support.domain.Attachment;
import com.xczhihui.common.support.service.AttachmentCenterService;
import com.xczhihui.bxg.common.util.JsonUtil;
import com.xczhihui.bxg.common.util.QiniuUtil;
import com.xczhihui.user.center.bean.Token;
import com.xczhihui.user.center.web.utils.UCCookieUtil;

/**
 * 百度富文本编辑器（ueditor）
 *
 * @author Haicheng Jiang
 */
@Controller
@RequestMapping("/ueditor")
public class UeditorUploadController {
    @Autowired
    private AttachmentCenterService service;

    /**
     * 上传
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "upload")
    @ResponseBody
    public Map<String, Object> upload(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, Object> mp = new HashMap<>();

        // 获得文件
        MultipartFile attachmentFile = null;
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Iterator<String> item = multipartRequest.getFileNames();
        while (item.hasNext()) {
            attachmentFile = multipartRequest.getFile(item.next());
        }
        if (attachmentFile == null) {
            throw new NullPointerException("文件为空");
        }

        String userId = "onlineWeb";
        // 获得登录成功的token
        Token token = UCCookieUtil.readTokenCookie(request);
        if (token != null) {
            userId = String.valueOf(token.getUserId());
        }

        String upload = service.upload(userId, // 用户中心的用户ID
                "online", attachmentFile.getOriginalFilename(), attachmentFile.getContentType(),
                attachmentFile.getBytes(), "2");

        Attachment a = JsonUtil.getBaseGson().fromJson(upload, Attachment.class);
        mp.put("original", a.getOrgFileName());
        mp.put("name", a.getFileName());
        mp.put("url", QiniuUtil.slim(a.getUrl()));
        mp.put("size", a.getFileSize());
        mp.put("type", a.getFileType());
        mp.put("state", "SUCCESS");

        return mp;
    }
}
