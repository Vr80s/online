package com.xczhihui.bxg.common.support.support;

/**
 * @author hejiwei
 */
public enum AttachmentBusinessType {
    //图片类型
    PICTURE("1"),
    //附件类型
    ATTACHMENT("2");

    private String value;

    AttachmentBusinessType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
