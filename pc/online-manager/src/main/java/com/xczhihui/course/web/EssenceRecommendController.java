package com.xczhihui.course.web;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.course.service.CourseService;
import com.xczhihui.course.service.EssenceRecommendService;
import com.xczhihui.course.vo.CourseVo;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 课程推荐列表管理控制层实现类
 * @author yxd
 */

@Controller
@RequestMapping("essencerecommend/course")
public class EssenceRecommendController {

	protected final static String PUBLIC_CLASS_PATH_PREFIX = "/cloudClass/";
	@Autowired
	private CourseService courseService;

	@Autowired
	private EssenceRecommendService ssenceRecommenedService;
	
	@RequestMapping(value = "index")
	public String index(HttpServletRequest request) {

		List<Menu> menuVos= courseService.getfirstMenus(null);
		request.setAttribute("menuVo", menuVos);
		
		return PUBLIC_CLASS_PATH_PREFIX + "/essenceRcommend";
	
	}
	
	//@RequiresPermissions("course:menu:essenceRecommend")
	@RequestMapping(value = "list")
	@ResponseBody
	public TableVo courses(TableVo tableVo) {
		int pageSize = tableVo.getiDisplayLength();
		int index = tableVo.getiDisplayStart();
		int currentPage = index / pageSize + 1;
		String params = tableVo.getsSearch();
		Groups groups = Tools.filterGroup(params);

        CourseVo searchVo=new CourseVo();
        //课程名查找
        Group courseName = groups.findByName("search_courseName");
        if (courseName != null) {
      	  searchVo.setCourseName(courseName.getPropertyValue1().toString());
        }
        
        // 直播大类型
        Group course_type = groups.findByName("search_type");
        if (course_type!=null && StringUtils.isNotBlank(course_type.getPropertyValue1().toString())) {
      	   searchVo.setType(Integer.valueOf(course_type.getPropertyValue1().toString()));
        }
        
        // 直播状态 
        Group course_liveStatus = groups.findByName("search_liveStatus");
        if (course_liveStatus!=null && StringUtils.isNotBlank(course_liveStatus.getPropertyValue1().toString())) {
      	  searchVo.setLiveStatus(Integer.valueOf(course_liveStatus.getPropertyValue1().toString()));
        }
        
        // 媒体类型 
        Group course_multimediaType = groups.findByName("search_multimediaType");
        if (course_multimediaType!=null &&StringUtils.isNotBlank(course_multimediaType.getPropertyValue1().toString())) {
      	  searchVo.setMultimediaType(Integer.valueOf(course_multimediaType.getPropertyValue1().toString()));
        }
        
		//查询学科
        Group menuId = groups.findByName("search_menu");
        if (menuId != null) {
      	  searchVo.setMenuId(Integer.valueOf(menuId.getPropertyValue1().toString()));
        }
		Page<CourseVo> page = ssenceRecommenedService.findCoursePage(searchVo,currentPage, pageSize);
		int total = page.getTotalCount();
		tableVo.setAaData(page.getItems());
		tableVo.setiTotalDisplayRecords(total);
		tableVo.setiTotalRecords(total);
		return tableVo;
	}
}
