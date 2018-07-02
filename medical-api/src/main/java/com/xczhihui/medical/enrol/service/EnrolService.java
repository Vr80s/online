package com.xczhihui.medical.enrol.service;

import java.util.List;

import com.xczhihui.medical.enrol.model.MedicalEnrollmentRegulations;
import com.xczhihui.medical.enrol.vo.MedicalEnrollmenRtegulationsCardInfoVO;
import com.xczhihui.medical.enrol.vo.MedicalEntryInformationVO;

/**
 * Description: <br>
 *
 * @author: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time:  2018/5/22 0022-上午 11:19<br>
 */
public interface EnrolService {

    Object getEnrollmentRegulationsList(int page, int size);

    MedicalEnrollmentRegulations getMedicalEnrollmentRegulationsById(int id, String userId);

    MedicalEntryInformationVO getMedicalEntryInformationByUserIdAndERId(int merId, String userId);

    void saveMedicalEntryInformation(MedicalEntryInformationVO medicalEntryInformation);

    MedicalEnrollmenRtegulationsCardInfoVO getMedicalEnrollmentRegulationsCardInfoById(int id, String userId, String returnOpenidUri);

    /**
     * 查询招生简章
     *
     * @param merId
     * @return
     */
    MedicalEnrollmentRegulations findById(int merId);

    /**
     * 根据医师id查询招生简章
     *
     * @param doctorId
     * @return
     */
    List<MedicalEnrollmentRegulations> listByDoctorId(String doctorId);
}
