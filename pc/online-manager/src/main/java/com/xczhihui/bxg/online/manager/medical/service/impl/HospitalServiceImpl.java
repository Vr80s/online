package com.xczhihui.bxg.online.manager.medical.service.impl;

import com.alipay.api.domain.MedicalHospitalDeptInfo;
import com.alipay.api.domain.MedicalHospitalDoctorInfo;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.po.MedicalHospital;
import com.xczhihui.bxg.online.api.po.MedicalHospitalPicture;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.*;
import com.xczhihui.bxg.online.manager.cloudClass.service.LecturerService;
import com.xczhihui.bxg.online.manager.medical.dao.HospitalDao;
import com.xczhihui.bxg.online.manager.medical.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 *   MedicalHospitalServiceImpl:医馆业务层接口实现类
 *   @author Rongcai Kang
 */
@Service
public class HospitalServiceImpl extends OnlineBaseServiceImpl implements HospitalService {

    @Autowired
    private LecturerService lecturerService;
    @Autowired
    private HospitalDao hospitalDao;
    @Value("${ENV_FLAG}")
	private String envFlag;
    
    @Override
	public Page<MedicalHospital> findMedicalHospitalPage(MedicalHospital medicalHospital, int pageNumber, int pageSize) {
    	Page<MedicalHospital> page = hospitalDao.findMedicalHospitalPage(medicalHospital, pageNumber, pageSize);
    	return page;
	
	}

	@Override
	public List<MedicalHospital> list(String MedicalHospitalType) {
		String sql="select *,grade_name as MedicalHospitalName from oe_MedicalHospital where is_delete=0 and status=1 ";
		Map<String,Object> params=new HashMap<String,Object>();
		if(MedicalHospitalType != null && !"".equals(MedicalHospitalType)){
			sql += " and MedicalHospital_type = :MedicalHospitalType ";
			params.put("MedicalHospitalType", MedicalHospitalType);
		}
		List<MedicalHospital> voList=dao.findEntitiesByJdbc(MedicalHospital.class, sql, params);
		return voList;
	}

	@Override
	public void addMedicalHospital(MedicalHospital medicalHospital) {
		String id = UUID.randomUUID().toString().replace("-","");
		medicalHospital.setId(id);
		medicalHospital.setCreateTime(new Date());
		medicalHospital.setDeleted(false);
		medicalHospital.setStatus(false);
		dao.save(medicalHospital);
	}


	@Override
	public void updateMedicalHospital(MedicalHospital medicalHospital) {
//		MedicalHospital medicalHospital = dao.findOneEntitiyByProperty(MedicalHospital.class, "id", medicalHospitalvo.getId());


		dao.update(medicalHospital);
	}
	
	@Override
	public void updateRecImgPath(MedicalHospital MedicalHospital) {
//		MedicalHospital MedicalHospital = dao.findOneEntitiyByProperty(MedicalHospital.class, "id", MedicalHospital.getId());
//		MedicalHospital.setRecImgPath(MedicalHospital.getRecImgPath());
//		dao.update(MedicalHospital);
	}


	@Override
	public void updateStatus(String id) {
		// TODO Auto-generated method stub
		
		 String hql="from MedicalHospital where 1=1 and deleted=0 and id = ?";
         MedicalHospital MedicalHospital= dao.findByHQLOne(hql, new Object[]{id});
         
         if(MedicalHospital.getStatus()){
        	 MedicalHospital.setStatus(false);
         }else{
        	 MedicalHospital.setStatus(true);
         }
         
         dao.update(MedicalHospital);
	}


	@Override
	public void deleteMedicalHospitalById(String id) {
         hospitalDao.deleteById(id);
	}

	@Override
	public void deletes(String[] ids) {

		for(String id:ids){
			String hqlPre="from MedicalHospital where id = ?";
	        MedicalHospital medicalHospital= dao.findByHQLOne(hqlPre,new Object[] {id});
            if(medicalHospital !=null){
				medicalHospital.setDeleted(true);
                 dao.update(medicalHospital);
            }
        }
		
	}


	@Override
	public void updateMedicalHospitalDetail(String medicalHospitalId, String picture1, String picture2, String picture3, String picture4, String picture5 ) {
		List<MedicalHospitalPicture> mhps = dao.findEntitiesByProperty(MedicalHospitalPicture.class, "hospitalId", medicalHospitalId);
		for (int i = 0; i < mhps.size(); i++) {
			dao.delete(mhps.get(i));
		}
		savePicture(medicalHospitalId,picture1,"1");
		savePicture(medicalHospitalId,picture2,"2");
		savePicture(medicalHospitalId,picture3,"3");
		savePicture(medicalHospitalId,picture4,"4");
		savePicture(medicalHospitalId,picture5,"5");
	}

	public void savePicture(String medicalHospitalId,String picture,String version){
		MedicalHospitalPicture mhp = new MedicalHospitalPicture();
		String id = UUID.randomUUID().toString().replace("-","");
		mhp.setId(id);
		mhp.setHospitalId(medicalHospitalId);
		mhp.setPicture(picture);
		mhp.setVersion(version);
		mhp.setCreateTime(new Date());
		dao.save(mhp);
	}



	@Override
	public Map<String, Object> getMedicalHospitalDetail(String medicalHospitalId) {
		MedicalHospital mh = hospitalDao.findOneEntitiyByProperty(MedicalHospital.class, "id", medicalHospitalId);

		String sql="select * from medical_hospital_picture where hospital_id = '"+medicalHospitalId+"' order by version";

		List<MedicalHospitalPicture> voList=dao.findEntitiesByJdbc(MedicalHospitalPicture.class, sql, null);

//		List<MedicalHospitalPicture> c = dao.findEntitiesByProperty(MedicalHospitalPicture.class, "hospitalId", medicalHospitalId);
			Map<String, Object> retn = new HashMap<String, Object>();
			retn.put("hospital", mh);
			retn.put("picture", voList);
			/*2017-08-14---yuruixin*/
			return retn;
	}

	public List<MedicalHospital> findByName(String name){
		List<MedicalHospital> MedicalHospitals=dao.findEntitiesByProperty(MedicalHospital.class, "name", name);
		return MedicalHospitals;
	}

	@Override
	public MedicalHospital findMedicalHospitalById(String id) {
		return dao.findOneEntitiyByProperty(MedicalHospital.class, "id", id);
	}


}
