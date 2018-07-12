package com.xczhihui.medical.enrol.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.enrol.model.ApprenticeSettings;
import com.xczhihui.medical.enrol.model.MedicalEnrollmentRegulations;
import com.xczhihui.medical.enrol.model.MedicalEntryInformation;
import com.xczhihui.medical.enrol.vo.MedicalEnrollmenRtegulationsCardInfoVO;
import com.xczhihui.medical.enrol.vo.MedicalEntryInformationVO;
import com.xczhihui.medical.headline.vo.SimpleUserVO;

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

    /**
     * 新增招生简章
     *
     * @param medicalEnrollmentRegulations 招生简章
     */
    void save(MedicalEnrollmentRegulations medicalEnrollmentRegulations);

    /**
     * 更新招生简章
     *
     * @param medicalEnrollmentRegulations 招生简章信息
     * @param id                           id
     */
    void updateById(MedicalEnrollmentRegulations medicalEnrollmentRegulations, Integer id);

    /**
     * 查询医师的招生简章列表
     *
     * @param doctorId 医师id
     * @param page     page
     * @param pageSize pageSize
     * @return
     */
    Page<MedicalEnrollmentRegulations> listPageByDoctorId(String doctorId, int page, int pageSize);

    /**
     * 更新招生简章状态
     *
     * @param id       id
     * @param doctorId 医师id
     * @param status   状态
     */
    void updateRegulationsStatus(int id, String doctorId, boolean status);

    /**
     * 查询医师的所有报名的人
     *
     * @param doctorId   doctorId
     * @param type       type
     * @param apprentice apprentice
     * @param page       page
     * @param size       size
     * @return
     */
    Page<MedicalEntryInformationVO> listByDoctorId(String doctorId, Integer type, Integer apprentice, int page, int size);

    /**
     * 审核弟子报名
     *
     * @param id         id
     * @param apprentice 是否通过成为弟子
     */
    void updateStatusEntryInformationById(int id, int apprentice);

    /**
     * 查询用户在线弟子申请
     *
     * @param accountId accountId
     * @param doctorId  doctorId
     * @return
     */
    MedicalEntryInformation findOnlineEntryInformation(String accountId, String doctorId);

    /**
     * 查询医师的收徒设置
     *
     * @param doctorId doctorId
     * @return
     */
    ApprenticeSettings findSettingsByDoctorId(String doctorId);

    /**
     * 更新或新增医师收徒设置
     *
     * @param apprenticeSettings apprenticeSettings
     */
    void saveApprenticeSettings(ApprenticeSettings apprenticeSettings);

    /**
     * 查询医师的全部弟子
     * @param doctorId doctorId
     * @return
     */
    List<SimpleUserVO> findApprenticesByDoctorId(String doctorId);
}
