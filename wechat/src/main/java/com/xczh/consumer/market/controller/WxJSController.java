package com.xczh.consumer.market.controller;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.bean.WxcpClientUserWxMapping;
import com.xczh.consumer.market.bean.WxcpConcernInfo;
import com.xczh.consumer.market.bean.WxcpWxJsconfig;
import com.xczh.consumer.market.service.CacheService;
import com.xczh.consumer.market.service.WxcpConcernInfoService;
import com.xczh.consumer.market.service.WxcpWxJsconfigService;
import com.xczh.consumer.market.utils.ClientUserUtil;
import com.xczh.consumer.market.utils.ConfigUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.Sha1SignUtil;
import com.xczh.consumer.market.utils.Token;
import com.xczh.consumer.market.vo.ItcastUser;
import com.xczh.consumer.market.wxpay.consts.WxPayConst;
import com.xczh.consumer.market.wxpay.util.CommonUtil;
import com.xczh.consumer.market.wxpay.util.HttpsRequest;
import com.xczhihui.user.center.bean.TokenExpires;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.security.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/bxg/wxjs")
public class WxJSController {

	@Autowired
	private WxcpWxJsconfigService wxcpWxJsconfigService;
	 
	@Autowired
	private WxcpConcernInfoService wxcpConcernInfoService;
	
	@Autowired
	private CacheService cacheService;

	@Value("${returnOpenidUri}")
	private String returnOpenidUri;
	
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
	@RequestMapping("h5JSSDK")
	@ResponseBody
	public ResponseObject h5JSSDK(HttpServletRequest req, HttpServletResponse res, Map<String, String> params) throws Exception{
		
		String url = req.getParameter("url");
		
		String cache_access_token =  cacheService.get("access_token");
		String js_ticket_access_token = cacheService.get("js_ticket");
		
		if(cache_access_token ==null || js_ticket_access_token==null || 
				js_ticket_access_token.equals("js_ticket")){
			String strLinkHome 	= "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET"
					.replace("appid=APPID", "appid="+ WxPayConst.gzh_appid).replace("secret=APPSECRET", "secret="+ WxPayConst.gzh_Secret);
			
			System.out.println("strLinkHome:"+strLinkHome);
			String out = "";
			StringBuffer buffer = HttpsRequest.httpsRequest(strLinkHome, "GET", out);
			
			System.out.println("buffer:"+buffer.toString());
			
			JSONObject jsonObject = JSONObject.fromObject(buffer.toString());//Map<String, Object> access_info =GsonUtils.fromJson(code_buffer, Map.class);
			String access_token = (String)jsonObject.get("access_token");
			strLinkHome = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi".replace("access_token=ACCESS_TOKEN","access_token="+access_token);
			buffer = HttpsRequest.httpsRequest(strLinkHome, "GET", out);
			jsonObject = JSONObject.fromObject(buffer.toString());
			js_ticket_access_token = (String)jsonObject.get("ticket");
			
			System.out.println(js_ticket_access_token);
			
			if(StringUtils.hasText(access_token)){
				cacheService.set("access_token",access_token, 7100);
			}
			if(StringUtils.hasText(js_ticket_access_token)){
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

			System.out.println("strSha1"+strSha1);
			System.out.println("map："+map);
		} catch (DigestException e) {
			e.printStackTrace();
			 return ResponseObject.newErrorResponseObject("获取初始化信息数据有误");
		} 
	   return ResponseObject.newSuccessResponseObject(map);
	}
	/**
	 * Description：调用微信信息获取到用户基本信息。
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("h5BsGetCodeUrl")
	public void getOpenId(HttpServletRequest req, HttpServletResponse res, Map<String, String> params) throws Exception{
		
		String strLinkHome 	= "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WxPayConst.gzh_appid+"&redirect_uri="+returnOpenidUri+"/bxg/wxpay/h5GetOpenid&response_type=code&scope=snsapi_userinfo&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect".replace("appid=APPID", "appid="+ WxPayConst.gzh_appid);
		
		System.out.println("strLinkHome:"+strLinkHome);
		//存到session中，如果用户回调成功
		res.sendRedirect(strLinkHome);
	}

	
	/**
	 * Description：来自分享的调用   -- 主要是为了，存在的用户自动登录，并且直接去课程的页面。不存在的用户让绑定手机号
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("h5ShareGetWxOpenId")
	public void getShareOpenId(HttpServletRequest req, HttpServletResponse res) throws Exception{
		
		String	courseId = req.getParameter("courseId");
		String	type = req.getParameter("type");
		String	lineState = req.getParameter("lineState");                                                                                                                           
		String courseId_type_lineState =  courseId+"_"+type+"_"+lineState;
		String strLinkHome 	= "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WxPayConst.gzh_appid+"&redirect_uri="+returnOpenidUri+"/bxg/wxpay/h5ShareGetWxUserInfo?courseId_type_lineState="+courseId_type_lineState+"&response_type=code&scope=snsapi_userinfo&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect".replace("appid=APPID", "appid="+ WxPayConst.gzh_appid);
		System.out.println("strLinkHome:"+strLinkHome);
		//存到session中，如果用户回调成功
		res.sendRedirect(strLinkHome);
	}
	
	
	/**
	 * Description：调用微信信息获取到用户基本信息
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("h5BsGetCodeUrlReqParams")
	public void getOpenIdReqParams(HttpServletRequest req, HttpServletResponse res, Map<String, String> params) throws Exception{

		String userName = req.getParameter("username");
		if(userName ==null){
			OutputStream outputStream = res.getOutputStream();//获取OutputStream输出流
			res.setHeader("content-type", "text/html;charset=UTF-8");//通过设置响应头控制浏览器以UTF-8的编码显示数据，如果不加这句话，那么浏览器显示的将是乱码
	        /**
	         * getBytes()方法如果不带参数，那么就会根据操作系统的语言环境来选择转换码表，如果是中文操作系统，那么就使用GB2312的码表
	         */
	        byte[] dataByteArr = "参数异常".getBytes("UTF-8");//将字符转换成字节数组，指定以UTF-8编码进行转换
	        outputStream.write(dataByteArr);//使用OutputStream流向客户端输出字节数组
		}
		String strLinkHome 	=
			"https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WxPayConst.gzh_appid+
			"&redirect_uri="+returnOpenidUri+
			"/bxg/wxpay/h5GetCodeAndUserMobile?userName="+userName+
		    "&response_type=code&scope=snsapi_userinfo&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect";
		
