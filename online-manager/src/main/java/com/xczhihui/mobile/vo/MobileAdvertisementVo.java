package com.xczhihui.mobile.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Description：广告页
 * creed: Talk is cheap,show me the code
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/8/6 17:52
 **/
public class MobileAdvertisementVo implements Serializable {

    private static final long serialVersionUID = 8408851760572361763L;
    /**
     * 唯一标识
     */
    private Integer id;
    /**
     * 名称
     */
    private String name;

    /**
     * 图片地址
     */
    private String url;

    /**
     * 点击量
     */
    private Integer clickSum;

    /**
     * 创建人id
     */
    private String createPerson;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 状态，0禁用，1启用
     */
    private Integer status;

    /**
     * 图片链接地址
     */
    private String imgPath;

    /**
     * 连接类型：1：活动页、2：专题页、3：课程:4：主播:5：课程列表（带筛选条件）
     */
    private Integer linkType;

    /**
     * 连接条件
     */
    private String linkCondition;

    private String routeType;

    private String linkParam;

    //路由链接
    private String target;

    private String linkDesc;
    private String menuId;

    private boolean isDelete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }
}
