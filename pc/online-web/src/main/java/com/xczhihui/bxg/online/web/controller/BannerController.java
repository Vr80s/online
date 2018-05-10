package com.xczhihui.bxg.online.web.controller;

import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.web.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Banner控制层实现类
 * @author Rongcai Kang
 */
@RestController
@RequestMapping(value = "/banner")
public class BannerController extends AbstractController{

    @Autowired
    private BannerService  service;
    /**
     * 查询Banner列表
     * type:2.主页banner3.头条banner4.创业banner5.海外banner
     * @return ResponseObject
     */
    @RequestMapping(value = "/getBannerList",method= RequestMethod.GET)
    public ResponseObject listBanner(Integer type){
        return ResponseObject.newSuccessResponseObject(service.list(null,null,type));
    }

    /**
     * 点击数量加1
     * @param id 资讯id
     * @return
     */
    @RequestMapping(value = "/updateClickCount",method=RequestMethod.POST)
    public ResponseObject updateClickCount(Integer id) {
        service.updateClickCount(id);
        return ResponseObject.newSuccessResponseObject("操作成功！");
    }
}
