package com.xczh.consumer.market.service.impl;

import com.xczh.consumer.market.bean.WxcpWxTrans;
import com.xczh.consumer.market.dao.WxcpWxTransMapper;
import com.xczh.consumer.market.service.WxcpWxTransService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class WxcpWxTransServiceImpl implements WxcpWxTransService {

	@Autowired
	private WxcpWxTransMapper wxcpWxTransMapper;
	
	public int insert( WxcpWxTrans record ) throws SQLException {
		return wxcpWxTransMapper.insert(record);
	}
	
	public int delete( String id ) throws SQLException {
		return wxcpWxTransMapper.delete(id);
	}
	
	public int update( WxcpWxTrans record ) throws SQLException {
		return wxcpWxTransMapper.update(record);
	}
	
	public List<WxcpWxTrans> select(WxcpWxTrans condition, String limit) throws SQLException {
		return wxcpWxTransMapper.select(condition, limit);
	}
		
}
