package com.xczhihui.bxg.online.common.enums;

import java.util.ArrayList;
import java.util.List;

public enum Dismissal {

    D1(1, "课程中含有不符合要求的内容，请修改"),
    D2(2, "课程未发现可播放视频"),
    D0(0, "其他");

    // 成员变量
    private String text;
    private int code;

    // 构造方法
    private Dismissal(int code, String text) {
        this.text = text;
        this.code = code;
    }

    public static List<Dismissal> getDismissalList(){
        List<Dismissal> dismissalList = new ArrayList<Dismissal>();
        for (Dismissal e : Dismissal.values()) {
            dismissalList.add(e);
        }
        return dismissalList;
    }

    public static String getDismissal(int code){
        for (Dismissal e : Dismissal.values()) {
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

    public static void main(String[] arg) {
//        Dismissal
//        System.err.println(Dismissal.D1.code+"==="+Dismissal.D1.text);
//        System.err.println(Dismissal.D2.code+"==="+Dismissal.D2.text);
//        System.err.println(Dismissal.D3.code+"==="+Dismissal.D3.text);
        for (Dismissal e : Dismissal.values()) {
            System.out.println(e.getCode());
        }
    }

}