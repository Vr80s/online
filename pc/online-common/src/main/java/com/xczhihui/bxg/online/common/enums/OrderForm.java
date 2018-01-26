package com.xczhihui.bxg.online.common.enums;

/**
 * Description：订单来源
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 上午 10:55 2018/1/25 0025
 **/
public enum OrderForm {

    PC(1, "pc"),
    H5(2, "h5"),
    ANDROID(3, "android"),
    IOS(4, "ios");

    /**
     * 描述
     **/
    private String text;
    private int code;

    private OrderForm(int code, String text) {
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