package com.xczhihui.bxg.online.web.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xczhihui.bxg.online.web.controller.AbstractController;
import com.xczhihui.common.support.domain.BxgUser;

/**
 * Description：取当前登录用户,兼容之前调用方式
 * creed: Talk is cheap,show me the code
 *
 * @author name：yuxin
 * @Date: 2018/6/5 0005 上午 10:20
 **/
public final class UserLoginUtil {
    protected static Logger logger = LoggerFactory.getLogger(UserLoginUtil.class);

    /**
     * 获取当前登录用户。
     *
     * @return
     */
    public static BxgUser getLoginUser() {
        BxgUser ou = AbstractController.getCurrentUser();
        return ou;
    }

}
