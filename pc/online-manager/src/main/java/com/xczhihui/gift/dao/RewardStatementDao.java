package com.xczhihui.gift.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.common.dao.HibernateDao;
import com.xczhihui.gift.vo.RewardStatementVo;

/**
 * ClassName: RewardStatementDao.java <br>
 * Description: 打赏流水表<br>
 * Create by: name：yuxin <br>
 * email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年8月16日<br>
 */
@Repository("rewardStatementDao")
public class RewardStatementDao extends HibernateDao<Course> {

	public Page<RewardStatementVo> findRewardStatementPage(
			RewardStatementVo rewardStatementVo, int pageNumber, int pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(
				"SELECT ogs.`id`,ogs.`price`,ou1.name giver,ou2.name receiver,"
						+ "ogs.`pay_type`,ogs.`create_time` createTime,ogs.`channel`,ogs.client_type clientType FROM `oe_reward_statement` ogs "
						+ "LEFT JOIN `oe_user` ou1 ON ogs.`giver`=ou1.`id` LEFT JOIN `oe_user` ou2 ON ogs.`receiver`=ou2.`id` where 1=1 ");
		if (rewardStatementVo.getSearchType() != null) {
			switch (rewardStatementVo.getSearchType()) {
			case 1:// 礼物金额
				if (rewardStatementVo.getSearchCondition() != null) {
					paramMap.put("price",
							rewardStatementVo.getSearchCondition());
					sql.append(" and ogs.price = :price ");
				}
				break;
			case 2:// 赠送人
				if (rewardStatementVo.getSearchCondition() != null) {
					paramMap.put("giver",
							"%" + rewardStatementVo.getSearchCondition() + "%");
					sql.append(" and ou1.name like :giver ");
				}
				break;
			case 3:// 收到人
				if (rewardStatementVo.getSearchCondition() != null) {
					paramMap.put("receiver",
							"%" + rewardStatementVo.getSearchCondition() + "%");
					sql.append(" and ou2.name like :receiver ");
				}
				break;
			case 4:// 订单编号
				if (rewardStatementVo.getSearchCondition() != null) {
					paramMap.put("id", rewardStatementVo.getSearchCondition());
					sql.append(" and ogs.`id` = :id ");
				}
				break;

			default:
				break;
			}
		}

		if (rewardStatementVo.getStartTime() != null) {
			sql.append(" and ogs.create_time >=:startTime");
			paramMap.put("startTime", rewardStatementVo.getStartTime());
		}

		if (rewardStatementVo.getStopTime() != null) {
			sql.append(" and ogs.create_time <=:stopTime");
			// sql.append(" and DATE_FORMAT(ogs.create_time,'%Y-%m-%d') <=:stopTime");
			paramMap.put("stopTime", rewardStatementVo.getStopTime());
		}

		sql.append(" order by ogs.create_time desc");

		Page<RewardStatementVo> rewardStatementVos = this.findPageBySQL(
				sql.toString(), paramMap, RewardStatementVo.class, pageNumber,
				pageSize);
		return rewardStatementVos;
	}

	/*
	 * public void deleteById(Integer id) { String
	 * sql="UPDATE `oe_gift` SET is_delete = 1 WHERE  id = :id";
	 * Map<String,Object> params=new HashMap<String,Object>(); params.put("id",
	 * id); this.getNamedParameterJdbcTemplate().update(sql, params); }
	 */

}
