package com.xczhihui.course.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: Course.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年1月14日<br>
 */
@TableName("oe_course_mobile_banner")
public class MobileBanner extends Model<MobileBanner> {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 图片地址
     */
    @TableField("url")
    private String url;

    /**
     * 点击量
     */
    @TableField("click_sum")
    private Integer clickSum;

    /**
     * 创建人id
     */
    @TableField("create_person")
    private String createPerson;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private java.util.Date createTime;

    /**
     * 状态，0禁用，1启用
     */
    @TableField("status")
    private Integer status;

    /**
     * 排序
     */
    @TableField("seq")
    private Integer seq;

    /**
     * 图片链接地址
     */
    @TableField("img_path")
    private String imgPath;

    /**
     * 连接类型：1：活动页、2：专题页、3：课程:4：主播:5：课程列表（带筛选条件）
     */
    @TableField("link_type")
    private Integer linkType;

    /**
     * 连接条件
     */
    @TableField("link_condition")
    private String linkCondition;

    /**
     * 1 推荐 2 线下课程 3 直播 4 听课
     */
    @TableField("banner_type")
    private Integer bannerType;

    @TableField("route_type")
    private String routeType;

    @TableField("link_param")
    private String linkParam;

    //客户端类型1:pc,2:h5,3:android,4:ios
    @TableField("client_type")
    private String clientType;

    //路由链接
    @TableField(exist = false)
    private String target;

    @Override
    protected Serializable pkVal() {
        return null;
    }

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

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
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

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }
}
