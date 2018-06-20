package com.xczhihui.medical.doctor.vo;

import java.io.Serializable;

public class DoctorQueryVo  implements Serializable {

	private static final long serialVersionUID = 1L;


	private String type;

	private String departmentId;
	
	private String queryKey;
	
	private Integer sortType;

	private String field;
	
	private String hospitalId;
	

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

	@Override
	public String toString() {
		return "DoctorQueryVo [type=" + type + ", departmentId=" + departmentId + ", queryKey=" + queryKey
				+ ", sortType=" + sortType + ", field=" + field + ", hospitalId=" + hospitalId + "]";
	}


	
}
