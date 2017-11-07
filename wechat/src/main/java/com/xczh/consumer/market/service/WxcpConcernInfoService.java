package com.xczh.consumer.market.service;

import com.xczh.consumer.market.bean.WxcpConcernInfo;
import com.xczh.consumer.market.bean.WxcpWxJsconfig;

import java.sql.SQLException;
import java.util.List;

public interface WxcpConcernInfoService {
	
	public int insert(WxcpConcernInfo record) throws SQLException ;
	public int delete(WxcpConcernInfo condition) throws SQLException ;
	public int update(WxcpWxJsconfig record) throws SQLException ;
	public List<WxcpConcernInfo> select(WxcpConcernInfo condition, String limit) throws SQLException ;
	
}
