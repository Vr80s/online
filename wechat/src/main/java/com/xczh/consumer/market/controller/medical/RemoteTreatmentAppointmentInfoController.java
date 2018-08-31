package com.xczh.consumer.market.controller.medical;


import static com.xczhihui.common.util.enums.CommunicationMessageType.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.body.treatment.TreatmentAppointmentInfoBody;
import com.xczh.consumer.market.interceptor.HeaderInterceptor;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.enums.AppointmentStatus;
import com.xczhihui.common.util.enums.MessageTypeEnum;
import com.xczhihui.common.util.enums.ResultCode;
import com.xczhihui.common.util.enums.RouteTypeEnum;
import com.xczhihui.common.util.vhallyun.InteractionService;
import com.xczhihui.course.model.Course;
import com.xczhihui.course.params.BaseMessage;
import com.xczhihui.course.service.ICommonMessageService;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.medical.doctor.model.Treatment;
import com.xczhihui.medical.doctor.model.TreatmentAppointmentInfo;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsService;
import com.xczhihui.medical.doctor.service.IRemoteTreatmentService;
import com.xczhihui.medical.enrol.service.EnrolService;
import com.xczhihui.medical.exception.MedicalException;
import com.xczhihui.user.center.service.UserCenterService;

/**
 * 远程诊疗
 *
 * @author hejiwei
 */
@RequestMapping("doctor/treatment")
@RestController
public class RemoteTreatmentAppointmentInfoController {

    @Autowired
    private IRemoteTreatmentService remoteTreatmentService;
    @Autowired
    private EnrolService enrolService;
    @Autowired
    private ICourseService courseService;
    @Autowired
    private IMedicalDoctorBusinessService medicalDoctorBusinessService;
    @Autowired
    private IMedicalDoctorPostsService medicalDoctorPostsService;
    @Autowired
    private ICommonMessageService commonMessageService;
    @Autowired
    private UserCenterService userCenterService;

    @RequestMapping(value = "appointmentInfo", method = RequestMethod.POST)
    public ResponseObject save(TreatmentAppointmentInfoBody treatmentAppointmentInfoBody, @Account String accountId) {
        int result = remoteTreatmentService.saveAppointmentInfo(treatmentAppointmentInfoBody.build(accountId));
        if (result != 1) {
            return ResponseObject.newErrorResponseObject("抱歉~该时间段已被预约, 请重新选择预约时间", ResultCode.APPOINTMENT_STATE_CHANGE);
        }
        return ResponseObject.newSuccessResponseObject(null);
    }

