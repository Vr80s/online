package com.xczh.consumer.market.dao;

import com.xczh.consumer.market.bean.WxcpClientUser;
import com.xczh.consumer.market.utils.DateUtil;
import com.xczh.consumer.market.utils.JdbcUtil;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * WxcpClientUserMapper数据库操作接口类
 * @author yanghui
 **/
@Repository
public class WxcpClientUserMapper extends BasicSimpleDao{
	
	/**
	 * 根据用户id查找用户
	 * @param clientId
	 * @return
	 * @throws SQLException 
	 */
	public WxcpClientUser getWxcpClientUser(String clientId) throws SQLException{
		if(clientId==null) {
            return null;
        }
		StringBuffer sql = new StringBuffer();
		sql.append(" select user.user_id as user_id,user.user_name as user_name, ");
		sql.append(" user.user_nick_name as user_nick_name,user.openname as openname, ");
		sql.append(" user.user_mobile as user_mobile,user.city as city,user.country as country, ");
		sql.append(" user.province as province,user.user_birthday as user_birthday,user_sex as user_sex ");
		sql.append(" from wxcp_client_user as user where user_status = 1 and user_id = ? ");
		Object params[] = {clientId};
		return this.query(JdbcUtil.getCurrentConnection(), sql.toString(),new BeanHandler<>(WxcpClientUser.class),params);
	}
	
	/**
	 * 根据用户手机号查找用户
	 * @param user_mobile
	 * @return
	 * @throws SQLException 
	 */
	public WxcpClientUser getWxcpClientUserByMobile(String user_mobile) throws SQLException{
		if(user_mobile==null) {
            return null;
        }
		String sql = "select * from wxcp_client_user where user_status = 1 and user_mobile = ? ";
		Object params[] = {user_mobile};
		return this.query(JdbcUtil.getCurrentConnection(), sql.toString(),new BeanHandler<>(WxcpClientUser.class),params);
	}

	/**
	 * 根据openid查找用户
	 * @param user_mobile
	 * @return
	 * @throws SQLException 
	 */
	public WxcpClientUser getWxcpClientUserByOpenid(String openid) throws SQLException{
		if(openid==null) {
            return null;
        }
		String sql = "select * from wxcp_client_user where user_status = 1 and openid = ? ";
		Object params[] = {openid};
		return this.query(JdbcUtil.getCurrentConnection(), sql.toString(),new BeanHandler<>(WxcpClientUser.class),params);
	}
	
	/**
	 * 更新用户信息
	 * @param user
	 * @throws SQLException
	 */
	public void updateWxcpClientUser(WxcpClientUser user) throws SQLException {
		if(user==null) {
            return;
        }
		String sql = "update wxcp_client_user set user_nick_name = ? , openname = ? "
				+ ", user_mobile= ? , user_sex = ? ,user_birthday = ? where user_status = 1 and user_id = ? ";
		Object params[] = {user.getUser_nick_name(),user.getOpenname(),user.getUser_mobile(),
				user.getUser_sex(),user.getUser_birthday(),user.getUser_id()};
		this.update(JdbcUtil.getCurrentConnection(),sql, params);
	}
	
	/**
	 * 添加
	 **/
	public int insert( WxcpClientUser user ) throws SQLException {
		
		if(user==null) {
            return -1;
        }
		
		WxcpClientUser user_0  = this.getWxcpClientUserByMobile(user.getUser_mobile());
		if(user_0 == null) { 	
			
			StringBuilder sql = new StringBuilder();
			sql.append("insert into wxcp_client_user 		");
			sql.append("(	                               	");
			sql.append("   	user_id				,           ");
			sql.append("   	user_name			,           ");
			sql.append("   	user_nick_name		,           ");
			sql.append("   	user_password		,           ");
			sql.append("   	openname			,           ");
			sql.append("   	user_mobile			,           ");
			sql.append("   	user_status			,           ");
			sql.append("   	city				,           ");
			sql.append("   	country				,           ");
			sql.append("   	province			,           ");
			sql.append("   	user_birthday		,           ");
			sql.append("   	user_sex			,           ");
			sql.append("   	face_image_id		,           ");
			sql.append("   	create_time			,           ");
			sql.append("   	user_email			,           ");
			sql.append("   	openid				,           ");
			sql.append("   	wx_public_id		,           ");
			sql.append("   	wx_public_name		,           ");
			sql.append("   	user_identifyId	                ");
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
			sql.append("  ?                                 ");
			sql.append(")                                   ");
			
			String id = UUID.randomUUID().toString().replace("-", "");
			if(user.getUser_id()==null || user.getUser_id().isEmpty()) {
                user.setUser_id(id);
            }
			if(user.getCreate_time()==null) {
                user.setCreate_time(new Date());
            }
			
			super.update(
				JdbcUtil.getCurrentConnection(), 
				sql.toString(), 
				user.getUser_id()									,      
				user.getUser_name()		    						,      
				user.getUser_nick_name()							,      
				user.getUser_password()	    						,      
				user.getOpenname()		    						,  
				user.getUser_mobile()								,      
				user.getUser_status()								,  
				user.getCity()			    						,      
				user.getCountry()									,  
				user.getProvince()		    						,      
				user.getUser_birthday()	    						,      
				user.getUser_sex()		    						,      
				user.getFace_image_id()	    						,     								
				DateUtil.formatDate(user.getCreate_time())			,
				user.getUser_email() 								,
				user.getOpenid()									,
				user.getWx_public_id()								,
				user.getWx_public_name()							,
				user.getUser_identifyId()           
				);	
			
		} else {
			
			user.setUser_id(user_0.getUser_id());
			this.update(user);			
		}
		
		return 0;
	}
	
