package com.xczhihui.bxg.online.common.domain;
// default package

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.GenerationType.TABLE;

/**
 */
@Entity
@Table(name = "oe_focus")
public class Focus implements java.io.Serializable {

	// Fields

	private String id;
	private String lecturerId;
	private String userId;
	private String userName;
	private String userHeadImg;
	private Integer courseId;
	private String lecturerName;
	private String lecturerHeadImg;
	private Integer roomNumber;


	private String fansCount;       //粉丝数


	private Integer isFocus;	    //是否关注  0：未关注  1:已关注

	// Constructors

	/** default constructor */
	public Focus() {
	}

	/** full constructor */
	public Focus(String lecturerId, String userId, String userName, String userHeadImg, Integer courseId,
			String lecturerName, String lecturerHeadImg, Integer roomNumber) {
		this.lecturerId = lecturerId;
		this.userId = userId;
		this.userName = userName;
		this.userHeadImg = userHeadImg;
		this.courseId = courseId;
		this.lecturerName = lecturerName;
		this.lecturerHeadImg = lecturerHeadImg;
		this.roomNumber = roomNumber;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "lecturer_id", length = 32)

	public String getLecturerId() {
		return this.lecturerId;
	}

	public void setLecturerId(String lecturerId) {
		this.lecturerId = lecturerId;
	}

	@Column(name = "user_id", length = 32)

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "user_name")

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "user_head_img")

	public String getUserHeadImg() {
		return this.userHeadImg;
	}

	public void setUserHeadImg(String userHeadImg) {
		this.userHeadImg = userHeadImg;
	}

	@Column(name = "course_id")

	public Integer getCourseId() {
		return this.courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	@Column(name = "lecturer_name")

	public String getLecturerName() {
		return this.lecturerName;
	}

	public void setLecturerName(String lecturerName) {
		this.lecturerName = lecturerName;
	}

	@Column(name = "lecturer_head_img")

	public String getLecturerHeadImg() {
		return this.lecturerHeadImg;
	}

	public void setLecturerHeadImg(String lecturerHeadImg) {
		this.lecturerHeadImg = lecturerHeadImg;
	}

	@Column(name = "room_number")

	public Integer getRoomNumber() {
		return this.roomNumber;
	}

	public void setRoomNumber(Integer roomNumber) {
		this.roomNumber = roomNumber;
	}

	@Transient
	@Column(insertable = false,updatable = false)
	public String getFansCount() {
		return fansCount;
	}

	public void setFansCount(String fansCount) {
		this.fansCount = fansCount;
	}

	@Column(insertable = false,updatable = false)
	@Transient
	public Integer getIsFocus() {
		return isFocus;
	}

	public void setIsFocus(Integer isFocus) {
		this.isFocus = isFocus;
	}
}