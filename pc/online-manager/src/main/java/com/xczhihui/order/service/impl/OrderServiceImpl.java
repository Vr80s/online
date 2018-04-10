package com.xczhihui.order.service.impl;

import com.xczhihui.bxg.common.support.service.SystemVariateService;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xczhihui.order.dao.OrderDao;
import com.xczhihui.order.service.OrderService;
import com.xczhihui.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.order.vo.OrderVo;

@Service
public class OrderServiceImpl extends OnlineBaseServiceImpl implements
		OrderService {

	@Autowired
	private OrderDao orderDao;
	@Autowired
	private SystemVariateService sv;

	@Override
	public Page<OrderVo> findOrderPage(OrderVo orderVo, Integer pageNumber,
			Integer pageSize) {
		Page<OrderVo> page = orderDao.findOrderPage(orderVo, pageNumber,
				pageSize);
		return page;
	}

	@Override
	public void deletes(String[] ids) {
		// TODO Auto-generated method stub
		for (String id : ids) {
			String hqlPre = "from Order where isDelete=0 and id = ?";
			Order order = orderDao.findByHQLOne(hqlPre, new Object[] { id });
			if (order != null) {
				order.setDelete(true);
				orderDao.update(order);
			}
		}
	}

	@Override
	public List getOrderPreferenty(String orderNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String sql = "select "
				+ " t5.grade_name as courseName , "
				+ " t5.current_price as price , "
				+ " if(t4.id is not null, concat('满',t4.reach_money,'元减',t4.minus_money,'元'),t3.preferenty_type) as preferenty ,"
				+ "	t4.minus_money as minusMoney ,"
				+ "	t1.actual_pay as totalPay ,"
				+ " t2.actual_pay as actualPay "
				+ " from oe_course t5, "
				+ " oe_order t1, "
				+ " oe_order_detail t2 left join oe_order_preferenty_record t3 on t2.id=t3.order_detail_id left join oe_activity_rule_detail t4 on t2.activity_rule_detal_id = t4.id "
				+ " where  t5.id = t2.course_id and t1.id = t2.order_id and t1.order_no=:orderNo order by preferenty ";
		// String
		// sqlOld="select * from oe_order_preferenty_record where order_no=:orderNo";
		paramMap.put("orderNo", orderNo);
		List<Map<String, Object>> list = orderDao
				.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
		Double sum = 0.0;
		for (Map<String, Object> r : list) {
			if (r.get("preferenty") != null
					&& StringUtil.isNumber(r.get("preferenty").toString())) {
				r.put("preferenty", sv.getNameByValue("preferentyType",
						r.get("preferenty").toString()));
				r.put("actualPay",
						Double.parseDouble(r.get("actualPay").toString()) - 1000);

			}
			/*
			 * if(r.get("preferenty")!=null&&!StringUtil.isNumber(r.get("preferenty"
			 * ).toString())&&!"老学员优惠".equals(r.get("preferenty").toString())){
			 * sum=sum+Double.parseDouble(r.get("actualPay").toString());
			 * r.put("actualPay"
			 * ,sum-Double.parseDouble(r.get("minusMoney").toString()) ); }
			 */
		}
		return list;
	}

}
