package com.xczh.consumer.market.utils;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
//import com.xczh.distributed.common.annotation.Autowired;
//import com.xczh.distributed.common.bean.ResponseObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.bean.WxcpClientUserWxMapping;
import com.xczh.consumer.market.controller.OnlineUserController;
import com.xczh.consumer.market.dao.OnlineUserMapper;
import com.xczh.consumer.market.service.WxcpClientUserService;
import com.xczh.consumer.market.service.WxcpClientUserWxMappingService;
import com.xczh.consumer.market.vo.ItcastUser;
import com.xczh.consumer.market.wxpay.consts.WxPayConst;
import com.xczh.consumer.market.wxpay.util.Base64;
import com.xczh.consumer.market.wxpay.util.CommonUtil;
import com.xczhihui.common.util.SLEmojiFilter;
import com.xczhihui.common.util.WeihouInterfacesListUtil;
import com.xczhihui.bxg.online.api.service.CityService;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import com.xczhihui.user.center.bean.UserOrigin;
import com.xczhihui.user.center.bean.UserSex;
import com.xczhihui.user.center.bean.UserStatus;
import com.xczhihui.user.center.bean.UserType;

import net.sf.json.JSONObject;

public class ClientUserUtil {
	@SuppressWarnings("static-access")
	public static String setWxInfo (
			String auth_code,
			String user_mobile,
			String user_nick_name,	
			WxcpClientUserService wxcpClientUserService,
			WxcpClientUserWxMappingService wxcpClientUserWxMappingService,
			UserCenterAPI userCenterAPI,
			OnlineUserMapper onlineUserMapper,
			HttpServletRequest req, HttpServletResponse res
			) throws Exception {		
		try { 			
			String code_buffer = CommonUtil.getOpenId(auth_code);
			JSONObject jsonObject = JSONObject.fromObject(code_buffer);//Map<String, Object> access_info =GsonUtils.fromJson(code_buffer, Map.class);
			String access_token = (String)jsonObject.get("access_token");
			String expires_in = String.valueOf(jsonObject.get("expires_in"));
			String refresh_token = (String)jsonObject.get("refresh_token");
			String openid = (String)jsonObject.get("openid");
			String scope = (String)jsonObject.get("scope");
			
			Base64 base64 = new Base64();
			if(user_nick_name != null) {			
				byte[] bytes = base64.decode(user_nick_name);
				user_nick_name = new String(bytes,0,bytes.length,"UTF-8");  
			}		
				
			String public_id = WxPayConst.gzh_appid ;
			String public_name = WxPayConst.appid4name ;
			
			WxcpClientUserWxMapping m = wxcpClientUserWxMappingService.
					getWxcpClientUserWxMappingByOpenId(openid);
			
			String accessToken_buffer =  CommonUtil.getAccessToken();
			jsonObject = JSONObject.fromObject(accessToken_buffer);
			System.out.println("access_token:"+jsonObject.get("access_token"));
			CommonUtil.getUserManagerGetInfo(accessToken_buffer, openid);
			
			
			String user_buffer =  CommonUtil.getUserInfo(access_token,openid);
			if(null == m){
				WxcpClientUserWxMapping wxcpClientUserWxMapping = new WxcpClientUserWxMapping();
				wxcpClientUserWxMapping.setWx_id(UUID.randomUUID().toString().replace("-", ""));
				wxcpClientUserWxMapping.setNickname(user_nick_name);
				wxcpClientUserWxMapping.setWx_public_id(public_id);
				wxcpClientUserWxMapping.setWx_public_name(public_name);
				
				
				jsonObject = JSONObject.fromObject(user_buffer);//Map<String, Object> user_info =GsonUtils.fromJson(user_buffer, Map.class);
				String openid_ = (String)jsonObject.get("openid");
				String nickname_ = (String)jsonObject.get("nickname");
				nickname_ = SLEmojiFilter.filterEmoji(nickname_);
				
				String sex_ = String.valueOf(jsonObject.get("sex"));
				String language_ = (String)jsonObject.get("language");
				String city_ = (String)jsonObject.get("city");
				String province_ = (String)jsonObject.get("province");
				String country_ = (String)jsonObject.get("country");
				String headimgurl_ = (String)jsonObject.get("headimgurl");
				//String privilege_ = (String)jsonObject.get("privilege");
				String unionid_ = (String)jsonObject.get("unionid");
				
				System.out.println("unionid_:"+unionid_);
				
				if(nickname_ == null) {
                    nickname_ = user_nick_name;
                }
				//WxcpClientUserWxMapping wxcpClientUserWxMapping = new WxcpClientUserWxMapping();
				wxcpClientUserWxMapping.setOpenid(openid_);
				wxcpClientUserWxMapping.setNickname(nickname_);
				wxcpClientUserWxMapping.setSex(sex_);
				wxcpClientUserWxMapping.setLanguage(language_);
				wxcpClientUserWxMapping.setCity(city_);
				wxcpClientUserWxMapping.setProvince(province_);
				wxcpClientUserWxMapping.setCountry(country_);
				wxcpClientUserWxMapping.setHeadimgurl(headimgurl_);
				wxcpClientUserWxMapping.setProvince(province_);
				wxcpClientUserWxMapping.setUnionid(unionid_);	
				
				int ret = wxcpClientUserWxMappingService.insert(wxcpClientUserWxMapping);
				
				
				if(ret == 0) {
                    return code_buffer;
                } else {
                    return null;
                }
			}else{
				//
				//微信端进入页面即登录---20170727---yuruixin
				String clientId = m.getClient_id();
				if(clientId != null && onlineUserMapper!=null && userCenterAPI != null){
					OnlineUser user = onlineUserMapper.findUserById(clientId);
					Token t = userCenterAPI.wechatLogin(user.getLoginName());
					user.setTicket(t.getTicket());
					new OnlineUserController().onlogin(req, res, t, user);
				}
			}
			 return code_buffer;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}	
	
	/**
	 * Description：
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	public static OnlineUser addWxInfoByCode(
			String access_token,String openid,WxcpClientUserWxMappingService wxcpClientUserWxMappingService,
			UserCenterAPI userCenterAPI,OnlineUserMapper onlineUserMapper,
			HttpServletRequest req, HttpServletResponse res
			) throws Exception {		
		try { 			
//			String code_buffer = CommonUtil.getOpenId(auth_code);		
//			JSONObject jsonObject = JSONObject.fromObject(code_buffer);//Map<String, Object> access_info =GsonUtils.fromJson(code_buffer, Map.class);
//			String access_token = (String)jsonObject.get("access_token");
//			String expires_in = String.valueOf(jsonObject.get("expires_in"));
//			String refresh_token = (String)jsonObject.get("refresh_token");
//			String openid = (String)jsonObject.get("openid");
//			String scope = (String)jsonObject.get("scope");
			
//			WX get access_token:F4spz-qURMp8ymyHxxz93PcCm64VWjh6TfOmgIU7xRgkEO6a9K5HwO4d2Dium-SThBo3O35s8N-4zlqExcoJwQ
//			WX get openid:oN9qS1bvUngZ_49YFIamssdhP9Co
			
			String public_id = WxPayConst.gzh_appid ;
			String public_name = WxPayConst.appid4name ;
			/**
			 * 通过微信得到用户基本信息
			 */
			String user_buffer =  CommonUtil.getUserInfo(access_token,openid);
			
			JSONObject jsonObject = JSONObject.fromObject(user_buffer);//Map<String, Object> user_info =GsonUtils.fromJson(user_buffer, Map.class);
			String openid_ = (String)jsonObject.get("openid");
			String nickname_ = (String)jsonObject.get("nickname");
			nickname_ = SLEmojiFilter.filterEmoji(nickname_);
			
			String sex_ = String.valueOf(jsonObject.get("sex"));
			String language_ = (String)jsonObject.get("language");
			String city_ = (String)jsonObject.get("city");
			String province_ = (String)jsonObject.get("province");
			String country_ = (String)jsonObject.get("country");
			String headimgurl_ = (String)jsonObject.get("headimgurl");
			//String privilege_ = (String)jsonObject.get("privilege");  //微信特权信息
			String unionid_ = (String)jsonObject.get("unionid");
			
			WxcpClientUserWxMapping m = wxcpClientUserWxMappingService.getWxcpClientUserWxMappingByOpenId(openid);
			/**
			 * 如果存在，那么就不添加了。
			 */
			if(null == m){
				WxcpClientUserWxMapping wxcpClientUserWxMapping = new WxcpClientUserWxMapping();
				wxcpClientUserWxMapping.setWx_id(UUID.randomUUID().toString().replace("-", ""));
				wxcpClientUserWxMapping.setWx_public_id(public_id);
				wxcpClientUserWxMapping.setWx_public_name(public_name);
				wxcpClientUserWxMapping.setOpenid(openid_);
				wxcpClientUserWxMapping.setNickname(nickname_);
				wxcpClientUserWxMapping.setSex(sex_);
				wxcpClientUserWxMapping.setLanguage(language_);
				wxcpClientUserWxMapping.setCity(city_);
				wxcpClientUserWxMapping.setProvince(province_);
				wxcpClientUserWxMapping.setCountry(country_);
				wxcpClientUserWxMapping.setHeadimgurl(headimgurl_);
				wxcpClientUserWxMapping.setProvince(province_);
				wxcpClientUserWxMapping.setUnionid(unionid_);	
				//wxcpClientUserWxMapping.setClient_id(client_id);
				wxcpClientUserWxMappingService.insert(wxcpClientUserWxMapping);
			}		
			/*
			 * 判断这个uninonid是否在user表中存在
			 */
			OnlineUser ou =  onlineUserMapper.findOnlineUserByUnionid(unionid_);
			if(ou == null){
				/**
				 * 向用户中心添加数据
				 *  用户名、 密码、昵称、性别、邮箱、手机号
				 *  第三方登录的用户名和密码是opendi
				 */
/*				userCenterAPI.regist(openid_, unionid_,nickname_, UserSex.parse(falgSex), null,
						null, UserType.COMMON, UserOrigin.online, UserStatus.NORMAL);*/
				
				ItcastUser iu =  userCenterAPI.getUser(openid_);
				/**
				 * 保存用户信息到user表中。保存微信的unionid_如果这个用户表中存在这个id，那么说明已经登录过了！查找用户就ok。
				 */
				OnlineUser u = new OnlineUser();
				u.setId(UUID.randomUUID().toString().replace("-", ""));
				u.setSex(Integer.parseInt(sex_));
				u.setUnionId(unionid_);
				u.setStatus(0);
				u.setCreateTime(new Date());
				u.setDelete(false);
				u.setName(nickname_);   //微信名字
				u.setSmallHeadPhoto(headimgurl_);//微信头像
				u.setVisitSum(0);
				u.setStayTime(0);
				u.setUserType(0);
				u.setOrigin("weixin");
				u.setMenuId(-1);
				u.setCreateTime(new Date());
				u.setType(1);

				String weihouUserId = WeihouInterfacesListUtil.createUser(u.getId(), 
						WeihouInterfacesListUtil.MOREN, u.getName(), u.getSmallHeadPhoto());
				
				u.setVhallId(weihouUserId);  //微吼id
				u.setVhallPass(WeihouInterfacesListUtil.MOREN);        //微吼密码
				//u.setVhallName(u.getId());         //第三方id  
				u.setVhallName(u.getName());
				u.setPassword(unionid_);     
				u.setUserCenterId(iu.getId());
				u.setLoginName(openid_);
				
				//onlineUserMapper.addOnlineUser(u); 
				return u;
			}else{
				return null;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}	
	
	
	public static String saveWxPublicInfo (
			String auth_code,
			String user_mobile,
			String user_nick_name,	
			WxcpClientUserService wxcpClientUserService,
			WxcpClientUserWxMappingService wxcpClientUserWxMappingService,
			UserCenterAPI userCenterAPI,
			OnlineUserMapper onlineUserMapper,
			HttpServletRequest req, HttpServletResponse res,
			CityService cityService
			) throws Exception {		
		try { 			
			String code_buffer = CommonUtil.getOpenId(auth_code);
			JSONObject jsonObject = JSONObject.fromObject(code_buffer);//Map<String, Object> access_info =GsonUtils.fromJson(code_buffer, Map.class);
			String access_token = (String)jsonObject.get("access_token");
			String expires_in = String.valueOf(jsonObject.get("expires_in"));
			String refresh_token = (String)jsonObject.get("refresh_token");
			String openid = (String)jsonObject.get("openid");
			String scope = (String)jsonObject.get("scope");
			

			Base64 base64 = new Base64();
			if(user_nick_name != null) {			
				byte[] bytes = base64.decode(user_nick_name);
				user_nick_name = new String(bytes,0,bytes.length,"UTF-8");  
			}		
			String public_id = WxPayConst.gzh_appid ;
			String public_name = WxPayConst.appid4name ;
			
			String user_buffer =  CommonUtil.getUserInfo(access_token,openid);
			
			jsonObject = JSONObject.fromObject(user_buffer);//Map<String, Object> user_info =GsonUtils.fromJson(user_buffer, Map.class);
			String openid_ = (String)jsonObject.get("openid");
			String nickname_ = (String)jsonObject.get("nickname");
			nickname_ = SLEmojiFilter.filterEmoji(nickname_);
			
			String sex_ = String.valueOf(jsonObject.get("sex"));
			String language_ = (String)jsonObject.get("language");
			String city_ = (String)jsonObject.get("city");
			String province_ = (String)jsonObject.get("province");
			String country_ = (String)jsonObject.get("country");
			String headimgurl_ = (String)jsonObject.get("headimgurl");
			//String privilege_ = (String)jsonObject.get("privilege");
			
			String unionid_ = (String)jsonObject.get("unionid");
			
/*			String accessToken_buffer =  CommonUtil.getAccessToken();
			jsonObject = JSONObject.fromObject(accessToken_buffer);
			System.out.println("access_token:"+jsonObject.get("access_token"));
			//通过access_token和openid得到unionid
			String manageruser_buffer = CommonUtil.getUserManagerGetInfo(access_token,openid);
			jsonObject = JSONObject.fromObject(manageruser_buffer);
			System.out.println("unionid====="+jsonObject.get("unionid"));*/
			
			
			WxcpClientUserWxMapping m = wxcpClientUserWxMappingService.
					getWxcpClientUserWxMappingByOpenId(openid);
			if(null == m){
				WxcpClientUserWxMapping wxcpClientUserWxMapping = new WxcpClientUserWxMapping();
				wxcpClientUserWxMapping.setWx_id(UUID.randomUUID().toString().replace("-", ""));
				wxcpClientUserWxMapping.setNickname(user_nick_name);
				wxcpClientUserWxMapping.setWx_public_id(public_id);
				wxcpClientUserWxMapping.setWx_public_name(public_name);
				if(nickname_ == null) {
                    nickname_ = user_nick_name;
                }
				wxcpClientUserWxMapping.setOpenid(openid_);
				wxcpClientUserWxMapping.setNickname(nickname_);
				wxcpClientUserWxMapping.setSex(sex_);
				wxcpClientUserWxMapping.setLanguage(language_);
				wxcpClientUserWxMapping.setCity(city_);
				wxcpClientUserWxMapping.setProvince(province_);
				wxcpClientUserWxMapping.setCountry(country_);
				wxcpClientUserWxMapping.setHeadimgurl(headimgurl_);
				wxcpClientUserWxMapping.setProvince(province_);
				wxcpClientUserWxMapping.setUnionid(unionid_);	
			    wxcpClientUserWxMappingService.insert(wxcpClientUserWxMapping);
			}
			/*
			 * 判断这个uninonid是否在user表中存在
			 */
			OnlineUser common = new OnlineUser();
			OnlineUser ou =  onlineUserMapper.findOnlineUserByUnionid(unionid_);
			if(ou == null){
				/**
				 * 向用户中心添加数据
				 *  用户名、 密码、昵称、性别、邮箱、手机号
				 *  第三方登录的用户名和密码是opendi
				 */
				
			    ItcastUser iu = userCenterAPI.getUser(openid_);
				if(iu == null){
					userCenterAPI.regist(openid_, unionid_,nickname_, UserSex.parse(Integer.parseInt(sex_)), null,
							null, UserType.COMMON, UserOrigin.ONLINE, UserStatus.NORMAL);
					iu =  userCenterAPI.getUser(openid_);
				}
				/**
				 * 保存用户信息到user表中。保存微信的unionid_如果这个用户表中存在这个id，那么说明已经登录过了！查找用户就ok。
				 */
				OnlineUser u = new OnlineUser();
				u.setId(UUID.randomUUID().toString().replace("-", ""));
				u.setSex(Integer.parseInt(sex_));
				u.setUnionId(unionid_);
				u.setStatus(0);
				u.setCreateTime(new Date());
				u.setDelete(false);
				u.setName(nickname_);   //微信名字
				u.setSmallHeadPhoto(headimgurl_);//微信头像
				u.setVisitSum(0);
				u.setStayTime(0);
				u.setUserType(0);
				u.setOrigin("weixin");
				u.setMenuId(-1);
				u.setCreateTime(new Date());
				u.setType(1);
				String uuid = UUID.randomUUID().toString().replace("-", "");
				String weihouUserId = WeihouInterfacesListUtil.createUser(u.getId(), WeihouInterfacesListUtil.MOREN, u.getName(), u.getSmallHeadPhoto());
				u.setVhallId(weihouUserId);  //微吼id
				u.setVhallPass(WeihouInterfacesListUtil.MOREN);        //微吼密码
				u.setVhallName(u.getName());         //第三方id  
				u.setPassword(unionid_);     
				u.setUserCenterId(iu.getId());
				u.setLoginName(openid_);
				/**
				 * 将从微信获取的省市区信息变为对应的id和name
				 */
				
				System.out.println("country_:"+country_+",province_:"+province_+",city_:"+city_);
				
				Map<String,Object> map = cityService.getSingProvinceByCode(country_);
				if(map!=null){
					Object objId = map.get("cid");
					int countryId = Integer.parseInt(objId.toString());
					u.setDistrict(countryId+"");
					map = cityService.getSingCityByCodeAndPid(province_, countryId);
					if(map!=null){
						objId = map.get("cid");
						Object objName = map.get("name");	
						int provinceId = Integer.parseInt(objId.toString());
						u.setProvince(provinceId+"");
						u.setProvinceName(objName.toString());
						map = cityService.getSingDistrictByCodeAndPid(city_, provinceId);
						if(map!=null){
							objId = map.get("cid");
							objName = map.get("name");
							int cityId = Integer.parseInt(objId.toString());
							u.setCity(cityId+"");
							u.setCityName(objName.toString());
						}
					}
				}
				onlineUserMapper.addOnlineUser(u);
				common = u;
			}else{
				common = ou;
			}
			Token t = userCenterAPI.wechatLogin(common.getLoginName());
			common.setTicket(t.getTicket());
			new OnlineUserController().onlogin(req, res, t, common);
			return code_buffer;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}	
	/**
	 * Description：保存微信信息，通过openid，判别用户是否一致了
	 * @param auth_code
	 * @param wxcpClientUserWxMappingService
	 * @return
	 * @return WxcpClientUserWxMapping
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	public static WxcpClientUserWxMapping saveWxInfo(String auth_code,WxcpClientUserWxMappingService wxcpClientUserWxMappingService){
		try {
			String code = auth_code;
			String code_buffer = CommonUtil.getOpenId(code);
			JSONObject jsonObject = JSONObject.fromObject(code_buffer);//Map<String, Object> access_info =GsonUtils.fromJson(code_buffer, Map.class);
			String access_token = (String)jsonObject.get("access_token");
			String expires_in = String.valueOf(jsonObject.get("expires_in"));
			String refresh_token = (String)jsonObject.get("refresh_token");
			String openid = (String)jsonObject.get("openid");
			String scope = (String)jsonObject.get("scope");
			
			String public_id = WxPayConst.gzh_appid ;
			String public_name = WxPayConst.appid4name ;
			
			String user_buffer =  CommonUtil.getUserInfo(access_token,openid);
			
			jsonObject = JSONObject.fromObject(user_buffer);//Map<String, Object> user_info =GsonUtils.fromJson(user_buffer, Map.class);
			String openid_ = (String)jsonObject.get("openid");
			String nickname_ = (String)jsonObject.get("nickname");
			nickname_ = SLEmojiFilter.filterEmoji(nickname_);
			
			String sex_ = String.valueOf(jsonObject.get("sex"));
			String language_ = (String)jsonObject.get("language");
			String city_ = (String)jsonObject.get("city");
			String province_ = (String)jsonObject.get("province");
			String country_ = (String)jsonObject.get("country");
			String headimgurl_ = (String)jsonObject.get("headimgurl");
			//String privilege_ = (String)jsonObject.get("privilege");
			
			String unionid_ = (String)jsonObject.get("unionid");
			
			WxcpClientUserWxMapping m = wxcpClientUserWxMappingService.getWxcpClientUserWxMappingByOpenId(openid);
			
			if(null == m){
				WxcpClientUserWxMapping wxcpClientUserWxMapping = new WxcpClientUserWxMapping();
				wxcpClientUserWxMapping.setWx_id(UUID.randomUUID().toString().replace("-", ""));
				wxcpClientUserWxMapping.setWx_public_id(public_id);
				wxcpClientUserWxMapping.setWx_public_name(public_name);
				wxcpClientUserWxMapping.setOpenid(openid_);
				wxcpClientUserWxMapping.setNickname(nickname_);
				wxcpClientUserWxMapping.setSex(sex_);
				wxcpClientUserWxMapping.setLanguage(language_);
				wxcpClientUserWxMapping.setCity(city_);
				wxcpClientUserWxMapping.setProvince(province_);
				wxcpClientUserWxMapping.setCountry(country_);
				wxcpClientUserWxMapping.setHeadimgurl(headimgurl_);
				wxcpClientUserWxMapping.setProvince(province_);
				wxcpClientUserWxMapping.setUnionid(unionid_);
				//TODO  这个地方要改下
				/*wxcpClientUserWxMapping.setClient_id(common.getId());*/
			    wxcpClientUserWxMappingService.insert(wxcpClientUserWxMapping);
			    return wxcpClientUserWxMapping;
			}else{
				return m;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Description：保存微信信息，通过unionId，判别用户是否一致了
	 * @param auth_code
	 * @param wxcpClientUserWxMappingService
	 * @return
	 * @return WxcpClientUserWxMapping
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	public static WxcpClientUserWxMapping saveWxInfoByUnionId(String auth_code,WxcpClientUserWxMappingService wxcpClientUserWxMappingService){
		try {
			String code = auth_code;
			String code_buffer = CommonUtil.getOpenId(code);
			JSONObject jsonObject = JSONObject.fromObject(code_buffer);//Map<String, Object> access_info =GsonUtils.fromJson(code_buffer, Map.class);
			String access_token = (String)jsonObject.get("access_token");
			String expires_in = String.valueOf(jsonObject.get("expires_in"));
			String refresh_token = (String)jsonObject.get("refresh_token");
			String openid = (String)jsonObject.get("openid");
			String scope = (String)jsonObject.get("scope");
			
			String public_id = WxPayConst.gzh_appid ;
			String public_name = WxPayConst.appid4name ;
			
			String user_buffer =  CommonUtil.getUserManagerGetInfo(access_token,openid);
			
			jsonObject = JSONObject.fromObject(user_buffer);//Map<String, Object> user_info =GsonUtils.fromJson(user_buffer, Map.class);
			String openid_ = (String)jsonObject.get("openid");
			String nickname_ = (String)jsonObject.get("nickname");
			nickname_ = SLEmojiFilter.filterEmoji(nickname_);
			
			String sex_ = String.valueOf(jsonObject.get("sex"));
			String language_ = (String)jsonObject.get("language");
			String city_ = (String)jsonObject.get("city");
			String province_ = (String)jsonObject.get("province");
			String country_ = (String)jsonObject.get("country");
			String headimgurl_ = (String)jsonObject.get("headimgurl");
			String unionid_ = (String)jsonObject.get("unionid");
			
			WxcpClientUserWxMapping m = wxcpClientUserWxMappingService.getWxcpClientUserByUnionId(unionid_);
			if(null == m){
				WxcpClientUserWxMapping wxcpClientUserWxMapping = new WxcpClientUserWxMapping();
				wxcpClientUserWxMapping.setWx_id(UUID.randomUUID().toString().replace("-", ""));
				wxcpClientUserWxMapping.setWx_public_id(public_id);
				wxcpClientUserWxMapping.setWx_public_name(public_name);
				wxcpClientUserWxMapping.setOpenid(openid_);
				wxcpClientUserWxMapping.setNickname(nickname_);
				wxcpClientUserWxMapping.setSex(sex_);
				wxcpClientUserWxMapping.setLanguage(language_);
				wxcpClientUserWxMapping.setCity(city_);
				wxcpClientUserWxMapping.setProvince(province_);
				wxcpClientUserWxMapping.setCountry(country_);
				wxcpClientUserWxMapping.setHeadimgurl(headimgurl_);
				wxcpClientUserWxMapping.setProvince(province_);
				wxcpClientUserWxMapping.setUnionid(unionid_);
				//TODO  这个地方要改下
				/*wxcpClientUserWxMapping.setClient_id(common.getId());*/
			    wxcpClientUserWxMappingService.insert(wxcpClientUserWxMapping);
			    return wxcpClientUserWxMapping;
			}else{
				return m;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
