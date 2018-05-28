package com.xczhihui.user.vo;

import java.util.Date;

import javax.persistence.Column;

public class WechatChannelVo {

	private Integer id;

	private String name;

	private String contact;

	private String mobile;

	private String city;

	private String province;

	private String qrCodeImg;

	private Date createTime;

	private Boolean isDelete;

	private String status;

	private String createPerson;

	private Integer sort;

	private String area;

	private String realCounty;

	private String realCitys;

	private String realProvince;

	// province:120000
	// realProvince:天津市
	// city:120100
	// realCitys:天津市
	// area:120101
	// realCounty:和平区
	
	
	private String customQrCodeUrl;
	private String customCaoliaoQrcode;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact == null ? null : contact.trim();
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile == null ? null : mobile.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province == null ? null : province.trim();
	}

	public String getQrCodeImg() {
		return qrCodeImg;
	}

	public void setQrCodeImg(String qrCodeImg) {
		this.qrCodeImg = qrCodeImg == null ? null : qrCodeImg.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson == null ? null : createPerson.trim();
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getRealCounty() {
		return realCounty;
	}

	public void setRealCounty(String realCounty) {
		this.realCounty = realCounty;
	}

	public String getRealCitys() {
		return realCitys;
	}

	public void setRealCitys(String realCitys) {
		this.realCitys = realCitys;
	}

	public String getRealProvince() {
		return realProvince;
	}

	public void setRealProvince(String realProvince) {
		this.realProvince = realProvince;
	}

	public String getCustomQrCodeUrl() {
		return customQrCodeUrl;
	}

	public void setCustomQrCodeUrl(String customQrCodeUrl) {
		this.customQrCodeUrl = customQrCodeUrl;
	}

	public String getCustomCaoliaoQrcode() {
		return customCaoliaoQrcode;
	}

	public void setCustomCaoliaoQrcode(String customCaoliaoQrcode) {
		this.customCaoliaoQrcode = customCaoliaoQrcode;
	}

	
}