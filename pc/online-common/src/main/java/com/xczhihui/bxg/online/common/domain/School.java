package com.xczhihui.bxg.online.common.domain;/**
 * Created by admin on 2016/8/29.
 */

import com.xczhihui.common.support.domain.BasicEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 学校信息实体类
 *
 * @author 康荣彩
 * @create 2016-08-29 15:40
 */
@Entity
@Table(name = "school")
public class School extends BasicEntity {

    /**
     * 城市编号
     */
    @Column(name = "city_id")
    private String cityId;

    /**
     *学校名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 排序字段
     */
    @Column(name = "sort")
    private Integer sort;


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    /**
     * 是否禁用状态:true:启用 false:禁用
     */

    @Column(name = "status")
    private boolean status;

}
