package com.xczhihui.bxg.common.util.enums;

/**
 * Description：账户类型
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 上午 10:55 2018/1/25 0025
 **/
public enum BalanceType {

    BALANCE(1, "用户熊猫币余额"),
    ANCHOR_BALANCE(2, "主播熊猫币余额"),
    ANCHOR_RMB(3, "主播人民币余额");

    /**
     * 描述
     **/
    private String text;
    private int code;

    private BalanceType(int code, String text) {
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