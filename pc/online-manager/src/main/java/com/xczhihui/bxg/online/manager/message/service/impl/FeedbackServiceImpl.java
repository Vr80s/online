package com.xczhihui.bxg.online.manager.message.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Message;
import com.xczhihui.bxg.online.manager.message.dao.FeedBackDao;
import com.xczhihui.bxg.online.manager.message.service.FeedbackService;
import com.xczhihui.bxg.online.manager.message.vo.FeedBackVo;
import com.xczhihui.bxg.online.manager.message.vo.MessageVo;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.PageVo;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.web.auth.UserHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * 问题反馈service
 * @author duanqh
 *
 */
@Service
public class FeedbackServiceImpl extends OnlineBaseServiceImpl  implements FeedbackService {

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
	public com.xczhihui.bxg.common.util.bean.Page<MessageVo> findPageMessages(MessageVo vo, int pageNumber, int pageSize) throws InvocationTargetException, IllegalAccessException {
		com.xczhihui.bxg.common.util.bean.Page<Message> page = this.dao.findPageMessages(vo, "answerStatus", pageNumber, pageSize);
		List<Message> items = page.getItems();
		List<MessageVo> itemsVo = new ArrayList<MessageVo>();
		for (Message q : items) {
			MessageVo dest = new MessageVo();
			BeanUtils.copyProperties(dest, q);
			String sql = "select t.name from oe_user t where t.id = :id";
			Map paramMap = new HashMap();
			paramMap.put("id", dest.getUserId());
			String userName = dao.getNamedParameterJdbcTemplate().queryForObject(sql, paramMap, String.class);
			try {
				dest.setUserName(userName);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			itemsVo.add(dest);
		}
		com.xczhihui.bxg.common.util.bean.Page<MessageVo> pageVo = new com.xczhihui.bxg.common.util.bean.Page<MessageVo>(itemsVo,
				page.getTotalCount(),
				page.getPageSize(), page.getCurrentPage());
		return pageVo;
	}
	@Override
	public void updateStatus(String id) {
		Message m = dao.get(id, Message.class);
		if(m.isDelete()){
			m.setDelete(false);
		}else{
			m.setDelete(true);
		}
		dao.update(m);
	}

	@Override
	public void addContext(FeedBackVo vo) {
		BxgUser u = UserHolder.getCurrentUser();
		Message message = new Message();
		message.setLastTime(new Date());
		message.setContext("<font color=\"#2cb82c\">意见反馈：</font>"+vo.getReplytext());
		message.setPid(vo.getId());
		message.setUserId(vo.getUserId());
		message.setType(0);
		message.setStatus((short) 1);
		message.setTitle(vo.getTitle());
//		message.setReplytext(vo.getReplytext());
		message.setCreateTime(new Date());
		message.setCreatePerson(u.getName());
		message.setReadstatus((short)0);
		dao.save(message);
		Message feekMessage=dao.get(vo.getId(), Message.class);
		feekMessage.setAnswerStatus((short) 1);
		feekMessage.setLastTime(new Date());
		dao.update(feekMessage);
	}

	@Override
	public Message findFeekBackByFeedId(String feedId) {
		return dao.findFeekBackByFeedId(feedId);
	}

}
