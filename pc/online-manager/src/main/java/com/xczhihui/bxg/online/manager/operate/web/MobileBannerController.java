package com.xczhihui.bxg.online.manager.operate.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
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
import com.xczhihui.bxg.online.manager.operate.service.MobileBannerService;
import com.xczhihui.bxg.online.manager.operate.vo.MobileBannerVo;

@Controller
@RequestMapping(value = "/operate/mobileBanner")
public class MobileBannerController{
	
	@Autowired
	private MobileBannerService mobileBannerService;
	
	/**
     * 
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView index(){
         ModelAndView mav=new ModelAndView("/operate/mobileBanner");
         return mav;
    }

	//@RequiresPermissions("mobile:menu:banner")
    @RequestMapping(value = "/findMobileBannerList", method = RequestMethod.POST)
    @ResponseBody
    public TableVo findMobileBannerList(TableVo tableVo) {
         int pageSize = tableVo.getiDisplayLength();
         int index = tableVo.getiDisplayStart();
         int currentPage = index / pageSize + 1;
         String params = tableVo.getsSearch();
         Groups groups = Tools.filterGroup(params);
         Group statusGroup = groups.findByName("search_status");
         Group nameGroup = groups.findByName("search_name");

         MobileBannerVo searchVo=new MobileBannerVo();

         if(statusGroup!=null){
        	 searchVo.setStatus(Integer.parseInt(statusGroup.getPropertyValue1().toString()));
         }

         if(nameGroup!=null){
              searchVo.setName(nameGroup.getPropertyValue1().toString());
         }
         
         Group bannerType = groups.findByName("banner_type");
 		 if (bannerType != null) {
 			searchVo.setBannerType(Integer.parseInt(bannerType.getPropertyValue1().toString()));
 		 }

         Page<MobileBannerVo> page = mobileBannerService.findMobileBannerPage(searchVo, currentPage, pageSize);

         
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
	//@RequiresPermissions("mobile:menu:banner")
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject addMobileBanner(MobileBannerVo mobileBannerVo,HttpServletRequest request){
		mobileBannerVo.setCreatePerson(UserLoginUtil.getLoginUser(request).getLoginName());
		mobileBannerService.addMobileBanner(mobileBannerVo);
        return ResponseObject.newSuccessResponseObject("新增成功!");
    }

	/**
	 * 编辑
	 * @param vo
	 * @return
	 */
	//@RequiresPermissions("mobile:menu:banner")
	@RequestMapping(value = "updateMobileBannerById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateMobileBannerById (MobileBannerVo mobileBannerVo){
		mobileBannerService.updateMobileBanner(mobileBannerVo);
		return ResponseObject.newSuccessResponseObject("修改成功!");
	}

	/**
	 * 修改状态(禁用or启用)
	 * @param Integer id
	 * @return
	 */
	@RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateStatus(MobileBannerVo mobileBannerVo){
		mobileBannerService.updateStatus(mobileBannerVo);
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
              mobileBannerService.deletes(_ids);
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
         mobileBannerService.updateSortUp(id);
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
         mobileBannerService.updateSortDown(id);
         responseObj.setSuccess(true);
         return responseObj;
    }
}
