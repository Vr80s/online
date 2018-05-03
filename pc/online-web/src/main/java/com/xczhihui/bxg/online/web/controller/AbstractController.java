package com.xczhihui.bxg.online.web.controller;

import com.xczhihui.bxg.online.common.domain.OnlineUser;

/**
 * Description: <br>
 *
 * @author: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time:  2018/3/7 0007-下午 8:29<br>
 */
public class AbstractController {

    private static final ThreadLocal<OnlineUser> CURRENT_USER = new ThreadLocal<OnlineUser>();

    public static void setCurrentUser(OnlineUser user) {
        CURRENT_USER.set(user);
    }

    public static OnlineUser getCurrentUser() {
        return CURRENT_USER.get();
    }

}
