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
public class CourseLiveAudioLikeMessageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    public CourseLiveAudioLikeMessageVO(int id, int likes) {
        this.id = id;
        this.likes = likes;
    }

    private int id;
    private int likes;
}
