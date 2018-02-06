package com.xczhihui.bxg.online.web.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.support.domain.SystemVariate;
import com.xczhihui.bxg.common.support.service.SystemVariateService;
import com.xczhihui.bxg.online.api.service.CommonApiService;
import com.xczhihui.bxg.online.api.vo.JobVo;
import com.xczhihui.bxg.online.web.dao.UserCenterDao;

@Service
public class CommonApiServiceImpl implements CommonApiService {

	@Autowired
	public UserCenterDao userCenterDao;//DAO
	
	@Autowired
	public SystemVariateService  systemVariateService;

	@Override
	public List<JobVo> getJob(String group) {
		// TODO Auto-generated method stub
		return userCenterDao.getJob(group);
	}

	@Override
	public List<SystemVariate> getProblems(String group) {
		// TODO Auto-generated method stub
		return systemVariateService.getSystemVariatesByName(group);
	}

	@Override
	public SystemVariate getProblemAnswer(String id) {
		// TODO Auto-generated method stub
		return systemVariateService.getSystemVariateById(id);
	}

}
