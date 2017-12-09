package com.xczhihui.bxg.online.manager.medical.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.po.MedicalDoctor;

import java.util.List;
import java.util.Map;

public interface DoctorService {
	

	/**
	 * 新增医师
	 *
	 *@return void
	 */
	public void addMedicalDoctor(MedicalDoctor MedicalDoctor);

	/**
	 * 修改医师
	 *
	 *@return void
	 */
	public void updateMedicalDoctor(MedicalDoctor MedicalDoctor);

	/**
	 * 修改医师
	 *
	 *@return void
	 */
	public void updateRecImgPath(MedicalDoctor MedicalDoctor);

	/**
	 * 根据条件分页获取医师信息。
	 *
	 * @return
	 */
    public Page<MedicalDoctor> findMedicalDoctorPage(MedicalDoctor MedicalDoctor, int pageNumber, int pageSize);

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
    public void deleteMedicalDoctorById(String id);

    /**
	 * 删除
	 * @param String id
	 * @return
	 */
     public void deletes(String[] ids);
  
	/**
	 * 增加医师详情
	 */
	public void updateMedicalDoctorDetail(String medicalDoctorId, String picture1, String picture2, String picture3, String picture4, String picture5);
	/**
	 * 获得医师详情
	 * @param MedicalDoctorId
	 * @return
	 */
	public Map<String,Object> getMedicalDoctorDetail(String MedicalDoctorId);
	

	/**
	 * 根据名字查找医师
	 * @param String name
	 */
	public List<MedicalDoctor> findByName(String name);

	MedicalDoctor findMedicalDoctorById(String id);

}
