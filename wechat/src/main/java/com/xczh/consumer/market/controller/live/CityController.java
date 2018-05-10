package com.xczh.consumer.market.controller.live;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.online.api.service.CityService;
import com.xczhihui.online.api.vo.UserAddressManagerVo;

/**
 * 点播控制器 ClassName: BunchPlanController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>
 * email: 15936216273@163.com <br>
 * Create Time: 2017年8月11日<br>
 */
@Controller
@RequestMapping("/bxg/city")
public class CityController {

	@Autowired
	private CityService cityService;

	@Autowired
	private AppBrowserService appBrowserService; 
	
	//UserAddressManagerVo
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CityController.class);
	
	/**
	 * 得到用户的地址管理列表
	 */
	@RequestMapping("getAddressAll")
	@ResponseBody
	public ResponseObject getAddressAll(HttpServletRequest req,
			HttpServletResponse res)
			throws Exception {
		
		OnlineUser ou = appBrowserService.getOnlineUserByReq(req);
		/**
		 * 获取所有的省份
		 */
		List<UserAddressManagerVo> list = cityService.getAddressAll(ou.getId());
		return ResponseObject.newSuccessResponseObject(list);
	}
	/**
	 * 保存编辑的地址
	 */
	@RequestMapping("saveAddress")
	@ResponseBody
	public ResponseObject saveAddress(HttpServletRequest req,
			HttpServletResponse res,@ModelAttribute UserAddressManagerVo udm)
			throws Exception {
		//OnlineUser ou = appBrowserService.getOnlineUserByReq(req, params);
		OnlineUser ou = appBrowserService.getOnlineUserByReq(req);
		udm.setUserId(ou.getId());
		try {
			cityService.saveAddress(udm);
			return ResponseObject.newSuccessResponseObject("保存成功");
		} catch (Exception e) {
            e.printStackTrace();
			return ResponseObject.newErrorResponseObject("保存失败");
		}
	}
	/**
	 * 保存编辑的地址
	 */
	@RequestMapping("updateAddress")
	@ResponseBody
	public ResponseObject updateAddress(HttpServletRequest req,
			HttpServletResponse res,@ModelAttribute UserAddressManagerVo udm){
		 try {
			cityService.updateAddress(udm);
			return ResponseObject.newSuccessResponseObject("修改成功");
		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("修改失败");
		}
	}
	/**
	 * 设置地址为默认地址
	 */
	@RequestMapping("updateIsAcquies")
	@ResponseBody
	public ResponseObject updateIsAcquies(HttpServletRequest req,
			HttpServletResponse res) {
		String newId = req.getParameter("newId");
		OnlineUser ou = appBrowserService.getOnlineUserByReq(req);
		try {
			cityService.updateIsAcquies(newId, ou.getUserId());
			return ResponseObject.newSuccessResponseObject("修改成功");
		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("修改失败");
		}
	}
	
	/**
	 * 删除这个地址啦
	 */
	@RequestMapping("deleteAddressById")
	@ResponseBody
	public ResponseObject deleteAddressById(HttpServletRequest req,
			HttpServletResponse res) {
		String id = req.getParameter("id");
		OnlineUser ou = appBrowserService.getOnlineUserByReq(req);
		try {
			cityService.deleteAddressById(id, ou.getUserId());
			return ResponseObject.newSuccessResponseObject("删除成功");
		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("删除失败");
		}
	}
	/**
	 * 根据id查找地址
	 */
	@RequestMapping("findAddressById")
	@ResponseBody
	public ResponseObject findAddressById(HttpServletRequest req,
			HttpServletResponse res) {
		String id = req.getParameter("id");
		try {
			UserAddressManagerVo umv =  cityService.findAddressById(id);
			return ResponseObject.newSuccessResponseObject(umv);
		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("修改失败");
		}
	}
	
	/**
	 * 根据用户id得到该用户的默认地址
	 */
	@RequestMapping("findAddressByUserId")
	@ResponseBody
	public ResponseObject findAcquiescenceAddressById(HttpServletRequest req,
			HttpServletResponse res) throws SQLException {
		OnlineUser ou = appBrowserService.getOnlineUserByReq(req);

		UserAddressManagerVo umv =  cityService.findAddressByUserIdAndAcq(ou.getId());
		return ResponseObject.newSuccessResponseObject(umv);
	}
	
	
	
	/**
	 * 得到所有都省份和市
	 */
	@RequestMapping("getAll")
	@ResponseBody
	public ResponseObject getAll(HttpServletRequest req,
								 HttpServletResponse res, Map<String, String> params)
			throws Exception {
		/**
		 * 获取所有的省份
		 */
		//List<Map<String, Object>> list = cityService.getProvince();
		List<Map<String, Object>> list = cityService.getAllProvinceCity();
		return ResponseObject.newSuccessResponseObject(list);
	}
	
	/**
	 * 得到所有都省、市、地区
	 */
	@RequestMapping("getAllPCC")
	@ResponseBody
	public ResponseObject getAllPCC(HttpServletRequest req,
								 HttpServletResponse res, Map<String, String> params)
			throws Exception {
		/**
		 * 获取所有的省份
		 */
		//List<Map<String, Object>> list = cityService.getProvince();
		List<Map<String, Object>> list = cityService.getAllProvinceCityCounty();
		return ResponseObject.newSuccessResponseObject(list);
	}
	
	/**
	 * 得到所有的国家
	 */
	@RequestMapping("getProvince")
	@ResponseBody
	public ResponseObject getProvince(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)
			throws Exception {
		/**
		 * 获取所有的省份
		 */
		//List<Map<String, Object>> list = cityService.getProvince();
		List<Map<String, Object>> list = cityService.getAllProvinceCity();
		return ResponseObject.newSuccessResponseObject(list);
	}

	/**
	 * 得到省下面的市
	 */
	@RequestMapping("getCity")
	@ResponseBody
	public ResponseObject getCity(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)
			throws Exception {
		String province_id = req.getParameter("province_id");
		if (province_id == null) {
			ResponseObject.newErrorResponseObject("参数异常");
		}
		/**
		 * 获取所有的省份
		 */
		List<Map<String, Object>> list = cityService.getCity(Integer
				.parseInt(province_id));
		return ResponseObject.newSuccessResponseObject(list);
	}

}
