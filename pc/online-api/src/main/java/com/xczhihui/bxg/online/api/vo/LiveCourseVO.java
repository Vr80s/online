package com.xczhihui.bxg.online.api.vo;

import java.io.Serializable;

/** 
 * ClassName: ReceivedGift.java <br>
 * Description: 直播课程<br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年9月16日<br>
 */
public class LiveCourseVO implements Serializable{

	private String id;
	private String courseName;
	private String startTime;
	private String endTime;
	private String enrollmentCount;
	private String totalAmount;
	private String price;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getEnrollmentCount() {
		return enrollmentCount;
	}

	public void setEnrollmentCount(String enrollmentCount) {
		this.enrollmentCount = enrollmentCount;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
}
