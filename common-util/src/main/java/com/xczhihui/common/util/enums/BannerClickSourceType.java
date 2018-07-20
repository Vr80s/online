package com.xczhihui.common.util.enums;

/**
 * 分享类型
 * <p>
 * ClassName: ThirdPartyType.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年2月2日<br>
 */
public enum BannerClickSourceType {

    //	1：课程分享2：主播分享 3：专辑（及其子课程）分享
    
    H5(1, "微信"),
    ANDROID(2, "安卓app"),
    IOS(3, "苹果app"),
    PC(4, "电脑端");

    /**
     * 描述
     **/
    private Integer code;
    private String text;

    private BannerClickSourceType(Integer code, String text) {
        this.text = text;
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
