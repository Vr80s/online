package com.xczhihui.bxg.online.web.service;


import com.xczhihui.bxg.online.common.domain.WxcpPayFlow;

import java.sql.SQLException;
import java.util.List;

public interface WxcpPayFlowService {
	
	public int insert(WxcpPayFlow record) throws SQLException;

	
}
