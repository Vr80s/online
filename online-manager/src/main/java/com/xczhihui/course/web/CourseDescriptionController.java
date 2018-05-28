package com.xczhihui.course.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xczhihui.course.service.CourseDescriptionService;
import com.xczhihui.course.vo.CourseDescriptionVo;
import com.xczhihui.support.shiro.ManagerUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.common.util.bean.ResponseObject;

@Controller
@RequestMapping(value = "/cloudClass/courseDescription")
public class CourseDescriptionController {

	@Autowired
	private CourseDescriptionService courseDescriptionService;

	@RequestMapping(value = "/getDesList", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject getDesList(CourseDescriptionVo courseDescriptionVo) {
		ResponseObject responseObj = new ResponseObject();
		List<Map<String, Object>> listCD = courseDescriptionService
				.getDesList(courseDescriptionVo);

		responseObj.setSuccess(true);
		responseObj.setResultObject(listCD);
		return responseObj;
	}

	/**
	 * 添加
	 *
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/addCourseDescription", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject addCourseDescription(
			CourseDescriptionVo courseDescriptionVo, HttpServletRequest request) {
		ResponseObject responseObj = new ResponseObject();
		courseDescriptionVo.setCreatePerson(ManagerUserUtil.getUsername());
		try {
			courseDescriptionVo.setCourseId(courseDescriptionVo
					.getDesCourseId());
			courseDescriptionVo.setPreview(false);
			courseDescriptionService.addCourseDescription(courseDescriptionVo);

			responseObj.setSuccess(true);
			responseObj.setErrorMessage("新增成功");
		} catch (Exception e) {
			e.printStackTrace();
			responseObj.setSuccess(false);
			responseObj.setErrorMessage("新增失败");
		}
		return responseObj;
	}

	/**
	 * 编辑
	 *
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "updateCourseDescriptionById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateCourseDescriptionById(
			CourseDescriptionVo courseDescriptionVo) {
		ResponseObject responseObj = new ResponseObject();
		try {
			courseDescriptionService
					.updateCourseDescription(courseDescriptionVo);
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
	 * 保存预览
	 *
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "testCourseDescriptionById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject testCourseDescriptionById(
			CourseDescriptionVo courseDescriptionVo) {
		ResponseObject responseObj = new ResponseObject();
		try {
			courseDescriptionService
					.updateTestCourseDescription(courseDescriptionVo);
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
	 * 批量逻辑删除
	 *
	 * @param Integer
	 *            id
	 * @return
	 */
	@RequestMapping(value = "deletes", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject deletes(String ids) {
		ResponseObject responseObject = new ResponseObject();
		if (ids != null) {
			String[] _ids = ids.split(",");
			courseDescriptionService.deletes(_ids);
		}
		responseObject.setSuccess(true);
		responseObject.setErrorMessage("删除成功!");
		return responseObject;
	}
}
