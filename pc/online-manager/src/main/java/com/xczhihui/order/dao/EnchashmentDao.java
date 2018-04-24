package com.xczhihui.order.dao;

import java.util.HashMap;
import java.util.Map;

import com.xczhihui.bxg.online.common.domain.EnchashmentApplyInfo;
import org.springframework.stereotype.Repository;

import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.util.bean.Page;

@Repository
public class EnchashmentDao extends SimpleHibernateDao {

	public Page<EnchashmentApplyInfo> findEnchashmentPage(
			EnchashmentApplyInfo orderVo, int pageNumber, int pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(
				"SELECT \n"
						+ "  eai.id,\n"
						+ "  eai.`time`,\n"
						+ "  eai.`enchashment_sum`,\n"
						+ "  ou.`login_name` loginName,\n"
						+ "  eai.`status`,\n"
						+ "  eai.tickling_time,\n"
						+ "  eai.`dismissal`,\n"
						+ "  eai.`dismissal_remark`,  \n"
						+ "  eai.`order_no`,  \n"
						+ "  ubc.`acct_name` acctName, ubc.`cert_id` certId,ubc.`acct_pan` acctPan,ubc.`bank_name` bankName\n"
						+ "FROM\n" + "  `enchashment_apply_info` eai\n"
						+ "  JOIN oe_user ou \n"
						+ "    ON ou.`id` = eai.`user_id` \n"
						+ "   join `oe_user_bank_card` ubc\n"
						+ "   on ou.id=ubc.`user_id`\n"
						+ "  JOIN `course_anchor` ca \n"
						+ "    ON ca.`user_id`=ou.`id`" + "where 1 = 1");

		if (orderVo.getStartTime() != null) {
			sql.append(" and eai.time >=:startTime");
			paramMap.put("startTime", orderVo.getStartTime());
		}

		if (orderVo.getStopTime() != null) {
			sql.append(" and DATE_FORMAT(eai.time,'%Y-%m-%d') <=:stopTime");
			paramMap.put("stopTime", orderVo.getStopTime());
		}

		if (orderVo.getStatus() != null) {
			sql.append(" and eai.status = :orderStatus ");
			paramMap.put("orderStatus", orderVo.getStatus());
		}

		if (orderVo.getId() != null) {
			sql.append(" and order_no LIKE :orderNo ");
			paramMap.put("orderNo", "%" + orderVo.getId() + "%");
		}

		if (orderVo.getUserId() != null) {
			sql.append(" and ou.name like :createPersonName ");
			paramMap.put("createPersonName", "%" + orderVo.getUserId() + "%");
		}

		if (orderVo.getAnthorType() != null) {
			sql.append(" and ca.type = :type ");
			paramMap.put("type", orderVo.getAnthorType());
		}

		sql.append(" GROUP BY eai.id order by eai.time desc ");
		Page<EnchashmentApplyInfo> ms = this.findPageBySQL(sql.toString(),
				paramMap, EnchashmentApplyInfo.class, pageNumber, pageSize);
		return ms;
	}

}
