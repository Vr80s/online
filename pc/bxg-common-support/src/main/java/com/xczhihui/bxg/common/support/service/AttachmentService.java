package com.xczhihui.bxg.common.support.service;

import java.util.List;
import java.util.Set;

import com.xczhihui.bxg.common.support.domain.Attachment;

/**
 * 统一附件处理的实体类；这类附件跟web服务器存储在相同的服务器上。
 * 
 * @author liyong
 */
public interface AttachmentService {

	/**
	 * 添加一个附件
	 * 
	 * @param createUserId
	 *            创建人的ID
	 * @param type
	 *            区分个业务:squad,course等，在附件目录里有体现。
	 * @param fileName
	 * @param fileData
	 * @return
	 */
	public Attachment addAttachment(String createUserId, AttachmentType type, String fileName, byte[] fileData);

	/**
	 * 删除一个附件,删除数据库记录及磁盘文件。
	 * 
	 * @param id
	 * @return
	 */
	public Attachment deleteAttachment(String id);

	/**
	 * 根据文件名和类型删除附件。
	 * 
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
	 * 批量获取附件
	 * 
	 * @param ids
	 * @return
	 */
	public List<Attachment> getAttachments(Set<String> ids);

	/**
	 * 获取附件的文件数据
	 * 
	 * @param attachment
	 * @return
	 */
	public byte[] getFileData(Attachment attachment);

	/**
	 * 修改附件存放的磁盘根路径(如:/data/attachment/)
	 * 
	 * @param path
	 */
	public void setAttachmentRootPath(String path);

	/**
	 * 获取附件存放的磁盘根路径(如:/data/attachment/)
	 * 
	 * @return
	 */
	public String getAttachmentRootPath();

	/**
	 * 传入相对路径，获取绝对路径。
	 * 
	 * @return getAttachmentRootPath() + relativePath
	 */
	public String getAbsPathFileName(String relativePath);

	/**
	 * 获取附件的文件数据
	 * 
	 * @param id
	 * @return
	 */
	public byte[] getFileDataById(String id);

	/**
	 * 传入相对路径，获取文件
	 * 
	 * @return
	 */
	public byte[] getFileDataByPath(String relativePath);

}
