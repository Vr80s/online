package com.xczhihui.message.dao;

import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.common.domain.Message;
import com.xczhihui.common.dao.HibernateDao;
import com.xczhihui.message.vo.MessageVo;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.PageVo;
import com.xczhihui.utils.TimeUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 意见反馈
 * 
 * @author duanqh
 *
 */
@Repository
public class FeedBackDao extends HibernateDao<Menu> {

	/**
	 * 查询意见反馈分页
	 * 
	 * @param groups
	 *            组列表
	 * @param page
	 *            页大小
	 * @return 分页对象
	 */
	public PageVo findPageFeedBack(Groups groups, PageVo page) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select m.id as id," + "m.title as title,"
				+ "m.user_id as userId," + "m.status as status,"
				+ "m.answerStatus as answerStatus,"
				+ "m.create_time as createTime," + "m.lasttime as lastTime,"
				+ "m.create_person as anwerName," + "m.context as context,"
				+ "m.replytext as replytext," + "m.is_delete as isDelete "
				+ "from oe_message m order by create_time desc");
		page = findPageByGroups(groups, page, sql.toString());
		return page;
	}

	/**
	 * 返回指定页面的消息推送
	 * 
	 * @param vo
	 *            查询对象
	 * @param orderByName
	 *            排序字段
	 * @param pageNumber
	 *            页码
	 * @param pageSize
	 *            页大小
	 * @return 分页对象
	 */
	public com.xczhihui.bxg.common.util.bean.Page<Message> findPageMessages(
			MessageVo vo, String orderByName, int pageNumber, int pageSize) {
		DetachedCriteria dc = DetachedCriteria.forClass(Message.class);
		dc.add(Restrictions.eq("isDelete", false));
		dc.add(Restrictions.eq("type", 2));
		if (StringUtils.isNotEmpty(vo.getTitle())) {
			dc.add(Restrictions.like("title", "%" + vo.getTitle() + "%"));
		}
		if (vo.getAnswerStatus() != null && vo.getAnswerStatus() != -1) {
			if (vo.getAnswerStatus() != 0) {
				dc.add(Restrictions.eq("answerStatus", vo.getAnswerStatus()));
			} else {
				dc.add(Restrictions.or(
						Restrictions.eq("answerStatus", vo.getAnswerStatus()),
						Restrictions.isNull("answerStatus")));
			}
		}
		if (!StringUtils.isEmpty(vo.getTime_start())) {
			dc.add(Restrictions.ge("createTime",
					TimeUtil.parseDate(vo.getTime_start() + " 00:00:00")));
		}
		if (!StringUtils.isEmpty(vo.getTime_end())) {
			dc.add(Restrictions.le("createTime",
					TimeUtil.parseDate(vo.getTime_end() + " 23:59:59")));
		}
		Order order = Order.asc(orderByName);
		dc.addOrder(order);
		Order order1 = Order.desc("createTime");
		dc.addOrder(order1);
		return this.findPageByCriteria(dc, pageNumber, pageSize);
	}

	/**
	 * 根据ID查询消息对象
	 * 
	 * @param feedId
	 *            消息对象ID
	 * @return 消息对象
	 */
	public Message findFeekBackByFeedId(String feedId) {
		DetachedCriteria dc = DetachedCriteria.forClass(Message.class);
		dc.add(Restrictions.eq("isDelete", false));
		dc.add(Restrictions.eq("pid", feedId));
		return this.findEntity(dc);
	}

}
