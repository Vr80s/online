package com.xczh.consumer.market.controller.school;

import java.util.ArrayList;
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
import com.xczh.consumer.market.service.MenuService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.enums.PagingFixedType;
import com.xczhihui.common.util.enums.ProjectType;
import com.xczhihui.course.model.MobileProject;
import com.xczhihui.course.model.OfflineCity;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.service.IMobileProjectService;
import com.xczhihui.course.service.IOfflineCityService;

/**
 * 分类控制器 ClassName: MobileRecommendController.java <br>
 * Description: <br>
 * Create by: name：wangyishuai <br>
 * email: 15210815880@163.com <br>
 * Create Time: 2018年1月16日<br>
 */
@Controller
@RequestMapping("/xczh/classify")
public class MobileClassifyController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MobileClassifyController.class);
    @Autowired
    private MenuService menuService;
    @Autowired
    private IMobileProjectService mobileProjectService;
    @Autowired
    private IOfflineCityService offlineCityService;
    @Autowired
    private ICourseService courseService;

    /**
     * 分类
     */
    @RequestMapping("schoolClass")
    @ResponseBody
    public ResponseObject schoolClass()
            throws Exception {

        List<Object> list = new ArrayList<Object>();
        //课程分类
        list.add(menuService.list());
        //课程专题
//		Page<MobileProject> MobileProjectPage = new Page<>();
//		MobileProjectPage.setCurrent(1);
//		MobileProjectPage.setSize(100);
        List<MobileProject> mplist = mobileProjectService.selectMobileProjectPage(ProjectType.PROJECT_CATEGORY.getCode());
        list.add(mplist);
        //课程类型
        List<Map<String, Object>> getCourseTypeList = mobileProjectService.getCourseType();
        list.add(getCourseTypeList);
        return ResponseObject.newSuccessResponseObject(list);
    }

    /**
     * 列表筛选
     */
    @RequestMapping("listScreen")
    @ResponseBody
    public ResponseObject listScreen() throws Exception {

        List<Object> list = new ArrayList<Object>();
        //课程分类
        list.add(menuService.list());
        //是否付费
        List<Map<String, Object>> getPayStatusList = courseService.getPayStatusList();
        list.add(getPayStatusList);
        //课程类型
        List<Map<String, Object>> getCourseTypeList = mobileProjectService.getCourseType();
        list.add(getCourseTypeList);

        //城市
        Page<OfflineCity> OfflineCityPage = new Page<>();
        OfflineCityPage.setCurrent(1);
        OfflineCityPage.setSize(PagingFixedType.OFFLINE_CITY_COURSE_RECOMMEND.getValue());
        List<OfflineCity> oclist = offlineCityService.selectOfflineCityPage(OfflineCityPage).getRecords();
        if (oclist != null && oclist.size() > 0) {
            OfflineCity oc = new OfflineCity();
            oc.setCityName("其他");
            oclist.add(oc);
        }
        for (OfflineCity city : oclist) {
            String name = city.getCityName();
            city.setName(name);
        }
        list.add(oclist);

        //直播状态
        List<Map<String, Object>> getLiveStatusList = courseService.getLiveStatusList();
        list.add(getLiveStatusList);

        return ResponseObject.newSuccessResponseObject(list);
    }


}
