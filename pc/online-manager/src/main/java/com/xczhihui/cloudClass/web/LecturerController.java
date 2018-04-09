package com.xczhihui.cloudClass.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.xczhihui.cloudClass.service.LecturerService;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.common.support.domain.SystemVariate;
import com.xczhihui.bxg.common.support.service.SystemVariateService;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.common.domain.Lecturer;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.course.vo.LecturerVo;

/**
 * 菜单控制层实现类
 * 
 * @author yxd
 */
@Controller
@RequestMapping(value = "/cloudClass/lecturer")
public class LecturerController {
	protected final static String CLOUD_CLASS_PATH_PREFIX = "/cloudClass/";
	@Autowired
	private LecturerService lecturerService;
	@Autowired
	SystemVariateService systemVariateService;

	// @RequiresPermissions("cloudClass:menu:lecturer")
	@RequestMapping(value = "index")
	public String index(HttpServletRequest request) {
		List<Menu> menuVos = lecturerService.getfirstMenus();
		request.setAttribute("menuVo", menuVos);
		List<SystemVariate> roles = systemVariateService
				.getSystemVariatesByParentValue("role_type");
		request.setAttribute("roles", roles);
		return CLOUD_CLASS_PATH_PREFIX + "/lecturer";
	}

	// @RequiresPermissions("cloudClass:menu:lecturer")
	@RequestMapping(value = "list")
	@ResponseBody
	public TableVo Lecturers(TableVo tableVo) {
		int pageSize = tableVo.getiDisplayLength();
		int index = tableVo.getiDisplayStart();
		int currentPage = index / pageSize + 1;
		String params = tableVo.getsSearch();
		Groups groups = Tools.filterGroup(params);

		LecturerVo searchVo = new LecturerVo();

		Group name = groups.findByName("search_name");
		if (name != null) {
			searchVo.setName(name.getPropertyValue1().toString());
		}

		Group menuId = groups.findByName("search_menu");
		if (menuId != null) {
			searchVo.setMenuId(Integer.valueOf(menuId.getPropertyValue1()
					.toString()));
		}

		Group roleType = groups.findByName("search_roleType");
		if (roleType != null) {
			searchVo.setRoleType(roleType.getPropertyValue1().toString());
		}

		Page<LecturerVo> page = lecturerService.findLecturerPage(searchVo,
				currentPage, pageSize);
		int total = page.getTotalCount();
		tableVo.setAaData(page.getItems());
		tableVo.setiTotalDisplayRecords(total);
		tableVo.setiTotalRecords(total);
		return tableVo;

	}

	/**
	 * 添加
	 * 
	 * @param vo
	 * @return
	 */
	// @RequiresPermissions("cloudClass:menu:lecturer")
	@RequestMapping(value = "addLecturer", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject add(Lecturer lecturer) {
		ResponseObject responseObj = new ResponseObject();
		try {
			lecturerService.addLecturer(lecturer);
			responseObj.setSuccess(true);
			responseObj.setErrorMessage("老师新增成功");
		} catch (Exception e) {
			responseObj.setSuccess(false);
			responseObj.setErrorMessage("老师新增失败");
		}
		return responseObj;
	}

	/**
	 * 修改
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject update(Lecturer lecturer) {
		ResponseObject responseObj = new ResponseObject();
		try {
			lecturerService.updateLecturer(lecturer);
			responseObj.setSuccess(true);
			responseObj.setErrorMessage("修改成功");
		} catch (Exception e) {
			responseObj.setSuccess(false);
			responseObj.setErrorMessage("修改失败");
		}
		return responseObj;
	}

	/**
	 * 授课记录
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "findTeachRecordsByLecturerId", method = RequestMethod.GET)
	@ResponseBody
	public List<LecturerVo> findTeachRecordsByLecturerId(String id) {
		List<LecturerVo> LecturerVos = lecturerService
				.findTeachRecordsByLecturerId(id);
		return LecturerVos;
	}

	@RequestMapping(value = "deletes", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject deletes(String ids) {
		ResponseObject responseObject = new ResponseObject();
		String msg = "";
		if (ids != null) {
			String[] _ids = ids.split(",");
			msg = lecturerService.deletes(_ids);
		}
		responseObject.setSuccess(true);
		responseObject.setErrorMessage(msg);
		return responseObject;
	}

}
