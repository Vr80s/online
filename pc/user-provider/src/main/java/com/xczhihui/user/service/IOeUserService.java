package com.xczhihui.user.service;

import com.baomidou.mybatisplus.service.IService;
import com.xczhihui.user.model.ItcastUser;
import com.xczhihui.user.model.OeUser;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuxin
 * @since 2018-05-14
 */
public interface IOeUserService extends IService<OeUser> {

    void updateUserInit(ItcastUser itcastUser);
}
