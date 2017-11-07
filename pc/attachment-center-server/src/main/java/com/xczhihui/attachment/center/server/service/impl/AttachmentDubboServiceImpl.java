package com.xczhihui.attachment.center.server.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.support.domain.Attachment;
import com.xczhihui.bxg.common.support.service.AttachmentCenterService;
import com.xczhihui.bxg.common.support.service.AttachmentType;
import com.xczhihui.bxg.common.util.FileUtil;
/**
 * 附件中心service的实现类
 * @author Haicheng Jiang
 */
@Service("attachmentDubboService")
public class AttachmentDubboServiceImpl implements AttachmentCenterService {
	
	@Value("${attachment.path:/data/attachment/}")
	private String rootPath = "/data/attachment/";
	
	@Value("${attachment.url:http://attachment-center.ixincheng.com}")
	private String attachmentUrl = "http://attachment-center.ixincheng.com";
	
	@Value("${picture.path:/data/picture/}")
	private String picturePath = "/data/picture/";
	
	private SimpleHibernateDao simpleDao;
	@Resource(name = "simpleHibernateDao")
	public void setSimpleDao(SimpleHibernateDao simpleDao) {
		this.simpleDao = simpleDao;
	}

	@Override
	public Attachment addAttachment(String createUserId, AttachmentType type, String fileName, byte[] fileData,
			String contentType, String ticket) {
		Attachment att = new Attachment();
		att.setOrgFileName(fileName);
		att.setFileSize(fileData.length);
		att.setBusinessType(type.getType());
		att.setCreateTime(new Date());
		att.setCreatePerson(createUserId);
		String ext = StringUtils.getFilenameExtension(fileName);
		att.setFileType(ext);
		String path = type.getName() + "/" + FileUtil.getYearMonthDayHourPath();
		String name = FileUtil.getUUIDFileName(ext);
		att.setFilePath(path);
		att.setFileName(name);
		att.setUrl(attachmentUrl+rootPath+path+"/"+name);
		this.saveFileData(att, fileData);
		this.simpleDao.save(att);
		return att;
	}

	@Override
	public String upload(String createUserId, String type, String fileName, String contentType, byte[] fileData,
			String flag, String ticket) throws Exception {
		Attachment att = this.addAttachment(createUserId,
				AttachmentType.valueOf(type.toUpperCase()),fileName,fileData,null,null);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		return gson.toJson(att);
	}

	@Override
	public String getAttachmentJson(String aid) throws Exception {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		return gson.toJson(this.simpleDao.get(aid, Attachment.class));
	}

	@Override
	public byte[] getAttachmentData(String aid) throws Exception {
		Attachment att = this.getAttachment(aid);
		return this.getFileData(att);
	}

	@Override
	public Attachment getAttachmentObject(String aid) throws Exception {
		return this.getAttachment(aid);
	}

	@Override
	public Attachment deleteAttachment(String id) {
		Attachment att = this.getAttachment(id);
		simpleDao.delete(att);
		return att;
	}

	@Override
	public Attachment deleteAttachment(String fileName, AttachmentType type) {
		if (fileName == null || fileName.length() < 1) {
			return null;
		}
		Attachment att = this.simpleDao.findOneEntitiyByProperty(Attachment.class, "fileName", fileName);
		if (att != null && att.getBusinessType() == type.getType()) {
			this.simpleDao.delete(att);
			String absPath = this.getAbsPathFileName(att);
			FileUtil.deleteFile(absPath);
		}
		return att;
	}

	@Override
	public String getAttachmentCenterPath() {
		return attachmentUrl;
	}
	
	private void saveFileData(Attachment att, byte[] fileData) {
		String absPath = this.getAbsPathFileName(att);
		FileUtil.writeToFile(absPath, fileData);
	}

	private String getAbsPathFileName(Attachment att) {
		return this.getAttachmentRootPath() + att.getFilePath() + "/" + att.getFileName();
	}
	
	public String getAttachmentRootPath() {
		return this.rootPath;
	}
	public Attachment getAttachment(String id) {
		return this.simpleDao.get(id, Attachment.class);
	}
	public byte[] getFileData(Attachment attachment) {
		String absPath = this.getAbsPathFileName(attachment);
		byte[] data = FileUtil.readFromFile(absPath);
		return data;
	}

	@Override
	public void saveFullAttachmentData(Attachment attachment) {
		simpleDao.save(attachment);
		
	}

	@Override
	public Attachment getAttachmentByFileName(String fileName) {
		return simpleDao.findOneEntitiyByProperty(Attachment.class, "fileName", fileName);
	}
	
}
