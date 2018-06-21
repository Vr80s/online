package com.xczhihui.common.solr.utils;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Description：solr分页
 * creed: Talk is cheap,show me the code
 *
 * @author name：yuxin
 * @Date: 2018/6/16 0016 下午 5:59
 **/
@Data
public class SolrPages implements Serializable {
    private static final long serialVersionUID = 1L;
    //总记录数
    private int total;
    //每页记录数
    private int size;
    //总页数
    private int pages;
    //当前页数
    private int current;
    //列表数据
    private List<?> list;

    /**
     * 分页
     * @param list       列表数据
     * @param total 总记录数
     * @param size   每页记录数
     * @param current   当前页数
     */
    public SolrPages(List<?> list, int total, int size, int current) {
        this.list = list;
        this.total = total;
        this.size = size;
        this.current = current;
        this.pages = (int) Math.ceil((double) total / size);
    }

}
