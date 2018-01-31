package com.xczhihui.bxg.online.manager.cloudClass.web;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import com.xczhihui.bxg.common.util.DateUtil;
import com.xczhihui.bxg.online.common.domain.ScoreType;
import com.xczhihui.bxg.online.common.domain.User;
import com.xczhihui.bxg.online.manager.user.service.UserService;

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
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.manager.cloudClass.service.CloudClassMenuService;
import com.xczhihui.bxg.online.manager.cloudClass.service.ScoreTypeService;
import com.xczhihui.bxg.online.manager.cloudClass.vo.MenuVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.ScoreTypeVo;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;

/**
 * 菜单控制层实现类
 * @author Rongcai Kang
 */
@RestController()
@RequestMapping(value = "/cloudClass/scoreType")
public class ScoreTypeController {

     @Autowired
     private ScoreTypeService service;
     @Autowired
     private UserService userService;

     @RequestMapping(value = "/index")
     public ModelAndView index(){
          ModelAndView mav=new ModelAndView("cloudClass/scoreType");
          return mav;
     }

     //@RequiresPermissions("cloudClass:menu:scoreType")
     @RequestMapping(value = "list")
     @ResponseBody
     public TableVo list(TableVo tableVo) {
          int pageSize = tableVo.getiDisplayLength();
          int index = tableVo.getiDisplayStart();
          int currentPage = index / pageSize + 1;
          String params = tableVo.getsSearch();
          Groups groups = Tools.filterGroup(params);
//          Group sortGroup = groups.findByName("courseCount");
          Group createPersonGroup = groups.findByName("createPerson");
          Group nameGroup = groups.findByName("name");
          Group time_startGroup = groups.findByName("time_start");
          Group time_endGroup = groups.findByName("time_end");
          ScoreTypeVo searchVo=new ScoreTypeVo();
//          if (sortGroup != null) {
//               searchVo.setOrderCourseCount(sortGroup.getPropertyValue1().toString());
//          }
          if(createPersonGroup!=null){
               searchVo.setCreatePerson(createPersonGroup.getPropertyValue1().toString());
          }
          if(time_startGroup!=null){
               searchVo.setTime_start(DateUtil.parseDate(time_startGroup.getPropertyValue1().toString(), "yyyy-MM-dd"));
          }
          if(time_startGroup!=null){
               searchVo.setTime_end(DateUtil.parseDate(time_endGroup.getPropertyValue1().toString(),"yyyy-MM-dd"));
          }
          if(nameGroup!=null){
               searchVo.setName(nameGroup.getPropertyValue1().toString());
          }
          Page<ScoreTypeVo> page = service.findMenuPage(searchVo, currentPage, pageSize);
          if(page.getItems().size()>0){
               for(ScoreTypeVo vo:page.getItems()){
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
     public ResponseObject add(ScoreTypeVo scoreTypeVo) throws InvocationTargetException, IllegalAccessException {
    	 ResponseObject responseObj = new ResponseObject();
          ScoreType entity=new ScoreType();
          BeanUtils.copyProperties(entity, scoreTypeVo);
          entity.setName(scoreTypeVo.getName());
          entity.setRemark(scoreTypeVo.getRemark());
          if(entity.getName()==null){
               throw new IllegalArgumentException("请输入课程类别名称");
          }
          ScoreType existsEntity = service.findScoreTypeByName(entity.getName());
          if (existsEntity != null && existsEntity.isDelete()!=true) {
               throw new IllegalArgumentException("已经存在");
          }
          entity.setCreatePerson(UserHolder.getCurrentUser().getName());
          entity.setCreateTime(new Date());
          entity.setSort(service.getMaxSort()+1);
          entity.setStatus(0);
          try{
        	  service.save(entity);
              responseObj.setSuccess(true);
              responseObj.setErrorMessage("新增课程类别成功");
         }catch(Exception e){
              responseObj.setSuccess(false);
              responseObj.setErrorMessage("新增课程类别失败");
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
     public ResponseObject update(ScoreTypeVo scoreTypeVo) throws InvocationTargetException, IllegalAccessException {
          ResponseObject responseObject=new ResponseObject();
          ScoreType entity=new ScoreType();
          BeanUtils.copyProperties(entity,scoreTypeVo);
          entity.setName(scoreTypeVo.getName());
          if(scoreTypeVo==null) {
              throw new IllegalArgumentException("不存在记录");
          }
          ScoreType searchEntity=service.findById(scoreTypeVo.getId());
          searchEntity.setName(scoreTypeVo.getName());
          if (service.exists(searchEntity)) {
               throw new IllegalArgumentException("已经存在了");
          }
          ScoreType me=service.findById(scoreTypeVo.getId());
          me.setName(entity.getName());
          me.setRemark(entity.getRemark());
          service.update(me);
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
               responseObj.setErrorMessage("操作成功");
          }catch(Exception e){
               responseObj.setSuccess(false);
               responseObj.setErrorMessage("操作失败");
          }
          return responseObj;
     }

}
