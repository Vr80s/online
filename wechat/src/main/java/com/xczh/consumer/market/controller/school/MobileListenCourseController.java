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
import com.xczh.consumer.market.utils.APPUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.enums.BannerType;
import com.xczhihui.course.model.MobileBanner;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.service.IMobileBannerService;
import com.xczhihui.course.vo.CourseLecturVo;

/**
 * 听课控制器 ClassName: MobileRecommendController.java <br>
 * Description: <br>
 * Create by: name：wangyishuai <br>
 * email: 15210815880@163.com <br>
 * Create Time: 2018年1月16日<br>
 */
@Controller
@RequestMapping("/xczh/bunch")
public class MobileListenCourseController {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MobileListenCourseController.class);

    @Autowired
    private IMobileBannerService mobileBannerService;

    @Autowired
    private ICourseService courseService;

    /**
     * 听课
     */
    @RequestMapping("listenCourse")
    @ResponseBody
    public ResponseObject onlineLive(HttpServletRequest req,
                                     HttpServletResponse res)
            throws Exception {

        Map<String, Object> mapAll = new HashMap<String, Object>();
        //听课banner
        Page<MobileBanner> mobileBannerPage = new Page<>();
        mobileBannerPage.setRecords(mobileBannerService.selectMobileBannerPage(BannerType.LISTEN.getCode(), IOSVersionInterceptor.onlyThread.get(), APPUtil.getMobileSource(req)));
        mapAll.put("banner", mobileBannerPage);

        //听课课程列表
        List<CourseLecturVo> listenCourseList = mobileBannerService.listenCourseList(IOSVersionInterceptor.onlyThread.get());
        mapAll.put("listenCourseList", listenCourseList);
        return ResponseObject.newSuccessResponseObject(mapAll);
    }
}
