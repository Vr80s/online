package com.xczhihui.anchor.dao;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.CourseAnchor;
import com.xczhihui.bxg.online.common.domain.CourseApplyInfo;
import com.xczhihui.common.util.enums.CourseForm;
import com.xczhihui.anchor.vo.AnchorIncomeVO;
import com.xczhihui.common.dao.HibernateDao;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 云课堂课程管理DAO
 * 
 * @author yxd
 *
 */
@Repository
public class AnchorDao extends HibernateDao<CourseAnchor> {

	public Page<CourseAnchor> findCourseAnchorPage(CourseAnchor courseAnchor,
			int pageNumber, int pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(
				"SELECT ca.id,\n"
						+ "  ca.`name`,\n"
						+ "  ca.`user_id` as userId,\n"
						+ "  ou.`login_name` loginName,\n"
						+ "  ca.`type`,\n"
						+ "  ca.`vod_divide`,\n"
						+ "  ca.`live_divide`,\n"
						+ "  ca.`offline_divide`,\n"
						+ "  ca.`gift_divide`,\n"
						+ "  ca.`is_recommend` isRecommend,\n"
						+ " (SELECT count(*) from course_apply_info cai where cai.user_id=ca.user_id and cai.is_delete=0) as courseCount,"
						+ "  ca.`status`  \n" + "FROM\n"
						+ "  `course_anchor` ca \n" + "  JOIN `oe_user` ou \n"
						+ "    ON ca.`user_id` = ou.`id` \n"
						+ "WHERE ca.`deleted` = 0 \n"
						+ "  AND ou.`is_delete` = 0 \n" + "    ");
		if (courseAnchor.getLoginName() != null) {
			paramMap.put("loginName", "%" + courseAnchor.getLoginName() + "%");
			sql.append("and ou.login_name like :loginName ");
		}
		if (courseAnchor.getName() != null) {
			paramMap.put("name", "%" + courseAnchor.getName() + "%");
			sql.append("and ca.name like :name ");
		}
		if (courseAnchor.getType() != null) {
			paramMap.put("type", courseAnchor.getType());
			sql.append("and ca.type like :type ");
		}
		sql.append(" ORDER BY ca.`create_time` DESC");

		Page<CourseAnchor> courseAnchors = this.findPageBySQL(sql.toString(),
				paramMap, CourseAnchor.class, pageNumber, pageSize);

		return courseAnchors;
	}

	public Page<CourseAnchor> findCourseAnchorRecPage(
			CourseAnchor courseAnchor, int pageNumber, int pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder("SELECT ca.id,\n"
				+ "  ca.`user_id` as userId,\n"
				+ "  ca.`name`,\n" + "  ou.`login_name` loginName,\n"
				+ "  ca.`type`,\n" + "  ca.`vod_divide`,\n"
				+ "  ca.`live_divide`,\n" + "  ca.`offline_divide`,\n"
				+ "  ca.`gift_divide`,\n" + "  ca.`recommend_sort`,\n"
				+ "  ca.`status`  \n" + "FROM\n" + "  `course_anchor` ca \n"
				+ "  JOIN `oe_user` ou \n" + "    ON ca.`user_id` = ou.`id` \n"
				+ "WHERE ca.`deleted` = 0 \n" + "  AND ou.`is_delete` = 0 \n"
				+ "  AND ca.`is_recommend` = 1 \n" + "    ");
		if (courseAnchor.getName() != null) {
			paramMap.put("name", "%" + courseAnchor.getName() + "%");
			sql.append("and ou.name like :name ");
		}
		if (courseAnchor.getType() != null) {
			paramMap.put("type", courseAnchor.getType());
			sql.append("and ca.type like :type ");
		}
		sql.append(" ORDER BY ca.recommend_sort DESC");

		Page<CourseAnchor> courseAnchors = this.findPageBySQL(sql.toString(),
				paramMap, CourseAnchor.class, pageNumber, pageSize);

		return courseAnchors;
	}

	public CourseApplyInfo findCourseApplyById(Integer id) {
		DetachedCriteria dc = DetachedCriteria.forClass(CourseApplyInfo.class);
		dc.add(Restrictions.eq("id", id));
		CourseApplyInfo courseApplyInfo = this.findEntity(dc);

		return courseApplyInfo;
	}

