package com.xczh.consumer.market.controller.user;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.controller.live.CityController;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.CacheService;
import com.xczh.consumer.market.service.OLAttachmentCenterService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.service.WxcpClientUserService;
import com.xczh.consumer.market.service.WxcpClientUserWxMappingService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.Token;
import com.xczh.consumer.market.utils.UCCookieUtil;
import com.xczh.consumer.market.utils.XzStringUtils;
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
	 * 修改密码
	 * @param req
	 * @return
	 */
	@RequestMapping(value="editPassword")
	@ResponseBody
	public ResponseObject editPassword(HttpServletRequest req, HttpServletResponse res,
			@RequestParam("oldPassword")String oldPassword,
			@RequestParam("newPassword")String newPassword,
			@RequestParam("username")String username
			) throws Exception {

		try {
			if (!XzStringUtils.checkPassword(oldPassword)) {
				return ResponseObject.newErrorResponseObject("请输入6~18位的密码");
			}
			if (!XzStringUtils.checkPassword(newPassword)) {
				return ResponseObject.newErrorResponseObject("请输入6~18位的密码");
			}
			if (!XzStringUtils.checkPhone(username)) {
				return ResponseObject.newErrorResponseObject("请输入正确的手机号");
			}
			//更新用户密码
			userCenterAPI.updatePassword(username, oldPassword, newPassword);
			return ResponseObject.newSuccessResponseObject("修改密码成功");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject(e.getMessage());
		}
	}
	 /**
     * Description：给  vtype 包含 3 和   4  发送短信。
     *          如果是3的话，需要判断此手机号是否绑定，在发短信。
     *          如果是4的话，需要判断此要更改的手机号是否绑定，如果绑定就不发短信了。
     * @param req
     * @param res
     * @return
     * @throws Exception
     * @return ResponseObject
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
	@RequestMapping(value="phoneCheck")
	@ResponseBody
	public ResponseObject phoneCheck(HttpServletRequest req,
			@RequestParam("username")String username,
			@RequestParam("vtype")Integer vtype)throws Exception {
	
		/**
		 * 验证手机号
		 */
		if(!XzStringUtils.checkPhone(username)){
			return ResponseObject.newErrorResponseObject("请输入正确的手机号");
		}
		
		//短信验证码
		String str = onlineUserService.changeMobileSendCode(username,vtype);
		try {
			if("发送成功".equals(str)){
				return ResponseObject.newSuccessResponseObject(str);
			}else{
				return ResponseObject.newErrorResponseObject(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("获取错误信息啦"+e.getMessage());
			return ResponseObject.newErrorResponseObject("发送失败");
		}
	}
	
	 /**
     * Description：验证是否收到验证码啦
     * @param req
     * @param res
     * @return
     * @throws Exception
     * @return ResponseObject
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
	@RequestMapping(value="phoneCheckAndCode")
	@ResponseBody
	public ResponseObject phoneCheckAndCode(HttpServletRequest req,
			@RequestParam("username")String username,
			@RequestParam("code")String code,
			@RequestParam("vtype")Integer vtype) throws Exception {
		
		/**
		 * 验证手机号
		 */
		if(!XzStringUtils.checkPhone(username)){
			return ResponseObject.newErrorResponseObject("请输入正确的手机号");
		}
		
		//短信验证码
		ResponseObject checkCode = onlineUserService.changeMobileCheckCode(username, code,vtype);
		return checkCode;
	}
	
    /**
     * Description：更换手机号  --
     * @param req
     * @param res
     * @return
     * @throws Exception
     * @return ResponseObject
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
	@RequestMapping(value="updatePhone")
	@ResponseBody
	@Transactional
	public ResponseObject updatePhone(HttpServletRequest req,
			@RequestParam("oldUsername")String oldUsername,
			@RequestParam("newUsername")String newUsername,
			@RequestParam("code")String code,
			@RequestParam("vtype")Integer vtype) throws Exception {
		
		/**
		 * 验证手机号
		 */
		if(!XzStringUtils.checkPhone(oldUsername)
				|| !XzStringUtils.checkPhone(newUsername)){
			return ResponseObject.newErrorResponseObject("请输入正确的手机号");
		}
		
		//短信验证码
		ResponseObject checkCode = onlineUserService.changeMobileCheckCode(newUsername, code,vtype);
		if (!checkCode.isSuccess()) {
			return checkCode;
		}
		try {
			//更新用户信息 
			userCenterAPI.updateLoginName(oldUsername,newUsername);
			//更新用户表中的密码
			OnlineUser o = onlineUserService.findUserByLoginName(oldUsername);
			o.setLoginName(newUsername);
			onlineUserService.updateUserLoginName(o);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject(e.getMessage());
		}
		return ResponseObject.newSuccessResponseObject("更改手机号成功");
	}
	
	
	/**
	 * Description：根据 id 获取用户信息
	 * @param req
	 * @param userId
	 * @return
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping(value="getUserInfo")
	@ResponseBody
	public ResponseObject getUserInfo(HttpServletRequest req,
			@RequestParam("userId")String userId){
		try {
			OnlineUser ou = onlineUserService.findUserById(userId);
			if(ou != null){
				return ResponseObject.newSuccessResponseObject(ou);
			}else{
				return ResponseObject.newErrorResponseObject("获取用户信息有误");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.info("获取错误信息啦"+e.getMessage());
			return ResponseObject.newErrorResponseObject("信息有误");
		}
	}
	
	
	/**
	 * 判断用户是否登录着
	 * @param req
	 * @param res
	 * @param params
	 * @return 如果登录着返回当前用户，否则返回错误
	 * @throws Exception
	 */
	@RequestMapping("isLogined")
	@ResponseBody
	public ResponseObject isLogined(HttpServletRequest req, HttpServletResponse res, Map<String, String> params)throws Exception{
		Object ou = req.getSession().getAttribute("_user_");
		OnlineUser user = null;
		Token t = UCCookieUtil.readTokenCookie(req);
		if (ou != null && t != null) { //正常登录着
			String userId = ((OnlineUser)ou).getId();
			user = onlineUserService.findUserById(userId);
		} else if (ou == null) { //session过期了，续期
			user = onlineUserService.findUserByLoginName(t.getLoginName());
			req.getSession().setAttribute("_user_", user);
		} else if (t == null){ //cookie过期了，直接退出
			req.getSession().setAttribute("_user_", null);
		}
		if (user == null) {
			return ResponseObject.newErrorResponseObject("请登录");
		}
		return ResponseObject.newSuccessResponseObject(user);
	}
	
	
	
	/**
	 * 退出登录  -- 清理缓存啦
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("logout")
	@ResponseBody
	public ResponseObject logout(HttpServletRequest req, HttpServletResponse res, Map<String, String> params) throws Exception {
		UCCookieUtil.clearTokenCookie(res);
		req.getSession().setAttribute("_user_", null);
		String token = req.getParameter("token");
		if(token!=null){
			cacheService.delete(token);
		}
		return ResponseObject.newSuccessResponseObject("退出成功");
	}
	
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
		
		 if(!StringUtils.isNotBlank(user.getId())){
			 return ResponseObject.newErrorResponseObject("用户id不能为空");
		 }
		 LOGGER.info("user.getId():"+user.getId());
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
     			
     			JSONObject cardNegativeJson = JSONObject.parseObject(headImgPath);
     			user.setSmallHeadPhoto(cardNegativeJson.get("url").toString());
             }
          }

			String provinceCityName=user.getProvinceName();
			if(StringUtils.isNotBlank(provinceCityName)){
				String [] str =  provinceCityName.split(" ");
				//获取省市县
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
         OnlineUser newUser = onlineUserService.findUserById(user.getId());
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
         return ResponseObject.newSuccessResponseObject(newUser);
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
			/**
			 * 保存个人资料信息
			 */

			String provinceCityName=user.getProvinceName();
			if(StringUtils.isNotBlank(provinceCityName)){
				String [] str =  provinceCityName.split(" ");
				//获取省市县
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
			/**
			 *  如果用户信息发生改变。
			 *  更改缓存中的数据
			 *  那么就改变token的信息，也就是redsei里面的信息
			 */
			String token = request.getParameter("token");
			OnlineUser newUser = onlineUserService.findUserById(user.getId());
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
	}
	
	
	/**
	 * Description：微信端修改头像
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("wechatSaveHeadImg")
	@ResponseBody
	@Transactional
	public ResponseObject wechatSaveHeadImg(HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		//TODO
    	 String base64Data = request.getParameter("base64Data");
    	 String imageName = request.getParameter("imageName");
    	 //logger.debug("上传文件的数据："+base64Data);
         String dataPrix = "";
         String data = "";

         //logger.debug("对数据进行判断");
         if(base64Data == null || "".equals(base64Data)){
             throw new Exception("上传失败，上传图片数据为空");
         }else{
             String [] d = base64Data.split("base64,");
             if(d != null && d.length == 2){
                 dataPrix = d[0];
                 data = d[1];
             }else{
                 throw new Exception("上传失败，数据不合法");
             }
         }
         String suffix = "";
         if("data:image/jpeg;".equalsIgnoreCase(dataPrix)){//data:image/jpeg;base64,base64编码的jpeg图片数据
             suffix = ".jpg";
         } else if("data:image/x-icon;".equalsIgnoreCase(dataPrix)){//data:image/x-icon;base64,base64编码的icon图片数据
             suffix = ".ico";
         } else if("data:image/gif;".equalsIgnoreCase(dataPrix)){//data:image/gif;base64,base64编码的gif图片数据
             suffix = ".gif";
         } else if("data:image/png;".equalsIgnoreCase(dataPrix)){//data:image/png;base64,base64编码的png图片数据
             suffix = ".png";
         }else{
             throw new Exception("上传图片格式不合法");
         }

         //因为BASE64Decoder的jar问题，此处使用spring框架提供的工具包
        byte[] bs123 = Base64Utils.decodeFromString(data);
    	
        String projectName="other";
		String fileType="1"; //图片类型了
		
		Map<String,String> map = new HashMap<String,String>();
		String headImgPath = service.upload(null,projectName, imageName, suffix, bs123,fileType,null);
		
		JSONObject json = JSONObject.parseObject(headImgPath);
		LOGGER.info("文件路径——path:"+headImgPath);
		map.put("smallHeadPhoto", json.get("url").toString());
    	  
		OnlineUser user = appBrowserService.getOnlineUserByReq(request);
        onlineUserService.updateUserCenterData(user,map);
        /**
         * 更新微吼信息
         */
        String weiHouResp = WeihouInterfacesListUtil.updateUser(user.getId(),null,null,map.get("smallHeadPhoto"));
          
         /**
          * 如果用户信息发生改变。那么就改变token的信息，也就是redsei里面的信息
          */
         OnlineUser newUser =   onlineUserService.findUserByLoginName(user.getLoginName());
         request.getSession().setAttribute("_user_",newUser);
          
         if(weiHouResp == null){
        	  LOGGER.info("同步微吼头像失败");
         }
         return ResponseObject.newSuccessResponseObject(map);
	}
	
	
	
	/**
	 * 得到用户的地址管理列表
	 */
	@RequestMapping("getAddressAll")
	@ResponseBody
	public ResponseObject getAddressAll(HttpServletRequest req,
			HttpServletResponse res)
			throws Exception {
		
		OnlineUser ou = appBrowserService.getOnlineUserByReq(req);
		if(ou ==null ){
			return ResponseObject.newErrorResponseObject("登录失效");
		}
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
			HttpServletResponse res,
			@ModelAttribute UserAddressManagerVo udm)
			throws Exception {
		OnlineUser ou = appBrowserService.getOnlineUserByReq(req);
		udm.setUserId(ou.getId());

		if(!XzStringUtils.checkPhone(udm.getPhone())){
			return ResponseObject.newErrorResponseObject("请输入正确的手机号");
		}
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
			if(!XzStringUtils.checkPhone(udm.getPhone())){
			   return ResponseObject.newErrorResponseObject("请输入正确的手机号");
			}
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
