package com.xczh.consumer.market.service;

import com.xczh.consumer.market.bean.WxcpPayFlow;

import java.sql.SQLException;
import java.util.List;

public interface WxcpPayFlowService {
	
	public List<WxcpPayFlow> select(WxcpPayFlow condition) throws SQLException ;
	public int insert(WxcpPayFlow record) throws SQLException;
	public int delete(WxcpPayFlow record) throws SQLException;
	public int update(WxcpPayFlow record) throws SQLException;
	
}
