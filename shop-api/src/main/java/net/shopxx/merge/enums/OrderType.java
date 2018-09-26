package net.shopxx.merge.enums;

public enum OrderType {
    /**
     * 推荐，按照医师动态推荐次数由多到少排序
     */
    RECOMMEND_DESC,

    /**
     * 销量降序
     */
    SALES_DESC,
    SALES_ASC,

    /**
     * 日期降序
     */
    DATE_DESC,
    DATE_ASC,

    /**
     * 价格升序
     */
    PRICE_ASC,

    /**
     * 价格降序
     */
    PRICE_DESC,

    INVENTORY_ASC,
    INVENTORY_DESC,
}
