package com.xczh.consumer.market.dao;

import com.xczh.consumer.market.bean.WxcpWxTrans;
import com.xczh.consumer.market.utils.JdbcUtil;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class WxcpWxTransMapper extends BasicSimpleDao {
	
	public int insert( WxcpWxTrans record ) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into wxcp_wx_trans 			");
		sql.append("(	                               	");
		sql.append("   		trans_id 			,       ");
		sql.append("   		mch_appid 			,       ");
		sql.append("   		mchid 				,       ");
		sql.append("   		device_info 		,       ");
		sql.append("   		nonce_str 			,       ");
		sql.append("   		sign 				,       ");
		sql.append("   		partner_trade_no 	,       ");
		sql.append("   		openid 			    ,       ");
		sql.append("   		re_user_name 		,       ");
		sql.append("   		amount 			    ,       ");
		sql.append("   		desc 				,       ");
		sql.append("   		spbill_create_ip 	,       ");
		sql.append("   		return_code 		,       ");
		sql.append("   		result_code 		,       ");
		sql.append("   		return_msg 		    ,       ");
		sql.append("   		payment_no 		    ,       ");
		sql.append("   		payment_time 		        ");
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
		sql.append("  ?                                 ");
		sql.append(")                                   ");
		                                        		
		String id = UUID.randomUUID().toString().replace("-", "");
		if(record.getTrans_id()==null || record.getTrans_id().isEmpty()) {
            record.setTrans_id(id);
        }
		//if(record.getCreate_time()==null) record.setCreate_time(new Date());
		
		super.update(
			JdbcUtil.getCurrentConnection(),
			sql.toString					(), 			
			record.getTrans_id 			    (),
			record.getMch_appid 			(),
			record.getMchid 				(),
			record.getDevice_info 		    (),
			record.getNonce_str 			(),
			record.getSign 				    (),
			record.getPartner_trade_no 	    (),
			record.getOpenid 			    (),
			record.getRe_user_name 		    (),
			record.getAmount 			    (),
			record.getDesc 				    (),
			record.getSpbill_create_ip 	    (),
			record.getReturn_code 		    (),
			record.getResult_code 		    (),
			record.getReturn_msg 		    (),
			record.getPayment_no 		    (),
			record.getPayment_time 		    ()
			);				
		
		return 0;
	}

	public int delete( String id ) throws SQLException {			
		return -1;
	}

	public int update( WxcpWxTrans record ) throws SQLException {
		return -1;
	}

	public List<WxcpWxTrans> select(WxcpWxTrans condition, String limit) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("select 									");
		sql.append("   	trans_id 			,               ");
		sql.append("   	mch_appid 			,               ");
		sql.append("   	mchid 				,               ");
		sql.append("   	device_info 		,               ");
		sql.append("   	nonce_str 			,               ");
		sql.append("   	sign 				,               ");
		sql.append("   	partner_trade_no 	,               ");
		sql.append("   	openid 			    ,               ");
		sql.append("   	re_user_name 		,               ");
		sql.append("   	amount 			    ,               ");
		sql.append("   	desc 				,               ");
		sql.append("   	spbill_create_ip 	,               ");
		sql.append("   	return_code 		,               ");
		sql.append("   	result_code 		,               ");
		sql.append("   	return_msg 		    ,               ");
		sql.append("   	payment_no 		    ,               ");
		sql.append("   	payment_time 		               	");
		sql.append("from                   					");
		sql.append("	wxcp_wx_trans   					");
		sql.append("where                  					");
		sql.append("	1 = 1               				");	
		if(condition.getTrans_id() != null && !condition.getTrans_id().isEmpty()) {
            sql.append("	and trans_id = ?    				");
        }
		if(condition.getOpenid() != null && !condition.getOpenid().isEmpty()) {
            sql.append("	and openid = ?    			");
        }
		//sql.append("order by    						");
		//sql.append("	create_time desc				");
		if(limit!=null && limit.length() > 0) {
            sql.append("	limit " + limit);
        }
		
		ArrayList<Object> conList = new ArrayList<Object>(); 
		if(condition.getTrans_id() != null && !condition.getTrans_id().isEmpty()) {
            conList.add(condition.getTrans_id());
        }
		if(condition.getOpenid() != null && !condition.getOpenid().isEmpty()) {
            conList.add(condition.getOpenid());
        }
		
		Object[] params = new Object [conList.size()];
		conList.toArray(params);				
		
		return super.query(JdbcUtil.getCurrentConnection(), sql.toString(),new BeanListHandler<>(WxcpWxTrans.class),params);
	}	
}
