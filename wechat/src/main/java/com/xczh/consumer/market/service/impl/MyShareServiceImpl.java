package com.xczh.consumer.market.service.impl;

import com.xczh.consumer.market.dao.MyShareMapper;
import com.xczh.consumer.market.service.MyShareService;
import com.xczh.consumer.market.vo.PartnerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MyShareServiceImpl implements MyShareService {

	@Autowired
    MyShareMapper shareMapper;

	@Override
	public Map<String, Object> findOneCourse(String courseId) throws SQLException {
		// TODO Auto-generated method stub
		return shareMapper.findOneCourse(courseId);
	}

	@Override
	public Map<String, Object> findMyBrokerage(String userId) throws SQLException {
		// TODO Auto-generated method stub
		return shareMapper.findMyBrokerage(userId);
	}

	@Override
	public List<Map<String, Object>> findMyPartnerList(String userId) throws SQLException {
		// TODO Auto-generated method stub
		return shareMapper.findMyPartnerList(userId);
	}
	
	@Override
	public List<PartnerVo> findMyPartnerList1(String userId) throws SQLException {
		// TODO Auto-generated method stub
		List<PartnerVo> tempList = new ArrayList<PartnerVo>();
		//获得一级人员列表
		List<PartnerVo> list = shareMapper.findMyPartnerList1(userId);
		for(PartnerVo p : list){
			String id = p.getId(); //获取一级用户ID
			p.setCountMap(shareMapper.getPartnerLevelCount(id)); //获取二级、三级的统计数据 赋值到对象中
			tempList.add(p);
		}
		return tempList;
	}

	@Override
	public List<Map<String, Object>> findMyBrokerageList(String userId) throws SQLException {
		// TODO Auto-generated method stub
		return shareMapper.findMyBrokerageList(userId);
	}

	@Override
	public Map<String, Object> findBrokerageDetail(String id) throws SQLException {
		// TODO Auto-generated method stub
		return shareMapper.findBrokerageDetail(id);
	}
}
