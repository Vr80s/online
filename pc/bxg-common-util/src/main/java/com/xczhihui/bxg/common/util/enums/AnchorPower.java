package com.xczhihui.bxg.common.util.enums;

/**
 * Description：主播权限
 * 1主播2权限禁用0非主播
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 下午 3:39 2018/1/29 0029
 **/
public enum AnchorPower {

    YES(1, "主播"),
    NO(0, "非主播"),
    BAN(2, "权限禁用");

    private String text;
    private int code;

    private AnchorPower(int code, String text) {
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