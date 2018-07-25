package com.xczhihui.common.util.enums;

/**
 * @author hejiwei
 */
public enum IndexAppointmentStatus {

    ORIGIN(0),
    FULL(1),
    MY_APPOINT(2);

    private int val;

    IndexAppointmentStatus(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
