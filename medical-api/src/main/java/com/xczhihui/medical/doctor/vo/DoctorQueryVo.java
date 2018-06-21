package com.xczhihui.medical.doctor.vo;

import java.io.Serializable;

import com.baomidou.mybatisplus.toolkit.StringUtils;


public class DoctorQueryVo  implements Serializable {

	private static final long serialVersionUID = 1L;


	private String type;

	private String departmentId;
	
	private String queryKey;
	
	private Integer sortType;

	private String field;
	
	private String hospitalId;
	
	private Integer departmentFalg;
	
	
	public void bulid() {
		this.setDepartmentFalg(departmentFalg);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getQueryKey() {
		return queryKey;
	}

	public void setQueryKey(String queryKey) {
		this.queryKey = queryKey;
	}

	public Integer getSortType() {
		return sortType;
	}

	public void setSortType(Integer sortType) {
		this.sortType = sortType;
	}
	

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}
	
	public Integer getDepartmentFalg() {
		return departmentFalg;
	}

	public void setDepartmentFalg(Integer departmentFalg) {
		String department = this.getDepartmentId();
		String queryKey = this.getQueryKey();
		
		if(StringUtils.isNotEmpty(department) && StringUtils.isEmpty(queryKey)){
			this.departmentFalg = 1;
		}else if(StringUtils.isEmpty(department) && StringUtils.isNotEmpty(queryKey)){
			this.departmentFalg = 2;
		}else if(StringUtils.isNotEmpty(department) &&  StringUtils.isNotEmpty(queryKey)) {
			this.departmentFalg = 3;
		}else if(StringUtils.isEmpty(department) && StringUtils.isEmpty(queryKey)){
			this.departmentFalg = 4;
		}
	}

	@Override
	public String toString() {
		return "DoctorQueryVo [type=" + type + ", departmentId=" + departmentId + ", queryKey=" + queryKey
				+ ", sortType=" + sortType + ", field=" + field + ", hospitalId=" + hospitalId + "]";
	}


	
}
