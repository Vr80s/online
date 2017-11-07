package com.xczh.consumer.market.wxpay.util;

import com.xczh.consumer.market.bean.WxcpWxTrans;
import com.xczh.consumer.market.wxpay.SignAbledBean;
import com.xczh.consumer.market.wxpay.consts.WxPayConst;
import com.xczh.consumer.market.wxpay.entity.PayInfo;
import com.xczh.consumer.market.wxpay.entity.SendRedPack;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * 微信支付相关通过用工具
 * 
 */
public class CommonUtil {

	public static String CreateNoncestr(int length) {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < length; i++) {
			Random rd = new Random();
			res += chars.indexOf(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	public static String CreateNoncestr() {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < 16; i++) {
			Random rd = new Random();
			res += chars.charAt(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	public static String FormatQueryParaMap(HashMap<String, String> parameters) throws SDKRuntimeException {
		String buff = "";
		try {
			List<Entry<String, String>> infoIds = new ArrayList<Entry<String, String>>(parameters.entrySet());

			Collections.sort(infoIds, new Comparator<Entry<String, String>>() {
				@Override
				public int compare(Entry<String, String> o1, Entry<String, String> o2) {
					return o1.getKey().compareTo(o2.getKey());
				}
			});

			for (int i = 0; i < infoIds.size(); i++) {
				Entry<String, String> item = infoIds.get(i);
				if (item.getKey() != "") {
					buff += item.getKey() + "=" + URLEncoder.encode(item.getValue(), "utf-8") + "&";
				}
			}
			if (buff.isEmpty() == false) {
				buff = buff.substring(0, buff.length() - 1);
			}
		} catch (Exception e) {
			throw new SDKRuntimeException(e.getMessage());
		}
		return buff;
	}

	/**
	 * 获取签名
	 *
	 * @param payInfo
	 * @return
	 * @throws Exception
	 */
	public static String getSign(SignAbledBean payInfo) throws Exception {
		String signTemp = orderParamByAscii(payInfo);
		String sign = MD5SignUtil.Sign(signTemp, WxPayConst.gzh_ApiKey);
		return sign;
	}

	/**
	 * 为了坑爹的微信package
	 *
	 * @author 周铭株 at 2016年5月16日 下午6:33:40
	 * @param payInfo
	 * @return
	 * @throws Exception
	 */
	public static String getSign(Map<String, String> payInfo) throws Exception {

		Iterator<String> itor = payInfo.keySet().iterator();
		List<String> sort = new ArrayList<String>();
		while (itor.hasNext())
			sort.add(itor.next());
		Collections.sort(sort);

		StringBuffer bf = new StringBuffer();
		Object fdvalue = null;
		boolean isft = true;// 是否第一次

		for (String fdname : sort) {
			fdvalue = payInfo.get(fdname);
			if (fdvalue != null && fdvalue.toString().length() > 0) {
				if (!isft)
					bf.append("&");
				else
					isft = false;
				bf.append(fdname + "=" + fdvalue);
			}
		}

		System.out.println("CommonUtil->getSign=\r\n\t"+bf);
		String sign = MD5SignUtil.Sign(bf.toString(), WxPayConst.gzh_ApiKey);
		return sign;
	}

	/**
	 * 按acsii码排序请求串
	 *
	 * @param bean
	 * @return
	 */
	public static String orderParamByAscii(SignAbledBean bean) {

		StringBuffer bf = new StringBuffer();

		Field[] fields = bean.getClass().getDeclaredFields();
		List<String> sortlst = new ArrayList<String>();
		String name = null;
		for (Field field : fields) {
			name = field.getName();
			if ("serialVersionUID".equals(name))
				continue;
			sortlst.add(name);
		}

		Collections.sort(sortlst);
		Object fdvalue = null;
		boolean isft = true;// 是否第一次
		for (String fdname : sortlst) {
			fdvalue = bean.getAttributeValue(fdname);
			if (fdvalue != null && fdvalue.toString().length() > 0) {
				if (!isft)
					bf.append("&");
				else
					isft = false;
				bf.append(fdname + "=" + fdvalue);
			}
		}
		return bf.toString();
	}


	public static String FormatBizQueryParaMap(HashMap<String, String> paraMap,
			boolean urlencode)
			throws SDKRuntimeException {

		String buff = "";
		try {

			List<Entry<String, String>> infoIds = new ArrayList<Entry<String, String>>(paraMap.entrySet());

			Collections.sort(infoIds, new Comparator<Entry<String, String>>() {
				@Override
				public int compare(Entry<String, String> o1, Entry<String, String> o2) {
					return o1.getKey().compareTo(o2.getKey());
				}
			});

			for (int i = 0; i < infoIds.size(); i++) {
				Entry<String, String> item = infoIds.get(i);
				// System.out.println(item.getKey());
				if (item.getKey() != "") {

					String key = item.getKey();
					String val = item.getValue();
					if (urlencode) {
						val = URLEncoder.encode(val, "utf-8");
					}
					buff += key.toLowerCase() + "=" + val + "&";

				}
			}

			if (buff.isEmpty() == false) {
				buff = buff.substring(0, buff.length() - 1);
			}
		} catch (Exception e) {
			throw new SDKRuntimeException(e.getMessage());
		}
		
		return buff;
	}

	public static boolean IsNumeric(String str) {
		if (str.matches("\\d *")) {
			return true;
		} else {
			return false;
		}
	}

	public static String ArrayToXml(HashMap<String, String> arr) {
		
		String xml = "<xml>";

		Iterator<Entry<String, String>> iter = arr.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			String key = entry.getKey();
			String val = entry.getValue();
			if (IsNumeric(val)) {
				xml += "<" + key + ">" + val + "</" + key + ">";

			} else
				xml += "<" + key + "><![CDATA[" + val + "]]></" + key + ">";
		}

		xml += "</xml>";
		return xml;
	}

	/**
	 * 通过调用统一下单接口，得到微信预支付相关信息(主要是付款URL)
	 * 
	 * @param xmlobj
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getPrePayInfos(Object xmlobj) throws Exception {
		
		HttpsRequest request = new HttpsRequest();
		String buffer = request.sendPost(WxPayConst.unitorder_url, xmlobj);
		if(buffer == null) buffer = "";buffer = buffer.trim();
		System.out.println("getPrePayInfos=\r\n" + buffer.toString());
		
		/*??
		//for test begin
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>																		") ;		
		sb.append("   	<return_code><![CDATA[SUCCESS]]></return_code>                          ") ;
		sb.append("   	<return_msg><![CDATA[OK]]></return_msg>                                 ") ;
		sb.append("   	<appid><![CDATA[wx9915cc99ba18cacef]]></appid>                          ") ;
		sb.append("   	<mch_id><![CDATA[10000100]]></mch_id>                                   ") ;
		sb.append("   	<nonce_str><![CDATA[IITRi8Iabbblz1Jc]]></nonce_str>                     ") ;
		sb.append("   	<openid><![CDATA[oUpF8uMuAJO_M2pxb1Q9zNjWeS6o]]></openid>               ") ;
		sb.append("   	<sign><![CDATA[7921E432F65EB8ED0CE9755F0E86D72F]]></sign>               ") ;
		sb.append("   	<result_code><![CDATA[SUCCESS]]></result_code>                          ") ;
		sb.append("   	<prepay_id><![CDATA[wx201411101639507cbf6ffd8b0779950874]]></prepay_id> ") ;
		sb.append("   	<trade_type><![CDATA[JSAPI]]></trade_type>                              ") ;
		sb.append("</xml>                                                                       ") ;	
		buffer = sb.toString();
		//for test end
		??*/
		
		Map<String, String> res = parseXml(buffer.toString());
		return res;
	}

	/**
	 * 通过 appid 和  secret 得到  token
	 * https://api.weixin.qq.com/cgi-bin/token?
	 * grant_type=client_credential&appid=wx81c7ce773415e00a&secret=b17cdd54ce4c35420a9e7782d7a27fa7
	 */
	public static String getAccessToken() throws Exception {
		
		String out = "";
		String in = WxPayConst.query_access_token.replace("appid=appid", "appid=" + WxPayConst.gzh_appid)
				.replace("secret=secret", "secret=" + WxPayConst.gzh_Secret);
		System.out.println("openidtest"+in);
		StringBuffer buffer = HttpsRequest.httpsRequest(in, "GET", out);
		System.out.println("buffer"+buffer.toString());
		return buffer.toString()+out;
	}
	
	/**
	 * 通过调用微信取得Code(基本信息)
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String getWxCode(String uri) throws Exception {
		
		String out = "";
		String in = WxPayConst.code_url.replace("appid=appid", "appid=" + WxPayConst.gzh_appid)
				.replace("redirect_uri=url", "redirect_uri=" + uri);
		System.out.println("openidtest"+in);
		
		StringBuffer buffer = HttpsRequest.httpsRequest(in, "GET", out);
		return buffer.toString()+out;
	}

	/**
	 * 通过调用微信取得Code2(详细信息)
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String getWxCode2(String uri) throws Exception {
		
		String out = "";
		String in = WxPayConst.code_url_2.replace("appid=appid", "appid=" + WxPayConst.gzh_appid)
				.replace("redirect_uri=url", "redirect_uri=" + uri);
		System.out.println("openidtest"+in);
		
		StringBuffer buffer = HttpsRequest.httpsRequest(in, "GET", out);
		return buffer.toString()+out;
	}
	
	/**
	 * 通过调用微信取得openId
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String getOpenId(String code) throws Exception {
		
		String out = ""; //?? openid获取URL : WxPayConst.oauth_url；
		String in=WxPayConst.oauth_url.replace("appid=APPID","appid="+WxPayConst.gzh_appid)
				.replace("secret=SECRET","secret="+WxPayConst.gzh_Secret).
				replace("code=CODE", "code="+code);
		System.out.println("openidtest"+in);
		StringBuffer buffer = HttpsRequest.httpsRequest(in, "GET", out);
		System.out.println("openidtest"+buffer);
		return buffer.toString();
	}
	
	/**
	 * 获取微信用户信息；
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String getUserInfo(String access_token,String openid) throws Exception {	
		String out = ""; 		
		String in=WxPayConst.user_url.replace("access_token=ACCESS_TOKEN","access_token="+access_token).replace("openid=OPENID","openid="+openid);
		StringBuffer buffer = HttpsRequest.httpsRequest(in, "GET", out);
		System.out.println("getUserInfo:"+buffer.toString());
		return buffer.toString();
	}
		
	
	/**
	 * 获取微信用户信息；
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String getUserManagerGetInfo(String access_token,String openid) throws Exception {	
		String out = ""; 		
		String in=WxPayConst.usermanager_getinfo_url.replace("access_token=ACCESS_TOKEN","access_token="+access_token).replace("openid=OPENID","openid="+openid);
		
	    /*in="https://api.weixin.qq.com/cgi-bin/user/info?access_token=WbOLClDPg-7y5R41vPYIxwoplYD6OFUzXEwycStaclA6HqOU"
	 		+ "zjmr2V_cCM7cc-yZz5wY8l-g-sLBVHaFaial0sGDDnkw8Tq_7VXfCfIrDwLMhQ01uRNgfqQ0XnhRvQtrMRFjADAFKY&openid=ovE_ow7RIZFm3Rf8NpCWwK00UGsU";*/
		
		StringBuffer buffer = HttpsRequest.httpsRequest(in, "GET", out);
		System.out.println("getUserManagerGetInfo:"+buffer.toString());
		return buffer.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(String xml) throws Exception {
		
		Map<String, String> map = new HashMap<String, String>();
		if(xml==null) return map;
		
		Document document = DocumentHelper.parseText(xml);
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();
		for (Element e : elementList)
			map.put(e.getName(), e.getText());
		
		return map;
	}

	static SimpleDateFormat dateFormate = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * 生成微信特定格式时间戳
	 * @return
	 */
	public static String getTimeStamp() {
		return dateFormate.format(new Date());
	}
		
	public static Map<String, String> createSendRedPackOrderSign(SendRedPack redPack){
		
		/*
        StringBuffer sign = new StringBuffer();
        sign.append("act_name=").append(redPack.getAct_name());
        sign.append("&client_ip=").append(redPack.getClient_ip());
        sign.append("&mch_billno=").append(redPack.getMch_billno());
        sign.append("&mch_id=").append(redPack.getMch_id());
        sign.append("&nonce_str=").append(redPack.getNonce_str());
        sign.append("&re_openid=").append(redPack.getRe_openid());
        sign.append("&remark=").append(redPack.getRemark());
        sign.append("&send_name=").append(redPack.getSend_name());
        sign.append("&total_amount=").append(redPack.getTotal_amount());
        sign.append("&total_num=").append(redPack.getTotal_num());
        sign.append("&wishing=").append(redPack.getWishing());
        sign.append("&wxappid=").append(redPack.getWxappid());
        sign.append("&key=").append(WxPayConst.key);//??sign.append("&key=").append(payKey);                
        return MD5Util.MD5(sign.toString()).toUpperCase();//??return DigestUtils.md5Hex(sign.toString()).toUpperCase();
        */
		
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("nonce_str", redPack.getNonce_str());
		sParaTemp.put("mch_billno", redPack.getMch_billno());
		sParaTemp.put("mch_id", redPack.getMch_id());
		sParaTemp.put("wxappid", redPack.getWxappid());
		sParaTemp.put("nick_name", redPack.getNick_name());
		sParaTemp.put("send_name", redPack.getSend_name());
		sParaTemp.put("re_openid", redPack.getRe_openid());
		sParaTemp.put("total_amount", String.valueOf(redPack.getTotal_amount()));
		sParaTemp.put("min_value", String.valueOf(redPack.getMin_value()));
		sParaTemp.put("max_value", String.valueOf(redPack.getMax_value()));
		sParaTemp.put("total_num", String.valueOf(redPack.getTotal_num()));
		sParaTemp.put("wishing", redPack.getWishing());
		sParaTemp.put("client_ip", redPack.getClient_ip());
		sParaTemp.put("act_name", redPack.getAct_name());
		sParaTemp.put("remark", redPack.getRemark());
				
        Map<String, String> sPara = CommonUtil.paraFilter(sParaTemp);
        String prestr = CommonUtil.createLinkString(sPara);
        String key = "&key="+WxPayConst.gzh_ApiKey;
        String sign = prestr + key;
        sign = MD5Util.MD5(sign.toString()).toUpperCase();//??return DigestUtils.md5Hex(sign.toString()).toUpperCase();        
		redPack.setSign(sign);		
		sParaTemp.put("sign",sign);
		return sParaTemp;
    }

	public static Map<String, String> createWxTransOrderSign(WxcpWxTrans wxTrans){
				
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("trans_id",wxTrans.getTrans_id());
		sParaTemp.put("mch_appid",wxTrans.getMch_appid());
		sParaTemp.put("mchid",wxTrans.getMchid());
		//sParaTemp.put("device_info",wxTrans.getDevice_info());
		sParaTemp.put("nonce_str",wxTrans.getNonce_str());
		sParaTemp.put("sign",wxTrans.getSign());
		sParaTemp.put("partner_trade_no",wxTrans.getPartner_trade_no());
		sParaTemp.put("openid",wxTrans.getOpenid());
		sParaTemp.put("re_user_name",wxTrans.getRe_user_name());
		sParaTemp.put("amount",String.valueOf(wxTrans.getAmount()));
		sParaTemp.put("desc",wxTrans.getDesc());
		sParaTemp.put("spbill_create_ip",wxTrans.getSpbill_create_ip());
		//sParaTemp.put("return_code",wxTrans.getReturn_code());
		//sParaTemp.put("result_code",wxTrans.getResult_code());
		//sParaTemp.put("return_msg",wxTrans.getReturn_msg());
		//sParaTemp.put("payment_no",wxTrans.getPayment_no());
		//sParaTemp.put("payment_time",wxTrans.getPayment_time());
		
        Map<String, String> sPara = CommonUtil.paraFilter(sParaTemp);
        String prestr = CommonUtil.createLinkString(sPara);
        String key = "&key="+WxPayConst.gzh_ApiKey;
        String sign = prestr + key;
        sign = MD5Util.MD5(sign.toString()).toUpperCase();//??return DigestUtils.md5Hex(sign.toString()).toUpperCase();        
        wxTrans.setSign(sign);		
        sParaTemp.put("sign",sign);
        return sParaTemp;
    }
	
	//send red pack request；
    public static Map<String, String> sendTrans(WxcpWxTrans wxTrans) throws Exception{
    	
    	Map<String, String> sParaTemp = createWxTransOrderSign(wxTrans);
    	//System.out.println("sign_1="+sParaTemp.get("sign"));
    	//sParaTemp.put("sign",null);
    	//String sign = CommonUtil.getSign(sParaTemp);
    	//sParaTemp.put("sign",sign);
    	//System.out.println("sign_2="+sParaTemp.get("sign"));
    	
        XMLUtil xmlUtil = new XMLUtil();
        xmlUtil.xstream().alias("xml", wxTrans.getClass());
        String xml = xmlUtil.xstream().toXML(wxTrans);
        xml = xml.replace("com.xczh.consumer.market.bean.WxcpWxTrans", "xml");
        xml = xml.replace("__", "_");
        
        String sendWxTranskUrl = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
        String response = HttpsRequest.sslPost(sendWxTranskUrl,xml);
        if(response==null) response = "";
        response = response.replace("__", "_");
        
        Map<String, String> responseMap = xmlUtil.parseXml(response);
        return responseMap;
    }
	
	public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<String, String>();
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }	
	
	public static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }	
	
