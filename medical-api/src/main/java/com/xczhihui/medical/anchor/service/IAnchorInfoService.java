package com.xczhihui.medical.anchor.service;

import java.util.Map;

import com.xczhihui.medical.anchor.vo.CourseAnchorVO;

/**
 * 主播工作台资产业务接口
 *
 * @author zhuwenbao
 */
public interface IAnchorInfoService {

    /**
     * 获取主播信息
     */
    CourseAnchorVO detail(String userId);

    /**
     * 更新主播信息
     */
    void update(CourseAnchorVO target);

    /**
     * 获取主播的认证信息
     */
    Object authInfo(String userId);

    void validateAnchorPermission(String userId);

    /**
     * 查看主播认证状态    0 未认证   1 医师   2 医馆   3 禁用
     *
     * @param userId
     * @return
     */
    Integer anchorPermissionStatus(String userId);

    /**
     * 通过医师id查看主播认证状态
     * 0 未认证   1 医师   2 医馆   3 禁用
     * 如果认证通过的话，医师对应的用户id
     *
     * @param DoctorId
     * @return
     */
    Map<String, Object> anchorPermissionStatusByDoctorId(String DoctorId);
}
