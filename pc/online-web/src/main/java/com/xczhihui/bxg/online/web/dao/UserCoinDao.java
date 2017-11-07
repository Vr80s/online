package com.xczhihui.bxg.online.web.dao;

import java.util.HashMap;
import java.util.Map;

import com.xczhihui.bxg.online.api.vo.MyConsumptionCoinRecords;
import com.xczhihui.bxg.online.web.vo.MyConsumptionRecords;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.po.UserCoin;
import com.xczhihui.bxg.online.api.po.UserCoinIncrease;
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
		String sql="select uci.`order_no_recharge` orderNo,uci.`create_time`,ROUND(uci.`value`) value,'支付宝' as payType ,apr.`total_amount` total"
				+ " from `user_coin_increase` uci ,`alipay_payment_record` apr where uci.`change_type`=1 and uci.`user_id`=:userId and uci.`order_no_recharge` = apr.`out_trade_no`"
				+ " union "
				+ "select uci.`order_no_recharge` orderNo,uci.`create_time`,ROUND(uci.`value`) value,'支付宝' as payType ,aprh.`total_amount` total"
				+ " from `user_coin_increase` uci ,`alipay_payment_record_h5` aprh where uci.`change_type`=1 and uci.`user_id`=:userId and uci.`order_no_recharge` = aprh.`out_trade_no`"
				+ " union "
				+ "select uci.`order_no_recharge` orderNo,uci.`create_time`,ROUND(uci.`value`) value,'微信' as payType , CAST((wpf.`total_fee`/100) AS DECIMAL(18,2)) AS total"
				+ " from `user_coin_increase` uci ,`wxcp_pay_flow` wpf where uci.`change_type`=1 and uci.`user_id`=:userId and uci.`order_no_recharge` = wpf.`out_trade_no`"

				+ " union "
				+ "select uci.`order_no_recharge` orderNo,uci.`create_time`,ROUND(uci.`value`) value,'applePay' as payType , ii.actual_price AS total"
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
		String sql="SELECT ucc.id orderNo,  CONCAT_WS('-','购买礼物',ogs.`gift_name`) remark,ucc.`create_time` TIME,  ogs.`count`, ROUND(ucc.value) total FROM" +
				"  `user_coin_consumption` ucc,  `oe_gift_statement` ogs WHERE ucc.`order_no_gift` = ogs.`id`   AND ucc.`change_type` = 8 " +
				" and ucc.user_id=:userId order by ucc.`create_time` desc";
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("userId", userId);
		Page<MyConsumptionCoinRecords> page = this.findPageBySQL(sql.toString(), paramMap, MyConsumptionCoinRecords.class, pageNumber, pageSize);
		return page;
	}
}
