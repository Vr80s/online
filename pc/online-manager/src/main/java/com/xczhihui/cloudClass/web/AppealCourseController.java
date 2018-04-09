package com.xczhihui.cloudClass.web;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xczhihui.cloudClass.service.CourseService;
import com.xczhihui.cloudClass.service.ExamineCourseService;
import com.xczhihui.cloudClass.service.PublicCourseService;
import com.xczhihui.cloudClass.vo.LiveExamineInfoVo;
import com.xczhihui.user.service.OnlineUserService;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.common.util.DateUtil;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.common.domain.Menu;

/**
 * 公开课管理控制层实现类
 * 
 * @author yxd
 */

@Controller
@RequestMapping("cloudClass/appeal")
public class AppealCourseController {
	protected final static String PUBLIC_CLASS_PATH_PREFIX = "/cloudClass/";
	@Autowired
	private PublicCourseService publicCourseService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private OnlineUserService onlineUserService;
	@Autowired
	private ExamineCourseService examineCourseService;

	@Value("${online.web.publiccloud.courseType}")
	private String courseType;
	@Value("${online.web.publiccloud.courseTypeId}")
	private String courseTypeId;

	@RequestMapping(value = "index")
	public String index(HttpServletRequest request) {
		List<Menu> menuVos = publicCourseService.getMenus();
		request.setAttribute("menuVo", menuVos);
		// 查找所有的讲师
		List<Map<String, Object>> mapList = onlineUserService
				.getAllUserLecturer();
		for (Map<String, Object> map : mapList) {
			String str = "昵称:" + map.get("name").toString() + ",帐号:"
					+ map.get("logo").toString();
			map.put("name", str);
		}
		request.setAttribute("mapList", mapList);
		return PUBLIC_CLASS_PATH_PREFIX + "/appealCourse";
	}

	// @RequiresPermissions("cloudClass:menu:appeal")
	@RequestMapping(value = "list")
	@ResponseBody
	public TableVo courses(TableVo tableVo) {
		int pageSize = tableVo.getiDisplayLength();
		int index = tableVo.getiDisplayStart();
		int currentPage = index / pageSize + 1;
		String params = tableVo.getsSearch();
		Groups groups = Tools.filterGroup(params);

		LiveExamineInfoVo liveExamineInfoVo = new LiveExamineInfoVo();
		Group startTime = groups.findByName("startTime");
		if (startTime != null) {
			liveExamineInfoVo.setS_startTime(DateUtil.parseDate(startTime
					.getPropertyValue1().toString(), "yyyy-MM-dd"));
		}
		Group stopTime = groups.findByName("stopTime");
		if (stopTime != null) {
			liveExamineInfoVo.setS_endTime(DateUtil.parseDate(stopTime
					.getPropertyValue1().toString(), "yyyy-MM-dd"));
		}
		Group status = groups.findByName("status");
		if (status != null) {
			liveExamineInfoVo.setExamineStatus(status.getPropertyValue1()
					.toString());
		}
		Group name = groups.findByName("name");
		if (name != null) {
			liveExamineInfoVo.setLecturerName((name.getPropertyValue1()
					.toString()));
		}
		Group ssIsdelete = groups.findByName("ssIsdelete");
		if (ssIsdelete != null) {
			String falg = ssIsdelete.getPropertyValue1().toString();
			liveExamineInfoVo.setSsisDelete("1".equals(falg) ? true : false);
		}

		Page<LiveExamineInfoVo> page = examineCourseService.findAppealListPage(
				liveExamineInfoVo, currentPage, pageSize);
		int total = page.getTotalCount();
		tableVo.setAaData(page.getItems());
		tableVo.setiTotalDisplayRecords(total);
		tableVo.setiTotalRecords(total);
		return tableVo;

	}

	/**
	 * 编辑
	 * 
	 * @param vo
	 * @return
	 */
	// @RequiresPermissions("cloudClass:menu：publicClass")
	@RequestMapping(value = "updateCourseById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateCourseById(LiveExamineInfoVo liveExamineInfoVo) {
		ResponseObject responseObj = new ResponseObject();
		try {
			examineCourseService.updateCourse(liveExamineInfoVo);
			responseObj.setSuccess(true);
			responseObj.setErrorMessage("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			responseObj.setSuccess(false);
			responseObj.setErrorMessage("修改失败");
		}
		return responseObj;
	}

	/**
	 * 通过审核 Description：
	 * 
	 * @param args
	 * @return void
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	// @RequiresPermissions("cloudClass:menu：publicClass")
	@RequestMapping(value = "passCourseById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateApply(String id) {
		ResponseObject responseObj = new ResponseObject();
		try {
			examineCourseService.updateApply(id);
			responseObj.setSuccess(true);
			responseObj.setErrorMessage("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			responseObj.setSuccess(false);
			responseObj.setErrorMessage("修改失败");
		}
		return responseObj;
	}

	@RequestMapping(value = "deletes", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject deletes(String ids) throws InvocationTargetException,
			IllegalAccessException {
		ResponseObject responseObject = new ResponseObject();
		if (ids != null) {
			String[] _ids = ids.split(",");
			examineCourseService.deletesAppeal(_ids);
		}
		responseObject.setSuccess(true);
		responseObject.setErrorMessage("删除成功!");
		return responseObject;
	}

	@RequestMapping(value = "recoverys", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateRecoverys(String ids)
			throws InvocationTargetException, IllegalAccessException {
		ResponseObject responseObject = new ResponseObject();
		if (ids != null) {
			String[] _ids = ids.split(",");
			examineCourseService.updateAppeal(_ids);
		}
		responseObject.setSuccess(true);
		responseObject.setErrorMessage("恢复成功!");
		return responseObject;
	}

}
