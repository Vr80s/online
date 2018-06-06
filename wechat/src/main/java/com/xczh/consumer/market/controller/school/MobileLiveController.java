package com.xczh.consumer.market.controller.school;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.interceptor.IOSVersionInterceptor;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.enums.BannerType;
import com.xczhihui.common.util.enums.PagingFixedType;
import com.xczhihui.course.model.MobileBanner;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.service.IMobileBannerService;

/**
 * 直播控制器 ClassName: MobileRecommendController.java <br>
 * Description: <br>
 * Create by: name：wangyishuai <br>
 * email: 15210815880@163.com <br>
 * Create Time: 2018年1月16日<br>
 */
@Controller
@RequestMapping("/xczh/live")
public class MobileLiveController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MobileLiveController.class);

    @Autowired
    private IMobileBannerService mobileBannerService;


    /*****************************************
     * 		新版app关于学堂的接口   -- 直播接口
     * **************************************
     */
    @RequestMapping("onlineLive")
    @ResponseBody
    public ResponseObject onlineLive(HttpServletRequest req,
                                     HttpServletResponse res)
            throws Exception {
        Map<String, Object> mapAll = new HashMap<String, Object>();
        //直播banner
        Page<MobileBanner> mobileBannerPage = new Page<>();
        mobileBannerPage.setRecords(mobileBannerService.selectMobileBannerPage(BannerType.LIVE.getCode()));
        mapAll.put("banner", mobileBannerPage);
        List<Map<String, Object>> mapCourseList = mobileBannerService.liveCourseList(
                PagingFixedType.LIVE_PAGETYPE_UP.getValue(),
                PagingFixedType.LIVE_PAGETYPE_DOWN.getValue(),
                IOSVersionInterceptor.onlyThread.get());
        mapAll.put("allCourseList", mapCourseList);
        return ResponseObject.newSuccessResponseObject(mapAll);
    }
}
