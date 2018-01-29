package com.xczhihui.bxg.online.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：订单来源
 * 1.pc 2.h5 3.android 4.ios 5.线下 6.工作人员
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 上午 10:55 2018/1/25 0025
 **/
public enum OrderFrom {

    PC(1, "pc"),
    H5(2, "h5"),
    ANDROID(3, "android"),
    IOS(4, "ios"),
    OFFLINE(5, "线下"),
    WORKER(6, "工作人员");

    public static OrderFrom getOrderFrom(int code){
        for (OrderFrom orderFrom : OrderFrom.values()) {
            if(orderFrom.getCode() == code){
                return orderFrom;
            }
        }
        return null;
    }

    /**
     * 描述
     **/
    private String text;
    private int code;

    private OrderFrom(int code, String text) {
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