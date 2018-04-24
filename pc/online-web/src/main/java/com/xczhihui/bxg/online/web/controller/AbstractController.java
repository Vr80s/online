package com.xczhihui.bxg.online.web.controller;

import javax.servlet.http.HttpServletRequest;

import com.xczhihui.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.OnlineUser;

/**
 * Description: <br>
 *
 * @author: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time:  2018/3/7 0007-下午 8:29<br>
 */
public class AbstractController {

    public OnlineUser getOnlineUser(HttpServletRequest request) {
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        if (user == null) {
            throw new RuntimeException("未登录");
        }
        return user;
    }
}
