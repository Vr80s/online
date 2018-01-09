package com.xczhihui.bxg.online.manager.cloudClass.web;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.auth.UserHolder;
import com.xczhihui.bxg.online.common.domain.Project;
import com.xczhihui.bxg.online.common.domain.User;
import com.xczhihui.bxg.online.manager.cloudClass.service.ProjectTypeService;
import com.xczhihui.bxg.online.manager.user.service.UserService;
import com.xczhihui.bxg.online.manager.utils.TableVo;

/**
 * 菜单控制层实现类
 * @author Rongcai Kang
 */
@RestController()
@RequestMapping(value = "/cloudClass/projectType")
public class ProjectTypeController {

     @Autowired
     private ProjectTypeService service;
     @Autowired
     private UserService userService;

     @RequestMapping(value = "/index")
     public ModelAndView index(){
          ModelAndView mav=new ModelAndView("cloudClass/projectType");
          return mav;
     }

     @RequestMapping(value = "list")
     @ResponseBody
     public TableVo list(TableVo tableVo) {
          int pageSize = tableVo.getiDisplayLength();
          int index = tableVo.getiDisplayStart();
          int currentPage = index / pageSize + 1;
          
          Page<Project> page = service.findProjectPage(null, currentPage, pageSize);
          
          if(page.getItems().size()>0){
               for(Project vo:page.getItems()){
                    User user=userService.getUserById(vo.getCreatePerson());
                    if(user!=null)
                         vo.setCreatePerson(user.getName());
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
      * @param projectTypeVo
      * @return
      * @throws InvocationTargetException
      * @throws IllegalAccessException
      */
     @RequestMapping(value = "add", method = RequestMethod.POST)
     @ResponseBody
     public ResponseObject add(Project project) throws InvocationTargetException, IllegalAccessException {
    	  ResponseObject responseObj = new ResponseObject();
    	  
    	  System.out.println(project.getName());
    	  System.out.println(project.getIcon());
          if(project.getName()==null){
               throw new IllegalArgumentException("请输入课程类别名称");
          }
          Project existsEntity = service.findProjectTypeByName(project.getName());
          if (existsEntity != null && existsEntity.isDelete()!=true) {
               throw new IllegalArgumentException("已经存在");
          }
          project.setCreatePerson(UserHolder.getCurrentUser().getName());
          project.setCreateTime(new Date());
          project.setSort(service.getMaxSort()+1);
          project.setStatus(0);
          try{
        	  service.save(project);
              responseObj.setSuccess(true);
              responseObj.setErrorMessage("新增课程专题成功");
         }catch(Exception e){
              responseObj.setSuccess(false);
              responseObj.setErrorMessage("新增课程专题失败");
         }
         return responseObj;
     }
     
     /**
      * 更新课程类别管理表
      * @param Project
      * @return
      * @throws InvocationTargetException
      * @throws IllegalAccessException
      */
     @RequestMapping(value = "update", method = RequestMethod.POST)
     @ResponseBody
     public ResponseObject update(Project project) throws InvocationTargetException, IllegalAccessException {
          ResponseObject responseObject=new ResponseObject();
          
          if(project==null || project.getName() == null || project.getIcon() ==null)
              throw new IllegalArgumentException("请输入必填项");
          Project existsEntity = service.findProjectTypeByName(project.getName());
   
          if (existsEntity!=null && !existsEntity.getId().equals(project.getId())) {
               throw new IllegalArgumentException("已经存在了");
          }
          existsEntity.setName(project.getName());
          existsEntity.setIcon(project.getIcon());
          service.update(existsEntity);
          responseObject.setSuccess(true);
          responseObject.setErrorMessage("修改完成!");
          return responseObject;
     }
     
     /**
      * 删除数据
      * @param ids
      * @return
      * @throws InvocationTargetException
      * @throws IllegalAccessException
      */
     @RequestMapping(value = "deletes", method = RequestMethod.POST)
     @ResponseBody
     public ResponseObject deletes(String ids) throws InvocationTargetException, IllegalAccessException {
    	 String msg = "";
          ResponseObject responseObject=new ResponseObject();
          if(ids!=null) {
               String[] _ids = ids.split(",");
               msg = service.deletes(_ids);
          }
          responseObject.setSuccess(true);
          responseObject.setErrorMessage(msg);
          return responseObject;
     }
     
     /**
      * 禁用or启用
      * @param request
      * @param model
      * @return
      */
     @RequestMapping(value = "updateStatus", method = RequestMethod.POST)
     @ResponseBody
     public ResponseObject updateStatus(String id) {
          ResponseObject responseObj = new ResponseObject();
          try{
               service.updateStatus(id);
               responseObj.setSuccess(true);
               responseObj.setResultObject("操作成功");
          }catch(Exception e){
               responseObj.setSuccess(false);
               responseObj.setErrorMessage("操作失败");
          }
          return responseObj;
     }

 	/**
      * 上移
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
