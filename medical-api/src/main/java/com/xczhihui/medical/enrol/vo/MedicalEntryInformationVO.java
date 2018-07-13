package com.xczhihui.medical.enrol.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class MedicalEntryInformationVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 师承模块开发
     */
    private Integer merId;
    private String name;
    private Integer age;
    /**
     * 男1 女0 未知2
     */
    private Integer sex;
    /**
     * 省-市
     */
    private String nativePlace;
    /**
     * 0无、1小学、2初中、3高中、4大专、5本科、6研究生、7博士生、8博士后
     */
    private Integer education;
    /**
     * 学习经历
     */
    private String educationExperience;
    /**
     * 行医经历
     */
    private String medicalExperience;
    /**
     * 学中医的目标
     */
    private String goal;
    /**
     * 手机号
     */
    private String tel;
    /**
     * 微信号
     */
    private String wechat;
    /**
     * 用户id
     */
    private String userId;

    private String doctorId;

    private Boolean applied;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date createTime;

    private Integer apprentice;

}
