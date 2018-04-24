package com.xczhihui.bxg.online.common.domain;

import com.xczhihui.common.support.domain.BasicEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 学科-课程类别中间表实体类
 *  @author Rongcai Kang
 */
@Entity
@Table(name = "menu_coursetype")
public class MenuCourseType  extends BasicEntity {

    /**
     * 学科id
     */
    @Column(name = "menu_id")
    private Integer  menuId;

    /**
     * 课程类别id
     */
    @Column(name = "course_type_id")
    private String courseTypeId;

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getCourseTypeId() {
        return courseTypeId;
    }

    public void setCourseTypeId(String courseTypeId) {
        this.courseTypeId = courseTypeId;
    }
}
