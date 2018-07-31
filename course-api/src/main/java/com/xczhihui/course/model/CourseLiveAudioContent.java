package com.xczhihui.course.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author yuxin
 * @since 2018-07-31
 */
@TableName("oe_course_live_audio_content")
@Data
public class CourseLiveAudioContent extends Model<CourseLiveAudioContent> {

    private static final long serialVersionUID = 1L;

    /**
     * 音频直播内容记录表
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 课程id
     */
    @TableField("course_id")
    private Integer courseId;
    /**
     * 课件图片id
     */
    @TableField("audio_ppt_id")
    private Integer audiopptId;
    /**
     * 1.文字2.语音3.图片
     */
    @TableField("content_type")
    private Integer contentType;
    private String content;
    /**
     * 音频长度，单位：s
     */
    private Integer length;
    @TableField("user_id")
    private String userId;
    /**
     * 回复的讨论id
     */
    @TableField("discussion_id")
    private Integer discussionId;
    /**
     * 点赞数
     */
    private Integer like;
    @TableField("is_delete")
    private Boolean delete;
    @TableField("create_time")
    private Date createTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
