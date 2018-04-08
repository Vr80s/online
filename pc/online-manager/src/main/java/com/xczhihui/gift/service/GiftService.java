package com.xczhihui.gift.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.gift.vo.GiftVo;

public interface GiftService {

	/**
	 * 新增课程
	 * 
	 * @return void
	 */
	public void addGift(GiftVo giftVo) throws IllegalAccessException, InvocationTargetException;

	/**
	 * 修改课程
	 * 
	 * @return void
	 */
	public void updateGift(GiftVo giftVo) throws IllegalAccessException, InvocationTargetException;

	public GiftVo getGiftById(Integer giftId);

	/**
	 * 根据条件分页获取课程信息。
	 * 
	 * @param groups
	 * @param pageVo
	 * @return
	 */
	public Page<GiftVo> findGiftPage(GiftVo giftVo, int pageNumber,
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
	 * 逻辑删除
	 * 
	 * @param Integer
	 *            id
	 * @return
	 */
	public void deleteGiftById(Integer id);

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

	/**
	 * 删除
	 * 
	 * @param Integer
	 *            id
	 * @return
	 */
	public void deletes(String[] ids);

	List<GiftVo> getGiftlist(String search);

	public void updateBrokerage(String ids, String brokerage);

}
