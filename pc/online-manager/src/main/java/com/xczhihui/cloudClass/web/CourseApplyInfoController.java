package com.xczhihui.cloudClass.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xczhihui.cloudClass.service.CourseApplyService;
import com.xczhihui.cloudClass.service.CourseService;
import com.xczhihui.support.shiro.ManagerUserUtil;
import com.xczhihui.user.service.OnlineUserService;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.common.support.service.AttachmentCenterService;
import com.xczhihui.bxg.common.util.DateUtil;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.controller.AbstractController;
import com.xczhihui.bxg.online.common.domain.CourseApplyInfo;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.common.domain.ScoreType;
import com.xczhihui.bxg.online.common.domain.TeachMethod;
import com.xczhihui.bxg.online.common.enums.CourseDismissal;
import com.xczhihui.bxg.online.common.enums.CourseForm;
import com.xczhihui.bxg.online.common.enums.Multimedia;
import com.xczhihui.bxg.online.common.utils.RedissonUtil;
import com.xczhihui.cloudClass.vo.LecturerVo;

/**
 * 课程管理控制层实现类
 *
 * @author yxd
 */

@Controller
@RequestMapping("cloudclass/courseApply")
public class CourseApplyInfoController extends AbstractController {
	protected final static String CLOUD_CLASS_PATH_PREFIX = "/cloudClass/";
	@Autowired
	private CourseService courseService;
	@Autowired
	private CourseApplyService courseApplyService;
	@Autowired
	private AttachmentCenterService att;
	@Value("${online.web.url}")
	private String weburl;

	@Autowired
	private OnlineUserService onlineUserService;
	@Autowired
	private RedissonUtil redissonUtil;

	@RequestMapping(value = "index")
	public String index(HttpServletRequest request) {

		List<Menu> menuVos = courseService.getfirstMenus(null);
		request.setAttribute("menuVo", menuVos);

		// 在列表初始化时查找出课程类别
		List<ScoreType> scoreTypeVos = courseService.getScoreType();
		request.setAttribute("scoreTypeVo", scoreTypeVos);

		// 在列表初始化时查找出授课方式
		List<TeachMethod> teachMethodVos = courseService.getTeachMethod();
		request.setAttribute("teachMethodVo", teachMethodVos);

		List<LecturerVo> lecturers = courseService.getLecturers();
		request.setAttribute("lecturerVo", lecturers);

		List<Map<String, Object>> mapList = onlineUserService
				.getAllUserLecturer();
		for (Map<String, Object> map : mapList) {
			String str = "昵称:" + map.get("name").toString() + ",帐号:"
					+ map.get("logo").toString();
			map.put("name", str);
		}
		request.setAttribute("mapList", mapList);

		return CLOUD_CLASS_PATH_PREFIX + "/courseApplyInfo";
	}

	// @RequiresPermissions("cloudClass:menu:course")
	@RequestMapping(value = "list")
	@ResponseBody
	public TableVo courses(TableVo tableVo) {
		int pageSize = tableVo.getiDisplayLength();
		int index = tableVo.getiDisplayStart();
		int currentPage = index / pageSize + 1;
		String params = tableVo.getsSearch();
		Groups groups = Tools.filterGroup(params);

		CourseApplyInfo searchVo = new CourseApplyInfo();

		Group courseName = groups.findByName("search_courseName");
		if (courseName != null) {
			searchVo.setTitle(courseName.getPropertyValue1().toString());
		}

		Group searchLecturer = groups.findByName("search_lecturer");
		if (searchLecturer != null) {
			searchVo.setLecturer(searchLecturer.getPropertyValue1().toString());
		}

		Group menuId = groups.findByName("search_menu");
		if (menuId != null) {
			searchVo.setCourseMenu(menuId.getPropertyValue1().toString());
		}

		Group form = groups.findByName("search_form");
		if (form != null) {
			// 音频
			if (form.getPropertyValue1().toString().equals("4")) {
				searchVo.setCourseForm(CourseForm.VOD.getCode());
				searchVo.setMultimediaType(Multimedia.AUDIO.getCode());
			} else {
				searchVo.setCourseForm(Integer.valueOf(form.getPropertyValue1()
						.toString()));
			}
		}

		Group isFree = groups.findByName("search_isFree");
		if (isFree != null) {
			if (isFree.getPropertyValue1().equals("0")) {
				searchVo.setIsFree(true);
			} else {
				searchVo.setIsFree(false);
			}
		}

		Group status = groups.findByName("search_status");
		if (status != null) {
			searchVo.setStatus(Integer.valueOf(status.getPropertyValue1()
					.toString()));
		}

		Group startTime = groups.findByName("startTime");
		if (startTime != null) {
			searchVo.setStartTime(DateUtil.parseDate(startTime
					.getPropertyValue1().toString(), "yyyy-MM-dd"));
		}
		Group stopTime = groups.findByName("stopTime");
		if (stopTime != null) {
			searchVo.setEndTime(DateUtil.parseDate(stopTime.getPropertyValue1()
					.toString(), "yyyy-MM-dd"));
		}

		Page<CourseApplyInfo> page = courseApplyService.findCoursePage(
				searchVo, currentPage, pageSize);
		int total = page.getTotalCount();
		tableVo.setAaData(page.getItems());
		tableVo.setiTotalDisplayRecords(total);
		tableVo.setiTotalRecords(total);
		return tableVo;

	}

	@RequestMapping(value = "courseDetail")
	public String courseDetail(HttpServletRequest request, Integer id) {
		CourseApplyInfo courseApplyInfo = courseApplyService
				.findCourseApplyById(id);
		request.setAttribute("courseApplyInfo", courseApplyInfo);
		List<CourseDismissal> dismissalList = CourseDismissal
				.getDismissalList();
		request.setAttribute("dismissalList", dismissalList);
		return CLOUD_CLASS_PATH_PREFIX + "/courseApplyDetail";
	}

	/**
	 * 通过审核
	 *
	 * @param courseApplyId
	 * @return
	 */
	// @RequiresPermissions("cloudClass:menu:course")
	@RequestMapping(value = "pass", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject pass(Integer courseApplyId) {
		ResponseObject responseObj = new ResponseObject();
		courseApplyService.savePass(courseApplyId, ManagerUserUtil.getId());
		responseObj.setSuccess(true);
		responseObj.setErrorMessage("成功通过申请");
		return responseObj;
	}

	/**
	 * 未通过审核
	 *
	 * @param courseApplyInfo
	 * @return
	 */
	// @RequiresPermissions("cloudClass:menu:course")
	@RequestMapping(value = "notPass", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject notPass(@RequestBody CourseApplyInfo courseApplyInfo) {
		ResponseObject responseObj = new ResponseObject();
		courseApplyService
				.saveNotPass(courseApplyInfo, ManagerUserUtil.getId());
		responseObj.setSuccess(true);
		responseObj.setErrorMessage("成功拒绝申请");
		return responseObj;
	}

}
