package com.xczh.consumer.market.controller.medical;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.medical.enrol.service.EnrolService;
import com.xczhihui.medical.enrol.vo.MedicalEntryInformationVO;

/**
 * Description: 师承模块<br>
 *
 * @author: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time:  2018/5/22 0022-下午 2:54<br>
 */
@RestController
@RequestMapping("/xczh/enrol")
public class EnrolController {

    @Autowired
    private EnrolService enrolService;

    @Value("${returnOpenidUri}")
    private String returnOpenidUri;

    @RequestMapping(value = "enrollmentRegulations", method = RequestMethod.GET)
    public ResponseObject getEnrollmentRegulationsList(int page, int size) {
        return ResponseObject.newSuccessResponseObject(enrolService.getEnrollmentRegulationsList(page, size));
    }

    @RequestMapping(value = "enrollmentRegulations/{id}", method = RequestMethod.GET)
    public ResponseObject enrollmentRegulations(@Account String accountId, @PathVariable int id) {
        return ResponseObject.newSuccessResponseObject(enrolService.getMedicalEnrollmentRegulationsById(id, accountId));
    }

    @RequestMapping(value = "enrollmentRegulations/{id}/cardInfo", method = RequestMethod.GET)
    public ResponseObject enrollmentRegulationsCardInfo(@Account String accountId, @PathVariable int id) {
        return ResponseObject.newSuccessResponseObject(enrolService.getMedicalEnrollmentRegulationsCardInfoById(id, accountId, returnOpenidUri));
    }

    @RequestMapping(value = "medicalEntryInformation/{merId}", method = RequestMethod.GET)
    public ResponseObject medicalEntryInformation(@Account String accountId, @PathVariable int merId) {
        return ResponseObject.newSuccessResponseObject(enrolService.getMedicalEntryInformationByUserIdAndERId(merId, accountId));
    }

    @RequestMapping(value = "medicalEntryInformation", method = RequestMethod.POST)
    public ResponseObject saveMedicalEntryInformation(@Account String accountId, MedicalEntryInformationVO medicalEntryInformationVO, HttpServletRequest req) {
        medicalEntryInformationVO.setUserId(accountId);
        enrolService.saveMedicalEntryInformation(medicalEntryInformationVO);
        return ResponseObject.newSuccessResponseObject("报名成功");
    }

}
