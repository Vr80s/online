package com.xczhihui.bxg.online.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 笔记模块封装实体类
 * @Author Fudong.Sun【】
 * @Date 2016/12/13 10:51
 */
public class NotesVo {
    /**
     * ID
     */
    private String id;
    /**
     * 课程ID
     */
    private Integer course_id;
    /**
     * 视频ID
     */
    private String video_id;
    /**
     * 知识点ID
     */
    private String chapter_id;
    /**
     * 用户ID
     */
    private String user_id;
    /**
     * 笔记作者名
     */
    private String user_name;
    /**
     * 作者头像
     */
    private String small_head_photo;
    /**
     * 班级ID
     */
    private Integer grade_id;
    /**
     * 笔记内容
     */
    private String content;
    /**
     * 点赞数
     */
    private Integer praise_sum;
    /**
     * 是否分享
     */
    private Boolean is_share;
    /**
     * 评论数
     */
    private Integer comment_sum;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date create_time;
    /**
     * 创建人
     */
    private String create_person;
    /**
     * 当前用户是否点赞
     */
    private Boolean praise;
    /**
     * 当前用户是否收藏
     */
    private Boolean collect;
    /**
     * 当前用户是否显示删除按钮
     */
    private Boolean deleteButton;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Integer course_id) {
        this.course_id = course_id;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Integer getGrade_id() {
        return grade_id;
    }

    public void setGrade_id(Integer grade_id) {
        this.grade_id = grade_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPraise_sum() {
        return praise_sum;
    }

    public void setPraise_sum(Integer praise_sum) {
        this.praise_sum = praise_sum;
    }

    public Boolean getIs_share() {
        return is_share;
    }

    public void setIs_share(Boolean is_share) {
        this.is_share = is_share;
    }

    public Integer getComment_sum() {
        return comment_sum;
    }

    public void setComment_sum(Integer comment_sum) {
        this.comment_sum = comment_sum;
    }

    public String getCreate_person() {
        return create_person;
    }

    public String getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public void setCreate_person(String create_person) {
        this.create_person = create_person;
    }

    public Boolean getPraise() {
        return praise;
    }

    public void setPraise(Boolean praise) {
        this.praise = praise;
    }

    public Boolean getCollect() {
        return collect;
    }

    public void setCollect(Boolean collect) {
        this.collect = collect;
    }

    public Boolean getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(Boolean deleteButton) {
        this.deleteButton = deleteButton;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getSmall_head_photo() {
        return small_head_photo;
    }

    public void setSmall_head_photo(String small_head_photo) {
        this.small_head_photo = small_head_photo;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
