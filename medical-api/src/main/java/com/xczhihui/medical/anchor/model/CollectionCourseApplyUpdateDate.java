package com.xczhihui.medical.anchor.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("collection_course_apply_update_date")
public class CollectionCourseApplyUpdateDate extends Model<CollectionCourseApplyUpdateDate> {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("collection_apply_id")
    private Integer collectionApplyId;

    @TableField("collection_id")
    private Integer collectionId;

    //1,2,3等分别代表周一，周二，周三等
    private Integer date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCollectionApplyId() {
        return collectionApplyId;
    }

    public void setCollectionApplyId(Integer collectionApplyId) {
        this.collectionApplyId = collectionApplyId;
    }

    public Integer getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Integer collectionId) {
        this.collectionId = collectionId;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
