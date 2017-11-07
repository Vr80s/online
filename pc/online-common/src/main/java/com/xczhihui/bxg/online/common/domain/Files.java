package com.xczhihui.bxg.online.common.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.xczhihui.bxg.common.support.domain.BasicEntity;
/**
 *  文件实体类
 * @author yxd
 */
@Entity
@Table(name = "oe_files")
public class Files extends BasicEntity implements Serializable{
	
	@Column(name = "chapter_id")//章节表ID
    private String chapterId;
	@Column(name = "name")//名称
    private String name;
	@Column(name = "version")//版本
    private String version;
	@Column(name = "suffix")//后缀
    private String suffix;
	@Column(name = "type")//文件类型
    private Integer type;
	@Column(name = "file_url")//文件路径
    private String fileUrl;
	@Column(name = "sort")//排序
    private Integer sort;
	@Column(name = "status")//状态
    private Integer status;
	@Column(name = "course_id")//课程id
    private Integer courseId;
	@Column(name = "description")//描述
    private String  description;
	
	@Transient
	private String fileUrlOld;
	@Transient
	private String chapterName;
 
    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId == null ? null : chapterId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix == null ? null : suffix.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl == null ? null : fileUrl.trim();
    }
   
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId ;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFileUrlOld() {
		return fileUrlOld;
	}

	public void setFileUrlOld(String fileUrlOld) {
		this.fileUrlOld = fileUrlOld;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}
    
    
}