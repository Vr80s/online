package com.xczhihui.common.util.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：主播课程驳回理由
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 下午 3:41 2018/1/29 0029
 **/
public enum EnchashmentDismissal {

    D1(1, "账号有误"),
    D2(2, "提现申请存疑"),
    D0(0, "其他");

    private String text;
    private int code;

    private EnchashmentDismissal(int code, String text) {
        this.text = text;
        this.code = code;
    }

    public static List<EnchashmentDismissal> getDismissalList(){
        List<EnchashmentDismissal> dismissalList = new ArrayList<EnchashmentDismissal>();
        for (EnchashmentDismissal e : EnchashmentDismissal.values()) {
            dismissalList.add(e);
        }
        return dismissalList;
    }

    public static String getDismissal(int code){
        for (EnchashmentDismissal e : EnchashmentDismissal.values()) {
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