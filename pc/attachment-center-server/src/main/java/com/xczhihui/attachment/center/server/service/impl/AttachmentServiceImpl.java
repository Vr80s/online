package com.xczhihui.attachment.center.server.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xczhihui.attachment.center.server.bean.Attachment1;
import com.xczhihui.attachment.center.server.service.AttachmentService;
import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.support.domain.Attachment;
import com.xczhihui.bxg.common.support.service.AttachmentType;
import com.xczhihui.bxg.common.util.FileUtil;
/**
 * 附件中心service的实现类
 * @author Haicheng Jiang
 */
@Service("attachmentService")
public class AttachmentServiceImpl implements AttachmentService {

	
	private SimpleHibernateDao simpleDao;

	@Value("${attachment.path:/data/attachment/}")
	private String rootPath = "/data/attachment/";
	
	@Value("${attachment.url:http://attachment-center.ixincheng.com}")
	private String attachmentUrl = "http://attachment-center.ixincheng.com";
	
	@Value("${picture.path:/data/picture/}")
	private String picturePath = "/data/picture/";
	
	public Attachment addAttachmentDirect(String createUserId, AttachmentType type, String fileName, byte[] fileData) {
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
	public Attachment getAttachmentByFileName(String fileName) {
		return this.simpleDao.findOneEntitiyByProperty(Attachment.class, "fileName", fileName);
	}

	@Override
	public byte[] getFileDataById(String id) {
		Attachment att = this.getAttachment(id);
		return this.getFileData(att);
	}

	public byte[] getFileData(Attachment attachment) {
		String absPath = this.getAbsPathFileName(attachment);
		File f = new File(absPath);
		if(!f.exists()){
			return null;
		}
		f =  null;
		byte[] data = FileUtil.readFromFile(absPath);
		return data;
	}

	@Override
	public List<Attachment> getAttachments(Set<String> ids) {
		return this.simpleDao.findEntities(Attachment.class, ids);
	}
	
	private void savePictureData(String path,String name, byte[] fileData) {
		String absPath = this.getAbsPathPictrureName(path,name);
		FileUtil.writeToFile(absPath, fileData);
	}

	private String getAbsPathPictrureName(String path,String name) {
		return this.getPicturePath() + path + "/" + name;
	}

	@Override
	public void setAttachmentRootPath(String path) {
		this.rootPath = path;
	}

	@Override
	public String getAttachmentRootPath() {
		return this.rootPath;
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public SimpleHibernateDao getSimpleDao() {
		return simpleDao;
	}

	@Resource(name = "simpleHibernateDao")
	public void setSimpleDao(SimpleHibernateDao simpleDao) {
		this.simpleDao = simpleDao;
	}

	@Override
	public Attachment addImage(AttachmentType type, String fileName, byte[] fileData) {
		String ext = StringUtils.getFilenameExtension(fileName);
		String path = type.getName() + "/" + FileUtil.getYearMonthDayHourPath();
		String name = FileUtil.getUUIDFileName(ext);
		this.savePictureData(path, name, fileData);
		Attachment a = new Attachment();
		a.setError(0);
		a.setFileName(name);
		a.setOrgFileName(fileName);
		a.setUrl(attachmentUrl+picturePath+path+"/"+name);
		return a;
	}
	
	
	@Override
	public Attachment saveAttachmentOnUpload(String fileType,String createUserId, AttachmentType type, String fileName,String storeName, String storePath, long size) {
		if ("1".equals(fileType)) {//上传图片，返回附件id等信息：
			Attachment a = new Attachment();
			a.setError(0);
			a.setFileName(storeName);
			a.setOrgFileName(fileName);
			a.setUrl(attachmentUrl+picturePath + storePath+"/" + storeName);
			return a;		 
			
		} else if("2".equals(fileType)){//上传附件，返回附件id等信息：
			Attachment att = new Attachment();
			att.setOrgFileName(fileName);
			att.setFileSize((int)size);
			att.setBusinessType(type.getType());
			att.setCreateTime(new Date());
			att.setCreatePerson(createUserId);
			String ext = StringUtils.getFilenameExtension(fileName);
			att.setFileType(ext);
//			String path = type.getName() + "/" + FileUtil.getYearMonthDayHourPath();
//			String name = FileUtil.getUUIDFileName(ext);
			att.setFilePath(storePath);
			att.setFileName(storeName);
			att.setUrl(attachmentUrl + rootPath + storePath + "/" + storeName);
			this.simpleDao.saveOrUpdate(att);
			return att;  
			
		} else {
			return new Attachment(1,"请传入fileType参数，1图片，2附件");
		}

	}

//	@Override
//	public Attachment saveImageOnUpload(AttachmentType type, String fileName) {
//		String ext = StringUtils.getFilenameExtension(fileName);
//		String path = type.getName() + "/" + FileUtil.getYearMonthDayHourPath();
//		String name = FileUtil.getUUIDFileName(ext);
//		Attachment a = new Attachment();
//		a.setError(0);
//		a.setFileName(name);
//		a.setOrgFileName(fileName);
//		a.setUrl(attachmentUrl+picturePath+path+"/"+name);
//		return a;
//	}
	
	
	
	
	public String getAttachmentUrl() {
		return attachmentUrl;
	}

	public void setAttachmentUrl(String attachmentUrl) {
		this.attachmentUrl = attachmentUrl;
	}

	/**
	 * 多个附件ID获取文件
	 */
	@Override
	public List<File> getAttachmentFiles(List<String> ids) {
		Set<String> sids = new HashSet<String>(ids);
		List<Attachment> atts = this.simpleDao.findEntities(Attachment.class, sids);
		List<File> files = new ArrayList<File>();
		if(atts!=null && atts.size()>0){
			for(Attachment attachment :atts){
				String absPath = this.getAbsPathFileName(attachment);
				File f = new File(absPath);
				files.add(f);
			}
		}
		return files;
	}

	@Override
	public void saveFullAttachment(Attachment1 attachment) {
		simpleDao.save(attachment);
	}
	
}
