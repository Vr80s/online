package com.xczhihui.gift.dao;

import java.util.HashMap;
import java.util.Map;

import com.xczhihui.gift.vo.RewardVo;
import org.springframework.stereotype.Repository;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.common.dao.HibernateDao;

/**
 * ClassName: RewardDao.java <br>
 * Description: 打赏dao<br>
 * Create by: name：yuxin <br>
 * email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年8月16日<br>
 */
@Repository("rewardDao")
public class RewardDao extends HibernateDao<Course> {
	public Page<RewardVo> findRewardPage(RewardVo giftVo, int pageNumber,
			int pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(
				"SELECT oer.`id`,oer.`price`,oer.`is_freedom` isFreeDom,oer.`sort`,oer.`status`,oer.`create_time` createTime,"
						+ "oer.`brokerage` FROM `oe_reward` oer WHERE is_delete = 0");
		paramMap.put("status", giftVo.isStatus());
		// sql.append(" and oer.status = :status ");

		sql.append(" order by oer.status desc,oer.sort desc");

		Page<RewardVo> giftVos = this.findPageBySQL(sql.toString(), paramMap,
				RewardVo.class, pageNumber, pageSize);
		return giftVos;
	}

	public void updateStatusById(Integer id) {
		String sql = "UPDATE `oe_reward` SET is_delete = 1 WHERE  id = :id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		this.getNamedParameterJdbcTemplate().update(sql, params);
	}

}
