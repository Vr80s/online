package com.xczhihui.bxg.online.web.controller.medical;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xczhihui.bxg.online.web.body.doctor.EnrollmentRegulationsBody;
import com.xczhihui.bxg.online.web.controller.AbstractController;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.doctor.vo.MedicalDoctorVO;
import com.xczhihui.medical.enrol.model.MedicalEnrollmentRegulations;
import com.xczhihui.medical.enrol.service.EnrolService;

/**
 * 招生简章
 *
 * @author hejiwei
 */
@RequestMapping("doctor/enrollmentRegulations")
@RestController
public class EnrollmentRegulationsController extends AbstractController {

    @Autowired
    private EnrolService enrolService;
    @Autowired
    private IMedicalDoctorBusinessService medicalDoctorBusinessService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseObject save(@Valid @RequestBody EnrollmentRegulationsBody enrollmentRegulationsBody) {
        String doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(getUserId());
        enrolService.save(enrollmentRegulationsBody.build(getUserId(), doctorId));
        return ResponseObject.newSuccessResponseObject();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseObject get(@PathVariable int id) {
        MedicalEnrollmentRegulations medicalEnrollmentRegulations = enrolService.findById(id);
        MedicalDoctorVO medicalDoctorVO = medicalDoctorBusinessService.findSimpleById(medicalEnrollmentRegulations.getDoctorId());
        medicalEnrollmentRegulations.setName(medicalDoctorVO.getName());
        return ResponseObject.newSuccessResponseObject(medicalEnrollmentRegulations);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseObject update(@Valid @RequestBody EnrollmentRegulationsBody enrollmentRegulationsBody, @PathVariable int id) {
        String doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(getUserId());
        enrolService.updateById(enrollmentRegulationsBody.build(getUserId(), doctorId), id);
        return ResponseObject.newSuccessResponseObject();
    }

    @RequestMapping(value = "{id}/{status}", method = RequestMethod.PUT)
    public ResponseObject updateStatus(@PathVariable int id, @PathVariable boolean status) {
        String doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(getUserId());
        enrolService.updateRegulationsStatus(id, doctorId, status);
        return ResponseObject.newSuccessResponseObject();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseObject list(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        String doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(getUserId());
        return ResponseObject.newSuccessResponseObject(enrolService.listPageByDoctorId(doctorId, page, size));
    }
}
