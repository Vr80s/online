package com.xczhihui.common.util.enums;

/**
 * Description：医师动态类型
 * creed: Talk is cheap,show me the code
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/7/10 17:02
 **/
public enum DoctorPostsType {
    COMMONPOSTS(1, "普通动态"),
    PICTUREPOSTS(2, "图片动态"),
    VIDEOPOSTS(3, "视频动态"),
    ARTICLEPOSTS(4, "文章动态"),
    COURSEPOSTS(5, "课程动态"),
    POSTSPOSTS(6, "商品动态"),
    ;

    private String text;
    private int code;

    private DoctorPostsType(int code, String text) {
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