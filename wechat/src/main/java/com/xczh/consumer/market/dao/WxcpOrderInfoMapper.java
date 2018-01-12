package com.xczh.consumer.market.dao;

import com.xczh.consumer.market.bean.WxcpOrderInfo;
import com.xczh.consumer.market.utils.DateUtil;
import com.xczh.consumer.market.utils.GenerateSequenceUtil;
import com.xczh.consumer.market.utils.JdbcUtil;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 
 * WxcpOrderInfoMapper数据库操作接口类
 * @author yanghui
 **/
@Repository
public class WxcpOrderInfoMapper extends BasicSimpleDao{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	public WxcpOrderInfo selectByPrimaryKey (String id ) throws SQLException {
		return null;
	}

	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	public int deleteByPrimaryKey ( String id ) throws SQLException {
		
		StringBuilder sql = new StringBuilder();
		sql.append("update  				");
		sql.append(" 	wxcp_order_info 	");
		sql.append("set  				  	");
		sql.append("  	is_deleted =  -1  	");
		sql.append("where                	");
		sql.append("	1 = 1  			   	");
		sql.append("	and id = ?        	");
		
		super.update(
				JdbcUtil.getCurrentConnection(),
				sql.toString(), 
				id      
				);
		
		return 0;
	}

	/**
	 * 
	 * 取消（根据主键ID取消）
	 * 
	 **/
	public int cancelByPrimaryKey ( String buyer_id,String order_id ) throws SQLException {
		
		StringBuilder sql = new StringBuilder();
		sql.append("update  				");
		sql.append(" 	wxcp_order_info 	");
		sql.append("set  				  	");
		sql.append("  	order_status =  30  ");
		sql.append("where                	");
		sql.append("	1 = 1  			   	");
		sql.append("	and id = ?        	");
		sql.append("	and buyer_id = ?  	");
		
		super.update(
				JdbcUtil.getCurrentConnection(), 
				sql.toString(), 
				order_id,
				buyer_id
				);		
		return 0;
	}

	/**
	 * 
	 * 修改订单状态；
	 * 
	 **/
	public int updateOrderStatus ( String id,String order_status ) throws SQLException {
		
		StringBuilder sql = new StringBuilder();
		sql.append("update  				");
		sql.append(" 	wxcp_order_info 	");
		sql.append("set  				  	");
		sql.append("  	order_status =  ?  ");
		sql.append("where                	");
		sql.append("	1 = 1  			   	");
		sql.append("	and id = ?        	");
		
		super.update(
				JdbcUtil.getCurrentConnection(), 
				sql.toString(), 
				order_status,
				id
				);		
		
		return 0;
	}
	
