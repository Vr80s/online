package com.xczh.consumer.market.controller.course;

import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.ImmutableMap;
import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.interceptor.HeaderInterceptor;
import com.xczh.consumer.market.utils.APPUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.enums.CourseStatus;
import com.xczhihui.common.util.enums.OnlineApprenticeStatus;
import com.xczhihui.common.util.enums.ResourceCheck;
import com.xczhihui.common.util.enums.RouteTypeEnum;
import com.xczhihui.course.consts.MultiUrlHelper;
import com.xczhihui.course.consts.RouteUrlUtil;
import com.xczhihui.course.model.Course;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.service.IFocusService;
import com.xczhihui.course.service.IMyInfoService;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.medical.doctor.model.MedicalDoctorAccount;
import com.xczhihui.medical.doctor.service.*;
import com.xczhihui.medical.doctor.vo.DoctorBannerVO;
import com.xczhihui.medical.doctor.vo.MobileArticleVO;
import com.xczhihui.medical.enrol.service.EnrolService;
import com.xczhihui.medical.headline.vo.SimpleUserVO;
import com.xczhihui.medical.hospital.model.MedicalHospital;
import com.xczhihui.medical.hospital.service.IMedicalHospitalApplyService;

/**
 * ClassName: HostController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年1月16日<br>
 */
