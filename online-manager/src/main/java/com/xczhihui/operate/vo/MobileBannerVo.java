package com.xczhihui.operate.vo;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;

public class MobileBannerVo extends OnlineBaseVo {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String url;
    private String imgPath;
    private Integer clickSum;
    private String createPerson;
    private java.util.Date createTime;
    private Integer status;
    private Integer seq;
    private String createPersonName;

    private Integer linkType;
    private String linkCondition;
    private Integer bannerType;
    private String linkParam;
    private String routeType;
    private String linkDesc;
    private String clientType;

    private String menuId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getClickSum() {
        return clickSum;
    }

    public void setClickSum(Integer clickSum) {
        this.clickSum = clickSum;
    }

    @Override
    public String getCreatePerson() {
        return createPerson;
    }

    @Override
    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    @Override
    public java.util.Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getCreatePersonName() {
        return createPersonName;
    }

    public void setCreatePersonName(String createPersonName) {
        this.createPersonName = createPersonName;
    }

    public Integer getLinkType() {
        return linkType;
    }

    public void setLinkType(Integer linkType) {
        this.linkType = linkType;
    }

    public String getLinkCondition() {
        return linkCondition;
    }

    public void setLinkCondition(String linkCondition) {
        this.linkCondition = linkCondition;
    }

    public Integer getBannerType() {
        return bannerType;
    }

    public void setBannerType(Integer bannerType) {
        this.bannerType = bannerType;
    }

    public String getLinkParam() {
        return linkParam;
    }

    public void setLinkParam(String linkParam) {
        this.linkParam = linkParam;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public String getLinkDesc() {
        return linkDesc;
    }

    public void setLinkDesc(String linkDesc) {
        this.linkDesc = linkDesc;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }
}
