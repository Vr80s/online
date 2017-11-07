package com.xczhihui.bxg.online.manager.activity.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.manager.activity.vo.ActivityRuleVo;
import com.xczhihui.bxg.online.manager.utils.StringUtil;
@Repository
public class ActivityDetailDao extends SimpleHibernateDao{

	public Page<ActivityRuleVo> findActivityDetailPage(ActivityRuleVo activityRuleVo, Integer pageNumber,
			Integer pageSize) {
		// TODO Auto-generated method stub
		Map<String,Object> paramMap=new HashMap<String,Object>();
		   StringBuilder sql=new StringBuilder(" SELECT " +
				                               "	oar.id, " +
				                               "	oar.name, " +
				                               "	oar.url, " +
				                               "	oar.create_person, " +
				                               "	oar.create_time, " +
				                               "    SUM(oard.act_order_sum) as actOrderSum, " +
				                               "    SUM(oard.act_user_sum) as actUserSum, " +
				                               "    SUM(oard.act_order_money_total) as actOrderMoneyTotal, " +
				                               "	ou.name createPersonName ," +
				                               "	oar.start_time, " +
				                               "	oar.end_time " +
				                               "    FROM " +
				                               "	oe_activity_rule oar left join oe_activity_rule_data oard on oar.id = oard.act_id " +
				                               "    left join user ou on  oar.create_person = ou.login_name " +
				                               " where oar.start_time< now() ");
		   if(activityRuleVo.getName() != null && !"".equals(activityRuleVo.getName())){
			   sql.append(" and oar.name like :name");
			   paramMap.put("name", "%" + activityRuleVo.getName() + "%");
		   }
		   
		   if(activityRuleVo.getStartTime() !=null){
			  sql.append(" and oar.start_time >=:startTime ");
			  paramMap.put("startTime", activityRuleVo.getStartTime());
		   }

		   if(activityRuleVo.getEndTime() !=null){
			   sql.append(" and DATE_FORMAT(oar.end_time,'%Y-%m-%d') <=:endTime ");
			  paramMap.put("endTime", activityRuleVo.getEndTime());
		   }
		   
		   sql.append(" GROUP BY oar.id order by oar.create_time desc ");
		   
		   Page<ActivityRuleVo> ms = this.findPageBySQL(sql.toString(), paramMap, ActivityRuleVo.class, pageNumber, pageSize);
		   for(int i=0 ;i<ms.getItems().size();i++){
			   if(ms.getItems().get(i).getId()==null){
				   ms.getItems().remove(i);
			   }
			   
		   }
		  
   	   return ms;
	}

}
