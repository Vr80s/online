package com.xczhihui.common.util.enums;

/**
 * 发送短信类型           枚举  1注册，2重置密码, 3 原来手机号获取验证码 ,4 新的手机号获取验证码
 * ClassName: VCodeType.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年1月29日<br>
 */
public enum VCodeType {

    //注册，忘记秘密使用
    RETISTERED(1, "注册", "SMS_76395131"),
    FORGOT_PASSWORD(2, "重置密码", "SMS_135755600"),

    //更换手机号使用
    OLD_PHONE(3, "原来手机号获取验证码", "SMS_135770456"),
    NEW_PHONE(4, "新的手机号获取验证码", "SMS_135775476"),

    //提现 短信验证
    WITHDRAWAL(5, "提现短信验证", "SMS_135770462"),
    //三方登陆后绑定手机号
    BIND(6, "绑定手机号", "SMS_135765541");

    /**
     * 描述
     **/
    private int code;
    private String text;
    private String smsCode;

    VCodeType(int code, String text, String smsCode) {
        this.text = text;
        this.code = code;
        this.smsCode = smsCode;
    }

    public static boolean contains(Integer type) {
        for (VCodeType typeEnum : VCodeType.values()) {
            if (typeEnum.getCode() == type) {
                return true;
            }
        }
        return false;
    }

    public static VCodeType getType(int code) {
        for (VCodeType type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("验证码参数类型错误");
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

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}
