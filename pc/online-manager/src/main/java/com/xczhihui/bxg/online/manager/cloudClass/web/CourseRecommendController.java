package com.xczhihui.bxg.online.manager.cloudClass.web;

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
import com.xczhihui.bxg.online.manager.ask.vo.AccuseVo;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;

import com.xczhihui.bxg.online.manager.cloudClass.service.CourseRecommendService;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseRecommendVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseVo;

@Controller
@RequestMapping(value = "/cloudClass/courseRecommend")
public class CourseRecommendController{
	
	@Autowired
	private CourseRecommendService courseRecommendService;
	
	@RequiresPermissions("cloudClass:menu:course")
	@RequestMapping(value = "recList")
	@ResponseBody
	public TableVo recList(TableVo tableVo) {
		int pageSize = 10;
		int index = 1;
		int currentPage = index / pageSize + 1;
		String params = tableVo.getsSearch();
		
//		System.out.println(params);
		Groups groups = Tools.filterGroup(params);
		
		CourseRecommendVo searchVo=new CourseRecommendVo();
		
		Group showCourseId = groups.findByName("search_showCourseId");
//		System.out.println(""+showCourseId.getPropertyValue1().toString());
		if (showCourseId != null) {
			searchVo.setShowCourseId(Integer.parseInt(showCourseId.getPropertyValue1().toString()));
		}
		
		Page<CourseVo> page = courseRecommendService.findCourseRecommendPage(searchVo, currentPage, pageSize);
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
             courseRecommendService.deletes(_ids);
        }
        responseObject.setSuccess(true);
        responseObject.setErrorMessage("取消成功!");
        return responseObject;
   }


	/**
    * 上移
    * @param id
    * @return
    */
   @RequestMapping(value = "upMoveRec")
   @ResponseBody
   public ResponseObject upMove(Integer id) {
        ResponseObject responseObj = new ResponseObject();
        courseRecommendService.updateSortUp(id);
        responseObj.setSuccess(true);
        return responseObj;
   }
   
	/**
    * 下移
    * @param id
    * @return
    */
   @RequestMapping(value = "downMoveRec")
   @ResponseBody
   public ResponseObject downMove(Integer id) {
        ResponseObject responseObj = new ResponseObject();
        courseRecommendService.updateSortDown(id);
        responseObj.setSuccess(true);
        return responseObj;
   }

    
    /**
	 * 添加
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/addCourseRecommend", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject addCourseRecommend(String showCourseId,HttpServletRequest request){
		ResponseObject responseObj = new ResponseObject();
		String[] recCourseHids = request.getParameterValues("recCourseHid");//这个是个数组
		try{
			courseRecommendService.addCourseRecommend(showCourseId,recCourseHids,UserLoginUtil.getLoginUser(request).getLoginName());
            responseObj.setSuccess(true);
            responseObj.setErrorMessage("保存成功!");
       }catch(Exception e){
    	   e.printStackTrace();
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("保存失败！");
       }
        return responseObj;
    }
}