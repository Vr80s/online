package com.xczhihui.medical.hospital.vo;

import java.io.Serializable;
import java.util.List;

import com.xczhihui.medical.field.vo.MedicalFieldVO;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public class MedicalHospitalVo implements Serializable {

    /**
     * 医馆表
     */
    private String id;
    /**
     * 经纬度
     */
    private String lal;
    /**
     * 医馆名称
     */
    private String name;
    /**
     * 医馆简介
     */
    private String description;
    /**
     * 联系电话
     */
    private String tel;

    private String email;
    /**
     * 邮编
     */
    private Integer postCode;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 市
     */
    private String county;
    /**
     * 详细地址
     */
    private String detailedAddress;

    /**
     * 是否已认证
     */
    private Boolean authentication;
    /**
     * 分值
     */
    private Double score;

    private List<MedicalHospitalPictureVO> medicalHospitalPictures;

    private List<MedicalFieldVO> fields;

    //坐诊时间
    private String visitTime;

    /**
     * 认证类别   1 医师认证   2 医馆认证
     */
    private Integer certificationType;

    /**
     * 医馆封面图
     */
    private String frontImg;

    /**
     * 微信
     */
    private String wechat;

    /**
     * 头像
     */
    private String headPortrait;

    /**
     * 联系人名称
     */
    private String contactor;

    public String getContactor() {
        return contactor;
    }

    public void setContactor(String contactor) {
        this.contactor = contactor;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLal() {
        return lal;
    }

    public void setLal(String lal) {
        this.lal = lal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPostCode() {
        return postCode;
    }

    public void setPostCode(Integer postCode) {
        this.postCode = postCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    public Boolean getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Boolean authentication) {
        this.authentication = authentication;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public List<MedicalHospitalPictureVO> getMedicalHospitalPictures() {
        return medicalHospitalPictures;
    }

    public void setMedicalHospitalPictures(List<MedicalHospitalPictureVO> medicalHospitalPictures) {
        this.medicalHospitalPictures = medicalHospitalPictures;
    }

    public List<MedicalFieldVO> getFields() {
        return fields;
    }

    public void setFields(List<MedicalFieldVO> fields) {
        this.fields = fields;
    }

    public String getDescription() {
        if (description == null) {
            return null;
        }
        description = description.replace("\n\n", "<br/>");
        description = description.replace("\n", "<br/>");
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }

    /**
     * 认证类别   1 医师认证   2 医馆认证
     */
    public Integer getCertificationType() {
        return certificationType;
    }

    /**
     * 认证类别   1 医师认证   2 医馆认证
     */
    public void setCertificationType(Integer certificationType) {
        this.certificationType = certificationType;
    }

    public String getFrontImg() {
        return frontImg;
    }

    public void setFrontImg(String frontImg) {
        this.frontImg = frontImg;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    @Override
    public String toString() {
        return "MedicalHospitalVo{" +
                ", id=" + id +
                ", lal=" + lal +
                ", name=" + name +
                ", description=" + description +
                ", tel=" + tel +
                ", email=" + email +
                ", postCode=" + postCode +
                ", province=" + province +
                ", city=" + city +
                ", detailedAddress=" + detailedAddress +
                "}";
    }
}
