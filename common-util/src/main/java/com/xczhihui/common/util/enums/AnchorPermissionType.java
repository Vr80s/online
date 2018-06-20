package com.xczhihui.common.util.enums;

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
public enum AnchorPermissionType {

    NO_PERMISSION(0, "无权限"),
    DOCTOR_PASS(1, "医师认证通过"),
    HOSPITAL_PASS(2, "医馆认证通过"),
    PERMISSION_DISABLE(3, "主播权限被禁用");

    // 成员变量
    private String text;
    private int code;

    // 构造方法
    private AnchorPermissionType(int code, String text) {
        this.text = text;
        this.code = code;
    }

    public static List<Map> getDoctorTypeList(){
        List<Map> list = new ArrayList<Map>();
        for (AnchorPermissionType e : AnchorPermissionType.values()) {
            Map m = new HashMap();
            m.put("code",e.getCode());
            m.put("value",e.getText());
            list.add(m);
        }
        return list;
    }
    

    public static String getDoctorTypeText(int code){
        for (AnchorPermissionType e : AnchorPermissionType.values()) {
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