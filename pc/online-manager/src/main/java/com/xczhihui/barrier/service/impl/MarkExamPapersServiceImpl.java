package com.xczhihui.barrier.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.xczhihui.barrier.dao.MarkExamPapersDao;
import com.xczhihui.barrier.service.MarkExamPapersService;
import com.xczhihui.barrier.vo.*;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.course.vo.CourseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Fudong.Sun【】
 * @Date 2016/12/22 10:41
 */
@Service
public class MarkExamPapersServiceImpl extends OnlineBaseServiceImpl implements
		MarkExamPapersService {

	@Autowired
	MarkExamPapersDao markExamPapersDao;

	@Override
	public List<CourseVo> findCourses() {
		Map<String, Object> paramMap = new HashMap<>();
		StringBuilder sql = new StringBuilder();
		sql.append(" select oc.*,oc.grade_name as courseName");
		sql.append(" from oe_barrier ob");
		sql.append(" LEFT JOIN oe_course oc on oc.id=ob.course_id");
		sql.append(" LEFT JOIN oe_grade og on og.course_id=oc.id");
		sql.append(" where ob.is_delete=0 and oc.is_delete=0 AND og.is_delete=0");
		sql.append(" GROUP BY oc.id");
		return dao.findEntitiesByJdbc(CourseVo.class, sql.toString(), paramMap);
	}

	@Override
	public Page<MarkGradeVo> findGradeList(MarkGradeVo gradeVo,
			Integer pageNumber, Integer pageSize) {
		Map<String, Object> paramMap = new HashMap<>();
		pageNumber = pageNumber == null ? 1 : pageNumber;
		pageSize = pageSize == null ? 20 : pageSize;
		StringBuilder sql = new StringBuilder();
		sql.append(" select og.id AS id,");
		sql.append(" oc.id AS course_id,");
		sql.append(" oc.grade_name as course_name,");
		sql.append(" og.`name` as grade_name,");
		sql.append(" ifnull(og.student_count, 0) AS student_count,");
		sql.append(" (select count(1) from(SELECT count(1) sum,barrier_id FROM `oe_barrier_record` GROUP BY user_id)a LEFT JOIN oe_barrier ob on a.barrier_id=ob.id where ob.course_id=oc.id and ob.is_delete=0 and a.sum>0 and barrier_id=ob.id) as join_num,");
		sql.append(" (select count(obu.id) from oe_barrier_user obu LEFT JOIN oe_barrier ob on obu.barrier_id=ob.id where ob.course_id=oc.id and barrier_status=1 and ob.is_delete=0) as pass_num,");
		sql.append(" (select count(1) from oe_barrier where course_id=oc.id and is_delete=0) as barrier_count");
		sql.append(" from oe_barrier ob ");
		sql.append(" LEFT JOIN oe_course oc ON oc.id = ob.course_id ");
		sql.append(" LEFT JOIN oe_grade og on og.course_id=oc.id");
		sql.append(" where og.is_delete=0 and oc.is_delete=0 and ob.is_delete=0");
		if (StringUtils.isNotEmpty(gradeVo.getGrade_name())) {
			sql.append(" and og.name like :gname");
			paramMap.put("gname", "%" + gradeVo.getGrade_name() + "%");
		}
		if (gradeVo.getCourse_id() != null && gradeVo.getCourse_id() != -1) {
			sql.append(" and oc.id=:courseId");
			paramMap.put("courseId", gradeVo.getCourse_id());
		}
		sql.append(" GROUP BY og.id");
		sql.append(" order by og.curriculum_time desc");
		return dao.findPageBySQL(sql.toString(), paramMap, MarkGradeVo.class,
				pageNumber, pageSize);
	}

	@Override
	public Page<MarkBarrierVo> findbarriers(Integer grade_id,
			Integer pageNumber, Integer pageSize) {
		Map<String, Object> paramMap = new HashMap<>();
		pageNumber = pageNumber == null ? 1 : pageNumber;
		pageSize = pageSize == null ? 20 : pageSize;
		StringBuilder sql = new StringBuilder();
		sql.append(" select ob.id,");
		sql.append(" ob.`name` as barrier_name,");
		sql.append(" (SELECT count(DISTINCT(user_id)) from `oe_barrier_record` where barrier_id=ob.id) as join_num,");
		sql.append(" (select count(1) from oe_barrier_user where barrier_status=1 and barrier_id=ob.id) as pass_num,");
		sql.append(" (select count(1) from oe_barrier_user where barrier_status=1 and barrier_count=1 and barrier_id=ob.id) as once_num,");
		sql.append(" (select count(1) from oe_barrier_user where barrier_status=1 and barrier_count=2 and barrier_id=ob.id) as twice_num,");
		sql.append(" og.student_count as student_num");
		sql.append(" from oe_barrier ob");
		sql.append(" LEFT JOIN oe_grade og on og.course_id=ob.course_id");
		sql.append(" where og.id= :grade_id and ob.is_delete=0");
		paramMap.put("grade_id", grade_id);
		return dao.findPageBySQL(sql.toString(), paramMap, MarkBarrierVo.class,
				pageNumber, pageSize);
	}

	@Override
	public Page<MarkRecordVo> findBarrierRecord(Integer barrier_status,
			String user_name, Integer grade_id, String barrier_id,
			Integer pageNumber, Integer pageSize) {
		Map<String, Object> paramMap = new HashMap<>();
		pageNumber = pageNumber == null ? 1 : pageNumber;
		pageSize = pageSize == null ? 20 : pageSize;
		StringBuilder sql = new StringBuilder();
		sql.append(" select u.id,");
		sql.append("(select student_number from apply_r_grade_course where grade_id= :grade_id and apply_id=oa.id) as student_number,");
		sql.append(" u.`name` as user_name,");
		sql.append(" (select group_concat(obr.score ORDER BY obr.create_time SEPARATOR '/'))as score_record,");
		sql.append(" (select obr.create_time from oe_barrier_record obr where obr.barrier_id=ob.id and obr.user_id=u.id ORDER BY obr.create_time desc limit 1)as create_time,");
		sql.append(" (select obr.submit_time from oe_barrier_record obr where obr.barrier_id=ob.id and obr.user_id=u.id ORDER BY obr.create_time desc limit 1)as submit_time,");
		sql.append(" (select obr.use_time from oe_barrier_record obr where obr.barrier_id=ob.id and obr.user_id=u.id ORDER BY obr.create_time desc limit 1)as expend_time,");
		sql.append(" (select count(1) from oe_barrier_record obr where obr.barrier_id=ob.id and obr.user_id=u.id) as recore_num,");
		sql.append(" (select SUM(total_score) from oe_barrier_strategy where barrier_id=ob.id) as total_score,");
		sql.append(" ob.pass_score_percent,");
		sql.append(" (select barrier_status from oe_barrier_user where barrier_id=ob.id and user_id=u.id) as barrier_status");
		sql.append(" from oe_barrier_record obr");
		sql.append(" LEFT JOIN oe_user u on obr.user_id=u.id");
		sql.append(" LEFT JOIN oe_apply oa on oa.user_id=u.id");
		sql.append(" LEFT JOIN oe_barrier ob on obr.barrier_id=ob.id");
		sql.append(" where ob.id= :barrier_id");
		if (StringUtils.isNotEmpty(user_name)) {
			sql.append(" and u.name like :user_name");
			paramMap.put("user_name", "%" + user_name + "%");
		}
		if (barrier_status != -1) {
			sql.append(" and obu.barrier_status=:barrier_status");
			paramMap.put("barrier_status", barrier_status);
		}
		sql.append(" GROUP BY obr.user_id");
		paramMap.put("grade_id", grade_id);
		paramMap.put("barrier_id", barrier_id);
		return dao.findPageBySQL(sql.toString(), paramMap, MarkRecordVo.class,
				pageNumber, pageSize);
	}

	@Override
	public List<Map<String, Object>> getExamNum(String user_id,
			String barrier_id) {
		StringBuilder sql = new StringBuilder();
		sql.append("select \n");
		sql.append("obr.id,\n");
		sql.append("obr.create_time,\n");
		sql.append("obr.score,\n");
		sql.append("obr.result\n");
		sql.append("from oe_barrier_record obr\n");
		sql.append("where obr.user_id=? and obr.barrier_id=?\n");
		sql.append("ORDER BY obr.create_time");
		List<Map<String, Object>> list = dao.getNamedParameterJdbcTemplate()
				.getJdbcOperations()
				.queryForList(sql.toString(), user_id, barrier_id);
		for (Map<String, Object> obj : list) {
			obj.put("index", "第" + (list.indexOf(obj) + 1) + "次");
		}
		return list;
	}

	@Override
	public Map<String, Object> getExamPaper(String record_id) {
		Map<String, Object> paper = new HashMap<>();
		/** 组装试卷信息及试题 */
		List<ExamPaperVo> question0 = markExamPapersDao.getQuestionByType(
				record_id, 0);// 单选题
		List<ExamPaperVo> question1 = markExamPapersDao.getQuestionByType(
				record_id, 1);// 多选题
		List<ExamPaperVo> question2 = markExamPapersDao.getQuestionByType(
				record_id, 2);// 判断题

		PaperInfoVo paper0 = markExamPapersDao.getQuestionInfoByType(record_id,
				0);// 单选题信息
		paper0.setExamPaper(question0);
		PaperInfoVo paper1 = markExamPapersDao.getQuestionInfoByType(record_id,
				1);// 多选题信息
		paper1.setExamPaper(question1);
		PaperInfoVo paper2 = markExamPapersDao.getQuestionInfoByType(record_id,
				2);// 判断题信息
		paper2.setExamPaper(question2);
		paper.put("examScore", markExamPapersDao.getExamScore(record_id));
		paper.put("paper0", paper0);
		paper.put("paper1", paper1);
		paper.put("paper2", paper2);
		return paper;
	}
}
