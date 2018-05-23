package com.xczhihui.bxg.online.web.controller.school;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.aspectj.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.controller.ftl.AbstractFtlController;
import com.xczhihui.bxg.online.web.utils.ftl.ReplaceUrl;
import com.xczhihui.common.util.enums.BannerType;
import com.xczhihui.common.util.enums.CourseType;
import com.xczhihui.common.util.enums.LiveStatus;
import com.xczhihui.common.util.enums.PagingFixedType;
import com.xczhihui.common.util.enums.PayStatus;
import com.xczhihui.common.util.enums.ProjectType;
import com.xczhihui.common.util.enums.SearchType;
import com.xczhihui.course.model.OfflineCity;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.service.ICriticizeService;
import com.xczhihui.course.service.IFocusService;
import com.xczhihui.course.service.IMobileBannerService;
import com.xczhihui.course.service.IMobileHotSearchService;
import com.xczhihui.course.service.IMobileProjectService;
import com.xczhihui.course.service.IMyInfoService;
import com.xczhihui.course.service.IOfflineCityService;
import com.xczhihui.course.util.CourseUtil;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.course.vo.MenuVo;
import com.xczhihui.course.vo.QueryConditionVo;
import com.xczhihui.medical.hospital.model.MedicalHospital;
import com.xczhihui.medical.hospital.service.IMedicalHospitalApplyService;


/**
 * Description：主播信息页面 
 * @author name：yuxin <br>
 * 		email: yuruixin@ixincheng.com
 * @Date: 2018/3/28 0028 下午 4:50
 **/
@Controller
@RequestMapping(value = "/anchors")
public class AnchorsController extends AbstractFtlController {


	@Autowired
	private IMyInfoService myInfoService;

	@Autowired
	@Qualifier("focusServiceRemote")
	private IFocusService focusServiceRemote;

	@Autowired
	private ICourseService courseService;
	
	@Autowired
	private ICriticizeService criticizeService;
	
	@Autowired
	private IMedicalHospitalApplyService medicalHospitalApplyService;
	
	
	
