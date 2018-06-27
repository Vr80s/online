package com.xczhihui.medical.headline.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * @author 评论点赞
 */
@TableName("oe_appraise_praise")
public class OeBxsAppraisePraise extends Model<OeBxsAppraisePraise> {

    @TableId(type = IdType.AUTO)
    private int id;

    @TableField(value = "appraise_id")
    private String appraiseId;

    @TableField(value = "user_id")
    private String userId;

    private boolean deleted;

    @TableField("praise_time")
    private Date praiseTime;

    @TableField("un_praise_time")
    private Date unPraiseTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppraiseId() {
        return appraiseId;
    }

    public void setAppraiseId(String appraiseId) {
        this.appraiseId = appraiseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getPraiseTime() {
        return praiseTime;
    }

    public void setPraiseTime(Date praiseTime) {
        this.praiseTime = praiseTime;
    }

    public Date getUnPraiseTime() {
        return unPraiseTime;
    }

    public void setUnPraiseTime(Date unPraiseTime) {
        this.unPraiseTime = unPraiseTime;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
