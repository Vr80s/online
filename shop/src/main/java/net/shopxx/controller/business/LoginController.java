/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: mHkPS1XKAzZYGI7+4yb5+bSy2AqOofvo
 */
package net.shopxx.controller.business;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.shopxx.entity.Business;
import net.shopxx.security.CurrentUser;

/**
 * Controller - 商家登录
 *
 * @author ixincheng
 * @version 6.1
 */
@Controller("businessLoginController")
@RequestMapping("/business")
public class LoginController extends BaseController {

    @Value("${security.business_login_success_url}")
    private String businessLoginSuccessUrl;

    /**
     * 登录跳转
     */
    @GetMapping({"", "/"})
    public String index() {
        return "redirect:/business/login";
    }

    /**
     * 登录页面
     */
    @GetMapping("/login")
    public String index(@CurrentUser Business currentUser, ModelMap model, @RequestParam(required = false) String freeSecretKey) {
        if (currentUser != null) {
            try {
                if (!StringUtils.isNotBlank(freeSecretKey)) {
                    return "redirect:" + businessLoginSuccessUrl;
                } else {
                    SecurityUtils.getSubject().logout();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("businessLoginSuccessUrl", businessLoginSuccessUrl);
        return "business/login/index";
    }

}