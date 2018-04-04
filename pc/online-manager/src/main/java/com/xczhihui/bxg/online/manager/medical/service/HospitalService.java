package com.xczhihui.bxg.online.manager.medical.service;

import java.util.List;
import java.util.Map;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.MedicalHospital;

public interface HospitalService {

    /**
     * 新增医馆
     *
     * @return void
     */
    void addMedicalHospital(MedicalHospital MedicalHospital);

    /**
     * 修改医馆
     *
     * @return void
     */
    void updateMedicalHospital(MedicalHospital MedicalHospital);

    /**
     * 根据条件分页获取医馆信息。
     *
     * @return
     */
    Page<MedicalHospital> findMedicalHospitalPage(MedicalHospital MedicalHospital, int pageNumber, int pageSize);

    /**
     * 修改状态(禁用or启用)
     *
     * @return
     */
    void updateStatus(String id);

    /**
     * 逻辑删除
     *
     * @param String id
     * @return
     */
    void deleteMedicalHospitalById(String id);

    /**
     * 删除
     *
     * @param String id
     * @return
     */
    void deletes(String[] ids);

    /**
     * 增加医馆详情
     */
    void updateMedicalHospitalDetail(String medicalHospitalId, String picture1, String picture2, String picture3, String picture4, String picture5, String picture6, String picture7, String picture8, String picture9);

    /**
     * 获得医馆详情
     *
     * @param MedicalHospitalId
     * @return
     */
    Map<String, Object> getMedicalHospitalDetail(String MedicalHospitalId);


    /**
     * 根据名字查找医馆
     *
     * @param String name
     */
    List<MedicalHospital> findByName(String name);

    MedicalHospital findMedicalHospitalById(String id);

    boolean updateRec(String[] ids, int isRec);

    Page<MedicalHospital> findRecMedicalHospitalPage(MedicalHospital searchVo, int currentPage, int pageSize);

    void updateSortUpRec(String id);

    void updateSortDownRec(String id);
}
