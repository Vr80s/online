package com.xczhihui.medical.doctor.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.xczhihui.medical.common.bean.PictureSpecification;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Description：医师动态表
 * creed: Talk is cheap,show me the code
 *
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/6/20 11:48
 **/
@TableName("medical_doctor_posts")
public class MedicalDoctorPosts extends Model<MedicalDoctorPosts> {

    private static final long serialVersionUID = 1L;

    /**
     * 医师动态表
     */
    private Integer id;
    /**
     * 动态的文字内容
     */
    @TableField("content")
    private String content;
    /**
     * 动态类别：1.普通动态2.图片动态3.视频动态4.文章动态5.课程动态
     */
    @TableField("type")
    private Integer type;
    /**
     * 动态包含图片（分隔符分割）
     */
    @TableField("pictures")
    private String pictures;
    /**
     * 动态包含的视频资源（cc视频资源id）
     */
    @TableField("video")
    private String video;
    /**
     * 视频封面
     */
    @TableField("cover_img")
    private String coverImg;
    /**
     * 视频标题
     */
    @TableField("title")
    private String title;
    /**
     * 1已删除0未删除
     */
    @TableField("deleted")
    private Boolean deleted;
    /**
     * 状态
     */
    @TableField("status")
    private Boolean status;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 创建人id
     */
    @TableField("doctor_id")
    private String doctorId;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 课程id
     */
    @TableField("course_id")
    private Integer courseId;
    /**
     * 文章id
     */
    @TableField("article_id")
    private Integer articleId;
    /**
     * 是否置顶1:置顶0:取消置顶
     */
    @TableField("stick")
    private Boolean stick;
    
    /**
     * 商品id
     */
    @TableField("product_id")
    private Long productId;
    /**
     * 医师推荐星级
     */
    @TableField("level")
    private Integer level;
    
    
    /**
     * 现价
     */
    @TableField(exist = false)
    private double currentPrice;
    /**
     * 课程名
     */
    @TableField(exist = false)
    private String gradeName;
    /**
     * 课程小图
     */
    @TableField(exist = false)
    private String smallImgPath;
    /**
     * 0 不是专辑 1 是专辑
     */
    @TableField(exist = false)
    private Integer collection;
    /**
     * 上课时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @TableField(exist = false)
    private Date startTime;
    /**
     * 线下课地址
     */
    @TableField(exist = false)
    private String courseAddress;
    /**
     * 课程类型 1.直播2.点播3.线下课
     */
    @TableField(exist = false)
    private Integer courseType;
    /**
     * 多媒体类型1视频2音频
     */
    @TableField(exist = false)
    private Integer multimediaType;
    /**
     * 评论
     */
    @TableField(exist = false)
    private List<MedicalDoctorPostsComment> doctorPostsCommentList;
    /**
     * 点赞
     */
    @TableField(exist = false)
    private List<MedicalDoctorPostsLike> doctorPostsLikeList;
    /**
     * 是否点赞
     */
    @TableField(exist = false)
    private Boolean isPraise = false;
    @TableField(exist = false)
    private Boolean teaching = false;

    /**
     * 文章内容
     */
    @TableField(exist = false)
    private String articleContent;
    /**
     * 文章类型
     */
    @TableField(exist = false)
    private Integer typeId;
    /**
     * 文章标题
     */
    @TableField(exist = false)
    private String articleTitle;
    /**
     * 文章封面
     */
    @TableField(exist = false)
    private String articleImgPath;
    /**
     * 时间显示
     */
    @TableField(exist = false)
    private String dateStr;
    /**
     * 课程是否上架
     */
    @TableField(exist = false)
    private Boolean courseStatus = false;
    /**
     * 图片
     */
    @TableField(exist = false)
    private List<PictureSpecification> imgStr;
    /**
     * 文章是否上架
     */
    @TableField(exist = false)
    private Boolean articleStatus = false;
    /**
     * 著作id
     */
    @TableField(exist = false)
    private String writingsId;
    /**
     * 诊疗id
     *
     */
    @TableField(exist = false)
    private Integer treatmentId;
    /**
     * 诊疗弟子
     */
    @TableField(exist = false)
    private String pupilName;
    /**
     * 审核状态：1未审核 2通过 3拒绝  3 取消
     */
    @TableField(exist = false)
    private Integer treatmentStatus;
    /**
     * 商品价格
     */
    @TableField(exist = false)
    private BigDecimal productPrice;
    /**
     * 商品标题
     */
    @TableField(exist = false)
    private String productTitle;
    /**
     * 商品图片
     */
    @TableField(exist = false)
    private String productImages;

    /**
     * 商品是否上架
     */
    @TableField(exist = false)
    private Boolean productIsMarketable;
    /**
     * 详情地址
     */
    @TableField(exist = false)
    private String detailsUrl;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public Boolean getProductIsMarketable() {
        return productIsMarketable;
    }

