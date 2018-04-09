package com.xczhihui.message.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Message;
import com.xczhihui.bxg.online.common.domain.MessageRecord;
import com.xczhihui.common.web.UserVo;
import com.xczhihui.message.vo.MessageVo;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;

/**
 * 消息服务
 * 
 * @author duanqh
 *
 */
public interface MessageService {

	/**
	 * 获取所有
	 * 
	 * @return 组集合
	 */
	public List<Group> getAllGroup();

	/**
	 * 获取职业路径树
	 * 
	 * @return 消息集合
	 */
	public List<MessageVo> getOccupationTree();

	/**
	 * 根据条件获取一页视频
	 * 
	 * @param groups
	 *            组
	 * @param page
	 *            分页查询对象
	 * @return 分页对象
	 */
	public Page findPageMessage(Groups groups, Page page);

	/**
	 * 根据id伪删除数据
	 * 
	 * @param id
	 *            指定ID
	 */
	public void deleteById(String id);

	/**
	 * 根据id多条删除
	 * 
	 * @param ids
	 *            ID列表
	 */
	public void deleteBatch(String... ids);

	/**
	 * 根据视频id获取视频
	 * 
	 * @param vid
	 * @return
	 */
	public Message findMessageById(String vid);

	/**
	 * 查询所有推送消息
	 * 
	 * @param vNameOrCreater
	 *            查询条件
	 * @param pageNumber
	 *            页码
	 * @param pageSize
	 *            页大小
	 * @return 分页对象
	 */
	public Page<MessageVo> findPageMessages(String vNameOrCreater,
			int pageNumber, int pageSize) throws InvocationTargetException,
			IllegalAccessException;

	/**
	 * 查询所有推送消息
	 * 
	 * @param vo
	 *            查询对象
	 * @param pageNumber
	 *            页码
	 * @param pageSize
	 *            页大小
	 * @return 分页对象
	 */
	public Page<MessageVo> findPageMessages(MessageVo vo, int pageNumber,
			int pageSize);

	/**
	 * 查询指定用户的推送消息
	 * 
	 * @param vNameOrCreater
	 *            查询字段
	 * @param pageNumber
	 *            页码
	 * @param pageSize
	 *            页大小
	 * @return 分页对象
	 */
	public Page<MessageVo> findPageMessagesByUser(UserVo userVo,
			String vNameOrCreater, int pageNumber, int pageSize);

	/**
	 * 保存推送信息
	 * 
	 * @param message
	 *            消息信息
	 */
	public void save(Message message);

	/**
	 * 保存信息记录
	 * 
	 * @param message
	 *            消息信息
	 */
	public void saveMessageRecord(MessageRecord messageRecord);

	public void saveMessage(Message message);
}