	/**
	 * 
	 * 更新；
	 * 
	 **/
	public int update( WxcpClientUser user ) throws SQLException {
		
		if(user==null) {
            return -1;
        }
		
		StringBuilder sql = new StringBuilder();
		sql.append("update  						");
		sql.append(" 	wxcp_client_user 			");
		sql.append("set  				  			");
		sql.append("   	user_id				= ?,   	");
		sql.append("   	user_name			= ?,   	");
		sql.append("   	user_nick_name		= ?,   	");
		sql.append("   	user_password		= ?,   	");
		sql.append("   	openname			= ?,   	");
		sql.append("   	user_mobile			= ?,   	");
		sql.append("   	user_status			= ?,   	");
		sql.append("   	city				= ?,   	");
		sql.append("   	country				= ?,   	");
		sql.append("   	province			= ?,   	");
		sql.append("   	user_birthday		= ?,    ");
		sql.append("   	user_sex			= ?,    ");
		sql.append("   	face_image_id		= ?,    ");
		sql.append("   	create_time			= ?,    ");
		sql.append("   	user_email			= ?,    ");
		sql.append("   	openid				= ?,    ");
		sql.append("   	wx_public_id		= ?,    ");
		sql.append("   	wx_public_name		= ?,    ");
		sql.append("   	user_identifyId	    = ?     ");
		sql.append("where                			");
		sql.append("	1 = 1  			   			");
		sql.append("	and user_id = ?        		");
				                                        			
		super.update(
				JdbcUtil.getCurrentConnection(), 
				sql.toString(), 
				user.getUser_id()									,      
				user.getUser_name()		    						,      
				user.getUser_nick_name()							,      
				user.getUser_password()	    						,      
				user.getOpenname()		    						,  
				user.getUser_mobile()								,      
				user.getUser_status()								,  
				user.getCity()			    						,      
				user.getCountry()									,  
				user.getProvince()		    						,      
				user.getUser_birthday()	    						,      
				user.getUser_sex()		    						,      
				user.getFace_image_id()	    						,     								
				DateUtil.formatDate(user.getCreate_time())			,      
				user.getUser_email() 								,
				user.getOpenid()									,
				user.getWx_public_id()								,
				user.getWx_public_name()							,				
				user.getUser_identifyId()							,
				user.getUser_id()									
				);		
		
		return 0;
	}
	
	/**
	 * 根据用户手机号查找用户
	 * @param user_mobile
	 * @return
	 * @throws SQLException 
	 */
	public WxcpClientUser getCenterUserByUserName(String userName) throws SQLException{
		
		if(userName==null) {
            return null;
        }
		
		StringBuffer sql = new StringBuffer("");
		sql.append(" select user.user_id as user_id,user.user_name as user_name,user.user_password as user_password , ");
		sql.append(" user.user_nick_name as user_nick_name,user.openname as openname,user.user_status as user_status, ");
		sql.append(" user.user_mobile as user_mobile,user.city as city,user.country as country, ");
		sql.append(" user.openid as openid,user.wx_public_id as wx_public_id,user.wx_public_name as wx_public_name, ");
		sql.append(" user.province as province,user.user_birthday as user_birthday,user.user_sex as user_sex ");
		sql.append(" from wxcp_client_user as user where user.user_status = 1 and user.user_name = ?  ");
		Object params[] = {userName};
		return this.query(JdbcUtil.getCurrentConnection(), sql.toString(),new BeanHandler<>(WxcpClientUser.class),params);
	}
	
	/**
	 * 根据用户查找用户信息
	 * @param user_mobile
	 * @return
	 * @throws SQLException 
	 */
	public List<WxcpClientUser> getCenterUserInfo(WxcpClientUser wxcpClientUser) throws SQLException{
		
		if(wxcpClientUser==null) {
            return null;
        }
		
		StringBuffer sql = new StringBuffer("");
		
		sql.append(" select user.user_id as user_id,user.user_name as user_name,user.user_password as user_password , ");
		sql.append(" user.user_nick_name as user_nick_name,user.openname as openname,user.user_status as user_status, ");
		sql.append(" user.user_mobile as user_mobile,user.city as city,user.country as country, ");
		sql.append(" user.openid as openid,user.wx_public_id as wx_public_id,user.wx_public_name as wx_public_name, ");
		sql.append(" user.province as province,user.user_birthday as user_birthday,user.user_sex as user_sex ");
		sql.append(" from wxcp_client_user as user where user_status = 1 ");
		
		ArrayList<Object> conList = new ArrayList<Object>(); 
		if(wxcpClientUser.getUser_id() != null) {
			sql.append(" and user.user_id = ?  ");	
			conList.add(wxcpClientUser.getUser_id());
		}
		if(wxcpClientUser.getUser_mobile() != null) {
			sql.append(" and user.user_mobile = ?  ");	
			conList.add(wxcpClientUser.getUser_mobile());
		}
		if(wxcpClientUser.getOpenid() != null) {
			sql.append(" and user.openid = ?  ");	
			conList.add(wxcpClientUser.getOpenid());
		}
		if(wxcpClientUser.getWx_public_id() != null) {
			sql.append(" and user.wx_public_id = ?  ");	
			conList.add(wxcpClientUser.getWx_public_id());
		}
		
		Object[] params = new Object [conList.size()];
		conList.toArray(params);				

		return super.query(JdbcUtil.getCurrentConnection(), sql.toString(),new BeanListHandler<>(WxcpClientUser.class),params);
	}
	
}