package com.xczhihui.attachment.center.server.service;

import java.io.File;
import java.util.List;
import java.util.Set;

import com.xczhihui.attachment.center.server.bean.Attachment1;
import com.xczhihui.bxg.common.support.domain.Attachment;
import com.xczhihui.bxg.common.support.service.AttachmentType;

/**
 * 附件中心，统一附件处理的实体类
 * @author Haicheng Jiang
 */
public interface AttachmentService {
	
	/**
	 * 添加附件
	 * @param createUserId
	 * @param type
	 * @param fileName
	 * @param fileData
	 * @return
	 */
	public Attachment addAttachment(String createUserId, AttachmentType type, String fileName, byte[] fileData);

	/**
	 * 删除一个附件,删除数据库记录及磁盘文件。
	 * @param id
	 * @return
	 */
	public Attachment deleteAttachment(String id);

	/**
	 * 根据文件名和类型删除附件。
	 * @param fileName
	 * @param type
	 * @return
	 */
	public Attachment deleteAttachment(String fileName, AttachmentType type);

	/**
	 * @param id
	 * @return
	 */
	public Attachment getAttachment(String id);
	/**
	 * 根据文件名获得附件信息
	 * @param fileName
	 * @return
	 */
	public Attachment getAttachmentByFileName(String fileName);

	/**
	 * 批量获取附件
	 * @param ids
	 * @return
	 */
	public List<Attachment> getAttachments(Set<String> ids);

	/**
	 * 获取附件的文件数据
	 * @param attachment
	 * @return
	 */
	public byte[] getFileData(Attachment attachment);

	/**
	 * 修改附件存放的磁盘根路径(如:/data/attachment/)
	 * @param path
	 */
	public void setAttachmentRootPath(String path);

	/**
	 * 获取附件存放的磁盘根路径(如:/data/attachment/)
	 * @return
	 */
	public String getAttachmentRootPath();

	/**
	 * 传入相对路径，获取绝对路径。
	 * @return getAttachmentRootPath() + relativePath
	 */
	public String getAbsPathFileName(String relativePath);

	/**
	 * 获取附件的文件数据
	 * @param id
	 * @return
	 */
	public byte[] getFileDataById(String id);

	/**
	 * 传入相对路径，获取文件
	 * @return
	 */
	public byte[] getFileDataByPath(String relativePath);
	/**
	 * 上传图片
	 * @param type
	 * @param fileName
	 * @param fileData
	 * @return
	 */
	public Attachment addImage(AttachmentType type, String fileName, byte[] fileData);

	
	/**
	 * 新附件上传保存方法
	 * @param createUserId
	 * @param type
	 * @param fileName
	 * @param size
	 * @return
	 */
	public Attachment saveAttachmentOnUpload(String fileType,String createUserId, AttachmentType type, String fileName ,String storeName, String storePath, long size);
 
	/**
	 *  多个附件ID获取文件
	 * @param ids
	 * @return
	 */
	public List<File> getAttachmentFiles(List<String> ids);

	/**
	 * 保存完整的附件实体信息，包括id,在内网附件改造上传的时添加，用于接收内网传过来的保存后在完整附件信息
	 * @param attachment
	 */
	public void saveFullAttachment(Attachment1 attachment);
	
}
