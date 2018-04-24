package com.xczhihui.order.service.impl;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.order.dao.ShareOrderDao;
import com.xczhihui.order.service.ShareOrderService;
import com.xczhihui.order.vo.ShareOrderVo;

@Service
public class ShareOrderServiceImpl extends OnlineBaseServiceImpl implements
		ShareOrderService {

	@Autowired
	private ShareOrderDao shareOrderDao;

	@Override
	public Page<ShareOrderVo> findShareOrderPage(ShareOrderVo shareOrderVo,
			Integer pageNumber, Integer pageSize) {
		Page<ShareOrderVo> page = shareOrderDao.findShareOrderPage(
				shareOrderVo, pageNumber, pageSize);
		return page;
	}

	@Override
	public Page<ShareOrderVo> findShareOrderDetailPage(
			ShareOrderVo shareOrderVo, Integer pageNumber, Integer pageSize) {
		Page<ShareOrderVo> page = shareOrderDao.findShareOrderDetailPage(
				shareOrderVo, pageNumber, pageSize);
		return page;
	}

	@Override
	public List getOrderDetailList(ShareOrderVo shareOrderVo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String sql = "SELECT " + "	buy_user_id buyUserId, "
				+ "	level,order_status, " + "	sum(oso.subsidies) subsidies "
				+ "FROM " + "	oe_share_order oso " + "WHERE 1=1";

		if (shareOrderVo.getTargetUserId() != null) {
			sql += " and oso.target_user_id = :targetUserid ";
			paramMap.put("targetUserid", shareOrderVo.getTargetUserId());
		}

		if (shareOrderVo.getOrderNo() != null) {
			sql += " and oso.order_no = :orderNo  ";
			paramMap.put("orderNo", shareOrderVo.getBuyUserId());
		}

		sql += " group by `order_status`";
		List list = shareOrderDao.getNamedParameterJdbcTemplate().queryForList(
				sql, paramMap);
		return list;
	}

	@Override
	public List getShareOrderDetail(ShareOrderVo shareOrderVo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String sql = "select " + "	oso.buy_user_id buy_user_id, "
				+ "	ou.login_name loginName, " + "	ou.`name`, "
				+ "	oso.subsidies, " + "	oso.`level` " + "FROM "
				+ "	oe_share_order oso "
				+ "JOIN oe_user ou ON oso.target_user_id = ou.id";

		if (shareOrderVo.getOrderNo() != null) {
			sql += " and oso.order_no = :orderNo  ";
			paramMap.put("orderNo", shareOrderVo.getOrderNo());
		}
		System.out.println("查询语句" + sql);
		List list = shareOrderDao.getNamedParameterJdbcTemplate().queryForList(
				sql, paramMap);
		return list;
	}
}
