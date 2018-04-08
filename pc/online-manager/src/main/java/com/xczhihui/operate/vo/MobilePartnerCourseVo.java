package com.xczhihui.operate.vo;

/**
 * 移动合伙人课程Vo
 * @Author Fudong.Sun【】
 * @Date 2017/3/9 21:01
 */
public class MobilePartnerCourseVo {
    private String id;
    private Integer course_id;
    private String img_url;
    private String share_desc;
    private String work_flow;

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

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getShare_desc() {
        return share_desc;
    }

    public void setShare_desc(String share_desc) {
        this.share_desc = share_desc;
    }

    public String getWork_flow() {
        return work_flow;
    }

    public void setWork_flow(String work_flow) {
        this.work_flow = work_flow;
    }
}
