package com.xczh.consumer.market.controller.course;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.interceptor.IOSVersionInterceptor;
import com.xczh.consumer.market.utils.APPUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.course.consts.MultiUrlHelper;
import com.xczhihui.course.enums.RouteTypeEnum;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.service.IFocusService;
import com.xczhihui.course.service.IMyInfoService;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBannerService;
import com.xczhihui.medical.doctor.vo.DoctorBannerVO;
import com.xczhihui.medical.hospital.model.MedicalHospital;
import com.xczhihui.medical.hospital.service.IMedicalHospitalApplyService;
import com.xczhihui.pay.util.StringUtils;

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
    public ResponseObject userHomePage(@Account(optional = true) Optional<String> accountIdOpt,
                                       HttpServletResponse res,
                                       @RequestParam("lecturerId") String lecturerId) throws Exception {

        LOGGER.info("lecturerId-->id" + lecturerId);

        Map<String, Object> mapAll = new HashMap<String, Object>();
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


        List<Integer> listff = focusServiceRemote.selectFocusOrFansCountOrCriticizeCount(lecturerId);
        mapAll.put("fansCount", listff.get(0));           //粉丝总数
        mapAll.put("focusCount", listff.get(1));           //关注总数
        mapAll.put("criticizeCount", listff.get(2));       //总数评论总数
        /**
         * 判断用户是否已经关注了这个主播
         */
        if (!accountIdOpt.isPresent()) {
            mapAll.put("isFours", 0);
        } else {
            Integer isFours = focusServiceRemote.isFoursLecturer(accountIdOpt.get(), lecturerId);
            mapAll.put("isFours", isFours);
        }
        /**
         * 此主播最近一次的直播
         */
        CourseLecturVo cv = courseService.selectLecturerRecentCourse(lecturerId, IOSVersionInterceptor.onlyThread.get());
        mapAll.put("recentCourse", cv);
        return ResponseObject.newSuccessResponseObject(mapAll);
    }

    @RequestMapping("doctor")
    @ResponseBody
    public ResponseObject homeV2(@Account(optional = true) Optional<String> accountIdOpt,
                                 HttpServletResponse res,
                                 @RequestParam(value = "lecturerId", required = false) String lecturerId,
                                 HttpServletRequest request, @RequestParam(value = "doctorId", required = false) String doctorId) throws Exception {
        Map<String, Object> mapAll = new HashMap<String, Object>();
        Map<String, Object> lecturerInfo = myInfoService.findDoctorInfoById(lecturerId);
        if (lecturerInfo == null) {
            return ResponseObject.newErrorResponseObject("获取医师信息有误");
        }
        List<Integer> listff = focusServiceRemote.selectFocusOrFansCountOrCriticizeCount(lecturerId);
        mapAll.put("lecturerInfo", lecturerInfo);
        mapAll.put("fansCount", listff.get(0));
        mapAll.put("focusCount", listff.get(1));
        if (accountIdOpt.isPresent()) {
            Integer isFours = focusServiceRemote.isFoursLecturer(accountIdOpt.get(), lecturerId);
            mapAll.put("isFours", isFours);
        } else {
            mapAll.put("isFours", 0);
        }
        List<DoctorBannerVO> list = medicalDoctorBannerService.listByUserId(lecturerId);
        mapAll.put("banners", list.stream()
                .peek(doctorBannerVO ->
                {
                    String routeType = doctorBannerVO.getRouteType();
                    if (RouteTypeEnum.APPRENTICE_DETAIL.name().equals(routeType)) {
                        String apprenticeParam = MultiUrlHelper.handleApprenticeParam(returnOpenidUri, doctorBannerVO.getLinkParam());
                        if (StringUtils.isNotBlank(apprenticeParam)) {
                            doctorBannerVO.setLinkParam(apprenticeParam);
                        }
                    }
                    doctorBannerVO.setUrl(MultiUrlHelper.getUrl(routeType, APPUtil.getMobileSource(request), doctorBannerVO.getLinkParam()));
                })
                .collect(Collectors.toList()));
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
                        if (RouteTypeEnum.APPRENTICE_DETAIL.name().equals(routeType)) {
                            String apprenticeParam = MultiUrlHelper.handleApprenticeParam(returnOpenidUri, doctorBannerVO.getLinkParam());
                            if (StringUtils.isNotBlank(apprenticeParam)) {
                                doctorBannerVO.setLinkParam(apprenticeParam);
                            }
                        }
                        doctorBannerVO.setUrl(MultiUrlHelper.getUrl(routeType, APPUtil.getMobileSource(request), doctorBannerVO.getLinkParam()));
                    })
                    .collect(Collectors.toList()));
        } else {
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
            Page<CourseLecturVo> list = courseService.selectLecturerAllCourse(page,
                    lecturerId, null, IOSVersionInterceptor.onlyThread.get());
            return ResponseObject.newSuccessResponseObject(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("网络开小差");
        }
    }
}
