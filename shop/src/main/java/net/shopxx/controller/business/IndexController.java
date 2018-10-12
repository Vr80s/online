/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: ijE5n49cFNoYcBLEkbICE3SpvQWMNiYS
 */
package net.shopxx.controller.business;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller - 商家中心
 *
 * @author SHOP++ Team
 * @version 6.1
 */
@Controller("businessIndexController")
@RequestMapping("/business/index")
public class IndexController extends BaseController {

    /**
     * 首页
     */
    @GetMapping
    public String index(@RequestParam(required = false) String freeSecretKey) {
        try {
            if (StringUtils.isNotBlank(freeSecretKey)) {
                SecurityUtils.getSubject().logout();
            }
        } catch (Exception e) {
        }
        return "/business/index";
    }

}