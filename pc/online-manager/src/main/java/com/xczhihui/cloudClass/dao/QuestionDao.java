package com.xczhihui.cloudClass.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.QuesStore;
import com.xczhihui.common.dao.HibernateDao;

@Repository("quesStoreDao")
public class QuestionDao extends HibernateDao<QuesStore> {

	public Page<QuesStore> findQuesPage(QuesStore searchVo, int currentPage,
			int pageSize) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(
				"select DISTINCT(q.id) as uniqueId ,q.* from oe_question_kpoint qk,oe_question q where q.course_id = "
						+ searchVo.getCourseId()
						+ " and qk.course_id="
						+ searchVo.getCourseId()
						+ " and q.is_delete = 0 and qk.question_id=q.id");
		if (!"0".equals(searchVo.getChapterId())
				&& !"".equals(searchVo.getChapterId())
				&& searchVo.getChapterId() != null) {
			sql.append(" and qk.kpoint_id in( ");
			String[] ids = searchVo.getChapterId().split(",");
			for (int i = 0; i < ids.length; i++) {
				if (i != 0) {
					sql.append(",");
				}
				sql.append("'" + ids[i] + "'");
			}
			sql.append(" ) ");
		}
		if (!"".equals(searchVo.getQuestionType())
				&& searchVo.getQuestionType() != null) {
			sql.append(" and q.question_type =:quesType ");
			paramMap.put("quesType", searchVo.getQuestionType());
		}
		if (!"".equals(searchVo.getQuestionHeadText())
				&& searchVo.getQuestionHeadText() != null) {
			sql.append(" and q.question_head_text like :quesHeadText ");
			paramMap.put("quesHeadText", "%" + searchVo.getQuestionHeadText()
					+ "%");
		}
		sql.append(" order by q.create_time desc ");
		Page<QuesStore> ms = this.findPageBySQL(sql.toString(), paramMap,
				QuesStore.class, currentPage, pageSize);
		return ms;
	}

	/**
	 * 获取题目的所有知识点名称
	 * 
	 * @param id
	 *            题目id
	 * @return
	 */
	public List<String> getKnowledgePointsName(String id) {
		// TODO Auto-generated method stub
		String sql = "SELECT c.name from oe_chapter c LEFT JOIN oe_question_kpoint k on k.kpoint_id = c.id where k.question_id=:id";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		List<String> list = new ArrayList<String>();
		list = this.getNamedParameterJdbcTemplate().queryForList(sql, map,
				String.class);
		return list;
	}

	/**
	 * 获取题目的所有知识点id
	 * 
	 * @param id
	 * @return
	 */
	public List<String> getKnowledgePointsId(String id) {
		// TODO Auto-generated method stub
		String sql = "SELECT k.kpoint_id FROM oe_question_kpoint k WHERE k.question_id =:id";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return this.getNamedParameterJdbcTemplate().queryForList(sql, map,
				String.class);
	}

	/**
	 * 获取题目的所有知识点id
	 * 
	 * @param id
	 * @return
	 */
	public List<String> getKnowledgePointsByQuestionIds(List<String> ids) {
		// TODO Auto-generated method stub
		String sql = "SELECT distinct k.kpoint_id FROM oe_question_kpoint k WHERE k.question_id in (:ids)";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return this.getNamedParameterJdbcTemplate().queryForList(sql, map,
				String.class);
	}

}
