package com.xczhihui.common.util.enums;

/**
 * @author 弟子状态
 */
public enum ApprenticeStatus {

    YES(1),
    NO(0);

    private int val;

    ApprenticeStatus(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
