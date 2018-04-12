package com.xczhihui.order.dao;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.order.vo.OrderVo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class OrderDao extends SimpleHibernateDao {

	public Page<OrderVo> findOrderPage(OrderVo orderVo, int pageNumber,
			int pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(
				" SELECT "
						+ "	oo.id, "
						+ "	group_concat(oc.grade_name) as courseName, "
						+ "	group_concat(oc.id) as courseId, "
						+ "	oo.order_no, "
						+ "	oo.create_time, "
						+
						/* "	ifnull(oo.preferenty_way,'无') preferenty_way, " + */
						"	oo.preferenty_money, "
						+ "	oo.actual_pay, "
						+ "	oo.create_person, "
						+ "	ou.name createPersonName, "
						+ "	oo.pay_type, "
						+ "	oo.pay_time, "
						+ "	oo.order_status, "
						+ "	(select IFNULL(sum(oso.subsidies),0) from oe_share_order oso where oo.order_no = oso.order_no) subsidies, "
						+ "	oo.order_from  "
						+ " FROM "
						+ "	oe_order oo "
						+ " LEFT JOIN oe_order_detail ood ON (oo.id = ood.order_id) "
						+ " LEFT JOIN oe_course oc ON (ood.course_id = oc.id) "
						+ " JOIN oe_user ou ON (oo.user_id = ou.id) where oo.is_delete = 0 ");

		if (orderVo.getStartTime() != null) {
			sql.append(" and oo.create_time >=:startTime");
			paramMap.put("startTime", orderVo.getStartTime());
		}

		if (orderVo.getStopTime() != null) {
			sql.append(" and DATE_FORMAT(oo.create_time,'%Y-%m-%d') <=:stopTime");
			paramMap.put("stopTime", orderVo.getStopTime());
		}

		if (orderVo.getOrderStatus() != null) {
			sql.append(" and oo.order_status = :orderStatus ");
			paramMap.put("orderStatus", orderVo.getOrderStatus());
		} else {
			sql.append(" and oo.order_status = :orderStatus ");
			paramMap.put("orderStatus", 1);
		}

		if (orderVo.getPayType() != null) {
			sql.append(" and oo.pay_type = :payType ");
			paramMap.put("payType", orderVo.getPayType());
		}

		if (orderVo.getCourseName() != null
				&& !"".equals(orderVo.getCourseName())) {
			sql.append(" and oc.grade_name like :courseName ");
			paramMap.put("courseName", "%" + orderVo.getCourseName() + "%");
		}

		if (orderVo.getOrderNo() != null) {
			sql.append(" and oo.order_no like :orderNo ");
			paramMap.put("orderNo", "%" + orderVo.getOrderNo() + "%");
		}

		if (orderVo.getCreatePersonName() != null) {
			sql.append(" and ou.login_name like :createPersonName ");
			paramMap.put("createPersonName",
					"%" + orderVo.getCreatePersonName() + "%");
		}

		if (orderVo.getOrderFrom() != null) {
			sql.append(" and oo.order_from = :order_from ");
			paramMap.put("order_from", orderVo.getOrderFrom());
		}

		// System.out.println("查询语句："+sql.toString());
		sql.append(" GROUP BY oo.order_no  order by oo.create_time desc ");
		Page<OrderVo> ms = this.findPageBySQL(sql.toString(), paramMap,
				OrderVo.class, pageNumber, pageSize);

		return ms;
	}
}
