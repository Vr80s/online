package com.xczhihui.common.util;

/**
 * 上传文件格式
 *
 * @author liuxiaodong
 */
public enum FileType {

    JPEG("FFD8FF"), GIF("47494638"), BMP("424D"), PNG("89504E47"), XML(
            "3C3F786D6C"), XLSX("504B0304"), XLS("D0CF11E0");

    private String value = "";

    private FileType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
