package com.xczhihui.cloudClass.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xczhihui.bxg.common.support.service.AttachmentCenterService;
import com.xczhihui.bxg.common.web.controller.AbstractController;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.common.domain.ScoreType;
import com.xczhihui.bxg.online.common.domain.TeachMethod;
import com.xczhihui.course.service.CourseService;
import com.xczhihui.course.vo.LecturerVo;
import com.xczhihui.user.service.OnlineUserService;

/**
 * 课程管理控制层实现类
 * 
 * @author yxd
 */

@Controller
@RequestMapping("cloudclass/course-jfyl")
public class CourseJfylController extends AbstractController {
	protected final static String CLOUD_CLASS_PATH_PREFIX = "/cloudClass/";
	@Autowired
	private CourseService courseService;
	@Autowired
	private AttachmentCenterService att;
	@Value("${online.web.url:http://www.ixincheng.com}")
	private String weburl;

	@Autowired
	private OnlineUserService onlineUserService;

	@RequestMapping(value = "index")
	public String index(HttpServletRequest request) {

		List<Menu> menuVos = courseService.getfirstMenus(3);
		request.setAttribute("menuVo", menuVos);

		// 在列表初始化时查找出课程类别
		List<ScoreType> scoreTypeVos = courseService.getScoreType();
		request.setAttribute("scoreTypeVo", scoreTypeVos);

		// 在列表初始化时查找出授课方式
		List<TeachMethod> teachMethodVos = courseService.getTeachMethod();
		request.setAttribute("teachMethodVo", teachMethodVos);

		List<LecturerVo> lecturers = courseService.getLecturers();
		request.setAttribute("lecturerVo", lecturers);
		request.setAttribute("type", 3);

		List<Map<String, Object>> mapList = onlineUserService
				.getAllUserLecturer();
		for (Map<String, Object> map : mapList) {
			String str = "昵称:" + map.get("name").toString() + ",帐号:"
					+ map.get("logo").toString();
			map.put("name", str);
		}
		request.setAttribute("mapList", mapList);

		return CLOUD_CLASS_PATH_PREFIX + "/course";
	}

}
