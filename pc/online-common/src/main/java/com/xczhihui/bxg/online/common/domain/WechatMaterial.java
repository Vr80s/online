package com.xczhihui.bxg.online.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.xczhihui.common.support.domain.BasicEntity2;

@Entity
@Table(name = "wechat_material")
public class WechatMaterial extends BasicEntity2 implements Serializable{
	
	
	private static final long serialVersionUID = 8080612633895343818L;
	
	@Column(name = "title")
    private String title;

	@Column(name = "thumb_media_id")
    private String thumbMediaId;

	@Column(name = "author")
    private String author;

	@Column(name = "digest")
    private String digest;

	@Column(name = "show_cover_pic")
    private Boolean showCoverPic;

	@Column(name = "content_source_url")
    private String contentSourceUrl;

	@Column(name = "material_type")
    private Integer materialType;

	@Column(name = "update_time")
    private Date updateTime;

	@Column(name = "associat_menu")
    private String associatMenu;


	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type="text")
	@Column(name = "content")
    private String content;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId == null ? null : thumbMediaId.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest == null ? null : digest.trim();
    }

    public Boolean getShowCoverPic() {
        return showCoverPic;
    }

    public void setShowCoverPic(Boolean showCoverPic) {
        this.showCoverPic = showCoverPic;
    }

    public String getContentSourceUrl() {
        return contentSourceUrl;
    }

    public void setContentSourceUrl(String contentSourceUrl) {
        this.contentSourceUrl = contentSourceUrl == null ? null : contentSourceUrl.trim();
    }

    public Integer getMaterialType() {
        return materialType;
    }

    public void setMaterialType(Integer materialType) {
        this.materialType = materialType;
    }


    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    public String getAssociatMenu() {
        return associatMenu;
    }

    public void setAssociatMenu(String associatMenu) {
        this.associatMenu = associatMenu == null ? null : associatMenu.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}