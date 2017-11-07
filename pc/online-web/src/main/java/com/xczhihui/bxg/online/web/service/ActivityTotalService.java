package com.xczhihui.bxg.online.web.service;

/**
 * 活动统计
 * @author Haicheng Jiang
 *
 */
public interface ActivityTotalService {
	public void addTotal(String fromCode);
	public void addTotalDetail4Reg(String fromCode,String loginName,String userName);
}
