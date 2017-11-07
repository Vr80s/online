package com.xczhihui.bxg.online.manager.activity.web;

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
import com.xczhihui.bxg.online.manager.activity.service.ActivityDetailService;
import com.xczhihui.bxg.online.manager.activity.vo.ActivityRuleVo;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;

@Controller
@RequestMapping(value="/activity/activityDetail")
public class ActivityDetailController {
	@Autowired
	private ActivityDetailService activityDetailService;
	
    @RequestMapping(value = "/index")
    public ModelAndView index(){
         ModelAndView mav=new ModelAndView("/activity/activityDetail");
         return mav;
    }
    @RequiresPermissions("activity:menu:detail")
    @RequestMapping(value = "/findActivityDetailList", method = RequestMethod.POST)
    @ResponseBody
    public TableVo findActivityDetailList(TableVo tableVo) {
         int pageSize = tableVo.getiDisplayLength();
         int index = tableVo.getiDisplayStart();
         int currentPage = index / pageSize + 1;
         String params = tableVo.getsSearch();
         Groups groups = Tools.filterGroup(params);
         Group nameGroup = groups.findByName("search_name");
         Group ruleStartTimeGroup = groups.findByName("search_ruleStartTime");
         Group ruleEndTimeGroup = groups.findByName("search_ruleEndTime");
         
         ActivityRuleVo searchVo=new ActivityRuleVo();
         if(nameGroup!=null){
             searchVo.setName(nameGroup.getPropertyValue1().toString());
        }
        if(ruleStartTimeGroup!=null){
             searchVo.setStartTime(DateUtil.parseDate(ruleStartTimeGroup.getPropertyValue1().toString(),"yyyy-MM-dd"));
        }
        if(ruleEndTimeGroup!=null){
       	 searchVo.setEndTime(DateUtil.parseDate(ruleEndTimeGroup.getPropertyValue1().toString(),"yyyy-MM-dd"));
        }

         
         Page<ActivityRuleVo> page = activityDetailService.findActivityDetailPage(searchVo, currentPage, pageSize);

         int total = page.getTotalCount();
         tableVo.setAaData(page.getItems());
         tableVo.setiTotalDisplayRecords(total);
         tableVo.setiTotalRecords(total);
         return tableVo;
    }
	/**
	 * 查看各规则数据
	 * @param 
	 * @return
	 */
	@RequestMapping(value ="/getActivityDetailPreferenty", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObject getActivityDetailPreferenty(String actId) {
		ResponseObject responseObj = new ResponseObject();
		responseObj.setSuccess(true);
		responseObj.setResultObject(activityDetailService.getActivityDetailPreferenty(actId));
		return responseObj;
	}
	/**
	 * 查看课程详情数据
	 * @param 
	 * @return
	 */
	@RequestMapping(value ="/getActivityDetailCourse", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObject getActivityDetailCourse(String actId) {
		ResponseObject responseObj = new ResponseObject();
		responseObj.setSuccess(true);
		responseObj.setResultObject(activityDetailService.getActivityDetailCourse(actId));
		return responseObj;
	}
}
	

	


