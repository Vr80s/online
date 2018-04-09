package com.xczhihui.cloudClass.web;

import javax.servlet.http.HttpServletRequest;

import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.cloudClass.service.StudentManagerService;
import com.xczhihui.course.vo.StudentManagerVo;

@Controller
@RequestMapping("cloudclass/studentmanager")
public class StudentManagerController {
	protected final static String CLOUD_CLASS_PATH_PREFIX = "/cloudClass/";

	@Autowired
	StudentManagerService studentManagerService;

	@RequestMapping(value = "index")
	public String index(HttpServletRequest request) {

		return CLOUD_CLASS_PATH_PREFIX + "/studentManager";
	}

	// @RequiresPermissions("cloudClass:menu：student")
	@RequestMapping(value = "list")
	@ResponseBody
	public TableVo studentmanagers(TableVo tableVo) {
		int pageSize = tableVo.getiDisplayLength();
		int index = tableVo.getiDisplayStart();
		int currentPage = index / pageSize + 1;
		String params = tableVo.getsSearch();
		Groups groups = Tools.filterGroup(params);

		StudentManagerVo searchVo = new StudentManagerVo();

		studentManagerService.findstudentsInfoPage(searchVo, currentPage,
				pageSize);
		return null;
	}

}
