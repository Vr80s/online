package com.xczhihui.bxg.online.web.controller;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.web.service.OtherlinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 友情链接控制层实现类
 * @author Rongcai Kang
 */
@RestController
@RequestMapping(value = "/otherlink")
public class OtherlinkController {

    @Autowired
    private OtherlinkService service;

    /**
     * 获取全部友情链接数据列表信息
     * @param pageNumber 当前是第几页，默认1
     * @param pageSize 每页显示多少行，默认20
     * @return
     */
    @RequestMapping(value = "/getOtherLink")
    public ResponseObject getOtherLink(){
        return ResponseObject.newSuccessResponseObject(service.getOtherLink(null,null));
    }
}
