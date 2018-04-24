package com.xczhihui.ask.dao;

import com.xczhihui.ask.vo.QuestionVo;
import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.util.bean.Page;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 问题管理层类
 *
 * @author 王高伟
 * @create 2016-10-13 18:03:39
 */
@Repository
public class QuestionDao extends SimpleHibernateDao {

	public Page<QuestionVo> findQuestionPage(QuestionVo questionVo,
			int pageNumber, int pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder("SELECT " + "	oaq.id, "
				+ "	oaq.title, " + "	oaq.ment_id, " + "	oaq.name mentName, "
				+ "	oaq.answer_sum, " + "	oaq.browse_sum, "
				+ "	oaq.create_person, " + "	ou.name createPersonName, "
				+ "	oaq.create_time, " + "	oaq.status, " + "	oaq.tags, "
				+ "	ou.origin, " + "	ou.type " + " FROM "
				+ "	oe_ask_question oaq JOIN " +
				// "	oe_menu om ON(oaq.ment_id = om.id) JOIN  " +
				"	oe_user ou ON(oaq.user_id = ou.id and oaq.is_delete = 0)");
		if ("1".equals(questionVo.getStatus())) {// 未解决
			sql.append(" and oaq.status < 2");
		}
		if ("2".equals(questionVo.getStatus())) {// 未解决
			sql.append(" and oaq.status = 2");
		}
		if (questionVo.getTitle() != null && !"".equals(questionVo.getTitle())) {
			sql.append(" and oaq.title like :title ");
			paramMap.put("title", "%" + questionVo.getTitle() + "%");
		}
		if (questionVo.getCreatePersonName() != null
				&& !"".equals(questionVo.getCreatePersonName())) {
			sql.append(" and ou.name like :name");
			paramMap.put("name", "%" + questionVo.getCreatePersonName() + "%");
		}
		if (questionVo.getStartTime() != null) {
			sql.append(" and oaq.create_time >=:startTime");
			paramMap.put("startTime", questionVo.getStartTime());
		}
		if (questionVo.getStopTime() != null) {
			sql.append(" and DATE_FORMAT(oaq.create_time,'%Y-%m-%d') <=:stopTime");
			paramMap.put("stopTime", questionVo.getStopTime());
		}
		if (questionVo.getMentId() != null
				&& !"".equals(questionVo.getMentId())
				&& questionVo.getMentId() != 0) {
			sql.append(" and oaq.ment_id = " + questionVo.getMentId() + " ");
			paramMap.put("ment_id", questionVo.getMentId());
		}
		if (questionVo.getTags() != null && !"".equals(questionVo.getTags())) {
			sql.append(" and instr(concat(',',oaq.tags,','),:tags) > 0 ");
			paramMap.put("tags", "," + questionVo.getTags() + ",");
		}
		if (questionVo.getOrigin() != null
				&& !"".equals(questionVo.getOrigin())) {
			sql.append(" and ou.origin = :origin ");
			paramMap.put("origin", questionVo.getOrigin());
		}
		if (questionVo.getType() != null && !"".equals(questionVo.getType())) {
			sql.append(" and ou.type = :type ");
			paramMap.put("type", questionVo.getType());
		}

		if (questionVo.getAnswerSum() != null && questionVo.getAnswerSum() > 0) {
			sql.append(" order by oaq.answer_sum desc,oaq.create_time desc");
		} else if (questionVo.getBrowseSum() != null
				&& questionVo.getBrowseSum() > 0) {
			sql.append(" order by oaq.browse_sum desc, oaq.create_time desc");
		} else {
			sql.append(" order by oaq.create_time desc ");
		}
		Page<QuestionVo> ms = this.findPageBySQL(sql.toString(), paramMap,
				QuestionVo.class, pageNumber, pageSize);

		return ms;
	}

	/**
	 * 验证状态是否已经处理 返回true已处理 其他 未处理
	 * 
	 * @param questionVo
	 * @return
	 */
	public boolean checkQuestionStatus(QuestionVo questionVo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String sql = "select oaq.status from oe_ask_question oaq where oaq.id = :id";
		paramMap.put("id", questionVo.getId());
		String status = this.getNamedParameterJdbcTemplate().queryForObject(
				sql, paramMap, String.class);
		return "2".equals(status) ? true : false;
	}
}
