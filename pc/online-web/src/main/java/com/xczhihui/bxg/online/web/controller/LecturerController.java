package com.xczhihui.bxg.online.web.controller;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.web.service.LecturerService;
import com.xczhihui.bxg.online.web.vo.LecturVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 授课教师控制层实现类
 * @author Rongcai Kang
 */
@RestController
@RequestMapping(value = "/lecturer")
public class LecturerController {

    @Autowired
    private LecturerService lecturerService;

    /**
     * 查询授课教师全部列表
     * @return ResponseObject
     */
    @RequestMapping(value = "/list/course/{courseId}",method= RequestMethod.GET)
    public ResponseObject detail(@PathVariable String courseId) throws InvocationTargetException, IllegalAccessException {
        List<LecturVo> lecturVoList=lecturerService.listByCourseId(courseId);
        if(lecturVoList.size()>0){
            for(LecturVo lecturVo:lecturVoList){
                lecturVo.setName(lecturVo.getName());
            }
        }
        return ResponseObject.newSuccessResponseObject(lecturVoList);
    }
}
