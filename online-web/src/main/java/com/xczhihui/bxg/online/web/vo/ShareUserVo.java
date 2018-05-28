package com.xczhihui.bxg.online.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 分享用户封装类
 * @Author Rongcai.Kang
 * @Date 2016/12/9 10:52
 */
public class ShareUserVo implements Serializable {

    /**
     * 用户昵称
     */
    private String  name;

    /**
     * 用户名
     */
    private String  login_name;
    /**
     * 注册时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Timestamp create_time;

    /**
     * 购买课程的数量
     */
    private Integer buyCouseCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public Integer getBuyCouseCount() {
        return buyCouseCount==null ? 0 :buyCouseCount;
    }

    public void setBuyCouseCount(Integer buyCouseCount) {
        this.buyCouseCount = buyCouseCount;
    }
}
