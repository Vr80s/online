package com.xczhihui.bxg.online.web.controller.medical;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.web.body.MedicalHospitalRecruitBody;
import com.xczhihui.bxg.online.web.controller.AbstractController;
import com.xczhihui.medical.hospital.service.IMedicalHospitalRecruitBusinessService;

@RestController
@RequestMapping(value = "/medical/hospitalRecruit")
public class HospitalRecruitController extends AbstractController {

    @Autowired
    private IMedicalHospitalRecruitBusinessService medicalHospitalRecruitBusinessService;

    /**
     * Description：通过医馆id获取招聘列表
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 3:09 2017/12/10 0010
     **/
    @RequestMapping(value = "/getHospitalRecruitById", method = RequestMethod.GET)
    public ResponseObject getHospitalRecruitById(String hospitalId) {
        return ResponseObject.newSuccessResponseObject(medicalHospitalRecruitBusinessService.selectHospitalRecruitByHospitalId(hospitalId));
    }

    @RequestMapping(value = "/getRecHospitalRecruits", method = RequestMethod.GET)
    public ResponseObject getRecHospitalRecruits() {
        return ResponseObject.newSuccessResponseObject(medicalHospitalRecruitBusinessService.selectRecHospitalRecruit());
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseObject list(@RequestParam String hospitalId, @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseObject.newSuccessResponseObject(medicalHospitalRecruitBusinessService.listByPage(hospitalId, keyword, page, size));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseObject create(@RequestBody @Valid MedicalHospitalRecruitBody medicalHospitalRecruitBody, HttpServletRequest request) {
        medicalHospitalRecruitBusinessService.save(medicalHospitalRecruitBody.build(), getOnlineUser(request).getId());
        return ResponseObject.newSuccessResponseObject("保存成功");
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseObject update(@PathVariable String id, @RequestBody @Valid MedicalHospitalRecruitBody medicalHospitalRecruitBody, HttpServletRequest request) {
        medicalHospitalRecruitBusinessService.update(id, medicalHospitalRecruitBody.build());
        return ResponseObject.newSuccessResponseObject("保存成功");
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseObject get(@PathVariable String id) {
        return ResponseObject.newSuccessResponseObject(medicalHospitalRecruitBusinessService.get(id));
    }

    @RequestMapping(value = "{id}/{status}", method = RequestMethod.PUT)
    public ResponseObject changeStatus(@PathVariable String id, @PathVariable boolean status) {
        medicalHospitalRecruitBusinessService.updateStatus(id, status);
        return ResponseObject.newSuccessResponseObject("状态修改成功");
    }
}
