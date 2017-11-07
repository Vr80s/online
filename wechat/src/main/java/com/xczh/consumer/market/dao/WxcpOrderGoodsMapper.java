package com.xczh.consumer.market.dao;

import com.xczh.consumer.market.bean.WxcpOrderGoods;
import com.xczh.consumer.market.utils.DateUtil;
import com.xczh.consumer.market.utils.JdbcUtil;
import com.xczh.consumer.market.vo.WxcpOrderGoodsExt;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

//import com.xczh.consumer.market.bean.WxcpGoodsInfo;


/**
 * 
 * WxcpOrderGoodsMapper数据库操作接口类
 * @author yanghui
 **/
@Repository
public class WxcpOrderGoodsMapper extends BasicSimpleDao{

	
	/**
	 * 
	 * 根据订单ID，查询该订单对应的商品列表；
	 * 
	 **/
	public List<WxcpOrderGoodsExt> getListWxcpGoodsInfo(ArrayList<String> ids/*String order_id*/) throws SQLException{

		if(ids == null || ids.size() == 0) return new ArrayList<WxcpOrderGoodsExt>();
		
		String con_str = "";		
		for(int i=0;i<ids.size();i++) con_str += "'" + ids.get(i) + "',"; 
		if(con_str.charAt(con_str.length()-1) == ',') con_str = con_str.substring(0,con_str.length()-1);
		
		StringBuilder sql = new StringBuilder();
		sql.append("select 															");			
		sql.append("	wxcp_goods_info.goods_id 			,						");		
		sql.append("	wxcp_goods_info.shop_id 			,						");
		sql.append("	wxcp_goods_info.category_id 		,   					");
		sql.append("	wxcp_goods_info.title 				,   					");
		sql.append("	wxcp_goods_info.notice_title 		,   					");
		sql.append("	wxcp_goods_info.is_onsale 			,   					");
		sql.append("	wxcp_goods_info.is_violation 		,   					");
		sql.append("	wxcp_goods_info.is_delivery_payed 	,   					");
		sql.append("	wxcp_goods_info.is_enable 			,   					");
		sql.append("	wxcp_goods_info.create_user_id 		,   					");
		sql.append("	wxcp_goods_info.create_datetime 	,   					");
		sql.append("	wxcp_goods_info.update_datetime 	,   					");
		sql.append("	wxcp_goods_info.image_URL 			,   					");
		sql.append("	wxcp_goods_info.origin_unit_money 	,   					");
		sql.append("	wxcp_goods_info.coupon_unit_money 	,   					");
		sql.append("	wxcp_order_goods.goods_num			, 	   					");
		sql.append("	wxcp_order_goods.order_id 	   								");
		sql.append("from                                                            ");
		sql.append("	wxcp_goods_info,wxcp_order_goods                            ");
		sql.append("where                                                           ");
		sql.append("	1 = 1                                                       ");
		sql.append("	and wxcp_goods_info.goods_id  = wxcp_order_goods.goods_id   ");		
		//sql.append("	and wxcp_order_goods.order_id = ?              				");
		//sql.append("	and wxcp_order_goods.order_id in ( ? )              		");
		//sql.append("	and wxcp_order_goods.order_id in (" + ids.toArray()  +   ") ");
		sql.append("	and wxcp_order_goods.order_id in (" + con_str  + 		")  ");
		sql.append("order by                                                        ");
		sql.append("	wxcp_order_goods.created_time desc                          ");
		
		Object params [] = {};//Object params = ids.toArray();//Object params[] = {order_id};
		return super.query(JdbcUtil.getCurrentConnection(), sql.toString(),new BeanListHandler<>(WxcpOrderGoodsExt.class),params);
	}
	
