package com.xczhihui.medical.bbs.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * @author hejiwei
 */
@TableName("bbs_user_status")
public class BBSUserStatus implements Serializable {

    @TableField("user_id")
    private String userId;

    @TableField("is_blacklist")
    private String blacklist;

    @TableField("is_gag")
    private String gag;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(String blacklist) {
        this.blacklist = blacklist;
    }

    public String getGag() {
        return gag;
    }

    public void setGag(String gag) {
        this.gag = gag;
    }
}
