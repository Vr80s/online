package com.xczhihui.bxg.online.common.enums;

/**
 * Description：验证码类型
 * 1注册2.重置密码3.提现
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 下午 3:39 2018/1/29 0029
 **/
public enum VCodeType {

    REGISTER(1, "注册"),
    RESET(2, "重置密码"),
    ENCHASHMENT(3, "提现");

    private String text;
    private int code;

    private VCodeType(int code, String text) {
        this.text = text;
        this.code = code;
    }

    public static String getType(int code){
        for (VCodeType e : VCodeType.values()) {
            if(e.getCode() == code){
                return e.getText();
            }
        }
        return null;
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