	@Value("${web.url}")
	private String  webUrl;

	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 课程详情页面中的 推荐课程
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "{userId}/info", method = RequestMethod.GET)
	public ModelAndView info(HttpServletRequest req,HttpServletResponse res,
			@PathVariable String userId) throws IOException {
		
		ModelAndView view = new ModelAndView("school/anchor_details");
		
		//显示详情呢、大纲、评论、常见问题呢
		view.addObject("type","info");
		view.addObject("userId",userId);
		view.addObject("webUrlParam",webUrl+"/"+userId);
		
		
		view = getHostBaseInfo(view,userId);
	    
	    //推荐课程   -- 从推荐值最高的课程里面查询啦啦啦啦。
  		view.addObject("recommendCourse",getRecommendCourse());
	    
		return view;
	}
	
	
	/**
	 * 课程详情页面中的 推荐课程
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "{userId}/courses", method = RequestMethod.GET)
	public ModelAndView courses(HttpServletRequest req,HttpServletResponse res,
			@PathVariable String userId,
			@RequestParam(required=false)Integer pageSize,
			@RequestParam(required=false)Integer pageNumber) throws IOException {
		
		
		pageNumber = pageNumber == null ? 1 : pageNumber;
		pageSize = pageSize == null ? 2 : pageSize;
		
		ModelAndView view = new ModelAndView("school/anchor_details");
		//显示详情呢、大纲、评论、常见问题呢
		view.addObject("type","courses");
		view.addObject("userId",userId);
		view.addObject("webUrlParam",webUrl+"/"+userId);
	    /**
		 * 这个主播可能认证的是医馆，也可能认证的是医师
		 */
		view = getHostBaseInfo(view,userId);
	    
	    //查找主播课程
	    Page<CourseLecturVo> page = new Page<>();
	    page.setCurrent(pageNumber);
	    page.setSize(pageSize);
		Page<CourseLecturVo> list = courseService.selectLecturerAllCourse(page,userId);
		view.addObject("courseList",list);
		
		//推荐课程   -- 从推荐值最高的课程里面查询啦啦啦啦。
		view.addObject("recommendCourse",getRecommendCourse());
		
		return view;
	}
	
	
	/**
	 * 课程详情页面中的 推荐课程
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "{userId}/comment ", method = RequestMethod.GET)
	public ModelAndView comment(HttpServletRequest req,HttpServletResponse res,
			@PathVariable String userId,
			@PathVariable String type,
			@RequestParam(required=false)Integer pageSize,
			@RequestParam(required=false)Integer pageNumber) throws IOException {
		
		
		pageNumber = pageNumber == null ? 1 : pageNumber;
		pageSize = pageSize == null ? 2 : pageSize;
		
		ModelAndView view = new ModelAndView("school/anchor_details");
		
		//显示详情呢、大纲、评论、常见问题呢
		view.addObject("type","comment");
		view.addObject("userId",userId);
		view.addObject("webUrlParam",webUrl+"/"+userId);
		
		/**
		 * 这个主播可能认证的是医馆，也可能认证的是医师
		 */
		view = getHostBaseInfo(view,userId);
		
		//获取用户信息
	    OnlineUser user = getCurrentUser();
		//课程评价
		Map<String,Object> map =  criticizeService.getAnchorCriticizes(new Page<>(pageNumber,pageSize),userId,user!= null ? user.getId() :null);
		view.addObject("criticizesMap",map);
		//查询各种平均值
		List<Integer> listComment = criticizeService.selectPcCUserCommentMeanCount(userId);
		view.addObject("listCommentCount",listComment);
		
		//推荐课程   -- 从推荐值最高的课程里面查询啦啦啦啦。
		view.addObject("recommendCourse",getRecommendCourse());
		
		return view;
	}
	
	/*
	 * 获取推荐课程
	 */
	private List<CourseLecturVo>  getRecommendCourse(){
		//推荐课程   -- 从推荐值最高的课程里面查询啦啦啦啦。
		Page<CourseLecturVo> pageRecommend = new Page<CourseLecturVo>();
		pageRecommend.setCurrent(0);
		pageRecommend.setSize(3);
		return courseService.selectRecommendSortAndRandCourse(pageRecommend);
	}
	
	/*
	 * 获取主播基本信息啦
	 */
	private ModelAndView  getHostBaseInfo(ModelAndView view,String userId){
		//推荐课程   -- 从推荐值最高的课程里面查询啦啦啦啦。
		 /**
		 * 这个主播可能认证的是医馆，也可能认证的是医师
		 */
	    Map<String,Object> lecturerInfo = myInfoService.findHostInfoById(userId);
	    view.addObject("lecturerInfo", lecturerInfo);
	    
	    MedicalHospital mha = null;
		//1.医师2.医馆
		if(lecturerInfo.get("type").toString().equals("1")){
			 mha = 	medicalHospitalApplyService.getMedicalHospitalByMiddleUserId(userId);	
		}else if(lecturerInfo.get("type").toString().equals("2")){
			 mha =  medicalHospitalApplyService.getMedicalHospitalByUserId(userId);	
		}
		//认证的主播 还是 医馆
		view.addObject("hospital",mha);
	    
	    
	    List<Integer> listff =   focusServiceRemote.selectFocusAndFansCountAndCriticizeCount(userId);
	    view.addObject("fansCount", listff.get(0));       	   //粉丝总数
	    view.addObject("focusCount", listff.get(1));   	  	   //关注总数
	    view.addObject("criticizeCount", listff.get(2));   	   //总数评论总数
	    
	    OnlineUser user = getCurrentUser();
	    if(user==null){
	    	view.addObject("isFours", 0); 
	    }else{
	    	Integer isFours  = focusServiceRemote.isFoursLecturer(user.getId(),userId);
	    	view.addObject("isFours", isFours); 
	    }
	    return view;
	}
	
	
}
