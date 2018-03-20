package com.xczhihui.wechat.course.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * ClassName: Course.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年1月14日<br>
 */
@TableName("oe_watch_history1")
public class WatchHistory extends Model<WatchHistory> {

    private static final long serialVersionUID = 1L;

    private Long id;
    
    /**
   	 *用户	user_Id
   	 */
   	@TableField("user_Id")
    private String userId;
    /**
   	 *讲师	lecturer_Id
   	 */
   	@TableField("lecturer_Id")
    private String lecturerId;
    /**
   	 *课程	course_Id
   	 */
   	@TableField("course_Id")
    private Integer courseId;
    /**
   	 *创建时间	create_time
   	 */
   	@TableField("create_time")
    private Date createTime;
   	
   	/**
   	 * 是否删除
   	 */
   	@TableField("is_delete" )
	private  boolean isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(String lecturerId) {
        this.lecturerId = lecturerId == null ? null : lecturerId.trim();
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	@Override
	protected Serializable pkVal() {
		return null;
	}
}
