package com.xczh.consumer.market.service;

import com.xczh.consumer.market.bean.WxcpOrderInfo;

import java.sql.SQLException;
import java.util.List;

/**
 * 
 * 订单表
 * 
 **/
public interface WxcpOrderInfoService{
	
	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	public WxcpOrderInfo selectByPrimaryKey(String id) throws SQLException;

	/**
	 *
	 * 删除（根据主键ID删除）(取消订单)
	 *
	 **/
	public int deleteByPrimaryKey(String id) throws SQLException;

	/**
	 *
	 * 取消（根据主键ID取消）
	 *
	 **/
	public int cancelByPrimaryKey(String buyer_id, String order_id) throws SQLException;

	/**
	 *
	 * 修改订单状态；
	 *
	 **/
	public int updateOrderStatus(String id, String order_status) throws SQLException ;

	/**
	 *
	 * 添加
	 *
	 **/
	public int insert(WxcpOrderInfo record) throws SQLException;

	/**
	 *
	 * 添加 （匹配有值的字段）
	 *
	 **/
	public int insertSelective(WxcpOrderInfo record) throws SQLException;

	/**
	 *
	 * 修改 （匹配有值的字段）
	 *
	 **/
	public int updateByPrimaryKeySelective(WxcpOrderInfo record) throws SQLException;

	/**
	 *
	 * 修改（根据主键ID修改）
	 *
	 **/
	public int updateByPrimaryKey (WxcpOrderInfo record) throws SQLException;

	/**
	 * 
	 * 查询买家对应的订单信息；
	 * 
	 **/
	public List<WxcpOrderInfo> getListWxcpOrderInfo(WxcpOrderInfo condition) throws SQLException;
}
