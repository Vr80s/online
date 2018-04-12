package com.xczhihui.bxg.online.web.controller.medical;

import static com.xczhihui.bxg.common.util.bean.ResponseObject.newErrorResponseObject;
import static com.xczhihui.bxg.common.util.bean.ResponseObject.newSuccessResponseObject;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.web.controller.ftl.AbstractController;
import com.xczhihui.medical.hospital.model.MedicalHospitalAnnouncement;
import com.xczhihui.medical.hospital.service.IMedicalHospitalAnnouncementService;
import com.xczhihui.medical.hospital.service.IMedicalHospitalBusinessService;

/**
 * 医馆公告
 *
 * @author hejiwei
 */
@RequestMapping("hospital/{hospitalId}/announcement")
@Controller
public class HospitalAnnouncementController extends AbstractController {

    @Autowired
    private IMedicalHospitalAnnouncementService medicalHospitalAnnouncementService;
    @Autowired
    private IMedicalHospitalBusinessService medicalHospitalBusinessService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject save(@Length(max = 100) @RequestParam String content,
                               @PathVariable String hospitalId, HttpServletRequest request) {
        String userId = getUserId(request);
        if (!medicalHospitalBusinessService.check(userId, hospitalId)) {
            return newErrorResponseObject("非法参数");
        } else {
            MedicalHospitalAnnouncement medicalHospitalAnnouncement = new MedicalHospitalAnnouncement(hospitalId, content);
            medicalHospitalAnnouncement.setCreatePerson(userId);
            medicalHospitalAnnouncementService.save(medicalHospitalAnnouncement);
            return newSuccessResponseObject();
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject list(@RequestParam(defaultValue = "1") int page,
                               @PathVariable String hospitalId, HttpServletRequest request) {
        if (!medicalHospitalBusinessService.check(getUserId(request), hospitalId)) {
            return newErrorResponseObject("非法参数");
        } else {
            return newSuccessResponseObject(medicalHospitalAnnouncementService.list(page, hospitalId));
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseObject delete(@PathVariable String id, @PathVariable String hospitalId, HttpServletRequest request) {
        if (!medicalHospitalBusinessService.check(getUserId(request), hospitalId)) {
            return newErrorResponseObject("非法参数");
        } else {
            if (!medicalHospitalAnnouncementService.deleteById(id, hospitalId)) {
                return newErrorResponseObject("删除失败!");
            } else {
                return newSuccessResponseObject();
            }
        }
    }
}
