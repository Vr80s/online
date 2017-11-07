package com.xczhihui.bxg.online.manager.order.service;

import java.util.List;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.manager.order.vo.ShareOrderVo;


public interface ShareOrderService {

	public Page<ShareOrderVo> findShareOrderPage(ShareOrderVo shareOrderVo, Integer pageNumber, Integer pageSize);
	public Page<ShareOrderVo> findShareOrderDetailPage(ShareOrderVo shareOrderVo, Integer pageNumber, Integer pageSize);
	public List getOrderDetailList(ShareOrderVo shareOrderVo);
	public List getShareOrderDetail(ShareOrderVo shareOrderVo);
}
