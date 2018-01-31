package com.xczhihui.wechat.course.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.xczhihui.wechat.course.vo.OnlineUserVO;


public interface IMyInfoService {
	
	/**
	 * Description：根据用户id查找得到学员数、课程数、熊猫币
	 * @param userId
	 * @return
	 * @return List<BigDecimal>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	List<BigDecimal> selectCollegeCourseXmbNumber(String userId);
	
	
	List<Map<String,Object>> selectSettlementList(String id);


	List<Map<String,Object>> selectWithdrawalList(String id);


	void updateUserSetInfo(OnlineUserVO user);
}
