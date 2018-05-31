package com.xczhihui.user.center.service;


import com.xczhihui.common.util.enums.TokenExpires;
import com.xczhihui.common.util.enums.UserOrigin;
import com.xczhihui.user.center.vo.OeUserVO;
import com.xczhihui.user.center.vo.Token;

/**
 * 用户中心API
 *
 * @author Haicheng Jiang
 */
public interface UserCenterService {

    /**
     * 注册一个用户，会对明文密码MD5；password属性必须是明文。
     *
     * @param loginName
     * @param password
     * @param nikeName
     * @param origin    用户来源(从什么地方注册)
     * @return
     */
    void regist(
            String loginName,
            String password,
            String nikeName,
            UserOrigin origin);

    /**
     * 逻辑删除用户
     *
     * @param userId
     * @return 返回被删除的用户，如果没有找到对应的用户，返回NULL
     */
    void deleteUserLogic(String userId);

    /**
     * 更新用户信息；不会更新登录名、密码、用户来源（origin）。
     *
     * @return
     */
    void update(OeUserVO oeUserVO);

    /**
     * 修改用户中心用户状态
     *
     * @param userId
     * @param status 0正常，-1禁用，详见#UserStatus
     */
    void updateStatus(String userId, int status);

    /**
     * 修改密码；密码值必须是明文。如果是重置密码，oldPassword可以不传，传了会校验原密码是否正确。
     *
     * @param loginName
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @return
     */
    void updatePassword(String loginName, String oldPassword, String newPassword);

    void resetPassword(String loginName, String newPassword);

    void updateLoginName(String oldLoginName, String newLoginName);

    /**
     * 根据登录名查找用户
     *
     * @param loginName
     * @return
     */
    OeUserVO getUserVO(String loginName);

    /**
     * 延长票的有效期。
     *
     * @param ticket
     * @param tokenExpires 过期时间类型。
     * @return
     */
    Token reflushTicket(String ticket, TokenExpires tokenExpires);

    Token loginThirdPart(String username, TokenExpires tokenExpires) throws Exception;

    Token loginMobile(String loginName, String password, TokenExpires tokenExpires);

    Token getToken(String ticket);

    void deleteToken(String ticket);

    void updateTokenInfo(String userId, String ticket);

    /**
     * 判断用户是否被禁用
     *
     * @param userId
     * @return
     */
    boolean isDisabled(String userId);
}
