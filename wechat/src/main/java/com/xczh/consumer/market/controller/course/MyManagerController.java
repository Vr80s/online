package com.xczh.consumer.market.controller.course;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.enums.OrderFrom;
import com.xczhihui.common.util.enums.SMSCode;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.service.IFocusService;
import com.xczhihui.course.service.IMyInfoService;
import com.xczhihui.course.util.XzStringUtils;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.medical.anchor.service.IUserBankService;
import com.xczhihui.medical.anchor.vo.UserBank;
import com.xczhihui.medical.doctor.service.IMedicalDoctorApplyService;
import com.xczhihui.online.api.service.EnchashmentService;
import com.xczhihui.online.api.service.UserCoinService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的信息管理页面 ClassName: MyManagerController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>
 * email: 15936216273@163.com <br>
 * Create Time: 2018年1月22日<br>
 */
@Controller
@RequestMapping("/xczh/manager")
public class MyManagerController {

	@Autowired
	private ICourseService courseService;

	@Autowired
	private OnlineUserService onlineUserService;

	@Autowired
	private AppBrowserService appBrowserService;

	@Autowired
	private EnchashmentService enchashmentService;

	@Autowired
	private UserCoinService userCoinService;

	@Autowired
	private IMyInfoService myInfoService;

	@Autowired
	private IUserBankService userBankService;

	@Autowired
	private IMedicalDoctorApplyService medicalDoctorApplyService;
	
	@Autowired
	@Qualifier("focusServiceRemote")
	private IFocusService focusServiceRemote;

