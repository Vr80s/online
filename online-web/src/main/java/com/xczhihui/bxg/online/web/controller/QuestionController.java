package com.xczhihui.bxg.online.web.controller;/**
 * Created by admin on 2016/8/29.
 */

import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.web.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;

/**
 * 课程问题及回答信息的控制层
 *
 * @author 康荣彩
 * @create 2016-08-29 17:14
 */
@RestController
@RequestMapping(value = "online/question")
public class QuestionController {
    @Autowired
    private QuestionService  questionService;

    /**
     * 查询Banner全部列表
     * @return ResponseObject
     */
    @RequestMapping(value = "/getQuestionList",method= RequestMethod.GET)
    public ResponseObject getQuestionList(Integer courseId) throws InvocationTargetException, IllegalAccessException {
        return ResponseObject.newSuccessResponseObject(questionService.getQuestionList(courseId));
    }
}
