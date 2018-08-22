package com.xczhihui.common.util.enums;

/**
 * @author hejiwei
 */
public enum TreatmentInfoApplyStatus {

    //待审核
    WAIT_DOCTOR_APPLY(1),
    //待开始
    APPLY_PASSED(2),
    //审核不通过
    APPLY_NOT_PASSED(3),
    //已完成
    FINISHED(4),
    //已过期
    EXPIRED(5);

    private int val;

    TreatmentInfoApplyStatus(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
