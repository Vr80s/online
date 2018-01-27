package com.xczh.consumer.market.controller.course;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.OnlineCourseService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.wechat.course.service.ICourseService;
import com.xczhihui.wechat.course.service.IFocusService;
/**
 * 点播控制器 ClassName: BunchPlanController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>
 * email: 15936216273@163.com <br>
 * Create Time: 2017年8月11日<br>
 */
@Controller
@RequestMapping("/xczh/mylive")
public class MyLiveController {

	@Autowired
	private ICourseService courseServiceImpl;

	@Autowired
	@Qualifier("focusServiceRemote")
	private IFocusService ifocusService;
	
	@Autowired
	private OnlineUserService onlineUserService;
	
	@Autowired
	private OnlineCourseService onlineCourseService;
	
	@Autowired
	private AppBrowserService appBrowserService;
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MyLiveController.class);
	
	/**
	 * Description：开始直播时调用，因为要区分这个直播来自app直播呢，还是pc端直播了。
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("appLivePre")
	@ResponseBody
	public ResponseObject myFocus(HttpServletRequest req,
			@RequestParam("courseId")Integer courseId)
			throws Exception {
	    onlineCourseService.updateLiveSourceType(courseId);
		return ResponseObject.newSuccessResponseObject("操作成功");
	}
}
