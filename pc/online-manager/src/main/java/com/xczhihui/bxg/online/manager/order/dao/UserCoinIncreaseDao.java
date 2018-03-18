package com.xczhihui.bxg.online.manager.order.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.po.UserCoinIncrease;


@Repository
public class UserCoinIncreaseDao extends SimpleHibernateDao {

	public Page<UserCoinIncrease> findUserCoinIncreasePage(UserCoinIncrease orderVo, int pageNumber, int pageSize){
		   Map<String,Object> paramMap=new HashMap<String,Object>();
		   StringBuilder sql=new StringBuilder("SELECT \n" +
				   "  ou.`login_name` userId,\n" +
				   "  uci.`order_no_recharge` orderNoRecharge,\n" +
				   "  uci.`value`,\n" +
				   "  uci.`create_time`,\n" +
				   "  uci.`order_from`,\n" +
				   "  uci.`pay_type` \n" +
				   "FROM\n" +
				   "  `user_coin_increase` uci \n" +
				   "  LEFT JOIN oe_user ou \n" +
				   "    ON ou.`id` = uci.`user_id` \n" +
				   "WHERE uci.`change_type` = 1 \n" +
				   "  AND deleted = 0 \n" +
				   "  AND uci.status = 1 ");
		   if(orderVo.getStartTime() !=null){
			  sql.append(" and uci.create_time >=:startTime");
			  paramMap.put("startTime", orderVo.getStartTime());
		   }

		   if(orderVo.getStopTime() !=null){
			  sql.append(" and DATE_FORMAT(uci.create_time,'%Y-%m-%d') <=:stopTime");
			  paramMap.put("stopTime", orderVo.getStopTime());
		   }

		   if(orderVo.getPayType()!=null){
			   sql.append(" and uci.pay_type = :payType ");
			   paramMap.put("payType", orderVo.getPayType());
		   }

	       if(orderVo.getOrderFrom()!=null){
	    	   sql.append(" and uci.order_from = :orderFrom ");
	    	   paramMap.put("orderFrom", orderVo.getOrderFrom());
	       }
	       
	       if(orderVo.getOrderNoRecharge()!=null){
	    	   sql.append(" and uci.order_no_recharge like :orderNo ");
	    	   paramMap.put("orderNo", "%" + orderVo.getOrderNoRecharge() + "%");
	       }
	       
	       if(orderVo.getUserId()!=null){
	    	   sql.append(" and ou.login_name like :loginName ");
	    	   paramMap.put("loginName", "%" + orderVo.getUserId() + "%");
	       }
	       
		   sql.append(" order by uci.create_time desc ");
		   Page<UserCoinIncrease> ms = this.findPageBySQL(sql.toString(), paramMap, UserCoinIncrease.class, pageNumber, pageSize);
		   
	  	   return ms;
	}
}

