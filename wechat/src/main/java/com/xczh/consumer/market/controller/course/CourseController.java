package com.xczh.consumer.market.controller.course;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.interceptor.HeaderInterceptor;
import com.xczh.consumer.market.utils.APPUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.CourseUtil;
import com.xczhihui.common.util.XzStringUtils;
import com.xczhihui.common.util.enums.ClientType;
import com.xczhihui.common.util.enums.UserUnitedStateType;
import com.xczhihui.common.util.enums.WatchStateType;
import com.xczhihui.common.util.enums.WechatShareLinkType;
import com.xczhihui.common.util.vhallyun.BaseService;
import com.xczhihui.common.util.vhallyun.VhallUtil;
import com.xczhihui.course.consts.MultiUrlHelper;
import com.xczhihui.course.service.*;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.medical.anchor.service.ICourseApplyService;

/**
 * 点播控制器 ClassName: BunchPlanController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>
 * email: 15936216273@163.com <br>
 * Create Time: 2017年8月11日<br>
 */
@RestController
@RequestMapping("/xczh/course")
public class CourseController {
	
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CourseController.class);
    
    @Autowired
    public IWatchHistoryService watchHistoryServiceImpl;
    @Autowired
    private ICourseService courseServiceImpl;
    @Autowired
    private IMobileBannerService mobileBannerService;

    @Value("${gift.im.room.postfix}")
    private String postfix;

    @Value("${returnOpenidUri}")
    private String returnOpenidUri;

    @Autowired
    private ICourseApplyService courseApplyService;

    @Autowired
    private ICriticizeService criticizeService;
    @Autowired
    private ICourseSolrService courseSolrService;

    /**
     * Description：用户当前课程状态   User current course status.
     * 用户判断用户是否购买了这个课程
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    @RequestMapping("userCurrentCourseStatus")
    public ResponseObject userCurrentCourseStatus(@Account(optional = true) Optional<String> accountIdOpt, @RequestParam("courseId") Integer courseId) {
        // 这里需要判断是否购买过了
        CourseLecturVo cv = null;
        if (accountIdOpt.isPresent()) {
            String accountId = accountIdOpt.get();
            cv = courseServiceImpl.selectUserCurrentCourseStatus(courseId, accountId);
            /*
             * 如果是免费的  判断是否学习过
			 */
            if (cv != null && cv.getWatchState() == 1) { // 免费课程
                Integer falg = criticizeService.hasCourse(accountId, courseId);
                if (falg > 0) { // 如果购买过返回true 如果没有购买返回false
                    cv.setLearning(1);
                }
            }

        } else {
            cv = courseServiceImpl.selectCurrentCourseStatus(courseId);
        }
        return ResponseObject.newSuccessResponseObject(cv);
    }


    /**
     * Description：课程详情（展示页面）页面
     *
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    @RequestMapping("details")
    public ResponseObject details(@Account(optional = true) Optional<String> accountIdOpt, @RequestParam("courseId") Integer courseId) {

        CourseLecturVo cv = courseServiceImpl.selectCourseMiddleDetailsById(accountIdOpt.isPresent() ? accountIdOpt.get() : null, courseId);
        if (cv == null) {
            return ResponseObject.newErrorResponseObject("课程信息有误");
        }
        //赋值公共参数
        cv = assignCommonData(cv, courseId);

        return ResponseObject.newSuccessResponseObject(cv);
    }

    /**
     * Description：课程详情（播放页面）页面
     *
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    @RequestMapping("liveDetails")
    public ResponseObject liveDetails(@Account String accountId, @RequestParam("courseId") Integer courseId,
                                      HttpServletRequest request) throws Exception {

    	
    	
        CourseLecturVo cv = courseServiceImpl.selectCourseDetailsById(accountId, courseId);
        
        if (cv == null) {
            return ResponseObject.newErrorResponseObject("获取课程有误");
        }

        if (WatchStateType.PAY.getCode() == cv.getWatchState() && MultiUrlHelper.URL_TYPE_MOBILE.equals(APPUtil.getMobileSource(request))) {
            String page = this.coursePage(cv);
            return ResponseObject.newErrorResponseObject(page, UserUnitedStateType.NO_PAY.getCode());
        }

        //赋值公共参数
        cv = assignCommonData(cv, courseId);
        
        if (cv.getChannelId() != null && cv.getDirectId() != null && cv.getInavId()!=null) {
        	
        	cv.setVhallYunToken(BaseService.createAccessToken4InteractionLive(accountId, cv.getDirectId(),
        			cv.getChannelId(),cv.getInavId()));
        	
            cv.setAppid(VhallUtil.APP_ID);
            
        }else if(cv.getChannelId() != null && cv.getDirectId() != null) {
        	
        	cv.setVhallYunToken(BaseService.createAccessToken4Live(accountId, cv.getDirectId(), cv.getChannelId()));
            cv.setAppid(VhallUtil.APP_ID);
        }
        return ResponseObject.newSuccessResponseObject(cv);
    }

    @RequestMapping(value = "openLiveStatus", method = RequestMethod.GET)
    public ResponseObject openLiveStatus(@RequestParam Integer courseId) throws Exception {
        return ResponseObject.newSuccessResponseObject(courseServiceImpl.getCourseLivePushStreamStatus(courseId));
    }

    /**
     * Description：通过合辑id获取合辑中的课程 creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>
     * email: yuruixin@ixincheng.com
     * @Date: 上午 10:29 2018/1/22 0022
     **/
    @RequestMapping("getCoursesByCollectionId")
    public ResponseObject getCoursesByCollectionId(@RequestParam(value = "collectionId") Integer collectionId) {
        return ResponseObject.newSuccessResponseObject(courseServiceImpl.selectCoursesByCollectionId(collectionId));
    }

    /**
     * Description：猜你喜欢
     *
     * @param courseId
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    @RequestMapping("guessYouLike")
    public ResponseObject selectMenuTypeAndRandCourse(@RequestParam(value = "courseId") Integer courseId) {
        Page<CourseLecturVo> page = new Page<CourseLecturVo>();
        page.setCurrent(0);
        page.setSize(2);
        Page<CourseLecturVo> courses = courseServiceImpl
                .selectMenuTypeAndRandCourse(page, courseId);
        return ResponseObject.newSuccessResponseObject(courses);
    }


    /**
     * Description：下架课程推荐
     *
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    @RequestMapping("unshelveCouserRecommen")
    public ResponseObject unshelveCouserRecommen(@RequestParam(required = false, value = "pageSize") Integer pageSize) {

        if (pageSize == null || pageSize == 0) {
            pageSize = 8;
        }
        List<CourseLecturVo> courses = mobileBannerService.selectUnshelveRecommenCourse(pageSize);
        return ResponseObject.newSuccessResponseObject(courses);
    }

    /**	
     * Description：根据直播状态跳转不同页面
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin
     * @Date: 2018/6/5 0005 下午 8:23
     **/
    @RequestMapping("live/{courseId}")
    public void liveCourse(@PathVariable("courseId") String courseId, @Account String accountId,
                           HttpServletResponse response) throws IOException {

        String liveCourseUrl4Wechat = courseServiceImpl.getLiveCourseUrl4Wechat(accountId, courseId);
        //添加学习记录
        if (liveCourseUrl4Wechat != null && liveCourseUrl4Wechat.indexOf("details.html") != -1) {
            watchHistoryServiceImpl.addLookHistory(Integer.parseInt(courseId), accountId, 2, null);
        }
        response.sendRedirect(liveCourseUrl4Wechat);
    }

    /**
     * 用户课程类型数量
     *
     * @param userId
     * @param type
     * @return
     * @throws Exception
     */
    @RequestMapping("courseTypeNumber")
    public ResponseObject courseTypeNumber(@RequestParam(value = "userId") String userId, Integer type) {
        Integer count = courseServiceImpl.selectLiveCountByUserIdAndType(userId, type);
        return ResponseObject.newSuccessResponseObject(count);
    }

    /**
     * 随机为你推荐推荐值最高的10个课程里面最高的四个
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("recommendSortAndRand")
    public ResponseObject recommendSortAndRand(Integer pageSize) {

        pageSize = (pageSize == null ? 4 : pageSize);

        //推荐课程   -- 从推荐值最高的课程里面查询啦啦啦啦。
        Page<CourseLecturVo> page = new Page<CourseLecturVo>();
        page.setCurrent(0);
        page.setSize(pageSize);

        return ResponseObject.newSuccessResponseObject(courseServiceImpl.selectCourseByLearndCount(page, 1));
    }

    @RequestMapping("teaching")
    public ResponseObject teaching(@Account(optional = true) Optional<String> accountIdOpt, Integer pageSize, Integer pageNumber, @RequestParam("userId") String lecturerId) {

        pageSize = (pageSize == null ? 4 : pageSize);
        pageNumber = (pageNumber == null ? 1 : pageNumber);

        Page<CourseLecturVo> page = new Page<CourseLecturVo>();
        page.setCurrent(pageNumber);
        page.setSize(pageSize);
        String userId = accountIdOpt.isPresent() ? accountIdOpt.get() : null;
        return ResponseObject.newSuccessResponseObject(courseServiceImpl.selectTeachingCoursesByUserId(page, lecturerId, userId));
    }

    @RequestMapping(value = "updateLiveStatus", method = RequestMethod.POST)
    public ResponseObject updateLiveStatus(HttpServletRequest request, String event, String directId) throws IOException, SolrServerException {

        courseServiceImpl.updateCourseLiveStatus(event, directId, APPUtil.getMobileSource(request));

        return ResponseObject.newSuccessResponseObject(null);
    }

    /**
     * 查看当前用户是否关注了主播以及是否购买了这个课程
     *
     * @param cv
     * @param courseId
     * @return
     */
    private CourseLecturVo assignCommonData(CourseLecturVo cv, Integer courseId) {

        //设置星星级别
        cv.setStartLevel(CourseUtil.criticizeStartLevel(cv.getStartLevel()));
        
        cv.setRichCourseDetailsUrl(returnOpenidUri + "/xcview/html/person_fragment.html?type=1&typeId=" + courseId);
        cv.setRichHostDetailsUrl(returnOpenidUri + "/xcview/html/person_fragment.html?type=3&typeId=" + courseId);
        cv.setHostCourseDetailsUrl(returnOpenidUri + "/xcview/html/person_fragment2.html?typeId=" + courseId);

        //专辑查看更新时间
        if (cv.getCollection()) {
            //已更新多少集，等于总集数
            if (cv.getCourseNumber() != null && cv.getDirtyNumber() != null && cv.getCourseNumber().equals(cv.getDirtyNumber())) {
                cv.setDirtyDate(XzStringUtils.COLLECTION_UPDATE_FINISH);
            } else {
                cv.setDirtyDate(courseApplyService.getCollectionUpdateDateText(courseId));
            }

            cv.setOutlineDetailsUrl(returnOpenidUri + "/xcview/html/outline_fragment.html?courseId=" + courseId);
        }
        return cv;
    }

    /**
     * @param
     * @param 参数
     * @return String    返回类型
     * @throws
     * @Title: coursePage
     * @Description: 课程跳转
     * @author yangxuan
     */
    public String coursePage(CourseLecturVo cv) {

        String coursePage = WechatShareLinkType.INDEX_PAGE.getLink();

        if (cv.getWatchState().equals(0) || cv.getWatchState().equals(1)) {

            if (cv.getType().equals(1) || cv.getType().equals(2)) {
                //视频音频购买
                coursePage = WechatShareLinkType.SCHOOL_AUDIO.getLink();
            } else if (cv.getType().equals(3)) {
                //直播购买
                coursePage = WechatShareLinkType.SCHOOL_PLAY.getLink();
            } else {
                //线下课购买
                coursePage = WechatShareLinkType.SCHOOL_CLASS.getLink();
            }
        } else if (cv.getWatchState().equals(2)) {

            if (cv.getType().equals(1) || cv.getType().equals(2)) {
                if (cv.getCollection()) {
                    //专辑视频音频播放页
                    coursePage = WechatShareLinkType.LIVE_SELECT_ALBUM.getLink();
                } else {
                    coursePage = WechatShareLinkType.LIVE_AUDIO.getLink();
                }
            } else if (cv.getType().equals(3)) {
                //播放页面
                coursePage = WechatShareLinkType.LIVE_PLAY.getLink();
            } else {
                //线下课页面
                coursePage = WechatShareLinkType.LIVE_CLASS.getLink();
            }
        }
        return returnOpenidUri + coursePage + cv.getId();
    }


}
