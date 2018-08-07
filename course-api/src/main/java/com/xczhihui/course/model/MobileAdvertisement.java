package com.xczhihui.course.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * Description：广告页实体类
 * creed: Talk is cheap,show me the code
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/8/7 17:03
 **/
@TableName("oe_mobile_advertisement")
public class MobileAdvertisement extends Model<MobileAdvertisement> {

    private static final long serialVersionUID = 1L;

    private Integer id;

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
    private Date createTime;

    /**
     * 状态，0禁用，1启用
     */
    @TableField("status")
    private Integer status;

    /**
     * 图片链接地址
     */
    @TableField("img_path")
    private String imgPath;

    @TableField("route_type")
    private String routeType;

    @TableField("link_param")
    private String linkParam;

    //路由链接
    @TableField(exist = false)
    private String target;

    @Override
    protected Serializable pkVal() {
        return null;
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

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