	public CourseAnchor findCourseAnchorById(Integer id) {
		String sql = "SELECT \n" + "  ca.`name`,\n" + "  ca.`type`,\n"
				+ "  ca.`vod_divide` vodDivide,\n"
				+ "  ca.`live_divide` liveDivide,\n"
				+ "  ca.`offline_divide` offlineDivide,\n"
				+ "  ca.`gift_divide` giftDivide,\n" + "  ca.`status` \n"
				+ "FROM\n" + "  `course_anchor` ca \n"
				+ "  JOIN `oe_user` ou \n" + "    ON ca.`user_id` = ou.`id` \n"
				+ "WHERE ca.`deleted` = 0 \n" + "  AND ou.`is_delete` = 0 \n"
				+ "  AND ca.id=:id  \n" + "  ORDER BY ca.`create_time`\n";
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("id", id);
		return this.findEntityByJdbc(CourseAnchor.class, sql, m);
	}

	public CourseAnchor findCourseAnchorByUserId(String userId) {
		String sql = "SELECT \n" + "  ca.`name`,\n" + "  ca.`type`,\n"
				+ "  ca.`vod_divide` vodDivide,\n"
				+ "  ca.`live_divide` liveDivide,\n"
				+ "  ca.`offline_divide` offlineDivide,\n"
				+ "  ca.`gift_divide` giftDivide,\n" + "  ca.`status` \n"
				+ "FROM\n" + "  `course_anchor` ca \n"
				+ "  JOIN `oe_user` ou \n" + "    ON ca.`user_id` = ou.`id` \n"
				+ "WHERE ca.`deleted` = 0 \n" + "  AND ou.`is_delete` = 0 \n"
				+ "  AND ou.id=:userId  \n" + "  ORDER BY ca.`create_time`\n";
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("userId", userId);
		return this.findEntityByJdbc(CourseAnchor.class, sql, m);
	}

	public Page<AnchorIncomeVO> findCourseAnchorIncomePage(
			CourseAnchor courseAnchor, int currentPage, int pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(" SELECT \n"
				+ "  ou.`login_name`,\n" + "  ca.`name`,\n" + "  ou.`id`,\n"
				+ "  IFNULL(SUM(uci.`value`), 0) total \n" + "FROM\n"
				+ "  `course_anchor` ca \n" + "  JOIN `oe_user` ou \n"
				+ "    ON ca.`user_id` = ou.`id` \n"
				+ "  LEFT JOIN `user_coin_increase` uci \n"
				+ "    ON ca.`user_id` = uci.`user_id` \n"
				+ "    AND uci.`change_type` IN (3, 7) " + " where 1=1 \n");
		if (courseAnchor.getName() != null) {
			paramMap.put("name", "%" + courseAnchor.getName() + "%");
			sql.append("and ou.name like :name ");
		}
		sql.append("GROUP BY ou.id ORDER BY total " + courseAnchor.getRemark());

		Page<AnchorIncomeVO> courseAnchors = this.findPageBySQL(sql.toString(),
				paramMap, AnchorIncomeVO.class, currentPage, pageSize);
		List<AnchorIncomeVO> courseAnchorList = courseAnchors.getItems();
		for (AnchorIncomeVO courseAnchorIncome : courseAnchorList) {
			String userId = courseAnchorIncome.getId();
			setCourseIncome(courseAnchorIncome, userId);
			setGiftIncome(courseAnchorIncome, userId);
			System.out.println(userId);
			setBalance(courseAnchorIncome, userId);
			setEnchashmentTotal(courseAnchorIncome, userId);
			setEnchashmentCount(courseAnchorIncome, userId);

			DecimalFormat df = new DecimalFormat("0.00");
			String total="";
			if(courseAnchorIncome.getTotal().equals("0")){
				total="0.00";
			}else {
				Double d = Double.parseDouble(courseAnchorIncome.getTotal());
				total=df.format(d);
			}
			courseAnchorIncome.setTotal(total);

		}
		return courseAnchors;
	}

	private void setEnchashmentCount(AnchorIncomeVO courseAnchorIncome,
			String userId) {
		String sql = "SELECT \n" + "  COUNT(eai.`enchashment_sum`) COUNT\n"
				+ "FROM\n" + "  `course_anchor` ca \n"
				+ "  JOIN `oe_user` ou \n" + "    ON ca.`user_id` = ou.`id` \n"
				+ "  JOIN  `enchashment_apply_info` eai\n"
				+ "  ON ou.id = eai.`user_id`\n" + "  AND eai.`status`=1"
				+ "  AND ou.id=?";
		int ec = this.queryForInt(sql, userId);
		courseAnchorIncome.setEnchashmentCount(ec + "");
	}

	private void setEnchashmentTotal(AnchorIncomeVO courseAnchorIncome,
			String userId) {
		String sql = "SELECT \n" + "  SUM(eai.`enchashment_sum`)\n" + "FROM\n"
				+ "  `course_anchor` ca \n" + "  JOIN `oe_user` ou \n"
				+ "    ON ca.`user_id` = ou.`id` \n"
				+ "  JOIN  `enchashment_apply_info` eai\n"
				+ "  ON ou.id = eai.`user_id`\n" + "  AND eai.`status`=1\n"
				+ "  AND ou.id=?";
		Double rmb = this.queryForDouble(sql, userId);
		DecimalFormat df = new DecimalFormat("0.00");
		courseAnchorIncome.setEnchashmentTotal(rmb == 0 ? "0.00" : df
				.format(rmb));
	}

