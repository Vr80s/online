package com.xczhihui.medical.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.MedicalHospitalRecruit;

/**
 * 医馆招聘
 *
 * @author hejiwei
 */
public interface HospitalRecruitService {

    /**
     * 上移招聘信息
     *
     * @param id
     */
    void updateSortUp(String id);

    /**
     * 下移招聘信息
     *
     * @param id
     */
    void updateSortDown(String id);

    /**
     * 招聘信息列表
     *
     * @param searchVo
     * @param currentPage
     * @param pageSize
     * @return
     */
    Page<MedicalHospitalRecruit> findMedicalHospitalRecruitPage(
            MedicalHospitalRecruit searchVo, int currentPage, int pageSize);

    /**
     * 新增招聘
     *
     * @param medicalHospitalRecruit
     */
    void addMedicalHospitalRecruit(MedicalHospitalRecruit medicalHospitalRecruit);

    /**
     * 通过id招聘一条招聘信息
     *
     * @param id
     * @return
     */
    MedicalHospitalRecruit findMedicalHospitalRecruitById(String id);

    /**
     * 更新
     *
     * @param medicalHospitalRecruit
     */
    void updateMedicalHospitalRecruit(
            MedicalHospitalRecruit medicalHospitalRecruit);

    /**
     * 更新状态
     *
     * @param id
     */
    void updateRecruitStatus(String id);

    /**
     * 删除
     *
     * @param ids
     */
    void deleteByIds(String[] ids);
}
