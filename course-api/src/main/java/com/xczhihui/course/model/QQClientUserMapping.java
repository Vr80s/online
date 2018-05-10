package com.xczhihui.course.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName("qq_client_user_mapping")
public class QQClientUserMapping extends  Model<QQClientUserMapping>{
   
	private static final long serialVersionUID = 1L;
	
	@TableField("id")
	private String id;

	@TableField("user_id")
    private String userId;
	
	@TableField("open_id")
    private String openId;

	@TableField("nickname")
    private String nickname;

	@TableField("figureurl")
    private String figureurl;

	@TableField("figureurl_1")
    private String figureurl1;

	@TableField("figureurl_2")
    private String figureurl2;

	@TableField("gender")
    private String gender;

	@TableField("vip")
    private Boolean vip;

	@TableField("level")
    private Integer level;

	@TableField("is_yellow_year_vip")
    private Boolean yellowYearVip;

	@TableField("remark")
    private String remark;

	@TableField("create_time")
    private Date createTime;
	
	@TableField("union_id")
    private String unionId;

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


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }


    public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}


    public Boolean getVip() {
		return vip;
	}

	public void setVip(Boolean vip) {
		this.vip = vip;
	}


	public Boolean getYellowYearVip() {
		return yellowYearVip;
	}

	public void setYellowYearVip(Boolean yellowYearVip) {
		this.yellowYearVip = yellowYearVip;
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
	
	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	
	
	@Override
	public String toString() {
		return "QQClientUserMapping [id=" + id + ", userId=" + userId
				+ ", openId=" + openId + ", nickname=" + nickname
				+ ", figureurl=" + figureurl + ", figureurl1=" + figureurl1
				+ ", figureurl2=" + figureurl2 + ", gender=" + gender
				+ ", vip=" + vip + ", level=" + level + ", yellowYearVip="
				+ yellowYearVip + ", remark=" + remark + ", createTime="
				+ createTime + ", unionId=" + unionId + "]";
	}

	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return this.id;
	}
}