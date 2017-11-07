package com.xczh.consumer.market.service.impl;

import com.xczh.consumer.market.dao.WxcpWxRedpackMapper;
import com.xczh.consumer.market.service.WxcpWxRedpackService;
import com.xczh.consumer.market.wxpay.entity.SendRedPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class WxcpWxRedpackServiceImpl implements WxcpWxRedpackService {

	@Autowired
	private WxcpWxRedpackMapper wxcpWxRedpackMapper;
	
	public int insert( SendRedPack record ) throws SQLException {
		return wxcpWxRedpackMapper.insert(record);
	}
	
	public int delete( String id ) throws SQLException {
		return wxcpWxRedpackMapper.delete(id);
	}
	
	public int update( SendRedPack record ) throws SQLException {
		return wxcpWxRedpackMapper.update(record);
	}
	
	public List<SendRedPack> select(SendRedPack condition, String limit) throws SQLException {
		return wxcpWxRedpackMapper.select(condition, limit);
	}

}