	//send red pack request；
    public static Map<String, String> sendRedPack(SendRedPack redPack) throws Exception{
    	
    	Map<String, String> sParaTemp = createSendRedPackOrderSign(redPack);//String sign = createSendRedPackOrderSign(redPack);//redPack.setSign(sign);
                        
        XMLUtil xmlUtil = new XMLUtil();
        xmlUtil.xstream().alias("xml", redPack.getClass());
        String xml = xmlUtil.xstream().toXML(redPack);
        xml = xml.replace("com.xczh.consumer.market.wxpay.entity.SendRedPack", "xml");
        xml = xml.replace("__", "_");
        
        String sendRedPackUrl = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
        String response = HttpsRequest.sslPost(sendRedPackUrl,xml);
        if(response==null) response = "";
        response = response.replace("__", "_");
        
        Map<String, String> responseMap = xmlUtil.parseXml(response);
        return responseMap;
    }
	
	public static void main(String[] args) {
		/*
		try {
			String str="{\"access_token\":\"2yAuK-gAlOrwb8Tnb0irWt-op_BQez1SJt9zqLnLj6Ik5TxcXl6YEicge06scEUCwTpytYoqBjftyf3RYRLpzhziU1n3gy1_Uwnl81iNeD8\",\"expires_in\":7200,\"refresh_token\":\"Jz_l22mn9M1Vc3ZED7bWv53DdlWL8oMKBr6AAQMD-DkGc7HF2Fi0lt0_Bq-yr7PYzWFi2zS3xYEjQgkT0W0s9gc3dqHp65_JKihULWstYIw\",\"openid\":\"o4pRAwc17kSiqDDsM1VJZ9op88B4\",\"scope\":\"snsapi_base\"}";
			Map<String, String> res =GsonUtils.fromJson(str, Map.class);
			System.out.println(res.get("openid"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}
//	public static String getSign(SignAbledBean payInfo) throws Exception {
//		String signTemp = orderParamByAscii(payInfo);
//		String sign = MD5SignUtil.Sign(signTemp, WxPayConst.app_key);
//		return sign;
//	}
	public static String getSign(SignAbledBean payInfo, String tradeType) throws SDKRuntimeException {
		String signTemp = orderParamByAscii(payInfo);
		String sign ="";
		if(PayInfo.trade_type_app.equals(tradeType)){
		    sign = MD5SignUtil.Sign(signTemp, WxPayConst.app_ApiKey);
		}else{
			sign = MD5SignUtil.Sign(signTemp, WxPayConst.gzh_ApiKey);
		}
		return sign;
	}
	//app二次签名
	public static Map<String,String> getSignER(Map<String,String> map) throws SDKRuntimeException {
		/*AppInfo ai = new AppInfo();
		ai.setAppid(map.get("appid"));
		ai.setNoncestr(map.get("nonce_str"));
		ai.setPackage_app(map.get("Sign=WXPay"));
		ai.setPartnerid(map.get("mch_id"));
		ai.setPrepayid(map.get("prepay_id"));
		ai.setTimestamp(map.get(System.currentTimeMillis()/1000+""));*/
		String timestamp = System.currentTimeMillis()/1000+"";
		String str = "appid="+map.get("appid")+"&noncestr="+map.get("nonce_str")+""
				   + "&package=Sign=WXPay"
                   +"&partnerid="+map.get("mch_id")+"&prepayid="+map.get("prepay_id")+"" 
                   +"&timestamp="+timestamp+"";
		//String signTemp = orderParamByAscii(ai);
		String sign ="";
	    sign = MD5SignUtil.Sign(str, WxPayConst.app_ApiKey);
	    map.put("sign",sign);
	    map.put("timestamp", timestamp);
		return map;
	}
	
	
}
