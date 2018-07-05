package com.xczhihui.medical.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 动态评论VO
 * @author hejiwei
 */
public class DoctorPostsCommentVO {

    private Integer id;

    private String content;

    private String doctorName;

    private String nickname;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @Override
    public String toString() {
        return "DoctorPostsCommentVO{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", nickname='" + nickname + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
