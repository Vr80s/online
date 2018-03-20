package com.xczhihui.medical.anchor.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuxin
 * @since 2018-01-19
 */
@TableName("course_apply_resource")
public class CourseApplyResource extends Model<CourseApplyResource> {

    private static final long serialVersionUID = 1L;

    /**
     * 课程申请资源表
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 标题
     */
	private String title;
    /**
     * 资源id
     */
	private String resource;
    /**
     * 作者id
     */
	@TableField("user_id")
	private String userId;
    /**
     * 多媒体类型:1视频2音频
     */
	@TableField("multimedia_type")
	private Integer multimediaType;
    /**
     * 是否删除：0 未删除 1 删除
     */
	@TableField("is_delete")
	private Boolean isDelete;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
    /**
     * 更新时间
     */
	@TableField("update_time")
	private Date updateTime;

	private String length;

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getMultimediaType() {
		return multimediaType;
	}

	public void setMultimediaType(Integer multimediaType) {
		this.multimediaType = multimediaType;
	}

	public Boolean getDelete() {
		return isDelete;
	}

	public void setDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "CourseApplyResource{" +
			", id=" + id +
			", title=" + title +
			", resource=" + resource +
			", userId=" + userId +
			", multimediaType=" + multimediaType +
			", isDelete=" + isDelete +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			"}";
	}
}
