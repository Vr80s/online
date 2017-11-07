package com.xczhihui.bxg.online.manager.order.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.po.EnchashmentApplication;


@Repository
public class EnchashmentDao extends SimpleHibernateDao {
	 
	public Page<EnchashmentApplication> findEnchashmentPage(EnchashmentApplication orderVo, int pageNumber, int pageSize){
		   Map<String,Object> paramMap=new HashMap<String,Object>();
		   StringBuilder sql=new StringBuilder("SELECT ea.id,ea.`enchashment_status`,ea.`time`,ea.`enchashment_sum`,ea.enable_enchashment_balance enableEnchashmentBalance,"
		   		+ "ea.`client_type`,ou.`login_name` loginName,ea.`enchashment_account`,ea.`enchashment_account_type`, "
		   		+ "ea.`real_name`,ea.phone,ea.tickling_time,ea.operate_remark operateRemark,ea.cause_type "
		   		+ "FROM `enchashment_application` ea JOIN oe_user ou ON ou.`id`=ea.`user_id` "
		   		+ "where 1=1 ");

		   if(orderVo.getStartTime() !=null){
			  sql.append(" and ea.time >=:startTime");
			  paramMap.put("startTime", orderVo.getStartTime());
		   }

		   if(orderVo.getStopTime() !=null){
			  sql.append(" and DATE_FORMAT(ea.time,'%Y-%m-%d') <=:stopTime");
			  paramMap.put("stopTime", orderVo.getStopTime());
		   }

		   if(orderVo.getEnchashmentStatus()!=null){
			  sql.append(" and ea.enchashment_status = :orderStatus ");
			  paramMap.put("orderStatus", orderVo.getEnchashmentStatus());
		   }
		   
		   if(orderVo.getEnchashmentAccountType()!=null){
			   sql.append(" and ea.enchashment_account_type = :payType ");
			   paramMap.put("payType", orderVo.getEnchashmentAccountType());
		   }
		   
//		   if(orderVo.getCourseName()!=null && !"".equals(orderVo.getCourseName())){
//		       sql.append(" and oc.grade_name like :courseName ");
//		       paramMap.put("courseName", "%" + orderVo.getCourseName() + "%");
//		   }

	       if(orderVo.getId()!=null){
	    	   sql.append(" and CAST(ea.`id` AS CHAR) LIKE :orderNo ");
	    	   paramMap.put("orderNo", "%" + orderVo.getId() + "%");
	       }
	         
	       if(orderVo.getUserId()!=null){
	    	   sql.append(" and ou.name like :createPersonName ");
	    	   paramMap.put("createPersonName", "%" + orderVo.getUserId() + "%");
	       }
	       
	       if(orderVo.getClientType()!=null){
	    	   sql.append(" and ea.client_type = :order_from ");
	    	   paramMap.put("order_from", orderVo.getClientType());
	       }
		   
//		   System.out.println("查询语句："+sql.toString());
		   sql.append(" order by ea.time desc ");
		   Page<EnchashmentApplication> ms = this.findPageBySQL(sql.toString(), paramMap, EnchashmentApplication.class, pageNumber, pageSize);
//		   List<EnchashmentApplication> eas = ms.getItems();
//		   for (EnchashmentApplication enchashmentApplication : eas) {
//			   enchashmentApplication.setEnableEnchashmentBalance(enchashmentApplication.getEnableEnchashmentBalance().divide(new BigDecimal(rate), 2,RoundingMode.DOWN));
//		   }
	  	   return ms;
	}

}

