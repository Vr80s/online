package com.xczh.consumer.market.dao;

import com.xczh.consumer.market.bean.WxcpClientUserWxMapping;
import com.xczh.consumer.market.utils.DateUtil;
import com.xczh.consumer.market.utils.JdbcUtil;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

/**
 * 
 * WxcpClientUserWxMappingMapper数据库操作接口类
 * @author yanghui
 **/
@Repository
public class WxcpClientUserWxMappingMapper extends BasicSimpleDao{
	
	/**
	 * 根据用户id，查询微信信息
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public WxcpClientUserWxMapping getWxcpClientUserWxMappingByUserId(String userId) throws SQLException{
		String sql = "select * from wxcp_client_user_wx_mapping where client_id = ? ";
		Object params[] = {userId};
		return this.query(JdbcUtil.getCurrentConnection(), sql.toString(),new BeanHandler<>(WxcpClientUserWxMapping.class),params);
	}
	
	/**
	 * 根据opendId 查找 微信信息表
	 * @param openId
	 * @return
	 * @throws SQLException
	 */
	public WxcpClientUserWxMapping getWxcpClientUserWxMappingByOpenId(String openId) throws SQLException {
		if(openId==null) {
            return null;
        }
		String sql = "select * from wxcp_client_user_wx_mapping where openid = ? ";
		Object params[] = {openId};
		return this.query(JdbcUtil.getCurrentConnection(), sql.toString(),new BeanHandler<>(WxcpClientUserWxMapping.class),params);
	}

	/**
	 * 根据opendId 查找 微信信息表
	 * @return
	 * @throws SQLException
	 */
	public WxcpClientUserWxMapping getWxcpClientUserWxMappingInfo(String client_id, String openid, String wx_public_id) throws SQLException {
		if(client_id != null) {
			String sql = "select * from wxcp_client_user_wx_mapping where client_id = ? ";
			Object params[] = {client_id};
			return this.query(JdbcUtil.getCurrentConnection(), sql.toString(),new BeanHandler<>(WxcpClientUserWxMapping.class),params);
		} else if(openid != null && wx_public_id != null) {
			String sql = "select * from wxcp_client_user_wx_mapping where openid = ? and wx_public_id = ? ";
			Object params[] = {openid,wx_public_id};
			return this.query(JdbcUtil.getCurrentConnection(), sql.toString(),new BeanHandler<>(WxcpClientUserWxMapping.class),params);
		}
		else {
            return new WxcpClientUserWxMapping();
        }
	}
	
	/**
	 * 添加
	 **/
	public int insert( WxcpClientUserWxMapping record ) throws SQLException {
		
		if(record==null) {
            return -1;
        }
		
		WxcpClientUserWxMapping record_0  = this.getWxcpClientUserWxMappingInfo(record.getClient_id(),record.getOpenid(),record.getWx_public_id());
		if(record_0 == null) { 	
			
			
			/**
			 * Parameters: [4ff5f5046dd04a1b95bd40d4554fea84, null, null, 
			 * o3sGo0ggBrHS-s0BsbMQy-iprOlw, null, f..🍒, 2, Shijiazhuang, 
			 * CN, Hebei, zh_CN, http://wx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEIw1P8hjmojibQjOzkh3mibic120pqibhrmRvZ2R7oIS8WptQWpjhuo2clvxibayYTazAp0sfIkZZnnvMg/0,
			 *  2017-11-01 17:53:48, o4gO40i4UxVt1ekWdqStCYAAeQaU, null, null, wx48d230a99f1c20d9, 熊猫中医]
			 */
			StringBuilder sql = new StringBuilder();
			sql.append("insert into wxcp_client_user_wx_mapping 	");
			sql.append("(	                               			");
			sql.append("   	wx_id				,           		");
			sql.append("   	client_id			,           		");
			sql.append("   	subscribe			,           		");
			sql.append("   	openid				,           		");
			sql.append("   	openname			,           		");
			sql.append("   	nickname			,           		");
			sql.append("   	sex					,           		");
			sql.append("   	city				,           		");
			sql.append("   	country				,           		");
			sql.append("   	province			,           		");
			sql.append("   	language			,           		");
			sql.append("   	headimgurl			,           		");
			sql.append("   	subscribe_time		,           		");
			sql.append("   	unionid				,           		");
			sql.append("   	remark				,           		");
			sql.append("   	groupid				,           		");
			sql.append("   	wx_public_id		,           		");
			sql.append("   	wx_public_name	    ,            		");
			
			sql.append("   	tagid_list			,           		");
			sql.append("   	subscribe_scene			,           		");
			sql.append("   	qr_scene		,           		");
			sql.append("   	qr_scene_str				,           		");
			sql.append("   	create_time				,           		");
			sql.append("   	last_update_time				           		");
			
			sql.append(")                                   		");
			sql.append("values                              		");
			sql.append("(                                   		");
			sql.append("  ?,                                		");
			sql.append("  ?,                                		");
			sql.append("  ?,                                		");
			sql.append("  ?,                                		");
			sql.append("  ?,                                		");
			sql.append("  ?,                                		");
			sql.append("  ?,                                		");
			sql.append("  ?,                                		");
			sql.append("  ?,                                		");
			sql.append("  ?,                                		");
			sql.append("  ?,                                		");
			sql.append("  ?,                                		");
			sql.append("  ?,                                		");
			sql.append("  ?,                                		");
			sql.append("  ?,                                		");
			sql.append("  ?,                                		");
			sql.append("  ?,                                		");
			sql.append("  ?                                 		");
			sql.append(")                                   		");
			
			super.update(
				JdbcUtil.getCurrentConnection(), 
				sql.toString(), 
				record.getWx_id()								,      
				record.getClient_id()							,      
				record.getSubscribe()							,      
				record.getOpenid()								,      
				record.getOpenname()							,  
				record.getNickname()							,      
				record.getSex()									,  
				record.getCity()								,      
				record.getCountry()								,  
				record.getProvince()							,      
				record.getLanguage()							,      
				record.getHeadimgurl()							,      
				DateUtil.formatDate(record.getSubscribe_time())	,     								
				record.getUnionid()								,      
				record.getRemark()								,      
				record.getGroupid()								,      
				record.getWx_public_id()						,      
				record.getWx_public_name()	,
				
				record.getTagid_list()							,      
				record.getSubscribe_scene()							,  
				record.getQr_scene()							,      
				record.getQr_scene_str()							,  
				record.getCreate_time()	,     								
				record.getLast_update_time()	     
				);	
			
		} else {
			
			record.setWx_id(record_0.getWx_id());
			this.update(record);			
		}
		
		return 0;
	}
	
