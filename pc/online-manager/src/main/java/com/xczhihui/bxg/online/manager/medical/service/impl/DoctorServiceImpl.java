package com.xczhihui.bxg.online.manager.medical.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.po.MedicalDoctor;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.manager.medical.dao.DoctorDao;
import com.xczhihui.bxg.online.manager.medical.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *   MedicalDoctorServiceImpl:医师业务层接口实现类
 *   @author Rongcai Kang
 */
@Service
public class DoctorServiceImpl extends OnlineBaseServiceImpl implements DoctorService {

    @Autowired
    private DoctorDao DoctorDao;
    @Value("${ENV_FLAG}")
	private String envFlag;
    
    @Override
	public Page<MedicalDoctor> findMedicalDoctorPage(MedicalDoctor medicalDoctor, int pageNumber, int pageSize) {
    	Page<MedicalDoctor> page = DoctorDao.findMedicalDoctorPage(medicalDoctor, pageNumber, pageSize);
    	return page;
	
	}

	@Override
	public void addMedicalDoctor(MedicalDoctor medicalDoctor) {
		String id = UUID.randomUUID().toString().replace("-","");
		medicalDoctor.setId(id);
		medicalDoctor.setCreateTime(new Date());
		medicalDoctor.setDeleted(false);
		medicalDoctor.setStatus(false);
		dao.save(medicalDoctor);
	}


	@Override
	public void updateMedicalDoctor(MedicalDoctor medicalDoctor) {
//		MedicalDoctor medicalDoctor = dao.findOneEntitiyByProperty(MedicalDoctor.class, "id", medicalDoctorvo.getId());


		dao.update(medicalDoctor);
	}
	
	@Override
	public void updateRecImgPath(MedicalDoctor MedicalDoctor) {
//		MedicalDoctor MedicalDoctor = dao.findOneEntitiyByProperty(MedicalDoctor.class, "id", MedicalDoctor.getId());
//		MedicalDoctor.setRecImgPath(MedicalDoctor.getRecImgPath());
//		dao.update(MedicalDoctor);
	}


	@Override
	public void updateStatus(String id) {
		// TODO Auto-generated method stub
		
		 String hql="from MedicalDoctor where 1=1 and deleted=0 and id = ?";
         MedicalDoctor MedicalDoctor= dao.findByHQLOne(hql, new Object[]{id});
         
         if(MedicalDoctor.getStatus()){
        	 MedicalDoctor.setStatus(false);
         }else{
        	 MedicalDoctor.setStatus(true);
         }
         
         dao.update(MedicalDoctor);
	}


	@Override
	public void deleteMedicalDoctorById(String id) {
         DoctorDao.deleteById(id);
	}

	@Override
	public void deletes(String[] ids) {

		for(String id:ids){
			String hqlPre="from MedicalDoctor where id = ?";
	        MedicalDoctor medicalDoctor= dao.findByHQLOne(hqlPre,new Object[] {id});
            if(medicalDoctor !=null){
				medicalDoctor.setDeleted(true);
                 dao.update(medicalDoctor);
            }
        }
		
	}


	@Override
	public void updateMedicalDoctorDetail(String medicalDoctorId, String picture1, String picture2, String picture3, String picture4, String picture5 ) {
//		List<MedicalDoctorPicture> mhps = dao.findEntitiesByProperty(MedicalDoctorPicture.class, "DoctorId", medicalDoctorId);
//		for (int i = 0; i < mhps.size(); i++) {
//			dao.delete(mhps.get(i));
//		}
//		savePicture(medicalDoctorId,picture1,"1");
//		savePicture(medicalDoctorId,picture2,"2");
//		savePicture(medicalDoctorId,picture3,"3");
//		savePicture(medicalDoctorId,picture4,"4");
//		savePicture(medicalDoctorId,picture5,"5");
	}

	public void savePicture(String medicalDoctorId,String picture,String version){
//		MedicalDoctorPicture mhp = new MedicalDoctorPicture();
//		String id = UUID.randomUUID().toString().replace("-","");
//		mhp.setId(id);
//		mhp.setDoctorId(medicalDoctorId);
//		mhp.setPicture(picture);
//		mhp.setVersion(version);
//		mhp.setCreateTime(new Date());
//		dao.save(mhp);
	}



	@Override
	public Map<String, Object> getMedicalDoctorDetail(String medicalDoctorId) {
//		MedicalDoctor mh = DoctorDao.findOneEntitiyByProperty(MedicalDoctor.class, "id", medicalDoctorId);
//
//		String sql="select * from medical_Doctor_picture where Doctor_id = '"+medicalDoctorId+"' order by version";
//
//		List<MedicalDoctorPicture> voList=dao.findEntitiesByJdbc(MedicalDoctorPicture.class, sql, null);

//		List<MedicalDoctorPicture> c = dao.findEntitiesByProperty(MedicalDoctorPicture.class, "DoctorId", medicalDoctorId);
			Map<String, Object> retn = new HashMap<String, Object>();
//			retn.put("Doctor", mh);
//			retn.put("picture", voList);
			/*2017-08-14---yuruixin*/
			return retn;
	}

	public List<MedicalDoctor> findByName(String name){
		List<MedicalDoctor> MedicalDoctors=dao.findEntitiesByProperty(MedicalDoctor.class, "name", name);
		return MedicalDoctors;
	}

	@Override
	public MedicalDoctor findMedicalDoctorById(String id) {
		return dao.findOneEntitiyByProperty(MedicalDoctor.class, "id", id);
	}


}
