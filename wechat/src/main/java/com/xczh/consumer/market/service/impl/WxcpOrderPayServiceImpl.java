package com.xczh.consumer.market.service.impl;

import com.xczh.consumer.market.bean.WxcpOrderPay;
import com.xczh.consumer.market.dao.WxcpOrderPayMapper;
import com.xczh.consumer.market.service.WxcpOrderPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;


/**
 * 
 * 订单支付主表
 * 
 **/
@Service
public class WxcpOrderPayServiceImpl implements WxcpOrderPayService {
	
	@Autowired
	private WxcpOrderPayMapper wxcpOrderPayMapper;

	/**
	 * 查询（根据主键ID查询）
	 **/
	@Override
	public WxcpOrderPay selectByPrimaryKey (String id ) throws SQLException {
		return wxcpOrderPayMapper.selectByPrimaryKey(id);
	}

	/**
	 * 删除（根据主键ID删除）
	 **/
	@Override
	public int deleteByPrimaryKey ( String id ) throws SQLException {
		return wxcpOrderPayMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 添加
	 **/
	@Override
	public int insert( WxcpOrderPay record ) throws SQLException {
		return wxcpOrderPayMapper.insert(record);
	}

	/**
	 * 添加 （匹配有值的字段）
	 **/
	@Override
	public int insertSelective( WxcpOrderPay record ) throws SQLException {
		return wxcpOrderPayMapper.insertSelective(record);	
	}

	/**
	 * 修改 （匹配有值的字段）
	 **/
	@Override
	public int updateByPrimaryKeySelective( WxcpOrderPay record ) throws SQLException {
		return wxcpOrderPayMapper.updateByPrimaryKeySelective(record);	
	}

	/**
	 * 修改（根据主键ID修改）
	 **/
	@Override
	public int updateByPrimaryKey ( WxcpOrderPay record ) throws SQLException {
		return wxcpOrderPayMapper.updateByPrimaryKey(record);	
	}

	/**
	 * 查询订单对应的支付流水单信息；
	 **/
	@Override
	public List<WxcpOrderPay> getListWxcpOrderPay(WxcpOrderPay condition) throws SQLException {
		return wxcpOrderPayMapper.getListWxcpOrderPay(condition);
	}
	
}