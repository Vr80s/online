package com.xczhihui.common.util.enums;

/**
 * @author hejiwei
 */
public enum OnlineApprenticeStatus {

    //没有报名
    NOT_ENTRY(1),
    //没有审核
    NOT_APPLY(2),
    //审核未通过
    NOT_PASS(3),
    //审核已通过
    PASSED(4);

    private int val;

    OnlineApprenticeStatus(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
