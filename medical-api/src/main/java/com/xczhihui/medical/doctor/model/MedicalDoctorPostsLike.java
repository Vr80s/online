package com.xczhihui.medical.doctor.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * Description：医师动态点赞表
 * creed: Talk is cheap,show me the code
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/6/21 12:13
 **/
@TableName("medical_doctor_posts_like")
public class MedicalDoctorPostsLike extends Model<MedicalDoctorPostsLike> {

    private static final long serialVersionUID = 1L;

    /**
     * 医师动态点赞表
     */
	private Integer id;
    /**
     * 动态的ID
     */
	@TableField("posts_id")
	private Integer postsId;
    /**
     * 用户id
     */
	@TableField("user_id")
	private String userId;
	/**
	 * 1已删除0未删除
	 */
	@TableField("deleted")
	private Boolean deleted;
	/**
	 * 创建时间
	 */
	@TableField("create_time")
	private Date createTime;
	/**
	 * 用户id
	 */
	@TableField(exist = false)
	private String userName;
	/**
	 * 是否点赞
	 */
	@TableField(exist = false)
	private Boolean isPraise=false;

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

	public Integer getPostsId() {
		return postsId;
	}

	public void setPostsId(Integer postsId) {
		this.postsId = postsId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Boolean getPraise() {
		return isPraise;
	}

	public void setPraise(Boolean praise) {
		isPraise = praise;
	}
}
