package com.xczhihui.bxg.online.web.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.online.common.domain.Recharges;

@Repository
public class RechargesServiceDao extends SimpleHibernateDao {

	/** 
	 * Description：获取所有礼充值记录
	 * @return
	 * @return List<Recharges>
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public List<Recharges> getRecharges() {
		DetachedCriteria dc = DetachedCriteria.forClass(Recharges.class);
		dc.add(Restrictions.eq("isDelete", false));
		dc.add(Restrictions.eq("status", "1"));
		dc.addOrder(Order.desc("sort"));
		List<Recharges> recharges = this.findEntities(dc);
		return recharges;
	}
}