		System.out.println("strLinkHome:"+strLinkHome);
		/*
		 * 	   这个地方需要用到微信重定向了，因为需要用户授权了。
		 * 存到session中，如果用户回调成功。一旦转发后，
		 * 那么就存在了两个请求和两个响应了。
		 */
		res.sendRedirect(strLinkHome);
	}

	/**
	 * Description：调用微信接口，静默获取用户openid。
	 * 不同的手机号登录呢。
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("h5BsGetCodeUrlAndBase")
	public void h5BsGetCodeUrlAndBase(HttpServletRequest req, HttpServletResponse res, Map<String, String> params) throws Exception{
		
		/**
		 * "?share="+share+"&courseid="+courseid+"" +
		 *				"&shareCourseId="+shareCourseId;
		 *				
		 */
		
		String share = req.getParameter("share");
		String courseid = req.getParameter("courseid");
		String shareCourseId = req.getParameter("shareCourseId");
		
		String strLinkHome 	=
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WxPayConst.gzh_appid+"&"
				+ "redirect_uri="+returnOpenidUri+"/bxg/wxpay/h5BsGetCodeUrlAndBase?"
				+ "share="+share+"&courseid="+courseid+"&shareCourseId="+shareCourseId
				+ "&response_type=code&scope=snsapi_base&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect";
		
		System.out.println("h5BsGetCodeUrlAndBase:"+strLinkHome);
		
		res.sendRedirect(strLinkHome);
	}
	
	
	@RequestMapping("setWxMenu")
	@ResponseBody
	public ResponseObject setWxMenu (HttpServletRequest req, HttpServletResponse res, Map<String, String> params) throws Exception {
		
		String access_token = req.getParameter("access_token");
		access_token ="6mcGpY9ORGOF_Vw7s0VdYnSoNIaOTeYnJWrHAcb1Xaihi7dIDi-SqjV6B_uY4FJ_N6PT2NKtYKQjCWvVB5OTptOea-JBV13UEfYmskk2L1wTBCeABADLM";
		if(access_token == null || access_token.isEmpty()) return null;
		String strUrl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN"
				.replace("access_token=ACCESS_TOKEN", "access_token=" + access_token);
		
		String strLinkHome 	= 	" \"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=http://m.ipandatcm.com/bxg/wxpay/h5GetOpenid&response_type=code&scope=snsapi_userinfo&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect\" "
								.replace("appid=APPID", "appid=wx81c7ce773415e00a");
		String strMyCenter 	= 	" \"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=http://m.ipandatcm.com/bxg/wxpay/h5GetOpenidForPersonal&response_type=code&scope=snsapi_userinfo&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect\" "
				.replace("appid=APPID", "appid=wx81c7ce773415e00a");
//		String strMyShare 	= 	" \"url\":\"+"returnOpenidUri"+/Views/h5/my_share.html\" ";
//		String strMyCenter 	= 	" \"url\":\"+"returnOpenidUri"+/bxg/page/personal\" ";
		
		StringBuilder sb = new StringBuilder();
		sb.append("	{																");
		sb.append("	\"button\":                                                		");
		sb.append("	[                                                         		");
		sb.append(" 	{	                                                  		");
		sb.append("    	\"type\":\"view\",                                     		");
		sb.append("    	\"name\":\"在线课堂\",                                   		");		
		sb.append(strLinkHome);														//sb.append("    	\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx42fe48fd3935151e&redirect_uri=+"returnOpenidUri"+/bxg/wxpay/h5GetOpenid&response_type=code&scope=snsapi_userinfo&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect\"           ");//sb.append("          \"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe8667c589b5bdc0c&redirect_uri=+"returnOpenidUri"+/bxg/wxpay/h5GetOpenid&response_type=code&scope=snsapi_base&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect\"           ");
		sb.append("    	},                                                      	");
//		sb.append("     {	                                                    	");
//		sb.append("    	\"type\":\"view\",                                     		");
//		sb.append("    	\"name\":\"我的分享\",                                      	");
//		sb.append(strMyShare);														//sb.append("    	\"url\":\"+"returnOpenidUri"+/Views/h5/my_center.html\"	");
//		sb.append("    	},                                                       	");
		sb.append("     {	                                                    	");
		sb.append("    	\"type\":\"view\",                                     		");
		sb.append("    	\"name\":\"个人中心\",                                      	");
		sb.append(strMyCenter);														//sb.append("    	\"url\":\"+"returnOpenidUri"+/Views/h5/my_center.html\"   ");
		sb.append("    	}                                                       	");
		sb.append("	]                                                               ");
		sb.append("	}                                                               ");		
			
		HttpsRequest request = new HttpsRequest();
		JSONObject jsonObject = JSONObject.fromObject(sb.toString());
		String buffer = request.sendPost2(strUrl, jsonObject);		
		return ResponseObject.newSuccessResponseObject(buffer);
	}	
		
	@RequestMapping("checkInWx")
	public void checkInWx (HttpServletRequest req, HttpServletResponse res, Map<String, String> params) throws Exception {
		
		// 微信加密签名
	    String signature = req.getParameter("signature");	    
	    // 随机字符串
	    String echostr = req.getParameter("echostr");
	    // 时间戳
	    String timestamp = req.getParameter("timestamp");
	    // 随机数
	    String nonce = req.getParameter("nonce");
	    
		ConfigUtil cfg = new ConfigUtil(req.getSession());
		String wxToken = cfg.getConfig("wxToken");	
		System.out.println("wxToken=" + wxToken);
	    
	    String[] str = { wxToken, timestamp, nonce };
	    Arrays.sort(str); // 字典序排序
	    String bigStr = str[0] + str[1] + str[2];
	       
        //SHA1加密
        //String digest = new SHA1().getDigestOfString(bigStr.getBytes()).toLowerCase();
	    
	    String digest = "";
	    try {
	    	MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();
	        crypt.update(bigStr.getBytes("UTF-8"));
	        digest = byteToHex(crypt.digest());
	        digest = digest.toLowerCase();
	    } catch (NoSuchAlgorithmException e) {
	    	e.printStackTrace();
	    } catch (UnsupportedEncodingException e) {
	    	e.printStackTrace();
	    }
	    	    
	    //if(true) {
	    //	System.out.println("signature=" + signature);
	    //	System.out.println("echostr=" + echostr);
	    //	System.out.println("timestamp=" + timestamp);
	    //	System.out.println("nonce=" + nonce);
	    //	System.out.println("digest=" + digest);
	    //}
	    
	    // 确认请求来至微信
	    if (digest.equals(signature)) {
	    	res.getWriter().print(echostr);
	    } else {
	    	res.getWriter().print("error");
	    }
	   	    
	}
	
	@RequestMapping("getWxConcernList")
	@ResponseBody
	public ResponseObject getWxConcernList (HttpServletRequest req, HttpServletResponse res, Map<String, String> params) throws Exception {
		
		List<WxcpConcernInfo> listWxcpConcernInfo = new LinkedList<WxcpConcernInfo>();
		String access_token = req.getParameter("access_token");
		String public_id  = WxPayConst.gzh_appid;
		if(public_id == null || public_id.isEmpty() || access_token == null || access_token.isEmpty() )		
			return ResponseObject.newSuccessResponseObject(listWxcpConcernInfo);
		System.out.println("getWxConcernList->access_token->\r\n\t"+access_token);
		
		WxcpConcernInfo condConcernInfo = new WxcpConcernInfo();
		condConcernInfo.setPublic_id(public_id);
		listWxcpConcernInfo = wxcpConcernInfoService.select(condConcernInfo, null);
		if(listWxcpConcernInfo==null) listWxcpConcernInfo = new LinkedList<WxcpConcernInfo>();
		System.out.println("getWxConcernList->listWxcpConcernInfo->\r\n\t"+JSONArray.fromObject(listWxcpConcernInfo).toString());
		
		String strUrl = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid="
				.replace("access_token=ACCESS_TOKEN", "access_token=" + access_token);
		StringBuffer sbRet = HttpsRequest.httpsRequest(strUrl, "GET", null);//{"access_token":"ACCESS_TOKEN","expires_in":7200}//{"errcode":40013,"errmsg":"invalid appid"}
		if(sbRet==null) return ResponseObject.newSuccessResponseObject(listWxcpConcernInfo); 
		JSONObject jsonObject = JSONObject.fromObject(sbRet.toString());
		String total = jsonObject.getString("total");
		String count = jsonObject.getString("count");		
		JSONArray data = jsonObject.getJSONArray("data");//String data = (String)jsonObject.get("data");
		String next_openid = (String)jsonObject.getString("next_openid");
		if(total == null || count  == null || data == null /*|| next_openid == null*/) return ResponseObject.newSuccessResponseObject(listWxcpConcernInfo);
		System.out.println("getWxConcernList->/user/get?access_token->\r\n\t"+sbRet.toString());
		
		List<WxcpConcernInfo> listWxcpConcernInfoNew = new LinkedList<WxcpConcernInfo>();
		for(int i=0;i<data.size();i++) {
			WxcpConcernInfo item = new WxcpConcernInfo();
			//item.setConcern_id(UUID.randomUUID().toString().replace("-", ""));
			item.setOpen_id(data.getString(i));
			item.setPublic_id(public_id);
			listWxcpConcernInfoNew.add(item);
		}
		
		while(true) {
			if(next_openid == null || next_openid.trim().isEmpty()) break;
			strUrl = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID"
					.replace("access_token=ACCESS_TOKEN", "access_token=" + access_token)
					.replace("next_openid=NEXT_OPENID", "next_openid=" + next_openid);	
			
			sbRet = HttpsRequest.httpsRequest(strUrl, "GET", null);//{"access_token":"ACCESS_TOKEN","expires_in":7200}//{"errcode":40013,"errmsg":"invalid appid"}
			if(sbRet==null) break; 
			
			jsonObject = JSONObject.fromObject(sbRet.toString());
			total = jsonObject.getString("total");
			count = jsonObject.getString("count");		
			data = jsonObject.getJSONArray("data");//String data = (String)jsonObject.get("data");
			next_openid = (String)jsonObject.getString("next_openid");
			if(total == null || count  == null || data == null ) break;
			if(next_openid == null || (next_openid != null && next_openid.trim().length() == 0) ) break;
			
			System.out.println("getWxConcernList->/user/get?access_token->\r\n\t"+sbRet.toString());			
			
			for(int i=0;i<data.size();i++) {
				WxcpConcernInfo item = new WxcpConcernInfo();
				//item.setConcern_id(UUID.randomUUID().toString().replace("-", ""));
				item.setOpen_id(data.getString(i));
				item.setPublic_id(public_id);
				listWxcpConcernInfoNew.add(item);
			}
		}
		System.out.println("getWxConcernList->listWxcpConcernInfoNew->\r\n\t"+JSONArray.fromObject(listWxcpConcernInfoNew).toString());
		
		List<WxcpConcernInfo> listWxcpConcernInfoDel = new LinkedList<WxcpConcernInfo>();
		for(int i=0;i<listWxcpConcernInfo.size();i++) {
			int have = 0;
			for(int j=0;j<listWxcpConcernInfoNew.size();j++){
				if(listWxcpConcernInfo.get(i).getOpen_id().equals(listWxcpConcernInfoNew.get(j).getOpen_id())) {
					have = 1;break;
				}
			}
			if(0 == have) listWxcpConcernInfoDel.add(listWxcpConcernInfo.get(i));
		}
		for(int k=0;k<listWxcpConcernInfoDel.size();k++) {
			wxcpConcernInfoService.delete(listWxcpConcernInfoDel.get(k));
		}
		
		List<WxcpConcernInfo> listWxcpConcernInfoIns = new LinkedList<WxcpConcernInfo>();
		for(int i=0;i<listWxcpConcernInfoNew.size();i++) {
			int have = 0;
			for(int j=0;j<listWxcpConcernInfo.size();j++){
				if(listWxcpConcernInfoNew.get(i).getOpen_id().equals(listWxcpConcernInfo.get(j).getOpen_id())) {
					have = 1;break;
				}
			}
			if(0 == have) {
				listWxcpConcernInfoNew.get(i).setConcern_id(UUID.randomUUID().toString().replace("-", ""));
				listWxcpConcernInfoIns.add(listWxcpConcernInfoNew.get(i));
			}
		}
		for(int k=0;k<listWxcpConcernInfoIns.size();k++) {
			wxcpConcernInfoService.insert(listWxcpConcernInfoIns.get(k));
		}
		
		System.out.println("getWxConcernList->listWxcpConcernInfoIns->\r\n\t"+JSONArray.fromObject(listWxcpConcernInfoIns).toString());
		System.out.println("getWxConcernList->listWxcpConcernInfoDel->\r\n\t"+JSONArray.fromObject(listWxcpConcernInfoDel).toString());
		
		return ResponseObject.newSuccessResponseObject(listWxcpConcernInfo);
	}

	@RequestMapping("addWxConcernInfo")
	@ResponseBody
	public ResponseObject addWxConcernInfo (HttpServletRequest req, HttpServletResponse res, Map<String, String> params) throws Exception {
		
		List<WxcpConcernInfo> listWxcpConcernInfo = new LinkedList<WxcpConcernInfo>();
		String public_id  = WxPayConst.gzh_appid;
		String open_id  = req.getParameter("open_id");
		if(public_id == null || public_id.isEmpty() || open_id == null || open_id.isEmpty())		
			return ResponseObject.newSuccessResponseObject(-1);
		
		WxcpConcernInfo condConcernInfo = new WxcpConcernInfo();
		condConcernInfo.setPublic_id(public_id);
		condConcernInfo.setOpen_id(open_id);;
		listWxcpConcernInfo = wxcpConcernInfoService.select(condConcernInfo, null);
		if(listWxcpConcernInfo==null) listWxcpConcernInfo = new LinkedList<WxcpConcernInfo>();
		if(listWxcpConcernInfo != null && listWxcpConcernInfo.size()>0)
			return ResponseObject.newSuccessResponseObject(0);
		else 
			return ResponseObject.newSuccessResponseObject(-1);
	}
	
	@RequestMapping("getWxJsConfig")
	@ResponseBody
	public ResponseObject getWxJsConfig (HttpServletRequest req, HttpServletResponse res, Map<String, String> params) throws Exception {
		
		WxcpWxJsconfig wxcpWxJsconfig = new WxcpWxJsconfig();
		String openid = req.getParameter("openid");
		String jsconfig_url  = req.getParameter("jsconfig_url");
		if(openid == null || openid.isEmpty() || jsconfig_url == null || jsconfig_url.isEmpty())		
			return ResponseObject.newSuccessResponseObject(wxcpWxJsconfig);
		System.out.println("getWxJsConfig->openid->\r\n\t"+openid);
		System.out.println("getWxJsConfig->jsconfig_url->\r\n\t"+jsconfig_url);
		
		WxcpWxJsconfig condWxJsconfig = new WxcpWxJsconfig();
		condWxJsconfig.setOpenid(openid);
		condWxJsconfig.setAppid(WxPayConst.gzh_appid);
		String limit = "1";
		
		List<WxcpWxJsconfig> listWxcpWxJsconfig = wxcpWxJsconfigService.select(condWxJsconfig,limit) ;
		if(listWxcpWxJsconfig != null && listWxcpWxJsconfig.size() > 0) {
			/*??
			wxcpWxJsconfig = listWxcpWxJsconfig.get(0);
			Date create_time = wxcpWxJsconfig.getCreate_time();
			Calendar c = Calendar.getInstance(); 
			c.setTime(create_time);			
			c.add(Calendar.MINUTE, 99);//c.add(Calendar.HOUR, 2);
			create_time = c.getTime();
			Date now_time = new Date();
			if(create_time.getTime() > now_time.getTime()) {
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String expires_time = sdf.format(c.getTime());
				wxcpWxJsconfig.setExpires_time(expires_time);
				wxcpWxJsconfig.setExpires_timestamp(String.valueOf(c.getTime().getTime()));
				setWxRights(wxcpWxJsconfig);
				return ResponseObject.newSuccessResponseObject(wxcpWxJsconfig);
			}
			??*/
		}
		
		String access_token = "";
		if(true) {
			String strUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET"
					.replace("appid=APPID", "appid=" + WxPayConst.gzh_appid)
					.replace("secret=APPSECRET", "secret=" + WxPayConst.gzh_Secret);
			StringBuffer sbRet = HttpsRequest.httpsRequest(strUrl, "GET", null);//{"access_token":"ACCESS_TOKEN","expires_in":7200}//{"errcode":40013,"errmsg":"invalid appid"}
			if(sbRet==null) return ResponseObject.newSuccessResponseObject(wxcpWxJsconfig); 
			JSONObject jsonObject = JSONObject.fromObject(sbRet.toString());
			access_token = (String)jsonObject.get("access_token");
			//if(access_token==null || access_token.length()<=6) return ResponseObject.newSuccessResponseObject(wxcpWxJsconfig);//for test			
			if(access_token==null || access_token.length()<=6) access_token = "dWfPE4DxkXGEsM4AOVs8VMC_PGGVi490u5h9nbSlYy3-s8VMCPGGVi490sM4AOVdWfPE4Dxk";
			System.out.println("getWxJsConfig->access_token->\r\n\t"+sbRet.toString());
		}
		
		String jsapi_ticket = "";
		if(true) {
			String strUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi"		
					.replace("access_token=ACCESS_TOKEN", "access_token=" + access_token);
			StringBuffer sbRet = HttpsRequest.httpsRequest(strUrl, "GET", null);//{"errcode":0,"errmsg":"ok","ticket":"bxLdikRXVbTPdHSM05e5u5sUoXNKd8-41ZO3MhKoyN5OfkWITDGgnr2fwJ0m9E8NYzWKVZvdVtaUgWvsdshFKA","expires_in":7200}
			if(sbRet==null) return ResponseObject.newSuccessResponseObject(wxcpWxJsconfig); 
			JSONObject jsonObject = JSONObject.fromObject(sbRet.toString());
			//??String errcode = (String)jsonObject.get("errcode");
			//??if(errcode == null || !errcode.equals("0")) return ResponseObject.newSuccessResponseObject(wxcpWxJsconfig);
			jsapi_ticket = (String)jsonObject.get("ticket");
			//return ResponseObject.newSuccessResponseObject(wxcpWxJsconfig);//for test 
			if(jsapi_ticket==null || jsapi_ticket.length()<=6) jsapi_ticket = "sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg";
			System.out.println("getWxJsConfig->jsapi_ticket->\r\n\t"+sbRet.toString());
		}
				
		if(true) {			
			Map<String,String> mapRet = sign(jsapi_ticket,jsconfig_url);	
			
			wxcpWxJsconfig.setId(UUID.randomUUID().toString().replace("-", ""));
			wxcpWxJsconfig.setAppid(WxPayConst.gzh_appid);
			wxcpWxJsconfig.setTimestamp(mapRet.get("timestamp"));
			wxcpWxJsconfig.setNonce_str(mapRet.get("nonceStr"));
			wxcpWxJsconfig.setSignature(mapRet.get("signature"));
			wxcpWxJsconfig.setOpenid(openid);
			wxcpWxJsconfig.setAccess_token(access_token);
			wxcpWxJsconfig.setJsapi_ticket(jsapi_ticket);
			wxcpWxJsconfig.setJsconfig_url(jsconfig_url);
			wxcpWxJsconfig.setCreate_time(new Date());		
			
			Calendar c = Calendar.getInstance(); 
			c.setTime(wxcpWxJsconfig.getCreate_time());
			c.add(Calendar.MINUTE, 99);//c.add(Calendar.HOUR, 2);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String expires_time = sdf.format(c.getTime());
			wxcpWxJsconfig.setExpires_time(expires_time);	
			wxcpWxJsconfig.setExpires_timestamp(String.valueOf(c.getTime().getTime()));
			
			wxcpWxJsconfigService.insert(wxcpWxJsconfig);
			setWxRights(wxcpWxJsconfig);
		}
			
		return ResponseObject.newSuccessResponseObject(wxcpWxJsconfig);
	}
	
	public static void setWxRights(WxcpWxJsconfig wxcpWxJsconfig) {
		wxcpWxJsconfig.getJsApiList().add("'checkJsApi'");
		wxcpWxJsconfig.getJsApiList().add("'onMenuShareTimeline'");
		wxcpWxJsconfig.getJsApiList().add("'onMenuShareAppMessage'");
		wxcpWxJsconfig.getJsApiList().add("'onMenuShareQQ'");
		wxcpWxJsconfig.getJsApiList().add("'onMenuShareWeibo'");
		wxcpWxJsconfig.getJsApiList().add("'hideMenuItems'");
		wxcpWxJsconfig.getJsApiList().add("'showMenuItems'");
		wxcpWxJsconfig.getJsApiList().add("'hideAllNonBaseMenuItem'");
		wxcpWxJsconfig.getJsApiList().add("'showAllNonBaseMenuItem'");
		wxcpWxJsconfig.getJsApiList().add("'translateVoice'");
		wxcpWxJsconfig.getJsApiList().add("'startRecord'");
		wxcpWxJsconfig.getJsApiList().add("'stopRecord'");
		wxcpWxJsconfig.getJsApiList().add("'onRecordEnd'");
		wxcpWxJsconfig.getJsApiList().add("'playVoice'");
		wxcpWxJsconfig.getJsApiList().add("'pauseVoice'");
		wxcpWxJsconfig.getJsApiList().add("'stopVoice'");
		wxcpWxJsconfig.getJsApiList().add("'uploadVoice'");
		wxcpWxJsconfig.getJsApiList().add("'downloadVoice'");
		wxcpWxJsconfig.getJsApiList().add("'chooseImage'");
		wxcpWxJsconfig.getJsApiList().add("'previewImage'");
		wxcpWxJsconfig.getJsApiList().add("'uploadImage'");
		wxcpWxJsconfig.getJsApiList().add("'downloadImage'");
		wxcpWxJsconfig.getJsApiList().add("'getNetworkType'");
		wxcpWxJsconfig.getJsApiList().add("'openLocation'");
		wxcpWxJsconfig.getJsApiList().add("'getLocation'");
		wxcpWxJsconfig.getJsApiList().add("'hideOptionMenu'");
		wxcpWxJsconfig.getJsApiList().add("'showOptionMenu'");
		wxcpWxJsconfig.getJsApiList().add("'closeWindow'");
		wxcpWxJsconfig.getJsApiList().add("'scanQRCode'");
		wxcpWxJsconfig.getJsApiList().add("'chooseWXPay'");
		wxcpWxJsconfig.getJsApiList().add("'openProductSpecificView'");
		wxcpWxJsconfig.getJsApiList().add("'addCard'");
		wxcpWxJsconfig.getJsApiList().add("'chooseCard'");
		wxcpWxJsconfig.getJsApiList().add("'openCard'");
		wxcpWxJsconfig.getJsApiList().add("'onVoiceRecordEnd'");
		wxcpWxJsconfig.getJsApiList().add("'onVoicePlayEnd'");				
	}
	
    public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        //??String string1;
        String signature = "";

        /*??
        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        System.out.println(string1);
		??*/        
		String[] paramArr = new String[] { "jsapi_ticket=" + jsapi_ticket,
				"timestamp=" + timestamp, "noncestr=" + nonce_str, "url=" + url };
		Arrays.sort(paramArr);
		String content = paramArr[0].concat("&"+paramArr[1]).concat("&"+paramArr[2])
				.concat("&"+paramArr[3]);
        /*??
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(content.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
		??*/
		//??String gensignature = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			// 对拼接后的字符串进行 sha1 加密
			byte[] digest = md.digest(content.toString().getBytes());
			signature = byteToStr(digest);//??gensignature = byteToStr(digest);
			signature = signature.toLowerCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
				
        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        
        System.out.println("WxJSController->sign->\r\n\t");
        System.out.println("\tcontent="+content);
        System.out.println("\turl="+url);
        System.out.println("\tjsapi_ticket="+jsapi_ticket);
        System.out.println("\tnonceStr="+nonce_str);
        System.out.println("\ttimestamp="+timestamp);
        System.out.println("\tsignature="+signature);
        
        return ret;
    }
	
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
	
    /**
	 * 将字节数组转换为十六进制字符串
	 *
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 将字节转换为十六进制字符串
	 *
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];
		String s = new String(tempArr);
		return s;
	}    
	

    public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, MalformedURLException, ProtocolException, UnsupportedEncodingException, IOException {
    	    String strLinkHome 	= "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET"
				.replace("appid=APPID", "appid="+ WxPayConst.gzh_appid).replace("secret=APPSECRET", "secret="+ WxPayConst.gzh_Secret);

    		String out = "";
    		StringBuffer buffer = HttpsRequest.httpsRequest(strLinkHome, "GET", out);
    		JSONObject jsonObject = JSONObject.fromObject(buffer.toString());//Map<String, Object> access_info =GsonUtils.fromJson(code_buffer, Map.class);
    		String access_token = (String)jsonObject.get("access_token");

    		strLinkHome = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi".replace("access_token=ACCESS_TOKEN","access_token="+access_token);

    		buffer = HttpsRequest.httpsRequest(strLinkHome, "GET", out);
    		jsonObject = JSONObject.fromObject(buffer.toString());
    		String ticket = (String)jsonObject.get("ticket");

    		String nostr = CommonUtil.CreateNoncestr();
    		long timestamp = new Date().getTime();

    		Map<String,Object> map = new HashMap<String,Object>();
        	map.put("noncestr", nostr);
        	map.put("jsapi_ticket", ticket);
        	map.put("timestamp",System.currentTimeMillis()/1000+"");
        	///map.put("url", returnOpenidUri+"/xcviews/html/share.html");

    		try {
    			//微信加密sh1 js
				String strSha1 = Sha1SignUtil.SHA1(map);
				System.out.println(strSha1);
			} catch (DigestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
 