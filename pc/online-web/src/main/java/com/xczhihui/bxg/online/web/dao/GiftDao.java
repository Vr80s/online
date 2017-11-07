package com.xczhihui.bxg.online.web.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.po.Gift;
import com.xczhihui.bxg.online.api.po.GiftStatement;
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
	 * @param userId
	 * @return
	 * @return int
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public int findByUserId(String userId) {
		String sql="select IFNULL(SUM(COUNT),0) from oe_gift_statement where receiver= \""+userId+"\"";
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

//	public List<Map<String, Object>> getGiftRecord(String courseId) {
//		DetachedCriteria dc = DetachedCriteria.forClass(GiftStatement.class);
//		dc.add(Restrictions.eq("isDelete", false));
//		dc.add(Restrictions.eq("status", "1"));
//		List<GiftStatement> gs = this.findEntities(dc);
//		return null;
//	}
	
}
