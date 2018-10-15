package com.xczh.consumer.market.controller.medical;

import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.medical.constitution.model.MedicalConstitutionQuestionRecordDetails;
import com.xczhihui.medical.constitution.service.IConstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/xczh/constitution")
public class ConstitutionController {

    @Autowired
    private IConstitutionService constitutionService;

    @Value("${returnOpenidUri}")
    private String returnOpenidUri;

    @RequestMapping(value = "questionBank", method = RequestMethod.GET)
    public ResponseObject getQuestionBank() {
        return ResponseObject.newSuccessResponseObject(constitutionService.getQuestionBank());
    }

    @RequestMapping(value = "record/{sex}/{birthday}", method = RequestMethod.POST)
    public ResponseObject saveRecord(@Account String userId, @PathVariable String birthday, @PathVariable Integer sex, @RequestBody List<MedicalConstitutionQuestionRecordDetails> recordDetailsList) {
        return ResponseObject.newSuccessResponseObject(constitutionService.saveRecord(userId, birthday, sex, recordDetailsList));
    }

}
