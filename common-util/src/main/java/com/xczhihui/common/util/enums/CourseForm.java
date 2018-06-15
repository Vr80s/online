package com.xczhihui.common.util.enums;

public enum CourseForm {

    LIVE(1, "直播"),
    VOD(2, "点播"),
    OFFLINE(3, "线下");

    // 成员变量
    private String text;
    private int code;

    // 构造方法
    private CourseForm(int code, String text) {
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