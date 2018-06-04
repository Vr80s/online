package com.xczhihui.medical.enrol.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class MedicalEnrollmenRtegulationsCardInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    private String title;

    /**
     * 宣传语
     */
    private String propaganda;
    /**
     * 老师超短介绍
     */
    private String doctorIntroduction;
    /**
     * 学费
     */
    private String tuition;
    /**
     * 招生人数
     */
    private String countLimit;
    /**
     * 报名截至时间
     */
    private Date deadline;

    /**
     * 学习地址（省-市-区-详细地址）
     */
    private String studyAddress;

    private String profilePicture;

    private String enrolShareUrl;

}
