package com.xczhihui.message.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Message;
import com.xczhihui.message.dao.FeedBackDao;
import com.xczhihui.message.service.FeedbackService;
import com.xczhihui.message.vo.FeedBackVo;
import com.xczhihui.message.vo.MessageVo;
import com.xczhihui.support.shiro.ManagerUserUtil;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.PageVo;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 问题反馈service
 * 
 * @author duanqh
 *
 */
@Service
public class FeedbackServiceImpl extends OnlineBaseServiceImpl implements
		FeedbackService {

	/**
	 * 意见反馈Dao层
	 */
	@Autowired
	public FeedBackDao dao;

	@Override
	public PageVo findPageFeedBack(Groups groups, PageVo page) {
		page = dao.findPageFeedBack(groups, page);
		return page;
	}

	@SuppressWarnings("unchecked")
	@Override
	public com.xczhihui.bxg.common.util.bean.Page<MessageVo> findPageMessages(
			MessageVo vo, int pageNumber, int pageSize)
			throws InvocationTargetException, IllegalAccessException {
		com.xczhihui.bxg.common.util.bean.Page<Message> page = this.dao
				.findPageMessages(vo, "answerStatus", pageNumber, pageSize);
		List<Message> items = page.getItems();
		List<MessageVo> itemsVo = new ArrayList<MessageVo>();
		for (Message q : items) {
			MessageVo dest = new MessageVo();
			BeanUtils.copyProperties(dest, q);
			if (dest.getUserId() != null) {
				String sql = "select t.login_name as loginName from oe_user t where t.id = :id";
				Map paramMap = new HashMap();
				paramMap.put("id", dest.getUserId());
				System.out.println("userId" + dest.getUserId());
				// String userName =
				// dao.getNamedParameterJdbcTemplate().queryForObject(sql,
				// paramMap, String.class);
				List<Map<String, Object>> list = dao
						.getNamedParameterJdbcTemplate().queryForList(sql,
								paramMap);
				if (list.size() > 0) {
					try {
						dest.setUserName(list.get(0).get("loginName")
								.toString());
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			} else {
				dest.setUserName("游客");
			}
			itemsVo.add(dest);
		}

		com.xczhihui.bxg.common.util.bean.Page<MessageVo> pageVo = new com.xczhihui.bxg.common.util.bean.Page<MessageVo>(
				itemsVo, page.getTotalCount(), page.getPageSize(),
				page.getCurrentPage());
		return pageVo;
	}

	@Override
	public void updateStatus(String id) {
		Message m = dao.get(id, Message.class);
		if (m.isDelete()) {
			m.setDelete(false);
		} else {
			m.setDelete(true);
		}
		dao.update(m);
	}

	@Override
	public void addContext(FeedBackVo vo) {
		Message message = new Message();
		message.setLastTime(new Date());
		message.setContext("<font color=\"#2cb82c\">意见反馈：</font>"
				+ vo.getReplytext());
		message.setPid(vo.getId());
		message.setUserId(vo.getUserId());
		message.setType(0);
		message.setStatus((short) 1);
		message.setTitle(vo.getTitle());
		// message.setReplytext(vo.getReplytext());
		message.setCreateTime(new Date());
		message.setCreatePerson(ManagerUserUtil.getName());
		message.setReadstatus((short) 0);
		dao.save(message);
		Message feekMessage = dao.get(vo.getId(), Message.class);
		feekMessage.setAnswerStatus((short) 1);
		feekMessage.setLastTime(new Date());
		dao.update(feekMessage);
	}

	@Override
	public Message findFeekBackByFeedId(String feedId) {
		return dao.findFeekBackByFeedId(feedId);
	}

}
