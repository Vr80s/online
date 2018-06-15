package com.xczhihui.user.center.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 用户登录后的标识
 *
 * @author liyong
 */
@Data
public class Token implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户在用户中心的ID
     */
    private String userId;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 用户来源(从什么地方注册)bxg，dual，ask
     */
    private String origin;
    /**
     * 登录后产生的票
     */
    private String ticket;
    /**
     * 票的有效期,1970开始的毫秒数
     */
    private long expires;

    private String mobile;

    /**
     * 目前定义了普通用户、学生、老师，也可以由业务系统自己定义。
     */
    private int type;

    private String email;

    private String nickName;

    private String headPhoto;

    private String uuid;

    private String passWord;

    private Boolean visitor;

}
