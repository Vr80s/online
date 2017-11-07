package com.xczh.consumer.market.service.impl;

import com.xczh.consumer.market.bean.WxcpBinner;
import com.xczh.consumer.market.dao.OLBinnerMapper;
import com.xczh.consumer.market.service.OLBinnerServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OLBinnerServiceImpl implements OLBinnerServiceI {

	@Autowired
	private OLBinnerMapper wxcpBinnerMapper;

	/***
	 * 轮播图列表
	 */
	@Override
	public List<WxcpBinner> binnerList() throws Exception {
		List<WxcpBinner> list = wxcpBinnerMapper.binnerList();
		return list;
	}

	@Override
	public void binnerClick(String binner_id) throws Exception {
		wxcpBinnerMapper.binnerClick(binner_id);
	}
}
