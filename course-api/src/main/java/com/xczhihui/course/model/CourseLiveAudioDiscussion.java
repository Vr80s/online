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
@TableName("oe_course_live_audio_discussion")
@Data
public class CourseLiveAudioDiscussion extends Model<CourseLiveAudioDiscussion> {

    private static final long serialVersionUID = 1L;

    /**
     * 音频直播弹幕讨论记录表
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 课程id
     */
    @TableField("course_id")
    private Integer courseId;
    /**
     * 音频直播记录id(最后一个播放的音频记录id,可为空)
     */
    @TableField("audio_live_content_id")
    private Integer audioLiveContentId;
    /**
     * 是否为提问，默认为否
     */
    @TableField("is_question")
    private Boolean question;
    /**
     * 1.文字2.语音3.图片
     */
    @TableField("content_type")
    private Integer contentType;
    private String content;
    @TableField("user_id")
    private String userId;
    /**
     * 回复的讨论id
     */
    @TableField("discussion_id")
    private Integer discussionId;
    /**
     * 源直播记录id(主播回复讨论时插入该id)
     */
    @TableField("source_audio_live_content_id")
    private Integer sourceAudioLiveContentId;
    @TableField("is_delete")
    private Boolean delete;
    @TableField("create_time")
    private Date createTime;
    /**
     * 点赞数
     */
    private Integer like;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
