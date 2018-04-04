package com.xczhihui.medical.headline.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * <p>
 * 
 * </p>
 *
 * @author yuxin
 * @since 2018-04-02
 */
@Data
@TableName("oe_bxs_appraise")
public class OeBxsAppraise extends Model<OeBxsAppraise> {

    private static final long serialVersionUID = 1L;

	private String id;
    /**
     * 文章id
     */
	@TableField("article_id")
	private Integer articleId;
    /**
     * 评论内容
     */
	private String content;
	@TableField("create_time")
	private Date createTime;
	@TableField("is_delete")
	private Boolean isDelete;
    /**
     * 评论者用户id
     */
	@TableField("user_id")
	private String userId;
    /**
     * 被回复者用户id
     */
	@TableField("target_user_id")
	private String targetUserId;

	@TableField(exist = false)
	private Boolean isMySelf;

	@TableField(exist = false)
	private String smallHeadPhoto;

	@TableField(exist = false)
	private String name;

	@TableField(exist = false)
	private String nickName;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
