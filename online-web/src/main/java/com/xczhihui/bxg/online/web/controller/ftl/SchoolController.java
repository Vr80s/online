package com.xczhihui.bxg.online.web.controller.ftl;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.body.course.LineApplyBody;
import com.xczhihui.bxg.online.web.controller.ftl.AbstractFtlController;
import com.xczhihui.bxg.online.web.utils.ftl.ReplaceUrl;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.util.enums.*;
import com.xczhihui.course.model.OfflineCity;
import com.xczhihui.course.service.*;
import com.xczhihui.course.util.CourseUtil;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.course.vo.MenuVo;
import com.xczhihui.course.vo.QueryConditionVo;
import com.xczhihui.medical.anchor.service.ICourseApplyService;

@Controller
@RequestMapping(value = "/courses")
public class SchoolController extends AbstractFtlController {

    @Autowired
    private IMobileBannerService mobileBannerService;

    @Autowired
    private IMyInfoService myInfoService;

    @Autowired
    private IMobileProjectService mobileProjectService;

    @Autowired
    private IMobileHotSearchService mobileHotSearchService;

    @Autowired
    private IOfflineCityService offlineCityService;

    @Autowired
    private ICourseService courseService;

    @Autowired
    private ICriticizeService criticizeService;

    @Autowired
    private ICourseApplyService courseApplyService;

    @Autowired
    private ILineApplyService lineApplyService;

    @Value("${web.url}")
    private String webUrl;


    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 推荐页面
     *
     * @return
     */
    @RequestMapping(value = "recommendation", method = RequestMethod.GET)
    public ModelAndView recommendation() {
        ModelAndView view = new ModelAndView("school/list/school_index");


        //控制banner图跳转方法
        view.addObject("replaceUrl", new ReplaceUrl());

        //截取等号后边的
        /**
         * banner图
         */
        view.addObject("bannerList", mobileBannerService.selectMobileBannerPage(BannerType.RECOMMENDATION.getCode()));
        /**
         * 默认搜索、热门搜索
         */
        view.addObject("defaultSearch", mobileHotSearchService.HotSearchList(SearchType.DEFAULT_SEARCH.getCode()));
        view.addObject("hotList", mobileHotSearchService.HotSearchList(SearchType.HOT_SEARCH.getCode()));
        /**
         * 名医推荐
         */
        view.addObject("doctorList", myInfoService.hostInfoRec());
        /**
         * 课程专题啦
         */
        // 课程专题
        view.addObject("projectList", mobileProjectService.selectMobileProjectPage(ProjectType.PROJECT.getCode()));
        /**
         * 课程分类
         */
        List<MenuVo> listMenu = mobileProjectService.selectMenuVo();
        view.addObject("cateGoryList", listMenu);

        /**
         * 课程列表
         */
        view.addObject("courseTypeList",
                mobileBannerService.recommendCourseList(listMenu,
                        PagingFixedType.PC_RECOMMENDATION.getValue()));
        return view;
    }

    /**
     * 线下培训班
     *
     * @return
     */
    @RequestMapping(value = "real", method = RequestMethod.GET)
    public ModelAndView real() {
        ModelAndView view = new ModelAndView("school/list/school_real");

        //控制banner图跳转方法
        view.addObject("replaceUrl", new ReplaceUrl());

        // 线下课banner
        view.addObject("bannerList", mobileBannerService.selectMobileBannerPage(BannerType.REAL.getCode()));
        // 线下培训班课程
        Page<OfflineCity> OfflineCity = new Page<>();
        OfflineCity.setCurrent(1);
        OfflineCity.setSize(4);
        Page<OfflineCity> ocl = offlineCityService.selectOfflineRecommendedCityPage(OfflineCity);

        view.addObject("courseTypeList", mobileBannerService.realCourseList(ocl.getRecords(),
                PagingFixedType.REAL_PAGETYPE_UP.getValue(), PagingFixedType.REAL_PAGETYPE_DOWN.getValue()));
        // 名医推荐
        view.addObject("doctorList", myInfoService.hostInfoRec());
        return view;
    }

    /**
     * 直播课
     *
     * @return
     */
    @RequestMapping(value = "live", method = RequestMethod.GET)
    public ModelAndView live() {
        ModelAndView view = new ModelAndView("school/list/school_live");

        //控制banner图跳转方法
        view.addObject("replaceUrl", new ReplaceUrl());

        // 直播课banner
        view.addObject("bannerList", mobileBannerService.selectMobileBannerPage(BannerType.LIVE.getCode()));

        // 直播课程
        view.addObject("courseTypeList",
                mobileBannerService.liveCourseList(PagingFixedType.PC_LIVE_PAGETYPE.getValue()));

        // 名医推荐
        view.addObject("doctorList", myInfoService.hostInfoRec());
        return view;
    }