	@Value("${rate}")
	private int rate;

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MyManagerController.class);

	/**
	 * Description：进入我的页面显示几个初始化数据
	 *
	 * @param req
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	@RequestMapping("home")
	@ResponseBody
	public ResponseObject home(HttpServletRequest req)
			throws Exception {
		/*
		 * 显示熊猫币、已购买的课程（不包含免费的）、是否是主播 获取用户信息
		 */
		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
		/**
		 * 判断这个用户有没有主播权限
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		if (user != null) {
			String userId = user.getId();
			
			// 熊猫币-- 普通用户的平台
			map.put("xmbCount",userCoinService.getBalanceByUserId(userId));
			// 更新下用户信息
			OnlineUser ou = onlineUserService.findUserById(userId);
			if(ou == null){
				return ResponseObject.newErrorResponseObject("token过期",1002);
			}
			ou.setUserCenterId(ou.getId());

			map.put("user", ou);
			// 查找购买的课程数
			map.put("courseCount",courseService.selectMyFreeCourseListCount(userId));
			//是否拥有主播权限
			Integer hostPermissions = myInfoService.getUserHostPermissions(userId);
			LOGGER.info(hostPermissions+"");
			// 查看主播权限   -- 并且把主播信息给返回过去
			map.put("hostPermissions", hostPermissions != null ? hostPermissions :0);
			if(hostPermissions!=null &&  hostPermissions == 1){
				//申请的医师信息
				map.put("medicalDoctor", medicalDoctorApplyService.getLastOne(userId));
			}
			
			map.put("tokenVaild",1);
			//新增关注数和粉丝数的显示
			List<Integer> listff =   focusServiceRemote.selectFocusAndFansCountAndCriticizeCount(userId);
			
			map.put("fansCount", listff.get(0));       	   //粉丝总数
			map.put("focusCount", listff.get(1));   	  	   //关注总数
		} else {
			map.put("xmbCount", 0);
			map.put("user", "");
			map.put("courseCount", 0);
			map.put("hostPermissions",0);
			map.put("tokenVaild",0);
			map.put("fansCount", 0);       	   //粉丝总数
			map.put("focusCount",0);   	  	   //关注总数
		}
		return ResponseObject.newSuccessResponseObject(map);
	}

	/**
	 * Description：我已购买课程的不包含免费的接口
	 * @param req
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	@RequestMapping("freeCourseList")
	@ResponseBody
	public ResponseObject freeCourseList(HttpServletRequest req,
										 @RequestParam("pageNumber") Integer pageNumber,
										 @RequestParam("pageSize") Integer pageSize) throws Exception {
		/*
		 * 显示熊猫币、已购买的课程（不包含免费的）、是否是主播 获取用户信息
		 */
		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
		if(user == null){
			return ResponseObject.newErrorResponseObject("登录失效！");
		}
		Page<CourseLecturVo> page = new Page<>();
		if(pageNumber==0){
			pageNumber= 1;
		}
		page.setCurrent(pageNumber);
		page.setSize(pageSize);
		return ResponseObject.newSuccessResponseObject(courseService.selectMyFreeCourseList(page, user.getUserId()));
	}


	/**
	 *
	 * Description：获取 我的钱包  熊猫币余额
	 * @param request
	 * @param res
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	@RequestMapping("getWalletEnchashmentBalance")
	@ResponseBody
	public ResponseObject getWalletEnchashmentBalance(HttpServletRequest request,
													  HttpServletResponse res) throws Exception {
		OnlineUser user = appBrowserService.getOnlineUserByReq(request);
		if (user == null) {
			return ResponseObject.newSuccessResponseObject(0);
		} else {
			return ResponseObject.newSuccessResponseObject(userCoinService.getBalanceByUserId(user.getId()));
		}
	}
	/**
	 *
	 * Description：获取 主播控制台  熊猫币余额
	 * @param request
	 * @param res
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	@RequestMapping("getEnchashmentBalance")
	@ResponseBody
	public ResponseObject getEnchashmentBalance(HttpServletRequest request,
												HttpServletResponse res) throws Exception {
		OnlineUser user = appBrowserService.getOnlineUserByReq(request);
		if (user == null) {
			return ResponseObject.newSuccessResponseObject(0);
		} else {

			String xmbye = userCoinService.getSettlementBalanceByUserId(user.getId());
			double d_xmbye = Double.valueOf(xmbye);
			if(d_xmbye>=1){
				int i = (int)d_xmbye;
				return ResponseObject.newSuccessResponseObject(i);
			}else{
				return ResponseObject.newSuccessResponseObject(d_xmbye);
			}

		}
	}

	/**
	 *
	 * Description：获取 主播控制台  人民币
	 * @param request
	 * @param res
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	@RequestMapping("getEnchashmentRmbBalance")
	@ResponseBody
	public ResponseObject getEnchashmentRmbBalance(HttpServletRequest request,
												   HttpServletResponse res) throws Exception {
		OnlineUser user = appBrowserService.getOnlineUserByReq(request);
		if (user == null) {
			throw new RuntimeException("登录失效");
		}
		return ResponseObject.newSuccessResponseObject(userCoinService.getEnchashmentBalanceByUserId(user.getId()));
	}

	/**
	 * Description：我的钱包接口
	 *
	 * @param req
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	@RequestMapping("wallet")
	@ResponseBody
	public ResponseObject wallet(HttpServletRequest req,
								 @RequestParam("pageNumber") Integer pageNumber,
								 @RequestParam("pageSize") Integer pageSize) throws Exception {

		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
		if (user == null) {
			return ResponseObject.newErrorResponseObject("登录失效");
		}

		int num = (pageNumber - 1) * pageSize;
		num = num < 0 ? 0 : num;

		return ResponseObject.newSuccessResponseObject(myInfoService.findUserWallet(num,pageSize, user.getId()));
	}

	/**
	 * Description：主播控制台 -- 显示上面的数量级
	 *
	 * @param req
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	@RequestMapping("anchorConsoleNumber")
	@ResponseBody
	public ResponseObject anchorConsoleNumber(HttpServletRequest req)
			throws Exception {

		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
		if (user == null) {
			return ResponseObject.newErrorResponseObject("登录失效");
		}
		String userId = user.getId();
		Map<String, Object> map = new HashMap<String, Object>();
		List<BigDecimal> list = myInfoService
				.selectCollegeCourseXmbNumber(userId);

		map.put("collegeNumber", list.get(0).intValue()); // 学员数量
		map.put("courseNumber", list.get(1).intValue());  // 课程数量
		/*
		 * 熊猫币数量和人民币数量，
		 */
		String xmbye = userCoinService.getSettlementBalanceByUserId(user.getId());
		double d_xmbye = Double.valueOf(xmbye);
		map.put("xmbNumber",d_xmbye);
		map.put("rmbNumber",userCoinService.getEnchashmentBalanceByUserId(userId));

		return ResponseObject.newSuccessResponseObject(map);
	}

	/**
	 * Description：主播控制台 -- 显示主播的课程
	 *
	 * @param req
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	@RequestMapping("anchorConsoleCourse")
	@ResponseBody
	public ResponseObject anchorConsoleCourse(HttpServletRequest req)
			throws Exception {

		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
		if (user == null) {
			return ResponseObject.newErrorResponseObject("登录失效");
		}
		List<Map<String, Object>> mapCourseList = new ArrayList<Map<String, Object>>();
		List<CourseLecturVo> list = courseService.selectUserConsoleCourse(user
				.getId());

		Map<String, Object> mapTj = new HashMap<String, Object>();
		Map<String, Object> mapNw = new HashMap<String, Object>();
		List<CourseLecturVo> listTj = new ArrayList<CourseLecturVo>();
		List<CourseLecturVo> listNw = new ArrayList<CourseLecturVo>();
		for (CourseLecturVo courseLecturVo : list) {
			if ("直播间".equals(courseLecturVo.getNote())) {
				listTj.add(courseLecturVo);
			}
			if ("我的课程".equals(courseLecturVo.getNote())) {
				listNw.add(courseLecturVo);
			}
		}
		mapTj.put("title", "直播间");
		mapTj.put("courseList", listTj);
		mapNw.put("title", "我的课程");
		mapNw.put("courseList", listNw);

		mapCourseList.add(mapTj);
		mapCourseList.add(mapNw);
		return ResponseObject.newSuccessResponseObject(mapCourseList);
	}



	/**
	 * Description：主播控制台 -- 直播间的课程
	 * @param req
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	@RequestMapping("anchorConsoleLiveCourse")
	@ResponseBody
	public ResponseObject anchorConsoleCourse(HttpServletRequest req,
											  @RequestParam("pageNumber") Integer pageNumber,
											  @RequestParam("pageSize") Integer pageSize)
			throws Exception {

		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
		if (user == null) {
			return ResponseObject.newErrorResponseObject("登录失效");
		}
		Page<CourseLecturVo> page = new Page<>();
		if(pageNumber==0){
			pageNumber= 1;
		}
		page.setCurrent(pageNumber);
		page.setSize(pageSize);
		List<CourseLecturVo> list = courseService.selectUserConsoleCourseLiveByPage(page,user.getId());
		return ResponseObject.newSuccessResponseObject(list);
	}



	/**
	 * Description：主播控制台 我的课程（app端我的课程 全部、直播、视频、线下课、音频） 包括审批的包括没有审批的
	 *
	 * @param req
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	@RequestMapping("anchorConsoleApplyCourse")
	@ResponseBody
	public ResponseObject anchorConsoleApplyCourse(HttpServletRequest req,
												   @RequestParam("pageNumber") Integer pageNumber,
												   @RequestParam("pageSize") Integer pageSize,
												   @RequestParam("type") Integer type) throws Exception {

		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
		if (user == null) {
			return ResponseObject.newErrorResponseObject("登录失效");
		}
		Integer courseFrom = null; // 课程类型：1.直播 2.点播 3.线下课
		Integer multimediaType = null; // 多媒体类型:1视频2音频

		//type 查询类型：0 全部 1 直播 2 视频 3 线下课 4 音频
		if (type == 1) {
			courseFrom = 1;
		} else if (type == 2) {
			courseFrom = 2;
			multimediaType = 1;
		} else if (type == 3) {
			courseFrom = 3;
		} else if (type == 4) {
			courseFrom = 2;
			multimediaType = 2;
		}

		Page<CourseLecturVo> page = new Page<>();
		if(pageNumber==0){
			pageNumber= 1;
		}
		page.setCurrent(pageNumber);
		page.setSize(pageSize);

		return ResponseObject.newSuccessResponseObject(courseService
				.selectAppCourseApplyPage(page, user.getId(), courseFrom,multimediaType));
	}

	/**
	 * Description：资产--->结算tab记录 
	 * @param req
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	@RequestMapping("settlementList")
	@ResponseBody
	public ResponseObject settlementList(HttpServletRequest req,
										 @RequestParam("pageNumber") Integer pageNumber,
										 @RequestParam("pageSize") Integer pageSize) throws Exception {

		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
		if (user == null) {
			return ResponseObject.newErrorResponseObject("登录失效");
		}
		int num = (pageNumber - 1) * pageSize;
		num = num < 0 ? 0 : num;

		return ResponseObject.newSuccessResponseObject(myInfoService.selectSettlementList(num,pageSize,user.getId()));
	}

	/**
	 * Description：资产---> 提现tab记录
	 * @param req
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	@RequestMapping("withdrawalList")
	@ResponseBody
	public ResponseObject withdrawalList(HttpServletRequest req,
										 @RequestParam("pageNumber") Integer pageNumber,
										 @RequestParam("pageSize") Integer pageSize) throws Exception {

		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
		if (user == null) {
			return ResponseObject.newErrorResponseObject("登录失效");
		}
		int num = (pageNumber - 1) * pageSize;
		num = num < 0 ? 0 : num;
		return ResponseObject.newSuccessResponseObject(myInfoService.selectWithdrawalList(num,pageSize,user.getId()));
	}


	/**
	 * Description：结算 --- 扣减熊猫币增加人民币
	 * @param req
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	@RequestMapping("settlement")
	@ResponseBody
	public ResponseObject settlement(HttpServletRequest req,
									 @RequestParam("xmbNumber") Integer xmbNumber,
									 @RequestParam("orderFrom") Integer orderFrom) throws Exception {
		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
		if (user == null) {
			return ResponseObject.newErrorResponseObject("登录失效");
		}
		/**
		 * 结算服务
		 */
		enchashmentService.saveSettlement(user.getId(), xmbNumber,OrderFrom.valueOf(orderFrom));
		return ResponseObject.newSuccessResponseObject("结算成功");
	}

	/**
	 * Description：提现前-- 验证银行卡信息时候正确
	 * @param req
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	@RequestMapping("withdrawalValidation")
	@ResponseBody
	public ResponseObject withdrawalValidation(HttpServletRequest req,
											   @RequestParam("bankCard") String bankCard) throws Exception {
		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
		if (user == null) {
			return ResponseObject.newErrorResponseObject("登录失效");
		}
		UserBank ub = userBankService.selectUserBankByUserIdAndAcctPan(user.getId(), bankCard, null);
		if (ub == null) {
			return ResponseObject.newErrorResponseObject("请输入有效的银行卡信息");
		}
		return ResponseObject.newSuccessResponseObject("银行卡信息正确,点击获取验证码,验证短信信息");
	}

	/**
	 * Description：提现   --- 发送短信验证码  
	 * @param req
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	@RequestMapping("sendSMSCode")
	@ResponseBody
	public ResponseObject withdrawalSendSMSCode(HttpServletRequest req) throws Exception {

		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
		if (user == null) {
			return ResponseObject.newErrorResponseObject("登录失效");
		}
		/**
		 * 验证手机号
		 */
		String userName = req.getParameter("mobile");
		if(!StringUtils.isNotBlank(userName)){
			userName = user.getLoginName();
		}
		if(!XzStringUtils.checkPhone(userName)){
			return ResponseObject.newErrorResponseObject("请输入正确的手机号");
		}
		/**
		 * 验证银行卡号
		 */
