package com.xczhihui.bxg.online.common.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xczhihui.bxg.common.support.domain.BasicEntity2;

@Entity
@Table(name = "oe_wechat_channel")
public class WechatChannel extends BasicEntity2 implements Serializable{
    
	private static final long serialVersionUID = 80806126338955818L;

	@Column(name = "name")
    private String name;

	@Column(name = "contact")
    private String contact;

	@Column(name = "mobile")
    private String mobile;

	@Column(name = "qr_code_img")
    private String qrCodeImg;

	@Column(name = "status")
    private String status;

	@Column(name = "sort")
    private Integer sort;

	@Column(name = "area")
    private String area;
	
	@Column(name = "city")
    private String city;

	@Column(name = "province")
    private String province;
	
	
	@Column(name = "area_id")
    private Integer areaId;
	
	@Column(name = "city_id")
    private Integer cityId;

	@Column(name = "province_id")
    private Integer provinceId;
	
	@Column(name = "custom_qr_code_url")
    private String customQrCodeUrl;
	
	@Column(name = "custom_caoliao_qrcode")
    private String customCaoliaoQrcode;


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


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
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