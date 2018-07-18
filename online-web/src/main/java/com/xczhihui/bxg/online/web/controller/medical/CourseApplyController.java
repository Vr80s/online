package com.xczhihui.bxg.online.web.controller.medical;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.controller.AbstractController;
import com.xczhihui.bxg.online.web.service.CourseService;
import com.xczhihui.bxg.online.web.service.OnlineUserCenterService;
import com.xczhihui.common.util.TimeUtil;
import com.xczhihui.common.util.VhallUtil;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.util.enums.ClientType;
import com.xczhihui.common.util.enums.MessageTypeEnum;
import com.xczhihui.common.util.enums.RouteTypeEnum;
import com.xczhihui.course.params.BaseMessage;
import com.xczhihui.course.service.ICommonMessageService;
import com.xczhihui.course.service.ICourseSolrService;
import com.xczhihui.course.service.IFocusService;
import com.xczhihui.course.service.ILineApplyService;
import com.xczhihui.course.util.TextStyleUtil;
import com.xczhihui.course.vo.FocusVo;
import com.xczhihui.course.vo.LineCourseApplyStudentVO;
import com.xczhihui.medical.anchor.model.CourseApplyInfo;
import com.xczhihui.medical.anchor.model.CourseApplyResource;
import com.xczhihui.medical.anchor.service.ICourseApplyService;
import com.xczhihui.medical.anchor.vo.CourseApplyInfoVO;
import com.xczhihui.medical.anchor.vo.CourseApplyResourceVO;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsService;
import com.xczhihui.medical.enrol.service.EnrolService;


/**
 * 主播工作台课程控制层
 *
 * @author yuruixin
 */
