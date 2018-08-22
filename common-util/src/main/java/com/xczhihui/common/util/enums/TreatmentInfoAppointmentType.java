package com.xczhihui.common.util.enums;

/**
 * 诊疗直播预约状态
 * <p>
 * ClassName: ThirdPartyType.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年2月2日<br>
 */
public enum TreatmentInfoAppointmentType {

    UNREVISED(1, "未审核"),
    PASS(2, "通过"),
    REFUSE(5, "拒绝"),
    CANCEL(6, "取消");

    /**
     * 描述
     **/
    private Integer code;
    private String text;

    private TreatmentInfoAppointmentType(Integer code, String text) {
        this.text = text;
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
