package com.xczhihui.medical.common.bean;

import java.io.Serializable;

/**
 * Description：图片路径及宽高
 * creed: Talk is cheap,show me the code
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/7/4 20:19
 **/
public class PictureSpecification implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 图片路径
     */
    private String imgUrl;
    /**
     * 图片宽
     */
    private Integer width;
    /**
     * 图片高
     */
    private Integer height;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "PictureSpecification{" +
                "imgUrl='" + imgUrl + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
