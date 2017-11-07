package com.xczhihui.bxg.online.web.vo;

/**
 * 用户视频分装类
 * @author Rongcai Kang
 *
 */
public class UserVideoVo {

    /**
     * 用户视频中间表id
     */
    private String id;

    /**
     * 视频id
     */
    private String video_id;
    /**
     *用户id
     */
    private String user_id;

    /**
     * 课程id
     */
    private Integer course_id;

    /**
     * 创建人
     */
    private String create_person;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Integer getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Integer course_id) {
        this.course_id = course_id;
    }

    public String getCreate_person() {
        return create_person;
    }

    public void setCreate_person(String create_person) {
        this.create_person = create_person;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }
}
