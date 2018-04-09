package com.xczhihui.order.web;

import java.text.ParseException;
import java.util.List;

import com.xczhihui.bxg.online.common.domain.EnchashmentApplyInfo;
import com.xczhihui.bxg.common.util.enums.EnchashmentDismissal;
import com.xczhihui.order.service.EnchashmentService;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.common.util.DateUtil;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;

import javax.servlet.http.HttpServletRequest;

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
    public String index(HttpServletRequest request){
        List<EnchashmentDismissal> enchashmentDismissals = EnchashmentDismissal.getDismissalList();
        request.setAttribute("enchashmentDismissals", enchashmentDismissals);
        return "/order/enchashmentManager";
    }

	//@RequiresPermissions("order:menu:enchashment")
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
         Group orderNoGroup = groups.findByName("search_orderNo");
         Group createPersonNameGroup = groups.findByName("search_createPersonName");
         Group anthorTypeGroup = groups.findByName("anthor_type");

        EnchashmentApplyInfo searchVo=new EnchashmentApplyInfo();
         
         if(startTimeGroup!=null){
             searchVo.setStartTime(DateUtil.parseDate(startTimeGroup.getPropertyValue1().toString(),"yyyy-MM-dd"));
         }

         if(stopTimeGroup!=null){
        	 searchVo.setStopTime(DateUtil.parseDate(stopTimeGroup.getPropertyValue1().toString(),"yyyy-MM-dd"));
         }
        
         if(orderStatusGroup!=null){
              searchVo.setStatus(Integer.parseInt(orderStatusGroup.getPropertyValue1().toString()));
         }

         if(orderNoGroup!=null){
        	 searchVo.setId(orderNoGroup.getPropertyValue1().toString());
         }
         
         if(createPersonNameGroup!=null){
        	 searchVo.setUserId(createPersonNameGroup.getPropertyValue1().toString());
         }

         if(anthorTypeGroup!=null){
             searchVo.setAnthorType(Integer.valueOf(anthorTypeGroup.getPropertyValue1().toString()));
         }

         Page<EnchashmentApplyInfo> page = enchashmentService.findEnchashmentPage(searchVo, currentPage, pageSize);

         int total = page.getTotalCount();
         tableVo.setAaData(page.getItems());
         tableVo.setiTotalDisplayRecords(total);
         tableVo.setiTotalRecords(total);
         return tableVo;
    }
	
	@RequestMapping(value = "handleEnchashment", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject handleEnchashment (String id,int status,Integer dismissal,String dismissalRemark) throws ParseException{
        EnchashmentApplyInfo eai = new EnchashmentApplyInfo();
		eai.setId(id);
        eai.setStatus(status);
        eai.setDismissal(dismissal);
        eai.setDismissalRemark(dismissalRemark);
        enchashmentService.updateHandleEnchashment(eai);
        ResponseObject responseObj = new ResponseObject();
        responseObj.setSuccess(true);
        responseObj.setErrorMessage("修改成功");
        return responseObj;
	}

}
