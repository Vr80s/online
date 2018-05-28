package com.xczhihui.bxg.online.web.vo;

/**
 * @Author Fudong.Sun【】
 * @Date 2016/11/2 14:06
 */
public class CourseDescriptionVo {
    /**
     * ID
     */
    private String id;

    /**
     * 上一个ID
     */
    private String lastId;

    /**
     * 下一个ID
     */
    private String nextId;

    /**
     * 课程ID
     */
    private Integer courseId;

    /**
     * 学科名
     */
    private String menuName;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 课程类型
     */
    private String courseType;

    /**
     * 课程标题
     */
    private String courseTitle;

    /**
     * 课程内容
     */
    private String courseContent;

    /**
     * 预览状态 1:阅览 0:非预览
     */
    private Boolean preview;

    /**
     * 课程介绍内容
     */
    private String courseTitlePreview;

    /**
     * 课程介绍内容
     */
    private String courseContentPreview;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastId() {
        return lastId;
    }

    public void setLastId(String lastId) {
        this.lastId = lastId;
    }

    public String getNextId() {
        return nextId;
    }

    public void setNextId(String nextId) {
        this.nextId = nextId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseContent() {
        return courseContent;
    }

    public void setCourseContent(String courseContent) {
        this.courseContent = courseContent;
    }

    public Boolean getPreview() {
        return preview;
    }

    public void setPreview(Boolean preview) {
        this.preview = preview;
    }


    public String getCourseTitlePreview() {
        return courseTitlePreview;
    }

    public void setCourseTitlePreview(String courseTitlePreview) {
        this.courseTitlePreview = courseTitlePreview;
    }

    public String getCourseContentPreview() {
        return courseContentPreview;
    }

    public void setCourseContentPreview(String courseContentPreview) {
        this.courseContentPreview = courseContentPreview;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
}
