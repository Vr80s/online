package com.xczhihui.medical.bbs.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * @author hejiwei
 */
@TableName("quark_browse_record")
public class BrowseRecord implements Serializable {

    @TableId(type = IdType.AUTO)
    private int id;

    @TableField("post_id")
    private int postId;

    @TableField("user_id")
    private String userId;

    @TableField("insert_time")
    private Date insertTime;

    public BrowseRecord() {
    }

    public BrowseRecord(int postId, String userId) {
        this.postId = postId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
}
