package com.xczh.consumer.market.vo;

/**
 *  教师结果封装类
 * @author Rongcai Kang
 */
public class LecturVo {

    /**
     * 讲师名
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 头像
     */
    private String headImg;

    private String link;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
