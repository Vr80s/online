/**  
* <p>Title: CourseVo.java</p>  
* <p>Description: </p>  
* @author yangxuan@ixincheng.com  
* @date 2018年8月21日 
*/  
package com.xczhihui.common.support.domain;

import java.io.Serializable;
import java.util.Date;

/**
* @ClassName: CourseVo
* @Description: TODO(这里用一句话描述这个类的作用)
* @author yangxuan
* @email yangxuan@ixincheng.com
* @date 2018年8月21日
*
*/

public class CouserMessagePushVo implements Serializable{

	
	private Integer id;
	 /**
     * yangxuan 新增用户讲师id
     */
    private String userLecturerId;
    /**
     * 课程名
     */
    private String gradeName;
    
    private Date startTime;
    
    private String address;
    
    //互动id
    private Integer appointmentInfoId;
    

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserLecturerId() {
		return userLecturerId;
	}

	public void setUserLecturerId(String userLecturerId) {
		this.userLecturerId = userLecturerId;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getAppointmentInfoId() {
		return appointmentInfoId;
	}

	public void setAppointmentInfoId(Integer appointmentInfoId) {
		this.appointmentInfoId = appointmentInfoId;
	}
	
}
