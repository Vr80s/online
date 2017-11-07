package com.xczh.consumer.market.controller;

import com.xczh.consumer.market.dao.OnlineUserMapper;
import com.xczh.consumer.market.service.OnlineCourseService;
import com.xczh.consumer.market.utils.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/bxg/testDao")
public class TestDaoController {
	
	@Autowired
	public OnlineUserMapper onlineUserDao;
	
	@Autowired
	public OnlineCourseService onlineCourseService;
	
	
	@RequestMapping("testEntity")
	@ResponseBody
	public ResponseObject testEntity(HttpServletRequest req,
									 HttpServletResponse res, Map<String, String> params)
			throws Exception {
		
		
		//System.out.println(oe.getGradeName());
		
		return null;
	}
	@RequestMapping("h5pay")
	@ResponseBody
	public ResponseObject h5OrderPay(HttpServletRequest req,
                                     HttpServletResponse res, Map<String, String> params)
			throws Exception {
		
		onlineCourseService.addLiveExamineData();
		
		return null;
	}
	
	public static void main(String[] args) {
		
		
	}
	

}