    /**
     * 听课页面
     *
     * @return
     */
    @RequestMapping(value = "listen", method = RequestMethod.GET)
    public ModelAndView listen() {
        ModelAndView view = new ModelAndView("school/list/school_video");

        //控制banner图跳转方法
        view.addObject("replaceUrl", new ReplaceUrl());


        // 听课banner
        view.addObject("bannerList", mobileBannerService.selectMobileBannerPage(BannerType.LISTEN.getCode()));
        // 听课
        view.addObject("courseList", courseService.listenCourseList());
        // 名医推荐
        view.addObject("doctorList", myInfoService.hostInfoRec());
        return view;
    }

    /**
     * 检索列表页面
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView list(HttpServletRequest req,
                             @RequestParam(value = "page", required = false)
                                     Integer current, Integer size,
                             QueryConditionVo queryConditionVo) {

        ModelAndView view = new ModelAndView("school/list/school_list");

        current = current == null ? 1 : current;
        size = size == null ? 12 : size;

        if (queryConditionVo.getMenuType() != null && "0".equals(queryConditionVo.getMenuType())) {
            queryConditionVo.setMenuType(null);
        }
        if (queryConditionVo.getLineState() != null && queryConditionVo.getLineState() == 0) {
            queryConditionVo.setLineState(null);
        }

        // 课程列表
        if (StringUtils.isNotBlank(queryConditionVo.getQueryKey())) {
            queryConditionVo.setQueryKey("%" + queryConditionVo.getQueryKey() + "%");
            view.addObject("courseList", mobileBannerService.searchQueryKeyCourseList(new Page<CourseLecturVo>(current, size), queryConditionVo));
        } else {
            view.addObject("courseList", mobileBannerService.searchCourseList(new Page<CourseLecturVo>(current, size), queryConditionVo));
        }

        //Map<String,String> returnMap = new HashMap<String,String>();
//        StringBuffer sb = new StringBuffer(webUrl + "/courses/list");
        StringBuffer sb = new StringBuffer("/courses/list");
        Enumeration em = req.getParameterNames();
        if (em.hasMoreElements()) {
            sb.append("?");
        }
        while (em.hasMoreElements()) {
            String name = (String) em.nextElement();
            String value = req.getParameter(name);
            if (!"page".equals(name)) {
                sb.append(name).append("=").append(value).append("&");
            }
        }

        log.info("sb.toString()" + sb.substring(0, sb.length() - 1));
        if (sb.indexOf("?") != -1) {
            view.addObject("webUrlParam", sb.substring(0, sb.length() - 1));
        } else {
            view.addObject("webUrlParam", sb.toString());
        }
        //new ReplaceUrl()   替换url的方法
        view.addObject("replaceUrl", new ReplaceUrl());
        /**
         * 判断如果是ajax请求的话，那么就不请求下面的分类列表了。
         * 	如果是页面跳转过来的话需要请求下呢。
         * 目前是先全部请求吧
         */
        // 课程分类
        view.addObject("courseMenuList", mobileProjectService.selectMenuVo());
        // 是否付费
        view.addObject("freeTypeEnum", PayStatus.getPayStatusList());

        // 课程类型
        view.addObject("courseTypeEnum", CourseType.getCourseType());
        // 直播状态
        view.addObject("liveStatusEnum", LiveStatus.getLiveStatusList());

