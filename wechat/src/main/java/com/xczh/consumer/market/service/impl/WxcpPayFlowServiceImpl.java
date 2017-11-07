package com.xczh.consumer.market.service.impl;

import com.xczh.consumer.market.bean.WxcpPayFlow;
import com.xczh.consumer.market.dao.WxcpPayFlowMapper;
import com.xczh.consumer.market.service.WxcpPayFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * 
 * 支付详情表；
 * 
 **/
@Service
public class WxcpPayFlowServiceImpl implements WxcpPayFlowService {
	
	@Autowired
	private WxcpPayFlowMapper wxcpPayFlowMapper;
	
	@Override
	public List<WxcpPayFlow> select(WxcpPayFlow condition) throws SQLException {
		return wxcpPayFlowMapper.select(condition);
	}
	
	@Override
	public int insert(WxcpPayFlow record) throws SQLException {
		return wxcpPayFlowMapper.insert(record);
	}
	
	@Override
	public int delete(WxcpPayFlow record) throws SQLException {
		return wxcpPayFlowMapper.delete(record);
	}
	
	@Override
	public int update(WxcpPayFlow record) throws SQLException {
		return wxcpPayFlowMapper.update(record);
	}
	
}
