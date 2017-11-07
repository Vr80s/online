package com.xczhihui.bxg.online.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 文章评论封装类
 *
 * @author 康荣彩
 * @create 2016-08-30 13:25
 */
public class AppraiseVo {

    /**
     * 评论id
     */
    private  String id;
    /**
     * 文章id
     */
    private  Integer article_id;

    /**
     * 评论内容
     */
    private  String content;

    /**
     * 评论者id
     */
    private String  user_id;

    /**
     * 被回复者id
     */
    private String target_user_id;

    /**
     * 评论时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date create_time;

    /**
     * 评论者头像
     */
    private String small_head_photo;

    /**
     * 评论者昵称
     */
    private String name;

    /**
     * 被回复者昵称
     */
    private String nickName;

    /**
     * 是否自己评论  0:非自己评论 1:自己评论
     */
    private Boolean  isMySelf;

    public Integer getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Integer article_id) {
        this.article_id = article_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTarget_user_id() {
        return target_user_id;
    }

    public void setTarget_user_id(String target_user_id) {
        this.target_user_id = target_user_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getSmall_head_photo() {
        return small_head_photo;
    }

    public void setSmall_head_photo(String small_head_photo) {
        this.small_head_photo = small_head_photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Boolean isMySelf() {
        return isMySelf;
    }

    public void setIsMySelf(Boolean isMySelf) {
        this.isMySelf = isMySelf;
    }


}
