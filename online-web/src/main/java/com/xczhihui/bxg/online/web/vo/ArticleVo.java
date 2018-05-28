package com.xczhihui.bxg.online.web.vo;

import java.util.Date;

/**
 * 文章信息封装类
 *
 * @author 康荣彩
 * @create 2016-08-30 13:25
 */
public class ArticleVo {
    /**
     * 文章id
     */
    private  Integer id;
    /**
     * 文章标题
     */
    private  String title;
    /**
     * 文章内容
     */
    private  String content;
    /**
     * 文章图片
     */
    private  String img_path;
    /**
     * 文章发表时间
     */
    private  Date create_time;
    /**
     * 作者
     */
    private String name;
    /**
     * 标签
     */
    private String tag;

    /**
     * 标签id
     */
    private String tagId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }
}
