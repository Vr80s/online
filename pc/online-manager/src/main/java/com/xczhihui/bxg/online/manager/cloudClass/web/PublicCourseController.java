package com.xczhihui.bxg.online.manager.cloudClass.web;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.common.util.DateUtil;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.Lecturer;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.common.enums.CourseForm;
import com.xczhihui.bxg.online.manager.cloudClass.service.CourseService;
import com.xczhihui.bxg.online.manager.cloudClass.service.PublicCourseService;
import com.xczhihui.bxg.online.manager.cloudClass.vo.ChangeCallbackVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseVo;
import com.xczhihui.bxg.online.manager.user.service.OnlineUserService;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;
import com.xczhihui.bxg.online.manager.vhall.VhallUtil;

/**
 * 公开课管理控制层实现类
 * @author yxd
 */

@Controller
@RequestMapping("publiccloudclass/course")
public class PublicCourseController {
	protected final static String PUBLIC_CLASS_PATH_PREFIX = "/cloudClass/";
	@Autowired
	private PublicCourseService publicCourseService;
	@Autowired
	private CourseService courseService;

	@Autowired
	private OnlineUserService  onlineUserService;
	
	
	@Value("${online.web.publiccloud.courseType}")
	private String courseType;
	@Value("${online.web.publiccloud.courseTypeId}")
	private String courseTypeId;
	
	@RequestMapping(value = "index")
	public String index(HttpServletRequest request) {
		List<Menu> menuVos= publicCourseService.getMenus();
		request.setAttribute("menuVo", menuVos);
		
		//查找所有的讲师
		//OnlineUserService
		List<Map<String, Object>> mapList = onlineUserService.getAllUserLecturer();
		for (Map<String, Object> map : mapList) {
			String str = "昵称:"+map.get("name").toString() + ",帐号:"+map.get("logo").toString();
			map.put("name", str);
		}
		request.setAttribute("mapList", mapList);
		//在列表初始化时查找出课程类别
//		List<ScoreType> scoreTypeVos = courseService.getScoreType();
//		request.setAttribute("scoreTypeVo", scoreTypeVos);
		return PUBLIC_CLASS_PATH_PREFIX + "/publicCourse";
	
	}
	
	//@RequiresPermissions("cloudClass:menu：publicClass")
	@RequestMapping(value = "list")
	@ResponseBody
	public TableVo courses(TableVo tableVo) {
		int pageSize = tableVo.getiDisplayLength();
		int index = tableVo.getiDisplayStart();
		int currentPage = index / pageSize + 1;
		String params = tableVo.getsSearch();
		Groups groups = Tools.filterGroup(params);

		CourseVo searchVo = new CourseVo();
		Group courseName = groups.findByName("search_courseName");

		if (courseName != null) {
			searchVo.setCourseName(courseName.getPropertyValue1().toString());
		}

		Group menuId = groups.findByName("search_menu");
		if (menuId != null) {
			searchVo.setMenuId(Integer.valueOf(menuId.getPropertyValue1()
					.toString()));
		}
		
		Group lecturerName = groups.findByName("search_lecturerName");
		if (lecturerName != null) {
			searchVo.setLecturerName(lecturerName.getPropertyValue1().toString());
		}
		Group status = groups.findByName("search_status");
		if (status != null) {
			searchVo.setStatus(status.getPropertyValue1().toString());
		}
		Group liveStatus = groups.findByName("search_live_status");
		if (liveStatus != null) {
			searchVo.setLiveStatus(Integer.valueOf(liveStatus.getPropertyValue1().toString()));
		}
		Page<CourseVo> coursePage = publicCourseService.findCoursePage(searchVo,
				currentPage, pageSize);
		Page<CourseVo> page = coursePage;
		int total = page.getTotalCount();
		tableVo.setAaData(page.getItems());
		tableVo.setiTotalDisplayRecords(total);
		tableVo.setiTotalRecords(total);
		return tableVo;
		
	}
	
	//@RequiresPermissions("cloudClass:menu：publicClass")
	@RequestMapping(value = "getTeacher")
	@ResponseBody
	public List<Lecturer> getTeacher(Integer menuId){
		return publicCourseService.getTeacher(menuId);
	}

	
	/**
	 * 添加
	 * @param vo
	 * @return
	 */
	//@RequiresPermissions("cloudClass:menu：publicClass")
	@RequestMapping(value = "addCourse", method = RequestMethod.POST)
	@ResponseBody
	 public ResponseObject add(CourseVo courseVo){
		ResponseObject responseObj = new ResponseObject();
		try{
			if(courseVo.getCurrentPrice() == null){
				courseVo.setCurrentPrice(0.0);
			}
			courseVo.setType(CourseForm.LIVE.getCode());
			courseService.addCourse(courseVo);
            responseObj.setSuccess(true);
            responseObj.setErrorMessage("新增成功");
       }catch(Exception e){
    	   	e.printStackTrace();
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("新增失败");
       }
        return responseObj;
    }
	
