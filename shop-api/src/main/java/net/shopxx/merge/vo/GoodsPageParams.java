/**
 * <p>Title: GoodsPageParams.java</p>
 * <p>Description: </p>
 *
 * @author yangxuan@ixincheng.com
 * @date 2018年9月13日
 */
package net.shopxx.merge.vo;

import java.io.Serializable;

import net.shopxx.merge.enums.OrderType;
/*
 * @author yangxuan
 * @ClassName: GoodsPageParams
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @email yangxuan@ixincheng.com
 * @date 2018年9月13日
 */
public class GoodsPageParams implements Serializable {
    private OrderType orderType;

    private Integer pageNumber = 1;


    private Integer pageSize = 10;

    private String keyWord;

    public Integer getPageNumber() {
        int num = (pageNumber - 1) * pageSize;
        num = num < 0 ? 0 : num;
        return num;
    }

    public void setPageNumber(Integer pageNumber) {

        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    @Override
    public String toString() {
        return "GoodsPageParams [pageNumber=" + pageNumber + ", pageSize=" + pageSize + "]";
    }
}
