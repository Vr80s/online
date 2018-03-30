package com.xczhihui.bxg.online.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：主播课程驳回理由
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 下午 3:41 2018/1/29 0029
 **/
public enum CourseDismissal {

    D1(1, "课程中含有不符合要求的内容，请修改"),
    D2(2, "课程未发现可播放视频"),
    D0(0, "其他");

    // 成员变量
    private String text;
    private int code;

    // 构造方法
    private CourseDismissal(int code, String text) {
        this.text = text;
        this.code = code;
    }

    public static List<CourseDismissal> getDismissalList(){
        List<CourseDismissal> dismissalList = new ArrayList<CourseDismissal>();
        for (CourseDismissal e : CourseDismissal.values()) {
            dismissalList.add(e);
        }
        return dismissalList;
    }

    public static String getDismissal(int code){
        for (CourseDismissal e : CourseDismissal.values()) {
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