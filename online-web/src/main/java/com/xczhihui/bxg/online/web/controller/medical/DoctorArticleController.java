package com.xczhihui.bxg.online.web.controller.medical;

import com.xczhihui.bxg.online.web.body.doctor.DoctorArticleBody;
import com.xczhihui.bxg.online.web.controller.ftl.AbstractFtlController;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.util.enums.HeadlineType;
import com.xczhihui.medical.doctor.model.MedicalDoctor;
import com.xczhihui.medical.doctor.service.IMedicalDoctorArticleService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsService;
import com.xczhihui.medical.headline.model.OeBxsArticle;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 医师专栏与报道
 *
 * @author hejiwei
 */
@RestController
@RequestMapping("/doctor/article")
public class DoctorArticleController extends AbstractFtlController {

    @Autowired
    private IMedicalDoctorBusinessService medicalDoctorBusinessService;
    @Autowired
    private IMedicalDoctorArticleService medicalDoctorArticleService;
    @Autowired
    private IMedicalDoctorPostsService medicalDoctorPostsService;

    @RequestMapping(value = "specialColumn", method = RequestMethod.POST)
    public ResponseObject saveSpecialColumn(@Valid @RequestBody DoctorArticleBody doctorArticleBody) {
        String userId = getUserId();
        String doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(userId);
        String doctorName = medicalDoctorBusinessService.get(doctorId).getName();
        OeBxsArticle bxsArticle;
        Integer type = doctorArticleBody.getTypeId();
        if (type == null || type == Integer.parseInt(HeadlineType.DJZL.getCode())) {
            bxsArticle = doctorArticleBody.build(HeadlineType.DJZL, doctorName);
        } else {
            bxsArticle = doctorArticleBody.build(HeadlineType.YA, doctorName);
        }
        bxsArticle.setCreatePerson(userId);
        medicalDoctorArticleService.saveSpecialColumn(doctorId, bxsArticle);
        return ResponseObject.newSuccessResponseObject();
    }

    @RequestMapping(value = "specialColumn/{id}", method = RequestMethod.PUT)
    public ResponseObject updateSpecialColumn(@Valid @RequestBody DoctorArticleBody doctorArticleBody,
                                              @PathVariable String id) {
        String userId = getUserId();
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
        if (medicalDoctorArticleService.deleteSpecialColumnById(id)) {
            return ResponseObject.newSuccessResponseObject("删除成功");
        } else {
            return ResponseObject.newErrorResponseObject("删除失败");
        }
    }

    @RequestMapping(value = "specialColumn", method = RequestMethod.GET)
    public ResponseObject listSpecialColumn(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
                                            @RequestParam(required = false) String keyQuery, @RequestParam(required = false) String type) {
        String userId = getUserId();
        if (StringUtils.isNotBlank(keyQuery)) {
            keyQuery = "%" + keyQuery + "%";
        }
        return ResponseObject.newSuccessResponseObject(medicalDoctorArticleService.listSpecialColumn(page, size, userId, keyQuery, type));
    }

    @RequestMapping(value = "specialColumn/{id}/{status}", method = RequestMethod.PUT)
    public ResponseObject changeStatus(@PathVariable String id, @PathVariable int status) {
        if (medicalDoctorArticleService.updateSpecialColumnStatus(id, status)) {
            if(status==1){
                String userId = getUserId();
                Integer articleId = Integer.valueOf(id);
                //更新动态
                medicalDoctorPostsService.addDoctorPosts(userId,null,articleId,"","",null);
            }
            return ResponseObject.newSuccessResponseObject("操作成功");
        } else {
            return ResponseObject.newErrorResponseObject("更新状态失败");
        }
    }

    @RequestMapping(value = "report", method = RequestMethod.POST)
    public ResponseObject saveReport(@Valid @RequestBody DoctorArticleBody doctorArticleBody) {
        String userId = getUserId();
        String doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(userId);
        OeBxsArticle bxsArticle = doctorArticleBody.build(HeadlineType.MYBD, doctorArticleBody.getAuthor());
        bxsArticle.setCreatePerson(userId);
        medicalDoctorArticleService.saveReport(doctorId, bxsArticle);
        return ResponseObject.newSuccessResponseObject("保存成功");
    }

    @RequestMapping(value = "report/{id}", method = RequestMethod.PUT)
    public ResponseObject updateReport(@Valid @RequestBody DoctorArticleBody doctorArticleBody,
                                       @PathVariable String id) {
        String userId = getUserId();
        String doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(userId);
        if (medicalDoctorArticleService.updateReport(doctorId, doctorArticleBody.build(HeadlineType.MYBD, doctorArticleBody.getAuthor()), id)) {
            return ResponseObject.newSuccessResponseObject("更新成功");
        } else {
            return ResponseObject.newErrorResponseObject("更新失败");
        }
    }

    @RequestMapping(value = "report/{id}", method = RequestMethod.GET)
    public ResponseObject getReport(@PathVariable String id) {
        return ResponseObject.newSuccessResponseObject(medicalDoctorArticleService.getReport(id));
    }

    @RequestMapping(value = "report/{id}", method = RequestMethod.DELETE)
    public ResponseObject deleteReport(@PathVariable String id) {
        if (medicalDoctorArticleService.deleteReportById(id)) {
            return ResponseObject.newSuccessResponseObject("删除成功");
        } else {
            return ResponseObject.newErrorResponseObject("删除失败");
        }
    }

    @RequestMapping(value = "report", method = RequestMethod.GET)
    public ResponseObject listReport(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(required = false) String keyQuery) {
        String userId = getUserId();
        if (StringUtils.isNotBlank(keyQuery)) {
            keyQuery = "%" + keyQuery + "%";
        }
        return ResponseObject.newSuccessResponseObject(medicalDoctorArticleService.listReport(page, size, userId, keyQuery));
    }

    @RequestMapping(value = "report/{id}/{status}", method = RequestMethod.PUT)
    public ResponseObject changeStatus(@PathVariable int status,
                                       @PathVariable String id) {
        String userId = getUserId();
        String doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(userId);
        if (medicalDoctorArticleService.updateReportStatus(id, status)) {
            if(status ==1){
                //更新动态
                medicalDoctorPostsService.addDoctorPosts(userId,null,Integer.valueOf(id),"","",null);
            }
            return ResponseObject.newSuccessResponseObject("操作成功");
        } else {
            return ResponseObject.newErrorResponseObject("操作失败");
        }
    }
}
