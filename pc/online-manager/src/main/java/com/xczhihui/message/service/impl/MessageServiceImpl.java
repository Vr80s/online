package com.xczhihui.message.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Message;
import com.xczhihui.bxg.online.common.domain.MessageRecord;
import com.xczhihui.bxg.online.common.domain.User;
import com.xczhihui.common.web.UserVo;
import com.xczhihui.message.dao.MessageDao;
import com.xczhihui.message.service.MessageService;
import com.xczhihui.message.vo.MessageVo;
import com.xczhihui.user.dao.UserDao;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TimeUtil;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.util.bean.Page;
import org.springframework.util.StringUtils;

@Service
public class MessageServiceImpl extends OnlineBaseServiceImpl implements
		MessageService {

	@Autowired
	private MessageDao dao;

	@Autowired
	private UserDao userDao;

	public void setDao(MessageDao dao) {
		this.dao = dao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public List<Group> getAllGroup() {
		return null;
	}

	@Override
	public List<MessageVo> getOccupationTree() {
		return null;
	}

	@Override
	public Page findPageMessage(Groups groups, Page page) {
		return null;
	}

	@Override
	public void deleteById(String id) {
		this.dao.deleteById(id);
	}

	@Override
	public void deleteBatch(String... ids) {
		List<String> list = new ArrayList<String>();
		for (String id : ids) {
			list.add(id);
		}
		dao.deleteLogics(Message.class, list);
	}

	@Override
	public Message findMessageById(String vid) {
		return null;
	}

	@Override
	public Page<MessageVo> findPageMessages(String vNameOrCreater,
			int pageNumber, int pageSize) throws InvocationTargetException,
			IllegalAccessException {
		Page<Message> page = this.dao.findPageMessages(vNameOrCreater,
				"createTime", pageNumber, pageSize);
		List<Message> items = page.getItems();
		List<MessageVo> itemsVo = new ArrayList<MessageVo>();
		for (Message q : items) {
			MessageVo dest = new MessageVo();
			BeanUtils.copyProperties(dest, q);
			User user = userDao.findOneEntitiyByProperty(User.class, "id",
					q.getUserId());
			dest.setUserName(user.getName());
			itemsVo.add(dest);
		}
		Page<MessageVo> pageVo = new Page<MessageVo>(itemsVo,
				page.getTotalCount(), page.getPageSize(), page.getCurrentPage());
		return pageVo;
	}

	@Override
	public Page<MessageVo> findPageMessages(MessageVo vo, int pageNumber,
			int pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(
				"select * from oe_message_record where 1=1 ");

		if (!StringUtils.isEmpty(vo.getTime_start())) {
			sql.append(" and create_time >=:startTime");
			paramMap.put("startTime",
					TimeUtil.parseDate(vo.getTime_start() + " 00:00:00"));
		}
		if (!StringUtils.isEmpty(vo.getTime_end())) {
			sql.append(" and create_time <=:endTime");
			paramMap.put("endTime",
					TimeUtil.parseDate(vo.getTime_end() + " 23:59:59"));
		}
		sql.append(" order by create_time desc");
		Page<MessageVo> ms = dao.findPageBySQL(sql.toString(), paramMap,
				MessageVo.class, pageNumber, pageSize);
		return ms;
	}

	@Override
	public Page<MessageVo> findPageMessagesByUser(UserVo userVo,
			String vNameOrCreater, int pageNumber, int pageSize) {
		Page<Message> page = this.dao.findPageMessagesByUser(userVo,
				vNameOrCreater, "createTime", pageNumber, pageSize);
		List<Message> items = page.getItems();
		List<MessageVo> itemsVo = new ArrayList<MessageVo>();
		User user = userDao.findOneEntitiyByProperty(User.class, "id",
				userVo.getId());
		for (Message q : items) {
			MessageVo dest = new MessageVo();
			try {
				BeanUtils.copyProperties(dest, q);
				dest.setUserName(user.getName());
			} catch (Exception e) {
				// logger.error(e.getMessage());
			}
			itemsVo.add(dest);
		}
		Page<MessageVo> pageVo = new Page<MessageVo>(itemsVo,
				page.getTotalCount(), page.getPageSize(), page.getCurrentPage());
		return pageVo;
	}

	/**
	 *
	 * @param message
	 */
	@Override
	public void save(Message message) {
		dao.save(message);
	}

	/**
	 *
	 * @param message
	 */
	@Override
	public void saveMessage(Message message) {
		String sql = "insert into oe_message (id,user_id,context,type,status,create_person,create_time,readstatus) values "
				+ "(:id,:userId,:context,type,status,:createPerson,:createTime,:readstatus) ";

		KeyHolder kh = new GeneratedKeyHolder();
		dao.getNamedParameterJdbcTemplate().update(sql,
				new BeanPropertySqlParameterSource(message), kh);
		// message.setId(kh.getKey().intValue());
	}

	@Override
	public void saveMessageRecord(MessageRecord messageRecord) {
		// TODO Auto-generated method stub
		dao.save(messageRecord);
	}
}
