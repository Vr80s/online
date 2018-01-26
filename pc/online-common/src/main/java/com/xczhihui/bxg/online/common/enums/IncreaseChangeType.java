package com.xczhihui.bxg.online.common.enums;

/**
 * Description：账户余额增加类型
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 上午 10:55 2018/1/25 0025
 **/
public enum IncreaseChangeType {

//1.充值2.平台赠送3.礼物4.打赏5.平台提现驳回退回
    RECHARGE(1, "充值"),
    GIVE(2, "平台赠送"),
    GIFT(3, "礼物"),
    REWARD(4, "打赏"),
    ENCHASHMENT(5, "平台提现驳回退回"),
    SETTLEMENT(6, "结算(rmb新增)"),
    COURSE(7, "卖课所得");

    /**
     * 描述
     **/
    private String text;
    private int code;

    private IncreaseChangeType(int code, String text) {
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