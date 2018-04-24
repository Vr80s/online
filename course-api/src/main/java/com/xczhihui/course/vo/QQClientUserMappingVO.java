package com.xczhihui.course.vo;

import java.io.Serializable;
import java.util.Date;

public class QQClientUserMappingVO implements Serializable{
    private String id;

    private String userId;
    
    private String openId;
    

    private String nickname;

    private String figureurl;

    private String figureurl1;

    private String figureurl2;

    private String figureurlQq1;

    private String figureurlQq2;

    private String gender;

    private Integer vip;

    private Date level;

    private Integer isYellowYearVip;

    private String remark;

    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getFigureurl() {
        return figureurl;
    }

    public void setFigureurl(String figureurl) {
        this.figureurl = figureurl == null ? null : figureurl.trim();
    }

    public String getFigureurl1() {
        return figureurl1;
    }

    public void setFigureurl1(String figureurl1) {
        this.figureurl1 = figureurl1 == null ? null : figureurl1.trim();
    }

    public String getFigureurl2() {
        return figureurl2;
    }

    public void setFigureurl2(String figureurl2) {
        this.figureurl2 = figureurl2 == null ? null : figureurl2.trim();
    }

    public String getFigureurlQq1() {
        return figureurlQq1;
    }

    public void setFigureurlQq1(String figureurlQq1) {
        this.figureurlQq1 = figureurlQq1 == null ? null : figureurlQq1.trim();
    }

    public String getFigureurlQq2() {
        return figureurlQq2;
    }

    public void setFigureurlQq2(String figureurlQq2) {
        this.figureurlQq2 = figureurlQq2 == null ? null : figureurlQq2.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public Integer getVip() {
        return vip;
    }

    public void setVip(Integer vip) {
        this.vip = vip;
    }

    public Date getLevel() {
        return level;
    }

    public void setLevel(Date level) {
        this.level = level;
    }

    public Integer getIsYellowYearVip() {
        return isYellowYearVip;
    }

    public void setIsYellowYearVip(Integer isYellowYearVip) {
        this.isYellowYearVip = isYellowYearVip;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
    
}