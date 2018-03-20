package com.xczh.consumer.market.controller.school;

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
import com.xczhihui.wechat.course.service.IMyInfoService;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
 * Create by: name：wangyishuai <br>
 * email: 15210815880@163.com <br>
 * Create Time: 2018年1月16日<br>
 */
@Controller
@RequestMapping("/xczh/recommend")
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
	
	@Autowired
	private IMyInfoService myInfoService;

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MobileRecommendController.class);
	


	/**
	 * 推荐  包含的信息:banner  ,推荐导航, 名师推荐
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
		mapAll.put("banner",mobileBannerService.selectMobileBannerPage(MobileBannerPage,bannerType));

		//课程专题

		Page<MobileProject> MobileProjectPage = new Page<>();
		MobileProjectPage.setCurrent(current);
		MobileProjectPage.setSize(size);
		int projectType = 1;
		mapAll.put("project",mobileProjectService.selectMobileProjectPage(MobileProjectPage,projectType));
		
		//名师推荐 名师推荐,没有按照排序做，或者按照这个讲师的课程数来排序呗
//		Page<MedicalDoctorVO> page = new Page<>();
//	    page.setCurrent(1);
//	    page.setSize(10);
	    
	    mapAll.put("doctorList",myInfoService.hostInfoRec());
		
		return ResponseObject.newSuccessResponseObject(mapAll);
	}
	
	 
	/**
	 * 推荐      包含的下面的课程 :精品课程, 最新课程, 分类课程
	 */
	@RequestMapping("recommendCourse")
	@ResponseBody
	public ResponseObject recommendBunch(HttpServletRequest req,
										   HttpServletResponse res)
			throws Exception {
		
		/**
		 * 精品课程 按照推荐值来排序。
		 * 最新课程 课程的时间排序
		 * 针灸课程
		 * 古书经典
		 */
	    List<MenuVo> listmv = menuService.list();
	    
		List<CourseLecturVo> listAll =wxcpCourseService.recommendCourseList(listmv);
		
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

		if(listTj.size()>0){
			mapTj.put("menuType","goodCourse");
			mapTj.put("title","精品课程");
			mapTj.put("courseList",listTj);
			mapCourseList.add(mapTj);
		}
		if(listNw.size()>0){
			mapNw.put("menuType","newCourse");
			mapNw.put("title","最新课程");
			mapNw.put("courseList",listNw);
			mapCourseList.add(mapNw);
		}


		//定义好这
		for (MenuVo menuVo : listmv) {
			Map<String,Object> mapMenu = new HashMap<String, Object>();
			List<CourseLecturVo> listMenu = new ArrayList<CourseLecturVo>();
			for (CourseLecturVo courseLecturVo : listAll) {
				if(menuVo.getName().equals(courseLecturVo.getNote())){
					listMenu.add(courseLecturVo);
				}
			}
			if(listMenu.size()>0){
				mapMenu.put("menuType", menuVo.getId());
				mapMenu.put("title", menuVo.getName());
				mapMenu.put("courseList", listMenu);
				mapCourseList.add(mapMenu);
			}

		}
		return ResponseObject.newSuccessResponseObject(mapCourseList);
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
	 * 是否付费
	 * 类型
	 * 城市
	 */
	@RequestMapping("queryAllCourse")
	@ResponseBody
	public ResponseObject queryAllCourse(String menuType,Integer lineState,Integer courseType,String city,String isFree,String queryKey,
			Integer pageNumber, Integer pageSize)
			throws Exception {

		List<CourseLecturVo> list = wxcpCourseService.queryAllCourse(menuType,lineState,courseType,isFree,city,queryKey,pageNumber,pageSize);
		
		return ResponseObject.newSuccessResponseObject(list);
	}

	/**
	 * banner点击量
	 */
	@RequestMapping("clickBanner")
	@ResponseBody
	public ResponseObject clickBanner(HttpServletRequest req,
										HttpServletResponse res,@RequestParam("id")String id)
			throws Exception {

		mobileBannerService.addClickNum(id);

		return ResponseObject.newSuccessResponseObject("点击量+1");
	}
}
