package com.xczhihui.bxg.online.web.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.vo.*;
import com.xczhihui.common.support.domain.BxgUser;

/**
 * 用户中心
 *
 * @author duanqh
 */
public interface OnlineUserCenterService {

    /**
     * 获取用户资料
     *
     * @return
     */
    UserDataVo getUserData(String userId);

    /**
     * 获取用户
     *
     * @param userId
     * @return
     */
    OnlineUser getUser(String userId);

    /**
     * 获取用户
     *
     * @param loginName
     * @return
     */
    OnlineUser getUserByLoginName(String loginName);

    /**
     * 修改用户资料
     *
     * @param user
     * @return
     */
    boolean updateUser(UserDataVo user);

    /**
     * 修改用户资料
     *
     * @param user
     * @return
     */
    Map<String, String> updateApply(ApplyVo user, HttpServletRequest request);

    /**
     * 修改用户头像
     *
     * @param user
     * @param img
     * @return
     */
    void updateUserHeadImg(BxgUser user, String img);

    /**
     * 修改密码
     *
     * @param userId 用户id
     * @param pwd    密码
     * @return
     */
    boolean updatePassword(String userId, String pwd);

    /**
     * 获取用户中心顶部显示信息
     *
     * @param userId 用户id
     * @return
     */
    UserCenterVo getUserInfo(String userId);

    /**
     * 根据区域id获取省份
     *
     * @param
     * @return
     */
    List<ProvinceVo> getAllProvince();

    /**
     * 根据省份id获取城市
     *
     * @param proId
     * @return
     */
    List<CityVo> getCityByProId(Integer proId);

    List<Map<String, Object>> getAllProvinceCity() throws SQLException;
}
