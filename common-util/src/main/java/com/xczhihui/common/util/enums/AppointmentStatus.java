package com.xczhihui.common.util.enums;

/**
 * 远程诊疗状态
 *
 * @author hejiwei
 */
public enum AppointmentStatus {

    //初始状态
    ORIGIN(0),
    //待审核
    WAIT_APPLY(1),
    //预约成功
    APPOINTMENT_SUCCESS(2);

    private int val;

    AppointmentStatus(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
