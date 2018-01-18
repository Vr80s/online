package com.xczh.consumer.market.controller.live;

//import java.util.Date;
//import java.util.LinkedList;
//import java.util.List;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.FocusService;
import com.xczh.consumer.market.service.GiftService;
import com.xczh.consumer.market.service.OLAttachmentCenterService;
import com.xczh.consumer.market.service.OLCourseServiceI;
import com.xczh.consumer.market.service.OnlineCourseService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.service.OnlineWebService;
import com.xczh.consumer.market.utils.Broadcast;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.vo.CourseLecturVo;
import com.xczhihui.bxg.online.api.po.LiveExamineInfo;
import com.xczhihui.bxg.online.api.service.LiveExamineInfoService;

//import java.util.UUID;

/**
 * 直播控制器
 * ClassName: LiveController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2017年8月10日<br>
 */
@Controller
@RequestMapping("/bxg/live")
public class LiveController {

	@Autowired
	private OnlineCourseService onlineCourseService;

	@Autowired
	private OLCourseServiceI wxcpCourseService;
	
	@Autowired
	private OnlineUserService onlineUserService;
	
	@Autowired
	private FocusService focusService;
	
	@Autowired
	private AppBrowserService appBrowserService;

	@Autowired
	private GiftService giftService;
	
	@Autowired
	private OnlineWebService onlineWebService;

	@Autowired
	private LiveExamineInfoService liveExamineInfoService;

	@Autowired
	private OLAttachmentCenterService service;


	@Value("${gift.im.room.postfix}")
	private String postfix;
	@Value("${gift.im.boshService}")
	private String boshService;//im服务地址
	@Value("${gift.im.host}")
	private  String host;

	@Autowired
	private Broadcast broadcast;
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(LiveController.class);
	
	/**
	 * Description： 直播搜索接口
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("listKeywordQuery")
	@ResponseBody
	public ResponseObject listKeywordQuery(HttpServletRequest req,
			 HttpServletResponse res, Map<String, String> params)
			throws Exception {
		
		try {
			String queryParam = req.getParameter("keyword");
			Map<String,Object> allMap = new HashMap<String, Object>();
			//第一步先让数据显示出来，查询管用！
			//第二步后续的话在修改程序吧！
			/**
			 * 查询主播信息   --》 按照关注数来查询主播的 
			 */
			List<Map<String, Object>> mapUserList = onlineUserService.findHotHostByQueryKey(queryParam);
			/**
			 * 查询直播信息 --》 直播信息按照礼物的多少来排序
			 */
			List<CourseLecturVo> liveList =
			     onlineCourseService.findLiveListByQueryKey(0,3,queryParam);
			/**
			 * 查询点播信息 --》 这个课程的学习人数
			 */
			List<CourseLecturVo> bunchlist = wxcpCourseService.courseCategoryXCList1(1, 3,queryParam);
			
			allMap.put("bozhu", mapUserList);
			allMap.put("zhibo", liveList);
			allMap.put("bunch", bunchlist);
			
