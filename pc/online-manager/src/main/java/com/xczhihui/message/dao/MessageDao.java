package com.xczhihui.message.dao;

import java.util.HashMap;
import java.util.Map;

import com.xczhihui.common.dao.HibernateDao;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.web.UserVo;
import com.xczhihui.order.vo.MessageShortVo;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.common.domain.Message;
import com.xczhihui.message.vo.MessageVo;
import com.xczhihui.utils.TimeUtil;

@Repository
public class MessageDao extends HibernateDao<Menu> {

	/**
	 * 返回指定页面的消息推送
	 * 
	 * @param vNameOrCreater
	 *            指定条件
	 * @param orderByName
	 *            排序字段
	 * @param pageNumber
	 *            页码
	 * @param pageSize
	 *            页大小
	 * @return 页对象
	 */
	public Page<Message> findPageMessages(
			String vNameOrCreater, String orderByName, int pageNumber,
			int pageSize) {
		DetachedCriteria dc = DetachedCriteria.forClass(Message.class);
		dc.add(Restrictions.eq("isDelete", false));
		dc.add(Restrictions.eq("type", (short) 1));
		dc.add(Restrictions.eq("status", (short) 1));
		if (StringUtils.isNotEmpty(vNameOrCreater)) {
			dc.add(Restrictions.like("title", "%" + vNameOrCreater + "%"));
		}
		Order order = Order.desc(orderByName);
		dc.addOrder(order);
		return this.findPageByCriteria(dc, pageNumber, pageSize);
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
	 * @return 页对象
	 */
	public Page<Message> findPageMessages(
			MessageVo vo, String orderByName, int pageNumber, int pageSize) {
		DetachedCriteria dc = DetachedCriteria.forClass(Message.class);
		dc.add(Restrictions.eq("isDelete", false));
		dc.add(Restrictions.eq("type", 1));
		dc.add(Restrictions.eq("status", (short) 1));
		if (vo.getUserIds() != null && vo.getUserIds().length > 0) {
			dc.add(Restrictions.in("userId", vo.getUserIds()));
		}
		if (StringUtils.isNotEmpty(vo.getContext())) {
			dc.add(Restrictions.like("context", "%" + vo.getContext() + "%"));
		}
		if (StringUtils.isNotEmpty(vo.getTitle())) {
			dc.add(Restrictions.like("title", "%" + vo.getTitle() + "%"));
		}
		if (!StringUtils.isEmpty(vo.getTime_start())) {
			dc.add(Restrictions.ge("createTime",
					TimeUtil.parseDate(vo.getTime_start() + " 00:00:00")));
		}
		if (!StringUtils.isEmpty(vo.getTime_end())) {
			dc.add(Restrictions.le("createTime",
					TimeUtil.parseDate(vo.getTime_end() + " 23:59:59")));
		}
		Order order = Order.desc(orderByName);
		dc.addOrder(order);
		return this.findPageByCriteria(dc, pageNumber, pageSize);
	}

	/**
	 * 返回指定用户下推送消息
	 * 
	 * @param vNameOrCreater
	 *            查询字段
	 * @param orderByName
	 *            排序字段
	 * @param pageNumber
	 *            页码
	 * @param pageSize
	 *            页大小
	 * @return 分页对象
	 */
	public Page<Message> findPageMessagesByUser(
			UserVo userVo, String vNameOrCreater, String orderByName,
			int pageNumber, int pageSize) {
		DetachedCriteria dc = DetachedCriteria.forClass(Message.class);
		dc.add(Restrictions.eq("isDelete", false));
		dc.add(Restrictions.eq("type", 1));
		dc.add(Restrictions.eq("status", (short) 1));
		dc.add(Restrictions.eq("userId", userVo.getId()));
		if (StringUtils.isNotEmpty(vNameOrCreater)) {
			dc.add(Restrictions.like("title", "%" + vNameOrCreater + "%"));
		}
		Order order = Order.desc(orderByName);
		dc.addOrder(order);
		return this.findPageByCriteria(dc, pageNumber, pageSize);
	}

	// /**
	// * 根据条件查询一页数据
	// * @param groups 所有组
	// * @param page 分页条件对象
	// * @return 分页结果
	// */
	// public PageVo findPagevideo(Groups groups, PageVo page){
	// StringBuffer sql = new StringBuffer();
	// sql.append(" select a.id as id,"
	// + "a.title as title,"
	// + "a.vimg as vimg,"
	// + "a.create_time as crateTime,"
	// + "b.status as status,"
	// + "b.id as bid,"
	// + "a.create_person as person,"
	// + "a.info as info ");
	// sql.append(" from oe_video a left join oe_r_tag_video_menu b on a.id = b.video_id left join oe_tag c on b.tag_id = c.id ");
	// page = findPageByGroups(groups, page, sql.toString());
	// return page;
	// }

	/**
	 * 删除一条数据(伪删除)
	 * 
	 * @param id
	 *            视频编号
	 */
	public void deleteById(String id) {
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE oe_message a SET a.is_delete = 1 WHERE a.id = :id ");
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		this.getNamedParameterJdbcTemplate().update(sql.toString(), map);
	}

	/**
	 * 根据id批量删除
	 * 
	 * @param ids
	 *            id列表
	 * @return 影响行数
	 */
	public int deleteBatch(String... ids) {
		final String hql = "update Message set isDelete = :isDelete where id in (:ids)";
		return this.getHibernateTemplate().execute(
				new HibernateCallback<Integer>() {
					@Override
					public Integer doInHibernate(Session session)
							throws HibernateException {
						Query query = session.createQuery(hql);
						query.setParameterList("ids", ids);
						query.setParameter("isDelete", true);
						return query.executeUpdate();
					}
				});
	}

	// /**
	// * 修改状态(伪删除)
	// * @param id 视频标签表编号
	// * @param status 状态
	// */
	// public void updateStatus(String id,String status){
	// StringBuilder sql = new StringBuilder();
	// if("true".equals(status)){
	// sql.append(" UPDATE oe_r_tag_video_menu a SET a.status = 0 WHERE a.id = :id ");
	// }else{
	// sql.append(" UPDATE oe_r_tag_video_menu a SET a.status = 1 WHERE a.id = :id ");
	// }
	//
	// Map<String, String> map = new HashMap<String, String>();
	// map.put("id", id);
	// this.getNamedParameterJdbcTemplate().update(sql.toString(),map);
	// }

	/**
	 * 发送消息
	 * 
	 * @param messageShortVo
	 */
	public void saveMessage(MessageShortVo messageShortVo) {
		String sql = "insert into oe_message (id,user_id,context,type,status,create_person ) values "
				+ "(:id,:user_id,:context,:type,1,:create_person)";
		this.getNamedParameterJdbcTemplate().update(sql,
				new BeanPropertySqlParameterSource(messageShortVo));
	}
}
