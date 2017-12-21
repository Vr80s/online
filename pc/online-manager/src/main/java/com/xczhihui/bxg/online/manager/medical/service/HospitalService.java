package com.xczhihui.bxg.online.manager.medical.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.MedicalHospital;
import com.xczhihui.bxg.online.common.domain.MedicalHospitalRecruit;

import java.util.List;
import java.util.Map;

public interface HospitalService {
	

	/**
	 * 新增医馆
	 *
	 *@return void
	 */
	public void addMedicalHospital(MedicalHospital MedicalHospital);

	/**
	 * 修改医馆
	 *
	 *@return void
	 */
	public void updateMedicalHospital(MedicalHospital MedicalHospital);

	/**
	 * 修改医馆
	 *
	 *@return void
	 */
	public void updateRecImgPath(MedicalHospital MedicalHospital);

	/**
	 * 根据条件分页获取医馆信息。
	 *
	 * @return
	 */
    public Page<MedicalHospital> findMedicalHospitalPage(MedicalHospital MedicalHospital, int pageNumber, int pageSize);

    /**
	 * 修改状态(禁用or启用)
	 * @return
	 */
    public void updateStatus(String id);

    /**
	 * 逻辑删除
	 * @param String id
	 * @return
	 */
    public void deleteMedicalHospitalById(String id);

    /**
	 * 删除
	 * @param String id
	 * @return
	 */
     public void deletes(String[] ids);
  
	/**
	 * 增加医馆详情
	 */
	public void updateMedicalHospitalDetail(String medicalHospitalId, String picture1, String picture2, String picture3, String picture4, String picture5, String picture6, String picture7, String picture8, String picture9);
	/**
	 * 获得医馆详情
	 * @param MedicalHospitalId
	 * @return
	 */
	public Map<String,Object> getMedicalHospitalDetail(String MedicalHospitalId);
	

	/**
	 * 根据名字查找医馆
	 * @param String name
	 */
	public List<MedicalHospital> findByName(String name);

	MedicalHospital findMedicalHospitalById(String id);

    boolean updateRec(String[] ids, int isRec);

	Page<MedicalHospital> findRecMedicalHospitalPage(MedicalHospital searchVo, int currentPage, int pageSize);

	void updateSortUpRec(String id);

	void updateSortDownRec(String id);

    Page<MedicalHospitalRecruit> findMedicalHospitalRecruitPage(MedicalHospitalRecruit searchVo, int currentPage, int pageSize);

    void addMedicalHospitalRecruit(MedicalHospitalRecruit medicalHospitalRecruit);

	MedicalHospitalRecruit findMedicalHospitalRecruitById(String id);

	void updateMedicalHospitalRecruit(MedicalHospitalRecruit old);

    void updateRecruitStatus(String id);

	void deletesRecruit(String[] ids);

	void updateSortDown(String id);

	void updateSortUp(String id);
}
