package com.xczhihui.medical.web;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.common.domain.MedicalField;
import com.xczhihui.bxg.online.common.domain.User;
import com.xczhihui.medical.service.FieldService;
import com.xczhihui.support.shiro.ManagerUserUtil;
import com.xczhihui.user.service.UserService;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

/**
 * 菜单控制层实现类
 * @author Rongcai Kang
 */
@RestController()
@RequestMapping(value = "/medical/field")
public class FieldController {

     @Autowired
     private FieldService service;
     @Autowired
     private UserService userService;

     @RequestMapping(value = "/index")
     public ModelAndView index(){
          ModelAndView mav=new ModelAndView("/medical/field");
          return mav;
     }

     @RequestMapping(value = "list")
     @ResponseBody
     public TableVo list(TableVo tableVo) {
          int pageSize = tableVo.getiDisplayLength();
          int index = tableVo.getiDisplayStart();
          int currentPage = index / pageSize + 1;
          String params = tableVo.getsSearch();
          Groups groups = Tools.filterGroup(params);
          Group createPersonGroup = groups.findByName("createPerson");
          Group nameGroup = groups.findByName("name");
          Group time_startGroup = groups.findByName("time_start");
          Group time_endGroup = groups.findByName("time_end");
          MedicalField searchVo=new MedicalField();
          if(createPersonGroup!=null){
               searchVo.setCreatePerson(createPersonGroup.getPropertyValue1().toString());
          }
//          if(time_startGroup!=null){
//               searchVo.setTime_start(DateUtil.parseDate(time_startGroup.getPropertyValue1().toString(), "yyyy-MM-dd"));
//          }
//          if(time_endGroup!=null){
//               searchVo.setTime_end(DateUtil.parseDate(time_endGroup.getPropertyValue1().toString(),"yyyy-MM-dd"));
//          }
          if(nameGroup!=null){
               searchVo.setName(nameGroup.getPropertyValue1().toString());
          }
          Page<MedicalField> page = service.findMenuPage(searchVo, currentPage, pageSize);
          if(page.getItems().size()>0){
               for(MedicalField vo:page.getItems()){
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

     @RequestMapping(value = "alllist")
     @ResponseBody
     public List<MedicalField> alllist(String id,Integer type) {

          List<MedicalField> allField = service.findAllField(id,type);

          return allField;
     }
     
     /**
      * 添加数据
      * @param MedicalField
      * @return
      * @throws InvocationTargetException
      * @throws IllegalAccessException
      */
     @RequestMapping(value = "add", method = RequestMethod.POST)
     @ResponseBody
     public ResponseObject add(MedicalField MedicalField) throws InvocationTargetException, IllegalAccessException {
    	 ResponseObject responseObj = new ResponseObject();
          MedicalField entity=new MedicalField();
          BeanUtils.copyProperties(entity, MedicalField);
          entity.setName(MedicalField.getName());
          entity.setRemark(MedicalField.getRemark());
          if(entity.getName()==null){
               throw new IllegalArgumentException("请输入医疗领域名称");
          }
          MedicalField existsEntity = service.findMedicalFieldByName(entity.getName());
          if (existsEntity != null && existsEntity.getDeleted()!=true) {
               throw new IllegalArgumentException("已经存在");
          }
          entity.setCreatePerson(ManagerUserUtil.getName());
          entity.setCreateTime(new Date());
//          entity.setSort(service.getMaxSort()+1);
//          entity.setStatus(false);
          service.save(entity);
          responseObj.setSuccess(true);
          responseObj.setErrorMessage("新增医疗领域成功");
         return responseObj;
     }
     
     /**
      * 更新医疗领域管理表
      * @param menu
      * @return
      * @throws InvocationTargetException
      * @throws IllegalAccessException
      */
     @RequestMapping(value = "update", method = RequestMethod.POST)
     @ResponseBody
     public ResponseObject update(MedicalField MedicalField) throws InvocationTargetException, IllegalAccessException {
          ResponseObject responseObject=new ResponseObject();
          MedicalField entity=new MedicalField();
          BeanUtils.copyProperties(entity,MedicalField);
          entity.setName(MedicalField.getName());
          if(MedicalField==null) {
              throw new IllegalArgumentException("不存在记录");
          }
          MedicalField searchEntity=service.findById(MedicalField.getId());
          searchEntity.setName(MedicalField.getName());
          if (service.exists(searchEntity)) {
               throw new IllegalArgumentException("已经存在了");
          }
          MedicalField me=service.findById(MedicalField.getId());
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



     @RequestMapping(value = "addHospitalField", method = RequestMethod.POST)
     @ResponseBody
     public ResponseObject addHospitalField(String id,String[] fieldId) {
          ResponseObject responseObject=new ResponseObject();
          service.addHospitalField(id,fieldId);
          responseObject.setSuccess(true);
          responseObject.setResultObject("医疗领域配置成功！");
          return responseObject;
     }

     @RequestMapping(value = "addDoctorField", method = RequestMethod.POST)
     @ResponseBody
     public ResponseObject addDoctorField(String id,String[] fieldId) {
          ResponseObject responseObject=new ResponseObject();
          service.addDoctorField(id,fieldId);
          responseObject.setSuccess(true);
          responseObject.setResultObject("医疗领域配置成功！");
          return responseObject;
     }

}
