package com.xczhihui.barrier.dao;

import com.xczhihui.barrier.vo.ExamPaperVo;
import com.xczhihui.barrier.vo.PaperInfoVo;
import com.xczhihui.common.support.dao.SimpleHibernateDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Fudong.Sun【】
 * @Date 2016/12/22 10:41
 */
@Repository
public class MarkExamPapersDao extends SimpleHibernateDao {
	public PaperInfoVo getQuestionInfoByType(String record_id, Integer type) {
		StringBuilder sql = new StringBuilder();
		Map<String, Object> paramMap = new HashMap<>();
		sql.append("SELECT\n");
		sql.append("(SELECT count(id) from oe_barrier_question where barrier_record_id= :record_id and question_type= :type)as total_count,\n");
		sql.append("(SELECT count(id) from oe_barrier_question where barrier_record_id= :record_id and question_type= :type and is_right=1)as right_count,\n");
		sql.append("(SELECT count(id) from oe_barrier_question where barrier_record_id= :record_id and question_type= :type and is_right=0)as wrong_count,\n");
		sql.append("IFNULL((SELECT sum(question_score) from oe_barrier_question where barrier_record_id= :record_id and question_type= :type),0)as total_score,\n");
		sql.append("IFNULL((SELECT sum(result_score) from oe_barrier_question where barrier_record_id= :record_id and question_type= :type),0)as right_score");
		paramMap.put("record_id", record_id);
		paramMap.put("type", type);
		List<PaperInfoVo> list = this.findEntitiesByJdbc(PaperInfoVo.class,
				sql.toString(), paramMap);
		return list.size() > 0 ? list.get(0) : null;
	}

	public List<ExamPaperVo> getQuestionByType(String record_id, Integer type) {
		StringBuilder sql = new StringBuilder();
		Map<String, Object> paramMap = new HashMap<>();
		sql.append("SELECT question_type,question_head,options,options_picture,");
		sql.append("answer,solution,is_right,my_answer,question_score");
		sql.append(" from oe_barrier_question");
		sql.append(" where barrier_record_id= :record_id and question_type= :type");
		paramMap.put("record_id", record_id);
		paramMap.put("type", type);
		return this.findEntitiesByJdbc(ExamPaperVo.class, sql.toString(),
				paramMap);
	}

	public Integer getExamScore(String record_id) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT score from oe_barrier_record where id=?");
		return this.getNamedParameterJdbcTemplate().getJdbcOperations()
				.queryForObject(sql.toString(), Integer.class, record_id);
	}
}
