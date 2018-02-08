package com.xczh.consumer.market.controller.user;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.controller.live.CityController;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.CacheService;
import com.xczh.consumer.market.service.OLAttachmentCenterService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.service.WxcpClientUserService;
import com.xczh.consumer.market.service.WxcpClientUserWxMappingService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.wxpay.util.WeihouInterfacesListUtil;
import com.xczhihui.bxg.online.api.service.CityService;
import com.xczhihui.bxg.online.api.service.CommonApiService;
import com.xczhihui.bxg.online.api.service.UserCoinService;
import com.xczhihui.bxg.online.api.vo.UserAddressManagerVo;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import com.xczhihui.user.center.bean.TokenExpires;
import com.xczhihui.wechat.course.service.IMyInfoService;
import com.xczhihui.wechat.course.vo.OnlineUserVO;

/**
 * 用户controller
 * @author zhangshixiong
 * @date 2017-02-22
 */
@Controller
@RequestMapping(value = "/xczh/set")
public class XzUserSetController {
	@Autowired
	private OnlineUserService onlineUserService;
	@Autowired
	private WxcpClientUserWxMappingService wxcpClientUserWxMappingService;
	@Autowired
	private UserCenterAPI userCenterAPI;
	@Autowired
	private WxcpClientUserService wxcpClientUserService;
	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private UserCoinService userCoinService;
	@Autowired
	private OLAttachmentCenterService service;
	@Autowired
	private AppBrowserService appBrowserService;
	@Autowired
	private CityService cityService;
	@Autowired
	private IMyInfoService myInfoService;
	@Autowired
	private CommonApiService commonApiService;
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(XzUserSetController.class);
	/**
	 * Description：用户中心保存接口
	 * @param request
	 * @param response
	 * @param user
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("userInfo")
	@ResponseBody
	@Transactional
	public ResponseObject userInfo(HttpServletRequest request,
			HttpServletResponse response,OnlineUserVO user)throws Exception{
		
        try{
         /**
          * 保存个人资料信息	
          */
    	 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
         MultipartFile fileMul = multipartRequest.getFile("file");
         if(fileMul!=null && !fileMul.isEmpty()){
             // 获得文件名：
             String filename = fileMul.getOriginalFilename();
             if(filename!=null && !"".equals(filename.trim())){
                 filename = filename.toLowerCase();
     			if (!filename.endsWith("image")&& !filename.endsWith("gif")&& !filename.endsWith("jpg")
     					&& !filename.endsWith("png")&& !filename.endsWith("bmp")) {
     				return ResponseObject.newErrorResponseObject("文件类型有误");
     			}
     			String contentType =  fileMul.getContentType();//文件类型
     			byte [] bs = fileMul.getBytes();
     			String projectName="other";
     			String fileType="1"; //图片类型了
     			String headImgPath = service.upload(null, //用户中心的用户ID
 				projectName, filename,contentType, bs,fileType,null);
     			LOGGER.info("文件路径——path:"+headImgPath);
     			user.setSmallHeadPhoto(headImgPath);
             }
          }
			OnlineUser u = appBrowserService.getOnlineUserByReq(request);
			if(u==null){
				return ResponseObject.newErrorResponseObject("获取用户信息有误");
			}
			user.setLoginName(u.getLoginName());
			user.setId(u.getId());
         /**
          * 更新信息
          */
         myInfoService.updateUserSetInfo(user);
         /*
          * 修改用户中心的方法
          * 直接调用，里面有封装好的方法
          */
         /*userCenterAPI.update(user.getLoginName(),
        		 user.getName(),user.getSex(),user.getEmail(),
        				 null, 10, 10);*/
         
