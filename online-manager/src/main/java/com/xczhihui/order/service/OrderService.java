package com.xczhihui.order.service;

import java.util.List;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.order.vo.OrderVo;

public interface OrderService {

	public Page<OrderVo> findOrderPage(OrderVo orderVo, Integer pageNumber,
			Integer pageSize);

	/**
	 * 逻辑批量删除
	 * 
	 * @return void
	 */
	public void deletes(String[] ids);

	public List getOrderPreferenty(String orderNo);

}