    public void setProductIsMarketable(Boolean productIsMarketable) {
        this.productIsMarketable = productIsMarketable;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getTeaching() {
        return teaching;
    }

    public void setTeaching(Boolean teaching) {
        this.teaching = teaching;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Boolean getStick() {
        return stick;
    }

    public void setStick(Boolean stick) {
        this.stick = stick;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getSmallImgPath() {
        return smallImgPath;
    }

    public void setSmallImgPath(String smallImgPath) {
        this.smallImgPath = smallImgPath;
    }

    public Integer getCollection() {
        return collection;
    }

    public void setCollection(Integer collection) {
        this.collection = collection;
    }

    public Integer getCourseType() {
        return courseType;
    }

    public void setCourseType(Integer courseType) {
        this.courseType = courseType;
    }

    public List<MedicalDoctorPostsComment> getDoctorPostsCommentList() {
        return doctorPostsCommentList;
    }

    public void setDoctorPostsCommentList(List<MedicalDoctorPostsComment> doctorPostsCommentList) {
        this.doctorPostsCommentList = doctorPostsCommentList;
    }

    public List<MedicalDoctorPostsLike> getDoctorPostsLikeList() {
        return doctorPostsLikeList;
    }

    public void setDoctorPostsLikeList(List<MedicalDoctorPostsLike> doctorPostsLikeList) {
        this.doctorPostsLikeList = doctorPostsLikeList;
    }

    public Boolean getPraise() {
        return isPraise;
    }

    public void setPraise(Boolean praise) {
        isPraise = praise;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleImgPath() {
        return articleImgPath;
    }

    public void setArticleImgPath(String articleImgPath) {
        this.articleImgPath = articleImgPath;
    }

    public String getCourseAddress() {
        return courseAddress;
    }

    public void setCourseAddress(String courseAddress) {
        this.courseAddress = courseAddress;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public Boolean getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(Boolean courseStatus) {
        this.courseStatus = courseStatus;
    }

    public List<PictureSpecification> getImgStr() {
        return imgStr;
    }

    public void setImgStr(List<PictureSpecification> imgStr) {
        this.imgStr = imgStr;
    }

    public Boolean getArticleStatus() {
        return articleStatus;
    }

    public void setArticleStatus(Boolean articleStatus) {
        this.articleStatus = articleStatus;
    }

    public String getWritingsId() {
        return writingsId;
    }

    public void setWritingsId(String writingsId) {
        this.writingsId = writingsId;
    }

    public Integer getMultimediaType() {
        return multimediaType;
    }

    public void setMultimediaType(Integer multimediaType) {
        this.multimediaType = multimediaType;
    }

    public String getPupilName() {
        return pupilName;
    }

    public void setPupilName(String pupilName) {
        this.pupilName = pupilName;
    }

    public Integer getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(Integer treatmentId) {
        this.treatmentId = treatmentId;
    }

    public Integer getTreatmentStatus() {
        return treatmentStatus;
    }

    public void setTreatmentStatus(Integer treatmentStatus) {
        this.treatmentStatus = treatmentStatus;
    }
    

    public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductImages() {
        return productImages;
    }

    public void setProductImages(String productImages) {
        this.productImages = productImages;
    }

    public String getDetailsUrl() {
        return detailsUrl;
    }

    public void setDetailsUrl(String detailsUrl) {
        this.detailsUrl = detailsUrl;
    }

    @Override
    public String toString() {
        return "MedicalDoctorPosts{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", pictures='" + pictures + '\'' +
                ", video='" + video + '\'' +
                ", coverImg='" + coverImg + '\'' +
                ", title='" + title + '\'' +
                ", deleted=" + deleted +
                ", status=" + status +
                ", createTime=" + createTime +
                ", doctorId='" + doctorId + '\'' +
                ", updateTime=" + updateTime +
                ", courseId=" + courseId +
                ", articleId=" + articleId +
                ", stick=" + stick +
                ", currentPrice=" + currentPrice +
                ", gradeName='" + gradeName + '\'' +
                ", smallImgPath='" + smallImgPath + '\'' +
                ", collection=" + collection +
                ", startTime=" + startTime +
                ", courseAddress='" + courseAddress + '\'' +
                ", courseType=" + courseType +
                ", multimediaType=" + multimediaType +
                ", doctorPostsCommentList=" + doctorPostsCommentList +
                ", doctorPostsLikeList=" + doctorPostsLikeList +
                ", isPraise=" + isPraise +
                ", teaching=" + teaching +
                ", articleContent='" + articleContent + '\'' +
                ", typeId=" + typeId +
                ", articleTitle='" + articleTitle + '\'' +
                ", articleImgPath='" + articleImgPath + '\'' +
                ", dateStr='" + dateStr + '\'' +
                ", courseStatus=" + courseStatus +
                ", imgStr=" + imgStr +
                ", articleStatus=" + articleStatus +
                ", writingsId='" + writingsId + '\'' +
                '}';
    }
}
