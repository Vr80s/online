package com.xczhihui.order.dao;

import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.order.vo.ShareOrderVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class ShareOrderDao extends SimpleHibernateDao {
	public Page<ShareOrderVo> findShareOrderPage(ShareOrderVo shareOrderVo,
			int pageNumber, int pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(" SELECT "
				+ "	oe.id createPerson, " + "	oe.id , "
				+ "	oe.name createPersonName, " + "	oe.sex, " + "	oe.mobile, "
				+ "	oe.email, " + "	oe.create_time, " + "	oe.parent_id, "
				+ "	oe2.`name` shareUserName " + " FROM " + "	oe_user oe "
				+ " LEFT JOIN oe_user oe2 ON oe2.id = oe.parent_id where 1=1");

		if (shareOrderVo.getStartTime() != null) {
			sql.append(" and oe.create_time >=:startTime");
			paramMap.put("startTime", shareOrderVo.getStartTime());
		}

		if (shareOrderVo.getStopTime() != null) {
			sql.append(" and DATE_FORMAT(oe.create_time,'%Y-%m-%d') <=:stopTime");
			paramMap.put("stopTime", shareOrderVo.getStopTime());
		}

		if (shareOrderVo.getMobile() != null) {

			sql.append(" and oe.mobile like :mobile ");
			paramMap.put("mobile", "%" + shareOrderVo.getMobile() + "%");
		}

		if (shareOrderVo.getEmail() != null) {
			sql.append(" and oe.email like :email ");
			paramMap.put("email", "%" + shareOrderVo.getEmail() + "%");
		}

		if (shareOrderVo.getCreatePersonName() != null) {
			sql.append(" and oe.name like :createPersonName ");
			paramMap.put("createPersonName",
					"%" + shareOrderVo.getCreatePersonName() + "%");
		}

		if (shareOrderVo.getShareUserName() != null) {
			sql.append(" and oe2.name like :shareUserName ");
			paramMap.put("shareUserName", "%" + shareOrderVo.getShareUserName()
					+ "%");
		}

		sql.append(" order by oe.create_time desc ");
		// 先取出该取得用户
		Page<ShareOrderVo> ms = this.findPageBySQL(sql.toString(), paramMap,
				ShareOrderVo.class, pageNumber, pageSize);

		// "	(select count(1) from oe_user ou where ou.parent_id = oe.id) shareCount,"
		// +//#总共发展人数
		// "	(select IFNULL(sum(t2.subsidies),0) from oe_share_order t2 where t2.target_user_id = oe.id ) totalShareMoney, "
		// +//#累计佣金
		// "	0 getShareMoney " +
		List<ShareOrderVo> list = ms.getItems();

		for (int i = 0; i < list.size(); i++) {
			String sqlShareCount = "select count(1) from oe_user ou where ou.parent_id = ? ";
			list.get(i).setShareCount(
					this.queryForInt(sqlShareCount, new Object[] { list.get(i)
							.getId() }));
			String sqlTotalShareMoney = "select IFNULL(sum(t2.subsidies),0) from oe_share_order t2 where t2.target_user_id = ?";
			list.get(i).setTotalShareMoney(
					this.getNamedParameterJdbcTemplate()
							.getJdbcOperations()
							.queryForObject(sqlTotalShareMoney,
									new Object[] { list.get(i).getId() },
									Double.class));
			list.get(i).setGetShareMoney(0d);
		}
		return ms;
	}

	// public Page<ShareOrderVo> findShareOrderPage(ShareOrderVo shareOrderVo,
	// int pageNumber, int pageSize){
	// Map<String,Object> paramMap=new HashMap<String,Object>();
	// StringBuilder sql=new StringBuilder(" SELECT " +
	// "	oe.id createPerson, " +
	// "	oe.name createPersonName, " +
	// "	oe.sex, " +
	// "	oe.mobile, " +
	// "	oe.email, " +
	// "	oe.create_time, " +
	// "	oe.parent_id, " +
	// "	oe2.`name` shareUserName, " +
	// "	(select count(1) from oe_user ou where ou.parent_id = oe.id) shareCount,"
	// +//#总共发展人数
	// "	(select IFNULL(sum(t2.subsidies),0) from oe_share_order t2 where t2.target_user_id = oe.id ) totalShareMoney, "
	// +//#累计佣金
	// "	0 getShareMoney " +
	// " FROM " +
	// "	oe_user oe " +
	// " LEFT JOIN oe_user oe2 ON oe2.id = oe.parent_id where 1=1");
	//
	// if(shareOrderVo.getStartTime() !=null){
	// sql.append(" and oe.create_time >=:startTime");
	// paramMap.put("startTime", shareOrderVo.getStartTime());
	// }
	//
	// if(shareOrderVo.getStopTime() !=null){
	// sql.append(" and DATE_FORMAT(oe.create_time,'%Y-%m-%d') <=:stopTime");
	// paramMap.put("stopTime", shareOrderVo.getStopTime());
	// }
	//
	// if(shareOrderVo.getMobile()!=null){
	//
	// sql.append(" and oe.mobile like :mobile ");
	// paramMap.put("mobile", "%"+shareOrderVo.getMobile()+"%");
	// }
	//
	// if(shareOrderVo.getEmail()!=null){
	// sql.append(" and oe.email like :email ");
	// paramMap.put("email", "%"+shareOrderVo.getEmail()+"%");
	// }
	//
	// if(shareOrderVo.getCreatePersonName()!=null){
	// sql.append(" and oe.name like :createPersonName ");
	// paramMap.put("createPersonName", "%" + shareOrderVo.getCreatePersonName()
	// + "%");
	// }
	//
	// if(shareOrderVo.getShareUserName()!=null){
	// sql.append(" and oe2.name like :shareUserName ");
	// paramMap.put("shareUserName", "%" + shareOrderVo.getShareUserName() +
	// "%");
	// }
	//
	// sql.append(" order by oe.create_time desc ");
	// //System.out.println("sql:"+sql.toString());
	// //先取出该取得用户
	// Page<ShareOrderVo> ms = this.findPageBySQL(sql.toString(), paramMap,
	// ShareOrderVo.class, pageNumber, pageSize);
	// return ms;
	// }

	public Page<ShareOrderVo> findShareOrderDetailPage(
			ShareOrderVo shareOrderVo, int pageNumber, int pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder("SELECT " + "	oso.order_no, "
				+ "	oso.share_order_no, " + "	oso.course_name, "
				+ "	oso.buy_user_id, " + "	ou.login_name createPersonName, "
				+ "	oso.actual_pay, " + "	oso.pay_time, " + "	oso.`level`, "
				+ "	oso.subsidies " + "FROM " + "	oe_share_order oso "
				+ "JOIN oe_user ou ON oso.buy_user_id = ou.id where 1=1 ");

		if (shareOrderVo.getTargetUserId() != null) {
			sql.append(" and oso.target_user_id = :targetUserid");
			paramMap.put("targetUserid", shareOrderVo.getTargetUserId());
		}

		if (shareOrderVo.getStartTime() != null) {
			sql.append(" and oso.pay_time >=:startTime");
			paramMap.put("startTime", shareOrderVo.getStartTime());
		}

		if (shareOrderVo.getStopTime() != null) {
			sql.append(" and DATE_FORMAT(oso.pay_time,'%Y-%m-%d') <=:stopTime");
			paramMap.put("stopTime", shareOrderVo.getStopTime());
		}

		if (shareOrderVo.getShareOrderNo() != null) {
			sql.append(" and oso.share_order_no like :shareOrderNo ");
			paramMap.put("shareOrderNo", "%" + shareOrderVo.getShareOrderNo()
					+ "%");
		}

		if (shareOrderVo.getCourseName() != null) {
			sql.append(" and oso.course_name like :courseName ");
			paramMap.put("courseName", "%" + shareOrderVo.getCourseName() + "%");
		}

		if (shareOrderVo.getCreatePersonName() != null) {
			sql.append(" and ou.name like :createPersonName ");
			paramMap.put("createPersonName",
					"%" + shareOrderVo.getCreatePersonName() + "%");
		}

		if (shareOrderVo.getLevel() != null) {
			sql.append(" and oso.level = :level ");
			paramMap.put("level", shareOrderVo.getLevel());
		}

		sql.append(" order by oso.order_no desc,oso.level asc ");

		// System.out.println("sql2:"+sql.toString());
		// 先取出该取得用户
		Page<ShareOrderVo> ms = this.findPageBySQL(sql.toString(), paramMap,
				ShareOrderVo.class, pageNumber, pageSize);
		return ms;
	}
}