package com.xczhihui.medical.common.service;

/**
 * 医师医馆公共服务接口
 */
public interface ICommonService {

    /**
     * 根据用户id 判断用户是否是认证医师
     * @param userId 用户id
     */
    boolean isDoctor(String userId);

    /**
     * 根据用户id 判断用户拥有认证医馆
     * @param userId 用户id
     */
    boolean isHospital(String userId);

    /**
     * 根据用户id 判断用户是医师还是医馆
     * @param userId 用户id
     * @return 1：医师认证 2：医馆认证 3：医师认证中 4：医馆认证中
     */
    Integer isDoctorOrHospital(String userId);
}
