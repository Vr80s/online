package com.xczhihui.bxg.online.common.domain;

import java.io.Serializable;

import javax.persistence.*;

@Table(name = "quark_label")
@Entity
public class BBSLabel implements Serializable {

    /**
     * 唯一标识
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //标签名称
    @Column(nullable = false, unique = true)
    private String name;

    //主题数量
    @Column(name = "posts_count")
    private Integer postsCount = 0;

    //是否被删除
    @Column(name = "is_disable")
    private boolean isDisable;

    //排序使用
    @Column(name = "sort")
    private Integer sort;

    //详情
    private String details;

    //排序使用
    @Column(name = "label_img_url")
    private String labelImgUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPostsCount() {
        return postsCount;
    }

    public void setPostsCount(Integer postsCount) {
        this.postsCount = postsCount;
    }

    public boolean isDisable() {
        return isDisable;
    }

    public void setDisable(boolean disable) {
        isDisable = disable;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLabelImgUrl() {
        return labelImgUrl;
    }

    public void setLabelImgUrl(String labelImgUrl) {
        this.labelImgUrl = labelImgUrl;
    }
}
