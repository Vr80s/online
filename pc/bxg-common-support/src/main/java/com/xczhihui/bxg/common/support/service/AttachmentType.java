package com.xczhihui.bxg.common.support.service;

import java.io.Serializable;

/**
 * 附件类型，用来区分附件是哪个业务系统的。
 *
 * @author liyong
 */
public enum AttachmentType implements Serializable {
    /**
     * 作业
     */
    HOMEWORK(1, "homework"),
    /**
     * 班级
     */
    CLASSES(2, "classes"),
    /**
     * 课程
     */
    COURSE(3, "course"),
    /**
     * 首页banner
     */
    BANNER(4, "banner"),
    /**
     * 题库
     */
    QUESTION(5, "question"),
    /**
     * 版权
     */
    COPYRIGHT(6, "copyright"),
    /**
     * 知识中心
     */
    KCENTER(7, "kcenter"),
    /**
     * 用户中心
     */
    UCENTER(8, "ucenter"),
    /**
     * 用户头像
     */
    HEADPHOTO(10, "headphoto"),
    /**
     * 考试系统用
     */
    EXAM(20, "exam"),
    /**
     * 版权
     */
    OTHER(100, "other"),

    //=================附件中心用的：======================
    /**
     * 双元
     */
    DUAL(81, "dual"),
    /**
     * 院校
     */
    UNIV(82, "univ"),
    /**
     * 在线
     */
    ONLINE(83, "online"),
    /**
     * 问答中心
     */
    QCENTER(84, "qcenter"),
    /**
     * 附件中心
     */
    ACENTER(85, "acenter");

    private AttachmentType(int type, String name) {
        this.type = type;
        this.name = name;
    }

    private int type;

    private String name;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
