package com.xczhihui.message.body;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author hejiwei
 */
public class MessageBody implements Serializable {

    private String id;

    private String title;

    private String content;

    private String pushTime;

    private int pushType;

    private String url;

    private String routeType;

    private MultipartFile pushUserFile;

    private String detailId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }

    public int getPushType() {
        return pushType;
    }

    public void setPushType(int pushType) {
        this.pushType = pushType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public MultipartFile getPushUserFile() {
        return pushUserFile;
    }

    public void setPushUserFile(MultipartFile pushUserFile) {
        this.pushUserFile = pushUserFile;
    }
}
