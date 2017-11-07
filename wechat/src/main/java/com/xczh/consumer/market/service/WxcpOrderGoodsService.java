package com.xczh.consumer.market.service;

import com.xczh.consumer.market.bean.WxcpOrderGoods;
import com.xczh.consumer.market.vo.WxcpOrderGoodsExt;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 订单关联的商品表
 * 
 **/
public interface WxcpOrderGoodsService{
	/**
	 * 
	 * 根据订单ID，查询该订单对应的商品列表；
	 * 
	 **/
	public List<WxcpOrderGoodsExt> getListWxcpGoodsInfo(ArrayList<String> ids/*String order_id*/) throws SQLException;
	
	/**
	 * 
	 * 查询（根据订单ID查询）
	 * 
	 **/
	public List<WxcpOrderGoods>  selectByOrderId(String order_id) throws SQLException;

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
	public int insert(WxcpOrderGoods record) throws SQLException;

	/**
	 *
	 * 添加 （匹配有值的字段）
	 *
	 **/
	public int insertSelective(WxcpOrderGoods record) throws SQLException;

	/**
	 *
	 * 修改 （匹配有值的字段）
	 *
	 **/
	public int updateByPrimaryKeySelective(WxcpOrderGoods record) throws SQLException;

	/**
	 *
	 * 修改（根据主键ID修改）
	 *
	 **/
	public int updateByPrimaryKey (WxcpOrderGoods record) throws SQLException;
	/**
	 * 根据商品id 查询购买人数
	 * @param goodsId
	 * @return
	 */
	public int getOrderNumByGoodsId(String goodsId) throws SQLException;
	
}