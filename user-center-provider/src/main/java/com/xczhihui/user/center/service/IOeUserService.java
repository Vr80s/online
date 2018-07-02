package com.xczhihui.user.center.service;

import com.baomidou.mybatisplus.service.IService;
import com.xczhihui.user.center.model.ItcastUser;
import com.xczhihui.user.center.model.OeUser;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yuxin
 * @since 2018-05-14
 */
public interface IOeUserService extends IService<OeUser> {

    void updateUsersInit() throws Exception;

    void updateUserInit(ItcastUser itcastUser);

    void addOeUser(OeUser oeUser);

    void updateOeUser(OeUser user);

    void updateStatus(String userId, int status);

    void updatePassword(String id, String password);

    void updateLoginName(String oldLoginName, String newLoginName);
}