	private void setBalance(AnchorIncomeVO courseAnchorIncome, String userId) {
		String rmb = getRmbBalance(userId);
		String coin = getCoinBalance(userId);
		courseAnchorIncome.setRmb(rmb);
		courseAnchorIncome.setCoin(coin);
	}

	/**
	 * Description：获取熊猫币余额 creed: Talk is cheap,show me the code
	 * 
	 * @author name：yuxin <br>
	 *         email: yuruixin@ixincheng.com
	 * @Date: 2018/2/8 0008 下午 4:09
	 **/
	private String getCoinBalance(String userId) {
		String sql = "SELECT \n" + "  uc.`balance_reward_gift`\n" + "FROM\n"
				+ "  `course_anchor` ca \n" + "  JOIN `oe_user` ou \n"
				+ "    ON ca.`user_id` = ou.`id` \n"
				+ "  JOIN  `user_coin` uc\n" + "  ON ou.id = uc.`user_id`"
				+ "  WHERE ou.id = ? \n";
		Double rmb = this.queryForDouble(sql, userId);
		DecimalFormat df = new DecimalFormat("0.00");
		return rmb == 0 ? "0.00" : df.format(rmb);
	}

	private String getRmbBalance(String userId) {
		String sql = "SELECT \n" + "  uc.`rmb`\n" + "FROM\n"
				+ "  `course_anchor` ca \n" + "  JOIN `oe_user` ou \n"
				+ "    ON ca.`user_id` = ou.`id` \n"
				+ "  JOIN  `user_coin` uc\n" + "  ON ou.id = uc.`user_id`"
				+ "  WHERE ou.id = ? \n";
		Double rmb = this.queryForDouble(sql, userId);
		DecimalFormat df = new DecimalFormat("0.00");
		return rmb == 0 ? "0.00" : df.format(rmb);
	}

	private void setGiftIncome(AnchorIncomeVO courseAnchorIncome, String userId) {
		String sql = "SELECT \n" + "  IFNULL(SUM(uci.`value`), 0) gift \n"
				+ "FROM\n" + "  `course_anchor` ca \n"
				+ "  JOIN `oe_user` ou \n" + "    ON ca.`user_id` = ou.`id` \n"
				+ "  LEFT JOIN `user_coin_increase` uci \n"
				+ "    ON ca.`user_id` = uci.`user_id` \n"
				+ "    AND uci.`change_type` = 3 \n" + "    AND ou.id = ? \n";
		Double inconme = this.queryForDouble(sql, userId);
		DecimalFormat df = new DecimalFormat("0.00");
		String gift = df.format(inconme);
		courseAnchorIncome.setGift(inconme == 0 ? "0.00" : gift);
	}

	public void setCourseIncome(AnchorIncomeVO courseIncome, String userId) {
		String live = getCourseIncome(userId, CourseForm.LIVE.getCode());
		String vod = getCourseIncome(userId, CourseForm.VOD.getCode());
		String offline = getCourseIncome(userId, CourseForm.OFFLINE.getCode());
		courseIncome.setLive(live);
		courseIncome.setVod(vod);
		courseIncome.setOffline(offline);
	}

	public String getCourseIncome(String userId, int type) {
		String sql = "SELECT \n" + "   IFNULL(SUM(uci.`value`), 0) vod \n"
				+ "FROM\n" + "  `course_anchor` ca \n"
				+ "  JOIN `oe_user` ou \n" + "    ON ca.`user_id` = ou.`id` \n"
				+ "  LEFT JOIN `user_coin_increase` uci \n"
				+ "    ON ca.`user_id` = uci.`user_id` \n"
				+ "    AND uci.`change_type` = 7\n"
				+ "  LEFT JOIN `oe_order_detail` ood\n"
				+ "    ON ood.id = uci.`order_no_course`\n"
				+ "  LEFT JOIN `oe_course` oc\n"
				+ "    ON oc.id = ood.`course_id`\n" + "  WHERE ou.`id`= ?"
				+ "  AND oc.type = ?";
		Double inconme = this.queryForDouble(sql, userId, type);
		DecimalFormat df = new DecimalFormat("0.00");
		return inconme == 0 ? "0.00" : df.format(inconme);
	}

	public static void main(String[] args) {
		DecimalFormat df = new DecimalFormat("0.00");
		double rmb = 0;
		System.out.println(df.format(rmb));
	}
}
