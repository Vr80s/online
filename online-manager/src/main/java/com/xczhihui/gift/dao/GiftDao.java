package com.xczhihui.gift.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.common.dao.HibernateDao;
import com.xczhihui.gift.vo.GiftVo;

/**
 * 云课堂课程管理DAO
 * 
 * @author yxd
 *
 */
@Repository("giftDao")
public class GiftDao extends HibernateDao<Course> {
	public Page<GiftVo> findGiftPage(GiftVo giftVo, int pageNumber, int pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(
				"SELECT og.id,og.name,u.`name` createPerson,og.`create_time` createTime,"
						+ "og.`smallimg_path` smallimgPath,og.`price`,og.`is_free` isFree,og.`is_continuous` isContinuous,og.`continuous_count` continuousCount,og.status,og.brokerage "
						+ "FROM `oe_gift` og LEFT JOIN `user` u ON u.id = og.`create_person` WHERE og.`is_delete` = 0");
		if (giftVo.getName() != null) {
			paramMap.put("name", "%" + giftVo.getName() + "%");
			sql.append(" and og.`name` like :name ");
		}

		if (giftVo.getStatus() != null) {
			paramMap.put("status", giftVo.getStatus());
			sql.append(" and og.status = :status ");
		}

		sql.append(" order by og.status desc, og.sort desc");

		Page<GiftVo> giftVos = this.findPageBySQL(sql.toString(), paramMap,
				GiftVo.class, pageNumber, pageSize);
		return giftVos;
	}

	public void deleteById(Integer id) {
		String sql = "UPDATE `oe_gift` SET is_delete = 1 WHERE  id = :id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		this.getNamedParameterJdbcTemplate().update(sql, params);
	}

}
