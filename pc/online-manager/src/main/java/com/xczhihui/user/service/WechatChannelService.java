package com.xczhihui.user.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.user.vo.WechatChannelVo;

public interface WechatChannelService {

	
	/**
	 * 新增课程
	 * 
	 * @return void
	 */
	public void addWechatChannel(WechatChannelVo WechatChannelVo) throws IllegalAccessException, InvocationTargetException;

	/**
	 * 修改课程
	 * 
	 * @return void
	 */
	public void updateWechatChannel(WechatChannelVo WechatChannelVo) throws IllegalAccessException, InvocationTargetException;

	public WechatChannelVo getWechatChannelById(Integer WechatChannelId);

	/**
	 * 根据条件分页获取课程信息。
	 * 
	 * @param groups
	 * @param pageVo
	 * @return
	 */
	public Page<WechatChannelVo> findWechatChannelPage(WechatChannelVo WechatChannelVo, int pageNumber,
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
	public void deleteWechatChannelById(Integer id);

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

	List<WechatChannelVo> getWechatChannellist(String search);

	public void updateBrokerage(String ids, String brokerage);

	List<WechatChannelVo> findWechatChannelList();
}
