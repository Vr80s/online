package com.xczh.consumer.market.service;

import com.xczh.consumer.market.bean.WxcpWxJsconfig;

import java.sql.SQLException;
import java.util.List;

public interface WxcpWxJsconfigService {
	public int insert(WxcpWxJsconfig record) throws SQLException ;
	public int delete(String id) throws SQLException ;
	public int update(WxcpWxJsconfig record) throws SQLException ;
	public List<WxcpWxJsconfig> select(WxcpWxJsconfig condition, String limit) throws SQLException ;
}
