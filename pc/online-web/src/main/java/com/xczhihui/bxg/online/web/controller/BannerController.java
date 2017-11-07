package com.xczhihui.bxg.online.web.controller;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
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
public class BannerController {

    @Autowired
    private BannerService  service;
    /**
     * 查询Banner全部列表
     * @return ResponseObject
     */
    @RequestMapping(value = "/getBannerList",method= RequestMethod.GET)
    public ResponseObject listBanner(){
        return ResponseObject.newSuccessResponseObject(service.list(null,null));
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
