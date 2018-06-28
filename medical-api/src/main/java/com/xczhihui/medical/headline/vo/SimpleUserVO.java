package com.xczhihui.medical.headline.vo;

import java.io.Serializable;

/**
 * 简单用户信息表
 *
 * @author hejiwei
 */
public class SimpleUserVO implements Serializable {
    private String id;

    private String name;

    private String smallHeadPhoto;

    public SimpleUserVO(String id, String name, String smallHeadPhoto) {
        this.id = id;
        this.name = name;
        this.smallHeadPhoto = smallHeadPhoto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSmallHeadPhoto() {
        return smallHeadPhoto;
    }

    public void setSmallHeadPhoto(String smallHeadPhoto) {
        this.smallHeadPhoto = smallHeadPhoto;
    }

    @Override
    public String toString() {
        return "SimpleUserVO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", smallHeadPhoto='" + smallHeadPhoto + '\'' +
                '}';
    }
}