//		UserBank ub = userBankService.selectUserBankByUserIdAndAcctPan(user.getId(), bankCard, null);
//		if (ub == null) {
//			return ResponseObject.newErrorResponseObject("请输入有效银行卡信息");
//		}
		try {
			/*
			 * 发送短信验证码
			 */
			String str =onlineUserService.addMessage(userName, SMSCode.WITHDRAWAL.getCode());
			if("发送成功".equals(str)){
				return ResponseObject.newSuccessResponseObject(str);
			}else{
				return ResponseObject.newErrorResponseObject(str);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.info("获取错误信息啦"+e.getMessage());
			return ResponseObject.newErrorResponseObject("发送失败");
		}
	}

	/**
	 * Description：提现 得到银行卡号，提现的额度
	 *
	 * @param req
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	@RequestMapping("withdrawal")
	@ResponseBody
	public ResponseObject withdrawal(HttpServletRequest req,
									 @RequestParam("rmbNumber") BigDecimal rmbNumber,
									 @RequestParam("smsCode") String smsCode,
									 @RequestParam("bankCardId") Integer bankCardId,
									 @RequestParam("orderFrom") Integer orderFrom) throws Exception {
		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
		if (user == null) {
			return ResponseObject.newErrorResponseObject("登录失效");
		}
		/**
		 * 验证手机号
		 */
		String userName = req.getParameter("userName");
		if(!StringUtils.isNotBlank(userName)){
			userName = user.getLoginName();
		}
		if(!XzStringUtils.checkPhone(userName)){
			return ResponseObject.newErrorResponseObject("请输入正确的手机号");
		}
		/**
		 * 验证银行卡信息
		 */
//		UserBank ub = userBankService.selectUserBankByUserIdAndAcctPan(user.getId(), bankCard, null);
//		if (ub == null) {
//			return ResponseObject.newErrorResponseObject("请输入有效银行卡信息");
//		}
		/**
		 * 验证短信信息
		 */
		ResponseObject rob = onlineUserService.changeMobileCheckCode(userName, smsCode, SMSCode.WITHDRAWAL.getCode());
		//短信验证码成功
		if(rob.isSuccess()){
			enchashmentService.saveEnchashmentApplyInfo(user.getId(),rmbNumber,bankCardId, OrderFrom.valueOf(orderFrom));
			return ResponseObject.newSuccessResponseObject("提现成功");
		}else{
			return rob;
		}
	}
}
