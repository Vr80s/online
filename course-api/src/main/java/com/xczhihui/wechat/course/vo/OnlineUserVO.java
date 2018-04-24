package com.xczhihui.wechat.course.vo;

import java.io.Serializable;
import java.util.Date;

public class OnlineUserVO implements Serializable{
	
	
    private String id;

    private String name;

    private String loginName;

    private String password;

    private Integer sex;

    private String mobile;

    private String email;

    private Boolean isDelete;

    private String smallHeadPhoto;

    private String bigHeadPhoto;

    private String createPerson;

    private Date createTime;

    private Integer status;

    private String lastLoginIp;

    private Date lastLoginDate;

    private Integer visitSum;

    private Integer stayTime;

    private String info;

    private Integer jobyears;

    private Integer occupation;

    private String occupationOther;

    private String target;

    private Boolean isApply;

    private String fullAddress;

    private Integer menuId;

    private String unionId;

    private Integer userType;

    private String refId;

    private String parentId;

    private String shareCode;

    private Date changeTime;

    private String origin;

    private Integer type;

    private Integer isLecturer;

    private Integer roomNumber;

    private String vhallId;

    private String vhallPass;

    private String vhallName;
    
    private String regionId;

    private String regionAreaId;

    private String regionCityId;

    private String provinceName;

    private String cityName;
    
    private String countyName;

    private String individualitySignature;

    private String blacklist;

    private String gag;

  

    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getSmallHeadPhoto() {
        return smallHeadPhoto;
    }

    public void setSmallHeadPhoto(String smallHeadPhoto) {
        this.smallHeadPhoto = smallHeadPhoto == null ? null : smallHeadPhoto.trim();
    }

    public String getBigHeadPhoto() {
        return bigHeadPhoto;
    }

    public void setBigHeadPhoto(String bigHeadPhoto) {
        this.bigHeadPhoto = bigHeadPhoto == null ? null : bigHeadPhoto.trim();
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson == null ? null : createPerson.trim();
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

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Integer getVisitSum() {
        return visitSum;
    }

    public void setVisitSum(Integer visitSum) {
        this.visitSum = visitSum;
    }

    public Integer getStayTime() {
        return stayTime;
    }

    public void setStayTime(Integer stayTime) {
        this.stayTime = stayTime;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }

    public Integer getJobyears() {
        return jobyears;
    }

    public void setJobyears(Integer jobyears) {
        this.jobyears = jobyears;
    }

    public Integer getOccupation() {
        return occupation;
    }

    public void setOccupation(Integer occupation) {
        this.occupation = occupation;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId == null ? null : regionId.trim();
    }

    public String getRegionAreaId() {
        return regionAreaId;
    }

    public void setRegionAreaId(String regionAreaId) {
        this.regionAreaId = regionAreaId == null ? null : regionAreaId.trim();
    }

    public String getRegionCityId() {
        return regionCityId;
    }

    public void setRegionCityId(String regionCityId) {
        this.regionCityId = regionCityId == null ? null : regionCityId.trim();
    }

    public String getOccupationOther() {
        return occupationOther;
    }

    public void setOccupationOther(String occupationOther) {
        this.occupationOther = occupationOther == null ? null : occupationOther.trim();
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target == null ? null : target.trim();
    }

    public Boolean getIsApply() {
        return isApply;
    }

    public void setIsApply(Boolean isApply) {
        this.isApply = isApply;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress == null ? null : fullAddress.trim();
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId == null ? null : unionId.trim();
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId == null ? null : refId.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode == null ? null : shareCode.trim();
    }

    public Date getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin == null ? null : origin.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsLecturer() {
        return isLecturer;
    }

    public void setIsLecturer(Integer isLecturer) {
        this.isLecturer = isLecturer;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getVhallId() {
        return vhallId;
    }

    public void setVhallId(String vhallId) {
        this.vhallId = vhallId == null ? null : vhallId.trim();
    }

    public String getVhallPass() {
        return vhallPass;
    }

    public void setVhallPass(String vhallPass) {
        this.vhallPass = vhallPass == null ? null : vhallPass.trim();
    }

    public String getVhallName() {
        return vhallName;
    }

    public void setVhallName(String vhallName) {
        this.vhallName = vhallName == null ? null : vhallName.trim();
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName == null ? null : provinceName.trim();
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public String getIndividualitySignature() {
        return individualitySignature;
    }

    public void setIndividualitySignature(String individualitySignature) {
        this.individualitySignature = individualitySignature == null ? null : individualitySignature.trim();
    }

    public String getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(String blacklist) {
        this.blacklist = blacklist == null ? null : blacklist.trim();
    }

    public String getGag() {
        return gag;
    }

    public void setGag(String gag) {
        this.gag = gag == null ? null : gag.trim();
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName == null ? null : countyName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

	@Override
	public String toString() {
		return "OnlineUserVO [id=" + id + ", name=" + name + ", loginName="
				+ loginName + ", password=" + password + ", sex=" + sex
				+ ", mobile=" + mobile + ", email=" + email + ", isDelete="
				+ isDelete + ", smallHeadPhoto=" + smallHeadPhoto
				+ ", bigHeadPhoto=" + bigHeadPhoto + ", createPerson="
				+ createPerson + ", createTime=" + createTime + ", status="
				+ status + ", lastLoginIp=" + lastLoginIp + ", lastLoginDate="
				+ lastLoginDate + ", visitSum=" + visitSum + ", stayTime="
				+ stayTime + ", info=" + info + ", jobyears=" + jobyears
				+ ", occupation=" + occupation + ", occupationOther="
				+ occupationOther + ", target=" + target + ", isApply="
				+ isApply + ", fullAddress=" + fullAddress + ", menuId="
				+ menuId + ", unionId=" + unionId + ", userType=" + userType
				+ ", refId=" + refId + ", parentId=" + parentId
				+ ", shareCode=" + shareCode + ", changeTime=" + changeTime
				+ ", origin=" + origin + ", type=" + type + ", isLecturer="
				+ isLecturer + ", roomNumber=" + roomNumber + ", vhallId="
				+ vhallId + ", vhallPass=" + vhallPass + ", vhallName="
				+ vhallName + ", regionId=" + regionId + ", regionAreaId="
				+ regionAreaId + ", regionCityId=" + regionCityId
				+ ", provinceName=" + provinceName + ", cityName=" + cityName
				+ ", countyName=" + countyName + ", individualitySignature="
				+ individualitySignature + ", blacklist=" + blacklist
				+ ", gag=" + gag + ", description=" + description + "]";
	}
    
    
}