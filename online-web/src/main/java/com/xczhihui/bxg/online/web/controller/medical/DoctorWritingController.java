package com.xczhihui.bxg.online.web.controller.medical;

import com.xczhihui.bxg.online.web.body.doctor.DoctorWritingBody;
import com.xczhihui.bxg.online.web.controller.ftl.AbstractFtlController;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.medical.doctor.model.MedicalDoctorPosts;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorWritingService;
import com.xczhihui.medical.doctor.vo.MedicalWritingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.xczhihui.common.util.bean.ResponseObject.newErrorResponseObject;
import static com.xczhihui.common.util.bean.ResponseObject.newSuccessResponseObject;

/**
 * 医师著作
 *
 * @author hejiwei
 */
@Controller
@RequestMapping("/doctor/writing")
public class DoctorWritingController extends AbstractFtlController {

    @Autowired
    private IMedicalDoctorWritingService medicalDoctorWritingService;
    @Autowired
    private IMedicalDoctorBusinessService medicalDoctorBusinessService;
    @Autowired
    private IMedicalDoctorPostsService medicalDoctorPostsService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject listByDoctorId(@RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        return newSuccessResponseObject(medicalDoctorWritingService.list(page, size, getUserId()));
    }

    @RequestMapping(value = "public", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject list(@RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(required = false) String doctorId) {
        return newSuccessResponseObject(medicalDoctorWritingService.listPublic(page, size, doctorId));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject get(@PathVariable String id, HttpServletRequest request) {
        MedicalWritingVO medicalWritingVO = medicalDoctorWritingService.get(id);
        return newSuccessResponseObject(medicalWritingVO);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject save(@RequestBody @Valid DoctorWritingBody doctorWritingBody) {
        String userId = getUserId();
        String doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(userId);
        medicalDoctorWritingService.save(doctorId, doctorWritingBody.build(userId), userId);
        return newSuccessResponseObject("保存成功");
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseObject update(@PathVariable String id, @RequestBody @Valid DoctorWritingBody doctorWritingBody) {
        String userId = getUserId();
        String doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(userId);
        medicalDoctorWritingService.update(id, doctorId, doctorWritingBody.build(userId));
        return newSuccessResponseObject("更新成功");
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseObject delete(@PathVariable String id) {
        String userId = getUserId();
        String doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(userId);
        medicalDoctorWritingService.delete(id, doctorId);
        return newSuccessResponseObject("删除成功");
    }

    @RequestMapping(value = "{id}/{status}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseObject updateStatus(@PathVariable String id, @PathVariable boolean status) {
        String userId = getUserId();
        String doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(userId);
        if (medicalDoctorWritingService.updateStatus(doctorId, id, status)) {
            if(status){
                MedicalWritingVO oba = medicalDoctorWritingService.get(id);
                //更新动态
                MedicalDoctorPosts mdp = new MedicalDoctorPosts();
                mdp.setType(4);
                mdp.setDoctorId(doctorId);
                mdp.setContent(oba.getTitle());
                mdp.setArticleId(Integer.valueOf(oba.getArticleId()));
                mdp.setArticleContent(oba.getRemark());
                mdp.setArticleImgPath(oba.getImgPath());
                mdp.setArticleTitle(oba.getTitle());
                medicalDoctorPostsService.addMedicalDoctorPosts(mdp);
            }
            return newSuccessResponseObject("操作成功");
        } else {
            return newErrorResponseObject("操作失败");
        }
    }
}
