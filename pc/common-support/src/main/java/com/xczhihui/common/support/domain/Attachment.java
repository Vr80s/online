package com.xczhihui.common.support.domain;

import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

/**
 * 统一附件处理的实体类
 * 
 * @author liyong
 */
@Entity
@Table(name = "attachment", uniqueConstraints = { @UniqueConstraint(columnNames = { "file_name" }) })
public class Attachment extends BasicEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3419011931048040434L;
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
	
	public Attachment(){}
	
	public Attachment(int error,String message){
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

	@Override
	public String toString() {
		return "Attachment [Id=" + this.getId() + ", createTime=" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.getCreateTime()) + ", createPerson=" + this.getCreatePerson() + ", isDelete=" + this.isDelete() + ", fileName=" + fileName + ", filePath="
		        + filePath + ", orgFileName=" + orgFileName + ", fileSize=" + fileSize + ", fileType=" + fileType + ", businessType=" + businessType + "]";
	}
	
	
	
}
