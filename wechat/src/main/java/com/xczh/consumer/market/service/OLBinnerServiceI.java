package com.xczh.consumer.market.service;

import com.xczh.consumer.market.bean.WxcpBinner;

import java.util.List;

public interface OLBinnerServiceI {
	
	public List<WxcpBinner> binnerList() throws Exception;
	
	public void binnerClick(String binner_id) throws Exception;
	

}
