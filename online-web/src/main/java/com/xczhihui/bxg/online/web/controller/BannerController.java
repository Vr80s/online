package com.xczhihui.bxg.online.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xczhihui.bxg.online.web.service.BannerService;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.course.service.IMobileBannerService;

/**
 * Banner控制层实现类
 *
 * @author Rongcai Kang
 */
@RestController
@RequestMapping(value = "/banner")
public class BannerController extends AbstractController {

    @Autowired
    private BannerService service;

    
    @Autowired
    private IMobileBannerService mobileBannerService;
    
    /**
     * 查询Banner列表
     * type:2.主页banner3.头条banner4.创业banner5.海外banner
     *
     * @return ResponseObject
     */
    @RequestMapping(value = "/getBannerList", method = RequestMethod.GET)
    public ResponseObject listBanner(Integer type) {
        return ResponseObject.newSuccessResponseObject(service.list(null, null, type));
    }

    /**
     * 点击数量加1
     *
     * @param id 资讯id
     * @return
     */
    @RequestMapping(value = "/updateClickCount", method = RequestMethod.POST)
    public ResponseObject updateClickCount(Integer id) {
        service.updateClickCount(id);
        return ResponseObject.newSuccessResponseObject("操作成功！");
    }
    
    /**
     * 学堂点击数加 1 啊
     * @param id 资讯id
     * @return
     */
    @RequestMapping(value = "/updateSchoolClickCount", method = RequestMethod.POST)
    public ResponseObject updateSchoolClickCount(String id,
            @RequestParam(value="dataSource",required=false) Integer dataSource) {
        Integer count = mobileBannerService.addClickNum(id,null,dataSource);
        return ResponseObject.newSuccessResponseObject("操作成功！");
    }
    
}
