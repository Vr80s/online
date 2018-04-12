package com.xczhihui.bbs.vo;

public class BBSOnlineUserVo {

    private String id;

    private String nickname;

    private String mobile;

    private String blacklist;

    private String gags;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(String blacklist) {
        this.blacklist = blacklist;
    }

    public String getGags() {
        return gags;
    }

    public void setGags(String gags) {
        this.gags = gags;
    }
}
