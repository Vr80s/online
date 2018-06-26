package com.xczhihui.bxg.online.web.controller.medical;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xczhihui.bxg.online.web.body.doctor.DoctorBannerBody;
import com.xczhihui.bxg.online.web.controller.AbstractController;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.medical.doctor.model.DoctorBanner;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBannerService;

/**
 * 医师轮播图
 *
 * @author hejiwei
 */
@RequestMapping("/doctor/banner")
@RestController
public class DoctorBannerController extends AbstractController {

    @Autowired
    private IMedicalDoctorBannerService medicalDoctorBannerService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseObject save(@RequestBody DoctorBannerBody doctorBannerBody) {
        DoctorBanner doctorBanner = doctorBannerBody.build();
        medicalDoctorBannerService.addBanner(doctorBanner, getUserId());
        return ResponseObject.newSuccessResponseObject();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseObject update(@RequestBody DoctorBannerBody doctorBannerBody, @PathVariable int id) {
        DoctorBanner doctorBanner = doctorBannerBody.build();
        medicalDoctorBannerService.updateBanner(doctorBanner, id, getUserId());
        return ResponseObject.newSuccessResponseObject();
    }

    @RequestMapping(value = "{id}/{status}", method = RequestMethod.PUT)
    public ResponseObject updateStatus(@PathVariable int id, @PathVariable boolean status) {
        medicalDoctorBannerService.updateStatus(id, status, getUserId());
        return ResponseObject.newSuccessResponseObject("修改成功");
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseObject list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseObject.newSuccessResponseObject(medicalDoctorBannerService.list(page, pageSize, getUserId()));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseObject get(@PathVariable int id) {
        return ResponseObject.newSuccessResponseObject(medicalDoctorBannerService.get(id, getUserId()));
    }
}
