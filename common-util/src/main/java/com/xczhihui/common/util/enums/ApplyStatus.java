package com.xczhihui.common.util.enums;

/**
 * Description：申请状态
 * 0拒绝1通过2未处理
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 下午 3:39 2018/1/29 0029
 **/
public enum ApplyStatus {

    UNTREATED(2, "未审核"),
    PASS(1, "通过"),
    NOT_PASS(0, "未通过"),
    //提现申请专用
    GRANT(3,"已打款");


    private String text;
    private int code;

    private ApplyStatus(int code, String text) {
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