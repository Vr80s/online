package com.xczhihui.bxg.online.common.enums;

/**
 * Description：头条类型
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/4/2 0002 上午 11:59
 **/
public enum HeadlineType {

    HYXW(1, "行业新闻"),
    HZKL(2, "合作交流"),
    ZCJD(3, "政策解读"),
    DJZL(4, "大家专栏"),
    ZDFG(5, "制度法规"),
    HWZX(6, "海外资讯"),
    MYBD(7, "名医报道");

    private String text;
    private int code;

    private HeadlineType(int code, String text) {
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