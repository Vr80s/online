package com.xczhihui.bxg.online.web.vo;


import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;

/**
 * banner图web端调用的结果封装类
 * @author Rongcai Kang
 */
public class BannerVo extends OnlineBaseVo {
    /**
     * 图片路径
     */
    private String imgPath;
    /**
     * 图片描述
     */
    private String description;
    /**
     *
     * 图片跳转路径
     */
    private String imgHref;


    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgHref() {
        return imgHref;
    }

    public void setImgHref(String imgHref) {
        this.imgHref = imgHref;
    }
}
