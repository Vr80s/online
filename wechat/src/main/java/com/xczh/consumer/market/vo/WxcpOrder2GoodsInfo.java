package com.xczh.consumer.market.vo;

import com.xczh.consumer.market.bean.WxcpOrderInfo;

import java.util.ArrayList;
import java.util.List;

//import com.xczh.consumer.market.bean.WxcpGoodsInfo;

//订单、订单2商品、商品
public class WxcpOrder2GoodsInfo {
	
	private WxcpOrderInfo wxcpOrderInfo;
	private List<WxcpOrderGoodsExt> listWxcpOrderGoodsExt = new ArrayList<WxcpOrderGoodsExt>();
	
	public WxcpOrderInfo getWxcpOrderInfo() {
		return wxcpOrderInfo;
	}
	public void setWxcpOrderInfo(WxcpOrderInfo wxcpOrderInfo) {
		this.wxcpOrderInfo = wxcpOrderInfo;
	}
	
	public List<WxcpOrderGoodsExt> getListWxcpOrderGoodsExt() {
		return listWxcpOrderGoodsExt;
	}
	public void setListWxcpOrderGoodsExt(List<WxcpOrderGoodsExt> listWxcpOrderGoodsExt) {
		this.listWxcpOrderGoodsExt = listWxcpOrderGoodsExt;
	}
		
}
