package com.xczh.consumer.market.controller.live;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.FocusService;
import com.xczh.consumer.market.service.GiftService;
import com.xczh.consumer.market.service.MenuService;
import com.xczh.consumer.market.service.OLCourseServiceI;
import com.xczh.consumer.market.service.OnlineCourseService;
import com.xczh.consumer.market.service.OnlineWebService;
import com.xczh.consumer.market.utils.JdbcUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.vo.CourseLecturVo;
import com.xczh.consumer.market.vo.MenuVo;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.doctor.vo.MedicalDoctorVO;

/**
 * 点播控制器 ClassName: BunchPlanController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>
 * email: 15936216273@163.com <br>
 * Create Time: 2017年8月11日<br>
 */
@Controller
@RequestMapping("/bxg/bunch")
public class BunchPlanController {

	@Autowired
	private OLCourseServiceI wxcpCourseService;

	@Autowired
	private OnlineCourseService onlineCourseService;
	
	@Autowired
	private FocusService focusService;
	
	@Autowired
	private AppBrowserService appBrowserService;
	
	@Autowired
	private OnlineWebService onlineWebService;
	
	@Autowired
	private GiftService giftService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private IMedicalDoctorBusinessService medicalDoctorBusinessService;

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(BunchPlanController.class);
	
	@Value("${gift.im.room.postfix}")
	private String postfix;
	@Value("${gift.im.boshService}")
	private String boshService;//im服务地址
	@Value("${gift.im.host}")
	private  String host;

	// 新增的课程列表页
	@RequestMapping("categorylist")
	@ResponseBody
	public ResponseObject categoryXCList(HttpServletRequest req,HttpServletResponse res, Map<String, String> params)
			throws Exception {
		
		return ResponseObject.newSuccessResponseObject(wxcpCourseService.categoryXCList());
	}

	// 新增点播课程list
	@RequestMapping("list")
	@ResponseBody
	public ResponseObject courseXCList(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)
			throws Exception {
		//多媒体类型1视频2音频
		String menid = req.getParameter("menu_id");
		String s = req.getParameter("pageNumber");
		String e = req.getParameter("pageSize");
		log.info("pageNumber:"+s+"===========================pageSize:"+e);
		String multimedia_type = req.getParameter("multimedia_type");
		if ("".equals(menid) || menid == null || "null".equals(menid)) {
			return ResponseObject.newErrorResponseObject("分类id不能为空");
		}
		if ("".equals(multimedia_type) || multimedia_type == null || "null".equals(multimedia_type)) {
			return ResponseObject.newErrorResponseObject("媒体类型不能为空");
		}
		int number = 0;
		if (!"".equals(s) && s != null && !"null".equals(s)) {
			number = Integer.valueOf(s);
		}
		int pageSize = 0;
		if ("".equals(e) || e == null || "null".equals(e)) {
			pageSize = 6;
		} else {
			pageSize = Integer.valueOf(e);
		}
		List<CourseLecturVo> list = wxcpCourseService.courseXCListByCategory(menid,number, pageSize,Integer.parseInt(multimedia_type));
		log.info("list.size():"+list.size());
		return ResponseObject.newSuccessResponseObject(list);
	}
	/***
	 * 课程详细信息
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("detail")
	@ResponseBody
	@Transactional
	public ResponseObject courseDetail(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)
			throws Exception {
		String courseid = req.getParameter("course_id");
		if ("".equals(courseid) || courseid == null || "null".equals(courseid)) {
			return ResponseObject.newErrorResponseObject("课程ID是空的");
		}
		OnlineUser user = appBrowserService.getOnlineUserByReq(req, params);
		CourseLecturVo courseLecturVo =wxcpCourseService.bunchDetailsByCourseId(Integer.parseInt(courseid));
		
		if(courseLecturVo == null){
			return ResponseObject.newSuccessResponseObject("获取课程异常");
		}
		if(user != null ){
			Integer isFours  = focusService.myIsFourslecturer(user.getId(), courseLecturVo.getUserId());
			courseLecturVo.setIsfocus(isFours);
			if(courseLecturVo.getWatchState()!=0){
				
				if(courseLecturVo.getUserId().equals(user.getId()) ||
						onlineWebService.getLiveUserCourse(Integer.parseInt(courseid),user.getId()).size()>0){
			       //log.info("同学,当前课程您已经报名了!");
					
			       courseLecturVo.setWatchState(0);    
			    };
			}
		}
		/*
		 * 我的粉丝总数
		 */
		Integer countFans =	focusService.findMyFansCount(courseLecturVo.getUserId());
		courseLecturVo.setCountFans(countFans);
		/*
		 * 我的礼物总数 
		 */
		courseLecturVo.setCountGift(giftService.findByUserId(courseLecturVo.getUserId()));
	
