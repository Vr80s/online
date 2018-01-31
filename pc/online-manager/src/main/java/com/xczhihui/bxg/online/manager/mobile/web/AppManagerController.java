package com.xczhihui.bxg.online.manager.mobile.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.AppVersionInfo;
import com.xczhihui.bxg.online.manager.operate.service.AppVersionManagerService;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;

@Controller
@RequestMapping(value = "/operate/appManager")
public class AppManagerController{
	
	@Autowired
	private AppVersionManagerService appVersionManagerService;
	
	/**
     * 
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView index(){
         ModelAndView mav=new ModelAndView("/mobile/appManager");
         return mav;
    }

	//@RequiresPermissions("mobile:appManager")
    @RequestMapping(value = "/findAppManagerList", method = RequestMethod.POST)
    @ResponseBody
    public TableVo findAppManagerList(TableVo tableVo) {
         int pageSize = tableVo.getiDisplayLength();
         int index = tableVo.getiDisplayStart();
         int currentPage = index / pageSize + 1;
         String params = tableVo.getsSearch();
         Groups groups = Tools.filterGroup(params);
         Group statusGroup = groups.findByName("search_status");
//         Group nameGroup = groups.findByName("search_name");

         AppVersionInfo searchVo=new AppVersionInfo();

         if(statusGroup!=null){
        	 searchVo.setStatus(Integer.parseInt(statusGroup.getPropertyValue1().toString()));
         }

//         if(nameGroup!=null){
//              searchVo.setName(nameGroup.getPropertyValue1().toString());
//         }

         Page<AppVersionInfo> page = appVersionManagerService.findAppVersionInfoPage(searchVo, currentPage, pageSize);

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
	//@RequiresPermissions("mobile:appManager")
	@RequestMapping(value = "/addAppManager", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject addAppManager(AppVersionInfo appVersionInfo,HttpServletRequest request){
		appVersionInfo.setCreatePerson(UserLoginUtil.getLoginUser(request).getId());
		appVersionManagerService.addAppVersionInfo(appVersionInfo);
        return ResponseObject.newSuccessResponseObject("新增成功!");
    }

	/**
	 * 编辑
	 * @param vo
	 * @return
	 */
	//@RequiresPermissions("mobile:appManager")
	@RequestMapping(value = "updateAppVersionInfoById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateAppVersionInfoById (AppVersionInfo appVersionInfo){
		appVersionManagerService.updateAppVersionInfo(appVersionInfo);
		return ResponseObject.newSuccessResponseObject("修改成功!");
	}

	/**
	 * 修改状态(禁用or启用)
	 * @param Integer id
	 * @return
	 */
	@RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateStatus(AppVersionInfo appVersionInfo){
		appVersionManagerService.updateStatus(appVersionInfo);
		return ResponseObject.newSuccessResponseObject("操作成功！");
	}
	
	/**
	 * 批量逻辑删除
	 * @param Integer id
	 * @return
	 */
    @RequestMapping(value = "deletes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletes(String ids){
         ResponseObject responseObject=new ResponseObject();
         if(ids!=null) {
              String[] _ids = ids.split(",");
              appVersionManagerService.deletes(_ids);
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
    public ResponseObject upMove(String id) {
         ResponseObject responseObj = new ResponseObject();
         appVersionManagerService.updateSortUp(id);
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
    public ResponseObject downMove(String id) {
         ResponseObject responseObj = new ResponseObject();
         appVersionManagerService.updateSortDown(id);
         responseObj.setSuccess(true);
         return responseObj;
    }
}
