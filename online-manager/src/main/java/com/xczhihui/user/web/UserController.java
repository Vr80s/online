package com.xczhihui.user.web;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.online.common.domain.Role;
import com.xczhihui.bxg.online.common.domain.User;
import com.xczhihui.common.support.service.SystemVariateService;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.web.controller.AbstractController;
import com.xczhihui.support.shiro.ManagerUserUtil;
import com.xczhihui.user.service.RoleService;
import com.xczhihui.user.service.UserService;
import com.xczhihui.utils.*;

/**
 * 权限用户管理
 *
 * @author Haicheng Jiang
 */
@Controller
@RequestMapping("user")
public class UserController extends AbstractController {

    protected final static String USER_PATH_PREFIX = "/user/";

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private SystemVariateService systemVariateService;

    @RequestMapping(value = "index")
    public String index(HttpServletRequest request) {
        List<Role> roles = this.roleService.getAllValidRoles();
        request.setAttribute("roles", roles);
        return USER_PATH_PREFIX + "users";
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public TableVo list(TableVo tableVo) {
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);
        Group group = groups.findByName("roleId");
        Group group2 = groups.findByName("searchName");
        String roleId = null;
        String searchName = null;
        if (group != null) {
            roleId = group.getPropertyValue1().toString();
        }
        if (group2 != null) {
            searchName = group2.getPropertyValue1().toString();
        }
        group = groups.findByName("is_delete");
        Boolean isDelete = null;
        if (group != null) {
            String is_delete = group.getPropertyValue1().toString();
            if ("1".equals(is_delete)) {
                isDelete = true;
            } else if ("0".equals(is_delete)) {
                isDelete = false;
            }
        }
        Page<User> page = userService.findUserPage(isDelete, roleId,
                searchName, currentPage, pageSize);
        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;
    }

    @RequestMapping(value = "list1")
    @ResponseBody
    public TableVo list1(TableVo tableVo) {
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);
        PageVo page = new PageVo(pageSize, currentPage);
        groups.setOrderbys(new String[]{"create_time"});
        groups.setOrders(new boolean[]{false});
        page = userService.findPageByGroups(groups, page);
        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject add(User user) {
        ResponseObject responseObject = new ResponseObject();
        User u = userService.getUserByLoginName(user.getLoginName());
        if (u != null) {
            responseObject.setSuccess(false);
            responseObject.setErrorMessage("登录名已经存在");
            throw new IllegalArgumentException("登录名已经存在");
        } else {
            user.setCreatePerson(ManagerUserUtil.getId());
            if (!StringUtils.hasText(user.getPassword())
                    && StringUtils.hasText(user.getMobile())) {
                user.setPassword(user.getMobile());
            }
            this.userService.addUser(user);

            responseObject.setSuccess(true);
            responseObject.setErrorMessage("保存成功");
        }
        return responseObject;
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject delete(HttpServletRequest request)
            throws ServletRequestBindingException {
        ResponseObject responseObject = new ResponseObject();
        String id = ServletRequestUtils.getRequiredStringParameter(request,
                "id");
        this.userService.deleteUser(id);
        responseObject.setSuccess(true);
        responseObject.setErrorMessage("删除成功");
        return responseObject;
    }

    @RequestMapping(value = "deletes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletes(HttpServletRequest request)
            throws ServletRequestBindingException {
        ResponseObject responseObject = new ResponseObject();
        String id = ServletRequestUtils.getRequiredStringParameter(request,
                "ids");
        String[] ids = id.split(",");
        this.userService.deleteUsers(Arrays.asList(ids));
        responseObject.setSuccess(true);
        responseObject.setErrorMessage("删除成功");
        return responseObject;
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject update(HttpServletRequest request, User user) {
        ResponseObject responseObject = new ResponseObject();
        User managerUser = this.userService.updateUser(user);
        responseObject.setSuccess(true);
        responseObject.setErrorMessage("修改成功");
        return responseObject;
    }

    @RequestMapping(value = "reset/password", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject resetPassword(HttpServletRequest request)
            throws ServletRequestBindingException {
        ResponseObject responseObject = new ResponseObject();
        String loginName = ServletRequestUtils.getRequiredStringParameter(
                request, "loginName");
        String password = ServletRequestUtils.getStringParameter(request,
                "password", "123456");
        this.userService.updateUserPassword(loginName, password);
        responseObject.setSuccess(true);
        responseObject.setErrorMessage("修改密码成功");
        return responseObject;
    }

    @RequestMapping(value = "edit/role", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateRole(HttpServletRequest request)
            throws ServletRequestBindingException {
        String strUids = ServletRequestUtils.getRequiredStringParameter(
                request, "userIds");
        String[] uids = strUids.split(",");
        String[] rids = ServletRequestUtils.getStringParameters(request,
                "roleIds");
        Set<String> roleIds = null;
        if (rids.length > 0) {
            roleIds = new HashSet<>(Arrays.asList(rids));
        }
        this.roleService.updateUserRoles(new HashSet<>(Arrays.asList(uids)),
                roleIds);
        return ResponseObject.newSuccessResponseObject("修改成功");
    }

    @RequestMapping(value = "check/login_name")
    @ResponseBody
    public String checkLoginName(HttpServletRequest request)
            throws ServletRequestBindingException {
        String loginName = ServletRequestUtils.getRequiredStringParameter(
                request, "loginName");
        User u = userService.getUserByLoginName(loginName);
        if (u != null) {
            return "false";
        }
        return "true";
    }

    @RequestMapping(value = "get/userRoleIds", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject getUserRoleIds(String userId)
            throws ServletRequestBindingException {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setResultObject(org.apache.commons.lang.StringUtils
                .join(this.roleService.getUserRoleIds(userId), ","));
        responseObject.setSuccess(true);
        return responseObject;
    }

    @RequestMapping(value = "initUserCoin", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject initUserCoin(String userId)
            throws ServletRequestBindingException {
        ResponseObject responseObject = new ResponseObject();
        userService.saveUserCoin();
        responseObject.setSuccess(true);
        return responseObject;
    }
}