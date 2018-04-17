package com.xczh.consumer.market.wxpay.util;

import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;

import com.xczh.consumer.market.wxpay.consts.WxPayConst;
import com.xczh.consumer.market.wxpay.entity.BizOrder;
import com.xczh.consumer.market.wxpay.entity.PayInfo;

public class PayUtils {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PayUtils.class);
	
	/**
	 * 根据业务实体生成支付二维码图片，并返回服务临时路径
	 * 
	 * @param bo
	 * @return
	 * @throws Exception
	 */
	public static String fromBizBean2QRImg(BizOrder bo) throws Exception {

		// 请求实体
		PayInfo testinfo = new PayInfo();
		testinfo = testinfo.createPayInfo(bo, PayInfo.TRADE_TYPE_NATIVE);
		// 发送请求
		Map<String, String> retobj = CommonUtil.getPrePayInfos(testinfo);
		//
		String generatedUrl = "";
		if (retobj != null
				&& WxPayConst.recode_success.equals(retobj
						.get(WxPayConst.return_code))) {
			// generatedUrl=MakeQRUtil.generateQRimg(retobj.get(WxPayConst.return_code_url),bo.getOrderCode());
			MakeQRUtil.outputQRimg(retobj.get(WxPayConst.return_code_url));

			generatedUrl = retobj.get(WxPayConst.return_code_url);// ??
			// LOGGER.info("成功!");
		} else {
            LOGGER.info("失败..." + retobj.get("return_msg"));
        }

		return generatedUrl;
	}

	public static void fromBizBean2QRImgStream(BizOrder bo, OutputStream stream)
			throws Exception {

		// 请求实体
		PayInfo testinfo = new PayInfo();
		testinfo = testinfo.createPayInfo(bo, PayInfo.TRADE_TYPE_NATIVE);

		// 发送请求
		Map<String, String> retobj = CommonUtil.getPrePayInfos(testinfo);
		//
		if (retobj != null
				&& WxPayConst.recode_success.equals(retobj
						.get(WxPayConst.return_code))) {
			// generatedUrl=MakeQRUtil.generateQRimg(retobj.get(WxPayConst.return_code_url),bo.getOrderCode());
			MakeQRUtil.outputQRimgStream(
					retobj.get(WxPayConst.return_code_url), stream);
		} else {
            LOGGER.info("失败..." + retobj.get("return_msg"));
        }
	}

	public static Map<String, String> fromBizBean2PrePay4App(BizOrder bo)
			throws Exception {
		// 请求实体
		PayInfo testinfo = new PayInfo();
		testinfo = testinfo.createPayInfo(bo, PayInfo.TRADE_TYPE_APP);

		// 发送请求
		Map<String, String> retobj = CommonUtil.getPrePayInfos(testinfo);
		LOGGER.info("微信app支付返回:" + retobj.toString());

		if (retobj != null
				&& WxPayConst.recode_success.equals(retobj.get("result_code"))) {
			// 附加移动支付额外参数
			Map<String, String> param = new HashMap<>();
			param.put("appid", testinfo.getAppid());
			param.put("noncestr", testinfo.getNonce_str());
			param.put("partnerid", testinfo.getMch_id());
			param.put("prepayid", retobj.get("prepay_id"));
			param.put("timestamp", String.valueOf(new Date().getTime() / 1000));
			param.put("package", "Sign=WXPay");
			param.put("sign", CommonUtil.getSign(param));
			// 转换package兼容android
			param.put("packagev", "Sign=WXPay");
			return param;
		} else {
            LOGGER.info("失败..." + retobj.get("return_msg"));
        }

		return null;
	}

	/**
	 * 公众号支付 Description：
	 * 
	 * @param bo
	 * @return
	 * @throws Exception
	 * @return Map<String,String>
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	public static Map<String, String> fromBizBean2PrePay4Jsapi(BizOrder bo)
			throws Exception {

		// 请求实体
		PayInfo testinfo = new PayInfo();
		testinfo = testinfo.createPayInfo(bo, PayInfo.TRADE_TYPE_JSAPI);
		// 发送请求
		Map<String, String> retobj = CommonUtil.getPrePayInfos(testinfo);
		LOGGER.info("微信app支付返回:" + retobj.toString());

		if (retobj != null
				&& WxPayConst.recode_success.equals(retobj.get("result_code"))) {
			// 附加移动支付额外参数
			Map<String, String> param = new HashMap<>();
			param.put("appId", testinfo.getAppid());
			param.put("nonceStr", testinfo.getNonce_str());
			// param.put("partnerid",testinfo.getMch_id());
			String preid = retobj.get("prepay_id");
			// param.put("prepayid",preid);
			param.put("timeStamp", String.valueOf(new Date().getTime() / 1000));
			param.put("package", "prepay_id=" + preid);
			param.put("signType", "MD5");
			param.put("paySign", CommonUtil.getSign(param));
			return param;
		} else {
            LOGGER.info("失败..." + retobj.get("return_msg"));
        }
		return null;
	}

	/**
	 * Description：H5支付
	 * @param bo
	 * @param khdip
	 * @return
	 * @throws Exception
	 * @return Map<String,String>
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	public static Map<String, String> fromBizBean2PrePay4H5(BizOrder bo,
			String khdip) throws Exception {
		// 请求实体
		PayInfo testinfo = new PayInfo();
		testinfo = testinfo.createPayInfoH5(bo, PayInfo.TRADE_TYPE_H5, khdip);
		// 发送请求
		Map<String, String> retobj = CommonUtil.getPrePayInfos(testinfo);
		LOGGER.info("微信app支付返回:" + retobj.toString());
		if (retobj != null && WxPayConst.recode_success.equals(retobj.get("result_code"))) {
			return retobj;
		} else {
            LOGGER.info("失败..." + retobj.get("return_msg"));
        }
		return null;
	}
	/**
	 * Description：APP支付
	 * @param bo
	 * @param khdip
	 * @return
	 * @throws Exception
	 * @return Map<String,String>
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	public static Map<String, String> fromBizBean2PrePay4App(BizOrder bo,
			String khdip) throws Exception {
		// 请求实体
		PayInfo testinfo = new PayInfo();
		testinfo = testinfo.createPayInfoApp(bo, PayInfo.TRADE_TYPE_APP, khdip);
		// 发送请求
		Map<String, String> retobj = CommonUtil.getPrePayInfos(testinfo);
		LOGGER.info("微信app支付返回:" + retobj.toString());
		if (retobj != null && WxPayConst.recode_success.equals(retobj.get("result_code"))) {
			return retobj;
		} else {
            LOGGER.info("失败..." + retobj.get("return_msg"));
        }
		return null;
	}
	
	public static Map<String, String> getPrePayInfosCommon(BizOrder bo,
			String khdip,String tradeType) throws Exception {
		
		// 请求实体
		PayInfo testinfo = new PayInfo();
		testinfo = testinfo.createPayInfoCommon(bo,tradeType, khdip);
		// 发送请求
		Map<String, String> retobj = CommonUtil.getPrePayInfos(testinfo);
		if(PayInfo.TRADE_TYPE_JSAPI.equals(tradeType)){
			if (retobj != null && WxPayConst.recode_success.equals(retobj.get("result_code"))) {
				// 附加移动支付额外参数
				Map<String, String> param = new HashMap<>();
				param.put("appId", testinfo.getAppid());
				param.put("nonceStr", testinfo.getNonce_str());
				String preid = retobj.get("prepay_id");
				param.put("timeStamp", String.valueOf(new Date().getTime() / 1000));
				param.put("package", "prepay_id=" + preid);
				param.put("signType", "MD5");
				param.put("paySign", CommonUtil.getSign(param));
				return param;
			} else {
                LOGGER.info("失败..." + retobj.get("return_msg"));
            }
		}
		LOGGER.info("微信app支付返回:" + retobj.toString());
		if (retobj != null && WxPayConst.recode_success.equals(retobj.get("result_code"))) {
			return retobj;
		} else {
            LOGGER.info("失败..." + retobj.get("return_msg"));
        }
		return null;
	}
	

}
