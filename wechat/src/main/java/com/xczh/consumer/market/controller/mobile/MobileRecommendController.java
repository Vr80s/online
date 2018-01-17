package com.xczh.consumer.market.controller.mobile;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.service.MenuService;
import com.xczh.consumer.market.service.OLCourseServiceI;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.vo.CourseLecturVo;
import com.xczh.consumer.market.vo.MenuVo;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.doctor.vo.MedicalDoctorVO;
import com.xczhihui.wechat.course.model.MobileBanner;
import com.xczhihui.wechat.course.model.MobileProject;
import com.xczhihui.wechat.course.service.IMobileBannerService;
import com.xczhihui.wechat.course.service.IMobileProjectService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 推荐控制器 ClassName: MobileRecommendController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>
 * email: 15936216273@163.com <br>
 * Create Time: 2017年8月11日<br>
 */
@Controller
@RequestMapping("/wechat/recommend")
public class MobileRecommendController {

	@Autowired
	private OLCourseServiceI wxcpCourseService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private IMedicalDoctorBusinessService medicalDoctorBusinessService;

	@Autowired
	private IMobileBannerService mobileBannerService;

	@Autowired
	private IMobileProjectService mobileProjectService;

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MobileRecommendController.class);
	


	/**
	 * 推荐中 上不包含的信息
	 */
	@RequestMapping("recommendTop")
	@ResponseBody
	public ResponseObject recommendTop() throws Exception {
		Integer current = 1;
		Integer size = 100;
		Map<String, Object> mapAll = new HashMap<String, Object>();
		//课程banner
		Page<MobileBanner> MobileBannerPage = new Page<>();
		MobileBannerPage.setCurrent(current);
		MobileBannerPage.setSize(size);
		int bannerType = 1;
		mapAll.put("bannerList",mobileBannerService.selectMobileBannerPage(MobileBannerPage,bannerType));

		//课程专题

		Page<MobileProject> MobileProjectPage = new Page<>();
		MobileProjectPage.setCurrent(current);
		MobileProjectPage.setSize(size);
		int projectType = 1;
		mapAll.put("projectList",mobileProjectService.selectMobileProjectPage(MobileProjectPage,projectType));
		
		//名师推荐 名师推荐,没有按照排序做，或者按照这个讲师的课程数来排序呗
		Page<MedicalDoctorVO> page = new Page<>();
	    page.setCurrent(1);
	    page.setSize(7);
	    
	    mapAll.put("doctorList",medicalDoctorBusinessService.selectRecDoctor());
		
		return ResponseObject.newSuccessResponseObject(mapAll);
	}
	
	 
	/**
	 * 推荐中包含的下面的课程
	 */
	@RequestMapping("recommendCourse")
	@ResponseBody
	public ResponseObject recommendBunch(HttpServletRequest req,
										   HttpServletResponse res, Integer id)
			throws Exception {
		
		/**
		 * 精品课程 按照推荐值来排序。
		 * 最新课程 课程的时间排序
		 * 针灸课程
		 * 古书经典
		 */
	    List<MenuVo> listmv = menuService.list();
	    
		List<CourseLecturVo> listAll =wxcpCourseService.recommendCourseList(0,4,null,listmv);
		
		LOGGER.info(listAll.size()+"");
		
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

		mapTj.put("menuType","goodCourse");
		mapTj.put("title","精品课程");
		mapTj.put("courseList",listTj);

		mapNw.put("menuType","newCourse");
		mapNw.put("title","最新课程");
		mapNw.put("courseList",listNw);
		
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
			mapMenu.put("menuType", menuVo.getId());
			mapMenu.put("title", menuVo.getName());
			mapMenu.put("courseList", listMenu);
			mapCourseList.add(mapMenu);
		}
		return ResponseObject.newSuccessResponseObject(mapCourseList);
	}

	/**
	 * 推荐中 上不包含的信息
	 */
	@RequestMapping("courseList")
	@ResponseBody
	public ResponseObject courseList(Integer current,Integer size) throws Exception {
		Map<String, Object> mapAll = new HashMap<String, Object>();
		//课程banner
		Page<MobileBanner> MobileBannerPage = new Page<>();
		MobileBannerPage.setCurrent(current);
		MobileBannerPage.setSize(size);
		int bannerType = 1;
		mapAll.put("bannerList",mobileBannerService.selectMobileBannerPage(MobileBannerPage,bannerType));


		return ResponseObject.newSuccessResponseObject(mapAll);
	}
	
	
	/*****************************************
	 * 
	 * 
	 * 		新版app关于学堂的接口   -- 线下培训班接口
	 * 
	 * **************************************
	 */
	
	/**
	 * 推荐中 上不包含的信息
	 */
	@RequestMapping("offLine")
	@ResponseBody
	public ResponseObject offLine(HttpServletRequest req,
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
		
		//城市
	    
		List<Map<String, Object>>  listNw  = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> m1 = new HashMap<String, Object>();
		m1.put("cId", "1");
		m1.put("title", "北京");
		m1.put("icon", "http://47.92.138.228:88/images/upload/2017-12-20/3bd6f786-ce14-46e3-a3c8-9b36a5ffcf03.png");
		Map<String, Object> m2 = new HashMap<String, Object>();
		m2.put("cId", "2");
		m2.put("title", "上海");
		m2.put("icon", "http://47.92.138.228:88/images/upload/2017-12-20/3bd6f786-ce14-46e3-a3c8-9b36a5ffcf03.png");
		Map<String, Object> m3 = new HashMap<String, Object>();
		m3.put("cId", "3");
		m3.put("title", "广州");
		m3.put("icon", "http://47.92.138.228:88/images/upload/2017-12-20/3bd6f786-ce14-46e3-a3c8-9b36a5ffcf03.png");
		Map<String, Object> m4 = new HashMap<String, Object>();
		m4.put("cId", "4");
		m4.put("title", "成都");
		m4.put("icon", "http://47.92.138.228:88/images/upload/2017-12-20/3bd6f786-ce14-46e3-a3c8-9b36a5ffcf03.png");
		
		Map<String, Object> m5 = new HashMap<String, Object>();
		m5.put("cId", "4");
		m5.put("title", "郑州");
		m5.put("icon", "http://47.92.138.228:88/images/upload/2017-12-20/3bd6f786-ce14-46e3-a3c8-9b36a5ffcf03.png");
		
		listNw.add(m1);
		listNw.add(m2);
		listNw.add(m3);
		listNw.add(m4);
		listNw.add(m5);
		mapAll.put("cityList", listNw);
		
	
		//城市  城市中的课程
	    List<Map<String,Object>> mapCourseList = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> mapTj = new HashMap<String, Object>();
		Map<String,Object> mapNw = new HashMap<String, Object>();
		Map<String,Object> mapZz = new HashMap<String, Object>();
		
		
		List<CourseLecturVo> list = wxcpCourseService.offLineClassList(1,5);
		
		mapTj.put("title","全国城市");
		mapTj.put("courseList",list);
		
		mapNw.put("title","上海城市");
		mapNw.put("courseList",list);
		
		mapZz.put("title","郑州城市");
		mapZz.put("courseList",list);
		
		mapCourseList.add(mapTj);
		mapCourseList.add(mapNw);
		mapCourseList.add(mapZz);
		mapAll.put("allCourseList",mapCourseList);
		
		return ResponseObject.newSuccessResponseObject(mapAll);
	}
	
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
	public ResponseObject queryAllCourse(String menuType,Integer courseType,String city,String isFree,String queryKey,
			Integer pageNumber, Integer pageSize)
			throws Exception {

		List<CourseLecturVo> list = wxcpCourseService.queryAllCourse(menuType,courseType,isFree,city,queryKey,pageNumber,pageSize);
		
		return ResponseObject.newSuccessResponseObject(list);
	}
	
	
	/*****************************************
	 * 		新版app关于学堂的接口   -- 直播接口
	 * **************************************
	 */
	/**
	 * 推荐中 上不包含的信息
	 */
	@RequestMapping("listenCourse")
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
		List<CourseLecturVo> list = wxcpCourseService.offLineClassList(1,5);
		
		mapTj.put("title","听课推荐");
		mapTj.put("courseList",list);
		mapCourseList.add(mapTj);
		mapAll.put("allCourseList",mapCourseList);
		
		return ResponseObject.newSuccessResponseObject(mapAll);
	}
	
}
