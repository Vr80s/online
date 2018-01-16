package com.xczhihui.bxg.online.common.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.xczhihui.bxg.common.support.domain.BasicEntity;

@Entity
@Table(name = "oe_criticize")
public class Criticize extends BasicEntity  {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6921285561593657883L;
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "sort")
	private Integer sort;
    
	@Column(name = "content")
	private String content;
	
//	@Column(name = "user_id")
//    private String userId;
	
	@Column(name = "chapter_id")
    private String chapterId;
	
	@Column(name = "video_id")
    private String videoId;
	
	@Column(name = "star_level")
    private Float starLevel;
	
	@Column(name = "praise_sum")
    private Integer praiseSum;
    
	@Column(name = "praise_login_names")
	private String praiseLoginNames;
	
	@Column(name = "response")
	private String response;
	
	@Column(name = "response_time")
	private Date responseTime;
	
	
	/**
	 * 杨宣新增   
	 */
	@OneToOne(targetEntity=OnlineUser.class,fetch = FetchType.EAGER)              //指定一对多关系
    @Cascade(value={CascadeType.ALL})        									 //设定级联关系
    @JoinColumn(name="user_id",unique = true)   
	private OnlineUser onlineUser;
	
	@OneToMany(targetEntity=Reply.class,fetch = FetchType.EAGER)              //指定一对多关系
    @Cascade(value={CascadeType.ALL})        								 //设定级联关系
    @JoinColumn(name="criticize_id")                  //指定与本类主键所对应的外表的外键
    private List<Reply> reply = new ArrayList<Reply>();
	
	//节目内容
	@Column(name = "content_level")
    private Float contentLevel;
	
	//主播演绎
	@Column(name = "deductive_level")
    private Float deductiveLevel;
	
	//评价标签  1.很赞 2 干货很多 3超值推荐 4喜欢 5买对了
	@Column(name = "criticize_lable")
    private Integer criticizeLable;

	
	
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
//	public String getUserId() {
//		return userId;
//	}
//	public void setUserId(String userId) {
//		this.userId = userId;
//	}
	public String getChapterId() {
		return chapterId;
	}
	public void setChapterId(String chapterId) {
		this.chapterId = chapterId;
	}
	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	public Float getStarLevel() {
		return starLevel;
	}
	public void setStarLevel(Float starLevel) {
		this.starLevel = starLevel;
	}
	public Integer getPraiseSum() {
		return praiseSum;
	}
	public void setPraiseSum(Integer praiseSum) {
		this.praiseSum = praiseSum;
	}
	public String getPraiseLoginNames() {
		return praiseLoginNames;
	}
	public void setPraiseLoginNames(String praiseLoginNames) {
		this.praiseLoginNames = praiseLoginNames;
	}
	public Date getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}
	public List<Reply> getReply() {
		return reply;
	}
	public void setReply(List<Reply> reply) {
		this.reply = reply;
	}
	public OnlineUser getOnlineUser() {
		return onlineUser;
	}
	public void setOnlineUser(OnlineUser onlineUser) {
		this.onlineUser = onlineUser;
	}
	public Float getContentLevel() {
		return contentLevel;
	}
	public void setContentLevel(Float contentLevel) {
		this.contentLevel = contentLevel;
	}
	public Float getDeductiveLevel() {
		return deductiveLevel;
	}
	public void setDeductiveLevel(Float deductiveLevel) {
		this.deductiveLevel = deductiveLevel;
	}
	public Integer getCriticizeLable() {
		return criticizeLable;
	}
	public void setCriticizeLable(Integer criticizeLable) {
		this.criticizeLable = criticizeLable;
	}
}
