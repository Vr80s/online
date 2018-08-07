package com.xczhihui.bxg.online.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.xczhihui.common.support.domain.BasicEntity2;


/**
 * 
* @ClassName: DoctorType分类
* @Description: 医师
* @author yangxuan
* @email yangxuan@ixincheng.com
* @date 2018年8月7日
*
 */
@Entity
@Table(name = "doctor_type")
public class DoctorType extends BasicEntity2 {

	
    /**
     * 名称
     */
	@Column(name ="title")
    private String title;

    /**
     * 图标
     */
    @Column(name ="icon")
    private String icon;

    /**
     * 排序
     */
    @Column(name ="sort")
    private Integer sort;
 
    /**
     * 状态
     */
    @Column(name ="status")
    private Integer status;

    
    /**
     * 备注
     */
    @Column(name ="remark")
    private String remark;
    
    @Transient
    private String doctorNumber;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDoctorNumber() {
		return doctorNumber;
	}

	public void setDoctorNumber(String doctorNumber) {
		this.doctorNumber = doctorNumber;
	}
}