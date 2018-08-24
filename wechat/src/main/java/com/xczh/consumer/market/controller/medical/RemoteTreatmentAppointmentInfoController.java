package com.xczh.consumer.market.controller.medical;

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
import com.xczhihui.common.util.enums.ResultCode;
import com.xczhihui.course.model.Course;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.medical.doctor.model.Treatment;
import com.xczhihui.medical.doctor.model.TreatmentAppointmentInfo;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsService;
import com.xczhihui.medical.doctor.service.IRemoteTreatmentService;
import com.xczhihui.medical.enrol.service.EnrolService;

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
    public ResponseObject userAppointment(@Account String accountId) {
        return ResponseObject.newSuccessResponseObject(remoteTreatmentService.listByUserId(accountId));
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
    public ResponseObject doctorTreatment(@Account String accountId) {
        String doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(accountId);
        return ResponseObject.newSuccessResponseObject(remoteTreatmentService.listByDoctorId(doctorId));
    }

    @RequestMapping(value = "operation/status", method = RequestMethod.POST)
    public ResponseObject treatmentStatusUpdate(@Account String accountId, @RequestParam int infoId, @RequestParam int status) {
        remoteTreatmentService.updateTreatmentStartStatus(infoId, status);
        return ResponseObject.newSuccessResponseObject(null);
    }

    @RequestMapping(value = "cancel/appointment", method = RequestMethod.POST)
    public ResponseObject cancelAppointment(@Account String accountId, @RequestParam int id) {
        remoteTreatmentService.updateAppointmentForCancel(id);
        Treatment treatment = remoteTreatmentService.selectTreatmentById(id);
        if (treatment != null && treatment.getCourseId() != null) {
            courseService.updateStatus(treatment.getCourseId(), 0);
        }
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
    
    @RequestMapping(value = "applyTest", method = RequestMethod.GET)
    public ResponseObject applyTest(@Account String accountId, @RequestParam int infoId, @RequestParam boolean status) throws Exception {
        TreatmentAppointmentInfo treatmentAppointmentInfo = remoteTreatmentService.selectById(infoId);
        remoteTreatmentService.updateStatus(treatmentAppointmentInfo.getTreatmentId(), status);
        if (status) {
            Integer courseId = courseService.createTherapyLive(infoId, HeaderInterceptor.getClientType().getCode(),
            		accountId);
            Course course = courseService.selectById(courseId);
            medicalDoctorPostsService.addDoctorPosts(accountId, course.getId(), null, course.getGradeName(), course.getSubtitle(), course.getAppointmentInfoId());
        }
        return ResponseObject.newSuccessResponseObject(null);
    }

    @RequestMapping(value = "inavUserList", method = RequestMethod.GET)
    public ResponseObject inavUserList( @RequestParam String inavId) throws Exception {
         remoteTreatmentService.inavUserList(inavId);
        return ResponseObject.newSuccessResponseObject(null);
    }
    
}
