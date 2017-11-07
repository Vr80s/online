package com.xczhihui.bxg.online.manager.boxueshe.web;

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
import com.xczhihui.bxg.common.web.controller.AbstractController;
import com.xczhihui.bxg.online.manager.boxueshe.service.BannerService;
import com.xczhihui.bxg.online.manager.boxueshe.vo.ArticleVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseVo;
import com.xczhihui.bxg.online.manager.operate.service.Banner2Service;
import com.xczhihui.bxg.online.manager.operate.vo.Banner2Vo;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;

/**
 * 博学社banner管理控制层实现类
 * @author wl
 */
@Controller
@RequestMapping(value = "/boxueshe/banner")
public class BannerController {
	protected final static String BOXUESHE_PATH_PREFIX = "/boxueshe/";
	@Autowired
	private BannerService bannerService;
	/**
     * 
     * @return
     */
	@RequiresPermissions("boxueshe:menu:banner")
	@RequestMapping(value = "index")
	public String index(HttpServletRequest request) {
		
		return BOXUESHE_PATH_PREFIX + "/banner";
	}
	@RequiresPermissions("boxueshe:menu:banner")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public TableVo banner(TableVo tableVo) {
         int pageSize = tableVo.getiDisplayLength();
         int index = tableVo.getiDisplayStart();
         int currentPage = index / pageSize + 1;
         String params = tableVo.getsSearch();
         Groups groups = Tools.filterGroup(params);
         ArticleVo searchVo=new ArticleVo();
         
         Page<ArticleVo> page = bannerService.findBannerPage(searchVo, currentPage, pageSize);

         int total = page.getTotalCount();
         tableVo.setAaData(page.getItems());
         tableVo.setiTotalDisplayRecords(total);
         tableVo.setiTotalRecords(total);
         return tableVo;
	}
	/**
	 * 取消推荐
	 * @param Integer id
	 * @return
	 */
    @RequestMapping(value = "updateRec", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateRec(Integer id) {
    	bannerService.updateRec(id);
    	return ResponseObject.newSuccessResponseObject("操作成功！");
    }
	/**
	 * 修改图片
	 * @param articleVo
	 * @return
	 */
	@RequestMapping(value = "updateBannerPath", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateBannerPath(ArticleVo articleVo){
		ResponseObject responseObj = new ResponseObject();
		bannerService.updateBannerPath(articleVo);
		responseObj.setSuccess(true);
		responseObj.setErrorMessage("设置成功！");
		return responseObj;
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
         bannerService.updateSortUp(id);
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
         bannerService.updateSortDown(id);
         responseObj.setSuccess(true);
         return responseObj;
    }
}
