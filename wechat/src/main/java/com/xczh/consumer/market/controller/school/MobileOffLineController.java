package com.xczh.consumer.market.controller.school;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.interceptor.IOSVersionInterceptor;
import com.xczh.consumer.market.utils.APPUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.enums.BannerType;
import com.xczhihui.common.util.enums.PagingFixedType;
import com.xczhihui.course.model.MobileBanner;
import com.xczhihui.course.model.OfflineCity;
import com.xczhihui.course.service.IMobileBannerService;
import com.xczhihui.course.service.IOfflineCityService;

/**
 * 线下课控制器 ClassName: MobileRecommendController.java <br>
 * Description: <br>
 * Create by: name：wangyishuai <br>
 * email: 15210815880@163.com <br>
 * Create Time: 2018年1月16日<br>
 */
@Controller
@RequestMapping("/xczh/bunch")
public class MobileOffLineController {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MobileOffLineController.class);


    @Autowired
    private IMobileBannerService mobileBannerService;

    @Autowired
    private IOfflineCityService offlineCityService;


    /*****************************************
     *
     *
     * 	新版app关于学堂的接口   -- 线下培训班接口
     *
     *
     * **************************************
     */

    @RequestMapping("offLine")
    @ResponseBody
    public ResponseObject offLine(HttpServletRequest request) throws Exception {
        Integer current = 1;
        Integer size = 100;
        Map<String, Object> mapAll = new HashMap<String, Object>();
        //线下课banner
        Page<MobileBanner> MobileBannerPage = new Page<>();
        MobileBannerPage.setRecords(mobileBannerService.selectMobileBannerPage(BannerType.REAL.getCode(), IOSVersionInterceptor.onlyThread.get(), APPUtil.getMobileSource(request)));
        mapAll.put("banner", MobileBannerPage);
        //城市
        Page<OfflineCity> OfflineCityPage = new Page<>();
        OfflineCityPage.setCurrent(current);
        OfflineCityPage.setSize(size);
        Page<OfflineCity> oclist = offlineCityService.selectOfflineRecommendedCityPage(OfflineCityPage);
        mapAll.put("cityList", oclist);

        Page<OfflineCity> OfflineCity = new Page<>();
        OfflineCity.setCurrent(1);
        OfflineCity.setSize(4);
        Page<OfflineCity> ocl = offlineCityService.selectOfflineRecommendedCityPage(OfflineCity);
        //城市  城市中的课程

        LOGGER.info(ocl.getRecords().size() + "");
        List<Map<String, Object>> mapCourseList = mobileBannerService.realCourseList(ocl.getRecords(), PagingFixedType.PC_REAL_PAGETYPE_UP.getValue(),
                PagingFixedType.PC_REAL_PAGETYPE_DOWN.getValue(), IOSVersionInterceptor.onlyThread.get());
        mapAll.put("allCourseList", mapCourseList);
        return ResponseObject.newSuccessResponseObject(mapAll);
    }
}
