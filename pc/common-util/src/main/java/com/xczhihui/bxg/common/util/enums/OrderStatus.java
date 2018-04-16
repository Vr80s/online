package com.xczhihui.bxg.common.util.enums;

/**
 * Description：支付状态 0:未支付 1:已支付 2:已关闭
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/4/15 0015 下午 4:33
 **/
public enum OrderStatus {

    UNPAID (0, "未支付"),
    PAID (1, "已支付"),
    CLOSED(2, "已关闭");

    // 成员变量
    private String text;
    private int code;

    // 构造方法
    private OrderStatus(int code, String text) {
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