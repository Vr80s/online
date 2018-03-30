package com.xczhihui.bxg.online.manager.ask.web;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.auth.UserHolder;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.common.domain.MenuCourseType;
import com.xczhihui.bxg.online.common.domain.User;
import com.xczhihui.bxg.online.manager.ask.service.AskMenuService;
import com.xczhihui.bxg.online.manager.ask.vo.MenuVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.ScoreTypeVo;
import com.xczhihui.bxg.online.manager.support.shiro.ManagerUserUtil;
import com.xczhihui.bxg.online.manager.user.service.UserService;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;

/**
 * 博问答学科管理控制层实现类
 *
 * @author yxd
 */
@RestController()
@RequestMapping(value = "/ask/menu")
public class AskMenuController {

    @Autowired
    private AskMenuService service;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("ask/menu");
        return mav;
    }

    /**
     * 增加学科拥有的课程类别（二级菜单）
     *
     * @param id
     * @param menuId
     * @return
     */
    @RequestMapping(value = "/addMenuCourseType")
    public ResponseObject addMenuCourseType(String id, Integer menuId) {
        return ResponseObject.newSuccessResponseObject(service.addMenuCourseType(id, menuId));
    }


    @RequestMapping(value = "/updateMenuCourseType")
    @ResponseBody
    public ResponseObject updateMenuCourseType(MenuVo menu) {
        ResponseObject responseObj = new ResponseObject();
        try {
            service.removeMenuCourseType(menu.getParentId());
            if (menu.getChildMenuNames() != null && !"".equals(menu.getChildMenuNames())) {

                service.addMenuCourseType(0 + "", menu.getParentId());
                String[] ChildMenuAry = menu.getChildMenuNames().split(",");
                for (int i = 0; i < ChildMenuAry.length; i++) {
                    service.addMenuCourseType(ChildMenuAry[i], menu.getParentId());
                }
            }
            responseObj.setSuccess(true);
            responseObj.setErrorMessage("关联课程类别成功");
        } catch (Exception e) {
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("关联课程类别失败");
        }
        return responseObj;

    }


    @RequestMapping(value = "/updateAskMenuLimit")
    @ResponseBody
    public ResponseObject updateAskMenuLimit(MenuVo menu) {
        ResponseObject responseObj = new ResponseObject();
        try {
            service.updateAskMenuLimit(menu);
            responseObj.setSuccess(true);
            responseObj.setErrorMessage("设置权限成功");
        } catch (Exception e) {
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("设置权限失败");
        }
        return responseObj;

    }


    //@RequiresPermissions("ask:menu")
    @RequestMapping(value = "list")
    @ResponseBody
    public TableVo list(TableVo tableVo) {
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);
//          Group sortGroup = groups.findByName("courseCount");
//          Group createPersonGroup = groups.findByName("createPerson");
        Group nameGroup = groups.findByName("menuName");
//          Group time_startGroup = groups.findByName("time_start");
//          Group time_endGroup = groups.findByName("time_end");
        MenuVo searchVo = new MenuVo();
