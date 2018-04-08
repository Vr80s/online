package com.xczhihui.gift.service;

import java.lang.reflect.InvocationTargetException;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.gift.vo.RewardVo;

public interface RewardService {

	/**
	 * 修改课程
	 * 
	 * @return void
	 */
	public void updateReward(RewardVo giftVo) throws IllegalAccessException, InvocationTargetException;

	public RewardVo getRewardById(Integer giftId);

	/**
	 * 根据条件分页获取课程信息。
	 * 
	 * @param groups
	 * @param pageVo
	 * @return
	 */
	public Page<RewardVo> findRewardPage(RewardVo giftVo, int pageNumber,
			int pageSize);

	/**
	 * 修改状态(禁用or启用)
	 * 
	 * @param Integer
	 *            id
	 * @return
	 */
	public void updateStatus(Integer id);


	/**
	 * 上移
	 * 
	 * @param Integer
	 *            id
	 * @return
	 */
	public void updateSortUp(Integer id);

	/**
	 * 下移
	 * 
	 * @param Integer
	 *            id
	 * @return
	 */
	public void updateSortDown(Integer id);


	public void updateBrokerage(String ids, String brokerage);

}
