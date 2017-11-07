package com.xczh.consumer.market.service;

import com.xczh.consumer.market.vo.PartnerVo;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface MyShareService {

	public Map<String, Object> findOneCourse(String courseId) throws SQLException;
	
	public Map<String, Object> findMyBrokerage(String userId) throws SQLException;
	
	public List<Map<String, Object>> findMyPartnerList(String userId) throws SQLException;
	
	public List<PartnerVo> findMyPartnerList1(String userId) throws SQLException;
	
	public List<Map<String, Object>> findMyBrokerageList(String userId) throws SQLException;
	
	public Map<String, Object> findBrokerageDetail(String id) throws SQLException;
}
