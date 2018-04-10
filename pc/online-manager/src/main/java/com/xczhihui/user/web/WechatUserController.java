package com.xczhihui.user.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xczhihui.course.service.CloudClassMenuService;
import com.xczhihui.utils.TableVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.controller.AbstractController;
import com.xczhihui.bxg.online.common.domain.WechatUser;
import com.xczhihui.course.vo.MenuVo;
import com.xczhihui.user.service.WechatChannelService;
import com.xczhihui.user.service.WechatUserService;
import com.xczhihui.user.vo.WechatChannelVo;

/**
 * 网站用户管理
 * 
 * @author Haicheng Jiang
 */
@Controller
@RequestMapping("wechatuser")
public class WechatUserController extends AbstractController {

	protected final static String USER_PATH_PREFIX = "/onlineuser/";

	@Autowired
	private WechatUserService service;

	@Autowired
	private CloudClassMenuService menuService;

	@Autowired
	private WechatChannelService wechatChannelService;

	/**
	 * 转到页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "index")
	public ModelAndView index(HttpServletRequest request) {

		List<WechatChannelVo> wechatChannelVoS = wechatChannelService.findWechatChannelList();
		request.setAttribute("wechatChannelVos", wechatChannelVoS);
		ModelAndView mav = new ModelAndView(USER_PATH_PREFIX + "wechatusers");
		return mav;
	}

	/**
	 * 查询列表
	 * 
	 * @param tableVo
	 * @return
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public TableVo list(TableVo tableVo) {

		Map<String, String> paramMap = new HashMap<String, String>();
		if (!StringUtils.isEmpty(tableVo.getsSearch())) {
			Gson gson = new Gson();
			List<Map<String, Object>> paramList = gson.fromJson(
					tableVo.getsSearch(),
					new TypeToken<List<Map<String, Object>>>() {
					}.getType());
			if (paramList.size() > 0) {
				for (Map<String, Object> item : paramList) {
					paramMap.put((String) item.get("propertyName"),
							item.get("propertyValue1").toString());
				}
			}
		}

		int pageSize = tableVo.getiDisplayLength();
		int index = tableVo.getiDisplayStart();
		int currentPage = index / pageSize + 1;

		
		Page<WechatUser> page = service.findUserPage(paramMap.get("nickname"),paramMap.get("loginname"),
				paramMap.get("subscribeTimeStart"),
				paramMap.get("subscribeTimeEnd"), paramMap.get("qr_scene"),
				currentPage, pageSize);

		int total = page.getTotalCount();
		tableVo.setAaData(page.getItems());
		tableVo.setiTotalDisplayRecords(total);
		tableVo.setiTotalRecords(total);
		return tableVo;
	}

	/**
	 * 启用/禁用
	 * 
	 * @param loginName
	 * @return
	 */
	// @RequiresPermissions("Wechatuser:manager")
	@RequestMapping(value = "updateUserStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateUserStatus(String loginName, int status) {
		service.updateUserStatus(loginName, status);
		return ResponseObject.newSuccessResponseObject(null);
	}

	/**
	 * 设置学科权限
	 * 
	 * @param WechatUser
	 * @return
	 */
	// @RequiresPermissions("Wechatuser:manager")
	@RequestMapping(value = "setMenu", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject setMenu(WechatUser entity) {
		service.updateMenuForUser(entity);
		return ResponseObject.newSuccessResponseObject("设置学科权限成功！");
	}

	/**
	 * 设置为讲师 --》并为这个讲师新增一个房间号
	 * 
	 * @param loginName
	 * @return
	 */
	// @RequiresPermissions("Wechatuser:manager")
	@RequestMapping(value = "updateUserLecturer", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateUserLecturer(String userId, int lecturerStatus,
			String description) {
		service.updateUserLecturer(userId, lecturerStatus, description);
		return ResponseObject.newSuccessResponseObject("设置成功！");
	}

	/**
	 * 转到页面
	 * 
	 * @param request
	 * @return
	 */
	// @RequiresPermissions("Wechatuser:manager")
	@RequestMapping(value = "editUserDescription")
	public ModelAndView editUserDescription(HttpServletRequest request,
			String userId) {
		List<MenuVo> menuVos = menuService.list();
		request.setAttribute("menus", menuVos);
		WechatUser ou = service.getWechatUserByUserId(userId);
		request.setAttribute("id", userId);
		ModelAndView mav = new ModelAndView(USER_PATH_PREFIX
				+ "userDescriptionEdit");
		return mav;
	}

	@RequestMapping(value = "modifyUser", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObject modifyUser(String userId, String loginName)
			throws ServletRequestBindingException {
		service.updateUserLogin(userId, loginName);
		return ResponseObject.newSuccessResponseObject("牛逼啦，替换成功！");
	}
}