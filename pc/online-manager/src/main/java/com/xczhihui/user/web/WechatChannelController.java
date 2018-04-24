package com.xczhihui.user.web;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import com.xczhihui.support.shiro.ManagerUserUtil;
import com.xczhihui.user.vo.WechatChannelVo;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.common.support.domain.Attachment;
import com.xczhihui.common.support.service.AttachmentCenterService;
import com.xczhihui.common.support.service.AttachmentType;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.web.controller.AbstractController;
import com.xczhihui.user.service.WechatChannelService;

/**
 * 课程管理控制层实现类
 * 
 * @author yxd
 */

@Controller
@RequestMapping("wechatChannel")
public class WechatChannelController extends AbstractController {

	protected final static String WechatChannel_PATH_PREFIX = "/onlineuser/";
	@Autowired
	private WechatChannelService WechatChannelService;
	@Autowired
	private AttachmentCenterService att;
	@Value("${web.url}")
	private String weburl;

	@RequestMapping(value = "index")
	public String index(HttpServletRequest request) {

		return WechatChannel_PATH_PREFIX + "/wechatChannel";
	}

	@RequestMapping(value = "list")
	@ResponseBody
	public TableVo WechatChannels(TableVo tableVo) {
		int pageSize = tableVo.getiDisplayLength();
		int index = tableVo.getiDisplayStart();
		int currentPage = index / pageSize + 1;
		String params = tableVo.getsSearch();
		Groups groups = Tools.filterGroup(params);

		WechatChannelVo searchVo = new WechatChannelVo();

		Group status = groups.findByName("search_status");

		if (status != null) {
			searchVo.setStatus(status.getPropertyValue1().toString());
		}

		Group contact = groups.findByName("contact");
		if (contact != null) {
			searchVo.setContact(contact.getPropertyValue1().toString());
		}
		
		Page<WechatChannelVo> page = WechatChannelService.findWechatChannelPage(searchVo, currentPage, pageSize);
		
		int total = page.getTotalCount();
		tableVo.setAaData(page.getItems());
		tableVo.setiTotalDisplayRecords(total);
		tableVo.setiTotalRecords(total);
		return tableVo;

	}

	/**
	 * 添加
	 * 
	 * @param courseVo
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "addWechatChannel", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject add(WechatChannelVo WechatChannelVo) throws Exception {
		ResponseObject responseObj = new ResponseObject();
		WechatChannelService.addWechatChannel(WechatChannelVo);
		responseObj.setSuccess(true);
		responseObj.setErrorMessage("新增成功");
		return responseObj;
	}

	/**
	 * 查看
	 * 
	 * @param id
	 * @return
	 */
	// @RequiresPermissions("WechatChannel:menu")
	@RequestMapping(value = "findCourseById", method = RequestMethod.GET)
	@ResponseBody
	public WechatChannelVo findWechatChannelById(Integer id) {

		return WechatChannelService.getWechatChannelById(id);
	}

	/**
	 * 编辑
	 * 
	 * @param courseVo
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "updateWechatChannelById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateWechatChannelById(
			WechatChannelVo WechatChannelVo) throws IllegalAccessException, InvocationTargetException {
		ResponseObject responseObj = new ResponseObject();

		WechatChannelService.updateWechatChannel(WechatChannelVo);
		responseObj.setSuccess(true);
		responseObj.setErrorMessage("修改成功");
		return responseObj;
	}

	/**
	 * 修改状态(禁用or启用)
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "updateStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateStatus(Integer id) {
		WechatChannelService.updateStatus(id);
		return ResponseObject.newSuccessResponseObject("操作成功！");
	}

	/**
	 * 逻辑删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "deleteCourseById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject deleteCourseById(Integer id) {
		WechatChannelService.deleteWechatChannelById(id);
		return ResponseObject.newSuccessResponseObject("操作成功！");
	}

	/**
	 * 上移
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "upMove", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject upMove(Integer id) {
		ResponseObject responseObj = new ResponseObject();
		WechatChannelService.updateSortUp(id);
		responseObj.setSuccess(true);
		return responseObj;
	}

	/**
	 * 下移
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "downMove", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject downMove(Integer id) {
		ResponseObject responseObj = new ResponseObject();
		WechatChannelService.updateSortDown(id);
		responseObj.setSuccess(true);
		return responseObj;
	}

	@RequestMapping(value = "deletes", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject deletes(String ids) throws InvocationTargetException,
			IllegalAccessException {
		ResponseObject responseObject = new ResponseObject();
		if (ids != null) {
			String[] _ids = ids.split(",");
			WechatChannelService.deletes(_ids);
		}
		responseObject.setSuccess(true);
		responseObject.setErrorMessage("删除成功!");
		return responseObject;
	}

	@RequestMapping(value = "uploadImg", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject uploadImg(String content) {
		String str = content.split("base64,")[1];
		byte[] b = org.apache.commons.codec.binary.Base64.decodeBase64(str);
		Attachment a = att.addAttachment(ManagerUserUtil.getId(),
				AttachmentType.ONLINE, "1.png", b, "image/png");
		if (a.getError() != 0) {
			return ResponseObject.newErrorResponseObject("上传失败！");
		}
		return ResponseObject.newSuccessResponseObject(a);
	}
}
