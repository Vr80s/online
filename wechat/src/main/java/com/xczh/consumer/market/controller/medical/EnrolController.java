package com.xczh.consumer.market.controller.medical;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.google.common.collect.ImmutableMap;
import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.enums.ApprenticeCheckStatus;
import com.xczhihui.medical.enrol.model.MedicalEntryInformation;
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

    private static final String WAIT_APPLY = "审核结果会以短信形式发送给您,请耐心等待";
    private static final String SUCCESS_PASS_APPLY = "恭喜~您的弟子申请已通过审核！";

    @Autowired
    private EnrolService enrolService;

    @Value("${returnOpenidUri}")
    private String returnOpenidUri;

    @RequestMapping(value = "enrollmentRegulations", method = RequestMethod.GET)
    public ResponseObject getEnrollmentRegulationsList(int page, int size) {
        return ResponseObject.newSuccessResponseObject(enrolService.getEnrollmentRegulationsList(page, size));
    }

    @RequestMapping(value = "enrollmentRegulations/{id}", method = RequestMethod.GET)
    public ResponseObject enrollmentRegulations(@Account(optional = true) Optional<String> accountIdOpt, @PathVariable int id) {
        String userId = accountIdOpt.isPresent() ? accountIdOpt.get() : null;
        return ResponseObject.newSuccessResponseObject(enrolService.getMedicalEnrollmentRegulationsById(id, userId));
    }

    @RequestMapping(value = "enrollmentRegulations/{id}/cardInfo", method = RequestMethod.GET)
    public ResponseObject enrollmentRegulationsCardInfo(@Account String accountId, @PathVariable int id) {
        return ResponseObject.newSuccessResponseObject(enrolService.getMedicalEnrollmentRegulationsCardInfoById(id, accountId, returnOpenidUri));
    }

    @RequestMapping(value = "medicalEntryInformation/{merId}", method = RequestMethod.GET)
    public ResponseObject medicalEntryInformation(@Account String accountId, @PathVariable int merId) {
        return ResponseObject.newSuccessResponseObject(enrolService.getMedicalEntryInformationByUserIdAndERId(merId, accountId));
    }

    @RequestMapping(value = "medicalEntryInformation/online", method = RequestMethod.GET)
    public ResponseObject onlineMedicalEntryInformation(@Account String accountId, @RequestParam String doctorId) {
        MedicalEntryInformation onlineEntryInformation = enrolService.findOnlineEntryInformation(accountId, doctorId);
        if (onlineEntryInformation == null) {
            return ResponseObject.newErrorResponseObject("您还未提交弟子申请");
        }
        String message = WAIT_APPLY;
        if (onlineEntryInformation.getApprentice() == 1) {
            message = SUCCESS_PASS_APPLY;
        }
        return ResponseObject.newSuccessResponseObject(ImmutableMap.of("message", message, "entryInformation", onlineEntryInformation));
    }

    @RequestMapping(value = "medicalEntryInformation", method = RequestMethod.POST)
    public ResponseObject saveMedicalEntryInformation(@Account String accountId, MedicalEntryInformationVO medicalEntryInformationVO, HttpServletRequest req) {
        medicalEntryInformationVO.setUserId(accountId);
        enrolService.saveMedicalEntryInformation(medicalEntryInformationVO);
        return ResponseObject.newSuccessResponseObject("报名成功");
    }

    @RequestMapping(value = "checkAuth", method = RequestMethod.GET)
    public ResponseObject checkApprenticeAuth(@Account String accountId, @RequestParam String doctorId, @RequestParam(value = "courseId", required = false) Integer courseId) {
        boolean auth = false;
        int type = ApprenticeCheckStatus.DEFAULT.getVal();
        boolean apprentice = enrolService.isApprentice(doctorId, accountId);
        //师承直播的校验
        if (courseId != null) {
            auth = enrolService.checkAuthTeachingCourse(accountId, courseId);
        } else {
            auth = apprentice;
        }
        if (!auth) {
            if (apprentice) {
                type = ApprenticeCheckStatus.APPRENTICE.getVal();
            } else if (enrolService.apprenticeApplying(doctorId, accountId)) {
                type = ApprenticeCheckStatus.APPLYING.getVal();
            }
        }
        return ResponseObject.newSuccessResponseObject(ImmutableMap.of("auth", auth, "type", type));
    }
}