			return ResponseObject.newSuccessResponseObject(allMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("后台流程异常");
		}
	}
	/**
	 * Description： 直播列表页
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("list")
	@ResponseBody
	public ResponseObject list(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)
			throws Exception {
		
		LOGGER.info("{}{}{}{}{}{}{}{}{}");
		
		if(null == req.getParameter("pageNumber") && null == req.getParameter("pageSize")){
			return ResponseObject.newErrorResponseObject("缺少分页参数");
		}
		int pageNumber =Integer.parseInt(req.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(req.getParameter("pageSize"));
		try {
			List<CourseLecturVo> list = onlineCourseService.findLiveListInfo(pageNumber,pageSize,null);
			LOGGER.info("list.size():"+list.size());
			if(list!=null && list.size()>0){
				return ResponseObject.newSuccessResponseObject(list);
			}else{
				return ResponseObject.newErrorResponseObject("数据为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseObject.newErrorResponseObject(null);
	}
    /**
     * Description：直播详情页面
     * @param req
     * @param res
     * @return
     * @throws Exception
     * @return ResponseObject
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
	@RequestMapping("liveDetails")
	@ResponseBody
	public ResponseObject liveDetails(HttpServletRequest req,
			HttpServletResponse res )
			throws Exception {

		Map<String, String> params =new HashMap<>();
		params.put("token",req.getParameter("token"));
		//根据课程id得到这些信息
		if(null == req.getParameter("course_id")){
			return ResponseObject.newErrorResponseObject("缺少参数");
		}
		/*
		 * 全部不拦截  --》
		 */
		OnlineUser user = appBrowserService.getOnlineUserByReq(req, params);
		int course_id =Integer.parseInt(req.getParameter("course_id"));
		CourseLecturVo courseLecturVo  = null;
		if(null == user){
			 courseLecturVo = onlineCourseService.
					liveDetailsByCourseId(course_id,null); //课程简介
			 if(null == courseLecturVo){
					return ResponseObject.newErrorResponseObject("获取课程数据有误");
			 }
		}else{
			courseLecturVo = onlineCourseService.
				liveDetailsByCourseId(course_id,user.getId()); //课程简介
			
			if(null == courseLecturVo){
				return ResponseObject.newErrorResponseObject("获取课程数据有误");
			}
			if(courseLecturVo.getWatchState()==0){
				onlineWebService.saveEntryVideo(course_id, user);
			}else{
				if(courseLecturVo.getUserId().equals(user.getId()) ||
						onlineWebService.getLiveUserCourse(course_id,user.getId()).size()>0){
			       //LOGGER.info("同学,当前课程您已经报名了!");
			       courseLecturVo.setWatchState(0);    
			    };
			}
			/**
			 * 是否已经关注了这个主播：0 未关注  1已关注
			 */
			Integer isFours  = focusService.myIsFourslecturer(user.getId(), courseLecturVo.getUserId());
			courseLecturVo.setIsfocus(isFours);
		}
		/**
		 * 发送广播
		 */
		if(courseLecturVo.getWatchState()==0){
			/**
			 * 增加浏览次数
			 */
			onlineCourseService.updateBrowseSum(course_id);
	      	Map<String,Object> onlineCount = new HashMap<String,Object>();
        	onlineCount.put("messageType", 2);
        	onlineCount.put("onlineCount", courseLecturVo.getLearndCount());
        	String onlineCountStr = JSONObject.toJSONString(onlineCount);
        	//{"messageType":2,"onlineCount":222}
			broadcast.loginAndSend(course_id+"", onlineCountStr);
		}//
		courseLecturVo.setImRoomId(courseLecturVo.getId()+postfix);
		courseLecturVo.setGiftCount(giftService.findByUserId(courseLecturVo.getUserId()));

		String moneySum=onlineCourseService.sumMoneyLive(course_id+"");
		courseLecturVo.setRewardCount(moneySum);

		return ResponseObject.newSuccessResponseObject(courseLecturVo);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	@RequestMapping("addLive")
	@ResponseBody
	public ResponseObject addLive(HttpServletRequest req,
								  HttpServletResponse res, LiveExamineInfo liveExamineInfo, @RequestParam("file")MultipartFile file)
			throws Exception {

		Map<String,String> map = new HashMap<String,String>();
		String projectName="other";
		String fileType="1"; //图片类型了
		String headImgPath = service.upload(null, //用户中心的用户ID
				projectName, file.getOriginalFilename(),file.getContentType(), file.getBytes(),fileType,null);

		JSONObject json = JSONObject.parseObject(headImgPath);
		LOGGER.info("文件路径——path:"+headImgPath);
		map.put("logo", json.get("url").toString());

		LOGGER.info("req.getParameterprice================"+req.getParameter("price"));
		if("1".equals(liveExamineInfo.getSeeMode())){//收费
			liveExamineInfo.setPrice(new BigDecimal(req.getParameter("price")));
		}
		if("2".equals(liveExamineInfo.getSeeMode())){//密码
			liveExamineInfo.setPassword(req.getParameter("password"));
		}

		liveExamineInfo.setLogo(map.get("logo"));
		String id=liveExamineInfoService.add(liveExamineInfo);
		Map<String,Object> result=new HashMap<>();
		result.put("examineId",id);
		return ResponseObject.newSuccessResponseObject(result);
	}

	@RequestMapping("myList")
	@ResponseBody
	public ResponseObject examineList(HttpServletRequest req,
									  HttpServletResponse res)
			throws Exception {

		int pageNumber = 0;
		if(null != req.getParameter("pageNumber")){
			pageNumber = Integer.valueOf(req.getParameter("pageNumber"));
		}
		int pageSize = 10;
		if(null != req.getParameter("pageSize")){
			pageSize = Integer.valueOf(req.getParameter("pageSize"));
		}
		return ResponseObject.newSuccessResponseObject(liveExamineInfoService.liseByExamineStatus(req.getParameter("userId"),req.getParameter("type"),pageNumber,pageSize));
	}

	/**
	 * 审核详情页
	 * @param req
	 * @param res
	 * @param examineId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("examineDetails")
	@ResponseBody
	public ResponseObject examineDetails(HttpServletRequest req,
									  HttpServletResponse res,String examineId)
			throws Exception {
		return ResponseObject.newSuccessResponseObject(liveExamineInfoService.examineDetails(examineId));
	}

	/**
	 * 申诉
	 * @param req
	 * @param res
	 * @param examineId
	 * @param content
	 * @return
	 * @throws Exception
	 */
	@Transactional
	@RequestMapping("appeal")
	@ResponseBody
	public ResponseObject appeal(HttpServletRequest req,
										 HttpServletResponse res,String examineId,String content)
			throws Exception {
		liveExamineInfoService.appeal(examineId,content);
		Map<String,Object> result=new HashMap<>();
		result.put("examineId",examineId);
		return ResponseObject.newSuccessResponseObject(result);
	}

	
	/**
	 * 取消审核
	 * Description：
	 * @param id
	 * @return
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("/cancelAudit")
	@ResponseBody
	public ResponseObject cancelAudit(HttpServletRequest req,
			 HttpServletResponse res,String examineId){
		try {
			liveExamineInfoService.cancelAudit(examineId);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("网络异常,请稍等");
		}
		return ResponseObject.newSuccessResponseObject("取消成功");
	}
	
	
	
	@RequestMapping("selectCurrentLive")
	@ResponseBody
	public ResponseObject selectCurrentLive(HttpServletRequest req,
			 HttpServletResponse res, Map<String, String> params)
			throws Exception {
		return ResponseObject.newSuccessResponseObject(liveExamineInfoService.liseByExamineStatus(req.getParameter("userId"),"2",1,10).get(0));
	}

	public static Object convertMap(Class type, Map map)
			throws IntrospectionException, IllegalAccessException,
			InstantiationException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
		Object obj = type.newInstance(); // 创建 JavaBean 对象
		// 给 JavaBean 对象的属性赋值
		PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
		for (int i = 0; i< propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (map.containsKey(propertyName)) {
				// 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
				try {
				Object value = map.get(propertyName);
				Object[] args = new Object[1];
				args[0] = value;
				descriptor.getWriteMethod().invoke(obj, args);
				}catch (Exception e){
				}
			}
		}
		return obj;
	}

	@RequestMapping("/getMoneySum")
	@ResponseBody
	public ResponseObject getLiveMoneySum(String id){
		String moneySum=onlineCourseService.sumMoneyLive(id);
		if(moneySum==null){
			moneySum="0";
		}
		return ResponseObject.newSuccessResponseObject(moneySum);
	}

	@RequestMapping("/getPreLiveCount")
	@ResponseBody
	public ResponseObject getPreLiveCount(String userId){
		if(userId == null){
			return ResponseObject.newSuccessResponseObject(0);
		}
		return ResponseObject.newSuccessResponseObject(liveExamineInfoService.getPreLiveCount(userId));
	}

	@RequestMapping("/liveIsAvailable")
	@ResponseBody
	public ResponseObject liveIsAvailable(Integer id){
		try {
			CourseLecturVo courseVo =onlineCourseService.get(id);
			if(courseVo==null){
				return ResponseObject.newErrorResponseObject("该直播已被删除");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ResponseObject.newSuccessResponseObject(null);
	}
	
	
	/*****************************************
	 * 		新版app关于学堂的接口   -- 直播接口
	 * **************************************
	 */
	/**
	 * 推荐中 上不包含的信息
	 */
	@RequestMapping("onlineLive")
	@ResponseBody
	public ResponseObject onlineLive(HttpServletRequest req,
										   HttpServletResponse res, Integer id)
			throws Exception {
		
		Map<String, Object> mapAll = new HashMap<String, Object>();
		//课程banner
		List<Map<String, Object>> listTj = new ArrayList<Map<String, Object>>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		map1.put("tid", "1");
		map1.put("imgUrl", "http://attachment-center.ixincheng.com:38080/data/picture/online/2017/11/20/16/635c0d0086bb4260878588df27ac833a.jpg");
		map1.put("linkUrl", "http://attachment-center.ixincheng.com:38080/data/picture/online/2018/01/02/14/915ddfe29efa467e8a3726598d83c429.jpg");
		map1.put("linkType", "1"); //活动页、专题页、课程、主播、课程列表（带筛选条件）；
		
		
		map2.put("tid", "2");
		map2.put("imgUrl", "http://attachment-center.ixincheng.com:38080/data/picture/online/2018/01/02/14/915ddfe29efa467e8a3726598d83c429.jpg");
		map2.put("linkUrl", "http://attachment-center.ixincheng.com:38080/data/picture/online/2018/01/02/14/915ddfe29efa467e8a3726598d83c429.jpg");
		map2.put("linkType", "1"); //活动页、专题页、课程、主播、课程列表（带筛选条件）；
		
		listTj.add(map1);
		listTj.add(map2);
		
		mapAll.put("banner", listTj);
		
		//城市  城市中的课程
	    List<Map<String,Object>> mapCourseList = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> mapTj = new HashMap<String, Object>();
		Map<String,Object> mapNw = new HashMap<String, Object>();
		Map<String,Object> mapZz = new HashMap<String, Object>();
		Map<String,Object> mapHf = new HashMap<String, Object>();
		
		
		List<CourseLecturVo> list = onlineCourseService.findLiveListInfo(1,4,null);
		
		mapTj.put("title","正在直播");
		mapTj.put("courseList",list);
		
		mapNw.put("title","即将直播");
		mapNw.put("courseList",list);
		
		mapZz.put("title","直播课程");
		mapZz.put("courseList",list);
		
		mapHf.put("title","精彩直播回放");
		mapHf.put("courseList",list);
		
		mapCourseList.add(mapTj);
		mapCourseList.add(mapNw);
		mapCourseList.add(mapZz);
		mapCourseList.add(mapHf);
		mapAll.put("allCourseList",mapCourseList);
		
		return ResponseObject.newSuccessResponseObject(mapAll);
	}
	
	
	
	
	
	
	
}
