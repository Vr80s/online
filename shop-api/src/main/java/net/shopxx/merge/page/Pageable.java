package net.shopxx.merge.page;

import java.io.Serializable;

public class Pageable implements Serializable {

    /**
     * 页码
     */
    private int pageNumber = 1;

    /**
     * 每页记录数
     */
    private int pageSize = 10;

    public Pageable() {
    }

    public Pageable(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
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
}
