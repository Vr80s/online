package com.xczhihui.pay.weixin.api;

public abstract class WxPayApiController{

    private static final String BUY_COURSE_TEXT = "购买课程{0}";
    private static final String BUY_COIN_TEXT = "充值熊猫币:{0}个";

    public abstract void initWxPayApiConfig();

}