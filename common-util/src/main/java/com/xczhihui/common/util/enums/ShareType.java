package com.xczhihui.common.util.enums;

/**
 * 分享类型
 * <p>
 * ClassName: ThirdPartyType.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年2月2日<br>
 */
public enum ShareType {

    //	1：课程分享2：主播分享 3：专辑（及其子课程）分享
    COURSE_SHARE(1, "课程分享"),
    HOST_SHARE(2, "主播分享"),
    ALBUM_SHARE(3, "专辑（及其子课程）分享"),
    APPRENTICE_SHARE(4, "师承分享"),
    DOCDOT_SHARE(5, "医师分享"),
    
    ACTICLE_SHARE(6, "文章分享，医案分享，著作分享"),
	
    PRODUCT_SHARE(7, "商品详情");

    /**
     * 描述
     **/
    private Integer code;
    private String text;

    private ShareType(Integer code, String text) {
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
