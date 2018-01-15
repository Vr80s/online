package com.xczh.consumer.market.service.impl;

import com.xczh.consumer.market.bean.WxcpOrderInfo;
import com.xczh.consumer.market.dao.WxcpOrderInfoMapper;
import com.xczh.consumer.market.service.WxcpOrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;


/**
 * 
 * 订单表
 * 
 **/
@Service
public class WxcpOrderInfoServiceImpl implements WxcpOrderInfoService {
	
	@Autowired
	private WxcpOrderInfoMapper wxcpOrderInfoMapper;
	
	/**
	 * 查询（根据主键ID查询）
	 **/
	@Override
	public WxcpOrderInfo selectByPrimaryKey (String id ) throws SQLException {
		return wxcpOrderInfoMapper.selectByPrimaryKey(id);
	}

	/**
	 * 删除（根据主键ID删除）(取消订单)
	 **/
	@Override
	public int deleteByPrimaryKey ( String id ) throws SQLException {
		return wxcpOrderInfoMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 
	 * 取消（根据主键ID取消）
	 * 
	 **/
	@Override
    public int cancelByPrimaryKey (String buyer_id, String order_id ) throws SQLException {
		return wxcpOrderInfoMapper.cancelByPrimaryKey(buyer_id,order_id);
	}
	
	/**
	 * 
	 * 修改订单状态；
	 * 
	 **/
	@Override
    public int updateOrderStatus (String id, String order_status ) throws SQLException {
		return wxcpOrderInfoMapper.updateOrderStatus(id,order_status);
	}

	/**
	 * 添加
	 **/
	@Override
	public int insert( WxcpOrderInfo record ) throws SQLException {
		return wxcpOrderInfoMapper.insert(record);
	}

	/**
	 * 添加 （匹配有值的字段）
	 **/
	@Override
	public int insertSelective( WxcpOrderInfo record ) throws SQLException {
		return wxcpOrderInfoMapper.insertSelective(record);
	}

	/**
	 * 修改 （匹配有值的字段）
	 **/
	@Override
	public int updateByPrimaryKeySelective( WxcpOrderInfo record ) throws SQLException {
		return wxcpOrderInfoMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 修改（根据主键ID修改）
	 **/
	@Override
	public int updateByPrimaryKey ( WxcpOrderInfo record ) throws SQLException {
		return wxcpOrderInfoMapper.updateByPrimaryKey(record);
	}

	/**
	 * 查询买家对应的订单信息；
	 **/
	@Override
	public List<WxcpOrderInfo> getListWxcpOrderInfo(WxcpOrderInfo condition) throws SQLException {
		return wxcpOrderInfoMapper.getListWxcpOrderInfo(condition);
	}
	
}