    @RequestMapping(value = "appointmentInfo", method = RequestMethod.GET)
    public ResponseObject get(@RequestParam int id) {
        return ResponseObject.newSuccessResponseObject(remoteTreatmentService.getInfo(id));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseObject list(@Account String accountId, String doctorId) {
        Map<String, Object> result = enrolService.findApprenticeInfo(doctorId, accountId);
        if (result == null) {
            return ResponseObject.newErrorResponseObject("您不是该医师的弟子~");
        }
        result.put("treatments", remoteTreatmentService.listAppointment(doctorId, true, accountId));
        return ResponseObject.newSuccessResponseObject(result);
    }

    @Deprecated
    @RequestMapping(value = "check/repeat", method = RequestMethod.GET)
    public ResponseObject checkAppointmentInfo(@RequestParam int id, @Account String accountId) {
        return ResponseObject.newSuccessResponseObject(remoteTreatmentService.checkRepeatAppoint(id, accountId));
    }

    @RequestMapping(value = "check", method = RequestMethod.GET)
    public ResponseObject checkAppointmentInfoValid(@RequestParam int id, @Account String accountId) {
        return ResponseObject.newSuccessResponseObject(remoteTreatmentService.checkAppointment(id, accountId));
    }

    @RequestMapping(value = "user/appointment", method = RequestMethod.GET)
    public ResponseObject userAppointment(@Account String accountId, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseObject.newSuccessResponseObject(remoteTreatmentService.listByUserId(accountId, page, size));
    }

    @RequestMapping(value = "user/appointment/delete", method = RequestMethod.POST)
    public ResponseObject userAppointmentDelete(@Account String accountId, @RequestParam int id) {
        remoteTreatmentService.deleteAppointmentInfo(id);
        return ResponseObject.newSuccessResponseObject(null);
    }

    @RequestMapping(value = "list/delete", method = RequestMethod.POST)
    public ResponseObject listDelete(@Account String accountId, @RequestParam int id) {
        remoteTreatmentService.delete(id, accountId);
        return ResponseObject.newSuccessResponseObject(null);
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResponseObject doctorTreatment(@Account String accountId, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        String doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(accountId);
        return ResponseObject.newSuccessResponseObject(remoteTreatmentService.listByDoctorId(doctorId, page, size));
    }

    @RequestMapping(value = "send/message", method = RequestMethod.POST)
    public ResponseObject sendMessage(@Account String accountId, @RequestParam int infoId, @RequestParam int type) {
        TreatmentAppointmentInfo treatmentAppointmentInfo = remoteTreatmentService.selectById(infoId);
        if (treatmentAppointmentInfo == null || treatmentAppointmentInfo.getTreatmentId() == null) {
            throw new MedicalException("参数错误");
        }
        Treatment treatment = remoteTreatmentService.selectTreatmentById(treatmentAppointmentInfo.getTreatmentId());
        Map<String, Object> params = new HashMap<>();
        Integer courseId = treatment.getCourseId();
        params.put("courseId", courseId);
        params.put("infoId", infoId);
        params.put("show", false);
        String targetUserId = null;
        String doctorId = treatment.getDoctorId();

        if (type == USER_TREATMENT_START.getVal() || type == USER_TREATMENT_REFUSE.getVal()) {
            params.put("user", userCenterService.findSimpleInfoByUserId(accountId));
            targetUserId = medicalDoctorBusinessService.getByDoctorId(doctorId).getAccountId();
        } else if (type == DOCTOR_TREATMENT_START.getVal() || type == DOCTOR_TREATMENT_STOP.getVal() || type == DOCTOR_TREATMENT_REFUSE.getVal()) {
            targetUserId = treatmentAppointmentInfo.getUserId();
            Map<String, Object> doctorInfo = medicalDoctorBusinessService.getDoctorInfoByDoctorId(doctorId);
            params.put("doctor", doctorInfo);
        } else {
            throw new MedicalException("参数错误");
        }
        params.put("type", type);

        if (type == DOCTOR_TREATMENT_STOP.getVal()) {
            remoteTreatmentService.updateTreatmentStartStatus(infoId, AppointmentStatus.FINISHED.getVal());
            courseService.updateCourseLiveStatus("stop", courseService.selectById(courseId).getDirectId(), String.valueOf(HeaderInterceptor.getClientTypeCode()));
        }
        if (type == DOCTOR_TREATMENT_START.getVal()) {
            Integer treatmentStatus = treatment.getStatus();
            if (treatmentStatus == AppointmentStatus.EXPIRED.getVal()) {
                throw new MedicalException("该诊疗直播已过期");
            }
            if (treatmentStatus == AppointmentStatus.FINISHED.getVal()) {
                throw new MedicalException("该诊疗直播已结束");
            }
        }
        commonMessageService.pushAppMessage(new BaseMessage.Builder(MessageTypeEnum.SYSYTEM.getVal()).buildAppPushWithParams(null, params).build(targetUserId, RouteTypeEnum.NONE, null));
        return ResponseObject.newSuccessResponseObject(null);
    }

    @RequestMapping(value = "cancel/appointment", method = RequestMethod.POST)
    public ResponseObject cancelAppointment(@Account String accountId, @RequestParam int id) {
        remoteTreatmentService.updateAppointmentForCancel(id);
        return ResponseObject.newSuccessResponseObject(null);
    }

    @RequestMapping(value = "apply", method = RequestMethod.POST)
    public ResponseObject updateTreatmentStatus(@Account String accountId, @RequestParam int infoId, @RequestParam boolean status) throws Exception {
        TreatmentAppointmentInfo treatmentAppointmentInfo = remoteTreatmentService.selectById(infoId);
        remoteTreatmentService.updateStatus(treatmentAppointmentInfo.getTreatmentId(), status);
        if (status) {
            Integer courseId = courseService.createTherapyLive(infoId, HeaderInterceptor.getClientType().getCode(), accountId);
            remoteTreatmentService.updateTreatmentCourseId(treatmentAppointmentInfo.getTreatmentId(), courseId);
            Course course = courseService.selectById(courseId);
            medicalDoctorPostsService.addDoctorPosts(accountId, course.getId(), null, course.getGradeName(), course.getSubtitle(), course.getAppointmentInfoId());
        }
        return ResponseObject.newSuccessResponseObject(null);
    }

    @RequestMapping(value = "inavUserList", method = RequestMethod.GET)
    public ResponseObject inavUserList(@RequestParam String inavId) throws Exception {
        List<String> inavUserList = InteractionService.getInavUserList(inavId);
        return ResponseObject.newSuccessResponseObject(inavUserList.isEmpty() ? Collections.emptyList() : userCenterService.findByIds(inavUserList));
    }

    @RequestMapping(value = "start", method = RequestMethod.POST)
    public ResponseObject startTreatment(@RequestParam int infoId) {
        TreatmentAppointmentInfo treatmentAppointmentInfo = remoteTreatmentService.selectById(infoId);
        if (treatmentAppointmentInfo == null || treatmentAppointmentInfo.getTreatmentId() == null) {
            throw new MedicalException("参数错误");
        }
        Treatment treatment = remoteTreatmentService.selectTreatmentById(treatmentAppointmentInfo.getTreatmentId());
        remoteTreatmentService.updateTreatmentStartStatus(infoId, AppointmentStatus.STARTED.getVal());
        courseService.updateCourseLiveStatus("start", courseService.selectById(treatment.getCourseId()).getDirectId(), String.valueOf(HeaderInterceptor.getClientTypeCode()));
        return ResponseObject.newSuccessResponseObject(null);
    }

    @RequestMapping(value = "checkInav", method = RequestMethod.GET)
    public ResponseObject checkInavStatus(@RequestParam int infoId) {
        TreatmentAppointmentInfo treatmentAppointmentInfo = remoteTreatmentService.selectById(infoId);
        if (treatmentAppointmentInfo == null || treatmentAppointmentInfo.getTreatmentId() == null) {
            throw new MedicalException("参数错误, treatmentAppointment为空");
        }
        Treatment treatment = remoteTreatmentService.selectTreatmentById(treatmentAppointmentInfo.getTreatmentId());
        if (treatment == null) {
            throw new MedicalException("参数错误, treatment为空");
        }
        if (treatment.getCourseId() == null) {
            throw new MedicalException("该诊疗未关联课程");
        }
        Course course = courseService.selectById(treatment.getCourseId());
        if (course == null) {
            throw new MedicalException("课程为空");
        }
        if (course.getInavId() == null) {
            throw new MedicalException("课程未关联互动房间id");
        }
        return ResponseObject.newSuccessResponseObject(InteractionService.getStatus(course.getInavId()));
    }
}
