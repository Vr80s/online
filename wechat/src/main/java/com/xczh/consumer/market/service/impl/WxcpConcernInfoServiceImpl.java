package com.xczh.consumer.market.service.impl;

import com.xczh.consumer.market.bean.WxcpConcernInfo;
import com.xczh.consumer.market.bean.WxcpWxJsconfig;
import com.xczh.consumer.market.dao.WxcpConcernInfoMapper;
import com.xczh.consumer.market.service.WxcpConcernInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class WxcpConcernInfoServiceImpl implements WxcpConcernInfoService {

	@Autowired
	private WxcpConcernInfoMapper wxcpConcernInfoMapper;
	
	@Override
    public int insert(WxcpConcernInfo record ) throws SQLException {
		return wxcpConcernInfoMapper.insert(record);
	}
	
	@Override
    public int delete(WxcpConcernInfo condition ) throws SQLException {
		return wxcpConcernInfoMapper.delete(condition);
	}
	
	@Override
    public int update(WxcpWxJsconfig record ) throws SQLException {
		return wxcpConcernInfoMapper.update(record);
	}
	
	@Override
    public List<WxcpConcernInfo> select(WxcpConcernInfo condition, String limit) throws SQLException {
		return wxcpConcernInfoMapper.select(condition,limit);
	}
	
}

