package com.xczh.consumer.market.dao;

import com.xczh.consumer.market.bean.WxcpConcernInfo;
import com.xczh.consumer.market.bean.WxcpWxJsconfig;
import com.xczh.consumer.market.utils.JdbcUtil;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * 
 * WxcpConcernInfoMapper微信公众号关联的用户表
 * @author yanghui
 **/
@Repository
public class WxcpConcernInfoMapper extends BasicSimpleDao {
	
	public int insert( WxcpConcernInfo record ) throws SQLException {
		if(record==null) return -1;
		
		StringBuilder sql = new StringBuilder();
		sql.append("insert into wxcp_concern_info 		");
		sql.append("(	                               	");
		sql.append("   concern_id		,               ");
		sql.append("   public_id		,               ");
		sql.append("   open_id		               		");
		sql.append(")                                   ");
		sql.append("values                              ");
		sql.append("(                                   ");
		sql.append("  					?,              ");
		sql.append("  					?,              ");
		sql.append("  					?               ");
		sql.append(")                                   ");
		                                        		
		String id = UUID.randomUUID().toString().replace("-", "");
		if(record.getConcern_id()==null || record.getConcern_id().isEmpty()) record.setConcern_id(id);
		
		super.update(
			JdbcUtil.getCurrentConnection(),
			sql.toString(), 
			record.getConcern_id() ,
			record.getPublic_id() , 
			record.getOpen_id() 								      			 		     
			);				
		
		return 0;
	}

	public int delete( WxcpConcernInfo condition ) throws SQLException {

		if(condition==null) return -1;
		
		StringBuilder sql = new StringBuilder();		
		sql.append("delete from wxcp_concern_info where 1 = 1 ");		
		if(condition.getConcern_id() != null && !condition.getConcern_id().isEmpty())
		sql.append("	and concern_id = ?    		");	
		if(condition.getPublic_id() != null && !condition.getPublic_id().isEmpty())
		sql.append("	and public_id = ?    		");
		if(condition.getOpen_id() != null && !condition.getOpen_id().isEmpty() )
		sql.append("   	and open_id = ? 			");	
		
		ArrayList<Object> conList = new ArrayList<Object>(); 
		if(condition.getConcern_id() != null && !condition.getConcern_id().isEmpty())
			conList.add(condition.getConcern_id());
		if(condition.getPublic_id() != null && !condition.getPublic_id().isEmpty())
			conList.add(condition.getPublic_id());	
		if(condition.getOpen_id() != null && !condition.getOpen_id().isEmpty() )
			conList.add(condition.getOpen_id());	
		
		Object[] params = new Object [conList.size()];
		conList.toArray(params);				
		
		return super.update(JdbcUtil.getCurrentConnection(),sql.toString(), params);
	}

	public int update( WxcpWxJsconfig record ) throws SQLException {
		return -1;
	}

	public List<WxcpConcernInfo> select(WxcpConcernInfo condition, String limit) throws SQLException {
		
		if(condition==null) return new LinkedList<WxcpConcernInfo>();;
		
		StringBuilder sql = new StringBuilder();
		sql.append("select 							");
		sql.append("	concern_id	 	,			");
		sql.append("	public_id 		,   		");
		sql.append("	open_id      	   			");
		sql.append("from                   			");
		sql.append("	wxcp_concern_info   		");
		sql.append("where                  			");
		sql.append("	1 = 1               		");	
		if(condition.getConcern_id() != null && !condition.getConcern_id().isEmpty())
		sql.append("	and concern_id = ?    		");	
		if(condition.getPublic_id() != null && !condition.getPublic_id().isEmpty())
		sql.append("	and public_id = ?    		");
		if(condition.getOpen_id() != null && !condition.getOpen_id().isEmpty() )
		sql.append("   	and open_id = ? 			");	
		if(limit!=null && limit.length() > 0) 
		sql.append("	limit " + limit);
		
		ArrayList<Object> conList = new ArrayList<Object>(); 
		if(condition.getConcern_id() != null && !condition.getConcern_id().isEmpty())
			conList.add(condition.getConcern_id());
		if(condition.getPublic_id() != null && !condition.getPublic_id().isEmpty())
			conList.add(condition.getPublic_id());	
		if(condition.getOpen_id() != null && !condition.getOpen_id().isEmpty() )
			conList.add(condition.getOpen_id());	
		
		Object[] params = new Object [conList.size()];
		conList.toArray(params);				
		
		return super.query(JdbcUtil.getCurrentConnection(), sql.toString(),new BeanListHandler<>(WxcpConcernInfo.class),params);
	}
	
}
