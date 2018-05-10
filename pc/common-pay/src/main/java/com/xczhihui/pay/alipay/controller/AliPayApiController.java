package com.xczhihui.pay.alipay.controller;

import com.xczhihui.pay.alipay.AliPayApiConfig;


public abstract class AliPayApiController {

	public static final String BUY_COURSE_TEXT = "购买课程{0}";
	public static final String BUY_COIN_TEXT = "充值熊猫币:{0}个";
	public static final String WEB_PRODUCT_CODE = "FAST_INSTANT_TRADE_PAY";
	public static final String WAP_PRODUCT_CODE = "QUICK_WAP_PAY";
	public static final String APP_PRODUCT_CODE = "QUICK_MSECURITY_PAY";
	public static final String TIMEOUT_EXPRESS = "24h";

	public abstract void initAliPayApiConfig();
}