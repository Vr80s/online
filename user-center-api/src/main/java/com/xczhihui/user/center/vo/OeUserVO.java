package com.xczhihui.user.center.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class OeUserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String loginName;
    private String salt;
    private String password;
    private Integer sex;
    private String mobile;
    private String email;
    private String smallHeadPhoto;
    private Date createTime;
    private Integer status;
    private String lastLoginIp;
    private Date lastLoginDate;
    private Integer visitSum;
    private Integer stayTime;
    private Integer jobyears;
    private Integer occupation;
    private String regionId;
    private String regionAreaId;
    /**
     * 市
     */
    private String regionCityId;
    /**
     * 职业,其他
     */
    private String occupationOther;
    /**
     * 详细地址
     */
    private String fullAddress;
    /**
     * 三方用户唯一标识
     */
    private String unionId;
    /**
     * 三方账号绑定的账号ID
     */
    private String refId;
    /**
     * 分享者id(上级用户)
     */
    private String parentId;
    /**
     * 分享码
     */
    private String shareCode;
    /**
     * 变更时间
     */
    private Date changeTime;
    /**
     * 用户来源：1.pc 2.h5 3.android 4.ios 5.导入
     */
    private String origin;
    /**
     * 微吼id
     */
    private String vhallId;
    /**
     * 微吼密码
     */
    private String vhallPass;
    /**
     * 微吼登录名
     */
    private String vhallName;
    private String provinceName;
    private String cityName;
    /**
     * 地区的名字
     */
    private String countyName;
    private Boolean visitor;

}
