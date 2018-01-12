package com.xczh.consumer.market.dao;

import com.xczh.consumer.market.bean.WxcpPayFlow;
import com.xczh.consumer.market.utils.JdbcUtil;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//import java.util.Date;
//import com.xczh.consumer.market.utils.GenerateSequenceUtil;
//import com.xczh.distributed.common.utils.DateUtil;
//import org.apache.commons.dbutils.handlers.BeanHandler;

@Repository
public class WxcpPayFlowMapper extends BasicSimpleDao {
	
	public List<WxcpPayFlow> select(WxcpPayFlow condition) throws SQLException {
		
		StringBuilder sql = new StringBuilder();
		sql.append("select 					");
		sql.append("	flow_id,         	");
		sql.append("	appid,            	");
		sql.append("	attach,            	");
		sql.append("	bank_type,         	");
		sql.append("	fee_type,          	");
		sql.append("	is_subscribe,      	");
		sql.append("	mch_id,            	");
		sql.append("	nonce_str,         	");
		sql.append("	openid,            	");
		sql.append("	out_trade_no,     	");
		sql.append("	result_code,      	");
		sql.append("	return_code,      	");
		sql.append("	sign,             	");
		sql.append("	sub_mch_id,       	");
		sql.append("	time_end,          	");
		sql.append("	total_fee,        	");
		sql.append("	trade_type,       	");
		sql.append("	transaction_id,   	");
		sql.append("	payment_type        ");
		sql.append("from                   	");
		sql.append("	wxcp_pay_flow   	");
		sql.append("where                  	");
		sql.append("	1 = 1               ");	
		if(condition.getOpenid() != null && !condition.getOpenid().isEmpty()) {
            sql.append("	and openid = ?    ");
        }
		if(condition.getOut_trade_no() != null && !condition.getOut_trade_no().isEmpty()) {
            sql.append("	and out_trade_no = ?    ");
        }
		sql.append("order by    			");
		sql.append("	date_format(time_end,'%Y-%m-%d %H:%i:%s') desc	");

		ArrayList<String> conList = new ArrayList<String>(); 
		if(condition.getOpenid() != null && !condition.getOpenid().isEmpty()) {
            conList.add(condition.getOpenid());
        }
		if(condition.getOut_trade_no() != null && !condition.getOut_trade_no().isEmpty()) {
            conList.add(condition.getOut_trade_no());
        }
		
		Object[] params = new Object [conList.size()];
		conList.toArray(params);				
		
		return super.query(JdbcUtil.getCurrentConnection(), sql.toString(),new BeanListHandler<>(WxcpPayFlow.class),params);
	}
	
	public int insert(WxcpPayFlow record) throws SQLException {
		
		StringBuilder sql = new StringBuilder();
		sql.append("insert into wxcp_pay_flow 			");
		sql.append("(	                               	");
		sql.append("   	flow_id,               			");
		sql.append("   	appid,               			");
		sql.append("   	attach,               			");
		sql.append("   	bank_type,               		");
		sql.append("   	fee_type,               		");
		sql.append("   	is_subscribe,               	");
		sql.append("   	mch_id,               			");
		sql.append("   	nonce_str,               		");
		sql.append("   	openid,               			");
		sql.append("   	out_trade_no,               	");
		sql.append("   	result_code,               		");
		sql.append("   	return_code,               		");
		sql.append("   	sign,               			");
		sql.append("   	sub_mch_id,               		");
		sql.append("   	time_end,               		");
		sql.append("   	total_fee,               		");
		sql.append("   	trade_type,               		");
		sql.append("   	transaction_id,               	");
		sql.append("   	user_id,               	");
		sql.append("   	subject,               	");
		sql.append("   	payment_type               		");
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
		sql.append("  ?,                                ");
		sql.append("  ?                                 ");
		sql.append(")                                   ");
		                                        		
		String id = UUID.randomUUID().toString().replace("-", "");
		if(record.getFlow_id()==null || record.getFlow_id().isEmpty()) {
            record.setFlow_id(id);
        }
		
		super.update(
			JdbcUtil.getCurrentConnection()	, 
			sql.toString()					, 
			record.getFlow_id()				,           
			record.getAppid()				,             
			record.getAttach()				,            
			record.getBank_type()			,         
			record.getFee_type()			,          
			record.getIs_subscribe()		,      
			record.getMch_id()				,            
			record.getNonce_str()			,         
			record.getOpenid()				,            
			record.getOut_trade_no()		,      
			record.getResult_code()			,       
			record.getReturn_code()			,       
			record.getSign()				,              
			record.getSub_mch_id()			,        
			record.getTime_end()			,          
			record.getTotal_fee()			,         
			record.getTrade_type()			,        
			record.getTransaction_id()		,
			record.getUser_id(),
				record.getSubject(),
			record.getPayment_type()       
			);				
		return 0;
	}
	
	public int delete(WxcpPayFlow wxcpPayFlow) throws SQLException {
		return -1;
	}

	public int update(WxcpPayFlow wxcpPayFlow) throws SQLException {
		return -1;
	}
}