		return ResponseObject.newSuccessResponseObject(courseLecturVo);
	}
	
	/**
	 * 线下培训班
	 */
	@RequestMapping("offLineClass")
	@ResponseBody
	public ResponseObject offLineClass(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)
			throws Exception {
		//多媒体类型1视频2音频
		String keyWord = req.getParameter("keyWord");
		String s = req.getParameter("pageNumber");
		String e = req.getParameter("pageSize");
		log.info("pageNumber:"+s+"===========================pageSize:"+e);
		int number = 0;
		if (!"".equals(s) && s != null && !"null".equals(s)) {
			number = Integer.valueOf(s);
		}
		int pageSize = 0;
		if ("".equals(e) || e == null || "null".equals(e)) {
			pageSize = 6;
		} else {
			pageSize = Integer.valueOf(e);
		}
		List<CourseLecturVo> list = wxcpCourseService.offLineClass(keyWord,number, pageSize);
		log.info("list.size():"+list.size());
		return ResponseObject.newSuccessResponseObject(list);
	}

	/**
	 * 线下培训班列表
	 */
	@RequestMapping("offLineClassList")
	@ResponseBody
	public ResponseObject offLineClassList(HttpServletRequest req,
									   HttpServletResponse res, Map<String, String> params)
			throws Exception {
		//多媒体类型1视频2音频
		String s = req.getParameter("pageNumber");
		String e = req.getParameter("pageSize");
		log.info("pageNumber:"+s+"===========================pageSize:"+e);
		int number = 1;
		if (!"".equals(s) && s != null && !"null".equals(s)) {
			number = Integer.valueOf(s);
		}
		int pageSize = 0;
		if ("".equals(e) || e == null || "null".equals(e)) {
			pageSize = 6;
		} else {
			pageSize = Integer.valueOf(e);
		}
		List<CourseLecturVo> list = wxcpCourseService.offLineClassList(number, pageSize);
		/**
		 * 循环把城市展示出来
		 */
		for (CourseLecturVo courseLecturVo : list) {
			String city = courseLecturVo.getAddress();
			String [] citys = city.split("-");
			courseLecturVo.setCity(citys[1]);
			/*
			 * 我感觉这里的发挥下后台的作用了
			 */
//			boolean falg = TimeUtil.dateCompare(courseLecturVo.getEndTime(),Calendar.getInstance(),1);
//			if(falg){
//				courseLecturVo.setCutoff(0);
//			}else{
//				courseLecturVo.setCutoff(1);
//			}
		}
		log.info("list.size():"+list.size());
		return ResponseObject.newSuccessResponseObject(list);
	}
	
	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
        /** 
         * 获取 年 ，月 ，日 
         */  
