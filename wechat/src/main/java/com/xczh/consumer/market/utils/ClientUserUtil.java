package com.xczh.consumer.market.utils;

import java.util.UUID;
//import com.xczh.distributed.common.annotation.Autowired;
//import com.xczh.distributed.common.bean.ResponseObject;

import com.xczh.consumer.market.bean.WxcpClientUserWxMapping;
import com.xczh.consumer.market.service.WxcpClientUserWxMappingService;
import com.xczh.consumer.market.wxpay.consts.WxPayConst;
import com.xczh.consumer.market.wxpay.util.CommonUtil;
import com.xczhihui.bxg.common.util.SLEmojiFilter;

import net.sf.json.JSONObject;

public class ClientUserUtil {
	
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
