package com.xczh.consumer.market.dao;

import com.xczh.consumer.market.bean.WxcpOrderPay;
import com.xczh.consumer.market.utils.DateUtil;
import com.xczh.consumer.market.utils.JdbcUtil;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

//import org.apache.commons.dbutils.handlers.BeanHandler;
//import com.xczh.consumer.market.bean.WxcpGoodsInfo;
//import com.xczh.consumer.market.bean.WxcpIntervalImg;
//import com.xczh.consumer.market.bean.WxcpOrderInfo;

/**
 * 
 * WxcpOrderPayMapper数据库操作接口类
 * @author yanghui
 **/
@Repository
public class WxcpOrderPayMapper extends BasicSimpleDao{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	public WxcpOrderPay selectByPrimaryKey (String id ) throws SQLException {
		return null;
	}

	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	public int deleteByPrimaryKey ( String id ) throws SQLException {
		return -1;
	}

	/**
	 * 
	 * 添加
	 * 
	 **/
	public int insert( WxcpOrderPay record ) throws SQLException {
		
		StringBuilder sql = new StringBuilder();
		sql.append("insert into wxcp_order_pay 			");
		sql.append("(	                               	");
		sql.append("   pay_id 			,               ");
		sql.append("   order_id			,               ");
		sql.append("   payment_type		,               ");
		sql.append("   amount			,               ");
		sql.append("   pay_user_id		,               ");
		sql.append("   create_datetime	,               ");
		sql.append("   pay_description	,               ");
		sql.append("   flow_id			                ");
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
		sql.append("  ?                                 ");
		sql.append(")                                   ");
		                                        
		
		String id = UUID.randomUUID().toString().replace("-", "");
		if(record.getPay_id()==null || record.getPay_id().isEmpty()) {
            record.setPay_id(id);
        }
		if(record.getCreate_datetime()==null) {
            record.setCreate_datetime(new Date());
        }
		
		super.update(
				JdbcUtil.getCurrentConnection(),
				sql.toString(), 
				record.getPay_id() 									,      
				record.getOrder_id() 								,
				record.getPayment_type() 							,  
				record.getAmount() 									,  
				record.getPay_user_id() 							,  
				DateUtil.formatDate(record.getCreate_datetime()) 	,
				record.getPay_description() 						,      
				record.getFlow_id() 								
				);
		
		return 0;
	}

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	public int insertSelective( WxcpOrderPay record ) throws SQLException {
		return -1;
	}

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	public int updateByPrimaryKeySelective( WxcpOrderPay record ) throws SQLException {
		return -1;
	}

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	public int updateByPrimaryKey ( WxcpOrderPay record ) throws SQLException {
		return -1;
	}

	/**
	 * 
	 * 查询订单对应的支付流水单信息；
	 * 
	 **/
	public List<WxcpOrderPay> getListWxcpOrderPay(WxcpOrderPay condition) throws SQLException {
		
		StringBuilder sql = new StringBuilder();
		sql.append("select 					");			
		sql.append("	pay_id 			,  	");
		sql.append("	order_id 		,  	");
		sql.append("	payment_type 	,  	");
		sql.append("	amount 			,  	");
		sql.append("	pay_user_id 	,  	");
		sql.append("	create_datetime ,   ");
		sql.append("	pay_description ,  	");
		sql.append("	flow_id 		   	");		
		sql.append("from                   	");
		sql.append("	wxcp_order_pay   	");
		sql.append("where                  	");
		sql.append("	1 = 1               ");	
		sql.append("	and order_id = ?    ");	
		
		Object params[] = {condition.getOrder_id()};
		return super.query(JdbcUtil.getCurrentConnection(), sql.toString(),new BeanListHandler<>(WxcpOrderPay.class),params);
	}
	
}