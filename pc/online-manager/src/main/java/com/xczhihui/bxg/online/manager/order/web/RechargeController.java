package com.xczhihui.bxg.online.manager.order.web;

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
import com.xczhihui.bxg.online.api.po.UserCoinIncrease;
import com.xczhihui.bxg.online.manager.order.service.RechargeService;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;

@Controller
@RequestMapping(value = "/recharge")
public class RechargeController{
	
	@Autowired
	private RechargeService rechargeService;
	
	/**
     * 
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView index(){
         ModelAndView mav=new ModelAndView("/order/recharge");
         return mav;
    }

	@RequiresPermissions("order:menu:order")
    @RequestMapping(value = "/findRechargeList")
    @ResponseBody
    public TableVo findRechargeList(TableVo tableVo) {
         int pageSize = tableVo.getiDisplayLength();
         int index = tableVo.getiDisplayStart();
         int currentPage = index / pageSize + 1;
         String params = tableVo.getsSearch();
         Groups groups = Tools.filterGroup(params);

         
         Group startTimeGroup = groups.findByName("startTime");
         Group stopTimeGroup = groups.findByName("stopTime");
         Group payTypeGroup = groups.findByName("search_payType");
         Group orderNoGroup = groups.findByName("search_orderNo");
         Group orderFromGroup = groups.findByName("order_from");
         Group createPersonNameGroup = groups.findByName("search_createPersonName");
         
         UserCoinIncrease searchVo=new UserCoinIncrease();
         
         if(startTimeGroup!=null){
             searchVo.setStartTime(DateUtil.parseDate(startTimeGroup.getPropertyValue1().toString(),"yyyy-MM-dd"));
         }

         if(stopTimeGroup!=null){
        	 searchVo.setStopTime(DateUtil.parseDate(stopTimeGroup.getPropertyValue1().toString(),"yyyy-MM-dd"));
         }
        
         if(payTypeGroup!=null){
        	 searchVo.setPayType(Integer.parseInt(payTypeGroup.getPropertyValue1().toString()));
         }
         
         if(orderNoGroup!=null){
        	 searchVo.setOrderNoRecharge(orderNoGroup.getPropertyValue1().toString());
         }
         
         if(orderFromGroup!=null){
        	 searchVo.setOrderFrom(Integer.valueOf(orderFromGroup.getPropertyValue1().toString()));
         }
         
         if(createPersonNameGroup!=null){
        	 searchVo.setUserId(createPersonNameGroup.getPropertyValue1().toString());
         }

         Page<UserCoinIncrease> page = rechargeService.findUserCoinIncreasePage(searchVo, currentPage, pageSize);

         int total = page.getTotalCount();
         tableVo.setAaData(page.getItems());
         tableVo.setiTotalDisplayRecords(total);
         tableVo.setiTotalRecords(total);
         return tableVo;
    }

	/**
	 * 批量逻辑删除
	 * @param Integer id
	 * @return
	 */
    @RequestMapping(value = "deletes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletes(String ids) {
         ResponseObject responseObject=new ResponseObject();
         if(ids!=null) {
              String[] _ids = ids.split(",");
              rechargeService.deletes(_ids);
         }
         responseObject.setSuccess(true);
         responseObject.setErrorMessage("删除完成!");
         return responseObject;
    }
    

}
