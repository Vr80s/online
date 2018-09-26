package net.shopxx.merge.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
* @ClassName: ReviewVO
* @Description: 商品评论
* @author yangxuan
* @email yangxuan@ixincheng.com
* @date 2018年9月17日
*
 */
public class ReviewVO implements Serializable {
	
    private Long id;

    private Date createdDate;

    private Date lastmodifieddate;

    private Long version;

    private String content;

    private String ip;

    private Boolean isshow;

    private Integer score;

    private String specifications;

    private Long forreviewId;

    private Long memberId;

    private Long productId;

    private Long storeId;
    
    private UsersVO user;
    
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastmodifieddate() {
        return lastmodifieddate;
    }

    public void setLastmodifieddate(Date lastmodifieddate) {
        this.lastmodifieddate = lastmodifieddate;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Boolean getIsshow() {
        return isshow;
    }

    public void setIsshow(Boolean isshow) {
        this.isshow = isshow;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications == null ? null : specifications.trim();
    }

    public Long getForreviewId() {
        return forreviewId;
    }

    public void setForreviewId(Long forreviewId) {
        this.forreviewId = forreviewId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

	public UsersVO getUser() {
		return user;
	}

	public void setUser(UsersVO user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "ReviewVO [id=" + id + ", createdDate=" + createdDate + ", lastmodifieddate=" + lastmodifieddate
				+ ", version=" + version + ", content=" + content + ", ip=" + ip + ", isshow=" + isshow + ", score="
				+ score + ", specifications=" + specifications + ", forreviewId=" + forreviewId + ", memberId="
				+ memberId + ", productId=" + productId + ", storeId=" + storeId + ", user=" + user + "]";
	}
    
	
	
}