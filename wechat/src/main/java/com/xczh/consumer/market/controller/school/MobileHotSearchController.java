package com.xczh.consumer.market.controller.school;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.enums.SearchType;
import com.xczhihui.course.service.IMobileHotSearchService;

/**
 * 热门搜索控制器 ClassName: MobileRecommendController.java <br>
 * Description: <br>默认关键字与热门搜索
 * Create by: name：wangyishuai <br>
 * email: 15210815880@163.com <br>
 * Create Time: 2018年1月16日<br>
 */
@Controller
@RequestMapping("/xczh/bunch")
public class MobileHotSearchController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MobileHotSearchController.class);
    @Autowired
    private IMobileHotSearchService mobileHotSearchService;

    /**
     * 热门搜索列表
     */
    @RequestMapping("hotSearch")
    @ResponseBody
    public ResponseObject hotSearchList()
            throws Exception {
        Map<String, Object> mapAll = new HashMap<String, Object>();
        //默认搜索框
        mapAll.put("defaultSearch", mobileHotSearchService.HotSearchList(SearchType.SCHOOL_DEFAULT_SEARCH.getCode()));
        //热门搜索
        mapAll.put("hotSearch", mobileHotSearchService.HotSearchList(SearchType.SCHOOL_HOT_SEARCH.getCode()));

        return ResponseObject.newSuccessResponseObject(mapAll);
    }

    /**
     * 热门医师搜索列表
     */
    @RequestMapping("hotDoctorSearch")
    @ResponseBody
    public ResponseObject hotDoctorSearchList()
            throws Exception {
        Map<String, Object> mapAll = new HashMap<String, Object>();
        //默认搜索框
        mapAll.put("defaultSearch", mobileHotSearchService.HotSearchList(SearchType.DOCTOR_DEFAULT_SEARCH.getCode()));
        //热门搜索
        mapAll.put("hotSearch", mobileHotSearchService.HotSearchList(SearchType.DOCTOR_HOT_SEARCH.getCode()));
        return ResponseObject.newSuccessResponseObject(mapAll);
    }
}
