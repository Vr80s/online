package com.xczhihui.medical.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.MedicalDoctor;
import com.xczhihui.bxg.online.common.domain.MedicalDoctorAuthenticationInformation;
import com.xczhihui.bxg.online.common.domain.MedicalHospital;

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
	 * @return
	 */
    public void deleteMedicalDoctorById(String id);

    /**
	 * 删除
	 * @return
	 */
     public void deletes(String[] ids);
  
	/**
	 * 
	 * Description：增加医馆信息
	 * @param medicalDoctorId
	 * @param picture1  真实头像
	 * @param picture2 职称证明
	 * @param picture3 身份证正面
	 * @param picture4  身份证反面
	 * @param picture5 医师资格证
	 * @param picture6 职业医师证
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	public void updateMedicalDoctorDetail(String medicalDoctorId,String authenticationInformationId,
			String picture1, String picture2, String picture3, String picture4, String picture5,String picture6);
	/**
	 * 获得医师详情
	 * @param MedicalDoctorId
	 * @return
	 */
	public Map<String,Object> getMedicalDoctorDetail(String MedicalDoctorId);
	

	/**
	 * 根据名字查找医师
	 */
	public List<MedicalDoctor> findByName(String name);

	MedicalDoctor findMedicalDoctorById(String id);

	public MedicalDoctorAuthenticationInformation mdaiDetail(String mdaiId);

	void updateMedicalHospitalDoctorDetail(String doctorId, String hospitalId);

	List<MedicalHospital> getMedicalHospital(String doctorId);

    Page<MedicalDoctor> findRecMedicalDoctorPage(MedicalDoctor searchVo, int currentPage, int pageSize);

    Integer updateRec(String[] ids, int isRec);

	void updateSortUpRec(String id);

	void updateSortDownRec(String id);

    List<MedicalDoctor> findAllDoctor(String id);

	void addDoctorAuthorArticle(String id, String[] doctorId);

    List<MedicalDoctor> allListForReport(String articleId);

	void addDoctorReport(String id, String[] doctorId);

	public List<MedicalDoctor> getMedicalDoctor(String writingsId);

	List<MedicalDoctor> getAllMedicalDoctorList();
}
