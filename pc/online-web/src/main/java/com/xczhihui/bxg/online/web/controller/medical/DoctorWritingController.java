package com.xczhihui.bxg.online.web.controller.medical;

import static com.xczhihui.bxg.common.util.bean.ResponseObject.newErrorResponseObject;
import static com.xczhihui.bxg.common.util.bean.ResponseObject.newSuccessResponseObject;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.web.body.doctor.DoctorWritingBody;
import com.xczhihui.bxg.online.web.controller.ftl.AbstractController;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorWritingService;
import com.xczhihui.medical.doctor.vo.MedicalWritingVO;

/**
 * 医师著作
 *
 * @author hejiwei
 */
@Controller
@RequestMapping("doctor/writing")
public class DoctorWritingController extends AbstractController {

    @Autowired
    private IMedicalDoctorWritingService medicalDoctorWritingService;
    @Autowired
    private IMedicalDoctorBusinessService medicalDoctorBusinessService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject listByDoctorId(@RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(required = false) String doctorId, HttpServletRequest request) {
        //不传医师id时，查询自己的著作列表
        if (StringUtils.isBlank(doctorId)) {
            doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(getUserId(request));
        }
        return newSuccessResponseObject(medicalDoctorWritingService.list(page, size, doctorId));
    }

    @RequestMapping(value = "public", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject list(@RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "10") int size) {
        return newSuccessResponseObject(medicalDoctorWritingService.list(page, size));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject get(@PathVariable String id, HttpServletRequest request) {
        MedicalWritingVO medicalWritingVO = medicalDoctorWritingService.get(id);
        return newSuccessResponseObject(medicalWritingVO);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject save(@RequestBody @Valid DoctorWritingBody doctorWritingBody, HttpServletRequest request) {
        String userId = getUserId(request);
        String doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(userId);
        medicalDoctorWritingService.save(doctorId, doctorWritingBody.build(userId), userId);
        return newSuccessResponseObject();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseObject update(@PathVariable String id, @RequestBody @Valid DoctorWritingBody doctorWritingBody, HttpServletRequest request) {
        String userId = getUserId(request);
        String doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(userId);
        medicalDoctorWritingService.update(id, doctorId, doctorWritingBody.build(userId));
        return newSuccessResponseObject();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseObject delete(@PathVariable String id, HttpServletRequest request) {
        String userId = getUserId(request);
        String doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(userId);
        medicalDoctorWritingService.delete(id, doctorId);
        return newSuccessResponseObject();
    }

    @RequestMapping(value = "{id}/{status}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseObject updateStatus(@PathVariable String id, @PathVariable boolean status, HttpServletRequest request) {
        String userId = getUserId(request);
        String doctorId = medicalDoctorBusinessService.getDoctorIdByUserId(userId);
        if (medicalDoctorWritingService.updateStatus(doctorId, id, status)) {
            return newSuccessResponseObject();
        } else {
            return newErrorResponseObject("状态修改失败");
        }
    }
}
