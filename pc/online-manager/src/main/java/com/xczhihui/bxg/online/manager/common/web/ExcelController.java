package com.xczhihui.bxg.online.manager.common.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;

import com.xczhihui.bxg.common.support.domain.Attachment;
import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.support.service.AttachmentCenterService;
import com.xczhihui.bxg.common.support.service.AttachmentType;
import com.xczhihui.bxg.common.web.auth.UserHolder;
import com.xczhihui.bxg.online.manager.common.util.FileType;
import com.xczhihui.bxg.online.manager.common.vo.KindeditorVo;
import com.xczhihui.user.center.bean.Token;
import com.xczhihui.user.center.web.utils.UCCookieUtil;

/**
 * ExcelController
 * 
 * @author Milton
 * @date 2015年12月4日 下午2:32:33
 */
@Controller
@RequestMapping("excel")
public class ExcelController {
	@Autowired
	private AttachmentCenterService attachmentCenterService;
	// 最大文件大小
	long maxSize = 1048576;
	/*
	 * @RequestMapping(value = "uploadKindeditor")
	 * @ResponseBody public void uploadKindeditor(HttpServletRequest request,
	 * HttpServletResponse response) { KindeditorVo kindeditorVo = new
	 * KindeditorVo(); MultipartHttpServletRequest multipartRequest =
	 * (MultipartHttpServletRequest) request; Iterator<String> it =
	 * multipartRequest.getFileNames(); String newFileName = "";// 新文件名 try {
	 * while (it.hasNext()) { String fileName = it.next(); MultipartFile
	 * uploadFile = multipartRequest.getFile(fileName); long size =
	 * uploadFile.getSize(); if (size > 500 * 1024)// 只能上传500K以内的文件 {
	 * kindeditorVo.setError(1); kindeditorVo.setMessage("只能上传500KB以内的图片！"); }
	 * else { String extName = "";// 扩展名 String nowTime = new
	 * SimpleDateFormat("yyyyMMddHHmmssSSS") .format(new Date());// 当前时间 毫秒数
	 * String filename = uploadFile.getOriginalFilename(); InputStream
	 * inputStream = uploadFile.getInputStream(); FileType fileType =
	 * getType(inputStream); if (fileType == null ||
	 * !fileType.getValue().equals( FileType.PNG.getValue()) &&
	 * !fileType.getValue().equals( FileType.JPEG.getValue()) &&
	 * !fileType.getValue().equals( FileType.GIF.getValue())) {
	 * kindeditorVo.setError(1); kindeditorVo.setMessage("图片格式不正确！"); } else {
	 * if (filename.lastIndexOf(".") >= 0) { extName =
	 * filename.substring(filename .lastIndexOf(".")); } newFileName = nowTime +
	 * extName; String savePath = request.getSession()
	 * .getServletContext().getRealPath("/"); savePath = savePath +
	 * File.separatorChar + "temp/"; File dir = new File(savePath); if
	 * (!dir.exists()) { dir.mkdirs(); } savePath += newFileName; File file =
	 * new File(savePath);// 在本地临时目录生成图片 uploadFile.transferTo(file);
	 * kindeditorVo.setUrl(savePath); kindeditorVo.setError(0); } } } } catch
	 * (Exception e) { kindeditorVo.setError(1); kindeditorVo.setMessage("上传出错："
	 * + e.toString()); } Gson gson = new Gson(); try {
	 * response.getWriter().write(gson.toJson(kindeditorVo)); } catch
	 * (IOException e) { e.printStackTrace(); } }
	 */

	@RequestMapping(value = "uploadKindeditor")
	@ResponseBody
	public void uploadKindeditor(HttpServletRequest request,
	        HttpServletResponse response) throws IOException {
		KindeditorVo kindeditorVo = new KindeditorVo();
		Gson gson = new Gson();
		// 获得登陆成功的token
		Token token = UCCookieUtil.readTokenCookie(request);
		if (token == null) {
			kindeditorVo.setError(1);
			kindeditorVo.setMessage("请登录！");
			response.getWriter().write(gson.toJson(kindeditorVo));
			return;
		}

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Iterator<String> it = multipartRequest.getFileNames();
		try {
			while (it.hasNext())
			{
				String fileName = it.next();
				MultipartFile uploadFile = multipartRequest.getFile(fileName);
				long size = uploadFile.getSize();
				if (size > 3 * 1024 * 1024)// 只能上传3M以内的文件
				{
					kindeditorVo.setError(1);
					kindeditorVo.setMessage("只能上传3M以内的文件！");
					response.getWriter().write(gson.toJson(kindeditorVo));
					return;
				}
				String filename = uploadFile.getOriginalFilename();
				// 检查扩展名
				String fileExt = filename.substring(
				        filename.lastIndexOf(".") + 1).toLowerCase();
				if (!"gif".equals(fileExt) && !"jpg".equals(fileExt) && !"jpeg".equals(fileExt)
				        && !"png".equals(fileExt) && !"bmp".equals(fileExt)) {
					kindeditorVo.setError(1);
					kindeditorVo.setMessage("图片格式不正确！");
					response.getWriter().write(gson.toJson(kindeditorVo));
					return;
				}
				BxgUser user = UserHolder.getCurrentUser();
				Attachment attachment = new Attachment();
				String contextPath = request.getContextPath();
				attachment = attachmentCenterService.addAttachment(
				        user.getId(), AttachmentType.KCENTER,
				        uploadFile.getOriginalFilename(),
				        uploadFile.getBytes(), uploadFile.getContentType(),
				        null);
				kindeditorVo.setUrl(contextPath
				        + "/attachmentCenter/download?aid="
				        + attachment.getId());
				kindeditorVo.setError(0);
				response.getWriter().write(gson.toJson(kindeditorVo));
			}
		} catch (Exception e) {
			kindeditorVo.setError(1);
			kindeditorVo.setMessage("上传出错：" + e.toString());
		}

	}