	/**
	 * 
	 * 更新；
	 * 
	 **/
	public int update( WxcpClientUserWxMapping record ) throws SQLException {
		
		if(record==null) {
            return -1;
        }
		
		StringBuilder sql = new StringBuilder();
		sql.append("update  							");
		sql.append(" 	wxcp_client_user_wx_mapping 	");
		sql.append("set  				  				");
		sql.append("   		wx_id				= ?,   	");
		sql.append("   		client_id			= ?,   	");
		sql.append("   		subscribe			= ?,   	");
		sql.append("   		openid				= ?,   	");
		sql.append("   		openname			= ?,   	");
		sql.append("   		nickname			= ?,   	");
		sql.append("   		sex					= ?,   	");
		sql.append("   		city				= ?,   	");
		sql.append("   		country				= ?,   	");
		sql.append("   		province			= ?,   	");
		sql.append("   		language			= ?,   	");
		sql.append("   		headimgurl			= ?,   	");
		sql.append("   		subscribe_time		= ?,   	");
		sql.append("   		unionid				= ?,   	");
		sql.append("   		remark				= ?,   	");
		sql.append("   		groupid				= ?,   	");
		sql.append("   		wx_public_id		= ?,    ");
		sql.append("   		wx_public_name		= ?,     ");
		
		
		sql.append("   	tagid_list			,           		");
		sql.append("   	subscribe_scene			,           		");
		sql.append("   	qr_scene		,           		");
		sql.append("   	qr_scene_str				,           		");
		sql.append("   	create_time				,           		");
		sql.append("   	last_update_time				           		");
		
		
		
		
		sql.append("where                				");
		sql.append("	1 = 1  			   				");
		sql.append("	and wx_id 				= ?     ");
				                                        			
		super.update(
			JdbcUtil.getCurrentConnection(), 
			sql.toString(), 
			record.getWx_id()								,      
			record.getClient_id()							,      
			record.getSubscribe()							,      
			record.getOpenid()								,      
			record.getOpenname()							,  
			record.getNickname()							,      
			record.getSex()									,  
			record.getCity()								,      
			record.getCountry()								,  
			record.getProvince()							,      
			record.getLanguage()							,      
			record.getHeadimgurl()							,      
			DateUtil.formatDate(record.getSubscribe_time())	,
			record.getUnionid()								,      
			record.getRemark()								,      
			record.getGroupid()								,      
			record.getWx_public_id()						,      
			record.getWx_public_name()			  			,
			record.getWx_id()	,
			
			record.getTagid_list()							,      
			record.getSubscribe_scene()							,  
			record.getQr_scene()							,      
			record.getQr_scene_str()							,  
			
			record.getCreate_time()	,     								
			record.getLast_update_time()
			
			);
		
		return 0;
	}

	/**
	 * Description：
	 * @param unionId
	 * @return
	 * @return WxcpClientUserWxMapping
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 * @throws SQLException 
	 */
	public WxcpClientUserWxMapping getWxcpClientUserByUnionId(String unionId) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "select * from wxcp_client_user_wx_mapping where unionid = ? ";
		Object params[] = {unionId};
		return this.query(JdbcUtil.getCurrentConnection(), sql.toString(),new BeanHandler<>(WxcpClientUserWxMapping.class),params);
	}

	public WxcpClientUserWxMapping getWxcpClientUserWxMappingByOpenId(
			String userId, String unionId) throws SQLException {
		String sql = "select * from wxcp_client_user_wx_mapping where client_id =? and unionid = ?  ";
		Object params[] = {userId,unionId};
		return this.query(JdbcUtil.getCurrentConnection(), sql.toString(),new BeanHandler<>(WxcpClientUserWxMapping.class),params);
	}

	public void deleteAccount(String userId) throws SQLException {
		StringBuilder sb = new StringBuilder("");
		sb.append("delete from  wxcp_client_user_wx_mapping  where client_id =? ");
		Object[] params = {userId};
		this.update(JdbcUtil.getCurrentConnection(), sb.toString(), params);
	}
		
}