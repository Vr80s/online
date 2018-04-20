package com.xczhihui.course.web;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.Lecturer;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.course.service.CourseService;
import com.xczhihui.course.service.PublicCourseService;
import com.xczhihui.course.vo.ChangeCallbackVo;
import com.xczhihui.course.vo.CourseVo;
import com.xczhihui.user.service.OnlineUserService;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;
import com.xczhihui.vhall.VhallUtil;

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

	
	@RequestMapping(value = "index")
	public String index(HttpServletRequest request) {
		List<Menu> menuVos= publicCourseService.getMenus();
		request.setAttribute("menuVo", menuVos);
		
		//查找所有的讲师
		List<Map<String, Object>> mapList = onlineUserService.getAllUserLecturer();
		for (Map<String, Object> map : mapList) {
			String str = "昵称:"+map.get("name").toString() + ",帐号:"+map.get("logo").toString();
			map.put("name", str);
		}
		request.setAttribute("mapList", mapList);
		return PUBLIC_CLASS_PATH_PREFIX + "/publicCourse";
	
	}
	
	//@RequiresPermissions("course:menu：publicClass")
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
	
	//@RequiresPermissions("course:menu：publicClass")
	@RequestMapping(value = "getTeacher")
	@ResponseBody
	public List<Lecturer> getTeacher(Integer menuId){
		return publicCourseService.getTeacher(menuId);
	}

	
	/**
	 * 编辑
	 * @param vo
	 * @return
	 */
	//@RequiresPermissions("course:menu：publicClass")
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
