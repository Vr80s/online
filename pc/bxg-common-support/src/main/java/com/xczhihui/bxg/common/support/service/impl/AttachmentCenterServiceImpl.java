package com.xczhihui.bxg.common.support.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.xczhihui.bxg.common.support.domain.Attachment;
import com.xczhihui.bxg.common.support.service.AttachmentCenterService;
import com.xczhihui.bxg.common.support.service.AttachmentType;
import com.xczhihui.bxg.common.util.DateUtil;
import com.xczhihui.bxg.common.util.HttpUtil;
/**
 * 附件中心客户端service
 * @author Haicheng Jiang
 */
@Service("attachmentCenterService")
public class AttachmentCenterServiceImpl implements AttachmentCenterService {
	
	@Value("${attachment.center.path:http://attachment-center.ixincheng.com}")
	private String attachmentCenterPath = "http://attachment-center.ixincheng.com";

	@Override
	public String upload(String createUserId, String projectName, String fileName, String contentType,byte[] fileData,String fileType,String ticket) throws Exception {
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("createUserId",createUserId);
		parameters.put("projectName",projectName);
		parameters.put("fileType",fileType);
		Map<String, String> cookies = new HashMap<String, String>();
		return HttpUtil.uploadFileWithCookieHeader(attachmentCenterPath+"/attachment/upload", parameters, "attachment", fileName, contentType, fileData,cookies,null);
	}

	@Override
	public String getAttachmentJson(String aid) throws Exception {
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("aid",aid);
		return HttpUtil.sendPostRequest(attachmentCenterPath+"/attachment/getAttachmentJson", parameters);
	}

	@Override
	public byte[] getAttachmentData(String aid) throws Exception {
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("aid",aid);
		return HttpUtil.sendGetRequestBytes(attachmentCenterPath+"/attachment/download?aid="+aid);
	}
	
	@Override
	public Attachment getAttachmentObject(String aid) throws Exception {
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("aid",aid);
		String json = HttpUtil.sendPostRequest(attachmentCenterPath+"/attachment/getAttachmentJson", parameters);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		return gson.fromJson(json,Attachment.class);
	}

	@Override
	public String getAttachmentCenterPath() {
		return attachmentCenterPath;
	}

	public void setAttachmentCenterPath(String attachmentCenterPath) {
		this.attachmentCenterPath = attachmentCenterPath;
	}

	@Override
	public Attachment addAttachment(String createUserId, AttachmentType type, String fileName, byte[] fileData,String contentType,String ticket) {
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("createUserId",createUserId);
		parameters.put("projectName",type.getName());
		parameters.put("fileType","2");
		Map<String, String> cookies = new HashMap<String, String>();
		String json = HttpUtil.uploadFileWithCookieHeader(attachmentCenterPath+"/attachment/upload", parameters, "attachment", fileName, contentType, fileData,cookies,null);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		Attachment att = gson.fromJson(json,Attachment.class);
		return  att;
	}
	
	@Override
	public Attachment deleteAttachment(String id) {
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("id",id);
		String json = HttpUtil.sendPostRequest(attachmentCenterPath+"/attachment/deleteById", parameters);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		Attachment att = gson.fromJson(json,Attachment.class);
		return  att;
	}

	@Override
	public Attachment deleteAttachment(String fileName, AttachmentType type) {
		if (fileName == null || fileName.length() < 1) {
			return null;
		}
		
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("fileName",fileName);
		parameters.put("type",type.toString());
		
		String json = HttpUtil.sendPostRequest(attachmentCenterPath+"/attachment/deleteByFileName", parameters);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		Attachment att = gson.fromJson(json,Attachment.class);
		return  att;
	}

	@Override
	public void saveFullAttachmentData(Attachment a) {
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("id",a.getId());
		parameters.put("createPerson",a.getCreatePerson());
		parameters.put("createTime",DateUtil.formatDate(a.getCreateTime()));
//		parameters.put("isDelete",a.isDelete());
		parameters.put("businessType",a.getBusinessType()+"");
		parameters.put("fileName",a.getFileName());
		parameters.put("filePath",a.getFilePath());
		parameters.put("fileSize",a.getFileSize()+"");
		parameters.put("fileType",a.getFileType());
		parameters.put("orgFileName",a.getOrgFileName());
 
		HttpUtil.sendPostRequest(attachmentCenterPath+"/attachment/saveFullAttachmentData", parameters);
	}

	@Override
	public Attachment getAttachmentByFileName(String fileName) {
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("fileName",fileName);
		String json = HttpUtil.sendPostRequest(attachmentCenterPath+"/attachment/getAttachmentByFileName", parameters);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		Attachment att = gson.fromJson(json,Attachment.class);
		return  att;
	}
}
