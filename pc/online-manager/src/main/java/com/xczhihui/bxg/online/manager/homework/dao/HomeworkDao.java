package com.xczhihui.bxg.online.manager.homework.dao;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.online.manager.homework.vo.UserPaperQuestionVo;
import org.apache.commons.collections.FastHashMap;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 作业管理模块数据访问层代码
 * @Author Fudong.Sun【】
 * @Date 2017/2/28 9:29
 */
@Repository
public class HomeworkDao extends SimpleHibernateDao{

    /**
     * 根据id查询班级试卷
     * @param id
     * @return
     */
    public Map<String,Object> findClassPaperById(String id){
        String sql = "select * from oe_exam_class_paper where id=?";
        List<Map<String,Object>> list = this.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sql,id);
        return list.size()>0? list.get(0):null;
    }

    public List<Map<String,Object>> findClassUsers(String classId){
        String sql = "select u.id,u.`name`,u.login_name from oe_user u LEFT JOIN oe_apply oa on u.id=oa.user_id\n" +
                "LEFT JOIN apply_r_grade_course agc on oa.id=agc.apply_id where agc.grade_id=:classId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("classId", classId);
        return this.getNamedParameterJdbcTemplate().queryForList(sql,params);
    }


    /**
     * 为学生保存成绩
     * @param userPaperIds
     */
    public void saveUserResult(String userPaperIds){
        String[] ids = userPaperIds.split(",");
        MapSqlParameterSource params = new MapSqlParameterSource();
        //循环保存学生成绩
        for (String userPaperId : ids){
            params.addValue("userPaperId", userPaperId);
            //1.统计学生作业成绩并保存
            String sql =" insert into oe_exam_user_total(id,user_paper_id,question_type,score,right_sum,wrong_sum,undo_sum)" +
                    " select replace(uuid(),'-','') id , upa.id,uq.question_type,sum(uq.user_score) score," +
                    " (SELECT count(*) from  oe_exam_user_question  where user_paper_id=upa.id and question_type=uq.question_type and rightOrWrong=2 ) right_sum," +
                    " (SELECT count(*) from  oe_exam_user_question  where user_paper_id=upa.id and question_type=uq.question_type and rightOrWrong=1 ) wrong_sum," +
                    " (SELECT count(*) from  oe_exam_user_question  where user_paper_id=upa.id and question_type=uq.question_type and rightOrWrong=0 ) undo_sum" +
                    " from oe_exam_user_question uq,oe_exam_user_paper upa  where uq.user_paper_id=upa.id and" +
                    " upa.id=:userPaperId and upa.status=2 group by uq.question_type";
            this.getNamedParameterJdbcTemplate().update(sql, params);

            //2.将学生此作业状态改为3：已发布成绩
            sql = "update oe_exam_user_paper set status=3 where id=:userPaperId and status=2";
            this.getNamedParameterJdbcTemplate().update(sql,params);
        }
    }

    /**
     * 根据用户试卷id查询试题
     * @param userPaperId
     * @param type
     * @return
     */
    public List<UserPaperQuestionVo> findUserPaperQuestionsByUserPaperId(String userPaperId,Integer type){
        String sql = "select * from oe_exam_user_question where user_paper_id=:userPaperId and question_type=:type";
        Map<String,Object> params = new FastHashMap();
        params.put("userPaperId",userPaperId);
        params.put("type",type);
        return this.findEntitiesByJdbc(UserPaperQuestionVo.class,sql,params);
    }

    /**
     * 根据课程试卷id查询试题
     * @param paperId
     * @param type
     * @return
     */
    public List<UserPaperQuestionVo> findPaperQuestionsByPaperId(String paperId,Integer type){
        String sql = "select id,question_type,question_head,options,options_picture,attachment_url,difficulty,answer,solution,question_score from oe_exam_paper_question where paper_id=:paperId and question_type=:type";
        Map<String,Object> params = new FastHashMap();
        params.put("paperId",paperId);
        params.put("type",type);
        return this.findEntitiesByJdbc(UserPaperQuestionVo.class,sql,params);
    }


}
