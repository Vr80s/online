package com.xczhihui.bxg.online.web.vo;

/**
 *  课程类别结果封装类
 * @author Rongcai Kang
 */
public class ScoreTypeVo {

    /**
     * 学科ID号
     */
    private String menuId;

    /**
     * 课程类别ID
     */
    private String courseTypeId;

    /**
     * 课程类别名称
     */
    private String name;


    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getCourseTypeId() {
        return courseTypeId;
    }

    public void setCourseTypeId(String courseTypeId) {
        this.courseTypeId = courseTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
