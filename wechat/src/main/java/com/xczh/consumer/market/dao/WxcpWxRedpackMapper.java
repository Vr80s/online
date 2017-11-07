package com.xczh.consumer.market.dao;

import com.xczh.consumer.market.utils.DateUtil;
import com.xczh.consumer.market.utils.JdbcUtil;
import com.xczh.consumer.market.wxpay.entity.SendRedPack;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public class WxcpWxRedpackMapper extends BasicSimpleDao {
	
	public int insert( SendRedPack record ) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into wxcp_wx_redpack 		");
		sql.append("(	                               	");
		sql.append("   	rp_id			,               ");
		sql.append("   	nonce_str		,               ");
		sql.append("   	sign			,               ");
		sql.append("   	mch_billno		,               ");
		sql.append("   	mch_id			,               ");
		sql.append("   	wxappid			,               ");
		sql.append("   	send_name		,               ");
		sql.append("   	re_openid		,               ");
		sql.append("   	total_amount	,               ");
		sql.append("   	total_num		,               ");
		sql.append("   	wishing			,               ");
		sql.append("   	client_ip		,               ");
		sql.append("   	act_name		,               ");
		sql.append("   	remark			,               ");
		sql.append("   	sub_mch_id		,               ");
		sql.append("   	nick_name		,               ");
		sql.append("   	min_value		,               ");
		sql.append("   	max_value		,               ");
		sql.append("   	logo_imgurl		,               ");
		sql.append("   	share_content	,               ");
		sql.append("   	share_url		,               ");
		sql.append("   	share_imgurl	,               ");
		sql.append("   	return_code	,               	");
		sql.append("   	result_code	,               	");
		sql.append("   	return_msg	,               	");
		sql.append("   	create_time		                ");
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
		sql.append("  ?,                                ");
		sql.append("  ?,                                ");
		sql.append("  ?,                                ");
		sql.append("  ?,                                ");
		sql.append("  ?,                                ");
		sql.append("  ?                                 ");
		sql.append(")                                   ");
		                                        		
		String id = UUID.randomUUID().toString().replace("-", "");
		if(record.getRp_id()==null || record.getRp_id().isEmpty()) record.setRp_id(id);
		if(record.getCreate_time()==null) record.setCreate_time(new Date());
		
		super.update(
			JdbcUtil.getCurrentConnection(),
			sql.toString			(), 			
			record.getRp_id			(),
			record.getNonce_str		(),
			record.getSign			(),
			record.getMch_billno	(),
			record.getMch_id		(),
			record.getWxappid		(),
			record.getSend_name		(),
			record.getRe_openid		(),
			record.getTotal_amount	(),
			record.getTotal_num		(),
			record.getWishing		(),
			record.getClient_ip		(),
			record.getAct_name		(),
			record.getRemark		(),
			record.getSub_mch_id	(),
			record.getNick_name		(),
			record.getMin_value		(),
			record.getMax_value		(),
			record.getLogo_imgurl	(),
			record.getShare_content	(),
			record.getShare_url		(),
			record.getShare_imgurl	(),
			record.getReturn_code	(),	
			record.getResult_code	(),
			record.getReturn_msg	(),	
			DateUtil.formatDate(record.getCreate_time())
			);				
		
		return 0;
	}

	public int delete( String id ) throws SQLException {			
		return -1;
	}

	public int update( SendRedPack record ) throws SQLException {
		return -1;
	}

	public List<SendRedPack> select(SendRedPack condition, String limit) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("select 								");
		sql.append("   	rp_id			,               ");
		sql.append("   	nonce_str		,               ");
		sql.append("   	sign			,               ");
		sql.append("   	mch_billno		,               ");
		sql.append("   	mch_id			,               ");
		sql.append("   	wxappid			,               ");
		sql.append("   	send_name		,               ");
		sql.append("   	re_openid		,               ");
		sql.append("   	total_amount	,               ");
		sql.append("   	total_num		,               ");
		sql.append("   	wishing			,               ");
		sql.append("   	client_ip		,               ");
		sql.append("   	act_name		,               ");
		sql.append("   	remark			,               ");
		sql.append("   	sub_mch_id		,               ");
		sql.append("   	nick_name		,               ");
		sql.append("   	min_value		,               ");
		sql.append("   	max_value		,               ");
		sql.append("   	logo_imgurl		,               ");
		sql.append("   	share_content	,               ");
		sql.append("   	share_url		,               ");
		sql.append("   	share_imgurl	,               ");
		sql.append("   	return_code	,               	");
		sql.append("   	result_code	,               	");
		sql.append("   	return_msg	,               	");
		sql.append("   	create_time		                ");		
		sql.append("from                   				");
		sql.append("	wxcp_wx_redpack   				");
		sql.append("where                  				");
		sql.append("	1 = 1               			");	
		if(condition.getRp_id() != null && !condition.getRp_id().isEmpty())
		sql.append("	and rp_id = ?    				");	
		if(condition.getRe_openid() != null && !condition.getRe_openid().isEmpty())
		sql.append("	and re_openid = ?    			");
		if(condition.getWxappid() != null && !condition.getWxappid().isEmpty() )
		sql.append("   	and wxappid = ? 				");	
		sql.append("order by    						");
		sql.append("	create_time desc				");
		if(limit!=null && limit.length() > 0) 
		sql.append("	limit " + limit);
		
		ArrayList<Object> conList = new ArrayList<Object>(); 
		if(condition.getRp_id() != null && !condition.getRp_id().isEmpty())
			conList.add(condition.getRp_id());
		if(condition.getRe_openid() != null && !condition.getRe_openid().isEmpty())
			conList.add(condition.getRe_openid());	
		if(condition.getWxappid() != null && !condition.getWxappid().isEmpty() )
			conList.add(condition.getWxappid());	
		
		Object[] params = new Object [conList.size()];
		conList.toArray(params);				
		
		return super.query(JdbcUtil.getCurrentConnection(), sql.toString(),new BeanListHandler<>(SendRedPack.class),params);
	}
	
}
