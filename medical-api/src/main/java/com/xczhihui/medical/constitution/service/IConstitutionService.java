package com.xczhihui.medical.constitution.service;

import com.xczhihui.medical.constitution.model.MedicalConstitution;
import com.xczhihui.medical.constitution.model.MedicalConstitutionQuestionBank;
import com.xczhihui.medical.constitution.model.MedicalConstitutionQuestionRecordDetails;
import com.xczhihui.medical.constitution.vo.AnalysisResult;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuxin
 * @since 2018-10-15
 */
public interface IConstitutionService {

    List<MedicalConstitutionQuestionBank> getQuestionBank();

    AnalysisResult saveRecord(String userId, String birthday, Integer sex, List<MedicalConstitutionQuestionRecordDetails> medicalQuestionRecordDetailsList);

    List<MedicalConstitution> getConstitution();

    AnalysisResult getRecordById(Integer id);
}
