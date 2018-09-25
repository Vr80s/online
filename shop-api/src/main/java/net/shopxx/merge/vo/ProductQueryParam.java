package net.shopxx.merge.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import net.shopxx.merge.enums.OrderType;

/**
 * @author hejiwei
 */
public class ProductQueryParam implements Serializable {

    private int pageNumber = 1;

    private int pageSize = 10;

    private String keyword;

    private Integer minSales = -1;

    private Integer maxSales = Integer.MAX_VALUE;

    private BigDecimal minPrice = new BigDecimal(-1);

    private BigDecimal maxPrice = new BigDecimal(Integer.MAX_VALUE);

    private OrderType orderType = OrderType.DATE_DESC;

    public int getPageNumber() {
        return pageNumber < 1 ? 1 : pageNumber ;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getMinSales() {
        return minSales;
    }

    public void setMinSales(Integer minSales) {
        this.minSales = minSales;
    }

    public Integer getMaxSales() {
        return maxSales;
    }

    public void setMaxSales(Integer maxSales) {
        this.maxSales = maxSales;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }
}
