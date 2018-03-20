package com.xczhihui.bxg.online.manager.activity.web;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;


import com.xczhihui.bxg.common.util.DateUtil;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.manager.cloudClass.service.CourseService;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.StringUtil;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.manager.activity.service.ActivityRuleService;
import com.xczhihui.bxg.online.manager.activity.vo.ActivityRuleVo;

@Controller
@RequestMapping(value = "/activity/activityRule")
public class ActivityRuleController{
	
	@Autowired
	private ActivityRuleService activityRuleService;
	
	@Autowired
	private CourseService courseService;
	
	/**
     * 
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView index(HttpServletRequest request){
         ModelAndView mav=new ModelAndView("/activity/activityRule");
         List<Menu> menuVos= courseService.getfirstMenus(null);
 		 request.setAttribute("menuVo", menuVos);
         return mav;
    }

	//@RequiresPermissions("activity:menu:rule")
    @RequestMapping(value = "/findActivityRuleList", method = RequestMethod.POST)
    @ResponseBody
    public TableVo findActivityRuleList(TableVo tableVo) {
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

         Page<ActivityRuleVo> page = activityRuleService.findActivityRulePage(searchVo, currentPage, pageSize);

         int total = page.getTotalCount();
         tableVo.setAaData(page.getItems());
         tableVo.setiTotalDisplayRecords(total);
         tableVo.setiTotalRecords(total);
         return tableVo;
    }
    
    /**
	 * 添加
	 * @param vo
	 * @return
	 */
	//@RequiresPermissions("activity:menu:rule")
	@RequestMapping(value = "/addActivityRule", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject addActivityRule(ActivityRuleVo activityRuleVo,HttpServletRequest request){
		activityRuleVo.setCreatePerson(UserLoginUtil.getLoginUser(request).getLoginName());
		
		activityRuleVo.setCourseIds(StringUtil.arrToString(request.getParameterValues("microCourseIds")));
		activityRuleVo.setReachMoneys(StringUtil.arrToString(request.getParameterValues("reachMoney")));
		activityRuleVo.setMinusMoneys(StringUtil.arrToString(request.getParameterValues("minusMoney")));

		activityRuleService.addActivityRule(activityRuleVo);
        return ResponseObject.newSuccessResponseObject("新增成功!");
    }

	/**
	 * 编辑
	 * @param vo
	 * @return
	 */
	//@RequiresPermissions("activity:menu:rule")
	@RequestMapping(value = "updateActivityRuleById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateActivityRuleById (ActivityRuleVo activityRuleVo,HttpServletRequest request){
		activityRuleVo.setCourseIds(StringUtil.arrToString(request.getParameterValues("microCourseIds")));
		activityRuleVo.setReachMoneys(StringUtil.arrToString(request.getParameterValues("reachMoney")));
		activityRuleVo.setMinusMoneys(StringUtil.arrToString(request.getParameterValues("minusMoney")));

		activityRuleService.updateActivityRule(activityRuleVo);
		return ResponseObject.newSuccessResponseObject("修改成功!");
	}

    /**
     * 获取根据学科ID获取到微课程
     * @param String subjectIds
     * @return
     */
    @RequestMapping(value = "getMicroCourseList", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject getMicroCourseList(String subjectIds){
    	return ResponseObject.newSuccessResponseObject(activityRuleService.getMicroCourseList(subjectIds));
    }
}
