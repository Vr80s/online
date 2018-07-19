package com.xczhihui.bxg.online.web.controller.ftl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.aspectj.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.body.course.LineApplyBody;
import com.xczhihui.bxg.online.web.utils.HtmlUtil;
import com.xczhihui.bxg.online.web.utils.ftl.ReplaceUrl;
import com.xczhihui.common.util.CourseUtil;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.util.enums.BannerType;
import com.xczhihui.common.util.enums.CourseForm;
import com.xczhihui.common.util.enums.CourseType;
import com.xczhihui.common.util.enums.LiveStatus;
import com.xczhihui.common.util.enums.Multimedia;
import com.xczhihui.common.util.enums.PagingFixedType;
import com.xczhihui.common.util.enums.PayStatus;
import com.xczhihui.common.util.enums.ProjectType;
import com.xczhihui.common.util.enums.SearchType;
import com.xczhihui.course.consts.MultiUrlHelper;
import com.xczhihui.course.model.OfflineCity;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.service.ICourseSolrService;
import com.xczhihui.course.service.ICriticizeService;
import com.xczhihui.course.service.ILineApplyService;
import com.xczhihui.course.service.IMobileBannerService;
import com.xczhihui.course.service.IMobileHotSearchService;
import com.xczhihui.course.service.IMobileProjectService;
import com.xczhihui.course.service.IMyInfoService;
import com.xczhihui.course.service.IOfflineCityService;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.course.vo.CourseSolrVO;
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

    @Autowired
    private ICourseSolrService courseSolrService;

    @Value("${web.url}")
    private String webUrl;


    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 推荐页面
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
        view.addObject("bannerList", mobileBannerService.selectMobileBannerPage(BannerType.RECOMMENDATION.getCode(), MultiUrlHelper.URL_TYPE_WEB));
        /**
         * 热门搜索
         */
        view.addObject("hotList", mobileHotSearchService.HotSearchList(SearchType.SCHOOL_HOT_SEARCH.getCode()));
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
        view.addObject("bannerList", mobileBannerService.selectMobileBannerPage(BannerType.REAL.getCode(), MultiUrlHelper.URL_TYPE_WEB));
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
        view.addObject("bannerList", mobileBannerService.selectMobileBannerPage(BannerType.LIVE.getCode(), MultiUrlHelper.URL_TYPE_WEB));

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
        view.addObject("bannerList", mobileBannerService.selectMobileBannerPage(BannerType.LISTEN.getCode(), MultiUrlHelper.URL_TYPE_WEB));
        // 听课
        view.addObject("courseList", mobileBannerService.listenCourseList(false));
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
    public ModelAndView list(HttpServletRequest req, @RequestParam(value = "page", required = false) Integer page, Integer size, QueryConditionVo queryConditionVo) throws IOException, SolrServerException {

        ModelAndView view = new ModelAndView("school/list/school_list");

        page = page == null ? 1 : page;
        size = size == null ? 12 : size;
        handleQueryConditionVo(queryConditionVo);

        Page<CourseSolrVO> pageList = new Page<>(page, size);
        pageList = courseSolrService.selectCourseListBySolr(pageList, queryConditionVo);
        view.addObject("courseList", pageList);

        //拼接url参数
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
        if (oclist != null && oclist.size() > 0) {
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
                             @RequestParam(required = false) Integer pageSize,
                             @RequestParam(required = false) Integer pageNumber) throws IOException {

        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 5 : pageSize;

        ModelAndView view = new ModelAndView("school/course_details");
        //显示详情呢、大纲、评论、常见问题呢
        //outline  comment    info   aq  selection
        if (isNotCoursePage(type)) {
            return to404();
        }
        view.addObject("type", type);

        view.addObject("webUrlParam", "/courses/" + courseId);
        //获取用户信息
        OnlineUser user = getCurrentUser();
        String userId = user==null?"":user.getId();
        CourseLecturVo clv = courseService.selectCourseMiddleDetailsById(userId,courseId);
        if (clv == null) {
            return to404();
        }
        //计算星级
        clv.setStartLevel(CourseUtil.criticizeStartLevel(clv.getStartLevel()));
        String strLevel = CourseUtil.criticizeStartLevel(clv.getStartLevel()) + "";
        view.addObject("startLevel", strLevel.replace(".", "_"));

        String description = "";
        if (clv.getDescription() != null) {
            description = HtmlUtil.getTextFromHtml(clv.getDescription());
            description = description.length() < 100 ? description : description.substring(0, 99);
        }
        
        
        //课程详情
        view.addObject("courseInfo", clv);
        view.addObject("description", description);
        
      //获取相关信息
        if(type.equals("selection") || (type.equals("info") && clv.getCollection())) {
            
            List<CourseLecturVo> courses = courseService.selectCoursesByCollectionId(clv.getId());
            view.addObject("collectionList", courses);
            view.addObject("collectionListSize", courses.size());
            view.addObject("updateDateText", courseApplyService.getCollectionUpdateDateText(clv.getId()));
        }
        
        
        if(type.equals("aq")) {
            //常见问题。
            String path = req.getServletContext().getRealPath("/template");
            File f = new File(path + File.separator + "/course_common_problem.html");
            view.addObject("commonProblem", FileUtil.readAsString(f));        
        }
        
        //课程评价
        if (type.equals("comment")) {
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
        final List typeList = Arrays.asList("outline", "comment","selection","info", "aq","albumInfo");
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
        CourseLecturVo clv = courseService.selectCourseMiddleDetailsById(userId,lineApplyBody.getCourseId());
       
        
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

    private void handleQueryConditionVo(QueryConditionVo queryConditionVo) {
        if (queryConditionVo.getMenuType() != null && "0".equals(queryConditionVo.getMenuType())) {
            queryConditionVo.setMenuType(null);
        }
        if (queryConditionVo.getLineState() != null && queryConditionVo.getLineState() == 0) {
            queryConditionVo.setLineState(null);
        }
        if (queryConditionVo.getCourseType() != null) {
            if (queryConditionVo.getCourseType().equals(CourseType.VIDEO.getId())) {
                queryConditionVo.setCourseForm(CourseForm.VOD.getCode());
                queryConditionVo.setMultimediaType(Multimedia.VIDEO.getCode());
            } else if (queryConditionVo.getCourseType().equals(CourseType.AUDIO.getId())) {
                queryConditionVo.setCourseForm(CourseForm.VOD.getCode());
                queryConditionVo.setMultimediaType(Multimedia.AUDIO.getCode());
            } else if (queryConditionVo.getCourseType().equals(CourseType.LIVE.getId())) {
                queryConditionVo.setCourseForm(CourseForm.LIVE.getCode());
            } else if (queryConditionVo.getCourseType().equals(CourseType.OFFLINE.getId())) {
                queryConditionVo.setCourseForm(CourseForm.OFFLINE.getCode());
            }
        }
    }
}
