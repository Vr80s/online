package com.xczhihui.medical.bbs.enums;

/**
 * 帖子的筛选类型
 *
 * @author hejiwei
 */
public enum PostFilterType {

    /**
     * 标签筛选
     */
    LABEL("label"),
    /**
     * 热门
     */
    HOT("hot"),
    /**
     * 精华
     */
    GOOD("good");

    private String value;

    PostFilterType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
