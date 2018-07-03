package com.xczhihui.common.util.enums;

/**
 * Description：客户端类型
 * creed: Talk is cheap,show me the code
 * @author name：yuxin
 * @Date: 2018/7/3 0003 下午 3:30
 **/
public enum ClientType {

    PC(1, "pc"),
    H5(2, "h5"),
    ANDROID(3, "android"),
    IOS(4, "ios"),
    OTHER(5, "其他");

    /**
     * 描述
     **/
    private String text;
    private int code;

    private ClientType(int code, String text) {
        this.text = text;
        this.code = code;
    }

    public static ClientType getOrderFrom(int code) {
        for (ClientType orderFrom : ClientType.values()) {
            if (orderFrom.getCode() == code) {
                return orderFrom;
            }
        }
        return null;
    }

    public static ClientType valueOf(int value) {

        switch (value) {
            case 1:
                return ClientType.PC;
            case 2:
                return ClientType.H5;
            case 3:
                return ClientType.ANDROID;
            case 4:
                return ClientType.IOS;
            case 5:
                return ClientType.OTHER;
            default:
                return null;
        }
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