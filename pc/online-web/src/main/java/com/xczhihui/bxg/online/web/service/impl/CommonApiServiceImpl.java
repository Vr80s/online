package com.xczhihui.bxg.online.web.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.common.support.service.SystemVariateService;
import com.xczhihui.online.api.service.CommonApiService;
import com.xczhihui.online.api.vo.JobVo;
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
	public List<Map<String,Object>> getProblems(String group) {
		// TODO Auto-generated method stub
		//return systemVariateService.getSystemVariatesByName(group);
		return userCenterDao.getProblems(group);
	}

	@Override
	public Map<String,Object> getProblemAnswer(String id) {
		// TODO Auto-generated method stub
		//return systemVariateService.getSystemVariateById(id);
		return userCenterDao.getProblemAnswer(id);
	}

}
