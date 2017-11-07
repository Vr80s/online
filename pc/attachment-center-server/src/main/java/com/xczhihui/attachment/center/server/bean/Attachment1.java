package com.xczhihui.attachment.center.server.bean;
/** 
* @author  arvin 
* @version 创建时间：2017年1月18日 上午10:34:06 
* 类说明 
*/

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

/**
 * 用作可以设定保存id的附件实体
 * 
 * @author quanfu
 */
@Entity
@Table(name = "attachment", uniqueConstraints = { @UniqueConstraint(columnNames = { "file_name" }) })
public class Attachment1  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5248434936871781913L;

	@Id
	private String id;

	/**
	 * 实体是否删除
	 */
	@Column(name = "is_delete")
	private boolean isDelete;

	/**
	 * 创建人ID
	 */
	@Column(name = "create_person")
	private String createPerson;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private Date createTime;
	
	//////////////////不映射字段/////////////////////
	/**
	 * 1为失败，0未成功
	 */
	@Transient
	private int error = 0;
	/**
	 * 文件的地址
	 */
	@Transient
	private String url;
	/**
	 * 错误信息
	 */
	@Transient
	private String message;

	/**
	 * 如果附件是音视频，计算时长
	 */
	@Transient
	private long duration;
	///////////////////不映射字段////////////////////
	
	
	/**
	 * 附件上传后产生的文件名UUID形式。
	 */
	@Column(name = "file_name")
	private String fileName;

	/**
	 * 附件存储的相对位置。
	 */
	@Column(name = "file_path")
	private String filePath;

	/**
	 * 附件原来的名字。
	 */
	@Column(name = "org_file_name")
	private String orgFileName;

	@Column(name = "file_size")
	private int fileSize;

	/**
	 * 文件类型(文件扩展名)
	 */
	@Column(name = "file_type")
	private String fileType;

	/**
	 * 业务类型，用来区分不同的业务模块，如:班级、课程等。
	 */
	@Column(name = "business_type")
	private int businessType;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOrgFileName() {
		return orgFileName;
	}

	public void setOrgFileName(String orgFileName) {
		this.orgFileName = orgFileName;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public int getBusinessType() {
		return businessType;
	}

	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public Attachment1(){}
	
	public Attachment1(int error,String message){
		this.error=error;
		this.message=message;
	}

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "Attachment [Id=" + this.getId() + ", createTime=" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.getCreateTime()) + ", createPerson=" + this.getCreatePerson() + ", isDelete=" + this.isDelete() + ", fileName=" + fileName + ", filePath="
		        + filePath + ", orgFileName=" + orgFileName + ", fileSize=" + fileSize + ", fileType=" + fileType + ", businessType=" + businessType + "]";
	}
	
	
	
}
