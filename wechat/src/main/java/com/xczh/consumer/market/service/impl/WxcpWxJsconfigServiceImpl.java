package com.xczh.consumer.market.service.impl;

import com.xczh.consumer.market.bean.WxcpWxJsconfig;
import com.xczh.consumer.market.dao.WxcpWxJsconfigMapper;
import com.xczh.consumer.market.service.WxcpWxJsconfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class WxcpWxJsconfigServiceImpl implements WxcpWxJsconfigService {
	
	@Autowired
	private WxcpWxJsconfigMapper wxcpWxJsconfigMapper;
	
	public int insert( WxcpWxJsconfig record ) throws SQLException {
		return wxcpWxJsconfigMapper.insert(record);
	}
	
	public int delete( String id ) throws SQLException {
		return wxcpWxJsconfigMapper.delete(id);
	}
	
	public int update( WxcpWxJsconfig record ) throws SQLException {
		return wxcpWxJsconfigMapper.update(record);
	}
	
	public List<WxcpWxJsconfig> select(WxcpWxJsconfig condition, String limit) throws SQLException {
		return wxcpWxJsconfigMapper.select(condition, limit);				
	}
	
}
