package com.xczhihui.bxg.online.common.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bbs_user_status")
@Entity
public class BBSUserStatus implements Serializable {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "is_blacklist")
    private String blacklist;

    @Column(name = "is_gag")
    private String gag;

    @Override
    public String toString() {
        return "BBSUserStatus{" +
                "userId='" + userId + '\'' +
                ", blacklist='" + blacklist + '\'' +
                ", gag='" + gag + '\'' +
                '}';
    }

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
