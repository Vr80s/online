package com.xczhihui.bxg.online.common.enums;

/**
 * Description：账户消费类型
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 上午 10:55 2018/1/25 0025
 **/
public enum ConsumptionChangeType {

    COURSE(10, "购买课程"),
    CONSUME(9, "其他消费"),
    GIFT(8, "刷礼物"),
    OVERDUE(7, "过期清零"),
    ENCHASHMENT(6, "提现"),
    SETTLEMENT(5, "结算");

    /**
     * 描述
     **/
    private String text;
    private int code;

    private ConsumptionChangeType(int code, String text) {
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