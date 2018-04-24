package com.xczhihui.common.util.enums;

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

    ICBC(95588, "工商银行"),
    CCB(95533, "建设银行"),
    BOC(95566, "中国银行"),
    ABC(95599, "农业银行"),
    BOCOM(95559, "交通银行"),
    CMB(95555, "招商银行"),
    ECITIC(95558, "中信银行"),
    CMBC(95568, "民生银行"),
    SPDB(95528, "浦东发展银行"),
    CIB(95561, "兴业银行"),
    CEB(95595, "光大银行"),
    PAB(95511, "平安银行"),
    HXB(95577, "华夏银行"),
    BOB(95526, "北京银行"),
    BOS(95594, "上海银行"),
    BOJS(95319, "江苏银行"),
    NBCB(95574, "宁波银行"),
    CZB(95527, "浙商银行"),
    EGB(95395, "恒丰银行"),
    PSBC(95580, "邮储银行")
    ;

    // 成员变量
    private String text;
    private int code;

    // 构造方法
    private BankCardType(int code, String text) {
        this.text = text;
        this.code = code;
    }

    public static List<Map> getBankCardList(){
        List<Map> list = new ArrayList<Map>();

        for (BankCardType e : BankCardType.values()) {
            Map m = new HashMap();
            m.put("code",e.getCode());
            m.put("value",e.getText());
            list.add(m);
        }

        return list;
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

}