package com.xczhihui.medical.anchor.vo;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author yuxin
 * @since 2018-01-19
 */
public class CourseApplyResourceVO implements Serializable {

    /**
     * 课程申请资源表
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer caiId;
    /**
     * 标题
     */
    private String title;
    /**
     * 资源id
     */
    private String resource;
    /**
     * 作者id
     */
    private String userId;
    /**
     * 多媒体类型:1视频2音频
     */
    private Integer multimediaType;
    /**
     * 资源来源:1资源2动态
     */
    private Integer sourceType;
    /**
     * 是否删除：0 未删除 1 删除
     */
    private Boolean isDelete;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    private String length;

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getMultimediaType() {
        return multimediaType;
    }

    public void setMultimediaType(Integer multimediaType) {
        this.multimediaType = multimediaType;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCaiId() {
        return caiId;
    }

    public void setCaiId(Integer caiId) {
        this.caiId = caiId;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }
}
