package com.xczhihui.cloudClass.service.impl;

import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.cloudClass.service.StudentManagerService;
import com.xczhihui.cloudClass.vo.StudentManagerVo;

@Service
public class StudentManagerServiceImpl implements StudentManagerService {

	@Override
	public Page<StudentManagerVo> findstudentsInfoPage(
			StudentManagerVo searchVo, int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

}
