package com.xczhihui.bxg.online.manager.headline.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.common.util.DateUtil;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.controller.AbstractController;
import com.xczhihui.bxg.online.manager.headline.service.AppraiseService;
import com.xczhihui.bxg.online.manager.headline.vo.AppraiseVo;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;

/**
 * 博学社评价管理控制层实现类
 * @author 
 */
@Controller
@RequestMapping(value="headline/appraise")
public class AppraiseController extends AbstractController{
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	protected final static String headline_PATH_PREFIX = "/headline/";
	@Autowired
	private AppraiseService appraiseService;

	//@RequiresPermissions("headline:menu:appraise")
	@RequestMapping(value = "index")
	public String index(HttpServletRequest request) {
		
		return headline_PATH_PREFIX + "/appraise";
	}
	
    //@RequiresPermissions("headline:menu:appraise")
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public TableVo Appraise(TableVo tableVo) {
         int pageSize = tableVo.getiDisplayLength();
         int index = tableVo.getiDisplayStart();
         int currentPage = index / pageSize + 1;
         String params = tableVo.getsSearch();
         Groups groups = Tools.filterGroup(params);
         Group articleIdGroup = groups.findByName("articleId");
         Group nameGroup = groups.findByName("name");
         Group contentGroup = groups.findByName("content");
         Group startTimeGroup = groups.findByName("startTime");
         Group stopTimeGroup = groups.findByName("stopTime");
//         
         AppraiseVo searchVo=new AppraiseVo();
         if(articleIdGroup!=null){
              searchVo.setArticleId(articleIdGroup.getPropertyValue1().toString());
         }
         if(nameGroup!=null){
        	 searchVo.setName(nameGroup.getPropertyValue1().toString());
         }
         if(contentGroup!=null){
              searchVo.setContent(contentGroup.getPropertyValue1().toString());
         }
         if(startTimeGroup!=null){
              searchVo.setStartTime(DateUtil.parseDate(startTimeGroup.getPropertyValue1().toString(),"yyyy-MM-dd"));
         }
         if(stopTimeGroup!=null){
        	 searchVo.setStopTime(DateUtil.parseDate(stopTimeGroup.getPropertyValue1().toString(),"yyyy-MM-dd"));
         }

         
         Page<AppraiseVo> page = appraiseService.findAppraisePage(searchVo, currentPage, pageSize);

         int total = page.getTotalCount();
         tableVo.setAaData(page.getItems());
         tableVo.setiTotalDisplayRecords(total);
         tableVo.setiTotalRecords(total);
         return tableVo;
    }
	/**
	 * 删除消息
	 * @param id 编号
	 * @param request 请求对象
	 * @return 响应对象
	 */
	@RequestMapping(value = "delete/{id}/{articleId}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject deleteById(@PathVariable String id,@PathVariable String articleId,HttpServletRequest request) {
		ResponseObject responseObj = new ResponseObject();

		try {
			if(StringUtils.isNotEmpty(id)) {
				appraiseService.deleteById(id,articleId);
				responseObj.setSuccess(true);
				responseObj.setErrorMessage("操作成功");
				return responseObj;
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		responseObj.setSuccess(false);
		responseObj.setErrorMessage("操作失败");
		return responseObj;
	}
}
