package com.xczhihui.order.web;

import com.xczhihui.order.service.OrderService;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;

import com.xczhihui.bxg.common.util.DateUtil;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.utils.Group;

import com.xczhihui.order.vo.OrderVo;

@Controller
@RequestMapping(value = "/order/order")
public class OrderController{
	
	@Autowired
	private OrderService orderService;
	
	/**
     * 
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView index(){
         ModelAndView mav=new ModelAndView("/order/order");
         return mav;
    }

	//@RequiresPermissions("order:menu:order")
    @RequestMapping(value = "/findOrderList")
    @ResponseBody
    public TableVo findQuestionList(TableVo tableVo) {
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
         
         OrderVo searchVo=new OrderVo();
         
         if(startTimeGroup!=null){
             searchVo.setStartTime(DateUtil.parseDate(startTimeGroup.getPropertyValue1().toString(),"yyyy-MM-dd"));
         }

         if(stopTimeGroup!=null){
        	 searchVo.setStopTime(DateUtil.parseDate(stopTimeGroup.getPropertyValue1().toString(),"yyyy-MM-dd"));
         }
        
         if(orderStatusGroup!=null){
              searchVo.setOrderStatus(Integer.parseInt(orderStatusGroup.getPropertyValue1().toString()));
         }
         
         if(payTypeGroup!=null){
        	 searchVo.setPayType(Integer.parseInt(payTypeGroup.getPropertyValue1().toString()));
         }
         
         if(courseNameGroup!=null){
        	 searchVo.setCourseName(courseNameGroup.getPropertyValue1().toString());
         }
         
         if(orderNoGroup!=null){
        	 searchVo.setOrderNo(orderNoGroup.getPropertyValue1().toString());
         }
         
         if(createPersonNameGroup!=null){
        	 searchVo.setCreatePersonName(createPersonNameGroup.getPropertyValue1().toString());
         }
         if(orderFromGroup!=null){
        	 searchVo.setOrderFrom(Integer.valueOf(orderFromGroup.getPropertyValue1().toString()));
         }

//         if(statusGroup!=null){
//        	 searchVo.setStatus(Integer.parseInt(statusGroup.getPropertyValue1().toString()));
//         }
//         if(contentGroup!=null){
//              searchVo.setContent(contentGroup.getPropertyValue1().toString());
//         }

         Page<OrderVo> page = orderService.findOrderPage(searchVo, currentPage, pageSize);

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
              orderService.deletes(_ids);
         }
         responseObject.setSuccess(true);
         responseObject.setErrorMessage("删除完成!");
         return responseObject;
    }
    
	/**
	 * 优惠方式
	 * @param String orderNo
	 * @return
	 */
	@RequestMapping(value = "/getOrderPreferenty", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObject getOrderPreferenty(String orderNo) {
		ResponseObject responseObj = new ResponseObject();
		responseObj.setSuccess(true);
		responseObj.setResultObject(orderService.getOrderPreferenty(orderNo));
		return responseObj;
	}
}
