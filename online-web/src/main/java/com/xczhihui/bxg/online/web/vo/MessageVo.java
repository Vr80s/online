
package com.xczhihui.bxg.online.web.vo;

import java.util.Date;

/**
 * @author hejiwei
 */
public class MessageVo {
    private String id;

    /**
     * 消息内容
     */
    private String context;

    /**
     * 读取状态：0.未读取  1.已读取;
     */
    private Short readstatus;

    /**
     * 创建时间
     */
    private Date createTime;

    private Integer type;

    private String detailId;

    private String routeType;

    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContext() {
        return this.context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Short getReadstatus() {
        return readstatus;
    }

    public void setReadstatus(Short readstatus) {
        this.readstatus = readstatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }
}
