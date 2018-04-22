package com.xczhihui.bxg.common.util.enums;

/**
 * Description：支付订单类型
 * 1主播2权限禁用0非主播
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 下午 3:39 2018/1/29 0029
 **/
public enum PayOrderType {

    COURSE_ORDER("order", "购课"),
    COIN_ORDER("recharge", "充值");

    private String text;
    private String code;

    private PayOrderType(String code, String text) {
        this.text = text;
        this.code = code;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}