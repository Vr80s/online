package com.xczhihui.bxg.common.web.controller;

import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xczhihui.bxg.common.support.domain.Attachment;
import com.xczhihui.bxg.common.support.domain.BasicEntity;
import com.xczhihui.bxg.common.support.service.AttachmentService;
import com.xczhihui.bxg.common.support.service.AttachmentType;

@Controller
public class AttachmentController extends AbstractController{

	@Autowired
	private AttachmentService attachmentService;

	/**
	 * 获取附件的路径。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/attachment/path")
	@ResponseBody
	public String path(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String attId = ServletRequestUtils.getRequiredStringParameter(request, "aid");
		Attachment att = attachmentService.getAttachment(attId);
		return att.getFilePath();
	}

	@RequestMapping(value = "/attachment/download")
	public String downloadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String attId = ServletRequestUtils.getRequiredStringParameter(request, "aid");
		Attachment att = attachmentService.getAttachment(attId);
		String fileName = att.getOrgFileName();
		// 下载乱码解决
		Boolean flag= request.getHeader("User-Agent").indexOf("like Gecko")>0;
		if (request.getHeader("User-Agent").toLowerCase().indexOf("msie") >0||flag){
			fileName = URLEncoder.encode(fileName, "UTF-8");
	    }else{
	    	fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
	    }
		response.setHeader("Content-Disposition", "attachment; filename=\"" +  fileName+"\"");
		response.setContentType("multipart/octet-stream");
		OutputStream out = response.getOutputStream();
		byte[] b = attachmentService.getFileData(att);
		response.addIntHeader("Content-Length", b.length);
		out.write(b);
		return null;
	}

	@RequestMapping(value = "/attachment/upload")
	@ResponseBody
	public Object uploadFile(HttpServletRequest request,
			@RequestParam(value = "attachment", required = false) MultipartFile attachmentFile) throws Exception {
		String stype = ServletRequestUtils.getRequiredStringParameter(request, "type");
		AttachmentType atype = AttachmentType.valueOf(stype.toUpperCase());
		Subject subject = SecurityUtils.getSubject();
		BasicEntity user = (BasicEntity) subject.getPrincipal();
		String createUserId = "";
		if (user != null) {
			createUserId = user.getId();
		}
		Attachment att = this.attachmentService.addAttachment(createUserId, atype, attachmentFile.getOriginalFilename(),
				attachmentFile.getBytes());
		return att;
	}
}
