package com.xczhihui.bxg.online.manager.site.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.controller.AbstractController;

@Controller
@RequestMapping(value="/site/information")
public class SiteController extends AbstractController{
	
	@RequestMapping(value = "/find")
	@ResponseBody
	public ResponseObject find(HttpSession session) {
		return ResponseObject.newSuccessResponseObject("");
	}
	
	@RequestMapping(value = "/save")
	@ResponseBody
	public ResponseObject save(String title,String keywork,String description,HttpSession session) {
		return ResponseObject.newSuccessResponseObject("");
	}
	
	@RequestMapping(value = "/index")
	public ModelAndView index(){
        ModelAndView mav=new ModelAndView("/site/information");
        return mav;
	}
}
