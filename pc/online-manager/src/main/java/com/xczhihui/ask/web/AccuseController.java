package com.xczhihui.ask.web;

import javax.servlet.http.HttpServletRequest;

import com.xczhihui.common.util.CodeUtil;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.xczhihui.common.util.DateUtil;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.ask.service.AccuseService;
import com.xczhihui.ask.vo.AccuseVo;
import com.xczhihui.support.shiro.ManagerUserUtil;
import com.xczhihui.utils.Group;

/**
 * 投诉管理
 *
 * @author 王高伟
 * @create 2016-10-16 09:59:09
 */
@RestController
@RequestMapping(value = "/ask/accuse")
public class AccuseController {

	@Autowired
	private AccuseService accuseService;

	@Value("${web.url}")
	private String weburl;

	/**
	 * 问答管理
	 *
	 * @return
	 */
	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("ask/accuse");
		mav.addObject(
				"md5UserName",
				CodeUtil.MD5Encode(CodeUtil.MD5Encode(ManagerUserUtil
						.getUsername() + "WWW.ixincheng.com20161021")));
		weburl = (weburl == null) ? "http://www.ixincheng.com" : weburl;
		request.setAttribute("weburl", weburl);
		return mav;
	}

	// @RequiresPermissions("ask:accuse")
	@RequestMapping(value = "/findAccuseList", method = RequestMethod.POST)
	@ResponseBody
	public TableVo findQuestionList(TableVo tableVo) {
		int pageSize = tableVo.getiDisplayLength();
		int index = tableVo.getiDisplayStart();
		int currentPage = index / pageSize + 1;
		String params = tableVo.getsSearch();
		Groups groups = Tools.filterGroup(params);
		Group accuseTypeGroup = groups.findByName("accuseType");
		Group statusGroup = groups.findByName("status");
		Group contentGroup = groups.findByName("content");
		Group startTimeGroup = groups.findByName("startTime");
		Group stopTimeGroup = groups.findByName("stopTime");
		//
		AccuseVo searchVo = new AccuseVo();
		if (accuseTypeGroup != null) {
			searchVo.setAccuseType(accuseTypeGroup.getPropertyValue1()
					.toString());
		}
		if (statusGroup != null) {
			searchVo.setStatus(Integer.parseInt(statusGroup.getPropertyValue1()
					.toString()));
		}
		if (contentGroup != null) {
			searchVo.setContent(contentGroup.getPropertyValue1().toString());
		}
		if (startTimeGroup != null) {
			searchVo.setStartTime(DateUtil.parseDate(startTimeGroup
					.getPropertyValue1().toString(), "yyyy-MM-dd"));
		}
		if (stopTimeGroup != null) {
			searchVo.setStopTime(DateUtil.parseDate(stopTimeGroup
					.getPropertyValue1().toString(), "yyyy-MM-dd"));
		}
		Page<AccuseVo> page = accuseService.findAccusePage(searchVo,
				currentPage, pageSize);

		int total = page.getTotalCount();
		tableVo.setAaData(page.getItems());
		tableVo.setiTotalDisplayRecords(total);
		tableVo.setiTotalRecords(total);
		return tableVo;
	}

	// @RequiresPermissions("ask:accuse")
	@RequestMapping(value = "/checkAccuseStatus", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObject checkAccuseStatus(AccuseVo accuseVo) {
		ResponseObject responseObject = new ResponseObject();
		if (accuseService.checkAccuseStatus(accuseVo)) {// 已经处理
			responseObject.setSuccess(true);
			responseObject.setErrorMessage("该投诉已经处理!");
		} else {
			responseObject.setSuccess(false);
		}
		return responseObject;
	}
}