@Controller
@RequestMapping("/xczh/host")
public class HostController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(HostController.class);


    @Autowired
    @Qualifier("focusServiceRemote")
    private IFocusService focusServiceRemote;
    @Autowired
    private IMedicalHospitalApplyService medicalHospitalApplyService;
    @Autowired
    private ICourseService courseService;
    @Autowired
    private IMyInfoService myInfoService;
    @Autowired
    private IMedicalDoctorBannerService medicalDoctorBannerService;
    @Autowired
    private IMedicalDoctorArticleService medicalDoctorArticleService;
    @Autowired
    private EnrolService enrolService;
    @Autowired
    private IMedicalDoctorQuestionService medicalDoctorQuestionService;
    @Autowired
    private IMedicalDoctorBusinessService medicalDoctorBusinessService;
    @Autowired
    private IRemoteTreatmentService remoteTreatmentService;

    @Value("${returnOpenidUri}")
    private String returnOpenidUri;


    /**
     * Description：用户主页
     *
     * @param accountIdOpt
     * @param res
     * @param lecturerId
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("hostPageInfo")
    @ResponseBody
    public ResponseObject hostPageInfo(@Account(optional = true) Optional<String> accountIdOpt,
                                       HttpServletResponse res,
                                       @RequestParam("lecturerId") String lecturerId) throws Exception {

        LOGGER.info("lecturerId-->id" + lecturerId);

        Map<String, Object> mapAll = myInfoService.selectUserHomePageData(accountIdOpt.isPresent() ? accountIdOpt.get() : null, lecturerId, HeaderInterceptor.ONLY_THREAD.get());
        /**
         * 得到讲师   主要是房间号，缩略图的信息、讲师的精彩简介
         *
         * 这个主播可能认证的是医馆，也可能认证的是医师
         */
        Map<String, Object> lecturerInfo = myInfoService.findHostInfoById(lecturerId, false);
        if (lecturerInfo == null) {
            return ResponseObject.newErrorResponseObject("获取医师信息有误");
        }
        lecturerInfo.put("richHostDetailsUrl", returnOpenidUri + "/xcview/html/person_fragment.html?type=4&typeId=" + lecturerId);

        mapAll.put("lecturerInfo", lecturerInfo);          //讲师基本信息
        MedicalHospital mha = null;

        LOGGER.info("lecturerInfo" + lecturerInfo.toString());
        //1.医师2.医馆
        if (lecturerInfo.get("type").toString().equals("1")) {
            mha = medicalHospitalApplyService.getMedicalHospitalByMiddleUserId(lecturerId);
        } else if (lecturerInfo.get("type").toString().equals("2")) {
            mha = medicalHospitalApplyService.getMedicalHospitalByUserId(lecturerId);
        }
        //认证的主播 还是 医馆
        mapAll.put("hospital", mha);

        return ResponseObject.newSuccessResponseObject(mapAll);
    }

    @RequestMapping("doctor/v2")
    @ResponseBody
    public ResponseObject doctorV2(@Account(optional = true) Optional<String> accountIdOpt,
                                   HttpServletResponse res,
                                   HttpServletRequest request, @RequestParam(value = "doctorId") String doctorId) throws Exception {
        Map<String, Object> mapAll = new HashMap<String, Object>();
        Map<String, Object> lecturerInfo = myInfoService.findDoctorInfoByDoctorId(doctorId);
        if (lecturerInfo == null) {
            return ResponseObject.newErrorResponseObject("获取医师信息有误");
        }
        String userId = MapUtils.getString(lecturerInfo, "userId");
        mapAll.put("lecturerInfo", lecturerInfo);
        if (userId != null) {
            List<Integer> listff = focusServiceRemote.selectFocusOrFansCountOrCriticizeCount(userId);
            mapAll.put("fansCount", listff.get(0));
            mapAll.put("focusCount", listff.get(1));
            mapAll.put("followHidden", false);
            List<DoctorBannerVO> list = medicalDoctorBannerService.listByUserId(userId);
            mapAll.put("banners", list.stream()
                    .peek(doctorBannerVO ->
                    {
                        String routeType = doctorBannerVO.getRouteType();
                        doctorBannerVO.setUrl(MultiUrlHelper.getUrl(routeType, APPUtil.getMobileSource(request),
                                MultiUrlHelper.handleParam(returnOpenidUri, doctorBannerVO.getLinkParam(), routeType)));
                    })
                    .collect(Collectors.toList()));
            mapAll.put("apprentice", enrolService.isApprentice(doctorId, userId));
            mapAll.put("apprenticeCount", enrolService.countApprentice(doctorId));
        } else {
            mapAll.put("apprentice", false);
            mapAll.put("apprenticeCount", 0);
            mapAll.put("followHidden", true);
            mapAll.put("fansCount", 0);
            mapAll.put("focusCount", 0);
        }
        if (accountIdOpt.isPresent() && userId != null) {
            Integer isFours = focusServiceRemote.isFoursLecturer(accountIdOpt.get(), userId);
            mapAll.put("isFours", isFours);
        } else {
            mapAll.put("isFours", 0);
        }

        return ResponseObject.newSuccessResponseObject(mapAll);
    }

    @RequestMapping("doctor/apprentice")
    @ResponseBody
    public ResponseObject doctorApprentice(@Account(optional = true) Optional<String> accountIdOpt,
                                           @RequestParam String doctorId, HttpServletRequest request) throws Exception {
        Map<String, Object> apprenticeData = new HashMap<>(5);
        List<Map<String, Object>> regulations = enrolService.listByDoctorId(doctorId);
        regulations.forEach(regulation -> regulation.put("url", MultiUrlHelper.getUrl(RouteTypeEnum.APPRENTICE_DETAIL.name(), APPUtil.getMobileSource(request),
                MultiUrlHelper.handleParam(returnOpenidUri, MapUtils.getString(regulation, "id"), RouteTypeEnum.APPRENTICE_DETAIL.name()))));
        apprenticeData.put("regulations", regulations);
        apprenticeData.put("questions", medicalDoctorQuestionService.selectQuestionByDoctorId(new Page<>(1, 100), doctorId).getRecords());
        apprenticeData.put("apprentices", enrolService.findApprenticesByDoctorId(doctorId).stream().map(SimpleUserVO::getSmallHeadPhoto).collect(Collectors.toList()));
        MedicalDoctorAccount doctorAccount = medicalDoctorBusinessService.getByDoctorId(doctorId);
        String userId = accountIdOpt.isPresent() ? accountIdOpt.get() : null;
        if (doctorAccount != null) {
            apprenticeData.put("apprenticeCourses", courseService.selectTeachingCoursesByUserId(new Page<CourseLecturVo>(1, 100),doctorAccount.getAccountId(),userId));
        } else {
            apprenticeData.put("apprenticeCourses", Collections.emptyList());
        }
        apprenticeData.put("settings", enrolService.findSettingsByDoctorId(doctorId));
        apprenticeData.put("onlineApprenticeStatus", accountIdOpt.map(accountId -> enrolService.getOnlineApprenticeStatus(doctorId, accountId))
                .orElse(OnlineApprenticeStatus.NOT_APPLY.getVal()));
        apprenticeData.put("treatments", remoteTreatmentService.listAppointment(doctorId, false));
        return ResponseObject.newSuccessResponseObject(apprenticeData);
    }

    /**
     * 校验路由的url是否失效
     *
     * @param url
     * @return
     */
    @RequestMapping(value = "resource/check", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject checkResource(@RequestParam String url) {
        Map<String, Object> urlMap;
        try {
            urlMap = RouteUrlUtil.handleUrl(URLDecoder.decode(url, "UTF-8"));
            String paramType = MapUtils.getString(urlMap, "pageType", null);
            if (StringUtils.isNotBlank(paramType)) {
                Map<String, Object> params = (Map<String, Object>) MapUtils.getObject(urlMap, "params");
                if (paramType.equals(MultiUrlHelper.APP_COURSE_DETAIL_TYPE)) {
                    Integer id = MapUtils.getInteger(params, "id");
                    Course course = courseService.findSimpleInfoById(id);
                    if (course != null && CourseStatus.ENABLE.getCode().equals(course.getStatus())) {
                        return ResponseObject.newSuccessResponseObject(ImmutableMap.of("type", ResourceCheck.COURSE.getCode(), "expired", false));
                    } else {
                        return ResponseObject.newSuccessResponseObject(ImmutableMap.of("type", ResourceCheck.COURSE.getCode(), "expired", true));
                    }
                } else if (paramType.equals(MultiUrlHelper.APP_SPECIAL_COLUMN_DETAIL_TYPE) || paramType.equals(MultiUrlHelper.APP_DOCTOR_CASE_DETAIL_TYPE)) {
                    Integer id = MapUtils.getInteger(params, "id");
                    MobileArticleVO mobileArticleVO = medicalDoctorArticleService.get(id);
                    if (mobileArticleVO != null) {
                        return ResponseObject.newSuccessResponseObject(ImmutableMap.of("type", ResourceCheck.ARTICLE.getCode(), "expired", false));
                    } else {
                        return ResponseObject.newSuccessResponseObject(ImmutableMap.of("type", ResourceCheck.ARTICLE.getCode(), "expired", true));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseObject.newSuccessResponseObject(ImmutableMap.of("expired", false, "type", 0));
    }

    /**
     * Description：用户主页    -- 课程列表
     *
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("hostPageCourse")
    @ResponseBody
    public ResponseObject userHomePageCourseList(@RequestParam("lecturerId") String lecturerId,
                                                 @RequestParam("pageNumber") Integer pageNumber,
                                                 @RequestParam("pageSize") Integer pageSize) throws Exception {
        Page<CourseLecturVo> page = new Page<>();
        page.setCurrent(pageNumber);
        page.setSize(pageSize);
        try {
            Page<CourseLecturVo> list = courseService.selectLecturerAllCourseByType(page,
                    lecturerId, null, HeaderInterceptor.ONLY_THREAD.get());
            return ResponseObject.newSuccessResponseObject(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("网络开小差");
        }
    }

}
