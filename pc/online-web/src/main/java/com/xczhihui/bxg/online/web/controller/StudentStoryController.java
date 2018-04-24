package com.xczhihui.bxg.online.web.controller;

import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.web.service.StudentStoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;

/**
 * Banner控制层实现类
 * @author Rongcai Kang
 */
@RestController
@RequestMapping(value = "/studentStory")
public class StudentStoryController {

    @Autowired
    private StudentStoryService studentStoryService;

    /**
     *学员故事列表
     * @return ResponseObject
     */
    @RequestMapping(value = "/listByIndex",method= RequestMethod.GET)
    public ResponseObject listByIndex() throws InvocationTargetException, IllegalAccessException {
        return ResponseObject.newSuccessResponseObject(studentStoryService.findListByIndex());
    }


    /**
     * 学员详情信息
     * @return ResponseObject
     */
    @RequestMapping(value = "/detail/{id}",method= RequestMethod.GET)
    public ResponseObject detail(@PathVariable String id) throws InvocationTargetException, IllegalAccessException {
        return ResponseObject.newSuccessResponseObject(studentStoryService.findById(id));
    }
}
