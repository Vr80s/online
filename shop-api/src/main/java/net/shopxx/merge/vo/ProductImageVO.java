package net.shopxx.merge.vo;

import java.io.Serializable;

/**
 * 商品图片VO
 *
 * @author hejiwei
 */
public class ProductImageVO implements Serializable {
    /**
     * 原图片
     */
    private String source;

    /**
     * 大图片
     */
    private String large;

    /**
     * 中图片
     */
    private String medium;

    /**
     * 缩略图
     */
    private String thumbnail;

    /**
     * 排序
     */
    private Integer order;

    @Override
    public String toString() {
        return "ProductImageVO{" +
                "source='" + source + '\'' +
                ", large='" + large + '\'' +
                ", medium='" + medium + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", order=" + order +
                '}';
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
