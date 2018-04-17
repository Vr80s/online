package com.xczhihui.bxg.online.web.controller.medical;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.util.enums.HeadlineType;
import com.xczhihui.bxg.online.web.body.doctor.DoctorArticleBody;
import com.xczhihui.bxg.online.web.controller.ftl.AbstractController;
import com.xczhihui.medical.doctor.model.MedicalDoctor;
import com.xczhihui.medical.doctor.service.IMedicalDoctorArticleService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;

/**
 * 医师专栏与报道
 *
 * @author hejiwei
 */
@RestController
@RequestMapping("doctor/article")
public class DoctorArticleController extends AbstractController {

    @Autowired
    private IMedicalDoctorBusinessService medicalDoctorBusinessService;
    @Autowired
    private IMedicalDoctorArticleService medicalDoctorArticleService;

    @RequestMapping(value = "specialColumn", method = RequestMethod.POST)
    public ResponseObject saveSpecialColumn(@Valid @RequestBody DoctorArticleBody doctorArticleBody, HttpServletRequest request) {
        String userId = getUserId(request);
        String doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(userId);
        String doctorName = medicalDoctorBusinessService.get(userId).getName();
        medicalDoctorArticleService.saveSpecialColumn(doctorId, doctorArticleBody.build(HeadlineType.DJZL, doctorName));
        return ResponseObject.newSuccessResponseObject();
    }

    @RequestMapping(value = "specialColumn/{id}", method = RequestMethod.PUT)
    public ResponseObject updateSpecialColumn(@Valid @RequestBody DoctorArticleBody doctorArticleBody,
                                              @PathVariable String id, HttpServletRequest request) {
        String userId = getUserId(request);
        String doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(userId);

        MedicalDoctor medicalDoctor = medicalDoctorBusinessService.get(doctorId);
        String doctorName = medicalDoctor.getName();
        medicalDoctorArticleService.updateSpecialColumn(doctorId, doctorArticleBody.build(HeadlineType.DJZL, doctorName), id);
        return ResponseObject.newSuccessResponseObject();
    }

    @RequestMapping(value = "specialColumn/{id}", method = RequestMethod.GET)
    public ResponseObject getSpecialColumn(@PathVariable String id) {
        return ResponseObject.newSuccessResponseObject(medicalDoctorArticleService.getSpecialColumn(id));
    }

    @RequestMapping(value = "specialColumn/{id}", method = RequestMethod.DELETE)
    public ResponseObject deleteSpecialColumn(@PathVariable String id) {
        return ResponseObject.newSuccessResponseObject(medicalDoctorArticleService.deleteSpecialColumnById(id));
    }

    @RequestMapping(value = "specialColumn", method = RequestMethod.GET)
    public ResponseObject listSpecialColumn(@RequestParam(defaultValue = "1") int page, HttpServletRequest request) {
        String userId = getUserId(request);
        String doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(userId);
        return ResponseObject.newSuccessResponseObject(medicalDoctorArticleService.listSpecialColumn(page, doctorId));
    }

    @RequestMapping(value = "specialColumn/{id}/{status}", method = RequestMethod.PUT)
    public ResponseObject changeStatus(@PathVariable String id, @PathVariable int status) {
        if (medicalDoctorArticleService.updateSpecialColumnStatus(id, status)) {
            return ResponseObject.newSuccessResponseObject();
        } else {
            return ResponseObject.newErrorResponseObject("更新状态失败");
        }
    }

    @RequestMapping(value = "report", method = RequestMethod.POST)
    public ResponseObject saveReport(@Valid @RequestBody DoctorArticleBody doctorArticleBody, HttpServletRequest request) {
        String userId = getUserId(request);
        String doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(userId);
        String doctorName = medicalDoctorBusinessService.get(doctorId).getName();
        medicalDoctorArticleService.saveReport(doctorId, doctorArticleBody.build(HeadlineType.MYBD, doctorName), doctorArticleBody.getReportDoctor());
        return ResponseObject.newSuccessResponseObject();
    }

    @RequestMapping(value = "report/{id}", method = RequestMethod.PUT)
    public ResponseObject updateReport(@Valid @RequestBody DoctorArticleBody doctorArticleBody,
                                       @PathVariable String id, HttpServletRequest request) {
        String userId = getUserId(request);
        String doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(userId);
        String doctorName = medicalDoctorBusinessService.get(doctorId).getName();
        medicalDoctorArticleService.updateReport(doctorId, doctorArticleBody.build(HeadlineType.MYBD, doctorName), id, doctorArticleBody.getReportDoctor());
        return ResponseObject.newSuccessResponseObject();
    }

    @RequestMapping(value = "report/{id}", method = RequestMethod.GET)
    public ResponseObject getReport(@PathVariable String id) {
        return ResponseObject.newSuccessResponseObject(medicalDoctorArticleService.getReport(id));
    }

    @RequestMapping(value = "report/{id}", method = RequestMethod.DELETE)
    public ResponseObject deleteReport(@PathVariable String id) {
        medicalDoctorArticleService.deleteReportById(id);
        return ResponseObject.newSuccessResponseObject();
    }

    @RequestMapping(value = "report", method = RequestMethod.GET)
    public ResponseObject listReport(@RequestParam(defaultValue = "1") int page, HttpServletRequest request) {
        String userId = getUserId(request);
        String doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(userId);
        return ResponseObject.newSuccessResponseObject(medicalDoctorArticleService.listReport(page, doctorId));
    }

    @RequestMapping(value = "report/{id}/{status}", method = RequestMethod.PUT)
    public ResponseObject changeStatus(@PathVariable int status,
                                       @PathVariable String id, HttpServletRequest request) {
        String userId = getUserId(request);
        String doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(userId);
        if (medicalDoctorArticleService.updateReportStatus(id, status)) {
            return ResponseObject.newSuccessResponseObject();
        } else {
            return ResponseObject.newErrorResponseObject("状态更新失败");
        }
    }
}
