package com.xczhihui.bxg.online.manager.wechat.dao;


import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.WechatMaterial;
import com.xczhihui.bxg.online.manager.common.dao.HibernateDao;

/**
 * 云课堂课程管理DAO
 * 
 * @author yxd
 *
 */
@Repository("wechatMaterialDao")
public class WechatMaterialDao extends HibernateDao<Course>{
	
	 public Page<WechatMaterial> findWechatMaterialPage(WechatMaterial wechatMaterial, int pageNumber, int pageSize){
		 Map<String, Object> paramMap = new HashMap<String, Object>();
		 StringBuilder sql = new StringBuilder("SELECT og.id,og.name,u.`name` createPerson,og.`create_time` createTime,"
		 		+ "og.`smallimg_path` smallimgPath,og.`price`,og.`is_free` isFree,og.`is_continuous` isContinuous,og.`continuous_count` continuousCount,og.status,og.brokerage "
		 		+ "FROM `oe_WechatMaterial` og LEFT JOIN `user` u ON u.id = og.`create_person` WHERE og.`is_delete` = 0");
/*		 if (WechatMaterial.getName() != null) {
			 paramMap.put("name", "%" + WechatMaterial.getName() + "%");
			 sql.append(" and og.`name` like :name ");
		 }
		 
		 if (WechatMaterial.getStatus() != null) {
			 paramMap.put("status", WechatMaterial.getStatus());
			 sql.append(" and og.status = :status ");
		 }*/
		 sql.append(" order by og.status desc, og.sort desc");

		 Page<WechatMaterial> WechatMaterials = this.findPageBySQL(sql.toString(), paramMap, WechatMaterial.class, pageNumber, pageSize);
		 return WechatMaterials;
	 }

	public void deleteById(Integer id) {
		String sql="UPDATE `oe_WechatMaterial` SET is_delete = 1 WHERE  id = :id";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("id", id);
		this.getNamedParameterJdbcTemplate().update(sql, params);
	}
	
	
}
