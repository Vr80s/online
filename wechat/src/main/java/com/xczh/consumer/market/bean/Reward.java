package com.xczh.consumer.market.bean;

import java.util.Date;

/**
 * @author liutao
 * @create 2017-08-26 15:09
 **/
public class Reward {

    private Integer id;
    private Boolean isDelete;
    private Date createTime;
    private Integer sort;
    private Boolean status;
    private Double price;
    private Boolean isFreedom;
    private Double brokerage;
    private String createPerson;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getIsFreedom() {
        return isFreedom;
    }

    public void setIsFreedom(Boolean freedom) {
        isFreedom = freedom;
    }

    public Double getBrokerage() {
        return brokerage;
    }

    public void setBrokerage(Double brokerage) {
        this.brokerage = brokerage;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }
}
