package com.xczhihui.bxg.online.web.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.web.vo.UserFcodeVo;

/**
 * F码
 * @author Haicheng Jiang
 */
public interface FcodeService {
	/**
	 * 查询我的优惠码
	 * @param userId
	 * @param status
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<UserFcodeVo> findMyFcode(String userId,Integer status,Integer pageNumber, Integer pageSize);
	/**
	 * 领取优惠码
	 * @param userId
	 * @param fcode
	 */
	public void addUserFcode(String userId,String fcode);
}
