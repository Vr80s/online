package com.xczhihui.bxg.online.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.Type;


/**
 * The persistent class for the medical_doctor database table.
 */
@Entity
@Table(name = "medical_doctor")
public class MedicalDoctor implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column(name = "authentication_information_id")
    private String authenticationInformationId;

    private String city;

    @Column(name = "create_person")
    private String createPerson;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    private boolean deleted;

    @Basic(fetch = FetchType.LAZY)
    @Type(type = "text")
    private String description;

    @Column(name = "detailed_address")
    private String detailedAddress;

    private String name;

    private String province;

    private String remark;

    private boolean status;

    private String tel;

    private String title;

    private String type;

    @Column(name = "update_person")
    private String updatePerson;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time")
    private Date updateTime;

    private String version;

    @Column(name = "work_time")
    private String workTime;

    @Transient
    private Integer statusnum;

    @Column(name = "client_type")
    private Integer clientType;

    @Transient
    private boolean has;

    private boolean recommend;

    @Column(name = "recommend_sort")
    private Integer recommendSort;

    @Column(name = "source_id")
    private String sourceId;

    @Column(name = "card_num")
    private String cardNum;

    @Column(name = "field_text")
    private String fieldText;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sort_update_time")
    private Date sortUpdateTime;

    @Transient
    private String department;
    @Transient
    private String hospital;
    @Transient
    private String loginName;

    public MedicalDoctor() {
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getFieldText() {
        return fieldText;
    }

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public void setFieldText(String fieldText) {
        this.fieldText = fieldText;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public boolean isHas() {
        return has;
    }

    public void setHas(boolean has) {
        this.has = has;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public Integer getRecommendSort() {
        return recommendSort;
    }

    public void setRecommendSort(Integer recommendSort) {
        this.recommendSort = recommendSort;
    }

    public Integer getStatusnum() {
        return statusnum;
    }

    public void setStatusnum(Integer statusnum) {
        this.statusnum = statusnum;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthenticationInformationId() {
        return this.authenticationInformationId;
    }

    public void setAuthenticationInformationId(String authenticationInformationId) {
        this.authenticationInformationId = authenticationInformationId;
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

    public boolean getDeleted() {
        return this.deleted;
    }

    public void setDeleted(boolean deleted) {
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

    public String getWorkTime() {
        return this.workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public Date getSortUpdateTime() {
        return sortUpdateTime;
    }

    public void setSortUpdateTime(Date sortUpdateTime) {
        this.sortUpdateTime = sortUpdateTime;
    }

}