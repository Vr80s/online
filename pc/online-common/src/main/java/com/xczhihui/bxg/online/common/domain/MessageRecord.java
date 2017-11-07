package com.xczhihui.bxg.online.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * 学员消息
 * @author duanqh
 *
 */
@Entity
@Table(name = "oe_message_record")
public class MessageRecord  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 唯一标识
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 创建人ID
	 */
	@Column(name = "create_person")
	private String createPerson;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private Date createTime;


	/**
	 * 消息内容
	 */
	@Type(type="text")
	@Column(name = "context")
	private String context;

	/**
	 * 学科
	 */
	@Column(name = "subject")
	private String subject;

	/**
	 * 课程
	 */
	@Column(name = "course")
	private String course;
	
	/**
	 * 班级
	 */
	@Column(name = "grade")
	private String grade;
	
	/**
	 * 发送用户数
	 */
	@Column(name = "user_count")
	private Integer userCount;
	
	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Integer getUserCount() {
		return userCount;
	}

	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}

	

}
