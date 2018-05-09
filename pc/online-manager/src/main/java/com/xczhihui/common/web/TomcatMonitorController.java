package com.xczhihui.common.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/")
public class TomcatMonitorController {

    @RequestMapping(value = "/test",method= RequestMethod.GET)
    public String saveApply(HttpServletRequest request){
    	return "ok";
    }
    

}
