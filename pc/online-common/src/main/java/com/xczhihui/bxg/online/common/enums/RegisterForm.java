package com.xczhihui.bxg.online.common.enums;

/**
 * 注册来源  
 * ClassName: RegisterForm.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年1月29日<br>
 */
public enum RegisterForm {

    PC(1, "pc"),
    H5(2, "h5"),
    ANDROID(3, "android"),
    IOS(4, "ios"),
    OFFLINE(5, "线下"),
    WORKER(6, "工作人员");

    /**
     * 描述
     **/
    private String text;
    private int code;

    private RegisterForm(int code, String text) {
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