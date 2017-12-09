package com.xczhihui.bxg.online.manager.medical.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.po.MedicalHospital;

import java.util.List;
import java.util.Map;

public interface HospitalService {
	

	public List<MedicalHospital> list(String MedicalHospitalType);
	/**
	 * 新增课程
	 *
	 *@return void
	 */
	public void addMedicalHospital(MedicalHospital MedicalHospital);

	/**
	 * 修改课程
	 *
	 *@return void
	 */
	public void updateMedicalHospital(MedicalHospital MedicalHospital);

	/**
	 * 修改课程
	 *
	 *@return void
	 */
	public void updateRecImgPath(MedicalHospital MedicalHospital);

	/**
	 * 根据条件分页获取课程信息。
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
	 * 增加课程详情
	 */
	public void updateMedicalHospitalDetail(String medicalHospitalId, String picture1, String picture2, String picture3, String picture4, String picture5 );
	/**
	 * 获得课程详情
	 * @param MedicalHospitalId
	 * @return
	 */
	public Map<String,Object> getMedicalHospitalDetail(String MedicalHospitalId);
	

	/**
	 * 根据名字查找课程
	 * @param String name
	 */
	public List<MedicalHospital> findByName(String name);

	MedicalHospital findMedicalHospitalById(String id);

}
