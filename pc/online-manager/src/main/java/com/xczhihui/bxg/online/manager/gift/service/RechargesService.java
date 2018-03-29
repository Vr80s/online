package com.xczhihui.bxg.online.manager.gift.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.manager.gift.vo.RechargesVo;

public interface RechargesService {

	
	/**
	 * 新增课程
	 * 
	 * @return void
	 */
	public void addRecharges(RechargesVo rechargesVo) throws IllegalAccessException, InvocationTargetException;

	/**
	 * 修改课程
	 * 
	 * @return void
	 */
	public void updateRecharges(RechargesVo rechargesVo) throws IllegalAccessException, InvocationTargetException;

	public RechargesVo getRechargesById(Integer rechargesId);

	/**
	 * 根据条件分页获取课程信息。
	 * 
	 * @param groups
	 * @param pageVo
	 * @return
	 */
	public Page<RechargesVo> findRechargesPage(RechargesVo rechargesVo, int pageNumber,
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
	public void deleteRechargesById(Integer id);

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

	List<RechargesVo> getRechargeslist(String search);

	public void updateBrokerage(String ids, String brokerage);
}
