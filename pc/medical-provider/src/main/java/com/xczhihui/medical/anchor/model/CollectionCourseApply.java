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
@TableName("collection_course_apply")
public class CollectionCourseApply extends Model<CollectionCourseApply> {

    private static final long serialVersionUID = 1L;

    /**
     * 申请合辑-课程关系表
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 合辑id
     */
	@TableField("collection_apply_id")
	private Integer collectionApplyId;
    /**
     * 课程id
     */
	@TableField("course_apply_id")
	private Integer courseApplyId;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCollectionApplyId() {
		return collectionApplyId;
	}

	public void setCollectionApplyId(Integer collectionApplyId) {
		this.collectionApplyId = collectionApplyId;
	}

	public Integer getCourseApplyId() {
		return courseApplyId;
	}

	public void setCourseApplyId(Integer courseApplyId) {
		this.courseApplyId = courseApplyId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "CollectionCourseApply{" +
			", id=" + id +
			", collectionApplyId=" + collectionApplyId +
			", courseApplyId=" + courseApplyId +
			", createTime=" + createTime +
			"}";
	}
}
