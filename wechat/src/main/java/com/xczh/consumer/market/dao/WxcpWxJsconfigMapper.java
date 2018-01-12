package com.xczh.consumer.market.dao;

import com.xczh.consumer.market.bean.WxcpWxJsconfig;
import com.xczh.consumer.market.utils.DateUtil;
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
 * WxcpWxJsconfigMapper数据库操作接口类
 * @author yanghui
 **/
@Repository
public class WxcpWxJsconfigMapper extends BasicSimpleDao {
	
	public int insert( WxcpWxJsconfig record ) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into wxcp_wx_jsconfig 		");
		sql.append("(	                               	");
		sql.append("   id				,               ");
		sql.append("   appid			,               ");
		sql.append("   timestamp		,               ");
		sql.append("   nonce_str		,               ");
		sql.append("   signature		,               ");
		sql.append("   openid			,               ");
		sql.append("   access_token		,               ");
		sql.append("   jsapi_ticket		,               ");
		sql.append("   jsconfig_url		,               ");
		sql.append("   create_time		                ");
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
		sql.append("  ?                                 ");
		sql.append(")                                   ");
		                                        		
		String id = UUID.randomUUID().toString().replace("-", "");
		if(record.getId()==null || record.getId().isEmpty()) {
            record.setId(id);
        }
		if(record.getCreate_time()==null) {
            record.setCreate_time(new Date());
        }
		
		super.update(
			JdbcUtil.getCurrentConnection(),
			sql.toString(), 
			record.getId() 									,      
			record.getAppid() 								,      
			record.getTimestamp() 							,      
			record.getNonce_str() 							,      
			record.getSignature() 							,  
			record.getOpenid() 								,      
			record.getAccess_token() 						,  
			record.getJsapi_ticket() 						,      
			record.getJsconfig_url() 						,  
			DateUtil.formatDate(record.getCreate_time())
			);				
		
		return 0;
	}

	public int delete( String id ) throws SQLException {			
		return -1;
	}

	public int update( WxcpWxJsconfig record ) throws SQLException {
		return -1;
	}

	public List<WxcpWxJsconfig> select(WxcpWxJsconfig condition, String limit) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("select 						");
		sql.append("	id	 			,		");
		sql.append("	appid 			,   	");
		sql.append("	timestamp      	,   	");
		sql.append("	nonce_str    	,   	");
		sql.append("	signature       ,   	");
		sql.append("	openid     		,   	");
		sql.append("	access_token   	,   	");
		sql.append("	jsapi_ticket  	,   	");
		sql.append("	jsconfig_url  	,   	");
		sql.append("	create_time 		   	");
		sql.append("from                   		");
		sql.append("	wxcp_wx_jsconfig   		");
		sql.append("where                  		");
		sql.append("	1 = 1               	");	
		if(condition.getId() != null && !condition.getId().isEmpty()) {
            sql.append("	and id = ?    			");
        }
		if(condition.getAppid() != null && !condition.getAppid().isEmpty()) {
            sql.append("	and appid = ?    		");
        }
		if(condition.getOpenid() != null && !condition.getOpenid().isEmpty() ) {
            sql.append("   	and openid = ? 			");
        }
		sql.append("order by    				");
		sql.append("	create_time desc		");
		if(limit!=null && limit.length() > 0) {
            sql.append("	limit " + limit);
        }
		
		ArrayList<Object> conList = new ArrayList<Object>(); 
		if(condition.getId() != null && !condition.getId().isEmpty()) {
            conList.add(condition.getId());
        }
		if(condition.getAppid() != null && !condition.getAppid().isEmpty()) {
            conList.add(condition.getAppid());
        }
		if(condition.getOpenid() != null && !condition.getOpenid().isEmpty() ) {
            conList.add(condition.getOpenid());
        }
		
		Object[] params = new Object [conList.size()];
		conList.toArray(params);				
		
		return super.query(JdbcUtil.getCurrentConnection(), sql.toString(),new BeanListHandler<>(WxcpWxJsconfig.class),params);
	}
	
}
