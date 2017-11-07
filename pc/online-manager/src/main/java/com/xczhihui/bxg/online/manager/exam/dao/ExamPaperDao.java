package com.xczhihui.bxg.online.manager.exam.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alibaba.dubbo.common.utils.StringUtils;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.support.domain.Attachment;
import com.xczhihui.bxg.common.support.service.AttachmentCenterService;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.QuestionVo;
import com.xczhihui.bxg.online.manager.exam.vo.ExamPaperVo;

@Repository
public class ExamPaperDao extends SimpleHibernateDao {

	@Autowired
    private AttachmentCenterService attachmentCenterService;

	public Page<ExamPaperVo> findExamPaperPage(ExamPaperVo examPaperVo, Integer pageNumber, Integer pageSize){
		   Map<String,Object> paramMap=new HashMap<String,Object>();
		   StringBuilder sql=new StringBuilder(" SELECT " +
											   "	oep.id, " +
											   "	oep.type, " +
											   "	oep.paper_name, " +
											   "	oep.score, " +
											   "	oep.duration, " +
											   "	oep.difficulty, " +
											   "	oep.create_person, " +
											   "	oep.create_time, " +
											   "	oep.use_sum, " +
											   "	t1.name createPersonName " +
											   " FROM " +
											   "	oe_exam_paper oep, " +
											   "	user t1 " +
											   " WHERE " +
											   "	oep.create_person = t1.login_name ");

		   if(examPaperVo.getCourseId() != null){
			   sql.append(" and oep.course_id = :courseId ");
			   paramMap.put("courseId", examPaperVo.getCourseId());
		   }
		   
		   if(examPaperVo.getPaperName() != null && !"".equals(examPaperVo.getPaperName())){
			   sql.append(" and oep.paper_name like :paperName");
			   paramMap.put("paperName", "%" + examPaperVo.getPaperName() + "%");
		   }

		   if(examPaperVo.getDifficulty() != null && !"".equals(examPaperVo.getDifficulty())){
			   sql.append(" and oep.difficulty = :difficulty ");
			   paramMap.put("difficulty", examPaperVo.getDifficulty());
		   }

		   sql.append(" order by oep.create_time desc ");
		   Page<ExamPaperVo> ms = this.findPageBySQL(sql.toString(), paramMap, ExamPaperVo.class, pageNumber, pageSize);
      	   return ms;
	}
	
	public Page<CourseVo> findCloudClassCoursePage(CourseVo courseVo, int pageNumber, int pageSize){
	 	Map<String,Object> paramMap=new HashMap<String,Object>();
	 	StringBuilder sql =new StringBuilder( 	"SELECT " +
									 			"	oc.id, " +
									 			"	oc.grade_name courseName, " +
									 			"	om.`name` xMenuName, " +
									 			"	oc.`status`, " +
									 			"	(select count(1) from oe_exam_paper t where oc.id = t.course_id ) paperNum " +
									 			"FROM " +
									 			"	oe_course oc " +
									 			"JOIN oe_menu om ON (oc.menu_id = om.id) " +
									 			"where 1=1 ");

	 	if(courseVo.getCourseName() != null){
	 		paramMap.put("courseName", "%"+courseVo.getCourseName()+"%");
	 		sql.append("and oc.grade_name like :courseName ");
	 	}
	 	
	 	if(courseVo.getMenuId() != null){
	 		paramMap.put("menuId", courseVo.getMenuId());
	 		sql.append("and oc.menu_id = :menuId ");
	 	}
	 	
	 	if(courseVo.getStatus() != null){
	 		paramMap.put("status", courseVo.getStatus());
	 		sql.append("and oc.status = :status ");
	 	}
	 	
	 	if(courseVo.getId() > 0){
	 		paramMap.put("courseId", courseVo.getId());
	 		sql.append("and oc.id <> :courseId ");
	 	}
	 	
	 	sql.append(" ORDER BY oc.create_time DESC");	
 		
 		Page<CourseVo> courseVos=this.findPageBySQL(sql.toString(), paramMap, CourseVo.class, pageNumber, pageSize);
		return courseVos;
	}

	public Page<QuestionVo> findCloudClassQuestionPage(QuestionVo questionVo, int pageNumber, int pageSize){
		Map<String,Object> paramMap=new HashMap<String,Object>();
		StringBuilder sql =new StringBuilder( "SELECT " +
												"	t1.id, " +
												"	t1.question_head, " +
												"	t1.question_head_text, " +
												"	t1.answer, " +
												"	t1.difficulty, " +
												"	ifnull(t1.solution,'暂无') solution, " +
												"	t1.options, " +
												"	t1.options_picture, " +
												"	t1.question_type, " +
												"	group_concat((select a.`name` from oe_chapter a where a.id= t2.kpoint_id)) chapter_name " +
												"FROM " +
												"	oe_question t1, " +
												"	oe_question_kpoint t2 " +
												"WHERE " +
												"	t1.id = t2.question_id " +
												"AND t1.status = 1 ");
		
		if(questionVo.getCourseId() != null && !"".equals(questionVo.getCourseId())){
			sql.append("and t1.course_id = :courseId ");
			paramMap.put("courseId", questionVo.getCourseId());
		}

		if(questionVo.getQuestionType() != null && !"".equals(questionVo.getQuestionType())){
			sql.append("and t1.question_type = :questionType ");
			paramMap.put("questionType", questionVo.getQuestionType());
		}

		if(questionVo.getDifficulty() != null && !"".equals(questionVo.getDifficulty())){
			sql.append("and t1.difficulty = :difficulty ");
			paramMap.put("difficulty", questionVo.getDifficulty());
		}

		if(questionVo.getQuestionHead() != null && !"".equals(questionVo.getQuestionHead())){
			sql.append("and t1.question_head like :questionHead ");
			paramMap.put("questionHead", "%"+questionVo.getQuestionHead()+"%");
		}
		
		if(questionVo.getKpointIds() != null && !"".equals(questionVo.getKpointIds())){
			sql.append("and t2.kpoint_id in ("+questionVo.getKpointIds()+") ");
		}
		
		if(questionVo.getId() != null && !"".equals(questionVo.getId())){
			sql.append("and t1.id not in ("+questionVo.getId()+") ");
		}

		sql.append(" GROUP BY t1.id ORDER BY t1.create_time DESC");
		
		Page<QuestionVo> question=this.findPageBySQL(sql.toString(), paramMap, QuestionVo.class, pageNumber, pageSize);
		for(int j = 0;j<question.getItems().size();j++){
			if(5 == question.getItems().get(j).getQuestionType() || 6 == question.getItems().get(j).getQuestionType()){
				if(!StringUtils.isEmpty(question.getItems().get(j).getAnswer())){
                	Attachment att;
					try {
						att = attachmentCenterService.getAttachmentObject(question.getItems().get(j).getAnswer());
						if(att!=null) {
	                        String fileName = att.getOrgFileName();
	                        question.getItems().get(j).setFileName(fileName);
	                	}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                	
				}
			}
		}
		return question;
	}
}

