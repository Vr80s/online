package com.xczhihui.bxg.online.manager.user.web;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.common.support.domain.SystemVariate;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.auth.UserHolder;
import com.xczhihui.bxg.common.web.controller.AbstractController;
import com.xczhihui.bxg.online.common.domain.Resource;
import com.xczhihui.bxg.online.manager.user.service.PermResourceService;
import com.xczhihui.bxg.online.manager.utils.ZtreeVo;

/**
 * 权限资源管理
 * 
 * @author Haicheng Jiang
 *
 */
@Controller
@RequestMapping("user/resource")
public class PermResourceController extends AbstractController {
	
	protected final static String USER_PATH_PREFIX = "/user/";
	
	@Autowired
	private PermResourceService resourceService;

	//@RequiresPermissions("resource:manager")
	@RequestMapping(value = "index")
	public String index(HttpServletRequest request) {
		List<SystemVariate> resourceTypes = this.resourceService.getResourceTypes();// 权限资源类型
		request.setAttribute("resourceTypes", resourceTypes);
		return USER_PATH_PREFIX + "/resources";
	}

	//@RequiresPermissions("resource:manager")
	@RequestMapping(value = "list")
	@ResponseBody
	public List<Resource> list(HttpServletRequest request) throws ServletRequestBindingException {
		String valid = ServletRequestUtils.getStringParameter(request, "valid");
		String type = ServletRequestUtils.getStringParameter(request, "type");
		Boolean isValid = null;
		if (StringUtils.hasText(valid)) {
			isValid = Boolean.valueOf(valid);
		}
		List<Resource> resources = this.resourceService.getHierarchyResources(type, isValid);
		return resources;
	}

	//@RequiresPermissions("resource:manager")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject add(Resource resource) {
		resource.setCreatePerson(UserHolder.getCurrentUser().getId());
		this.resourceService.addResource(resource);
		return ResponseObject.newSuccessResponseObject(resource);
	}

	//@RequiresPermissions("resource:manager")
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject delete(HttpServletRequest request) throws ServletRequestBindingException {
		String id = ServletRequestUtils.getRequiredStringParameter(request, "id");
		this.resourceService.deleteResources(Arrays.asList(id));
		return ResponseObject.newSuccessResponseObject("删除成功");
	}

	//@RequiresPermissions("resource:manager")
	@RequestMapping(value = "deletes", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject deletes(HttpServletRequest request) throws ServletRequestBindingException {
		String id = ServletRequestUtils.getRequiredStringParameter(request, "ids");
		String[] ids = id.split(",");
		this.resourceService.deleteResources(Arrays.asList(ids));
		return ResponseObject.newSuccessResponseObject("删除成功");
	}

	//@RequiresPermissions("resource:manager")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject update(Resource resource) {
		this.resourceService.updateResource(resource);
		return ResponseObject.newSuccessResponseObject(resource);
	}

	@RequestMapping(value = "tree")
	@ResponseBody
	public List<ZtreeVo> tree() {
		List<ZtreeVo> ztreeVos = this.resourceService.getResourcesTree();
		return ztreeVos;
	}

	@RequestMapping(value = "role/tree")
	@ResponseBody
	public List<ZtreeVo> roleTree(HttpServletRequest request) throws ServletRequestBindingException {
		String roleId = ServletRequestUtils.getRequiredStringParameter(request, "roleId");
		List<ZtreeVo> ztreeVos = this.resourceService.findRoleResourceTree(roleId);
		return ztreeVos;
	}
	
	//@RequiresPermissions("resource:manager")
	@RequestMapping(value = "using")
	@ResponseBody
	public String isUsing(HttpServletRequest request) throws ServletRequestBindingException {
		String resourceId = ServletRequestUtils.getRequiredStringParameter(request, "resourceId");
		boolean b = this.resourceService.isUsing(resourceId);
		return String.valueOf(b);
	}
}