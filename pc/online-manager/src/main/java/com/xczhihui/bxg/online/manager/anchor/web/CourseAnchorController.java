package com.xczhihui.bxg.online.manager.anchor.web;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.controller.AbstractController;
import com.xczhihui.bxg.online.api.po.CourseAnchor;
import com.xczhihui.bxg.online.manager.anchor.service.AnchorService;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 课程管理控制层实现类
 * @author yxd
 */

@Controller
@RequestMapping("anchor/courseAnchor")
public class CourseAnchorController extends AbstractController{

	protected final static String CLOUD_CLASS_PATH_PREFIX = "/anchor/";
	@Autowired
	private AnchorService anchorService;

	@RequestMapping(value = "index")
	public String index(HttpServletRequest request) {
		return CLOUD_CLASS_PATH_PREFIX + "/courseAnchor";
	}
	
	@RequiresPermissions("cloudClass:menu:course")
	@RequestMapping(value = "list")
	@ResponseBody
	public TableVo courses(TableVo tableVo) {
	      int pageSize = tableVo.getiDisplayLength();
          int index = tableVo.getiDisplayStart();
          int currentPage = index / pageSize + 1;
          String params = tableVo.getsSearch();
          Groups groups = Tools.filterGroup(params);
          
          CourseAnchor searchVo=new CourseAnchor();

          Group name = groups.findByName("search_name");
          if (name != null) {
        	  searchVo.setName(name.getPropertyValue1().toString());
          }

          Group searchLecturer = groups.findByName("search_type");
          if (searchLecturer != null) {
        	  searchVo.setType(Integer.valueOf(searchLecturer.getPropertyValue1().toString()));
          }

          Page<CourseAnchor> page = anchorService.findCourseAnchorPage(searchVo, currentPage, pageSize);
          int total = page.getTotalCount();
          tableVo.setAaData(page.getItems());
          tableVo.setiTotalDisplayRecords(total);
          tableVo.setiTotalRecords(total);
          return tableVo;
		
	}

    /**
     * 查看
     * @param id
     * @return
     */
    @RequiresPermissions("cloudClass:menu:course")
    @RequestMapping(value = "findCourseAnchorById", method = RequestMethod.GET)
    @ResponseBody
    public CourseAnchor findCourseAnchorById(Integer id) {
        return anchorService.findCourseAnchorById(id);
    }

    /**
     * 编辑
     * @return
     */
    @RequiresPermissions("cloudClass:menu:course")
    @RequestMapping(value = "updateCourseById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateCourseById (CourseAnchor courseAnchor){
        ResponseObject responseObj = new ResponseObject();

        anchorService.updateCourseAnchor(courseAnchor);
        responseObj.setSuccess(true);
        responseObj.setErrorMessage("修改成功");
        return responseObj;
    }

    /**
     * 编辑
     * @return
     */
    @RequiresPermissions("cloudClass:menu:course")
    @RequestMapping(value = "editPermissions")
    @ResponseBody
    public ResponseObject editPermissions (Integer id){
        ResponseObject responseObj = new ResponseObject();
        anchorService.updatePermissions(id);
        responseObj.setSuccess(true);
        responseObj.setErrorMessage("修改成功");
        return responseObj;
    }
}
