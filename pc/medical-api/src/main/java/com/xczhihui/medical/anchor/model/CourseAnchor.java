package com.xczhihui.medical.anchor.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 主播信息表
 * @author zhuwenbao
 */
@Data
@TableName("course_anchor")
public class CourseAnchor extends Model<CourseApplyInfo> {

    private Integer id;

    @TableField("user_id")
    private String userId;

    /**
     * 致辞视频
     */
    private String video;

    private Integer type;

    @TableLogic
    private Boolean deleted;

    private Boolean status;

    @TableField("create_time")
    private Date createTime;

    @TableField("create_person")
    private String createPerson;

    @TableField("update_time")
    private Date updateTime;

    @TableField("update_person")
    private String updatePerson;

    private String version;

    private String remark;

    @TableField("vod_divide")
    private BigDecimal vodDivide;

    @TableField("live_divide")
    private BigDecimal liveDivide;

    @TableField("offline_divide")
    private BigDecimal offlineDivide;

    @TableField("gift_divide")
    private BigDecimal giftDivide;

    private String name;

    @TableField("profile_photo")
    private String profilePhoto;

    private String detail;

    @TableField("resource_id")
    private Integer resourceId;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
