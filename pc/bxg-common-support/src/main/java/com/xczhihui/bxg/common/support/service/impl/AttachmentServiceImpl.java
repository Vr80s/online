package com.xczhihui.bxg.common.support.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.support.domain.Attachment;
import com.xczhihui.bxg.common.support.service.AttachmentService;
import com.xczhihui.bxg.common.support.service.AttachmentType;
import com.xczhihui.bxg.common.util.FileUtil;

@Service("attachmentService")
public class AttachmentServiceImpl implements AttachmentService {

	private SimpleHibernateDao simpleDao;
	
	@Value("${attachment.url:http://attachment.ixincheng.com}")
	private String attachmentUrl = "http://attachment.ixincheng.com";

	@Value("${attachment.path:/data/attachment/}")
	private String rootPath = "/data/attachment/";

	@Override
	public Attachment addAttachment(String createUserId, AttachmentType type, String fileName, byte[] fileData) {
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
	public Attachment deleteAttachment(String id) {
		Attachment att = this.getAttachment(id);
		this.simpleDao.delete(att);
		if (att != null) {
			String absPath = this.getAbsPathFileName(att);
			FileUtil.deleteFile(absPath);
		}
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

	private void saveFileData(Attachment att, byte[] fileData) {
		String absPath = this.getAbsPathFileName(att);
		FileUtil.writeToFile(absPath, fileData);
	}

	private String getAbsPathFileName(Attachment att) {
		return this.getAttachmentRootPath() + att.getFilePath() + "/" + att.getFileName();
	}

	@Override
	public String getAbsPathFileName(String relativePath) {
		return this.getAttachmentRootPath() + relativePath;
	}

	@Override
	public byte[] getFileDataByPath(String relativePath) {
		String absPath = getAbsPathFileName(relativePath);
		byte[] data = FileUtil.readFromFile(absPath);
		return data;
	}

	@Override
	public Attachment getAttachment(String id) {
		return this.simpleDao.get(id, Attachment.class);
	}

	@Override
	public byte[] getFileDataById(String id) {
		Attachment att = this.getAttachment(id);
		return this.getFileData(att);
	}

	public byte[] getFileData(Attachment attachment) {
		String absPath = this.getAbsPathFileName(attachment);
		byte[] data = FileUtil.readFromFile(absPath);
		return data;
	}

	@Override
	public List<Attachment> getAttachments(Set<String> ids) {
		return this.simpleDao.findEntities(Attachment.class, ids);
	}

	@Resource(name = "simpleHibernateDao")
	public void setSimpleHibernateDao(SimpleHibernateDao simpleDao) {
		this.simpleDao = simpleDao;
	}

	@Override
	public void setAttachmentRootPath(String path) {
		this.rootPath = path;
	}

	@Override
	public String getAttachmentRootPath() {
		return this.rootPath;
	}
}
