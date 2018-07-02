package com.xczhihui.bxg.online.web.vo;


import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;

/**
 * banner图web端调用的结果封装类
 *
 * @author Rongcai Kang
 */
public class BannerVo extends OnlineBaseVo {

    private Integer id;
    /**
     * 图片路径
     */
    private String imgPath;
    /**
     * 图片描述
     */
    private String description;
    /**
     * 图片跳转路径
     */
    private String imgHref;

    private String url;

    private String routeType;

    private String linkParam;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgHref() {
        return imgHref;
    }

    public void setImgHref(String imgHref) {
        this.imgHref = imgHref;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public String getLinkParam() {
        return linkParam;
    }

    public void setLinkParam(String linkParam) {
        this.linkParam = linkParam;
    }
}
