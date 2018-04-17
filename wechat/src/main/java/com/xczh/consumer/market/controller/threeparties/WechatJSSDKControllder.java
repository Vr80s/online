package com.xczh.consumer.market.controller.threeparties;

import java.security.DigestException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.service.CacheService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.Sha1SignUtil;
import com.xczh.consumer.market.wxpay.consts.WxPayConst;
import com.xczh.consumer.market.wxpay.util.CommonUtil;
import com.xczh.consumer.market.wxpay.util.HttpsRequest;

import net.sf.json.JSONObject;
import weibo4j.http.HttpClient;

/**
 * 
 * ClassName: ThirdPartyCertificationController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年2月2日<br>
 */
@Controller
@RequestMapping(value = "/xczh/wechatJssdk")
public class WechatJSSDKControllder {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(WechatJSSDKControllder.class);
	
	
	public HttpClient client = new HttpClient();
	
	@Autowired
	private CacheService cacheService;
	
	
	/**
	 * Description：如果是微信公众号的话需要签名下微信提供的jsssdk，这样才能使用微信的内置的分享和其他功能
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("certificationSign")
	@ResponseBody
	public ResponseObject certificationSign(HttpServletRequest req, HttpServletResponse res, Map<String, String> params) throws Exception{
		
		String url = req.getParameter("url");
		String cache_access_token =  cacheService.get("access_token");
		String js_ticket_access_token = cacheService.get("js_ticket");
		if(cache_access_token ==null || js_ticket_access_token==null ||
                "js_ticket".equals(js_ticket_access_token)){
			String strLinkHome 	= "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET"
					.replace("appid=APPID", "appid="+ WxPayConst.gzh_appid).replace("secret=APPSECRET", "secret="+ WxPayConst.gzh_Secret);
			
			LOGGER.info("strLinkHome:"+strLinkHome);
			String out = "";
			StringBuffer buffer = HttpsRequest.httpsRequest(strLinkHome, "GET", out);
			
			LOGGER.info("buffer:"+buffer.toString());
			
			JSONObject jsonObject = JSONObject.fromObject(buffer.toString());//Map<String, Object> access_info =GsonUtils.fromJson(code_buffer, Map.class);
			String access_token = (String)jsonObject.get("access_token");
			strLinkHome = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi".replace("access_token=ACCESS_TOKEN","access_token="+access_token);
			buffer = HttpsRequest.httpsRequest(strLinkHome, "GET", out);
			jsonObject = JSONObject.fromObject(buffer.toString());
			js_ticket_access_token = (String)jsonObject.get("ticket");
			
			LOGGER.info(js_ticket_access_token);
			
			if(StringUtils.isNotBlank(access_token)){
				cacheService.set("access_token",access_token, 7100);
			}
			if(StringUtils.isNotBlank(js_ticket_access_token)){
				cacheService.set("js_ticket",js_ticket_access_token, 7100);
			}
		}
		String nostr = CommonUtil.CreateNoncestr();
		Map<String,Object> map = new HashMap<String,Object>();
    	map.put("noncestr", nostr);
    	map.put("jsapi_ticket", js_ticket_access_token);
    	map.put("timestamp",System.currentTimeMillis()/1000+"");
    	map.put("url", url);
		try {
			//微信加密sh1 js
			String strSha1 = Sha1SignUtil.SHA1(map);
			
			map.put("sign", strSha1);
			map.put("appId", WxPayConst.gzh_appid);

			LOGGER.info("strSha1"+strSha1);
			LOGGER.info("map："+map);
		} catch (DigestException e) {
			e.printStackTrace();
			 return ResponseObject.newErrorResponseObject("获取初始化信息数据有误");
		} 
	   return ResponseObject.newSuccessResponseObject(map);
	}
	
	
}
