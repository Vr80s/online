package com.xczhihui.bxg.online.manager.operate.web;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;


import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;

import com.xczhihui.bxg.online.manager.operate.service.Banner2Service;
import com.xczhihui.bxg.online.manager.operate.vo.Banner2Vo;

@Controller
@RequestMapping(value = "/operate/banner2")
public class Banner2Controller{
	
	@Autowired
	private Banner2Service banner2Service;
	
	/**
     * 
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView index(){
         ModelAndView mav=new ModelAndView("/operate/banner2");
         return mav;
    }

	@RequiresPermissions("operate:menu:banner2")
    @RequestMapping(value = "/findBanner2List", method = RequestMethod.POST)
    @ResponseBody
    public TableVo findQuestionList(TableVo tableVo) {
         int pageSize = tableVo.getiDisplayLength();
         int index = tableVo.getiDisplayStart();
         int currentPage = index / pageSize + 1;
         String params = tableVo.getsSearch();
         Groups groups = Tools.filterGroup(params);
         Group statusGroup = groups.findByName("status");
         Group descriptionGroup = groups.findByName("description");
         Group typeGroup = groups.findByName("search_type");

         Banner2Vo searchVo=new Banner2Vo();
         if(statusGroup!=null){
              searchVo.setStatus(Integer.parseInt(statusGroup.getPropertyValue1().toString()));
         }
         
         if(descriptionGroup!=null){
        	 searchVo.setDescription(descriptionGroup.getPropertyValue1().toString());
         }

         if(typeGroup!=null){
        	 searchVo.setType(Integer.valueOf(typeGroup.getPropertyValue1().toString()));
         }
         
         Page<Banner2Vo> page = banner2Service.findBanner2Page(searchVo, currentPage, pageSize);

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
	@RequiresPermissions("operate:menu:banner2")
	@RequestMapping(value = "/addBanner2", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject add(Banner2Vo banner2Vo,HttpServletRequest request){
		ResponseObject responseObj = new ResponseObject();
		banner2Vo.setCreatePerson(UserLoginUtil.getLoginUser(request).getLoginName());
		try{
			banner2Service.addBanner(banner2Vo);
            responseObj.setSuccess(true);
            responseObj.setErrorMessage("新增成功");
       }catch(Exception e){
    	   e.printStackTrace();
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("新增失败");
       }
        return responseObj;
    }

	/**
	 * 编辑
	 * @param vo
	 * @return
	 */
	@RequiresPermissions("operate:menu:banner2")
	@RequestMapping(value = "updateBanner2ById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateBanner2ById (Banner2Vo banner2Vo){
		ResponseObject responseObj = new ResponseObject();
		 try{
				banner2Service.updateBanner(banner2Vo);
	            responseObj.setSuccess(true);
	            responseObj.setErrorMessage("修改成功");
	     }catch(Exception e){
	    	   e.printStackTrace();
	            responseObj.setSuccess(false);
	            responseObj.setErrorMessage("修改失败");
	     }
		 return responseObj;
	}

	/**
	 * 修改状态(禁用or启用)
	 * @param Integer id
	 * @return
	 */
	@RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateStatus(Banner2Vo banner2Vo){
		if(banner2Service.updateStatus(banner2Vo))
		{
			return ResponseObject.newSuccessResponseObject("操作成功！");
		}else{
			return ResponseObject.newSuccessResponseObject("启用失败，banner启用上限为5个！");
		}
		
	}
	
	/**
	 * 批量逻辑删除
	 * @param Integer id
	 * @return
	 */
    @RequestMapping(value = "deletes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletes(String ids) throws InvocationTargetException, IllegalAccessException {
         ResponseObject responseObject=new ResponseObject();
         if(ids!=null) {
              String[] _ids = ids.split(",");
              banner2Service.deletes(_ids);
         }
         responseObject.setSuccess(true);
         responseObject.setErrorMessage("删除完成!");
         return responseObject;
    }


	/**
     * 上移
     * @param id
     * @return
     */
    @RequestMapping(value = "upMove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject upMove(Integer id) {
         ResponseObject responseObj = new ResponseObject();
         banner2Service.updateSortUp(id);
         responseObj.setSuccess(true);
         return responseObj;
    }
    
	/**
     * 下移
     * @param id
     * @return
     */
    @RequestMapping(value = "downMove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject downMove(Integer id) {
         ResponseObject responseObj = new ResponseObject();
         banner2Service.updateSortDown(id);
         responseObj.setSuccess(true);
         return responseObj;
    }

}
