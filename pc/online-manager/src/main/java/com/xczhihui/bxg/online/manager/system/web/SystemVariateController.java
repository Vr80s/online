/*package com.xczhihui.bxg.online.manager.system.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.common.support.domain.Attachment;
import com.xczhihui.bxg.common.support.domain.SystemVariate;
import com.xczhihui.bxg.common.support.service.AttachmentCenterService;
import com.xczhihui.bxg.common.support.service.AttachmentType;
import com.xczhihui.bxg.common.support.service.SystemVariateService;
import com.xczhihui.bxg.common.util.DateUtil;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.auth.UserHolder;
import com.xczhihui.bxg.common.web.controller.AbstractController;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.Medicalvariates;
import com.xczhihui.bxg.online.manager.boxueshe.service.ArticleService;
import com.xczhihui.bxg.online.manager.boxueshe.service.systemVariateService;
import com.xczhihui.bxg.online.manager.boxueshe.vo.ArticleVo;
import com.xczhihui.bxg.online.manager.boxueshe.vo.variateVo;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;

*//**
 * 博学社文章管理控制层实现类
 * @author yxd
 *//*

@Controller
@RequestMapping("system/variate")
public class SystemVariateController extends AbstractController{
	protected final static String BOXUESHE_PATH_PREFIX = "/system/";
	
	@Autowired
	private SystemVariateService systemVariateService;
	
	@Value("${online.web.url:http://www.ixincheng.com}")
	private String weburl;
	
	@RequiresPermissions("system:menu:variate")
	@RequestMapping(value = "index")
	public String index(HttpServletRequest request) {
		
		return BOXUESHE_PATH_PREFIX + "/variateList";
	}
	
	@RequiresPermissions("system:menu:variate")
	@RequestMapping(value = "list")
	@ResponseBody
	public TableVo articles(TableVo tableVo) {
		 int pageSize = tableVo.getiDisplayLength();
         int index = tableVo.getiDisplayStart();
         int currentPage = index / pageSize + 1;
         String params = tableVo.getsSearch();
         Groups groups = Tools.filterGroup(params);
         
//        VariateVo searchVo=new VariateVo();
//         Group title = groups.findByName("search_title");
//         if (title != null) {
//       	  searchVo.setTitle(title.getPropertyValue1().toString());
//         }
//         Group status = groups.findByName("statusSearch");
//         if(status != null){
//          searchVo.setStatus(Integer.parseInt(status.getPropertyValue1().toString()));
//         }
//         Group startTime = groups.findByName("startTime");
//         if (startTime != null) {
//       	  searchVo.setStartTime(DateUtil.parseDate(startTime.getPropertyValue1().toString(),"yyyy-MM-dd"));
//         }
//         Group stopTime = groups.findByName("stopTime");
//         if (stopTime != null) {
//       	  searchVo.setStopTime(DateUtil.parseDate(stopTime.getPropertyValue1().toString(),"yyyy-MM-dd"));
//         }
         
         Page<SystemVariate> page = systemVariateService.getSystemVariatesList(null, currentPage, pageSize);
         
         int total = page.getTotalCount();
         tableVo.setAaData(page.getItems());
         tableVo.setiTotalDisplayRecords(total);
         tableVo.setiTotalRecords(total);
		return tableVo;
	}
	
	*//**
	 * 添加
	 * @param vo
	 * @return
	 *//*
	@RequiresPermissions("system:menu:variate")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	 public ResponseObject add(HttpServletRequest request,variateVo variateVo){
		String content = variateVo.getContent();
//		articleVo.setUserId(UserLoginUtil.getLoginUser(request).getId());
//		articleService.addArticle(articleVo);
//		articleService.addArticleTag(articleVo);
		variateVo.setUserId(UserLoginUtil.getLoginUser(request).getId());
		variateVo.setContent(content);
		//添加著作   文章
		systemVariateService.addvariate(variateVo);
		
		return ResponseObject.newSuccessResponseObject("操作成功！");
    }
	
	*//**
	 * 修改
	 * @param vo
	 * @return
	 *//*
	@RequiresPermissions("system:menu:variate")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	 public ResponseObject update(HttpServletRequest request,variateVo variateVo){
		String content = variateVo.getContent();
		content = Base64ToimageURL(content);
		articleVo.setContent(content);
		articleVo.setUserId(UserLoginUtil.getLoginUser(request).getId());
		articleService.updateArticle(articleVo);
		variateVo.setUserId(UserLoginUtil.getLoginUser(request).getId());
		//articleService.updatevariate(variateVo);
		systemVariateService.updatevariate(variateVo);
		
		return ResponseObject.newSuccessResponseObject("操作成功！");
    }
	*//**
	 * 删除
	 * 
	 *//*
	@RequiresPermissions("system:menu:variate")
	@RequestMapping(value = "deletes", method = RequestMethod.POST)
	@ResponseBody
	 public ResponseObject deletes(String ids){
	    if(ids!=null) {
            String[] _ids = ids.split(",");
            systemVariateService.deletes(_ids);
       }
		return ResponseObject.newSuccessResponseObject("操作成功！");
    }
	
	*//**
	 * 修改状态(禁用or启用)
	 * @param id
	 * @return
	 *//*
	@RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateStatus(String id){
		systemVariateService.updateStatus(id);
		return ResponseObject.newSuccessResponseObject("操作成功！");
	}
	
}
*/