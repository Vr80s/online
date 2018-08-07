package com.xczhihui.course.web;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.xczhihui.bxg.online.common.domain.DoctorType;
import com.xczhihui.bxg.online.common.domain.User;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.course.service.DoctorTypeService;
import com.xczhihui.support.shiro.ManagerUserUtil;
import com.xczhihui.user.service.UserService;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;

/**
 * 菜单控制层实现类
 *
 * @author Rongcai Kang
 */
@RestController()
@RequestMapping(value = "/medical/doctorType")
public class DoctorTypeController {

    @Autowired
    private DoctorTypeService service;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("medical/doctorType");
        return mav;
    }

    /**
     * 我们在增加线下课时，然后把这个城市同步到那边。我们在那边
     */

    @RequestMapping(value = "list")
    @ResponseBody
    public TableVo list(TableVo tableVo) {
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);
        Group searchType = groups.findByName("search_type");
        DoctorType p = new DoctorType();

        Page<DoctorType> page = service.findDoctorTypePage(p, currentPage, pageSize);

        if (page.getItems().size() > 0) {
            for (DoctorType vo : page.getItems()) {
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
     * 添加数据
     *
     * @param doctorType
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject add(DoctorType doctorType)
            throws InvocationTargetException, IllegalAccessException {
        ResponseObject responseObj = new ResponseObject();

       
        DoctorType existsEntity = service.finddoctorTypeTypeByName(
                doctorType.getTitle());
        
        if (existsEntity != null && existsEntity.isDelete() != true) {
            throw new IllegalArgumentException("已经存在");
            
        } else if (existsEntity != null && existsEntity.isDelete() == true) {
        	
            existsEntity.setIcon(doctorType.getIcon());
            existsEntity.setStatus(0);
            existsEntity.setDelete(false);
            service.update(existsEntity);
            responseObj.setSuccess(true);
            responseObj.setErrorMessage("新增医师类别成功");
            return responseObj;
        }
        
        doctorType.setCreatePerson(ManagerUserUtil.getName());
        doctorType.setCreateTime(new Date());
        doctorType.setSort(service.getMaxSort() + 1);
        doctorType.setStatus(0);
        try {
            service.save(doctorType);
            responseObj.setSuccess(true);
            responseObj.setErrorMessage("新增医师类别成功");
        } catch (Exception e) {
            responseObj.setSuccess(false);
            responseObj.setErrorMessage(e.getMessage());
        }
        return responseObj;
    }

    /**
     * 更新课程类别管理表
     *
     * @param doctorType
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject update(DoctorType doctorType)
            throws InvocationTargetException, IllegalAccessException {
        ResponseObject responseObject = new ResponseObject();

   
        DoctorType existsEntity = service.finddoctorTypeTypeByName(
                doctorType.getTitle());

        if (existsEntity != null  && !existsEntity.getId().equals(doctorType.getId())) {
            throw new IllegalArgumentException("已经存在了");
        }
        DoctorType pj = service.findById(doctorType.getId().toString());
        pj.setTitle(doctorType.getTitle());
        pj.setIcon(doctorType.getIcon());
        service.update(pj);
        responseObject.setSuccess(true);
        responseObject.setErrorMessage("修改完成!");
        return responseObject;
    }

    /**
     * 删除数据
     *
     * @param ids
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping(value = "deletes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletes(String ids) throws InvocationTargetException,
            IllegalAccessException {
        String msg = "";
        ResponseObject responseObject = new ResponseObject();
        if (ids != null) {
            String[] _ids = ids.split(",");
            msg = service.deletes(_ids);
        }
        responseObject.setSuccess(true);
        responseObject.setErrorMessage(msg);
        return responseObject;
    }

    /**
     * 禁用or启用
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateStatus(String id) {
        ResponseObject responseObj = new ResponseObject();
        try {
            service.updateStatus(id);
            responseObj.setSuccess(true);
            responseObj.setResultObject("操作成功");
        } catch (Exception e) {
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("操作失败");
        }
        return responseObj;
    }

    /**
     * 上移
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "upMove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject upMove(Integer id) {
        ResponseObject responseObj = new ResponseObject();
        service.updateSortUp(id);
        responseObj.setSuccess(true);
        return responseObj;
    }

    /**
     * 下移
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "downMove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject downMove(Integer id) {
        ResponseObject responseObj = new ResponseObject();
        service.updateSortDown(id);
        responseObj.setSuccess(true);
        return responseObj;
    }

}
