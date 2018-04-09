package com.xczhihui.bxg.online.web.dao;

import java.util.HashMap;
import java.util.Map;

import com.xczhihui.bxg.online.common.domain.CourseAnchor;
import com.xczhihui.bxg.online.api.vo.MyConsumptionCoinRecords;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.UserCoin;
import com.xczhihui.bxg.online.common.domain.UserCoinIncrease;
import com.xczhihui.bxg.online.api.vo.RechargeRecord;


/** 
 * ClassName: GiftDao.java <br>
 * Description:礼物打赏相关 <br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年8月16日<br>
 */
@Repository
public class UserCoinDao extends SimpleHibernateDao {


	/** 
	 * Description：获取用户代币余额信息
	 * @return
	 * @return List<Gift>
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public UserCoin getBalanceByUserId(String userId) {
		DetachedCriteria dc = DetachedCriteria.forClass(UserCoin.class);
		dc.add(Restrictions.eq("userId", userId));
		UserCoin uc = this.findEntity(dc);
		return uc;
	}
	
	/** 
	 * Description：我的充值记录
	 * @param userId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @return Page<RechargeRecord>
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public Page<RechargeRecord> getUserCoinIncreaseRecord(String userId,
			Integer pageNumber, Integer pageSize) {
		
		String sql="select uci.`order_no_recharge` orderNo,uci.`create_time`,ROUND(uci.`value`) value,'支付宝' as payType ,apr.`total_amount` total,uci.`balance` as balance"
				+ " from `user_coin_increase` uci ,`alipay_payment_record` apr where uci.`change_type`=1 and uci.`user_id`=:userId and uci.`order_no_recharge` = apr.`out_trade_no`"
				+ " union "
				+ "select uci.`order_no_recharge` orderNo,uci.`create_time`,ROUND(uci.`value`) value,'支付宝' as payType ,aprh.`total_amount` total,uci.`balance` as balance"
				+ " from `user_coin_increase` uci ,`alipay_payment_record_h5` aprh where uci.`change_type`=1 and uci.`user_id`=:userId and uci.`order_no_recharge` = aprh.`out_trade_no`"
				+ " union "
				+ "select uci.`order_no_recharge` orderNo,uci.`create_time`,ROUND(uci.`value`) value,'微信' as payType , CAST((wpf.`total_fee`/100) AS DECIMAL(18,2)) AS total,uci.`balance` as balance"
				+ " from `user_coin_increase` uci ,`wxcp_pay_flow` wpf where uci.`change_type`=1 and uci.`user_id`=:userId and uci.`order_no_recharge` = wpf.`out_trade_no`"

				+ " union "
				+ "select uci.`order_no_recharge` orderNo,uci.`create_time`,ROUND(uci.`value`) value,'applePay' as payType , ii.actual_price AS total,uci.`balance` as balance"
				+ " from `user_coin_increase` uci ,`iphone_iap` ii where uci.`change_type`=1 and uci.`user_id`=:userId and uci.`order_no_recharge` = ii.`order_no`"

				+ " ORDER BY `create_time` DESC";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        Page<RechargeRecord> page = this.findPageBySQL(sql.toString(), paramMap, RechargeRecord.class, pageNumber, pageSize);
		return page;
	}

	public UserCoinIncrease getUserCoinIncreaseRecordByOrder(String orderNo) {
		DetachedCriteria dc = DetachedCriteria.forClass(UserCoinIncrease.class);
		dc.add(Restrictions.eq("orderNoRecharge", orderNo));
		UserCoinIncrease uc = this.findEntity(dc);
		return uc;
	}

	public Object consumptionCoinList(String userId, Integer pageNumber, Integer pageSize) {
		String sql="SELECT \n" +
				"  * \n" +
				"FROM\n" +
				"  (SELECT \n" +
				"    ucc.id orderNo,\n" +
				"    CONCAT_WS(\n" +
				"      '-',\n" +
				"      '购买礼物',\n" +
				"      ogs.`gift_name`\n" +
				"    ) remark,\n" +
				"    ucc.`create_time` TIME,\n" +
				"    ogs.`count`,\n" +
				"    ROUND(ucc.value) total \n" +
				"  FROM\n" +
				"    `user_coin_consumption` ucc,\n" +
				"    `oe_gift_statement` ogs \n" +
				"  WHERE ucc.`order_no_gift` = ogs.`id` \n" +
				"    AND ucc.`change_type` = 8 \n" +
				"    AND ucc.user_id = :userId \n" +
				"  UNION\n" +
				"  SELECT \n" +
				"    ucc.id orderNo,\n" +
				"    CONCAT_WS(\n" +
				"      '-',\n" +
				"      '购买课程',\n" +
				"      oc.`grade_name`\n" +
				"    ) remark,\n" +
				"    ucc.`create_time` TIME,\n" +
				"    1 AS COUNT,\n" +
				"    ROUND(ucc.value) total \n" +
				"  FROM\n" +
				"    `user_coin_consumption` ucc,\n" +
				"    `oe_order` oo,\n" +
				"    `oe_order_detail` ood,\n" +
				"    `oe_course` oc \n" +
				"  WHERE ucc.`order_no_consume` = oo.order_no \n" +
				"    AND ood.order_id = oo.id \n" +
				"    AND ood.course_id = oc.id \n" +
				"    AND ucc.`change_type` = 10 \n" +
				"    AND ucc.user_id = :userId) a \n" +
				"ORDER BY a.TIME DESC ";
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("userId", userId);
		Page<MyConsumptionCoinRecords> page = this.findPageBySQL(sql.toString(), paramMap, MyConsumptionCoinRecords.class, pageNumber, pageSize);
		return page;
	}

	/**
	 * Description：获取主播分成比例信息
	 * creed: Talk is cheap,show me the code
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 * @Date: 下午 9:35 2018/1/25 0025
	 **/
    public CourseAnchor getCourseAnchor(String receiverId) {
		DetachedCriteria dc = DetachedCriteria.forClass(CourseAnchor.class);
		dc.add(Restrictions.eq("userId", receiverId));
		CourseAnchor uc = this.findEntity(dc);
		return uc;
    }
}
