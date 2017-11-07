package com.xczhihui.bxg.online.web.service;

import com.xczhihui.bxg.online.common.domain.Grade;
import com.xczhihui.bxg.online.web.vo.GradeVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *   GradeService:班级业务层接口类
 *  @author Rongcai Kang
 */
public interface GradeService {

    public List<GradeVo> findGradeByCourseId(Integer courseId,HttpServletRequest request) ;

    /**
     * 根据班级id，查找对应班级信息
     * @param gradeId
     * @return
     */
    public Grade findGradeById( Integer gradeId);

    /**
     * 修改班级学生数量
     * @param grade
     * @return
     */
    public void update( Grade  grade);

    /**
     * 收费课程下的班级信息
     * @param courseId 课程id
     * @return
     */
    public List<GradeVo> findGradeInfoByCourseId( Integer courseId);
}
