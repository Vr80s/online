package com.xczhihui.bxg.online.web.dao;/**
 * Created by admin on 2016/9/19.
 */

import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.EnchashmentApplication;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/** 
 * ClassName: EnchashmentDao.java <br>
 * Description: 提现<br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年9月17日<br>
 */
@Repository
public class EnchashmentApplyDao extends SimpleHibernateDao {

	public Page<EnchashmentApplication> enchashmentApplicationList(
			String userId, Integer pageNumber, Integer pageSize) {
		String sql="SELECT ea.`id`,ea.`enchashment_sum`,ea.`time`,ea.`enchashment_account_type`,ea.`enchashment_status`,"
				+ "ea.`enable_enchashment_balance`,ea.`enchashment_account` "
				+ "FROM `enchashment_application` ea where user_id=:userId order by ea.time desc";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        Page<EnchashmentApplication> page = this.findPageBySQL(sql.toString(), paramMap, EnchashmentApplication.class, pageNumber, pageSize);
		return page;
	
	}

	public void saveEnchashmentApplication(EnchashmentApplication ea) {
		this.save(ea);
	}


}
