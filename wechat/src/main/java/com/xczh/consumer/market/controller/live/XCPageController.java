package com.xczh.consumer.market.controller.live;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.vo.ItcastUser;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;


@Controller
@RequestMapping(value = "/bxg/xcpage")
public class XCPageController {

	@Autowired
	private UserCenterAPI userCenterAPI;

	@Autowired

	private AppBrowserService appBrowserService;

	@Value("${gift.im.room.postfix}")
	private String postfix;
	@Value("${gift.im.boshService}")
	private String boshService;//im服务地址
	@Value("${gift.im.host}")
	private  String host;

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(XCPageController.class);
	/**
	 * 跳转到观看历史页面
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 */
	@RequestMapping(value = "/history")
	public void history(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)throws Exception{
		req.getRequestDispatcher("/WEB-INF/jsp/history.jsp").forward(req, res);
	}
	/**
	 * 跳转到搜索页面
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryResult")
	public void queryResult(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)throws Exception{
		
		req.getRequestDispatcher("/WEB-INF/jsp/queryResult.jsp").forward(req, res);
	}
	
	/**
	 * 跳转到详情页面
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 */
	@RequestMapping(value = "/courseDetails")
	public void courseDetails(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)throws Exception{
		//page = 1 跳转到首页。 2、搜索页面    3、观看记录
		//String course_id = req.getParameter()("rest_course_id");
		
		String courseId = req.getParameter("courseId");
		for (String key : params.keySet()) {
			   log.info("key= "+ key + " and value= " + req.getParameter(key));
	    }
		req.setAttribute("course_id", courseId);

		OnlineUser user = appBrowserService.getOnlineUserByReq(req, params);
		if(user!=null){
			ItcastUser iu = userCenterAPI.getUser(user.getLoginName());
			req.setAttribute("guId",iu.getId());
			req.setAttribute("guPwd", iu.getPassword());
		}
		req.setAttribute("host", host);
		req.setAttribute("boshService",boshService);
		req.setAttribute("roomJId",courseId+postfix);
		req.getRequestDispatcher("/WEB-INF/jsp/details.jsp").forward(req, res);
	}
	/**
	 * 跳转到预约页面
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 */
	@RequestMapping(value = "/foreshow/{course_id}")
	public void foreshow(@PathVariable("course_id")String course_id,HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)throws Exception{
		req.setAttribute("course_id", course_id);
		req.getRequestDispatcher("/WEB-INF/jsp/foreshow.jsp").forward(req, res);
	}
	
}