@RestController
@RequestMapping(value = "/anchor/course")
public class CourseApplyController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(CourseApplyController.class);

    private static final String WEB_COURSE_ONLINE_MESSAGE_TIPS = "【课程上架通知】{0}您好，您关注的{1}老师有新课程-" + TextStyleUtil.LEFT_TAG + "《{2}》"
            + TextStyleUtil.RIGHT_TAG + "上架了，快去看看吧";
    private static final String APP_PUSH_COURSE_ONLINE_MESSAGE_TIPS = "您关注的主播有新课程上架~";

    @Autowired
    private ICourseApplyService courseApplyService;
    @Autowired
    private IFocusService focusService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ICommonMessageService commonMessageService;
    @Autowired
    private ILineApplyService lineApplyService;
    @Autowired
    private IMedicalDoctorPostsService medicalDoctorPostsService;
    @Value("${weixin.course.remind.code}")
    private String weixinTemplateMessageRemindCode;
    @Autowired
    private OnlineUserCenterService onlineUserCenterService;
    @Autowired
    private ICourseSolrService courseSolrService;
    @Autowired
    private EnrolService enrolService;
    @Autowired
    private IMedicalDoctorBusinessService medicalDoctorBusinessService;

    /**
     * Description：分页获取课程申请列表
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 3:42 2018/1/19 0019
     **/
    @RequestMapping(value = "/getCourseApplyList", method = RequestMethod.GET)
    public ResponseObject getCourseApplyList(Integer current, Integer size, Integer courseForm, Integer multimediaType, String title, int teaching) {
        Page<CourseApplyInfoVO> page = new Page<>();
        if (size == null) {
            size = Integer.MAX_VALUE;
            current = 1;
        }
        page.setCurrent(current);
        page.setSize(size);
        OnlineUser user = getCurrentUser();
        return ResponseObject.newSuccessResponseObject(courseApplyService.selectCourseApplyPage(page, user.getId(), courseForm, multimediaType, title,teaching));
    }

    /**
     * Description：分页获取专辑列表
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 3:50 2018/1/19 0019
     **/
    @RequestMapping(value = "/getCollectionApplyList", method = RequestMethod.GET)
    public ResponseObject getCollectionApplyList(Integer current, Integer size, Integer multimediaType, String title) {
        Page<CourseApplyInfoVO> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        OnlineUser user = getCurrentUser();
        return ResponseObject.newSuccessResponseObject(courseApplyService.selectCollectionApplyPage(page, user.getId(), multimediaType, title));
    }

    /**
     * Description：分页获取直播列表
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 3:51 2018/1/19 0019
     **/
    @RequestMapping(value = "/getLiveApplyList", method = RequestMethod.GET)
    public ResponseObject getLiveApplyList(Integer current, Integer size, String title) {
        Page<CourseApplyInfoVO> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        OnlineUser user = getCurrentUser();
        return ResponseObject.newSuccessResponseObject(courseApplyService.selectLiveApplyPage(page, user.getId(), title));
    }

    /**
     * Description：获取所有资源列表
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 3:51 2018/1/19 0019
     **/
    @RequestMapping(value = "/getAllCourseResources", method = RequestMethod.GET)
    public ResponseObject getAllCourseResources(Integer multimediaType) {
        OnlineUser user = getCurrentUser();
        return ResponseObject.newSuccessResponseObject(courseApplyService.selectAllCourseResources(user.getId(), multimediaType));
    }

    @RequestMapping(value = "/getAllCourses", method = RequestMethod.GET)
    public ResponseObject getAllCourses(Integer multimediaType) {
        OnlineUser user = getCurrentUser();
        return ResponseObject.newSuccessResponseObject(courseApplyService.selectAllCourses(user.getId(), multimediaType));
    }
    
    
    @RequestMapping(value = "/getCollectionNotExitCouse", method = RequestMethod.GET)
    public ResponseObject getCollectionNotExitCouse(Integer multimediaType,Integer collectionId,Integer caiId) {
        OnlineUser user = getCurrentUser();
        return ResponseObject.newSuccessResponseObject(courseApplyService.getCollectionNotExitCouse(user.getId(),
                multimediaType,collectionId,caiId));
    }
    

    @RequestMapping(value = "/getCourseApplyById", method = RequestMethod.GET)
    public ResponseObject getCourseApplyById(Integer caiId) {
        OnlineUser user = getCurrentUser();
        return ResponseObject.newSuccessResponseObject(courseApplyService.selectCourseApplyById(user.getId(), caiId));
    }

    /**
     * Description：分页获取资源列表
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 3:51 2018/1/19 0019
     **/
    @RequestMapping(value = "/getCourseResourceList", method = RequestMethod.GET)
    public ResponseObject getCourseResourceList(Integer current, Integer size) {
        Page<CourseApplyResourceVO> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        OnlineUser user = getCurrentUser();
        return ResponseObject.newSuccessResponseObject(courseApplyService.selectCourseResourcePage(page, user.getId()));
    }

    /**
     * Description：获取资源播放代码
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 10:18 2018/2/1 0001
     **/
    @RequestMapping(value = "/getCourseResourcePlayer", method = RequestMethod.GET)
    public ResponseObject getCourseResource(Integer resourceId) {
        OnlineUser user = getCurrentUser();
        return ResponseObject.newSuccessResponseObject(courseApplyService.selectCourseResourcePlayerById(user.getId(), resourceId));
    }

    /**
     * Description：新增课程申请
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 3:58 2018/1/19 0019
     **/
    @RequestMapping(value = "/saveCourseApply", method = RequestMethod.POST)
    public ResponseObject saveCourseApply(@RequestBody CourseApplyInfo courseApplyInfo) {
        OnlineUser user = getCurrentUser();
        courseApplyInfo.setUserId(user.getId());
        courseApplyInfo.setTeaching(false);
        courseApplyInfo.setClientType(ClientType.PC.getCode());
        courseApplyService.saveCourseApply(courseApplyInfo);
        return ResponseObject.newSuccessResponseObject("保存成功");
    }

    /**
     * Description：更新课程申请信息
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/2/4 0004 下午 7:35
     **/
    @RequestMapping(value = "/updateCourseApply", method = RequestMethod.POST)
    public ResponseObject updateCourseApply(@RequestBody CourseApplyInfo courseApplyInfo) {
        OnlineUser user = getCurrentUser();
        courseApplyInfo.setUserId(user.getId());
        courseApplyService.updateCourseApply(courseApplyInfo);
        return ResponseObject.newSuccessResponseObject("保存成功");
    }

    @RequestMapping(value = "/updateCollectionApply", method = RequestMethod.POST)
    public ResponseObject updateCollectionApply(@RequestBody CourseApplyInfo courseApplyInfo) {
        OnlineUser user = getCurrentUser();
        courseApplyInfo.setUserId(user.getId());
        courseApplyInfo.setClientType(ClientType.PC.getCode());
        courseApplyService.updateCollectionApply(courseApplyInfo);
        return ResponseObject.newSuccessResponseObject("保存成功");
    }

    /**
     * Description：新增课程专辑申请
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 3:58 2018/1/19 0019
     **/
    @RequestMapping(value = "/saveCollectionApply", method = RequestMethod.POST)
    public ResponseObject saveCollectionApply(@RequestBody CourseApplyInfo courseApplyInfo) {
        OnlineUser user = getCurrentUser();
        courseApplyInfo.setUserId(user.getId());
        courseApplyInfo.setTeaching(false);
        courseApplyInfo.setClientType(ClientType.PC.getCode());
        courseApplyService.saveCollectionApply(courseApplyInfo);
        
        return ResponseObject.newSuccessResponseObject("保存成功");
    }
    
    /**
     * 添加专辑下的课程
     * @param courseApplyInfo
     * @return
     */
    @RequestMapping(value = "/saveCollectionCourse", method = RequestMethod.POST)
    public ResponseObject saveCollectionCourse(@RequestBody CourseApplyInfo courseApplyInfo) {
      
        
        OnlineUser user = getCurrentUser();
        courseApplyInfo.setUserId(user.getId());
        courseApplyInfo.setTeaching(false);
        courseApplyInfo.setClientType(ClientType.PC.getCode());
        //课程审核的id
        courseService.saveCollectionCourse4Lock(user.getId(),courseApplyInfo);
        return ResponseObject.newSuccessResponseObject("保存成功");
    }
    

    /**
     * Description：新增资源
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 3:58 2018/1/19 0019
     **/
    @RequestMapping(value = "/saveCourseResource", method = RequestMethod.POST)
    public ResponseObject saveCourseResource(@RequestBody CourseApplyResource courseApplyResource) {
        OnlineUser user = getCurrentUser();
        courseApplyResource.setUserId(user.getId());
        courseApplyService.saveCourseApplyResource(courseApplyResource);
        return ResponseObject.newSuccessResponseObject("保存成功");
    }

    /**
     * Description：删除资源
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/2/3 0003 下午 9:12
     **/
    @RequestMapping(value = "/deleteCourseResource", method = RequestMethod.POST)
    public ResponseObject deleteCourseResource(String resourceId) {
        OnlineUser user = getCurrentUser();
        courseApplyService.deleteCourseApplyResource(user.getId(), resourceId);
        return ResponseObject.newSuccessResponseObject("删除成功");
    }

    /**
     * Description：删除课程申请
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/2/5 0005 下午 2:15
     **/
    @RequestMapping(value = "/deleteCourseApplyById")
    public ResponseObject deleteCourseApplyById(Integer caiId) {
        OnlineUser user = getCurrentUser();
        courseApplyService.deleteCourseApplyById(user.getId(), caiId);
        return ResponseObject.newSuccessResponseObject("删除成功");
    }

    /**
     * Description：获取微吼直播路径
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 上午 9:18 2018/1/23 0023
     **/
    @RequestMapping(value = "getWebinarUrl")
    public ResponseObject getWebinarUrl(String webinarId) {
        ResponseObject responseObj = new ResponseObject();
        String url = VhallUtil.getWebinarUrl(webinarId);
        responseObj.setSuccess(true);
        responseObj.setResultObject(url);
        return responseObj;
    }

    /**
     * Description：
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 9:45 2018/2/1 0001
     **/
    @RequestMapping(value = "getVhallInfo")
    public ResponseObject getVhallInfo() {
        ResponseObject responseObj = new ResponseObject();
        Map vhallInfo = new HashMap();
        OnlineUser user = getCurrentUser();
        vhallInfo.put("vhallAccount", "v" + user.getVhallId());
        vhallInfo.put("password", user.getVhallPass());
        responseObj.setSuccess(true);
        responseObj.setResultObject(vhallInfo);
        return responseObj;
    }

    /**
     * Description：上/下架课程
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 上午 9:18 2018/1/23 0023
     **/
    @RequestMapping(value = "changeSaleState", method = RequestMethod.POST)
    public ResponseObject changeSaleState(Integer courseId, String courseApplyId, Integer state) throws IOException, SolrServerException {
        ResponseObject responseObj = new ResponseObject();
        OnlineUser user = getCurrentUser();
        courseApplyService.updateSaleState(user.getId(), courseApplyId, state);
        responseObj.setSuccess(true);
        if (state == 1) {
            sendCourseOnlineMessage(courseApplyId, user);
            //添加医师动态
            Course course = courseService.findByApplyId(courseApplyId);
            medicalDoctorPostsService.addDoctorPosts(user.getId(),course.getId(),null,course.getGradeName(),course.getSubtitle());
            responseObj.setResultObject("上架成功");
        } else {
            responseObj.setResultObject("下架成功");
        }
        try {
            courseSolrService.initCourseSolrDataById(courseId);
        } catch (Exception e) {
           e.printStackTrace();
           return responseObj;
        }
        return responseObj;
    }

    /**
     * Description：获取嘉宾直播路径
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/4/11 0011 下午 4:10
     **/
    @RequestMapping(value = "getWebinarGuestUrl")
    public ResponseObject getWebinarGuestUrl(String webinarId) {
        ResponseObject responseObj = new ResponseObject();
        String url = VhallUtil.getWebinarGuestUrl(webinarId);
        responseObj.setSuccess(true);
        responseObj.setResultObject(url);
        return responseObj;
    }

    @RequestMapping(value = "student", method = RequestMethod.GET)
    public ResponseObject offlineStudent(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(required = false) Integer courseId) {
        Page<LineCourseApplyStudentVO> studentVOPage = new Page<>(page, size);
        return ResponseObject.newSuccessResponseObject(lineApplyService.listLineApplyStudent(studentVOPage, courseId, getCurrentUser().getId()));
    }

    @RequestMapping(value = "student/{id}/{learned}", method = RequestMethod.PUT)
    public ResponseObject studentLearned(@PathVariable String id, @PathVariable boolean learned) {
        if (lineApplyService.updateLearned(id, learned, getCurrentUser().getId())) {
            return ResponseObject.newSuccessResponseObject("设置成功");
        } else {
            return ResponseObject.newErrorResponseObject("设置失败");
        }
    }

    @RequestMapping(value = "/teaching/saveCourseApply", method = RequestMethod.POST)
    public ResponseObject saveCourseApply4Teaching(@RequestBody CourseApplyInfo courseApplyInfo) {
        OnlineUser user = getCurrentUser();
        courseApplyInfo.setUserId(user.getId());
        courseApplyInfo.setClientType(ClientType.PC.getCode());
        courseApplyInfo.setTeaching(true);
        courseApplyService.saveCourseApply(courseApplyInfo);
        return ResponseObject.newSuccessResponseObject("保存成功");
    }

    @RequestMapping(value = "/teaching/updateCourseApply", method = RequestMethod.POST)
    public ResponseObject updateCourseApply4Teaching(@RequestBody CourseApplyInfo courseApplyInfo) {
        OnlineUser user = getCurrentUser();
        courseApplyInfo.setUserId(user.getId());
        courseApplyInfo.setClientType(ClientType.PC.getCode());
        courseApplyInfo.setTeaching(true);
        courseApplyService.updateCourseApply(courseApplyInfo);
        return ResponseObject.newSuccessResponseObject("保存成功");
    }

    @RequestMapping(value = "/teaching/apprentices/{courseId}", method = RequestMethod.GET)
    public ResponseObject getApprentices4Teaching(@PathVariable String courseId) {
        String doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(getUserId());
        return ResponseObject.newSuccessResponseObject(enrolService.listByDoctorIdAndCourseId(doctorId, courseId));
    }

    @RequestMapping(value = "/teaching/apprentices/{courseId}", method = RequestMethod.POST)
    public ResponseObject saveCourseTeaching(@PathVariable String courseId,String apprenticeIds) {
        String doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(getUserId());
        enrolService.saveCourseTeaching(doctorId,courseId,apprenticeIds);
        return ResponseObject.newSuccessResponseObject("保存成功");
    }

    /**
     * 课程上架给关注该主播的用户发送消息
     *
     * @param courseApplyId
     * @param user
     */
    private void sendCourseOnlineMessage(String courseApplyId, OnlineUser user) {
        try {
            String userId = user.getId();
            logger.warn("userId:{}, courseApplyId:{}", userId, courseApplyId);
            List<FocusVo> focusVos = focusService.selectFansList(userId);
            logger.warn("size: {}", focusVos.size());
            if (!focusVos.isEmpty()) {
                Course course = courseService.findByApplyId(courseApplyId);
                Date startTime = course.getStartTime();
                String content;
                Map<String, String> weixinParams;
                for (FocusVo focusVo : focusVos) {
                    String fansId = focusVo.getUserId();
                    OnlineUser onlineUser = onlineUserCenterService.getUser(fansId);
                    if (onlineUser != null) {
                        content = MessageFormat.format(WEB_COURSE_ONLINE_MESSAGE_TIPS, onlineUser.getName(),
                                course.getLecturer(), course.getGradeName());
                        weixinParams = new HashMap<>(4);
                        weixinParams.put("first", TextStyleUtil.clearStyle(content));
                        weixinParams.put("keyword1", course.getGradeName());
                        weixinParams.put("keyword2", startTime == null ? "随到随学" : TimeUtil.getYearMonthDayHHmm(startTime));
                        weixinParams.put("remark", "");
                        commonMessageService.saveMessage(new BaseMessage.Builder(MessageTypeEnum.SYSYTEM.getVal())
                                .buildAppPush(APP_PUSH_COURSE_ONLINE_MESSAGE_TIPS)
                                .buildWeb(content)
                                .buildWeixin(weixinTemplateMessageRemindCode, weixinParams)
                                .detailId(String.valueOf(course.getId()))
                                .build(fansId, RouteTypeEnum.COMMON_COURSE_DETAIL_PAGE, userId));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("send course online error. courseApplyId: {}", courseApplyId);
            e.printStackTrace();
        }
    }


}
