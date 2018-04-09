package com.xczhihui.bxg.common.util.enums;

/**
 * Description：0.支付宝1.微信支付2.苹果支付3.熊猫币支付4.线下支付-1其他支付
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 下午 2:28 2018/1/29 0029
 **/
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