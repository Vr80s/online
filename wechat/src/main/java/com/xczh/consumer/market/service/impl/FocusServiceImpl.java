package com.xczh.consumer.market.service.impl;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.dao.FocusMapper;
import com.xczh.consumer.market.service.FocusService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.wxpay.entity.FocusVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class FocusServiceImpl implements FocusService {

	@Autowired
	public FocusMapper focusMapper;
	/**
	 * 添加关注信息
	 * @throws SQLException 
	 */
	@Override
	public void addFocusInfo(OnlineUser onlineUser, OnlineUser onlineLecturer, int course_id) throws SQLException {

		focusMapper.addFocusInfo(onlineUser,onlineLecturer,course_id);
	}

	@Override
	public List<FocusVo> findMyFans(String userId, Integer pageNumber, Integer pageSize) throws SQLException {
		// TODO Auto-generated method stub
		return focusMapper.findMyFans(userId,pageNumber,pageSize);
	}

	@Override
	public List<FocusVo> findmyFocus(String userId, Integer pageNumber, Integer pageSize) throws SQLException {
		// TODO Auto-generated method stub
		return focusMapper.findMyFocus(userId,pageNumber,pageSize);
	}
	
	@Override
	public Integer findMyFansCount(String userId) throws SQLException {
		// TODO Auto-generated method stub
		return focusMapper.findMyFansCount(userId);
	}

	@Override
	public Integer findMyFocusCount(String userId) throws SQLException {
		// TODO Auto-generated method stub
		return  focusMapper.findMyFocusCount(userId);
	}
	
	@Override
	public Integer myIsFourslecturer(String userId,String lecturerId) throws SQLException {
		// TODO Auto-generated method stub
		return  focusMapper.myIsFourslecturer(userId,lecturerId);
	}

	@Override
	public ResponseObject cancelFocus(String lecturerId, String userId) throws SQLException {
		// TODO Auto-generated method stub
		return focusMapper.cancelFocus(userId,lecturerId);
	}
	
}
