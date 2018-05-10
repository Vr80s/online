package com.xczh.consumer.market.wxpay.util;

import com.xczh.consumer.market.wxpay.entity.ReturnInfo;
import com.xczh.consumer.market.wxpay.util.v3.WXPayUtil;

import java.util.Map;

public class MD5SignUtil {
	
	public static String Sign(String content, String key)
			throws SDKRuntimeException {

		if (key == null || "".equals(key)) {
			throw new SDKRuntimeException("key不能为空，谢谢！");//??throw new SDKRuntimeException("�Ƹ�ͨǩ��key����Ϊ�գ�");
		}
		if (content == null || "".equals(content)) {
			throw new SDKRuntimeException("content不能为空，谢谢！");//??throw new SDKRuntimeException("�Ƹ�ͨǩ�����ݲ���Ϊ��");
		}
		
		String signStr = "";

		signStr = content + "&key=" + key;
		System.out.println("MD5SignUtil->Sign=\r\n\t"+signStr);
		return MD5Util.MD5(signStr).toUpperCase();

	}
	public static boolean VerifySignature(String content, 
			String sign, 
			String md5Key) {
		
		String signStr = content + "&key=" + md5Key;
		String calculateSign = MD5Util.MD5(signStr).toUpperCase();
		String tenpaySign = sign.toUpperCase();
		return (calculateSign == tenpaySign);
	}
	
	public static boolean VerifySignature(Map<String, String> map,
			String sign,
			String md5Key) {
		
		ReturnInfo returnInfo =new ReturnInfo();
		returnInfo.setAppid(map.get("appid"));
		returnInfo.setAttach(map.get("attach"));
		returnInfo.setBank_type(map.get("bank_type"));
		returnInfo.setCash_fee(map.get("cash_fee"));
		returnInfo.setDevice_info(map.get("device_info"));
		returnInfo.setFee_type(map.get("fee_type"));
		returnInfo.setIs_subscribe(map.get("is_subscribe"));
		returnInfo.setMch_id(map.get("mch_id"));
		returnInfo.setNonce_str(map.get("nonce_str"));
		returnInfo.setOpenid(map.get("openid"));
		returnInfo.setOut_trade_no(map.get("out_trade_no"));
		returnInfo.setResult_code(map.get("result_code"));
		returnInfo.setReturn_code(map.get("return_code"));
		returnInfo.setTime_end(map.get("time_end"));
		returnInfo.setTotal_fee(map.get("total_fee"));
		returnInfo.setTrade_type(map.get("trade_type"));
		returnInfo.setTransaction_id(map.get("transaction_id"));
		String signStr= CommonUtil.orderParamByAscii(returnInfo)+ "&key=" + md5Key;
		String calculateSign = MD5Util.MD5(signStr).toUpperCase();
		String tenpaySign = sign.toUpperCase();
		return (calculateSign.equals(tenpaySign));
	}

	/**
	 * Description：修改签名验证方式(兼容用户使用优惠券支付)
	 * creed: Talk is cheap,show me the code
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 * @Date: 2018/2/22 0022 下午 4:14
	 **/
	public static boolean VerifySignature(String content, String key) throws Exception {
		return WXPayUtil.isSignatureValid(content,key);
	}
	
}
