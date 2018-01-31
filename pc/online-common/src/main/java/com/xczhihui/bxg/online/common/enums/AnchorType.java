package com.xczhihui.bxg.online.common.enums;

/**
 * Description：申请状态
 * 1医师2医馆
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 下午 3:39 2018/1/29 0029
 **/
public enum AnchorType {

    PASS(1, "医师"),
    UNTREATED(2, "医馆");

    private String text;
    private int code;

    private AnchorType(int code, String text) {
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