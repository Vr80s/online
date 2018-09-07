package com.xczhihui.course.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xczhihui.common.util.enums.CourseLiveAudioMessageType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author yuxin
 * @since 2018-07-31
 */
@Data
public class CourseLiveAudioDiscussionVO implements Serializable{

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer courseId;
    private Boolean question;
    private Integer contentType;
    private String content;
    private String userId;
    private Integer discussionId;
    private Integer sourceAudioLiveContentId;
    private Integer likes;
    private Boolean anchor;
    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
    private Date createTime;

    private String name;
    private String imgUrl;

    private CourseLiveAudioDiscussionVO courseLiveAudioDiscussionVO;

    public String toJson() {
        CourseLiveAudioMessageVO message = new CourseLiveAudioMessageVO(CourseLiveAudioMessageType.CourseLiveAudioDiscussion.getCode(),this);
        return message.toJson();
    }
}
