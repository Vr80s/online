package com.xczhihui.medical.headline.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author yuxin
 * @since 2017-12-20
 */
@TableName("oe_bxs_article")
@Data
public class OeBxsArticle extends Model<OeBxsArticle> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 文章类型,外键引用
     */
    @TableField("type_id")
    private String typeId;
    /**
     * 图片
     */
    @TableField("img_path")
    private String imgPath;
    /**
     * banner图片
     */
    @TableField("banner_path")
    private String bannerPath;
    /**
     * 阅读量
     */
    @TableField("browse_sum")
    private Integer browseSum;
    /**
     * 点赞数
     */
    @TableField("praise_sum")
    private Integer praiseSum;
    /**
     * 评论数
     */
    @TableField("comment_sum")
    private Integer commentSum;

    /**
     * 是否推荐，1推荐，0不推荐
     */
    @TableField("is_recommend")
    private Boolean isRecommend;
    /**
     * banner图禁用状态，1启用，0禁用
     */
    @TableField("banner_status")
    private Integer bannerStatus;
    /**
     * 文章禁用状态，1启用，0禁用
     */
    private Integer status;
    @TableField("is_delete")
    private Boolean isDelete;
    /**
     * 用户id 关联user
     */
    @TableField("user_id")
    private String userId;
    /**
     * 排序
     */
    private Integer sort;
    @TableField("create_time")
    private Date createTime;
    /**
     * 点赞用户信息
     */
    @TableField("praise_login_names")
    private String praiseLoginNames;

    @TableField(exist = false)
    private String source;
    @TableField(exist = false)
    private String type;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public Boolean getRecommend() {
        return isRecommend;
    }

    public void setRecommend(Boolean recommend) {
        isRecommend = recommend;
    }
}
