package com.xczhihui.wechat.course.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: MobileProject
 * @Author: wangyishuai
 * @CreateDate: 2018/1/16 10:49
 **/
@TableName("oe_project")
public class MobileProject extends Model<MobileProject> {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     *创建人
     */
    @TableField("create_person")
    private String createPerson;

    /**
     *创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     *是否删除
     */
    @TableField("is_delete")
    private boolean isDelete;

    /**
     *名称
     */
    @TableField("name")
    private String name;

    /**
     *图标
     */
    @TableField("icon")
    private String icon;

    /**
     *排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     *状态
     */
    @TableField("status")
    private Integer status;

    /**
     *备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 连接类型：1：活动页、2：专题页、3：课程:4：主播:5：课程列表（带筛选条件）
     */
    @TableField("link_type")
    private Integer linkType;

    /**
     *连接地址
     */
    @TableField("link_condition")
    private String linkCondition;

    /**
     *1 推荐 2 分类
     */
    @TableField("type")
    private Integer type;

    /**
     * 启用时间
     */
    @TableField("start_time")
    private Date startTime;

    /**
     * 关闭时间
     */
    @TableField("end_time")
    private Date endTime;

    /**
     * 点击数
     */
    @TableField("click_num")
    private Integer clickNum;

    @Override
    protected Serializable pkVal() {
        return null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getClickNum() {
        return clickNum;
    }

    public void setClickNum(Integer clickNum) {
        this.clickNum = clickNum;
    }
}