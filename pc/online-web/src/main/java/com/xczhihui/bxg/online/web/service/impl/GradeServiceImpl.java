package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.ApplyGradeCourse;
import com.xczhihui.bxg.online.common.domain.Grade;
import com.xczhihui.bxg.online.web.dao.ApplyGradeCourseDao;
import com.xczhihui.bxg.online.web.service.GradeService;
import com.xczhihui.bxg.online.web.vo.GradeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 *   GradeServiceImpl:班级业务层接口实现类
 *   @author Rongcai Kang
 */
@Service
public class GradeServiceImpl extends OnlineBaseServiceImpl implements GradeService {

    @Autowired
    private ApplyGradeCourseDao applyGradeCourseDao;
    /**
     * 获取开课课程下面所有的班级信息
     * @param courseId
     * @return
     */
    @Override
    public List<GradeVo> findGradeByCourseId( Integer courseId,HttpServletRequest request) {
        //班级信息集合
        List<GradeVo>  newGradeVos =null;
        String sql="select gs.* from (select g.id, g.`name`,g.curriculum_time,g.seat,g.`status`,ifnull(g.qqno,'暂无') as qqno from  oe_grade g where  g.is_delete=0  and g.grade_status = 1  and g.course_id = ? and g.curriculum_time > SYSDATE() order by g.curriculum_time limit 2  ) as gs " +
                "  union all " +
                " select xs.* from (select g.id, g.`name`,g.curriculum_time,g.seat,g.`status`,ifnull(g.qqno,'暂无') as qqno from  oe_grade g where  g.is_delete=0  and g.grade_status = 1 and g.course_id = ?  and g.curriculum_time < SYSDATE() order by g.curriculum_time desc  limit 1  ) as xs ";
        //查找某课程下的班级，最多显示三条
        List<GradeVo> gradeVos = dao.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql, new Object[]{courseId,courseId}, BeanPropertyRowMapper.newInstance(GradeVo.class));

        //获取用户是否登录
        BxgUser loginUser = UserLoginUtil.getLoginUser(request);
        if(loginUser != null && !CollectionUtils.isEmpty(gradeVos)){ //用户登录，循环班级，查看当前用户针对当前班级是否报名,报名isApply=true,否则isApply=false
            newGradeVos= new ArrayList<GradeVo>();
              for (GradeVo gradeVo : gradeVos){
                   ApplyGradeCourse applyGradeCourse=applyGradeCourseDao.findInfoByID(loginUser.getId(), courseId, gradeVo.getId());
                   if(applyGradeCourse != null){
                       gradeVo.setIsApply(true);
                   }
                  newGradeVos.add(gradeVo);
              }
            return  newGradeVos;
        }
        return  gradeVos;
    }


    /**
     * 根据班级id，查找对应班级信息
     * @param gradeId
     * @return
     */
    @Override
    public Grade findGradeById(Integer gradeId) {
         return  dao.findByHQLOne("from Grade  where isDelete = 0 and  id=?", gradeId);
    }

    /**
     * 修改班级学生数量
     * @param grade
     * @return
     */
    @Override
    public void update(Grade  grade){
         dao.update(grade);
    }



    /**
     * 付费课程下的班级信息
     * @param courseId 课程id
     * @return
     */
    @Override
    public List<GradeVo> findGradeInfoByCourseId( Integer courseId) {
        //班级信息集合
        List<GradeVo>  newGradeVos =null;
        String sql=" select g.* from ( select name,1 as isOpenClass, SUBSTRING(curriculum_time,1,10) as curriculumTime, IFNULL(student_count,0) +default_student_count studentCount, (student_amount -  IFNULL(student_count,0) ) as seat from oe_grade  where is_delete=0  and grade_status = 1 and   course_id = ?   and  unix_timestamp(NOW()) <=  unix_timestamp(curriculum_time)  and  student_count<student_amount     order by  curriculum_time  limit 1 ) as g " +
                "    union all  " +
                "    select r.* from ( select name,0 as isOpenClass, SUBSTRING(curriculum_time,1,10) as curriculumTime, IFNULL(student_count,0) +default_student_count studentCount,(student_amount -  IFNULL(student_count,0) )  as seat from oe_grade   where is_delete=0  and grade_status = 1  and course_id = ? and  ( unix_timestamp(NOW()) > unix_timestamp(curriculum_time) or   student_count=student_amount   )  limit 2 ) as r ";
        //查找某课程下的班级，最多显示三条
        List<GradeVo> gradeVos = dao.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql, new Object[]{courseId, courseId}, BeanPropertyRowMapper.newInstance(GradeVo.class));

        return  gradeVos;
    }



}
