package com.xczhihui.bxg.online.manager.fcode.web;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.Grade;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.manager.activity.vo.ActivityRuleVo;
import com.xczhihui.bxg.online.manager.cloudClass.service.CourseService;
import com.xczhihui.bxg.online.manager.fcode.service.FcodeService;
import com.xczhihui.bxg.online.manager.fcode.vo.FcodeVo;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.StringUtil;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;

/**
 * 兑换码活动控制层实现类
 * @author yxd
 */

@Controller
@RequestMapping("factivity/fcode")
public class FcodeController {
	@Autowired
	FcodeService fcodeService;
	
	@Autowired
	private CourseService courseService;
	
	@RequestMapping(value = "/index" , method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request,String page,String menuName,String menuId){
		 ModelAndView mav=new ModelAndView("fcode/fcodeList");
		 List<Menu> menuVos= courseService.getfirstMenus(null);
 		 request.setAttribute("menuVo", menuVos);
	     return mav;
	}
	
	@RequestMapping(value = "/toFcodeIndex" , method = RequestMethod.GET)
	public ModelAndView toFcodeIndex(HttpServletRequest request,String page,String activityName,String lotNo){
		 ModelAndView mav=new ModelAndView("fcode/fcodeDetail");
		 List<FcodeVo> temp1 = fcodeService.getFcodeBylotNo(lotNo,null);
		 List<FcodeVo> temp2 = fcodeService.getFcodeBylotNo(lotNo,2);
		 List<FcodeVo> temp3 = fcodeService.getFcodeBylotNo(lotNo,3);
		 request.setAttribute("lotNo", lotNo);
		 request.setAttribute("activityName", activityName);
		 request.setAttribute("createFcodeSum", temp1.size());
		 request.setAttribute("lockFcodeSum", temp2.size());
		 request.setAttribute("useFcodeSum", temp3.size());
	     return mav;
	}
	
	/**
     * 获取F码列表信息
     * @return
     */
    @RequestMapping(value = "/findFcodeDetailList")
    @ResponseBody
    public TableVo findFcodeDetailList(TableVo tableVo){
    	  int pageSize = tableVo.getiDisplayLength();
          int index = tableVo.getiDisplayStart();
          int currentPage = index / pageSize + 1;
          String params = tableVo.getsSearch();
          Groups groups = Tools.filterGroup(params);
          Group nameSearch  = groups.findByName("NameSearch");
          Group statusSearch  = groups.findByName("search_status");
          Group lotNo  = groups.findByName("lotNo");
       

          FcodeVo searchVo=new FcodeVo();

           if(nameSearch!=null){
              searchVo.setName(nameSearch.getPropertyValue1().toString());
          }
           if(statusSearch!=null){
               searchVo.setStatus(Integer.parseInt(statusSearch.getPropertyValue1().toString()));
           }
           searchVo.setLotNo(lotNo.getPropertyValue1().toString());
           
          Page<FcodeVo> page= fcodeService.findFcodeDetailPage(searchVo, currentPage, pageSize);
          int total = page.getTotalCount();
          tableVo.setAaData(page.getItems());
          tableVo.setiTotalDisplayRecords(total);
          tableVo.setiTotalRecords(total);
          return tableVo;
    	
    }
	
	/**
     * 获取F码列表信息
     * @return
     */
    @RequestMapping(value = "/findFcodeList")
    @ResponseBody
    public TableVo findFcodeList(TableVo tableVo){
    	  int pageSize = tableVo.getiDisplayLength();
          int index = tableVo.getiDisplayStart();
          int currentPage = index / pageSize + 1;
          String params = tableVo.getsSearch();
          Groups groups = Tools.filterGroup(params);
          Group NameSearch  = groups.findByName("NameSearch");
       

          FcodeVo searchVo=new FcodeVo();

           if(NameSearch!=null){
              searchVo.setName(NameSearch.getPropertyValue1().toString());
          }
           
          Page<FcodeVo> page= fcodeService.findPage(searchVo, currentPage, pageSize);
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
     * @throws ParseException 
     * @throws DataAccessException 
	 */
	//@RequiresPermissions("activity:menu:fcode")
	@RequestMapping(value = "/addFcodeRule", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject addFcodeRule(FcodeVo fcodeVo,HttpServletRequest request) throws DataAccessException, ParseException{
		fcodeVo.setCreatePerson(UserLoginUtil.getLoginUser(request).getLoginName());
		fcodeService.addFcodeRule(fcodeVo);
        return ResponseObject.newSuccessResponseObject("新增成功!");
    }
	
	/**
	 * 修改
	 * @param vo
	 * @return
	 */
	//@RequiresPermissions("activity:menu:fcode")
	@RequestMapping(value = "/updateFcodeRule", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateFcodeRule(FcodeVo fcodeVo,HttpServletRequest request){
		fcodeService.updateFcodeRule(fcodeVo);
        return ResponseObject.newSuccessResponseObject("新增成功!");
    }

}
