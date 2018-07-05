package com.xczh.consumer.market.controller.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xczhihui.common.util.enums.CourseType;
import com.xczhihui.common.util.enums.RouteTypeEnum;
import com.xczhihui.course.consts.MultiUrlHelper;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.pay.util.StringUtils;

@Controller
@RequestMapping("/xczh/page/course")
public class CoursePageController {

    @Autowired
    private ICourseService courseServiceImpl;
    @Value("${returnOpenidUri}")
    private String returnOpenidUri;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String jump(@PathVariable Integer id) {
        CourseLecturVo courseLecturVo = courseServiceImpl.selectCourseMiddleDetailsById(id);
        String url = "";
        String detailId = String.valueOf(id);
        String routeType = null;
        if (courseLecturVo != null) {
            Integer type = courseLecturVo.getType();
            if (courseLecturVo.getCollection() != null && courseLecturVo.getCollection()) {
                routeType = RouteTypeEnum.COLLECTION_COURSE_DETAIL_PAGE.name();
            } else if (type == CourseType.VIDEO.getId() || type == CourseType.AUDIO.getId()) {
                routeType = RouteTypeEnum.VIDEO_AUDIO_COURSE_DETAIL_PAGE.name();
            } else if (type == CourseType.OFFLINE.getId()) {
                routeType = RouteTypeEnum.OFFLINE_COURSE_DETAIL_PAGE.name();
            } else if (type == CourseType.LIVE.getId()) {
                routeType = RouteTypeEnum.LIVE_COURSE_DETAIL_PAGE.name();
            }
        }
        if (routeType != null) {
            url = MultiUrlHelper.getUrl(routeType, MultiUrlHelper.URL_TYPE_MOBILE, detailId);
        }
        if (StringUtils.isBlank(url)) {
            url = "/xcview/html/home_page.html";
        }
        return "redirect:" + url;
    }
}
