package com.xczhihui.bxg.online.web.controller;/**
 * Created by admin on 2016/8/29.
 */

import com.xczhihui.bxg.online.web.service.SchoolService;
import com.xczhihui.bxg.online.web.vo.SchoolVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 学校信息控制层
 * @author 康荣彩
 * @create 2016-08-29 18:43
 */
@RestController
@RequestMapping(value = "online/school")
public class SchoolController {


    @Autowired
    private SchoolService   schoolService;
    /**
     * 根据市级编号 查找对应的学校信息
     * @param cityId
     * @return
     */
    @RequestMapping(value = "/getSchoolList",method= RequestMethod.GET)
    public List<SchoolVo> getSchoolList(String cityId){
        return schoolService.getSchoolList(cityId);
    }
}
