package com.xczhihui.bxg.online.web.controller;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.web.service.GradeService;
import com.xczhihui.bxg.online.web.vo.GradeVo;
import com.xczhihui.bxg.online.web.vo.NowTimeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 班级控制层实现类
 * @author Rongcai Kang
 */
@RestController
@RequestMapping(value = "/grade")
public class GradeController {

    @Autowired
    private GradeService  service;

    /**
     * 获取班级列表信息，根据课程ID号查找
     * @param courserId 课程编号
     * @return
     */
    @RequestMapping(value = "/findGradeByCourseId")
    public ResponseObject findGradeByCourseId(Integer courserId,HttpServletRequest request){
        return ResponseObject.newSuccessResponseObject(service.findGradeByCourseId(courserId,request));
    }

    /**
     * 获取系统当前时间
     * @return 返回封装系统时间的对象
     */
    @RequestMapping(value="/time/now")
    public NowTimeVo<Date> findList()
    {
        Date now = new Date();
        return new NowTimeVo(true,now);
    }

    /**
     * 获取班级信息
     * @param gradeId 课程编号
     * @return
     */
    @RequestMapping(value = "/findGradeById")
    public ResponseObject findGradeById(Integer gradeId){
        return ResponseObject.newSuccessResponseObject(service.findGradeById(gradeId));
    }




    /**
     * 收费课程下的班级信息
     * @param courseId 课程id
     * @return
     */
    @RequestMapping(value = "/findGradeInfoByCourseId")
    public ResponseObject findGradeInfoByCourseId( Integer courseId) {
        return ResponseObject.newSuccessResponseObject(service.findGradeInfoByCourseId(courseId));
    }
}
