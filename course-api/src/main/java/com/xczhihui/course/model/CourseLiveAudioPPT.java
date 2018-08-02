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
@TableName("oe_course_live_audio_ppt")
@Data
public class CourseLiveAudioPPT extends Model<CourseLiveAudioPPT> {

    private static final long serialVersionUID = 1L;

    /**
     * 音频直播课件表
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 课程id
     */
    @TableField("course_id")
    private Integer courseId;
    /**
     * 课件图片
     */
    @TableField("ppt_img_url")
    private String imgUrl;
    @TableField("user_id")
    private String userId;
    /**
     * 排序字段
     */
    private Integer sort;
    @TableField("create_time")
    private Date createTime;
    @TableField("is_delete")
    private Boolean delete;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
