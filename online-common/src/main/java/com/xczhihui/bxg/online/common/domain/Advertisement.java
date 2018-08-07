package com.xczhihui.bxg.online.common.domain;

import com.xczhihui.common.support.domain.BasicEntity2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 首页banner图实体类
 *
 * @author Rongcai Kang
 */
@Entity
@Table(name = "oe_mobile_advertisement")
public class Advertisement extends BasicEntity2 implements Serializable {

    private static final long serialVersionUID = -5266691915739016681L;
    /**
     * 广告名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 图片路径
     */
    @Column(name = "img_path")
    private String imgPath;
    /**
     * 图片跳转路径
     */
    @Column(name = "url")
    private String url;

    @Column(name = "status")
    private Integer status;

    @Column(name = "click_sum")
    private Integer clickSum;

    @Column(name = "route_type")
    private String routeType;

    @Column(name = "link_param")
    private String linkParam;

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
}
