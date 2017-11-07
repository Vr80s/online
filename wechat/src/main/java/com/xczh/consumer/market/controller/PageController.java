package com.xczh.consumer.market.controller;

import com.xczh.consumer.market.service.PageService;
import com.xczh.consumer.market.utils.CookieUtil;
import com.xczh.consumer.market.utils.ResponseObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;

/**
 * 页面跳转相关
 * @author Alex Wang
 */
@Controller
@RequestMapping(value = "/bxg/page")
public class PageController {
	
	@Autowired
	private PageService service;
	
	
	/**
	 * 请求转发用于验证用户的登录状态
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 */
	@RequestMapping(value = "/verifyLoginStatus")
	@ResponseBody
	public ResponseObject verifyLoginStatus(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)throws Exception{
		Integer statusFalg = 1000;
		if(req.getParameter("statusFalg")!=null){
			statusFalg = Integer.parseInt(req.getParameter("statusFalg"));
		}
		return ResponseObject.newSuccessResponseObject("登录状态验证",statusFalg);
		//req.getRequestDispatcher("/WEB-INF/jsp/login.jsp?openId="+openId).forward(req, res);
	}
	
	/**
	 * 首页
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 */
	@RequestMapping(value = "/index/{openId}/{code}")
	public void index(@PathVariable("openId")String openId,
			@PathVariable("code")String code,HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)throws Exception{
		
		req.setAttribute("openId", openId);
		req.setAttribute("code", code);
		req.setAttribute("access", "wx");
		
		System.out.println("openId"+openId);
		System.out.println("code"+code);
		
		req.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(req, res);
	}
	
	/**
	 * 分享页
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 */
	@RequestMapping(value = "/share/")
	public void share(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)throws Exception{
		req.getRequestDispatcher("/WEB-INF/jsp/share.jsp").forward(req, res);
	}
	
	/**
	 * 我的分享页
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 */
	@RequestMapping(value = "/my_share")
	public void my_share(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)throws Exception{
		req.getRequestDispatcher("/WEB-INF/jsp/my_share.jsp").forward(req, res);
	}
	
	/**
	 * 我的分享详情
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 */
	@RequestMapping(value = "/my_share_detail/{id}")
	public void my_share_detail(@PathVariable("id")String id,HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)throws Exception{
		
		//String id = req.getParameter("rest_id");
		req.setAttribute("id", id);
		req.getRequestDispatcher("/WEB-INF/jsp/my_share_detail.jsp").forward(req, res);
	}
	
	/**
	 * order_confirm页
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 */
	@RequestMapping(value = "/order_confirm/{id}")
	public void order_confirm(@PathVariable("id")String id,HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)throws Exception{
		//课程id
		//String id = req.getParameter("rest_id");
		req.setAttribute("id", id);
		req.getRequestDispatcher("/WEB-INF/jsp/order_confirm.html.jsp").forward(req, res);
	}
	/**
	 * 登陆页
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 */
	@RequestMapping(value = "/login/{page}")
	public void login(@PathVariable("page")String page,HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)throws Exception{
		
		//String page = req.getParameter("rest_page");
		req.setAttribute("page", page);
		String openId = req.getParameter("openid");
		System.out.println(openId);
		req.setAttribute("openId", openId);
		
		req.getRequestDispatcher("/WEB-INF/jsp/login.jsp?openId="+openId).forward(req, res);
	}
	/**
	 * 课程详情页
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 */
	@RequestMapping(value = "/courseDetail/{courseId}")
	public void courseDetail(@PathVariable("courseId")String courseId,HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)throws Exception{
		//String courseId = req.getParameter("rest_courseId");
		req.setAttribute("courseId", courseId);
		req.getRequestDispatcher("/WEB-INF/jsp/courseDetail.jsp").forward(req, res);
	}
	/**
	 * 课程详情页，带分享码的
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 */
	@RequestMapping(value = "/courseDetail/{courseId}/{courseName}/{shareCode}")
	public void courseDetailShare(
			@PathVariable("courseId")String courseId,@PathVariable("courseName")String courseName,
			@PathVariable("shareCode")String shareCode,	HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)throws Exception{
	/*	String courseId = req.getParameter("rest_courseId");
		String shareCode = req.getParameter("rest_shareCode");
		String courseName = req.getParameter("rest_courseName");*/
		if (shareCode != null 	&& !"".equals(shareCode.trim()) 
				&& !"null".equals(shareCode.trim().toLowerCase())
				&& service.needWriteShareCode(shareCode,req)) {
			CookieUtil.setCookie(res, "_usercode_", shareCode, "ixincheng.com");
		}
		req.setAttribute("courseId", courseId);
		req.setAttribute("courseName",java.net.URLDecoder.decode(courseName));
		req.getRequestDispatcher("/WEB-INF/jsp/courseDetail.jsp").forward(req, res);
	}
	