         /**
          *  如果用户信息发生改变。
          *  更改缓存中的数据
          *  那么就改变token的信息，也就是redsei里面的信息
          */
         String token = request.getParameter("token");
         OnlineUser newUser = onlineUserService.findUserByLoginName(user.getLoginName());
         if(token !=null ){
        	 cacheService.delete(token);
             cacheService.set(token, newUser, TokenExpires.TenDay.getExpires());
         }else{
        	 request.getSession().setAttribute("_user_", newUser);
         }   
         /**
          * 更改微吼信息
          */
          if(StringUtils.isNotBlank(user.getName()) || 
        		  StringUtils.isNotBlank(user.getSmallHeadPhoto()) ){
        	  
        	  String weiHouResp = WeihouInterfacesListUtil.updateUser(user.getId(),
            		  null,newUser.getName(),newUser.getSmallHeadPhoto());
              if(weiHouResp == null){
            	  LOGGER.info("同步微吼昵称，头像失败");
              }
          }
         return ResponseObject.newSuccessResponseObject(user);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("后台处理流程异常");
        }
	}

	/**
	 * Description：用户中心保存接口 h5
	 * @param request
	 * @param response
	 * @param user
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("userInfoWechat")
	@ResponseBody
	@Transactional
	public ResponseObject userInfoWechat(HttpServletRequest request,
								   HttpServletResponse response,OnlineUserVO user)throws Exception{
		try{
			/**
			 * 保存个人资料信息
			 */
			OnlineUser u = appBrowserService.getOnlineUserByReq(request);
			if(u==null){
				return ResponseObject.newErrorResponseObject("获取用户信息有误");
			}
			user.setLoginName(u.getLoginName());
			user.setId(u.getId());
			String provinceCityName=user.getCityName();
			if(StringUtils.isNotBlank(provinceCityName)){
				String [] str =  provinceCityName.split(" ");
				//根据名字得到id，好惨
				if(str.length ==3){
					user.setProvinceName(str[0]);
					user.setCityName(str[1]);
					user.setCountyName(str[2]);
				}
			}
			/**
			 * 更新信息
			 */
			myInfoService.updateUserSetInfo(user);
			/*
			 * 修改用户中心的方法
			 * 直接调用，里面有封装好的方法
			 */
         /*userCenterAPI.update(user.getLoginName(),
        		 user.getName(),user.getSex(),user.getEmail(),
        				 null, 10, 10);*/

			/**
			 *  如果用户信息发生改变。
			 *  更改缓存中的数据
			 *  那么就改变token的信息，也就是redsei里面的信息
			 */
			String token = request.getParameter("token");
			OnlineUser newUser = onlineUserService.findUserByLoginName(user.getLoginName());
			if(token !=null ){
				cacheService.delete(token);
				cacheService.set(token, newUser, TokenExpires.TenDay.getExpires());
			}else{
				request.getSession().setAttribute("_user_", newUser);
			}
			/**
			 * 更改微吼信息
			 */
			if(StringUtils.isNotBlank(user.getName()) ||
					StringUtils.isNotBlank(user.getSmallHeadPhoto()) ){

				String weiHouResp = WeihouInterfacesListUtil.updateUser(user.getId(),
						null,newUser.getName(),newUser.getSmallHeadPhoto());
				if(weiHouResp == null){
					LOGGER.info("同步微吼昵称，头像失败");
				}
			}
			return ResponseObject.newSuccessResponseObject(user);
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("后台处理流程异常");
		}
	}
	
	
	/**
	 * 得到用户的地址管理列表
	 */
	@RequestMapping("getAddressAll")
	@ResponseBody
	public ResponseObject getAddressAll(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)
			throws Exception {
		
		OnlineUser ou = appBrowserService.getOnlineUserByReq(req, params);
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
	 * 得到所有都省、市、地区
	 */
	@RequestMapping("getAllProvinces")
	@ResponseBody
	public ResponseObject getAllProvinces(HttpServletRequest req,
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
	 * Description：身份信息
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping(value="jobVo")
	@ResponseBody
	public ResponseObject JobVo(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String group = req.getParameter("group");
		return ResponseObject.newSuccessResponseObject(commonApiService.getJob(group));
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