//        log.info(calendar.get(Calendar.YEAR));  
//        //默认从0-11  
//        log.info(calendar.get(Calendar.MONTH)+1);  
//        log.info(calendar.get(Calendar.DATE));  
		
	}

	/**
	 * 线下培训班详情
	 */
	@RequestMapping("offLineClassItem")
	@ResponseBody
	public ResponseObject offLineClassItem(HttpServletRequest req,
										   HttpServletResponse res, Integer id)
			throws Exception {

		
		String userId=req.getParameter("userId");
		CourseLecturVo courseLecturVo=wxcpCourseService.offLineClassItem(id,userId);
		
		if(userId!=null){
			OnlineUser onlineUser=new OnlineUser();
			onlineUser.setId(userId);
			ResponseObject resp = onlineCourseService.courseIsBuy(onlineUser,id);
			if(resp.isSuccess()){//已经付过费了
				courseLecturVo.setWatchState(0);
			}else{
				
			}
		}
		return ResponseObject.newSuccessResponseObject(courseLecturVo);
	}
	
	
	
	/*****************************************
	 * 
	 * 
	 * 	新版app关于学堂的接口
	 * 
	 * 
	 * **************************************
	 */
	
	/**
	 * 分类
	 */
	@RequestMapping("schoolClass")
	@ResponseBody
	public ResponseObject schoolClass(HttpServletRequest req,
										   HttpServletResponse res, Integer id)
			throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		//课程分类
		map.put("category", menuService.list());
		
		List<Map<String, Object>>  list  = new ArrayList<Map<String,Object>>();
		Map<String, Object> m1 = new HashMap<String, Object>();
		m1.put("code", "1");
		m1.put("code", "1");
		m1.put("title", "大师课");
		Map<String, Object> m2 = new HashMap<String, Object>();
		m1.put("code", "2");
		m2.put("2", "经典课");
		Map<String, Object> m3 = new HashMap<String, Object>();
		m1.put("code", "3");
		m3.put("3", "小白课程");
		Map<String, Object> m4 = new HashMap<String, Object>();
		m1.put("code", "4");
		m4.put("4", "免费课程");
		
		list.add(m1);
		list.add(m2);
		list.add(m3);
		list.add(m4);
				
		//课程专题   -- 假数据
		map.put("project", list);
		
		
		List<Map<String, Object>>  list1  = new ArrayList<Map<String,Object>>();
		Map<String, Object> m11 = new HashMap<String, Object>();
		m11.put("1", "视频课程");
		Map<String, Object> m21 = new HashMap<String, Object>();
		m21.put("2", "直播课程");
		Map<String, Object> m31 = new HashMap<String, Object>();
		m31.put("3", "音频课程");
		Map<String, Object> m41 = new HashMap<String, Object>();
		m41.put("4", "线下课程");
		
		list1.add(m11);
		list1.add(m21);
		list1.add(m31);
		list1.add(m41);
		
		//课程类型
		map.put("type", list1);
		return ResponseObject.newSuccessResponseObject(map);
	}
	

	/**
	 * 推荐中 上不包含的信息
	 */
	@RequestMapping("recommendTop")
	@ResponseBody
	public ResponseObject recommendTop(HttpServletRequest req,
										   HttpServletResponse res, Integer id)
			throws Exception {
		
		Map<String, Object> mapAll = new HashMap<String, Object>();
		//课程banner
		List<Map<String, Object>> listTj = new ArrayList<Map<String, Object>>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		map1.put("cid", "1");
		map1.put("imgUrl", "http://attachment-center.ixincheng.com:38080/data/picture/online/2017/11/20/16/635c0d0086bb4260878588df27ac833a.jpg");
		
		map2.put("cid", "2");
		map2.put("imgUrl", "http://attachment-center.ixincheng.com:38080/data/picture/online/2018/01/02/14/915ddfe29efa467e8a3726598d83c429.jpg");
		listTj.add(map1);
		listTj.add(map2);
		
		mapAll.put("banner", listTj);
		
		
		//课程专题   -- 假数据
	    
		List<Map<String, Object>>  listNw  = new ArrayList<Map<String,Object>>();
		Map<String, Object> m1 = new HashMap<String, Object>();
		m1.put("1", "大师课");
		Map<String, Object> m2 = new HashMap<String, Object>();
		m2.put("2", "经典课");
		Map<String, Object> m3 = new HashMap<String, Object>();
		m3.put("3", "小白课程");
		Map<String, Object> m4 = new HashMap<String, Object>();
		m4.put("4", "免费课程");
		
		listNw.add(m1);
		listNw.add(m2);
		listNw.add(m3);
		listNw.add(m4);
		mapAll.put("project", listNw);
		
		
		//名师推荐 名师推荐,没有按照排序做，或者按照这个讲师的课程数来排序呗
		
		
		Page<MedicalDoctorVO> page = new Page<>();
	    page.setCurrent(1);
	    page.setSize(5);
		
	    //map.put("doctorList",medicalDoctorBusinessService.selectDoctorPage(page,null,null,null,null));
		
		return ResponseObject.newSuccessResponseObject(mapAll);
	}
	
	 
	/**
	 * 推荐中包含的下面的课程
	 */
	@RequestMapping("recommendBunch")
	@ResponseBody
	public ResponseObject recommendBunch(HttpServletRequest req,
										   HttpServletResponse res, Integer id)
			throws Exception {
		
		/**
		 * 精品课程 按照购买人数来排序。
		 * 最新课程 课程的时间排序
		 * 针灸课程
		 * 古书经典
		 */
	    List<MenuVo> listmv = menuService.list();
		List<CourseLecturVo> listAll =wxcpCourseService.recommendCourseList(0,1,null,listmv);
		
		log.info(listAll.size()+"");
		
		List<Map<String,Object>> mapCourseList = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> mapTj = new HashMap<String, Object>();
		Map<String,Object> mapNw = new HashMap<String, Object>();
		List<CourseLecturVo> listTj = new ArrayList<CourseLecturVo>();
		List<CourseLecturVo> listNw = new ArrayList<CourseLecturVo>();
		
		for (CourseLecturVo courseLecturVo : listAll) {
			if("精品课程".equals(courseLecturVo.getNote())){
				listTj.add(courseLecturVo);
			}
			if("最新课程".equals(courseLecturVo.getNote())){
				listNw.add(courseLecturVo);
			}
		}
		mapTj.put("title","精品课程");
		mapTj.put("courseList",listTj);
		
		mapTj.put("title","最新课程");
		mapTj.put("courseList",listNw);
		
		mapCourseList.add(mapTj);
		mapCourseList.add(mapNw);
		//定义好这
		for (MenuVo menuVo : listmv) {
			Map<String,Object> mapMenu = new HashMap<String, Object>();
			List<CourseLecturVo> listMenu = new ArrayList<CourseLecturVo>();
			for (CourseLecturVo courseLecturVo : listAll) {
				if(menuVo.getName().equals(courseLecturVo.getNote())){
					listMenu.add(courseLecturVo);
				}
			}
			mapMenu.put("title", menuVo.getName());
			mapMenu.put("courseList", listMenu);
			mapCourseList.add(mapMenu);
		}
		return ResponseObject.newSuccessResponseObject(mapCourseList);
	}
	
	
	
	/*****************************************
	 * 
	 * 
	 * 		新版app关于学堂的接口   -- 线下培训班接口
	 * 
	 * **************************************
	 */
	
	
	
	
	/*****************************************
	 * 
	 * 		检索管理
	 * 
	 * **************************************
	 */

	
	/**
	 * 搜索所有的课程
	 * 可通过关键字  -- 关键字是全文匹配
	 * 上面哪个是
	 * 分类搜索
	 * 是否收费
	 * 类型
	 * 城市
	 */
	@RequestMapping("queryAllCourse")
	@ResponseBody
	public ResponseObject queryAllCourse(Integer menuType,String multimediaType,String city,String isFree,String queryKey,
			Integer pageNumber, Integer pageSize)
			throws Exception {

		List<CourseLecturVo> list = wxcpCourseService.queryAllCourse(menuType,multimediaType,isFree,city,queryKey,pageNumber,pageSize);
		
		
		return ResponseObject.newSuccessResponseObject(null);
	}
	
	
}
