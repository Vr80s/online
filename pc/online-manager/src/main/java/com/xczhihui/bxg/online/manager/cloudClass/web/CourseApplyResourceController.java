package com.xczhihui.bxg.online.manager.cloudClass.web;

import com.xczhihui.bxg.common.support.service.AttachmentCenterService;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.controller.AbstractController;
import com.xczhihui.bxg.online.common.domain.CourseApplyResource;
import com.xczhihui.bxg.online.manager.cloudClass.service.CourseApplyService;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 资源管理控制层实现类
 * @author yxd
 */

@Controller
@RequestMapping("cloudclass/courseResource")
public class CourseApplyResourceController extends AbstractController{
	protected final static String CLOUD_CLASS_PATH_PREFIX = "/cloudClass/";
	@Autowired
	private CourseApplyService courseApplyService;
	@Autowired
	private AttachmentCenterService att;
	@Value("${online.web.url:http://www.ixincheng.com}")
	private String weburl;

	
	@RequestMapping(value = "index")
	public String index(HttpServletRequest request) {
		return CLOUD_CLASS_PATH_PREFIX + "/courseApplyResource";
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
          
          CourseApplyResource searchVo=new CourseApplyResource();

          Group title = groups.findByName("search_title");
          if (title != null) {
        	  searchVo.setTitle(title.getPropertyValue1().toString());
          }

          Group name = groups.findByName("search_name");
          if (name != null) {
        	  searchVo.setUserName(name.getPropertyValue1().toString());
          }

          Group isDeleted = groups.findByName("search_isDeleted");
          if (isDeleted != null) {
        	  searchVo.setDeleted(isDeleted.getPropertyValue1().toString().equals("1"));
          }

          Page<CourseApplyResource> page = courseApplyService.findCourseApplyResourcePage(searchVo, currentPage, pageSize);
          int total = page.getTotalCount();
          tableVo.setAaData(page.getItems());
          tableVo.setiTotalDisplayRecords(total);
          tableVo.setiTotalRecords(total);
          return tableVo;
		
	}

	/**
	 * 删除资源
	 * @return
	 */
	@RequiresPermissions("cloudClass:menu:course")
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject delete(Integer courseApplyResourceId){
		ResponseObject responseObj = new ResponseObject();
		courseApplyService.deleteOrRecoveryCourseApplyResource(courseApplyResourceId,true);
		responseObj.setSuccess(true);
		responseObj.setErrorMessage("成功删除资源");
		return responseObj;
	}

	/**
	 * 恢复资源
	 * @return
	 */
	@RequiresPermissions("cloudClass:menu:course")
	@RequestMapping(value = "recovery", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject recovery(Integer courseApplyResourceId){
		ResponseObject responseObj = new ResponseObject();
		courseApplyService.deleteOrRecoveryCourseApplyResource(courseApplyResourceId,false);
		responseObj.setSuccess(true);
		responseObj.setErrorMessage("成功恢复资源");
		return responseObj;
	}

}
