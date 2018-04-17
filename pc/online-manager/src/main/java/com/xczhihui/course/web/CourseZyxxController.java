package com.xczhihui.course.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xczhihui.course.service.CourseService;
import com.xczhihui.user.service.OnlineUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xczhihui.bxg.common.web.controller.AbstractController;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.common.domain.ScoreType;

/**
 * 课程管理控制层实现类
 * @author yxd
 */

@Controller
@RequestMapping("cloudclass/course-zyxx")
public class CourseZyxxController extends AbstractController{
	protected final static String CLOUD_CLASS_PATH_PREFIX = "/cloudClass/";
	@Autowired
	private CourseService courseService;
	@Value("${online.web.url}")
	private String weburl;
	
	@Autowired
	private OnlineUserService onlineUserService;
	
	@RequestMapping(value = "index")
	public String index(HttpServletRequest request) {
		
		List<Menu> menuVos= courseService.getfirstMenus(1);
		request.setAttribute("menuVo", menuVos);
		
		//在列表初始化时查找出课程类别
		List<ScoreType> scoreTypeVos = courseService.getScoreType();
		request.setAttribute("scoreTypeVo", scoreTypeVos);

		
		
		//查找所有的讲师
		List<Map<String, Object>> mapList = onlineUserService.getAllUserLecturer();
		for (Map<String, Object> map : mapList) {
			String str = "昵称:"+map.get("name").toString() + ",帐号:"+map.get("logo").toString();
			map.put("name", str);
		}
		request.setAttribute("mapList", mapList);
		
		return CLOUD_CLASS_PATH_PREFIX + "/course";
	}
	
}
