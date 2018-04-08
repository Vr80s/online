package com.xczhihui.user.web;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.controller.AbstractController;
import com.xczhihui.bxg.online.common.domain.Role;
import com.xczhihui.support.shiro.ManagerUserUtil;
import com.xczhihui.user.service.RoleService;
import com.xczhihui.utils.PageVo;

/**
 * 权限角色管理
 *
 * @author Haicheng Jiang
 */
@Controller
@RequestMapping("user/role")
public class RoleController extends AbstractController {
    protected final static String USER_PATH_PREFIX = "/user/";
    @Autowired
    private RoleService roleService;

    //@RequiresPermissions("role:manager")
    @RequestMapping(value = "index")
    public String index(HttpServletRequest request) {
        return USER_PATH_PREFIX + "/roles";
    }

    //@RequiresPermissions("role:manager")
    @RequestMapping(value = "list")
    @ResponseBody
    public TableVo roles(TableVo tableVo) {
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();

        Groups groups = Tools.filterGroup(params);
        PageVo page = new PageVo(pageSize, currentPage);
        groups.setOrderbys(new String[]{"create_time"});
        groups.setOrders(new boolean[]{false});
        page = roleService.findPageByGroups(groups, page);
        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;
    }

    //@RequiresPermissions("role:manager")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject addRole(Role role) {
        ResponseObject responseObject = new ResponseObject();
        Role r = this.roleService.getRoleByCode(role.getCode());
        if (r != null) {
            responseObject.setSuccess(false);
            responseObject.setErrorMessage("角色代码已经存在");
            throw new IllegalArgumentException("角色代码已经存在");
        } else {
            role.setCreatePerson(ManagerUserUtil.getId());
            this.roleService.addRole(role);
            responseObject.setSuccess(true);
            responseObject.setErrorMessage("创建成功");
        }
        return responseObject;
    }

    //@RequiresPermissions("role:manager")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject delete(HttpServletRequest request) throws ServletRequestBindingException {
        ResponseObject responseObject = new ResponseObject();
        String id = ServletRequestUtils.getRequiredStringParameter(request, "id");
        this.roleService.deleteRole(id);
        responseObject.setSuccess(true);
        responseObject.setErrorMessage("删除成功");
        return responseObject;
    }

    //@RequiresPermissions("role:manager")
    @RequestMapping(value = "deletes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletes(HttpServletRequest request) throws ServletRequestBindingException {
        ResponseObject responseObject = new ResponseObject();
        String id = ServletRequestUtils.getRequiredStringParameter(request, "ids");
        String[] strids = id.split(",");
        List<String> ids = Arrays.asList(strids);
        this.roleService.deleteRoles(ids);
        responseObject.setSuccess(true);
        responseObject.setErrorMessage("删除成功");
        return responseObject;
    }

    //@RequiresPermissions("role:manager")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject update(Role role) {
        ResponseObject responseObject = new ResponseObject();
        this.roleService.updateRole(role);
        responseObject.setSuccess(true);
        responseObject.setErrorMessage("修改成功");
        return responseObject;
    }

    //@RequiresPermissions("role:manager")
    @RequestMapping(value = "update/resources", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateResources(HttpServletRequest request) throws ServletRequestBindingException {
        ResponseObject responseObject = new ResponseObject();
        String roleId = ServletRequestUtils.getRequiredStringParameter(request, "roleId");
        String resIds = ServletRequestUtils.getStringParameter(request, "resourceIds");
        Set<String> rids = null;
        if (StringUtils.hasText(resIds)) {
            String[] ids = resIds.split(",");
            rids = new HashSet<>(Arrays.asList(ids));
        }
        this.roleService.updateRoleResources(roleId, rids);
        responseObject.setSuccess(true);
        responseObject.setErrorMessage("权限分配成功");
        return responseObject;
    }

    //@RequiresPermissions("role:manager")
    @RequestMapping(value = "check/name")
    @ResponseBody
    public String checkName(HttpServletRequest request) throws ServletRequestBindingException {
        String name = ServletRequestUtils.getRequiredStringParameter(request, "name");
        Role r = this.roleService.getRoleByCode(name);
        if (r != null) {
            return "false";
        }
        return "true";
    }
}