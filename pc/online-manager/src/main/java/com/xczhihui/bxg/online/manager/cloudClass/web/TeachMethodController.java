package com.xczhihui.bxg.online.manager.cloudClass.web;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
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
import com.xczhihui.bxg.online.common.domain.TeachMethod;
import com.xczhihui.bxg.online.common.domain.User;
import com.xczhihui.bxg.online.manager.cloudClass.service.TeachMethodService;
import com.xczhihui.bxg.online.manager.cloudClass.vo.TeachMethodVo;
import com.xczhihui.bxg.online.manager.user.service.UserService;
import com.xczhihui.bxg.online.manager.utils.TableVo;

/**
 * 菜单控制层实现类
 * @author Rongcai Kang
 */
@RestController()
@RequestMapping(value = "/cloudClass/teachMethod")
public class TeachMethodController {

     @Autowired
     private TeachMethodService teachMethodService;
     @Autowired
     private UserService userService;

     @RequestMapping(value = "/index")
     public ModelAndView index(){
          ModelAndView mav=new ModelAndView("cloudClass/teachMethod");
          return mav;
     }

     @RequiresPermissions("cloudClass:menu:teachMethod")
     @RequestMapping(value = "list")
     @ResponseBody
     public TableVo list(TableVo tableVo) {
          int pageSize = tableVo.getiDisplayLength();
          int index = tableVo.getiDisplayStart();
          int currentPage = index / pageSize + 1;
          TeachMethodVo teachMethodVo = new TeachMethodVo();
          Page<TeachMethodVo> page = teachMethodService.findTeachMethodPage(teachMethodVo, currentPage, pageSize);
          if(page.getItems().size()>0){
               for(TeachMethodVo vo:page.getItems()){
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
      * @param scoreTypeVo
      * @return
      * @throws InvocationTargetException
      * @throws IllegalAccessException
      */
     @RequestMapping(value = "add", method = RequestMethod.POST)
     @ResponseBody
     public ResponseObject add(TeachMethodVo teachMethodVo) throws InvocationTargetException, IllegalAccessException {
    	 ResponseObject responseObj = new ResponseObject();
    	 TeachMethod entity=new TeachMethod();
          BeanUtils.copyProperties(entity, teachMethodVo);
          entity.setName(teachMethodVo.getName());
          entity.setRemark(teachMethodVo.getRemark());
          if(entity.getName()==null){
               throw new IllegalArgumentException("请输入授课方式名称");
          }
          List<TeachMethod> existsEntitys = teachMethodService.findTeachMethodByName(entity.getName());
          for(TeachMethod existsEntity :existsEntitys){
        	  if (existsEntity != null&&existsEntity.isDelete()!=true) {
        		  throw new IllegalArgumentException("已经存在");
        	  }
          }
          entity.setCreatePerson(UserHolder.getCurrentUser().getName());
          entity.setCreateTime(new Date());
          entity.setSort(teachMethodService.getMaxSort()+1);
          entity.setStatus(false);
          
          try{
        	  teachMethodService.save(entity);
              responseObj.setSuccess(true);
              responseObj.setErrorMessage("新增授课方式成功");
         }catch(Exception e){
              responseObj.setSuccess(false);
              responseObj.setErrorMessage("新增授课方式失败");
         }
          
          return responseObj;
     }
     
     /**
      * 更新课程类别管理表
      * @param menu
      * @return
      * @throws InvocationTargetException
      * @throws IllegalAccessException
      */
     @RequestMapping(value = "update", method = RequestMethod.POST)
     @ResponseBody
     public ResponseObject update(TeachMethodVo teachMethodVo) throws InvocationTargetException, IllegalAccessException {
          ResponseObject responseObject=new ResponseObject();
          TeachMethod entity=new TeachMethod();
          BeanUtils.copyProperties(entity,teachMethodVo);
          entity.setName(teachMethodVo.getName());
          if(teachMethodVo==null) {
              throw new IllegalArgumentException("不存在记录");
          }
          TeachMethod searchEntity=teachMethodService.findById(teachMethodVo.getId());
          searchEntity.setName(teachMethodVo.getName());
          if (teachMethodService.exists(searchEntity)) {
               throw new IllegalArgumentException("已经存在了");
          }
          TeachMethod me=teachMethodService.findById(teachMethodVo.getId());
          me.setName(entity.getName());
          me.setRemark(entity.getRemark());
          teachMethodService.update(me);
          responseObject.setSuccess(true);
          responseObject.setErrorMessage("更新授课方式成功!");
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
          ResponseObject responseObject=new ResponseObject();
          if(ids!=null) {
               String[] _ids = ids.split(",");
               teachMethodService.deletes(_ids);
          }
          responseObject.setSuccess(true);
          responseObject.setErrorMessage("删除成功!");
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
        	  teachMethodService.updateStatus(id);
               responseObj.setSuccess(true);
               responseObj.setErrorMessage("操作成功");
          }catch(Exception e){
               responseObj.setSuccess(false);
               responseObj.setErrorMessage("操作失败");
          }
          return responseObj;
     }

}
