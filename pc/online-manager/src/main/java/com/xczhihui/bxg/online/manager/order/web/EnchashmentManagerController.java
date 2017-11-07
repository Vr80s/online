package com.xczhihui.bxg.online.manager.order.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xczhihui.bxg.common.util.DateUtil;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.api.po.EnchashmentApplication;
import com.xczhihui.bxg.online.manager.order.service.EnchashmentService;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;

@Controller
@RequestMapping(value = "/order/enchashmentManager")
public class EnchashmentManagerController{
	
	@Autowired
	private EnchashmentService enchashmentService;
	
	/**
     * 
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView index(){
         ModelAndView mav=new ModelAndView("/order/enchashmentManager");
         return mav;
    }

	@RequiresPermissions("order:menu:enchashment")
    @RequestMapping(value = "/findEnchashmentList")
    @ResponseBody
    public TableVo findEnchashmentList(TableVo tableVo) {
         int pageSize = tableVo.getiDisplayLength();
         int index = tableVo.getiDisplayStart();
         int currentPage = index / pageSize + 1;
         String params = tableVo.getsSearch();
         Groups groups = Tools.filterGroup(params);
         Group startTimeGroup = groups.findByName("startTime");
         Group stopTimeGroup = groups.findByName("stopTime");
         Group orderStatusGroup = groups.findByName("search_orderStatus");
         Group payTypeGroup = groups.findByName("search_payType");
         Group courseNameGroup = groups.findByName("search_courseName");
         Group orderNoGroup = groups.findByName("search_orderNo");
         Group createPersonNameGroup = groups.findByName("search_createPersonName");
         Group orderFromGroup = groups.findByName("order_from");
         
         EnchashmentApplication searchVo=new EnchashmentApplication();
         
         if(startTimeGroup!=null){
             searchVo.setStartTime(DateUtil.parseDate(startTimeGroup.getPropertyValue1().toString(),"yyyy-MM-dd"));
         }

         if(stopTimeGroup!=null){
        	 searchVo.setStopTime(DateUtil.parseDate(stopTimeGroup.getPropertyValue1().toString(),"yyyy-MM-dd"));
         }
        
         if(orderStatusGroup!=null){
              searchVo.setEnchashmentStatus(Integer.parseInt(orderStatusGroup.getPropertyValue1().toString()));
         }
         
         if(payTypeGroup!=null){
        	 searchVo.setEnchashmentAccountType(Integer.parseInt(payTypeGroup.getPropertyValue1().toString()));
         }
         
//         if(courseNameGroup!=null){
//        	 searchVo.setCourseName(courseNameGroup.getPropertyValue1().toString());
//         }
         
         if(orderNoGroup!=null){
        	 searchVo.setId(Integer.parseInt(orderNoGroup.getPropertyValue1().toString()));
         }
         
         if(createPersonNameGroup!=null){
        	 searchVo.setUserId(createPersonNameGroup.getPropertyValue1().toString());
         }
         if(orderFromGroup!=null){
        	 searchVo.setClientType(Integer.valueOf(orderFromGroup.getPropertyValue1().toString()));
         }

         Page<EnchashmentApplication> page = enchashmentService.findEnchashmentPage(searchVo, currentPage, pageSize);

         int total = page.getTotalCount();
         tableVo.setAaData(page.getItems());
         tableVo.setiTotalDisplayRecords(total);
         tableVo.setiTotalRecords(total);
         return tableVo;
    }
	
	@RequestMapping(value = "handleEnchashment", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject handleEnchashment (int id,int enchashmentStatus,String ticklingTime,String operateRemark,String causeType) throws ParseException{
		EnchashmentApplication ea = new EnchashmentApplication();
		ea.setId(id);
		ea.setCauseType(causeType==null?null:Integer.valueOf(causeType));
		ea.setEnchashmentStatus(enchashmentStatus);
		if(enchashmentStatus==1){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
			Date date = sdf.parse(ticklingTime);
			ea.setTicklingTime(date);
		}
	    
		ea.setOperateRemark(operateRemark);
		
		ResponseObject responseObj = new ResponseObject();
		 try{
			enchashmentService.updateHandleEnchashment(ea);
            responseObj.setSuccess(true);
            responseObj.setErrorMessage("修改成功");
	       }catch(Exception e){
	            responseObj.setSuccess(false);
	            responseObj.setErrorMessage("修改失败");
	            e.printStackTrace();
	       }
	        return responseObj;
	}

}
