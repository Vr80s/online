package com.xczhihui.mobile.web;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.common.support.domain.Attachment;
import com.xczhihui.common.support.service.AttachmentCenterService;
import com.xczhihui.common.support.service.AttachmentType;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.controller.AbstractController;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.common.domain.ScoreType;
import com.xczhihui.course.vo.CourseVo;
import com.xczhihui.mobile.service.MobileCourseService;
import com.xczhihui.support.shiro.ManagerUserUtil;
import com.xczhihui.utils.Group;

@Controller
@RequestMapping("mobile/course")
public class MobileCourseController extends AbstractController {
	protected final static String MOBILE_PATH_PREFIX = "/mobile/";
	@Autowired
	private MobileCourseService mobileCourseService;
	@Autowired
	private AttachmentCenterService att;
	@Value("${web.url}")
	private String weburl;

	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request) {

		List<Menu> menuVos = mobileCourseService.getfirstMenus();
		request.setAttribute("menuVo", menuVos);

		// 在列表初始化时查找出课程类别
		List<ScoreType> scoreTypeVos = mobileCourseService.getScoreType();
		request.setAttribute("scoreTypeVo", scoreTypeVos);
		return MOBILE_PATH_PREFIX + "/course";
	}

	@RequestMapping(value = "courseDetail")
	public String courseDetail(HttpServletRequest request) {
		List<Menu> menuVos = mobileCourseService.getfirstMenus();
		request.setAttribute("menuVo", menuVos);

		// 在列表初始化时查找出课程类别
		List<ScoreType> scoreTypeVos = mobileCourseService.getScoreType();
		request.setAttribute("scoreTypeVo", scoreTypeVos);

		request.setAttribute("weburl", weburl);
		return MOBILE_PATH_PREFIX + "/courseDetail";
	}

	// @RequiresPermissions("mobile:menu:course")
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

		Group scoreTypeId = groups.findByName("search_scoreType");
		if (scoreTypeId != null) {
			searchVo.setCourseTypeId(scoreTypeId.getPropertyValue1().toString());
		}

		Group isRecommend = groups.findByName("search_isRecommend");

		if (isRecommend != null) {
			searchVo.setIsRecommend(Integer.parseInt(isRecommend
					.getPropertyValue1().toString()));
		}

		Group searchCourse = groups.findByName("search_Course");
		if (searchCourse != null) {
			searchVo.setIsCourseDetails(Integer.parseInt(searchCourse
					.getPropertyValue1().toString()));
		}
		Page<CourseVo> page = mobileCourseService.findCoursePage(searchVo,
				currentPage, pageSize);
		int total = page.getTotalCount();
		tableVo.setAaData(page.getItems());
		tableVo.setiTotalDisplayRecords(total);
		tableVo.setiTotalRecords(total);
		return tableVo;

	}

	/**
	 * 查看
	 * 
	 * @param id
	 * @return
	 */
	// @RequiresPermissions("mobile:menu:course")
	@RequestMapping(value = "findCourseById", method = RequestMethod.GET)
	@ResponseBody
	public List<CourseVo> findCourseById(Integer id) {
		return mobileCourseService.findCourseById(id);
	}

	/**
	 * 编辑详情
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getCourseDetail", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject getCourseDetail(String courseId) {
		return ResponseObject.newSuccessResponseObject(mobileCourseService
				.getCourseDetail(courseId));
	}

	/**
	 * 获得模板内容
	 * 
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getTemplate", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObject getTemplate(String type, HttpSession session)
			throws Exception {

		String path = session.getServletContext().getRealPath("/template");
		File f = null;
		if ("detail".equals(type)) {
			f = new File(path + File.separator + "course_detail.html");
		} else if ("outline".equals(type)) {
			f = new File(path + File.separator + "/course_outline.html");
		} else if ("problem".equals(type)) {
			f = new File(path + File.separator + "/course_common_problem.html");
		}
		return ResponseObject
				.newSuccessResponseObject(FileUtil.readAsString(f));
	}

	/**
	 * 添加课程详情
	 * 
	 * @param courseId
	 * @param smallImgPath
	 * @param courseDetail
	 * @param courseOutline
	 * @param commonProblem
	 * @return
	 */
	@RequestMapping(value = "updateCourseDetail", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateCourseDetail(String courseId,
			String smallImgPath, String courseDetail, String courseOutline,
			String commonProblem) {
		mobileCourseService.updateCourseDetail(courseId, smallImgPath, null,
				courseDetail, courseOutline, commonProblem);
		return ResponseObject.newSuccessResponseObject("修改成功！");
	}

	@RequestMapping(value = "addPreview", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject addPreview(String courseId, String smallImgPath,
			String courseDetail, String courseOutline, String commonProblem) {
		mobileCourseService.addPreview(courseId, smallImgPath, null,
				courseDetail, courseOutline, commonProblem);
		return ResponseObject.newSuccessResponseObject("操作成功！");
	}

	@RequestMapping(value = "uploadImg", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject uploadImg(String content) {
		String str = content.split("base64,")[1];
		byte[] b = org.apache.commons.codec.binary.Base64.decodeBase64(str);
		Attachment a = att.addAttachment(ManagerUserUtil.getId(),
				AttachmentType.ONLINE, "1.png", b, "image/png");
		if (a.getError() != 0) {
			return ResponseObject.newErrorResponseObject("上传失败！");
		}
		return ResponseObject.newSuccessResponseObject(a);
	}

}
