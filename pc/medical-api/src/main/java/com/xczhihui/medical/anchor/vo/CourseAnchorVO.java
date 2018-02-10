package com.xczhihui.medical.anchor.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CourseAnchorVO implements Serializable {

    private static final long serialVersionUID = -3415214036193627704L;

    private Integer id;

    private String UserId;

    /**
     * 致辞视频
     */
    private String video;

    /**
     * 名称
     */
    private String name;

    /**
     * 头像
     */
    private String profilePhoto;

    /**
     * 主播介绍
     */
    private String detail;

    /**
     * 主播所在医馆名
     */
    private String hospitalName;

    /**
     * 主播所在医馆id
     */
    private String hospitalId;

    /**
     * 医师坐诊时间
     */
    private String workTime;

    /**
     * 医师预约电话
     */
    private String tel;

    /**
     * 主播所在省份
     */
    private String province;

    /**
     * 主播所在城市
     */
    private String city;

    /**
     *
     */
    private String detailAddress;

}
