package com.xczhihui.medical.bbs.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * @author hejiwei
 */
@TableName("quark_label")
public class Label implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField
    private String name;

    @TableField("posts_count")
    private Integer postsCount = 0;

    @TableField("is_disable")
    private boolean disabled;

    @TableField("sort")
    private Integer sort;

    private String details;

    @TableField("label_img_url")
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

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
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