//          if (sortGroup != null) {
//               searchVo.setOrderCourseCount(sortGroup.getPropertyValue1().toString());
//          }
        /*  if(createPersonGroup!=null){
               searchVo.setCreatePerson(createPersonGroup.getPropertyValue1().toString());
          }
          if(time_startGroup!=null){
               searchVo.setTime_start(DateUtil.parseDate(time_startGroup.getPropertyValue1().toString(), "yyyy-MM-dd"));
          }
          if(time_startGroup!=null){
               searchVo.setTime_end(DateUtil.parseDate(time_endGroup.getPropertyValue1().toString(),"yyyy-MM-dd"));
          }*/
        if (nameGroup != null) {
            searchVo.setName(nameGroup.getPropertyValue1().toString());
        }
        Page<MenuVo> page = service.findMenuPage(searchVo, currentPage, pageSize);
        if (page.getItems().size() > 0) {
            for (MenuVo vo : page.getItems()) {
                User user = userService.getUserById(vo.getCreatePerson());
                if (user != null) {
                    vo.setCreatePerson(user.getName());
                }
            }
        }
        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;
    }
    /**
     * 查询全部菜单列表
     * @return
     */
    /* @RequestMapping(value = "/getAllMenu")
     public ResponseObject getAllMenu(){
          return ResponseObject.newSuccessResponseObject(service.getAllMenu("",null,null));
     }*/

    /**
     * 禁用or启用
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateStatus(String id) {
        ResponseObject responseObj = new ResponseObject();
        try {
            String message = service.updateStatus(id);
            responseObj.setSuccess(true);
            if ("success".equals(message)) {
                responseObj.setErrorMessage("操作成功");
            } else {
                responseObj.setErrorMessage(message);
            }

        } catch (Exception e) {
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("操作失败");
        }
        return responseObj;
    }


    @RequestMapping(value = "childMenu", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject childMenu(String id, String menuId) {
        ResponseObject responseObj = new ResponseObject();
//          Menu menu=service.findById(Integer.parseInt(pid));
//          responseObj.setResultObject(service.findChildrenByNumber(menu.getNumber(),2));
        //点击设置按钮查询课程类别表
        responseObj.setResultObject(service.findScoreType(id));
        responseObj.setSuccess(true);
        return responseObj;
    }

    /**
     * 上移
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "up", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject up(String id) {
        ResponseObject responseObj = new ResponseObject();
        try {
            service.updateDirectionUp(id);
            responseObj.setSuccess(true);
            responseObj.setErrorMessage("操作成功");
        } catch (Exception e) {
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("操作失败");
            e.printStackTrace();
        }
        return responseObj;
    }

    /**
     * 课程类别管理上移
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "scoreTypeUp", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject scoreTypeUp(String id) {
        ResponseObject responseObj = new ResponseObject();
        try {
            service.updateScoreTypeUp(id);
            responseObj.setSuccess(true);
            responseObj.setResultObject(service.findScoreType(id));
            responseObj.setErrorMessage("操作成功");
        } catch (Exception e) {
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("操作失败");
        }
        return responseObj;
    }

    /**
     * 下移
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "down", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject down(String id) {
        ResponseObject responseObj = new ResponseObject();
        try {
            service.updateDirectionDown(id);
            responseObj.setSuccess(true);
            responseObj.setErrorMessage("操作成功");
        } catch (Exception e) {
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("操作失败");
        }
        return responseObj;
    }

    /**
     * 课程管理类别下移
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "scoreTypeDown", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject scoreTypeDown(String id) {
        ResponseObject responseObj = new ResponseObject();
        try {
            service.updateScoreTypeDown(id);
            responseObj.setSuccess(true);
            responseObj.setResultObject(service.findScoreType(id));
            responseObj.setErrorMessage("操作成功");
        } catch (Exception e) {
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("操作失败");
        }
        return responseObj;
    }

    /**
     * 保存课程类别到中间表
     *
     * @param menu
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping(value = "addChildren", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject addChildren(ScoreTypeVo scoreTypeVo) throws InvocationTargetException, IllegalAccessException {
        MenuCourseType menuCourseType = new MenuCourseType();
        menuCourseType.setCourseTypeId(scoreTypeVo.getId()); //课程类别id
        menuCourseType.setCreatePerson(ManagerUserUtil.getId()); //当前登录用户id
        service.saveMenuCourseType(menuCourseType);
        return ResponseObject.newSuccessResponseObject(null);
    }

    @RequestMapping(value = "deleteChildren", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deleteChildren(MenuVo menu) throws InvocationTargetException, IllegalAccessException {
        service.deletes(new String[]{menu.getId()});
        return ResponseObject.newSuccessResponseObject(null);
    }


    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject add(MenuVo menu) throws InvocationTargetException, IllegalAccessException {
        ResponseObject responseObj = new ResponseObject();
        Menu entity = new Menu();
        BeanUtils.copyProperties(entity, menu);
        entity.setName(menu.getMenuName());
        entity.setRemark(menu.getRemark());
        if (entity.getName() == null) {
            throw new IllegalArgumentException("请输入分类菜单名");
        }
        Menu existsEntity = service.findMenuByName(entity.getName());
        if (existsEntity != null && existsEntity.isDelete() != true) {
            throw new IllegalArgumentException("已经存在");
        }
        entity.setCreatePerson(ManagerUserUtil.getName());
        entity.setCreateTime(new Date());
        entity.setNumber(service.getMaxNumber(1) + 1);
        entity.setBoSort(service.getMaxBoSort() + 1);
        entity.setLevel("1");
        entity.setStatus(1);

        try {
            service.save(entity);
            responseObj.setSuccess(true);
            responseObj.setErrorMessage("新增学科成功");
        } catch (Exception e) {
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("新增学科失败");
        }
        return responseObj;
    }


    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject update(MenuVo menu) throws InvocationTargetException, IllegalAccessException {
        ResponseObject responseObject = new ResponseObject();
        Menu entity = new Menu();
        BeanUtils.copyProperties(entity, menu);
        entity.setName(menu.getMenuName());
        if (menu == null) {
            throw new IllegalArgumentException("不存在记录");
        }
        Menu searchEntity = service.findById(Integer.parseInt(menu.getId()));
        searchEntity.setName(menu.getMenuName());
        if (service.exists(searchEntity)) {
            throw new IllegalArgumentException("已经存在了");
        }
        Menu me = service.findById(Integer.parseInt(menu.getId()));
        me.setName(entity.getName());
        me.setRemark(entity.getRemark());
        service.update(me);
        responseObject.setSuccess(true);
        responseObject.setErrorMessage("修改成功!");
        return responseObject;
    }

    @RequestMapping(value = "deletes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletes(String ids) throws InvocationTargetException, IllegalAccessException {
        ResponseObject responseObject = new ResponseObject();
        String msg = "";
        if (ids != null) {
            String[] _ids = ids.split(",");
            msg = service.deletes(_ids);
        }
        responseObject.setSuccess(true);
        responseObject.setErrorMessage(msg);
        return responseObject;
    }

    @RequestMapping(value = "/maxSort")
    public ResponseObject getMaxSort() {
        return ResponseObject.newSuccessResponseObject(service.getMaxSort());
    }

}
