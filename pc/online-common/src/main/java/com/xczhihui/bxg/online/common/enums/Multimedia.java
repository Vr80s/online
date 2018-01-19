package com.xczhihui.bxg.online.common.enums;

public enum Multimedia {

    VIDEO(1, "视频"),
    AUDIO(2, "音频");

    // 成员变量
    private String text;
    private int code;

    // 构造方法
    private Multimedia(int code, String text) {
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