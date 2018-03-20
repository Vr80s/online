package com.xczh.consumer.market.service.impl;

import com.xczh.consumer.market.bean.WxcpOrderGoods;
import com.xczh.consumer.market.dao.WxcpOrderGoodsMapper;
import com.xczh.consumer.market.service.WxcpOrderGoodsService;
import com.xczh.consumer.market.vo.WxcpOrderGoodsExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * 订单关联的商品表
 * 
 **/
@Service
public class WxcpOrderGoodsServiceImpl implements WxcpOrderGoodsService {
	
	@Autowired
	private WxcpOrderGoodsMapper wxcpOrderGoodsMapper;
		
	/**
	 * 根据订单ID，查询该订单对应的商品列表；
	 **/
	@Override
	public List<WxcpOrderGoodsExt> getListWxcpGoodsInfo(ArrayList<String> ids/*String order_id*/) throws SQLException{
		return wxcpOrderGoodsMapper.getListWxcpGoodsInfo(ids/*order_id*/);
	}
	
	/**
	 * 查询（根据订单ID查询）
	 **/
	@Override
    public List<WxcpOrderGoods>  selectByOrderId (String order_id ) throws SQLException {
		return wxcpOrderGoodsMapper.selectByOrderId(order_id);
	}

	/**
	 * 删除（根据主键ID删除）
	 **/
	@Override
    public int deleteByPrimaryKey (String id ) throws SQLException {
		return wxcpOrderGoodsMapper.deleteByPrimaryKey(id);
	}
	
	/**
	 * 添加
	 **/
	@Override
    public int insert(WxcpOrderGoods record ) throws SQLException {
		return wxcpOrderGoodsMapper.insert(record);
	}

	/**
	 * 添加 （匹配有值的字段）
	 **/
	@Override
    public int insertSelective(WxcpOrderGoods record ) throws SQLException {
		return wxcpOrderGoodsMapper.insertSelective(record);
	}

	/**
	 * 修改 （匹配有值的字段）
	 **/
	@Override
    public int updateByPrimaryKeySelective(WxcpOrderGoods record ) throws SQLException {
		return wxcpOrderGoodsMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 修改（根据主键ID修改）
	 **/
	@Override
    public int updateByPrimaryKey (WxcpOrderGoods record ) throws SQLException {
		return wxcpOrderGoodsMapper.updateByPrimaryKey(record);
	}

	@Override
	public int getOrderNumByGoodsId(String goodsId) throws SQLException {
		return wxcpOrderGoodsMapper.getOrderNumByGoodsId(goodsId);
	}
	
}