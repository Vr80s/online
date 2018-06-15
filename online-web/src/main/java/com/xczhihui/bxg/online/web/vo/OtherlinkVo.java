package com.xczhihui.bxg.online.web.vo;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;

/**
 * Created by admin on 2016/7/27.
 */
public class OtherlinkVo extends OnlineBaseVo {
    /**
     * 链接名
     */
    private String name;
    /**
     *链接地址
     */
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
