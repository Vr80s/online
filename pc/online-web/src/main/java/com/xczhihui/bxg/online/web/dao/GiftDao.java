package com.xczhihui.bxg.online.web.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xczhihui.bxg.online.api.vo.LiveCourseUserVO;
import com.xczhihui.bxg.online.api.vo.LiveCourseVO;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Gift;
import com.xczhihui.bxg.online.common.domain.GiftStatement;
import com.xczhihui.bxg.online.api.vo.ReceivedGift;
import com.xczhihui.bxg.online.api.vo.ReceivedReward;
import com.xczhihui.bxg.online.common.domain.Reward;


/** 
 * ClassName: GiftDao.java <br>
 * Description:礼物打赏相关 <br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年8月16日<br>
 */
@Repository
public class GiftDao extends SimpleHibernateDao {

	public void addGiftStatement(GiftStatement giftStatement) {
		this.save(giftStatement);
	}

	/** 
	 * Description：获取所有礼物
	 * @return
	 * @return List<Gift>
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public List<Gift> getGift() {
		DetachedCriteria dc = DetachedCriteria.forClass(Gift.class);
		dc.add(Restrictions.eq("isDelete", false));
		dc.add(Restrictions.eq("status", "1"));
		dc.addOrder(Order.desc("sort"));
		List<Gift> gift = this.findEntities(dc);
		return gift;
	}
	
	/** 
	 * Description：获取所有打赏
	 * @return
	 * @return List<Gift>
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public List<Reward> getReward() {
		DetachedCriteria dc = DetachedCriteria.forClass(Reward.class);
		dc.add(Restrictions.eq("isDelete", false));
		dc.add(Restrictions.eq("status", "1"));
		dc.addOrder(Order.desc("sort"));
		List<Reward> gift = this.findEntities(dc);
		return gift;
	}

	/** 
	 * Description：查询某人获得的礼物数量
	 * @param liveId
	 * @return
	 * @return int
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public int findByLiveId(Integer liveId) {
		String sql="SELECT \n" +
				"  IFNULL(SUM(ogs.`price`),0)  \n" +
				"FROM\n" +
				"  `user_coin_increase` uci\n" +
				"   JOIN oe_gift_statement ogs \n" +
				"    ON uci.order_no_gift = ogs.id \n" +
				"WHERE uci.change_type = 3 \n" +
				"  AND ogs.live_id ="+liveId+" ";
		return (int) this.getNamedParameterJdbcTemplate().getJdbcOperations().queryForObject(sql, Integer.class);
	}

	/** 获取收到的礼物
	 * Description：
	 * @param userId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @return Object
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public Object getReceivedGift(String userId, Integer pageNumber,
			Integer pageSize) {
//		String sql="SELECT ogs.id orderNo,ogs.`gift_name` giftName,ogs.`count`,ogs.`price`,ogs.`create_time` createTime,ou.`name` "
//				+ "FROM `oe_gift_statement` ogs LEFT JOIN oe_user ou ON ou.`id`=ogs.`giver` "
//				+ "WHERE ogs.`receiver`=:userId order by ogs.`create_time` desc";
		String sql="SELECT ogs.id orderNo,ogs.`gift_name` giftName,ogs.`count`,ogs.`create_time` createTime,ou.`name` ,ROUND(ifnull(uci.`value`,0),2) gain"
				+ " FROM `oe_gift_statement` ogs left join `user_coin_consumption` ucc on ucc.`order_no_gift`=ogs.`id` left join `user_coin_increase` uci on uci.`order_no_gift`=ucc.`order_no_gift` AND uci.change_type=3  LEFT JOIN oe_user ou ON ou.`id` = ogs.`giver`"
				+ "WHERE ogs.`receiver`=:userId ORDER BY ogs.`create_time` DESC ";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        Page<ReceivedGift> page = this.findPageBySQL(sql.toString(), paramMap, ReceivedGift.class, pageNumber, pageSize);
		return page;
	}


	/** 
	 * Description：获取收到的打赏
	 * @param userId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @return Object
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public Object getReceivedReward(String userId, Integer pageNumber,
			Integer pageSize) {
		String sql="SELECT ors.`order_no` orderNo,ors.`price`,ou.`name`,ors.`create_time` createTime,ROUND(ifnull(uci.`value`,0),2) gain FROM `oe_reward_statement` ors LEFT JOIN oe_user ou "
				+ "ON ou.`id`=ors.`giver` left join `user_coin_increase` uci on uci.`order_no_reward`=ors.`id` and uci.`change_type`=4 "
				+ "WHERE ors.`receiver`= :userId  order by ors.`create_time` desc";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        Page<ReceivedReward> page = this.findPageBySQL(sql.toString(), paramMap, ReceivedReward.class, pageNumber, pageSize);
		return page;
	}

    public Object getLiveCourseByUserId(String userId, Integer pageNumber, Integer pageSize) {
		String sql="SELECT \n" +
				"  a.*,\n" +
				"  COUNT(a.argcid) enrollmentCount\n" +
				"FROM\n" +
				"  (SELECT \n" +
				"    argc.`course_id` id,\n" +
				"    argc.id argcid,\n" +
				"    oc.`grade_name` courseName,\n" +
				"    oc.`start_time` startTime,\n" +
				"    oc.`end_time` endTime,\n" +
				"    oc.`current_price` price \n" +
				"  FROM\n" +
				"    `oe_course` oc \n" +
				"    JOIN `oe_user` ou \n" +
				"      ON oc.`user_lecturer_id` = ou.`id` \n" +
				"    JOIN `apply_r_grade_course` argc \n" +
				"      ON oc.id = argc.`course_id` \n" +
				"    LEFT JOIN \n" +
				"      (SELECT \n" +
				"        aa.* \n" +
				"      FROM\n" +
				"        `oe_order_detail` aa \n" +
				"        JOIN `oe_order` bb \n" +
				"          ON aa.`order_id` = bb.`id` \n" +
				"          AND bb.`order_status` = 1) ood \n" +
				"      ON oc.id = ood.`course_id` \n" +
				"  WHERE oc.`is_delete` = 0 \n" +
				"    AND oc.`type` = 1 \n" +
				"    AND ou.id = :userId \n" +
				"  GROUP BY argc.id) a \n" +
				"GROUP BY a.id \n" +
				"ORDER BY a.`startTime` DESC ";

		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("userId", userId);
		Page<LiveCourseVO> page = this.findPageBySQL(sql.toString(), paramMap, LiveCourseVO.class, pageNumber, pageSize);
		for (int i = 0; i < page.getItems().size(); i++) {
			LiveCourseVO liveCourseVO = page.getItems().get(i);
			liveCourseVO.setTotalAmount(getLiveCourseTotalAmountById(liveCourseVO.getId()));
		}
		return page;
    }


	public String getLiveCourseTotalAmountById(String courseId) {
		String sql="SELECT \n" +
				"  SUM(ap.actualPay) actualPay \n" +
				"FROM\n" +
				"  (SELECT \n" +
				"    IFNULL(ood.`actual_pay`, 0) actualPay \n" +
				"  FROM\n" +
				"    `apply_r_grade_course` argc \n" +
				"    JOIN `oe_course` oc \n" +
				"      ON argc.`course_id` = oc.id \n" +
				"    JOIN `oe_user` ou \n" +
				"      ON argc.`user_id` = ou.`id` \n" +
				"    LEFT JOIN `oe_order_detail` ood \n" +
				"      ON ood.`course_id` = oc.id \n" +
				"  WHERE oc.`is_delete` = 0 \n" +
				"    AND argc.`is_payment` IN (0, 2) \n" +
				"    AND oc.id = :courseId \n" +
				"  GROUP BY argc.id \n" +
				"  ORDER BY argc.`create_time`) ap ";

		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("courseId", courseId);
		String totalAmount = this.getNamedParameterJdbcTemplate().queryForObject(sql, paramMap, String.class);
		return totalAmount;
	}


	public Object getLiveCourseUsersById(String id, String userId, Integer pageNumber, Integer pageSize) {
		String sql="SELECT \n" +
				"  ou.`login_name` loginName,\n" +
				"  ou.name,\n" +
				"  argc.`create_time` createTime,\n" +
				"  IFNULL(ood.`actual_pay`,0) actualPay\n" +
				"FROM\n" +
				"  `apply_r_grade_course` argc \n" +
				"  JOIN `oe_course` oc \n" +
				"    ON argc.`course_id` = oc.id \n" +
				"  JOIN `oe_user` ou \n" +
				"  ON argc.`user_id` = ou.`id`" +
				"  LEFT JOIN `oe_order_detail` ood\n" +
				"  ON ood.`course_id`=oc.id\n" +
				"  WHERE oc.`is_delete`=0\n" +
				"  AND argc.`is_payment` IN (0,2) AND oc.`user_lecturer_id`=:userId AND oc.id=:id\n" +
				" GROUP BY argc.id ORDER BY argc.`create_time`";

		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("userId", userId);
		paramMap.put("id", id);
		Page<LiveCourseUserVO> page = this.findPageBySQL(sql.toString(), paramMap, LiveCourseUserVO.class, pageNumber, pageSize);
		return page;
	}

//	public List<Map<String, Object>> getGiftRecord(String courseId) {
//		DetachedCriteria dc = DetachedCriteria.forClass(GiftStatement.class);
//		dc.add(Restrictions.eq("isDelete", false));
//		dc.add(Restrictions.eq("status", "1"));
//		List<GiftStatement> gs = this.findEntities(dc);
//		return null;
//	}
	
}
