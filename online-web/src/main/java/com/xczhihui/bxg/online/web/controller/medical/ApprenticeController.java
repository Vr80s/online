package com.xczhihui.bxg.online.web.controller.medical;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xczhihui.bxg.online.web.controller.AbstractController;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.enrol.model.ApprenticeSettings;
import com.xczhihui.medical.enrol.service.EnrolService;

/**
 * 医师弟子
 *
 * @author hejiwei
 */
@RestController
@RequestMapping("doctor/apprentice")
public class ApprenticeController extends AbstractController {

    @Autowired
    private EnrolService enrolService;
    @Autowired
    private IMedicalDoctorBusinessService medicalDoctorBusinessService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseObject list(@RequestParam(required = false, defaultValue = "1") int page,
                               @RequestParam(required = false, defaultValue = "10") int size,
                               @RequestParam(required = false) Integer type, @RequestParam(required = false) Integer status) {
        String doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(getUserId());
        return ResponseObject.newSuccessResponseObject(enrolService.listByDoctorId(doctorId, type, status, page, size));
    }

    @RequestMapping(value = "{id}/{apprentice}", method = RequestMethod.PUT)
    public ResponseObject apply(@PathVariable int id, @PathVariable int apprentice) {
        enrolService.updateStatusEntryInformationById(id, apprentice);
        return ResponseObject.newSuccessResponseObject();
    }

    @RequestMapping(value = "settings", method = RequestMethod.GET)
    public ResponseObject getSettings() {
        String doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(getUserId());
        return ResponseObject.newSuccessResponseObject(enrolService.findSettingsByDoctorId(doctorId));
    }

    @RequestMapping(value = "settings", method = RequestMethod.POST)
    public ResponseObject saveSettings(@Valid @RequestBody ApprenticeSettings apprenticeSettings) {
        String doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(getUserId());
        apprenticeSettings.setDoctorId(doctorId);
        apprenticeSettings.setCreator(getUserId());
        enrolService.saveApprenticeSettings(apprenticeSettings);
        return ResponseObject.newSuccessResponseObject();
    }

    @RequestMapping(value = "apprentices", method = RequestMethod.GET)
    public ResponseObject apprentices() {
        String doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(getUserId());
        return ResponseObject.newSuccessResponseObject(enrolService.findApprenticesByDoctorId(doctorId));
    }
}
