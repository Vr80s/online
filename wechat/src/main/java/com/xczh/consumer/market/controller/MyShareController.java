package com.xczh.consumer.market.controller;

import com.xczh.consumer.market.service.MyShareService;
import com.xczh.consumer.market.utils.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 分享中心接口
 * @author Libin Wang
 *
 * @date 2017年2月25日
 */

@Controller
@RequestMapping("/bxg/share")
public class MyShareController {

	@Autowired
	private MyShareService shareService;
	
	/**
	 * 一元课程详情
	 * param:courseId 固定值，从配置文件取，上线请更改
	 */
	@RequestMapping("OneCourse")
	@ResponseBody
	public ResponseObject findOneCourse(HttpServletRequest request, HttpServletResponse response,
										Map<String, String> parmas) throws Exception {

		/*刘涛修改 暂时写死*/
		String courseId = "191";
		return ResponseObject.newSuccessResponseObject(shareService.findOneCourse(courseId));
	}
	
	/**
	 * 我的学费补贴及上级合伙人
	 * userId:当前登录用户ID
	 */
	@RequestMapping("brokerage")
	@ResponseBody
	public ResponseObject findMyBrokerage(HttpServletRequest request, HttpServletResponse  response,
                                          Map<String, String> params) throws Exception {
		
		String userId = request.getParameter("userId"); //用户ID
		return ResponseObject.newSuccessResponseObject(shareService.findMyBrokerage(userId));
	}
	
	/**
	 * 我的合伙人列表
	 * userId:当前登录用户ID
	 */
	@RequestMapping("partnerList")
	@ResponseBody
	public ResponseObject findMyPartner(HttpServletRequest request, HttpServletResponse  response,
                                        Map<String, String> params) throws Exception {
		
		String userId = request.getParameter("userId"); //用户ID
		return ResponseObject.newSuccessResponseObject(shareService.findMyPartnerList1(userId));
	}
	
	/**
	 * 我的学费补贴详情列表
	 * userId:当前登录用户ID
	 */
	@RequestMapping("brokerageList")
	@ResponseBody
	public ResponseObject findMyBrokerageList(HttpServletRequest request, HttpServletResponse  response,
                                              Map<String, String> params) throws Exception {
		
		String userId = request.getParameter("userId"); //用户ID
		return ResponseObject.newSuccessResponseObject(shareService.findMyBrokerageList(userId));
	}
	
	/**
	 * 学费补贴详情
	 * id：佣金记录ID
	 */
	@RequestMapping("brokerageDetail")
	@ResponseBody
	public ResponseObject findBrokerageDetail(HttpServletRequest request, HttpServletResponse  response,
                                              Map<String, String> params) throws Exception {
		
		String id = request.getParameter("id"); //用户ID
		return ResponseObject.newSuccessResponseObject(shareService.findBrokerageDetail(id));
	}
}
