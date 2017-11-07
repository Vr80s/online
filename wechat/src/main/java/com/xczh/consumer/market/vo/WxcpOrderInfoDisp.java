package com.xczh.consumer.market.vo;

import java.util.ArrayList;
import java.util.List;

public class WxcpOrderInfoDisp {
	
	/**主键**/
	private String id;
	
	/**订单人性化编号，不用关键字id(UUID)；**/
	private String order_no;

	/**订单状态**/
	private Integer order_status;
	
	/**订单原价(以分为单位)**/
	private Integer origin_fee;
	
	/**订单包含商品信息**/
	List<WxcpGoodsInfoDisp> goods_info = new ArrayList<WxcpGoodsInfoDisp>();
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public Integer getOrder_status() {
		return order_status;
	}

	public void setOrder_status(Integer order_status) {
		this.order_status = order_status;
	}

	public Integer getOrigin_fee() {
		return origin_fee;
	}

	public void setOrigin_fee(Integer origin_fee) {
		this.origin_fee = origin_fee;
	}

	public List<WxcpGoodsInfoDisp> getGoods_info() {
		return goods_info;
	}

	public void setGoods_info(List<WxcpGoodsInfoDisp> goods_info) {
		this.goods_info = goods_info;
	}

	/**订单包含商品数量**/
	private Integer goods_sum;

	public Integer getGoods_sum() {
		return goods_sum;
	}

	public void setGoods_sum(Integer goods_sum) {
		this.goods_sum = goods_sum;
	}
	
}
