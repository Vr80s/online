package com.xczhihui.common.util.enums;

/**
 * banner图类型
 * <p>
 * ClassName: ThirdPartyType.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年2月2日<br>
 */
public enum SearchType {


    //1 课程搜索框 2 课程热门搜索   3 医师搜索框 4 医师热门搜索
    SCHOOL_DEFAULT_SEARCH(1, "课程  默认检索提示"),
    SCHOOL_HOT_SEARCH(2, "课程  热门搜索"),
    
    DOCTOR_DEFAULT_SEARCH(3, "医师 默认检索提示"),
    DOCTOR_HOT_SEARCH(4, "医师  热门搜索"),

    PRODUCT_DEFAULT_SEARCH(5, "好货 默认检索提示"),
    PRODUCT_HOT_SEARCH(6, "医好货 热门搜索");
	
    /**
     * 描述
     **/
    private int code;
    private String text;

    private SearchType(int code, String text) {
        this.text = text;
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
