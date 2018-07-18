package com.xczhihui.common.util.enums;

/**
 * @author hejiwei
 */
public enum ApprenticeCheckStatus {

    DEFAULT(0),
    APPLYING(1),
    APPRENTICE(2);

    private int val;

    ApprenticeCheckStatus(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}