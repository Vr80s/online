package com.xczhihui.utils;

import java.util.List;

public class CourseTreeVo {
	
	//课程ID
	private String id;
	
	private List<ZtreeVo> ztreeVos;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<ZtreeVo> getZtreeVos() {
		return ztreeVos;
	}

	public void setZtreeVos(List<ZtreeVo> ztreeVos) {
		this.ztreeVos = ztreeVos;
	}

}
