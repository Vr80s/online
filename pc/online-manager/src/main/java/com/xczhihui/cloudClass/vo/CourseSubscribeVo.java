package com.xczhihui.cloudClass.vo;

/** 
 * ClassName: CourseSubscribeVo.java <br>
 * Description: 课程预约实体<br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年8月12日<br>
 */
public class CourseSubscribeVo{

    private Integer id;

    /**
     *课程id
     */
    private String  courseId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 手机号
     */
    private String phone;
   
    /**
     * 是否删除
     */
    private Boolean isDelete;

    /**
     * 创建时间
     */
    private Boolean Timestamp;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Boolean getTimestamp() {
		return Timestamp;
	}

	public void setTimestamp(Boolean timestamp) {
		Timestamp = timestamp;
	}

    
}
