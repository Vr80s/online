package com.xczhihui.bxg.common.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xczhihui.bxg.common.support.domain.Attachment;
import com.xczhihui.bxg.common.support.service.AttachmentCenterService;
import com.xczhihui.user.center.bean.Token;
import com.xczhihui.user.center.web.utils.UCCookieUtil;

/**
 * 附件中心客户端Controller
 *
 * @author Haicheng Jiang
 */
@Controller
public class AttachmentCenterController extends AbstractController {

    @Autowired
    private AttachmentCenterService service;

    /**
     * 下载附件
     *
     * @param request
     * @param response
     * @param aid
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/attachmentCenter/download")
    public void download(HttpServletRequest request, HttpServletResponse response,
                         @RequestParam(value = "aid", required = true) String aid) {
        OutputStream out = null;
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            Attachment att = gson.fromJson(service.getAttachmentJson(aid), Attachment.class);
            if (att == null || att.getError() == 1) {
                throw new RuntimeException(att.getMessage());
            }
            String fileName = att.getOrgFileName();
            // 下载乱码解决
            fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.setContentType("multipart/octet-stream");
            out = response.getOutputStream();
            byte[] b = service.getAttachmentData(aid);
            response.addIntHeader("Content-Length", b.length);
            out.write(b);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 上传附件
     *
     * @param request
     * @param response
     * @param projectName dual双元，univ院校，online在线……
     * @param fileType    1图片，2附件
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/attachmentCenter/upload")
    public void upload(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "projectName", required = true) String projectName,
            @RequestParam(value = "fileType", required = false) String fileType) throws Exception {

        //防止中文乱码
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        //获得文件
        MultipartFile attachmentFile = null;
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Iterator<String> item = multipartRequest.getFileNames();
        while (item.hasNext()) {
            attachmentFile = multipartRequest.getFile(item.next());
        }
        if (attachmentFile == null || attachmentFile.getSize() <= 0) {
            response.getWriter().write(new Gson().toJson(new Attachment(1, "请上传文件！")));
            return;
        }

        //图片允许上传的文件扩展名
        String extname = attachmentFile.getOriginalFilename();
        String lowerCase = extname.toLowerCase();
        if ("1".equals(fileType)) {
            if (!lowerCase.endsWith("image")
                    && !lowerCase.endsWith("gif")
                    && !lowerCase.endsWith("jpg")
                    && !lowerCase.endsWith("png")
                    && !lowerCase.endsWith("bmp")) {
                response.getWriter().write(new Gson().toJson(new Attachment(1, "不支持的图片类型：“" + lowerCase + "”！")));
                return;
            }
        }

        String userId = projectName;
        //获得登录成功的token
        Token token = UCCookieUtil.readTokenCookie(request);
        if (token != null) {
            userId = String.valueOf(token.getUserId());
        }

        response.getWriter().write(service.upload(
                userId, //用户中心的用户ID
                projectName, attachmentFile.getOriginalFilename(), attachmentFile.getContentType(),
                attachmentFile.getBytes(), fileType)
        );
    }

    /**
     * 根据aid获得一个附件的json数据
     *
     * @param request
     * @param response
     * @param aid
     * @throws Exception
     */
    @RequestMapping(value = "/attachmentCenter/getAttachment")
    public void getAttachment(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam(value = "aid", required = true) String aid) throws Exception {
        //防止中文乱码
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setContentType("text/html");
        response.getWriter().write(service.getAttachmentJson(aid));

    }

    @RequestMapping(value = "/attachmentCenter/getAttachmentByFileName")
    @ResponseBody
    public Attachment getAttachmentByFileName(HttpServletRequest request,
                                              HttpServletResponse response, String fileName) throws Exception {
        return service.getAttachmentByFileName(fileName);
    }
}
