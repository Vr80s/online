package com.xczhihui.wechat.course.model;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuxin
 * @since 2018-04-13
 */
@TableName("oe_criticize")
public class Criticize extends Model<Criticize> {

    private static final long serialVersionUID = 1L;

	private String id;
    /**
     * 评论人id
     */
	@TableField("create_person")
	private String createPerson;
	@TableField("create_time")
	private Date createTime;
	@TableField("is_delete")
	private Boolean isDelete;
    /**
     * 评价内容
     */
	private String content;
    /**
     * 被评论人用户ID
     */
	@TableField("user_id")
	private String userId;
    /**
     * 总体印像--星级
     */
	@TableField("star_level")
	private Float starLevel;
    /**
     * 点赞数
     */
	@TableField("praise_sum")
	private Integer praiseSum;
    /**
     * 点赞的人的登录名，多个用英文逗号分隔
     */
	@TableField("praise_login_names")
	private String praiseLoginNames;
    /**
     * 课程id，关联oe_course中的id
     */
	@TableField("course_id")
	private Integer courseId;
    /**
     * 节目内容-星级
     */
	@TableField("content_level")
	private Float contentLevel;
    /**
     * 主播演绎级别
     */
	@TableField("deductive_level")
	private Float deductiveLevel;
    /**
     * 1,2,3    1.很赞 2 干货很多 3超值推荐 4喜欢 5买对了
     */
	@TableField("criticize_lable")
	private String criticizeLable;
    /**
     * 总体印像
     */
	@TableField("overall_level")
	private Float overallLevel;
    /**
     * 是否购买 0 ，未购买 1 购买
     */
	@TableField("is_buy")
	private Boolean isBuy;
    /**
     * 是否点赞 0 未点赞 1 点赞
     */
	@TableField(exist = false)
	private Boolean isPraise;

	@TableField(exist = false)
	private OnlineUser onlineUser;
	@TableField(exist = false)
	private List<Reply> reply;

	@TableField(exist = false)
	private String name;
	
	@TableField(exist = false)
	private String smallHeadPhoto;
	
	public String getSmallHeadPhoto() {
		return smallHeadPhoto;
	}

	public void setSmallHeadPhoto(String smallHeadPhoto) {
		if(this.onlineUser==null){
			this.onlineUser = new OnlineUser();
		}
		this.onlineUser.setSmallHeadPhoto(smallHeadPhoto);
		this.smallHeadPhoto = smallHeadPhoto;
	}

	@TableField(exist = false)
	private String loginName;
	

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		if(this.onlineUser==null){
			this.onlineUser = new OnlineUser();
		}
		this.onlineUser.setLoginName(loginName);
		this.loginName = loginName;
	}

	public OnlineUser getOnlineUser() {
		return onlineUser;
	}

	public void setOnlineUser(OnlineUser onlineUser) {
		this.onlineUser = onlineUser;
	}

	public List<Reply> getReply() {
		return reply;
	}

	public void setReply(List<Reply> reply) {
		this.reply = reply;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(this.onlineUser==null){
			this.onlineUser = new OnlineUser();
		}
		this.onlineUser.setId(this.getCreatePerson());
		this.onlineUser.setName(name);
		this.name = name;
	}

	public Boolean getBuy() {
		return isBuy;
	}

	public void setBuy(Boolean buy) {
		isBuy = buy;
	}

	public Boolean getPraise() {
		return isPraise;
	}

	public void setPraise(Boolean praise) {
		isPraise = praise;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		if(this.onlineUser==null){
			this.onlineUser = new OnlineUser();
		}
		this.onlineUser.setId(createPerson);
		this.createPerson = createPerson;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Boolean getDelete() {
		return isDelete;
	}

	public void setDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
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

	public String getCriticizeLable() {
		return criticizeLable;
	}

	public void setCriticizeLable(String criticizeLable) {
		this.criticizeLable = criticizeLable;
	}

	public Float getOverallLevel() {
		return overallLevel;
	}

	public void setOverallLevel(Float overallLevel) {
		this.overallLevel = overallLevel;
	}

	public Boolean getIsBuy() {
		return isBuy;
	}

	public void setIsBuy(Boolean isBuy) {
		this.isBuy = isBuy;
	}

	public Boolean getIsPraise() {
		return isPraise;
	}

	public void setIsPraise(Boolean isPraise) {
		this.isPraise = isPraise;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Criticize{" +
				"id='" + id + '\'' +
				", createPerson='" + createPerson + '\'' +
				", createTime=" + createTime +
				", isDelete=" + isDelete +
				", content='" + content + '\'' +
				", userId='" + userId + '\'' +
				", starLevel=" + starLevel +
				", praiseSum=" + praiseSum +
				", praiseLoginNames='" + praiseLoginNames + '\'' +
				", courseId=" + courseId +
				", contentLevel=" + contentLevel +
				", deductiveLevel=" + deductiveLevel +
				", criticizeLable='" + criticizeLable + '\'' +
				", overallLevel=" + overallLevel +
				", isBuy=" + isBuy +
				", isPraise=" + isPraise +
				", onlineUser=" + onlineUser +
				", reply=" + reply +
				", name='" + name + '\'' +
				", smallHeadPhoto='" + smallHeadPhoto + '\'' +
				'}';
	}
}
