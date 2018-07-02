package com.xczhihui.common.util.enums;

/**
 * Description：用户来源 1.pc 2.h5 3.android 4.ios 5.导入
 * creed: Talk is cheap,show me the code
 *
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/5/14 0014 下午 6:14
 **/
public enum UserOrigin {

    PC(1, "pc"),
    H5(2, "h5"),
    ANDROID(3, "android"),
    IOS(4, "ios"),
    IMPORT(5, "导入");

    /**
     * 描述
     **/
    private String text;
    private int code;

    UserOrigin(int code, String text) {
        this.text = text;
        this.code = code;
    }

    public static UserOrigin getUserOrigin(int code) {
        for (UserOrigin userOrigin : UserOrigin.values()) {
            if (userOrigin.getCode() == code) {
                return userOrigin;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }
}
