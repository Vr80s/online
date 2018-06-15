package com.xczhihui.bxg.online.web.vo;


import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 学员故事VO
 * @author Rongcai Kang
 */
public class StudentStoryVo extends OnlineBaseVo {

    private String id;
    private String headImg;
    private String name;
    private String course;
    private String company;
    private String salary;
    private String introduce;
    private String title;
    private String text;
    private String menu;
    private Integer menuId;
    private String otherName;
    private Boolean useOtherName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public String getMenu() {
        return menu;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public Boolean isUseOtherName() {
        return useOtherName;
    }

    public void setUseOtherName(Boolean useOtherName) {
        this.useOtherName = useOtherName;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
