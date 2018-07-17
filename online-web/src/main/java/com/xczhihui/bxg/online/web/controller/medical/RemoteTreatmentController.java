package com.xczhihui.bxg.online.web.controller.medical;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.xczhihui.bxg.online.web.body.doctor.TreatmentBody;
import com.xczhihui.bxg.online.web.controller.AbstractController;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.doctor.service.IRemoteTreatmentService;

/**
 * 远程诊疗
 *
 * @author hejiwei
 */
@RequestMapping("doctor/treatment")
@RestController
public class RemoteTreatmentController extends AbstractController {
    @Autowired
    private IMedicalDoctorBusinessService medicalDoctorBusinessService;
    @Autowired
    private IRemoteTreatmentService remoteTreatmentService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseObject list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseObject.newSuccessResponseObject();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseObject save(@Valid @RequestBody TreatmentBody treatmentBody) {
        String userId = getUserId();
        String doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(userId);
        remoteTreatmentService.save(treatmentBody.build(doctorId, userId));
        return ResponseObject.newSuccessResponseObject("保存成功");
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseObject update(@Valid @RequestBody TreatmentBody treatmentBody, @PathVariable Integer id) {
        String userId = getUserId();
        String doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(userId);
        remoteTreatmentService.update(treatmentBody.build(doctorId, userId), id);
        return ResponseObject.newSuccessResponseObject("更新成功");
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseObject delete(@PathVariable Integer id) {
        remoteTreatmentService.delete(id, getUserId());
        return ResponseObject.newSuccessResponseObject("删除成功");
    }
}
