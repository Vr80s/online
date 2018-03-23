package com.xczhihui.bxg.online.web.controller;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.xczhihui.bxg.common.support.domain.Attachment;
import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.support.service.AttachmentCenterService;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;

/**
 * 工具
 * 
 * @author Haicheng Jiang
 */
@RestController
@RequestMapping(value = "/online/util")
public class UtilController {
	@Autowired
	private AttachmentCenterService attService;

	/**
	 * 收藏/取消收藏
	 * 
	 * @param question_id
	 * @return
	 * @throws Exception 
	 * @throws IOException 
	 */
	@RequestMapping(value = "/upload4Simditor")
	public void upload4Simditor(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			//防止中文乱码
			response.setCharacterEncoding("UTF-8");  
			response.setContentType("application/json; charset=utf-8");
			
			MultipartFile attachmentFile = null;
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Iterator<String> item = multipartRequest.getFileNames();
			while (item.hasNext()) {
				attachmentFile = multipartRequest.getFile(item.next());
			}
			if (attachmentFile == null || attachmentFile.getSize() <= 0) {
				response.getWriter().write(new Gson().toJson(new SimditorResponse(false, "请上传文件！", null)));
				return;
			}
			if (attachmentFile.getSize() > 1024 * 1024 * 2) {
				response.getWriter().write(new Gson().toJson(new SimditorResponse(false,"文件大小不能超过2M！",null)));
				return;
			}
			
			BxgUser u = UserLoginUtil.getLoginUser(request);
			String upload = attService.upload(u.getId(), "online", attachmentFile.getOriginalFilename(), 
					attachmentFile.getContentType(), attachmentFile.getBytes(), "1");
			
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
			Attachment att = gson.fromJson(upload, Attachment.class);
			
			response.getWriter().write(gson.toJson(new SimditorResponse(true,"上传成功！",att.getUrl())));
		} catch (Exception e) {
			response.getWriter().write(new Gson().toJson(new SimditorResponse(false,e.getMessage(),null)));
		}
	}
	
	@SuppressWarnings("unused")
	private class SimditorResponse{
		public SimditorResponse(boolean success,String msg,String file_path){
			this.success = success;
			this.msg = msg;
			this.file_path = file_path;
		}
		public boolean isSuccess() {
			return success;
		}
		public void setSuccess(boolean success) {
			this.success = success;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		public String getFile_path() {
			return file_path;
		}
		public void setFile_path(String file_path) {
			this.file_path = file_path;
		}
		private boolean success;
		private String msg;
		private String file_path;
	}
}
