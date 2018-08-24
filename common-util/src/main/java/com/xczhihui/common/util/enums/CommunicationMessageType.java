package com.xczhihui.common.util.enums;

/**
 * @author hejiwei
 */
public enum CommunicationMessageType {

    //医师开始诊疗
    DOCTOR_TREATMENT_START(1),
    //医师结束诊疗
    DOCTOR_TREATMENT_STOP(2),
    //用户开始诊疗
    USER_TREATMENT_START(3),
    //拒绝医师的诊疗请求
    USER_TREATMENT_REFUSE(4),
    //医师拒绝诊疗请求
    DOCTOR_TREATMENT_REFUSE(5);

    private int val;

    CommunicationMessageType(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
