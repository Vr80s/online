package com.xczhihui.bxg.online.web.dao;/**
 * Created by admin on 2016/8/29.
 */

import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.online.web.vo.QuestionVo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 课程问题底层类
 *
 * @author 康荣彩
 * @create 2016-08-29 16:15
 */
@Repository
public class QuestionDao extends SimpleHibernateDao {

    /**
     * 根据课程id 查找到课程对应的问题信息
     * @param courseId
     * @return
     */
    public List<QuestionVo> getQuestionList(Integer courseId) {
              String sql=" select  id, question_name,course_id  from question where course_id = ? and pid = 'null' order by sort ";
              return this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql, new Object[]{courseId}, BeanPropertyRowMapper.newInstance(QuestionVo.class));
    }

    /**
     * 根据问题ID  查找到对应的回答信息
     * @param pid
     * @return
     */
    public QuestionVo getQuestionByPid(Integer courseId,String pid) {
        String sql=" select  question_name  from question where  course_id = ?  and pid = ?";
        List<QuestionVo> questionVos= this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql, new Object[]{courseId,pid}, BeanPropertyRowMapper.newInstance(QuestionVo.class));
        return questionVos.size() > 0 ? questionVos.get(0) : null;
    }
}
