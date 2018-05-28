package com.xczhihui.bxg.online.web.service.impl;



import com.xczhihui.bxg.online.common.domain.WxcpPayFlow;
import com.xczhihui.bxg.online.web.dao.WxcpPayFlowDao;
import com.xczhihui.bxg.online.web.service.WxcpPayFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * 
 * 支付详情表；
 * 
 **/
@Service
public class WxcpPayFlowServiceImpl implements WxcpPayFlowService {
	
	@Autowired
	private WxcpPayFlowDao wxcpPayFlowDao;
	

	@Override
	public int insert(WxcpPayFlow record) throws SQLException {
		return wxcpPayFlowDao.insert(record);
	}

	
}
