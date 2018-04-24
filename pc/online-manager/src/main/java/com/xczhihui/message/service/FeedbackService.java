package com.xczhihui.message.service;

import com.xczhihui.bxg.online.common.domain.Message;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.message.vo.FeedBackVo;
import com.xczhihui.message.vo.MessageVo;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.PageVo;

import java.lang.reflect.InvocationTargetException;

/**
 * 问题反馈
 * 
 * @author duanqh
 *
 */
public interface FeedbackService {

	/**
	 * 初始化列表数据
	 * 
	 * @param groups
	 *            组列表
	 * @param page
	 *            分页查询对象
	 * @return 分页对象
	 */
	public PageVo findPageFeedBack(Groups groups, PageVo page);

	/**
	 * 删除or启用
	 * 
	 * @param id
	 *            编号
	 */
	public void updateStatus(String id);

	/**
	 * 添加反馈消息
	 * 
	 * @param vo
	 *            消息对象
	 */
	public void addContext(FeedBackVo vo);

	/**
	 * 查看指定意见的反馈
	 * 
	 * @param feedId
	 *            意见反馈编号
	 * @return 消息对象
	 */
	public Message findFeekBackByFeedId(String feedId);

	/**
	 * 查询所有反馈消息
	 * 
	 * @param vo
	 *            条件查询对象
	 * @param pageNumber
	 *            页码
	 * @param pageSize
	 *            页大小
	 * @return 分页对象
	 */
	public Page<MessageVo> findPageMessages(
			MessageVo vo, int pageNumber, int pageSize)
			throws InvocationTargetException, IllegalAccessException;

}
