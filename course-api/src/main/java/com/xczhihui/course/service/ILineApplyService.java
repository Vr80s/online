package com.xczhihui.course.service;

import com.xczhihui.course.model.LineApply;

public interface ILineApplyService {
	
	
	LineApply findLineApplyByUserId(String userId);

	void saveOrUpdate(String lockId,LineApply lineApply);
	
}
