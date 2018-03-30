package com.xczhihui.bxg.online.manager.gift.dao;


import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.manager.common.dao.HibernateDao;
import com.xczhihui.bxg.online.manager.gift.vo.GiftStatementVo;


/** 
 * ClassName: GiftStatementDao.java <br>
 * Description: 礼物流水<br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年8月15日<br>
 */
@Repository("giftStatementDao")
public class GiftStatementDao extends HibernateDao<Course>{
	
	 public Page<GiftStatementVo> findGiftStatementPage(GiftStatementVo giftStatementVo, int pageNumber, int pageSize){
		 Map<String, Object> paramMap = new HashMap<String, Object>();
		 StringBuilder sql = new StringBuilder("SELECT ogs.`id`,ogs.`gift_name` giftName,ogs.`price`,ogs.`count`,ou1.name giver,ca.name receiver,ou2.login_name loginName,"
		 		+ "ogs.`pay_type`,ogs.`create_time` createTime,ogs.`channel`,ogs.client_type clientType FROM `oe_gift_statement` ogs "
		 		+ "LEFT JOIN `oe_user` ou1 ON ogs.`giver`=ou1.`id` LEFT JOIN `oe_user` ou2 ON ogs.`receiver`=ou2.`id` "
				+"LEFT JOIN course_anchor ca ON ogs.`receiver`=ca.`user_id` where 1=1 " );
		switch (giftStatementVo.getSearchType()) {
		case 1://礼物名称
			if (giftStatementVo.getSearchCondition() != null) {
				paramMap.put("giftName", "%" + giftStatementVo.getSearchCondition() + "%");
				sql.append(" and ogs.gift_name like :giftName ");
			}
			break;
		case 2://赠送人
			if (giftStatementVo.getSearchCondition() != null) {
				paramMap.put("giver", "%" + giftStatementVo.getSearchCondition() + "%");
				sql.append(" and ou1.name like :giver ");
			}
			break;
		case 3://收到人
			if (giftStatementVo.getSearchCondition() != null) {
				paramMap.put("receiver", "%" + giftStatementVo.getSearchCondition() + "%");
				sql.append(" and ou2.name like :receiver ");
			}
			break;
		case 4://订单编号
			if (giftStatementVo.getSearchCondition() != null) {
				 paramMap.put("id", giftStatementVo.getSearchCondition());
				 sql.append(" and ogs.`id` = :id ");
			 }
			break;

		default:
			break;
		}
		 
		 
		if (giftStatementVo.getStartTime() != null) {
			sql.append(" and ogs.create_time >=:startTime");
			paramMap.put("startTime", giftStatementVo.getStartTime());
		}

		if (giftStatementVo.getStopTime() != null) {
			sql.append(" and ogs.create_time <=:stopTime");
//			sql.append(" and DATE_FORMAT(ogs.create_time,'%Y-%m-%d') <=:stopTime");
			paramMap.put("stopTime", giftStatementVo.getStopTime());
		}

		 sql.append(" order by ogs.create_time desc");

		 Page<GiftStatementVo> giftStatementVos = this.findPageBySQL(sql.toString(), paramMap, GiftStatementVo.class, pageNumber, pageSize);
		 return giftStatementVos;
	 }
	 
/*
	public void deleteById(Integer id) {
		String sql="UPDATE `oe_gift` SET is_delete = 1 WHERE  id = :id";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("id", id);
		this.getNamedParameterJdbcTemplate().update(sql, params);
	}*/
	
	
}