        // 城市
        Page<OfflineCity> OfflineCityPage = new Page<>();
        OfflineCityPage.setCurrent(1);
        OfflineCityPage.setSize(5);
        List<OfflineCity> oclist = offlineCityService.selectOfflineCityPage(OfflineCityPage).getRecords();
        if (oclist.size() == 5) {
            OfflineCity oc = new OfflineCity();
            oc.setCityName("其他");
            oclist.add(oc);
        }
        for (OfflineCity city : oclist) {
            String name = city.getCityName();
            city.setName(name);
        }
        view.addObject("cityList", oclist);
        return view;
    }

    /**
     * 课程详情页面中的 推荐课程
     *
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "{courseId}/{type}", method = RequestMethod.GET)
    public ModelAndView info(HttpServletRequest req,
                             @PathVariable Integer courseId,
                             @PathVariable String type,
                             @RequestParam(required = false) String userId,
                             @RequestParam(required = false) Integer pageSize,
                             @RequestParam(required = false) Integer pageNumber) throws IOException {

        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 5 : pageSize;
        
        ModelAndView view = new ModelAndView("school/course_details");
        //显示详情呢、大纲、评论、常见问题呢
        //outline  comment    info   aq  selection
        if(isNotCoursePage(type)){
            return to404();
        }
        view.addObject("type", type);

        view.addObject("webUrlParam", "/courses/" + courseId);
        //获取用户信息
        OnlineUser user = getCurrentUser();
        CourseLecturVo clv = courseService.selectCourseMiddleDetailsById(courseId);
        if(clv == null){
            return to404();
        }
        //计算星级
        clv.setStartLevel(CourseUtil.criticizeStartLevel(clv.getStartLevel()));
        String strLevel = CourseUtil.criticizeStartLevel(clv.getStartLevel()) + "";
        view.addObject("startLevel", strLevel.replace(".", "_"));
        if (user != null) {
            /*
             * 收费课程判断有没有购买过
             * 免费课程判断有没有学习过
             */
            Integer falg = criticizeService.hasCourse(user.getId(), courseId);
            // 付费课程
            if (clv.getWatchState() == 0) {
                if (falg > 0) {
                    clv.setWatchState(2);
                }
                //如果是免费的  判断是否学习过
            } else if (clv.getWatchState() == 1) {
                if (falg > 0) {
                    clv.setLearning(1);
                }
            }
        }

        //获取专辑
        List<CourseLecturVo> courses = courseService.selectCoursesByCollectionId(clv.getId());
        view.addObject("collectionList", courses);
        view.addObject("collectionListSize", courses.size());
        view.addObject("updateDateText", courseApplyService.getCollectionUpdateDateText(clv.getId()));

        //课程详情
        view.addObject("courseInfo", clv);

        //常见问题。
        String path = req.getServletContext().getRealPath("/template");
        File f = new File(path + File.separator + "/course_common_problem.html");
        view.addObject("commonProblem", FileUtil.readAsString(f));

        //课程评价
        if(type!=null && type.equals("comment")) {
            Map<String, Object> map = null;
            if (courseId != null) {
                map = criticizeService.getCourseCriticizes(new Page<>(pageNumber, pageSize), courseId, user != null ? user.getId() : null);
            } else {
                map = criticizeService.getAnchorCriticizes(new Page<>(pageNumber, pageSize), userId, user != null ? user.getId() : null);
            }
            view.addObject("criticizesMap", map);
            
            //查询各种平均值
            List<Integer> listComment = criticizeService.selectPcCourseCommentMeanCount(clv.getCollection(), courseId);
            view.addObject("listCommentCount", listComment);
        }

        //推荐课程   -- 从推荐值最高的课程里面查询啦啦啦啦。
        Page<CourseLecturVo> page = new Page<CourseLecturVo>();
        page.setCurrent(0);
        page.setSize(2);
        view.addObject("recommendCourse", courseService.selectRecommendSortAndRandCourse(page));
        return view;
    }

    private boolean isNotCoursePage(String type) {
        //outline  comment    info   aq
        final List typeList = Arrays.asList("outline","comment","info","aq");
        return !typeList.contains(type);
    }

    /**
     * 增加线下课报名记录
     *
     * @param lineApplyBody 线下课报名信息
     * @return
     */
    @RequestMapping(value = "applyInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject add(LineApplyBody lineApplyBody) {
        String userId = getUserId();
        lineApplyService.saveOrUpdate(lineApplyBody.build(userId));
        CourseLecturVo clv = courseService.selectCourseMiddleDetailsById(lineApplyBody.getCourseId());
        return ResponseObject.newSuccessResponseObject(clv.getWatchState());
    }

    /**
     * 通过用户id获取
     *
     * @return
     */
    @RequestMapping(value = "applyInfo", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject applyInfo(@RequestParam Integer courseId) {
        Map<String, Object> result = new HashMap<>(2);
        String userId = getUserId();
        result.put("submitted", lineApplyService.submitted(userId, courseId));
        return ResponseObject.newSuccessResponseObject(result);
    }

    @RequestMapping(value = "offlineApply", method = RequestMethod.GET)
    public ModelAndView offlineApply(@RequestParam Integer courseId) {
        ModelAndView modelAndView = new ModelAndView("/school/offline_apply");
        Map<String, Object> lineApply = lineApplyService.findLineApplyByUserId(getUserId(), courseId);
        modelAndView.addObject("mobile", MapUtils.getString(lineApply, "mobile", ""));
        modelAndView.addObject("wechatNo", MapUtils.getString(lineApply, "wechatNo", ""));
        modelAndView.addObject("realName", MapUtils.getString(lineApply, "realName", ""));
        modelAndView.addObject("sex", MapUtils.getString(lineApply, "sex", ""));
        modelAndView.addObject("courseId", courseId);
        return modelAndView;
    }
}
