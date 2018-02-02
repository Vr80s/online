package com.xczhihui.bxg.online.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：银行卡
 * creed: Talk is cheap,show me the code
 * @author name：wys <br>email: wangyishuai@ixincheng.com
 * @Date: 下午 20:28 2018/2/1
 **/
public enum BankCardType {

    ICBC(95588, "中国工商银行"),
    CCB(95533, "中国建设银行"),
    BOC(95566, "中国银行"),
    ABC(95599, "中国农业银行"),
    BOCOM(95559, "交通银行"),
    CMB(95555, "招商银行"),
    ECITIC(95558, "中信银行"),
    CMBC(95568, "中国民生银行"),
    SPDB(95528, "上海浦东发展银行"),
    CIB(95561, "兴业银行"),
    CEB(95595, "中国光大银行"),
    PAB(95511, "平安银行"),
    HXB(95577, "华夏银行"),
    BOB(95526, "北京银行"),
    BOS(95594, "上海银行"),
    BOJS(95319, "江苏银行"),
    NBCB(95574, "宁波银行"),
    CZB(95527, "浙商银行"),
    EGB(95395, "恒丰银行"),
    PSBC(95580, "中国邮政储蓄银行")
    ;

    // 成员变量
    private String text;
    private int code;

    // 构造方法
    private BankCardType(int code, String text) {
        this.text = text;
        this.code = code;
    }

    public static Map<Integer,String> getBankCardList(){
        Map<Integer,String> m = new HashMap();
        for (BankCardType e : BankCardType.values()) {
            m.put(e.getCode(),e.getText());
        }
        return m;
    }

    public static String getBankCard(int code){
        for (BankCardType e : BankCardType.values()) {
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
        /*for (BankCardType e : BankCardType.values()) {
            System.out.println(e.getCode());
        }*/
        System.err.println(getBankCardList());
    }

}