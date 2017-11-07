package com.xczh.consumer.market.service;

import com.xczh.consumer.market.bean.WxcpWxTrans;

import java.sql.SQLException;
import java.util.List;

public interface WxcpWxTransService {

	public int insert(WxcpWxTrans record) throws SQLException ;
	public int delete(String id) throws SQLException ;
	public int update(WxcpWxTrans record) throws SQLException ;
	public List<WxcpWxTrans> select(WxcpWxTrans condition, String limit) throws SQLException ;
		
}
