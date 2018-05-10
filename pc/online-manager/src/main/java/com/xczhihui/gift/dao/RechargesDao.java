package com.xczhihui.gift.dao;

import java.util.HashMap;
import java.util.Map;

import com.xczhihui.gift.vo.RechargesVo;
import org.springframework.stereotype.Repository;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.common.dao.HibernateDao;

/**
 * 云课堂课程管理DAO
 * 
 * @author yxd
 *
 */
@Repository("rechargesDao")
public class RechargesDao extends HibernateDao<Course> {
	public Page<RechargesVo> findRechargesPage(RechargesVo rechargesVo,
			int pageNumber, int pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();

		StringBuilder sql = new StringBuilder(
				"SELECT og.id,u.`name` createPerson,og.`create_time` createTime,"
						+ "og.`price`,og.price*10 as xmbPrice,og.status as status "
						+ "FROM `oe_recharges` og LEFT JOIN `user` u ON u.id = og.`create_person` WHERE og.`is_delete` = 0");

		if (rechargesVo.getStatus() != null) {
			paramMap.put("status", rechargesVo.getStatus());
			sql.append(" and og.status = :status ");
		}

		sql.append(" order by og.status desc, og.sort desc");

		Page<RechargesVo> RechargesVos = this.findPageBySQL(sql.toString(),
				paramMap, RechargesVo.class, pageNumber, pageSize);
		return RechargesVos;
	}

	public void deleteById(Integer id) {
		String sql = "UPDATE `oe_recharges` SET is_delete = 1 WHERE  id = :id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		this.getNamedParameterJdbcTemplate().update(sql, params);
	}

}
