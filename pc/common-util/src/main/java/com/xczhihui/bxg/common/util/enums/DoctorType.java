package com.xczhihui.bxg.common.util.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：中医类型
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/2/27 0027 下午 6:47
 **/
public enum DoctorType {

    MQNZY(1, "名青年中医"),
    MLZY(2, "名老中医"),
    SSMZZY(3, "少数民族中医"),
    GYDS(4, "国医大师"),
    GZY(5, "家传中医")
    ;

    // 成员变量
    private String text;
    private int code;

    // 构造方法
    private DoctorType(int code, String text) {
        this.text = text;
        this.code = code;
    }

    public static List<Map> getDoctorTypeList(){
        List<Map> list = new ArrayList<Map>();
        for (DoctorType e : DoctorType.values()) {
            Map m = new HashMap();
            m.put("code",e.getCode());
            m.put("value",e.getText());
            list.add(m);
        }
        return list;
    }

    public static String getDoctorTypeText(int code){
        for (DoctorType e : DoctorType.values()) {
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