	/**
	 * 
	 * 添加
	 * 
	 **/
	public int insert( WxcpOrderInfo record ) throws SQLException {
		
		StringBuilder sql = new StringBuilder();
		sql.append("insert into wxcp_order_info 		");
		sql.append("(	                               	");
		sql.append("  id 				,               ");
		sql.append("  buyer_id 			,               ");
		sql.append("  buyer_name 		,               ");
		sql.append("  buyer_mobile 		,               ");
		sql.append("  seller_id 		,               ");
		sql.append("  seller_name 		,               ");
		sql.append("  seller_mobile 	,               ");
		sql.append("  shop_id 			,               ");
		sql.append("  shop_name 		,               ");
		sql.append("  order_status 		,               ");
		sql.append("  origin_fee 		,               ");
		sql.append("  acture_fee 		,               ");
		sql.append("  coupon_price 		,               ");
		sql.append("  expire_time 		,               ");
		sql.append("  payment_timeout 	,               ");
		sql.append("  payment_time 		,               ");
		sql.append("  is_deleted 		,               ");
		sql.append("  created_time 		,               ");
		sql.append("  order_no 			,               ");
		sql.append("  updated_time 	                    ");
		sql.append(")                                   ");
		sql.append("values                              ");
		sql.append("(                                   ");
		sql.append("  ?,                                ");
		sql.append("  ?,                                ");
		sql.append("  ?,                                ");
		sql.append("  ?,                                ");
		sql.append("  ?,                                ");
		sql.append("  ?,                                ");
		sql.append("  ?,                                ");
		sql.append("  ?,                                ");
		sql.append("  ?,                                ");
		sql.append("  ?,                                ");
		sql.append("  ?,                                ");
		sql.append("  ?,                                ");
		sql.append("  ?,                                ");
		sql.append("  ?,                                ");
		sql.append("  ?,                                ");
		sql.append("  ?,                                ");
		sql.append("  ?,                                ");
		sql.append("  ?,                                ");
		sql.append("  ?,                                ");
		sql.append("  ?                                 ");
		sql.append(")                                   ");
		                                        		
		String id = UUID.randomUUID().toString().replace("-", "");
		if(record.getId()==null || record.getId().isEmpty()) {
            record.setId(id);
        }
		if(record.getExpire_time()==null) {
            record.setExpire_time(new Date());
        }
		if(record.getPayment_timeout()==null) {
            record.setPayment_timeout(new Date());
        }
		if(record.getPayment_time()==null) {
            record.setPayment_time(new Date());
        }
		if(record.getCreated_time()==null) {
            record.setCreated_time(new Date());
        }
		if(record.getUpdated_time()==null) {
            record.setUpdated_time(new Date());
        }
		if(record.getOrder_no()==null || record.getOrder_no().isEmpty()) {
            record.setOrder_no(GenerateSequenceUtil.generateSequenceNo());
        }
		
		super.update(
			JdbcUtil.getCurrentConnection(), 
			sql.toString(), 
			record.getId() 										,      
			record.getBuyer_id() 								,      
			record.getBuyer_name() 								,      
			record.getBuyer_mobile() 							,      
			record.getSeller_id() 								,  
			record.getSeller_name() 							,      
			record.getSeller_mobile() 							,  
			record.getShop_id() 								,      
			record.getShop_name() 								,  
			record.getOrder_status() 							,      
			record.getOrigin_fee() 								,      
			record.getActure_fee() 								,      
			record.getCoupon_price() 							,      
			DateUtil.formatDate(record.getExpire_time()) 		,
			DateUtil.formatDate(record.getPayment_timeout()) 	,      
			DateUtil.formatDate(record.getPayment_time()) 		,      
			record.getIs_deleted() 								,      
			DateUtil.formatDate(record.getCreated_time()) 		, 
			record.getOrder_no()								,
			DateUtil.formatDate(record.getUpdated_time()) 	           
			);				
		return 0;
	}

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	public int insertSelective( WxcpOrderInfo record ) throws SQLException {
		return -1;
	}

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	public int updateByPrimaryKeySelective( WxcpOrderInfo record ) throws SQLException {
		return -1;
	}

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	public int updateByPrimaryKey ( WxcpOrderInfo record ) throws SQLException {
		return -1;
	}

	/**
	 * 
	 * 查询买家对应的订单信息；
	 * 
	 **/
	public List<WxcpOrderInfo> getListWxcpOrderInfo(WxcpOrderInfo condition) throws SQLException {
		
		StringBuilder sql = new StringBuilder();
		sql.append("select 						");
		sql.append("	id 				,		");
		sql.append("	buyer_id 		,   	");
		sql.append("	buyer_name      ,   	");
		sql.append("	buyer_mobile    ,   	");
		sql.append("	seller_id       ,   	");
		sql.append("	seller_name     ,   	");
		sql.append("	seller_mobile   ,   	");
		sql.append("	shop_id         ,   	");
		sql.append("	shop_name       ,   	");
		sql.append("	order_status 	,   	");
		sql.append("	origin_fee 		,   	");
		sql.append("	acture_fee 		,   	");
		sql.append("	coupon_price 	,   	");
		sql.append("	expire_time 	,   	");
		sql.append("	payment_timeout ,   	");
		sql.append("	payment_time 	,   	");
		sql.append("	is_deleted 		,   	");
		sql.append("	created_time 	,   	");
		sql.append("	order_no 		,   	");
		sql.append("	updated_time 	    	");		
		sql.append("from                   		");
		sql.append("	wxcp_order_info   		");
		sql.append("where                  		");
		sql.append("	1 = 1               	");	
		if(condition.getId() != null && !condition.getId().isEmpty()) {
            sql.append("	and id = ?    			");
        }
		if(condition.getBuyer_id() != null && !condition.getBuyer_id().isEmpty()) {
            sql.append("	and buyer_id = ?    	");
        }
		if(condition.getOrder_status() != null && condition.getOrder_status() > 0 ) {
            sql.append("   	and order_status = ? 	");
        }
		sql.append("order by    				");
		sql.append("	created_time desc		");

		ArrayList<Object> conList = new ArrayList<Object>(); 
		if(condition.getId() != null && !condition.getId().isEmpty()) {
            conList.add(condition.getId());
        }
		if(condition.getBuyer_id() != null && !condition.getBuyer_id().isEmpty()) {
            conList.add(condition.getBuyer_id());
        }
		if(condition.getOrder_status() != null && condition.getOrder_status() > 0) {
            conList.add(condition.getOrder_status());
        }
		
		Object[] params = new Object [conList.size()];
		conList.toArray(params);				
		
		return super.query(JdbcUtil.getCurrentConnection(), sql.toString(),new BeanListHandler<>(WxcpOrderInfo.class),params);
	}
	
}