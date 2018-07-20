package com.xczh.consumer.market.controller.medical;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.body.treatment.TreatmentAppointmentInfoBody;
import com.xczh.consumer.market.utils.ResponseObject;
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

    @RequestMapping(value = "appointmentInfo", method = RequestMethod.POST)
    public ResponseObject save(TreatmentAppointmentInfoBody treatmentAppointmentInfoBody, @Account String accountId) {
        int result = remoteTreatmentService.saveAppointmentInfo(treatmentAppointmentInfoBody.build(accountId));
        if (result != 1) {
            return ResponseObject.newErrorResponseObject("抱歉~该时间段已被预约, 请重新选择预约时间");
        }
        return ResponseObject.newSuccessResponseObject(null);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseObject list(@Account String accountId, String doctorId) {
        Map<String, Object> result = enrolService.findApprenticeInfo(doctorId, accountId);
        if (result == null) {
            return ResponseObject.newErrorResponseObject("您不是该医师的弟子~");
        }
        result.put("treatments", remoteTreatmentService.listAppointment(doctorId, true));
        return ResponseObject.newSuccessResponseObject(result);
    }
}