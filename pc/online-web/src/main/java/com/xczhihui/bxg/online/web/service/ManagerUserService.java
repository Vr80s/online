package com.xczhihui.bxg.online.web.service;/**
 * Created by admin on 2016/10/13.
 */

import com.xczhihui.bxg.online.common.domain.User;

/**
 * Manager端用户业务层接口
 *
 * @author 康荣彩
 * @create 2016-10-13 17:06
 */
public interface ManagerUserService {
    /**
     * 查看当前登录帐号是否存在于manager端
     * @param loginName  当前登录用户的登录帐号
     * @return
     */
    public User findUserByLoginName(String loginName);
}
