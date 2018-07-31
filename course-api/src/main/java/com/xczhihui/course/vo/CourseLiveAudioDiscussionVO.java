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
public class CourseLiveAudioDiscussionVO implements Serializable{

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer courseId;
    private Integer audioLiveContentId;
    private Boolean question;
    private Integer contentType;
    private String content;
    private String userId;
    private Integer discussionId;
    private Integer sourceAudioLiveContentId;
    private Integer like;

    private String name;
    private String imgUrl;
    private Boolean anchor;

}
