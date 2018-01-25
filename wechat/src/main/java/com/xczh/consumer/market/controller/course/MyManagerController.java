package com.xczh.consumer.market.controller.course;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.OnlineCourseService;
import com.xczh.consumer.market.service.OnlineOrderService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.bxg.online.api.service.EnchashmentService;
import com.xczhihui.bxg.online.api.service.UserCoinService;
import com.xczhihui.medical.doctor.vo.MedicalDoctorVO;
import com.xczhihui.wechat.course.service.ICourseService;
import com.xczhihui.wechat.course.service.IFocusService;
import com.xczhihui.wechat.course.vo.CourseLecturVo;
/**
 * 我的信息管理页面
 * ClassName: MyManagerController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年1月22日<br>
 */
@Controller
@RequestMapping("/xczh/manager")
public class MyManagerController {

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
	
	@Autowired
	private OnlineOrderService onlineOrderService;
	
	@Autowired
	private EnchashmentService enchashmentService;
	
	
	@Autowired
	private UserCoinService userCoinService;
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MyManagerController.class);
	/**
	 * Description：进入我的页面显示几个初始化数据
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("home")
	@ResponseBody
	public ResponseObject freeCourseNumber(HttpServletRequest req)
			throws Exception {
		/*
		 * 显示熊猫币、已购买的课程（不包含免费的）、是否是主播
		 * 获取用户信息
		 */
		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
		/**
		 * 判断这个用户有没有主播权限
		 */
		Map<String,Object> map = new HashMap<String,Object>();
		if(user != null){
			System.out.println("================");
			//熊猫币
			map.put("xmbCount", userCoinService.getBalanceByUserId(user.getId()).get("balanceTotal"));
			//更新下用户信息
			map.put("user",onlineUserService.findUserById(user.getId()));
			//查找购买的课程数
			map.put("courseCount",courseServiceImpl.selectMyFreeCourseListCount(user.getId()));
		}else{
			System.out.println("================1111111111");
			map.put("xmbCount",0);
			map.put("user","");
			map.put("courseCount",0); //这里存在列表存在总数
		}
		return ResponseObject.newSuccessResponseObject(map);
	}

	/**
	 * Description：我已购买课程的不包含免费的接口
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("freeCourseList")
	@ResponseBody
	public ResponseObject freeCourseList(HttpServletRequest req,
			@RequestParam("pageNumber")Integer pageNumber,
			@RequestParam("pageSize")Integer pageSize)
			throws Exception {
		/*
		 * 显示熊猫币、已购买的课程（不包含免费的）、是否是主播
		 * 获取用户信息
		 */
		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
		Page<CourseLecturVo> page = new Page<>();
	    page.setCurrent(pageNumber);
	    page.setSize(pageSize);
	    System.out.println("===========");
		return ResponseObject.newSuccessResponseObject(courseServiceImpl.selectMyFreeCourseList(page, user.getUserId()));
	}
	
	
    @RequestMapping("getEnchashmentBalance")
    @ResponseBody
    public ResponseObject getEnchashmentBalance(HttpServletRequest request, HttpServletResponse res) throws Exception{
      
        OnlineUser user = appBrowserService.getOnlineUserByReq(request); // onlineUserMapper.findUserById("2c9aec345d59c9f6015d59caa6440000");
        if (user == null) {
        	return ResponseObject.newSuccessResponseObject(0);	
        }else{
        	return ResponseObject.newSuccessResponseObject(enchashmentService.enableEnchashmentBalance(user.getId()));	
        }
    }
    @RequestMapping("getEnchashmentRmbBalance")
    @ResponseBody
    public ResponseObject getEnchashmentRmbBalance(HttpServletRequest request, HttpServletResponse res) throws Exception{

        OnlineUser user = appBrowserService.getOnlineUserByReq(request); // onlineUserMapper.findUserById("2c9aec345d59c9f6015d59caa6440000");
        if (user == null) {
            throw new RuntimeException("登录超时！");
        }
        return ResponseObject.newSuccessResponseObject(enchashmentService.enableEnchashmentRmbBalance(user.getId()));
    }
	
	
	/**
	 * Description：我的钱包接口
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("wallet")
	@ResponseBody
	public ResponseObject wallet(HttpServletRequest req,
			@RequestParam("pageNumber")Integer pageNumber,
			@RequestParam("pageSize")Integer pageSize)
			throws Exception {
	
		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
		if(user == null){
			return ResponseObject.newErrorResponseObject("登录失效");
		}
		return ResponseObject.newSuccessResponseObject(onlineOrderService.findUserWallet(pageNumber,pageSize,user.getId()));
	}
	
	
	/**
	 * Description：主播控制台
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("anchorConsole")
	@ResponseBody
	public ResponseObject anchorConsole(HttpServletRequest req,
			@RequestParam("pageNumber")Integer pageNumber,
			@RequestParam("pageSize")Integer pageSize)
			throws Exception {
	
		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
		if(user == null){
			return ResponseObject.newErrorResponseObject("登录失效");
		}
		
		
		
		
		
		
		return ResponseObject.newSuccessResponseObject(onlineOrderService.findUserWallet(pageNumber,pageSize,user.getId()));
	}
	
	
	
	
	
	
}
