package com.xczhihui.bxg.online.common.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.xczhihui.bxg.common.support.domain.BasicEntity;


@Entity
@Table(name = "oe_notes")
public class Notes extends BasicEntity  {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "sort")
    private Integer sort;
	
	@Column(name = "course_id")
    private Integer courseId;
	
	@Column(name = "video_id")
    private String videoId;
	
	@Column(name = "user_id")
    private String userId;
	
	@Column(name = "grade_id")
    private Integer gradeId;
	
	@Column(name = "content")
    private String content;
	
	@Column(name = "praise_sum")
    private Integer praiseSum;
	
	@Column(name = "is_share")
    private Boolean isShare;
	
	@Column(name = "comment_sum")
    private Integer commentSum;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getGradeId() {
		return gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getPraiseSum() {
		return praiseSum;
	}

	public void setPraiseSum(Integer praiseSum) {
		this.praiseSum = praiseSum;
	}

	public Boolean getIsShare() {
		return isShare;
	}

	public void setIsShare(Boolean isShare) {
		this.isShare = isShare;
	}

	public Integer getCommentSum() {
		return commentSum;
	}

	public void setCommentSum(Integer commentSum) {
		this.commentSum = commentSum;
	}

}
