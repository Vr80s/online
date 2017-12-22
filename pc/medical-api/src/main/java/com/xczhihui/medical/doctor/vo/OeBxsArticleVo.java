package com.xczhihui.medical.doctor.vo;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuxin
 * @since 2017-12-20
 */
public class OeBxsArticleVo implements Serializable {

    private static final long serialVersionUID = 1L;

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
	private String typeId;
    /**
     * 图片
     */
	private String imgPath;
    /**
     * banner图片
     */
	private String bannerPath;
	private String author;
    /**
     * 阅读量
     */
	private Integer browseSum;
    /**
     * 点赞数
     */
	private Integer praiseSum;
    /**
     * 评论数
     */
	private Integer commentSum;
    /**
     * 是否推荐，1推荐，0不推荐
     */
	private Boolean isRecommend;

	private Date createTime;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	private List<MedicalDoctorVo> medicalDoctors;

	public List<MedicalDoctorVo> getMedicalDoctors() {
		return medicalDoctors;
	}

	public void setMedicalDoctors(List<MedicalDoctorVo> medicalDoctors) {
		this.medicalDoctors = medicalDoctors;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

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

	public void setRecommend(Boolean recommend) {
		isRecommend = recommend;
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
			"}";
	}
}
