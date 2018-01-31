package com.xczhihui.bxg.online.manager.cloudClass.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.online.manager.cloudClass.service.StudentManagerService;
import com.xczhihui.bxg.online.manager.cloudClass.vo.StudentManagerVo;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;

@Controller
@RequestMapping("cloudclass/studentmanager")
public class StudentManagerController {
	protected final static String CLOUD_CLASS_PATH_PREFIX = "/cloudClass/";
	
	@Autowired
	StudentManagerService studentManagerService ;
	@RequestMapping(value = "index")
	public String index(HttpServletRequest request) {
		
		return CLOUD_CLASS_PATH_PREFIX + "/studentManager";
	}
	
	//@RequiresPermissions("cloudClass:menu：student")
	@RequestMapping(value = "list")
	@ResponseBody
	public TableVo studentmanagers(TableVo tableVo) {
		int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);
        
        StudentManagerVo searchVo=new StudentManagerVo();
        
        studentManagerService.findstudentsInfoPage(searchVo, currentPage, pageSize);
		return null;
	}
	
}
