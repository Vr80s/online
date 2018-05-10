package com.xczhihui.bxg.online.common.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xczhihui.common.support.domain.BasicEntity2;

@Entity
@Table(name = "wechat_media_manager")
public class WechatMediaManager extends BasicEntity2 implements Serializable{
	

	private static final long serialVersionUID = 8080612633895345817L;
	
	@Column(name = "media_id")
    private String mediaId;

	@Column(name = "media_type")
    private Integer mediaType;

	@Column(name = "url")
    private String url;


    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId == null ? null : mediaId.trim();
    }

    public Integer getMediaType() {
        return mediaType;
    }

    public void setMediaType(Integer mediaType) {
        this.mediaType = mediaType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

}