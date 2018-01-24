package com.xczhihui.bxg.online.common.enums;

public enum Payment {

    ALIPAY(0, "支付宝"),
    WECHATPAY(1, "微信支付"),
    APPLYPAY(2, "苹果支付"),
    COINPAY(3, "熊猫币支付"),
    OFFLINE(4, "线下支付"),
    OTHERPAY(-1,"其他支付");

    // 成员变量
    private String text;
    private int code;

    // 构造方法
    private Payment(int code, String text) {
        this.text = text;
        this.code = code;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}