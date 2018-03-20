package com.xczhihui.bxg.online.manager.cloudClass.web;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.Tools;
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
          Project p = new Project();
          if (searchType != null) {
               p.setType(Integer.parseInt(searchType.getPropertyValue1().toString()));
          }

          Page<Project> page = service.findProjectPage(p, currentPage, pageSize);
          
          if(page.getItems().size()>0){
               for(Project vo:page.getItems()){
                    User user=userService.getUserById(vo.getCreatePerson());
                    if(user!=null) {
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
      * @param project
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
          if(project.getLinkType()==null){
               throw new IllegalArgumentException("请选择连接类型");
          }
          if(project.getLinkCondition()==null){
               throw new IllegalArgumentException("请输入连接地址");
          }
          Project existsEntity = service.findProjectTypeByNameAndByType(project.getName(),project.getType());
          if (existsEntity != null && existsEntity.isDelete()!=true) {
               throw new IllegalArgumentException("已经存在");
          }else if(existsEntity != null && existsEntity.isDelete()==true){
               existsEntity.setLinkType(project.getLinkType());
               existsEntity.setLinkCondition(project.getLinkCondition());
               existsEntity.setIcon(project.getIcon());
               existsEntity.setStatus(0);
               existsEntity.setDelete(false);
               service.update(existsEntity);
               responseObj.setSuccess(true);
               responseObj.setErrorMessage("新增课程专题成功");
               return responseObj;
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
              responseObj.setErrorMessage(e.getMessage());
         }
         return responseObj;
     }
     
     /**
      * 更新课程类别管理表
      * @param project
      * @return
      * @throws InvocationTargetException
      * @throws IllegalAccessException
      */
     @RequestMapping(value = "update", method = RequestMethod.POST)
     @ResponseBody
     public ResponseObject update(Project project) throws InvocationTargetException, IllegalAccessException {
          ResponseObject responseObject=new ResponseObject();
          
          if(project==null || project.getName() == null || project.getIcon() ==null
                  || project.getLinkType() == null || project.getLinkCondition() == null) {
              throw new IllegalArgumentException("请输入必填项");
          }
          Project existsEntity = service.findProjectTypeByNameAndByType(project.getName(),project.getType());
   
          if (existsEntity!=null && !existsEntity.getId().equals(project.getId())) {
               throw new IllegalArgumentException("已经存在了");
          }
          Project pj = service.findById(project.getId().toString());
          pj.setName(project.getName());
          pj.setIcon(project.getIcon());
          pj.setLinkType(project.getLinkType());
          pj.setLinkCondition(project.getLinkCondition());
          service.update(pj);
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
      * @param id
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