	/**
	 * 我的已购买课程
	 */
	@RequestMapping(value = "/my_course")
	public void myCourse(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)throws Exception{
		
		req.getRequestDispatcher("/WEB-INF/jsp/mycourse.jsp").forward(req, res);
	}
	
	/**
	 * 去支付页面
	 */
	@RequestMapping(value = "/to_pay/{orderNo}")
	public void toPay(@PathVariable("orderNo")String orderNo,HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)throws Exception{
		
		//String orderNo = req.getParameter("rest_orderNo");
		req.setAttribute("orderNo", orderNo);
		req.getRequestDispatcher("/WEB-INF/jsp/to_pay.jsp").forward(req, res);
	}
	
	/**
	 * 去试学页面
	 */
	@RequestMapping(value = "/lession_course/{courseId}")
	public void lession_course(@PathVariable("courseId")String courseId,HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)throws Exception{
		
		//String courseId = req.getParameter("rest_courseId");
		req.setAttribute("courseId", courseId);
		req.getRequestDispatcher("/WEB-INF/jsp/lession_course.jsp").forward(req, res);
	}
	
	/**
	 * 个人中心页面
	 */
	@RequestMapping(value = "/personal/{opendId}/{code}")
	public void personal(@PathVariable("opendId")String openId,
			@PathVariable("code")String code,HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)throws Exception{
		/*yuruixin---add---2017-07-27*/
	/*	String openId = req.getParameter("rest_openId");
		String code = req.getParameter("rest_code");*/
		req.setAttribute("openId", openId);
		req.setAttribute("code", code);
		/*yuruixin---add---2017-07-27*/
		req.getRequestDispatcher("/WEB-INF/jsp/personal.jsp").forward(req, res);
	}
	
	/**
	 * 个人中心页面
	 */
	@RequestMapping(value = "/personal")
	public void personal1(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)throws Exception{
		req.getRequestDispatcher("/WEB-INF/jsp/personal.jsp").forward(req, res);
	}
	
	/**
	 * 待支付页面
	 * 
	 */
	@RequestMapping(value = "/wait_money")
	public void wait_money(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)throws Exception{
		
		req.getRequestDispatcher("/WEB-INF/jsp/wait-money.jsp").forward(req, res);
	}
	
	/**
	 * 去注册页面
	 * 
	 */
	@RequestMapping(value = "/reg")
	public void reg(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)throws Exception{
		
		req.getRequestDispatcher("/WEB-INF/jsp/reg.jsp").forward(req, res);
	}
	
	/**
	 * 去忘记密码页面
	 * 
	 */
	@RequestMapping(value = "/forgotPassword")
	public void forgotPassword(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)throws Exception{
		
		req.getRequestDispatcher("/WEB-INF/jsp/forgotPassword.jsp").forward(req, res);
	}
	
	
	/**
	 * 我的已购买课程
	 */
	@RequestMapping(value = "/my_course_video/{courseId}")
	public void myCourseVideo(@PathVariable("courseId")String courseId,HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)throws Exception{
		//String courseId = req.getParameter("rest_courseId");
		req.setAttribute("courseId", courseId);
		req.getRequestDispatcher("/WEB-INF/jsp/mycourse_video.jsp").forward(req, res);
	}
	
	/**
	 * 获取课程下视频
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 */
	@RequestMapping(value = "/look_course/{courseId}")
	public void look_course(@PathVariable("courseId")String courseId,HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)throws Exception{
		//String courseId = req.getParameter("rest_courseId");
		req.setAttribute("courseId", courseId);
		req.getRequestDispatcher("/WEB-INF/jsp/look_course.jsp").forward(req, res);
	}
	
}
