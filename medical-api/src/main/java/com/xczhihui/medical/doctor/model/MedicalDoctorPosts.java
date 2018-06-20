package com.xczhihui.medical.doctor.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * Description：医师动态表
 * creed: Talk is cheap,show me the code
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/6/20 11:48
 **/
@TableName("medical_doctor_posts")
public class MedicalDoctorPosts extends Model<MedicalDoctorPosts> {

    private static final long serialVersionUID = 1L;

    /**
     * 医师动态表
     */
	private Integer id;
    /**
     * 动态的文字内容
     */
	@TableField("content")
	private String content;
    /**
     * 动态类别：1.普通动态2.图片动态3.视频动态4.文章动态5.课程动态
     */
	@TableField("type")
	private Integer type;
    /**
     * 动态包含图片（分隔符分割）
     */
	@TableField("pictures")
	private String pictures;
	/**
	 * 动态包含的视频资源（cc视频资源id）
	 */
	@TableField("video")
	private String video;
	/**
	 * 视频封面
	 */
	@TableField("cover_img")
	private String coverImg;
	/**
	 * 视频标题
	 */
	@TableField("title")
	private String title;
	/**
	 * 1已删除0未删除
	 */
	@TableField("deleted")
	private Boolean deleted;
	/**
	 * 状态
	 */
	@TableField("status")
	private Boolean status;
	/**
	 * 创建时间
	 */
	@TableField("create_time")
	private Date createTime;
	/**
	 * 创建人id
	 */
	@TableField("doctor_id")
	private String doctorId;
	/**
	 * 更新时间
	 */
	@TableField("update_time")
	private Date updateTime;
	/**
	 * 课程id
	 */
	@TableField("course_id")
	private Integer courseId;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	@Override
	public String toString() {
		return "MedicalDoctorPosts{" +
				"id=" + id +
				", content='" + content + '\'' +
				", type=" + type +
				", pictures='" + pictures + '\'' +
				", video='" + video + '\'' +
				", coverImg='" + coverImg + '\'' +
				", title='" + title + '\'' +
				", deleted=" + deleted +
				", status=" + status +
				", createTime=" + createTime +
				", doctorId='" + doctorId + '\'' +
				", updateTime=" + updateTime +
				", courseId=" + courseId +
				'}';
	}
}
