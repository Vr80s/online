package com.xczhihui.bxg.online.web.dao;

import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.online.common.domain.WxcpPayFlow;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.UUID;


@Repository
public class WxcpPayFlowDao extends SimpleHibernateDao {

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

		return super.getNamedParameterJdbcTemplate().getJdbcOperations().update(
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
	}

}