	/**
	 * 
	 * 查询（根据订单ID查询）
	 * 
	 **/
	public List<WxcpOrderGoods>  selectByOrderId (String order_id ) throws SQLException {
		
		StringBuilder sql = new StringBuilder();
		sql.append("select 							");	
		sql.append("   	o2g_id			,           ");
		sql.append("   	order_id		,           ");
		sql.append("   	buyer_id		,           ");
		sql.append("   	seller_id		,           ");
		sql.append("   	origin_fee		,           ");
		sql.append("   	acture_fee		,           ");
		sql.append("   	goods_id		,           ");
		sql.append("   	created_time	,       	");
		sql.append("   	updated_time	,           ");
		sql.append("   	goods_num	               	");
		sql.append("from                   			");
		sql.append("	wxcp_order_goods   			");
		sql.append("where                  			");
		sql.append("	1 = 1               		");	
		sql.append("	and order_id = ?    		");
		sql.append("order by 						");
		sql.append("	created_time desc 			");
				
		Object params[] = {order_id};
		return super.query(JdbcUtil.getCurrentConnection(), sql.toString(),new BeanListHandler<>(WxcpOrderGoods.class),params);
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
	public int insert( WxcpOrderGoods record ) throws SQLException {
		
		StringBuilder sql = new StringBuilder();
		sql.append("insert into wxcp_order_goods 	");
		sql.append("(	                            ");
		sql.append("   	o2g_id			,           ");
		sql.append("   	order_id		,           ");
		sql.append("   	buyer_id		,           ");
		sql.append("   	seller_id		,           ");
		sql.append("   	origin_fee		,           ");
		sql.append("   	acture_fee		,           ");
		sql.append("   	goods_id		,           ");
		sql.append("   	created_time	,       	");
		sql.append("   	updated_time	,           ");
		sql.append("   	goods_num	               	");
		sql.append(")                               ");
		sql.append("values                          ");
		sql.append("(                               ");
		sql.append("  ?,                            ");
		sql.append("  ?,                            ");
		sql.append("  ?,                            ");
		sql.append("  ?,                            ");
		sql.append("  ?,                            ");
		sql.append("  ?,                            ");
		sql.append("  ?,                            ");
		sql.append("  ?,                            ");
		sql.append("  ?,                            ");
		sql.append("  ?                             ");
		sql.append(")                               ");
		                                        
		
		String id = UUID.randomUUID().toString().replace("-", "");
		if(record.getO2g_id()==null || record.getO2g_id().isEmpty()) record.setO2g_id(id);
		if(record.getCreated_time()==null) record.setCreated_time(new Date());
		if(record.getUpdated_time()==null) record.setUpdated_time(new Date());
		if(record.getSeller_id()==null) record.setSeller_id("");
		if(record.getActure_fee()==null) record.setActure_fee(0);
		
		super.update(
			JdbcUtil.getCurrentConnection(), 
			sql.toString(), 
			record.getO2g_id(),      
			record.getOrder_id(),      
			record.getBuyer_id(),      
			record.getSeller_id(),      
			record.getOrigin_fee(),  
			record.getActure_fee(),      
			record.getGoods_id(),     	  
			DateUtil.formatDate(record.getCreated_time()) ,
			DateUtil.formatDate(record.getUpdated_time()) ,  
			record.getGoods_num()	    	      
			);				
		return 0;
	}

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	public int insertSelective( WxcpOrderGoods record ) throws SQLException {
		return -1;
	}

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	public int updateByPrimaryKeySelective( WxcpOrderGoods record ) throws SQLException {
		return -1;
	}

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	public int updateByPrimaryKey ( WxcpOrderGoods record ) throws SQLException {
		return -1;
	}
	/**
	 * 根据商品id 查询购买人数
	 * @param goodsId
	 * @return
	 */
	public int getOrderNumByGoodsId(String goodsId) throws SQLException{
		StringBuffer sql = new StringBuffer(" select count(0) from wxcp_order_goods where goods_id = ? ");
		Object params[] = {goodsId};
		return (int)(long)this.query(JdbcUtil.getCurrentConnection(), sql.toString(),new ScalarHandler(),params);
	}
}