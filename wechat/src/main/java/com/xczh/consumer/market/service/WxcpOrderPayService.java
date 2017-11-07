package com.xczh.consumer.market.service;

import com.xczh.consumer.market.bean.WxcpOrderPay;

import java.sql.SQLException;
import java.util.List;

/**
 * 
 * 订单支付主表
 * 
 **/
public interface WxcpOrderPayService{
	
	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	public WxcpOrderPay selectByPrimaryKey(String id) throws SQLException;

	/**
	 *
	 * 删除（根据主键ID删除）
	 *
	 **/
	public int deleteByPrimaryKey(String id) throws SQLException;

	/**
	 *
	 * 添加
	 *
	 **/
	public int insert(WxcpOrderPay record) throws SQLException;

	/**
	 *
	 * 添加 （匹配有值的字段）
	 *
	 **/
	public int insertSelective(WxcpOrderPay record) throws SQLException;

	/**
	 *
	 * 修改 （匹配有值的字段）
	 *
	 **/
	public int updateByPrimaryKeySelective(WxcpOrderPay record) throws SQLException;

	/**
	 *
	 * 修改（根据主键ID修改）
	 *
	 **/
	public int updateByPrimaryKey (WxcpOrderPay record) throws SQLException;
	
	/**
	 * 
	 * 查询订单对应的支付流水单信息；
	 * 
	 **/
	public List<WxcpOrderPay> getListWxcpOrderPay(WxcpOrderPay condition) throws SQLException;
	
}
