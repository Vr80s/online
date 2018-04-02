package com.xczhihui.bxg.online.manager.user.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.manager.common.dao.HibernateDao;
import com.xczhihui.bxg.online.manager.user.vo.WechatChannelVo;

/**
 * 云课堂课程管理DAO
 * 
 * @author yxd
 *
 */
@Repository("wechatChannelDao")
public class WechatChannelDao extends HibernateDao<Course>{
	
	 public Page<WechatChannelVo> findWechatChannelPage(WechatChannelVo WechatChannelVo, int pageNumber, int pageSize){
		
		 Map<String, Object> paramMap = new HashMap<String, Object>();
		 StringBuilder sql = new StringBuilder(
				"	SELECT og.id,u.`name` createPerson,og.`create_time` createTime,og.sort,"
		 		+ " og.status as status,og.contact,og.mobile,og.city,og.province,og.area,"
		 		+ " og.city_id as cityId,og.province_id as provinceId,og.area_id as areaId "
		 		+ "FROM `oe_wechat_channel` "
		 		+ " og LEFT JOIN `user` u ON u.id = og.`create_person` WHERE og.`is_delete` = 0");

		 if (WechatChannelVo.getStatus() != null) {
			 paramMap.put("status", WechatChannelVo.getStatus());
			 sql.append(" and og.status = :status ");
		 }
		 
		 sql.append(" order by og.status desc, og.sort desc");
		 Page<WechatChannelVo> WechatChannelVos = this.findPageBySQL(sql.toString(), paramMap, WechatChannelVo.class, pageNumber, pageSize);
		 return WechatChannelVos;
	 }

	public void deleteById(Integer id) {
		String sql="UPDATE `oe_wechat_channel` SET is_delete = 1 WHERE  id = :id";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("id", id);
		this.getNamedParameterJdbcTemplate().update(sql, params);
	}
	
	
	
	public List<WechatChannelVo> findWechatChannelList(){
		
		 StringBuilder sql = new StringBuilder(
				"	SELECT og.id,og.`create_time` createTime,og.sort,"
		 		+ " og.status as status,og.contact,og.mobile,og.city,og.province,og.area,"
		 		+ " og.city_id as cityId,og.province_id as provinceId,og.area_id as areaId "
		 		+ "FROM `oe_wechat_channel` as og WHERE og.`is_delete` = 0");
		 Page<WechatChannelVo>  page = this.findPageBySQL(sql.toString(), null,
				 WechatChannelVo.class, 0, Integer.MAX_VALUE);
		 return page.getItems();
	 }
	
	
	
	
}
