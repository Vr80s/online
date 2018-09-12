package com.xczhihui.course.vo;

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
public class CourseLiveAudioContentVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer courseId;
    private Integer pptImgId;
    private Integer contentType;
    private String content;
    private String pptImgUrl;
    private Integer length;
    private String userId;
    private Integer discussionId;
    private Integer likes;
    private Date createTime;
    private String name;
    private String imgUrl;

    private CourseLiveAudioDiscussionVO courseLiveAudioDiscussionVO;

    public String toJson() {
        CourseLiveAudioMessageVO message = new CourseLiveAudioMessageVO(CourseLiveAudioMessageType.CourseLiveAudioContent.getCode(),this);
        return message.toJson();
    }
}