	/**
	 * kindeditor图片上传
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @author fanyinbo
	 * @date 2016年2月17日 下午3:45:29
	 */
	@RequestMapping(value = "uploadimg")
	@ResponseBody
	public void addUploadImg(HttpServletRequest request,
	        HttpServletResponse response) throws IOException {

		// 定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		Iterator<String> item = multipartRequest.getFileNames();
		String dirName = request.getParameter("dir");

		while (item.hasNext()) {
			// 获取文件名
			String fileName = item.next();
			MultipartFile file = multipartRequest.getFile(fileName);
			// 检查文件大小
			if (file.getSize() > maxSize) {
				response.getWriter().write(getError("上传文件大小超过限制。"));
				return;
			}
			// 检查扩展名
			String fileExt = file.getOriginalFilename()
			        .substring(file.getOriginalFilename().lastIndexOf(".") + 1)
			        .toLowerCase();

			// 检查是否包含上传的文件名
			if (!Arrays.asList(extMap.get(dirName).split(","))
			        .contains(fileExt)) {
				response.getWriter().write(
				        getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName)
				                + "格式。"));
				return;
			}
			Attachment attachment = new Attachment();
			BxgUser currentUser = UserHolder.getCurrentUser();

			attachmentCenterService.addAttachment(currentUser.getId(),
			        AttachmentType.OTHER, file.getOriginalFilename(),
			        file.getBytes(), file.getContentType(), null);
			// 请求的路径
			String contextPath = request.getContextPath();
			KindeditorVo kindeditorMessage = new KindeditorVo();
			kindeditorMessage.setUrl(contextPath
			        + "/attachmentCenter/download?aid="
			        + attachment.getId());
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(kindeditorMessage));
		}
	}
	
	@RequestMapping(value = "upload")
	@ResponseBody
	public String uploadFile(MultipartFile uploadify, HttpServletRequest request) {
		String newFileName = "";// 新文件名
		try {
			long size = uploadify.getSize();
			if (size > 20000000)// 只能上传20M以内的文件
			{
				return "failure:只能上传20M以内的文件！";
			}

			String extName = "";// 扩展名
			String nowTime = new SimpleDateFormat("yyyyMMddHHmmssSSS")
			        .format(new Date());// 当前时间 毫秒数
			String filename = uploadify.getOriginalFilename();
			String savePath = request.getSession().getServletContext()
			        .getRealPath("/");
			savePath = savePath + File.separatorChar + "temp/";
			File dir = new File(savePath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			if (filename.lastIndexOf(".") >= 0) {
				extName = filename.substring(filename.lastIndexOf("."));
			}
			BxgUser user = UserHolder.getCurrentUser();
			newFileName = user.getLoginName() + nowTime + extName;
			File file = new File(savePath + newFileName);

			uploadify.transferTo(file);
		} catch (Exception e) {
			return "failure:上传出错：" + e.toString();
		}
		return newFileName;
	}
	
	@RequestMapping(value = "uploadzip")
	@ResponseBody
	public String uploadzip(MultipartFile zipraratt, HttpServletRequest request) {
		try {
			String filename = zipraratt.getOriginalFilename();
			String savePath = request.getSession().getServletContext()
			        .getRealPath("/");
			savePath = savePath + File.separatorChar + "temp/";
			File dir = new File(savePath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			String extName = "";// 扩展名
			if (filename.lastIndexOf(".") >= 0) {
				extName = filename.substring(filename.lastIndexOf("."));
			}
			String newFileName = "zipFile" + extName;
			File file = new File(savePath + newFileName);
			zipraratt.transferTo(file);
			return "1";
		} catch (Exception e) {
			return "failure:上传出错：" + e.toString();
		}
	}
	
	private String getError(String message) {
		KindeditorVo test = new KindeditorVo();
		test.setError(1);
		test.setMessage(message);
		Gson gson = new Gson();
		return gson.toJson(test);
	}
}
