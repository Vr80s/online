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

import java.util.HashMap;
import java.util.Map;

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
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(
				"select om.id as id,om.user_id as userId,om.title as title,om.type as type,om.context as context," +
						"om.status as status,om.create_person as createPerson,om.create_time as createTime,om.lasttime as lastTime," +
						" om.pid as pid,om.readstatus as readstatus,om.answerStatus as answerStatus from oe_message om LEFT JOIN oe_user ou ON ou.id=om.user_id " +
						"where 1=1 and om.is_delete = 0 and om.type = 2 ");
		if (StringUtils.isNotEmpty(vo.getTitle())) {
			paramMap.put("title", "%" + vo.getTitle() + "%");
			sql.append("and om.title like :title ");
		}
		if (StringUtils.isNotEmpty(vo.getUserName())) {
			paramMap.put("userName", "%" + vo.getUserName() + "%");
			sql.append("and ou.login_name like :userName ");
		}
		if (vo.getAnswerStatus() != null && vo.getAnswerStatus() != -1) {
				paramMap.put("answerStatus", vo.getAnswerStatus());
				sql.append("and om.answerStatus = :answerStatus ");
		}
		if (!org.springframework.util.StringUtils.isEmpty(vo.getTime_start())) {
			sql.append(" and om.create_time >=:startTime");
			paramMap.put("startTime",
					TimeUtil.parseDate(vo.getTime_start() + " 00:00:00"));
		}
		if (!org.springframework.util.StringUtils.isEmpty(vo.getTime_end())) {
			sql.append(" and om.create_time <=:endTime");
			paramMap.put("endTime",
					TimeUtil.parseDate(vo.getTime_end() + " 23:59:59"));
		}
		sql.append(" order by om.answerStatus,om.create_time ");
		return this.findPageBySQL(sql.toString(), paramMap,
				Message.class, pageNumber, pageSize);
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
