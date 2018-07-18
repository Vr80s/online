package com.xczhihui.medical.banner.model;


import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
@TableName("oe_banner2")
public class OeBanner extends Model<OeBanner> {

    private static final long serialVersionUID = -2834185652843675982L;
    
    
    /**
     * 课程申请资源表
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    
    /**
     * 图片路径
     */
    @TableField("img_path")
    private String imgPath;
    
    /**
     * banner图片的16进制背景颜色
     */
    @TableField("bg_color")
    private String bgColor;
    
    /**
     * 图片描述
     */
    private String description;
    
    /**
     * 图片跳转路径
     */
    @TableField("img_href")
    private String imgHref;

    /**
     * 排序
     */
    private Integer sort;

    private Integer status;

    @TableField("start_time")
    private Date startTime;
    
    @TableField("end_time")
    private Date endTime;
    
    @TableField("click_count")
    private Integer clickCount;
    
    private Integer type;

    @TableField("route_type")
    private String routeType;

    @TableField("link_param")
    private String linkParam;
    
    
    
    /**
     * 是否删除：0 未删除 1 删除
     */
    @TableField("is_delete")
    private Boolean isDelete;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 创建人ID
     */
    @TableField("create_person")
    private String createPerson;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
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


    public Integer getSort() {
        return sort;
    }


    public void setSort(Integer sort) {
        this.sort = sort;
    }


    public Integer getStatus() {
        return status;
    }


    public void setStatus(Integer status) {
        this.status = status;
    }


    public Date getStartTime() {
        return startTime;
    }


    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }


    public Date getEndTime() {
        return endTime;
    }


    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    public Integer getClickCount() {
        return clickCount;
    }


    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }


    public Integer getType() {
        return type;
    }


    public void setType(Integer type) {
        this.type = type;
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


    public Boolean getIsDelete() {
        return isDelete;
    }


    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }


    public Date getCreateTime() {
        return createTime;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public String getCreatePerson() {
        return createPerson;
    }


    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public String getBgColor() {
        return bgColor;
    }


    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }


    @Override
    public String toString() {
        return "OeBanner [id=" + id + ", imgPath=" + imgPath + ", description=" + description + ", imgHref=" + imgHref
                + ", sort=" + sort + ", status=" + status + ", startTime=" + startTime + ", endTime=" + endTime
                + ", clickCount=" + clickCount + ", type=" + type + ", routeType=" + routeType + ", linkParam="
                + linkParam + ", isDelete=" + isDelete + ", createTime=" + createTime + ", createPerson=" + createPerson
                + "]";
    }

    
    
   
}
