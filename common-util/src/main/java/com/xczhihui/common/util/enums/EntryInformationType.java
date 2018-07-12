package com.xczhihui.common.util.enums;

/**
 * @author hejiwei
 */
public enum EntryInformationType {

    //招生简章报名
    ENROLLMENT_REGULATIONS_APPLY(1),
    ONLINE_APPLY(2);

    private int code;

    EntryInformationType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
