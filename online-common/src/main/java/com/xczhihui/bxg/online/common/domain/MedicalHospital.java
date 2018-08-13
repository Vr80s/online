package com.xczhihui.bxg.online.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * The persistent class for the medical_hospital database table.
 */
@Entity
@Table(name = "medical_hospital")
public class MedicalHospital implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String city;

    @Column(name = "create_person")
    private String createPerson;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    private Boolean deleted;

    @Basic(fetch = FetchType.LAZY)
    @Type(type = "text")
    private String description;

    @Column(name = "detailed_address")
    private String detailedAddress;

    private String email;

    private String lal;

    private String name;

    @Column(name = "post_code")
    private Integer postCode;

    private String province;

    private String remark;

    private Boolean status;

    @Transient
    private Integer statusnum;
    @Transient
    private boolean hasPicture;

    private String tel;

    @Column(name = "update_person")
    private String updatePerson;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time")
    private Date updateTime;

    private String version;
    @Transient
    private boolean isDependence;

    private boolean recommend;

    private boolean authentication;

    private Double score;

    private Integer sort;

    @Column(name = "client_type")
    private Integer clientType;

    @Column(name = "recommend_sort")
    private Integer recommendSort;

    @Column(name = "authentication_id")
    private String authenticationId;

    @Column(name = "source_id")
    private String sourceId;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sort_update_time")
    private Date sortUpdateTime;

    /**
     * 微信
     */
    private String wechat;

    /**
     * 联系人
     */
    private String contactor;

    /**
     * 头像
     */
    @Column(name = "head_portrait")
    private String headPortrait;

    /**
     * 启用时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @Column(name = "enable_time")
    private Date enableTime;

    @Transient
    private Integer authenticationNum;
    @Transient
    private String loginName;

    public MedicalHospital() {
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getContactor() {
        return contactor;
    }

    public void setContactor(String contactor) {
        this.contactor = contactor;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public boolean isAuthentication() {
        return authentication;
    }

    public void setAuthentication(boolean authentication) {
        this.authentication = authentication;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getRecommendSort() {
        return recommendSort;
    }

    public void setRecommendSort(Integer recommendSort) {
        this.recommendSort = recommendSort;
    }

    public boolean isDependence() {
        return isDependence;
    }

    public void setDependence(boolean dependence) {
        isDependence = dependence;
    }

    public boolean isHasPicture() {
        return hasPicture;
    }

    public void setHasPicture(boolean hasPicture) {
        this.hasPicture = hasPicture;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCreatePerson() {
        return this.createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetailedAddress() {
        return this.detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLal() {
        return this.lal;
    }

    public void setLal(String lal) {
        this.lal = lal;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPostCode() {
        return this.postCode;
    }

    public void setPostCode(Integer postCode) {
        this.postCode = postCode;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUpdatePerson() {
        return this.updatePerson;
    }

    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(String authenticationId) {
        this.authenticationId = authenticationId;
    }

    public Integer getStatusnum() {
        return statusnum;
    }

    public void setStatusnum(Integer statusnum) {
        this.statusnum = statusnum;
    }

    public Date getEnableTime() {
        return enableTime;
    }

    public void setEnableTime(Date enableTime) {
        this.enableTime = enableTime;
    }

    public Integer getAuthenticationNum() {
        return authenticationNum;
    }

    public void setAuthenticationNum(Integer authenticationNum) {
        this.authenticationNum = authenticationNum;
    }

    public Date getSortUpdateTime() {
        return sortUpdateTime;
    }

    public void setSortUpdateTime(Date sortUpdateTime) {
        this.sortUpdateTime = sortUpdateTime;
    }
    
    
}