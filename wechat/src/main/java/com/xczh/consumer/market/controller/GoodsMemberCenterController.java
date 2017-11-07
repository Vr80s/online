package com.xczh.consumer.market.controller;

import com.xczh.consumer.market.bean.WxcpClientUser;
import com.xczh.consumer.market.bean.WxcpClientUserWxMapping;
import com.xczh.consumer.market.service.WxcpClientUserService;
import com.xczh.consumer.market.service.WxcpClientUserWxMappingService;
import com.xczh.consumer.market.utils.ControllerUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 个人中心
 * @author zhangshixiong
 * @date 2017-01-16
 */
@Controller
@RequestMapping("/bxg/shop/membercenter")
public class GoodsMemberCenterController {
	@Autowired
	private WxcpClientUserService wxcpClientUserService;
	@Autowired
	private WxcpClientUserWxMappingService wxcpClientUserWxMappingService;
	/**
	 * 会员中心个人信息
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("memberinfo")
	@ResponseBody
	public ResponseObject memberInfo(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String id = req.getParameter("openId").toString();
		if(null == id){
			return ResponseObject.newErrorResponseObject("微信编号不能为空");
		}
		WxcpClientUserWxMapping wxcpClientUserWxMapping = wxcpClientUserWxMappingService
				.getWxcpClientUserWxMappingByOpenId(id);
		if(null == wxcpClientUserWxMapping){
			return ResponseObject.newErrorResponseObject("查询不到用户信息");
		}
		String client_id = wxcpClientUserWxMapping.getClient_id();
		WxcpClientUser wxcpClientUser = wxcpClientUserService.getWxcpClientUser(client_id);
		if(null == wxcpClientUser){
			return ResponseObject.newErrorResponseObject("查询不到用户信息");
		}
		if(StringUtils.isBlank(wxcpClientUser.getUser_sex())){
			wxcpClientUser.setUser_sex(wxcpClientUserWxMapping.getSex());
		}
		if(StringUtils.isBlank(wxcpClientUser.getUser_nick_name())){
			wxcpClientUser.setUser_nick_name(StringUtils.isBlank(wxcpClientUserWxMapping.getNickname())
					? wxcpClientUserWxMapping.getOpenname() : wxcpClientUserWxMapping.getNickname()
					);
		}
		if(StringUtils.isBlank(wxcpClientUser.getFace_image_id())){
			wxcpClientUser.setFace_image_id(wxcpClientUserWxMapping.getHeadimgurl());
		}
		return ResponseObject.newSuccessResponseObject(wxcpClientUser);
	}
	/**
	 * 修改会员信息
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updatememberinfo")
	@ResponseBody
	public ResponseObject updateMemberInfo(HttpServletRequest req, HttpServletResponse res,Map<String, String> params) throws Exception{
		WxcpClientUser bean = ControllerUtil.params2bean(params, WxcpClientUser.class);
		if(null == bean.getUser_id()){
			return ResponseObject.newErrorResponseObject("查询不到用户信息");
		}
		WxcpClientUser wxcpClientUser = wxcpClientUserService.getWxcpClientUser(bean.getUser_id());
		if(null == wxcpClientUser){
			return ResponseObject.newErrorResponseObject("查询不到用户信息");
		}
		wxcpClientUserService.updateWxcpClientUser(bean);
		return ResponseObject.newSuccessResponseObject("修改成功");
	}
}
