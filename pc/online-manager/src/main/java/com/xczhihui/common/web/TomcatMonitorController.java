package com.xczhihui.common.web;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import com.xczhihui.course.service.PublicCourseService;
import com.xczhihui.wechat.utils.TokenThread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class TomcatMonitorController {

	@Autowired
	private PublicCourseService publicCourseService;
   
    @RequestMapping(value = "/test",method= RequestMethod.GET)
    public String saveApply(HttpServletRequest request){
    	return "ok";
    }
    
    @PostConstruct
    public void yuruixin(){
    	publicCourseService.saveOpenCourseToSend();
    }
    
    
    /**
     * 初始化获取微信token
     */
    @PostConstruct
    public void initTokenFiter(){
    	new Thread(new TokenThread()).start();
    }
    
}
