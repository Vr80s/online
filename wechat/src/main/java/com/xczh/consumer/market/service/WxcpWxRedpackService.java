package com.xczh.consumer.market.service;

import com.xczh.consumer.market.wxpay.entity.SendRedPack;

import java.sql.SQLException;
import java.util.List;

public interface WxcpWxRedpackService {

	public int insert(SendRedPack record) throws SQLException ;
	public int delete(String id) throws SQLException ;
	public int update(SendRedPack record) throws SQLException ;
	public List<SendRedPack> select(SendRedPack condition, String limit) throws SQLException ;
		
}
