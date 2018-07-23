package com.xczhihui.bxg.online.web.controller.medical;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.online.web.body.doctor.DoctorBannerBody;
import com.xczhihui.bxg.online.web.controller.AbstractController;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.course.model.Course;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.medical.doctor.enums.DoctorBannerEnum;
import com.xczhihui.medical.doctor.model.DoctorBanner;
import com.xczhihui.medical.doctor.service.IMedicalDoctorArticleService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBannerService;
import com.xczhihui.medical.doctor.vo.OeBxsArticleVO;
import com.xczhihui.medical.enrol.model.MedicalEnrollmentRegulations;
import com.xczhihui.medical.enrol.service.EnrolService;
import com.xczhihui.medical.enrol.vo.MedicalEntryInformationVO;

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
    @Autowired
    private ICourseService courseService;
    @Autowired
    private IMedicalDoctorArticleService medicalDoctorArticleService;
    @Autowired
    private EnrolService enrolService;

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
        Page<DoctorBanner> list = medicalDoctorBannerService.list(page, pageSize, getUserId());
        handleLinkDesc(list);
        return ResponseObject.newSuccessResponseObject(list);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseObject get(@PathVariable int id) {
        return ResponseObject.newSuccessResponseObject(medicalDoctorBannerService.get(id, getUserId()));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseObject delete(@PathVariable int id) {
        medicalDoctorBannerService.delete(id);
        return ResponseObject.newSuccessResponseObject();
    }

    private void handleLinkDesc(Page<DoctorBanner> list) {
        list.getRecords().forEach(doctorBanner -> {
            String linkParam = doctorBanner.getLinkParam();
            int type = doctorBanner.getType();
            if (StringUtils.isNotBlank(linkParam)) {
                String linkDesc = "不跳转";
                DoctorBannerEnum bannerEnum = DoctorBannerEnum.getByType(type);
                if (bannerEnum.equals(DoctorBannerEnum.COURSE_DETAIL) || bannerEnum.equals(DoctorBannerEnum.LIVE_COURSE_DETAIL)) {
                    int paramId = Integer.parseInt(linkParam);
                    Course course = courseService.findSimpleInfoById(paramId);
                    if (course != null) {
                        linkDesc = bannerEnum.getDesc() + ":" + course.getGradeName();
                    }
                } else if (bannerEnum.equals(DoctorBannerEnum.SPECIAL_COLUMN) || bannerEnum.equals(DoctorBannerEnum.DOCTOR_CASE)) {
                    int paramId = Integer.parseInt(linkParam);
                    OeBxsArticleVO oeBxsArticleVO = medicalDoctorArticleService.getSimpleInfo(Integer.parseInt(linkParam));
                    if (oeBxsArticleVO != null) {
                        linkDesc = bannerEnum.getDesc() + ":" + oeBxsArticleVO.getTitle();
                    }
                } else if (bannerEnum.equals(DoctorBannerEnum.APPRENTICE)) {
                    int paramId = Integer.parseInt(linkParam);
                    MedicalEnrollmentRegulations medicalEnrollmentRegulations = enrolService.findById(paramId);
                    if (medicalEnrollmentRegulations != null) {
                        linkDesc = bannerEnum.getDesc() + ":" + medicalEnrollmentRegulations.getTitle();
                    }
                }
                doctorBanner.setLinkDesc(linkDesc);
            }
        });
    }
}
