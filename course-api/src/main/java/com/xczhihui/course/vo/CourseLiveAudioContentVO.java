package com.xczhihui.course.vo;

import java.io.Serializable;

import lombok.Data;

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
    private Integer length;
    private String userId;
    private Integer discussionId;
    private Integer like;

    private CourseLiveAudioDiscussionVO courseLiveAudioDiscussionVO;
}
