package com.xczhihui.medical.headline.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuxin
 * @since 2017-12-20
 */
@TableName("oe_bxs_article")
public class OeBxsArticle extends Model<OeBxsArticle> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
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


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getBannerPath() {
		return bannerPath;
	}

	public void setBannerPath(String bannerPath) {
		this.bannerPath = bannerPath;
	}

	public Integer getBrowseSum() {
		return browseSum;
	}

	public void setBrowseSum(Integer browseSum) {
		this.browseSum = browseSum;
	}

	public Integer getPraiseSum() {
		return praiseSum;
	}

	public void setPraiseSum(Integer praiseSum) {
		this.praiseSum = praiseSum;
	}

	public Integer getCommentSum() {
		return commentSum;
	}

	public void setCommentSum(Integer commentSum) {
		this.commentSum = commentSum;
	}

	public Boolean getRecommend() {
		return isRecommend;
	}

	public void setRecommend(Boolean isRecommend) {
		this.isRecommend = isRecommend;
	}

	public Integer getBannerStatus() {
		return bannerStatus;
	}

	public void setBannerStatus(Integer bannerStatus) {
		this.bannerStatus = bannerStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Boolean getDelete() {
		return isDelete;
	}

	public void setDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPraiseLoginNames() {
		return praiseLoginNames;
	}

	public void setPraiseLoginNames(String praiseLoginNames) {
		this.praiseLoginNames = praiseLoginNames;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "OeBxsArticle{" +
			", id=" + id +
			", title=" + title +
			", content=" + content +
			", typeId=" + typeId +
			", imgPath=" + imgPath +
			", bannerPath=" + bannerPath +
			", browseSum=" + browseSum +
			", praiseSum=" + praiseSum +
			", commentSum=" + commentSum +
			", isRecommend=" + isRecommend +
			", bannerStatus=" + bannerStatus +
			", status=" + status +
			", isDelete=" + isDelete +
			", userId=" + userId +
			", sort=" + sort +
			", createTime=" + createTime +
			", praiseLoginNames=" + praiseLoginNames +
			"}";
	}
}
