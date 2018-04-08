package com.xczhihui.order.web;

import com.xczhihui.order.service.ShareOrderService;
import com.xczhihui.order.vo.ShareOrderVo;
import com.xczhihui.utils.Group;
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

@Controller
@RequestMapping(value = "/order/shareOrder")
public class ShareOrderController{
	
	@Autowired
	private ShareOrderService shareOrderService;
	
	/**
     * 
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView index(){
         ModelAndView mav=new ModelAndView("/order/shareOrder");
         return mav;
    }

	//@RequiresPermissions("order:menu:share")
    @RequestMapping(value = "/findShareOrderList", method = RequestMethod.POST)
    @ResponseBody
    public TableVo findShareOrderList(TableVo tableVo) {
         int pageSize = tableVo.getiDisplayLength();
         int index = tableVo.getiDisplayStart();
         int currentPage = index / pageSize + 1;
         String params = tableVo.getsSearch();
         Groups groups = Tools.filterGroup(params);
         Group mobileGroup = groups.findByName("search_mobile");
         Group emailGroup = groups.findByName("search_email");
         Group createPersonNameGroup = groups.findByName("search_createPersonName");
         Group sharePersonNameGroup = groups.findByName("search_sharePersonName");
         Group startTimeGroup = groups.findByName("startTime");
         Group stopTimeGroup = groups.findByName("stopTime");
         
         ShareOrderVo searchVo=new ShareOrderVo();
         
         if(mobileGroup!=null){
              searchVo.setMobile(mobileGroup.getPropertyValue1().toString());
         }
         if(emailGroup!=null){
        	 searchVo.setEmail(emailGroup.getPropertyValue1().toString());
         }
         if(createPersonNameGroup!=null){
              searchVo.setCreatePersonName(createPersonNameGroup.getPropertyValue1().toString());
         }
         if(sharePersonNameGroup!=null){
        	 searchVo.setShareUserName(sharePersonNameGroup.getPropertyValue1().toString());
         }
         if(startTimeGroup!=null){
              searchVo.setStartTime(DateUtil.parseDate(startTimeGroup.getPropertyValue1().toString(),"yyyy-MM-dd"));
         }
         if(stopTimeGroup!=null){
        	 searchVo.setStopTime(DateUtil.parseDate(stopTimeGroup.getPropertyValue1().toString(),"yyyy-MM-dd"));
         }
         Page<ShareOrderVo> page = shareOrderService.findShareOrderPage(searchVo, currentPage, pageSize);

         int total = page.getTotalCount();
         tableVo.setAaData(page.getItems());
         tableVo.setiTotalDisplayRecords(total);
         tableVo.setiTotalRecords(total);
         return tableVo;
    }
	
	/**
     * 
     * @return
     */
    @RequestMapping(value = "/shareOrderDetail")
    public ModelAndView shareOrderDetail(){
         ModelAndView mav=new ModelAndView("/order/shareOrderDetail");
         return mav;
    }

	//@RequiresPermissions("order:menu:share")
    @RequestMapping(value = "/findShareOrderListDeatil", method = RequestMethod.POST)
    @ResponseBody
    public TableVo findShareOrderListDeatil(TableVo tableVo) {
         int pageSize = tableVo.getiDisplayLength();
         int index = tableVo.getiDisplayStart();
         int currentPage = index / pageSize + 1;
         String params = tableVo.getsSearch();
         Groups groups = Tools.filterGroup(params);
         Group targetUserIdGroup = groups.findByName("search_targetUserId");
         Group shareOrderNoGroup = groups.findByName("search_shareOrderNo");
         Group courseNameGroup = groups.findByName("search_courseName");
         Group createPersonNameGroup = groups.findByName("search_createPersonName");
         Group startTimeGroup = groups.findByName("startTime");
         Group stopTimeGroup = groups.findByName("stopTime");
         Group levelGroup = groups.findByName("level");
         
         ShareOrderVo searchVo=new ShareOrderVo();
         
         if(targetUserIdGroup!=null){
              searchVo.setTargetUserId(targetUserIdGroup.getPropertyValue1().toString());
         }
         if(shareOrderNoGroup!=null){
        	 searchVo.setShareOrderNo(shareOrderNoGroup.getPropertyValue1().toString());
         }
         if(courseNameGroup!=null){
        	 searchVo.setCourseName(courseNameGroup.getPropertyValue1().toString());
         }
         if(createPersonNameGroup!=null){
              searchVo.setCreatePersonName(createPersonNameGroup.getPropertyValue1().toString());
         }
         if(startTimeGroup!=null){
              searchVo.setStartTime(DateUtil.parseDate(startTimeGroup.getPropertyValue1().toString(),"yyyy-MM-dd"));
         }
         if(stopTimeGroup!=null){
        	 searchVo.setStopTime(DateUtil.parseDate(stopTimeGroup.getPropertyValue1().toString(),"yyyy-MM-dd"));
         }
         if(levelGroup!=null){
        	 searchVo.setLevel(Integer.parseInt(levelGroup.getPropertyValue1().toString()));
         }
         Page<ShareOrderVo> page = shareOrderService.findShareOrderDetailPage(searchVo, currentPage, pageSize);

         int total = page.getTotalCount();
         tableVo.setAaData(page.getItems());
         tableVo.setiTotalDisplayRecords(total);
         tableVo.setiTotalRecords(total);
         return tableVo;
    }
	
	//@RequiresPermissions("order:menu:share")
    @RequestMapping(value = "/getOrderDetailList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject getOrderDetailList(ShareOrderVo shareOrderVo) {
		ResponseObject responseObj = new ResponseObject();
		responseObj.setSuccess(true);
		responseObj.setResultObject(shareOrderService.getOrderDetailList(shareOrderVo));
        return responseObj;
    }

	//@RequiresPermissions("order:menu:share")
	@RequestMapping(value = "/getShareOrderDetail", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObject getShareOrderDetail(ShareOrderVo shareOrderVo) {
		ResponseObject responseObj = new ResponseObject();
		responseObj.setSuccess(true);
		responseObj.setResultObject(shareOrderService.getShareOrderDetail(shareOrderVo));
		return responseObj;
	}
}