	/**
	 * 编辑
	 * @param vo
	 * @return
	 */
	//@RequiresPermissions("cloudClass:menu：publicClass")
	@RequestMapping(value = "updateCourseById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateCourseById (CourseVo courseVo){
		ResponseObject responseObj = new ResponseObject();
		if(courseVo.getCurrentPrice() == null){
			courseVo.setCurrentPrice(0.0);
		}
		courseService.updateCourse(courseVo);
		responseObj.setSuccess(true);
		responseObj.setErrorMessage("修改成功");
		return responseObj;
	}
	
	
	
	//@RequiresPermissions("cloudClass:menu：publicClass")
	@RequestMapping(value = "coursesReclist")
	@ResponseBody
	public TableVo coursesRec(TableVo tableVo) {
		int pageSize = tableVo.getiDisplayLength();
		int index = tableVo.getiDisplayStart();
		int currentPage = index / pageSize + 1;
		String params = tableVo.getsSearch();
		Groups groups = Tools.filterGroup(params);

		CourseVo searchVo = new CourseVo();
		Group courseName = groups.findByName("search_courseName");

		if (courseName != null) {
			searchVo.setCourseName(courseName.getPropertyValue1().toString());
		}

		Group menuId = groups.findByName("search_menu");
		if (menuId != null) {
			searchVo.setMenuId(Integer.valueOf(menuId.getPropertyValue1()
					.toString()));
		}
		
		Group lecturerName = groups.findByName("search_lecturerName");
		if (lecturerName != null) {
			searchVo.setLecturerName(lecturerName.getPropertyValue1().toString());
		}
		
		Group status = groups.findByName("search_status");

		if (status != null) {
			searchVo.setStatus(status.getPropertyValue1().toString());
		}
		
		Group startTime = groups.findByName("search_startTime");

		if (startTime != null) {
			searchVo.setStartTime(DateUtil.parseDate(startTime.getPropertyValue1().toString(), "yyyy-MM-dd"));
		}
		Group endTime = groups.findByName("search_endTime");

		if (endTime != null) {
			searchVo.setEndTime(DateUtil.parseDate(endTime.getPropertyValue1().toString(), "yyyy-MM-dd"));
		}
		Group liveStatus = groups.findByName("search_liveStatus");

		if (liveStatus != null) {
			searchVo.setLiveStatus(Integer.valueOf(liveStatus.getPropertyValue1().toString()));
		}

		Page<CourseVo> page = publicCourseService.findCoursePage(searchVo,
				currentPage, pageSize);
		int total = page.getTotalCount();
		tableVo.setAaData(page.getItems());
		tableVo.setiTotalDisplayRecords(total);
		tableVo.setiTotalRecords(total);
		return tableVo;
		
	}
	
	
	/**
     * 上移
     * @param id
     * @return
     */
    @RequestMapping(value = "upMove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject upMove(Integer id) {
         ResponseObject responseObj = new ResponseObject();
         publicCourseService.updateSortUp(id);
         responseObj.setSuccess(true);
         return responseObj;
    }
    
	/**
     * 下移
     * @param id
     * @return
     */
    @RequestMapping(value = "downMove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject downMove(Integer id) {
         ResponseObject responseObj = new ResponseObject();
         publicCourseService.updateSortDown(id);
         responseObj.setSuccess(true);
         return responseObj;
    }
    
    
    /** 
     * Description：获取主播的直播地址
     * @param id
     * @return
     * @return ResponseObject
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     **/
    @RequestMapping(value = "getWebinarUrl", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject getWebinarUrl(String webinarId) {
    	ResponseObject responseObj = new ResponseObject();
    	String url = VhallUtil.getWebinarUrl(webinarId, null);
    	responseObj.setSuccess(true);
    	responseObj.setResultObject(url);
    	return responseObj;
    }
    
    /** 
     * Description：创建直播间（适用于平台直播间已创建，微吼直播间id创建失败或未插入活动id）
     * @param id
     * @return
     * @return ResponseObject
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     **/
    @RequestMapping(value = "reCreateWebinar", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject reCreateWebinar(Integer courseId) throws IllegalAccessException, InvocationTargetException {
    	ResponseObject responseObj = new ResponseObject();
    	Course course = courseService.getPublicCourseById(courseId);
    	String webinarId = publicCourseService.createWebinar(course);
    	course.setDirectId(webinarId);
    	publicCourseService.updateCourseDirectId(course);
    	responseObj.setSuccess(true);
    	responseObj.setResultObject("活动生成成功");
    	return responseObj;
    }
    
    /** 
     * Description：创建直播间（适用于平台直播间已创建，微吼直播间id创建失败或未插入活动id）
     * @param id
     * @return
     * @return ResponseObject
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     **/
    @RequestMapping(value = "changeCallback")
    @ResponseBody
    public ResponseObject changeCallback(ChangeCallbackVo changeCallbackVo) throws IllegalAccessException, InvocationTargetException {
    	ResponseObject responseObj = new ResponseObject();
    	publicCourseService.updateLiveStatus(changeCallbackVo);
    	responseObj.setSuccess(true);
    	responseObj.setResultObject(changeCallbackVo);
    	return responseObj;
    }
	
}
