package com.xczhihui.bxg.online.manager.medical.service.impl;

import java.util.*;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.xczhihui.bxg.online.common.domain.*;

import com.xczhihui.bxg.online.manager.medical.enums.MedicalExceptionEnum;
import com.xczhihui.bxg.online.manager.medical.exception.MedicalException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.web.auth.UserHolder;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.manager.medical.dao.DoctorDao;
import com.xczhihui.bxg.online.manager.medical.service.DoctorService;

/**
 *   MedicalDoctorServiceImpl:医师业务层接口实现类
 *   @author Rongcai Kang
 */
@Service
public class DoctorServiceImpl extends OnlineBaseServiceImpl implements DoctorService {

    @Autowired
    private DoctorDao doctorDao;
    @Value("${ENV_FLAG}")
	private String envFlag;
    
    @Override
	public Page<MedicalDoctor> findMedicalDoctorPage(MedicalDoctor medicalDoctor, int pageNumber, int pageSize) {
    	Page<MedicalDoctor> page = doctorDao.findMedicalDoctorPage(medicalDoctor, pageNumber, pageSize);
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
		doctorDao.deleteById(id);
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
	public void updateMedicalDoctorDetail(String medicalDoctorId,String authenticationInformationId,String picture1, String picture2, 
			String picture3, String picture4, String picture5,String picture6) {
		
		//picture1 真实头像	picture2 职称证明	picture3 身份证正面	
		//picture4 身份证反面	picture5 医师资格证	picture6 职业医师证

		// 根据医师id获取医师详情
		MedicalDoctor doctor = dao.findOneEntitiyByProperty(MedicalDoctor.class, "id", medicalDoctorId);
		if(doctor == null){
			throw new MedicalException(MedicalExceptionEnum.DOCTOR_NOT_EXIT);
		}else{
			if(StringUtils.isNotBlank(doctor.getSourceId())){
				throw new MedicalException(MedicalExceptionEnum.MUST_NOT_HANDLE);
			}
		}

		MedicalDoctorAuthenticationInformation mdai = new MedicalDoctorAuthenticationInformation();
		BxgUser u = UserHolder.getCurrentUser();
		if(!StringUtils.isNotBlank(authenticationInformationId)){
			String id = UUID.randomUUID().toString().replace("-","");
			
			mdai.setId(id);
			mdai.setCreateTime(new Date());
			mdai.setDeleted(false);
			mdai.setStatus(false);

			mdai.setCreatePerson(u.getId());
			mdai.setCreateTime(new Date());
		}else{
			
			mdai = dao.findOneEntitiyByProperty(MedicalDoctorAuthenticationInformation.class, "id", authenticationInformationId);
			mdai.setUpdatePerson(u.getId());
			mdai.setUpdateTime(new Date());
		}
		
		/**
		 * 保存用户的照片啦
		 */
		mdai.setHeadPortrait(picture1);
		mdai.setTitleProve(picture2);
		mdai.setCardPositive(picture3);
		mdai.setCardNegative(picture4);
		mdai.setQualificationCertificate(picture5);
		mdai.setProfessionalCertificate(picture6);
		
		if(authenticationInformationId == null){
			dao.save(mdai);
			
			MedicalDoctor md = findMedicalDoctorById(medicalDoctorId);
			md.setAuthenticationInformationId(mdai.getId());
		}else{
			 dao.update(mdai);
		}
		
	}

	@Override
	public Map<String, Object> getMedicalDoctorDetail(String medicalDoctorId) {
			Map<String, Object> retn = new HashMap<String, Object>();
			return retn;
	}

	@Override
    public List<MedicalDoctor> findByName(String name){
		List<MedicalDoctor> MedicalDoctors=dao.findEntitiesByProperty(MedicalDoctor.class, "name", name);
		return MedicalDoctors;
	}

	@Override
	public MedicalDoctor findMedicalDoctorById(String id) {
		return dao.findOneEntitiyByProperty(MedicalDoctor.class, "id", id);
	}

	@Override
	public MedicalDoctorAuthenticationInformation mdaiDetail(String mdaiId) {
		return dao.findOneEntitiyByProperty(MedicalDoctorAuthenticationInformation.class, "id", mdaiId);
	}

	/**
	 * 更新医师所在的医馆
	 * @param doctorId 医师id
	 * @param hospitalId 医馆id
	 */
	@Override
	public void updateMedicalHospitalDoctorDetail(String doctorId, String hospitalId) {

		// 根据医师id获取医师详情
		List<MedicalDoctor> doctors = dao.findEntitiesByProperty(MedicalDoctor.class, "id", doctorId);
		if(CollectionUtils.isEmpty(doctors)){
			throw new MedicalException(MedicalExceptionEnum.DOCTOR_NOT_EXIT);
		}else{
			if(StringUtils.isNotBlank(doctors.get(0).getSourceId())){
				throw new MedicalException(MedicalExceptionEnum.MUST_NOT_HANDLE);
			}
		}

		//删除之前关系表
		List<MedicalHospitalDoctor> mhps = dao.findEntitiesByProperty(MedicalHospitalDoctor.class, "doctorId", doctorId);
		for (int i = 0; i < mhps.size(); i++) {
			dao.delete(mhps.get(i));
		}

		if(hospitalId != null && !"".equals(hospitalId)){
			//添加新的关系表
			MedicalHospitalDoctor medicalHospitalDoctor = new MedicalHospitalDoctor();

			String id = UUID.randomUUID().toString().replace("-","");
			medicalHospitalDoctor.setId(id);
			medicalHospitalDoctor.setDoctorId(doctorId);
			medicalHospitalDoctor.setHospitalId(hospitalId);
			medicalHospitalDoctor.setCreateTime(new Date());
			dao.save(medicalHospitalDoctor);
		}
	}

	/**
	 * 获取医师所在的医馆
	 * @param doctorId 医师id
	 */
	@Override
	public List<MedicalHospital> getMedicalHospital(String doctorId) {
		List<MedicalHospital> list = getMedicalHospitals();

		List<MedicalHospitalDoctor> mhds = dao.findEntitiesByProperty(MedicalHospitalDoctor.class, "doctorId", doctorId);

		for (int i = 0; i < mhds.size(); i++) {
			for (int j = 0; j < list.size(); j++) {
				if(mhds.get(i).getHospitalId().equals(list.get(j).getId()) &&
						mhds.get(i).getDeleted().equals("0")){
					list.get(j).setDependence(true);
				}
			}
		}
		return list;
	}

	@Override
	public Integer updateRec(String[] ids,int isRecommend) {
		
		Integer code = 1000; //code：1000  操作成功!   code：1001  最多设置十个推荐医师!   code：1002   你所选中的被推荐人已是被推荐!
		
		// TODO Auto-generated method stub
		List<String> ids2 = new ArrayList();
		if(isRecommend == 1){//如果是要推荐 那么就验证 推荐数量是否大于4
			//校验是否被引用
			String hqlPre="from MedicalDoctor where deleted=0 and recommend = 1";
			List<MedicalDoctor> list= dao.findByHQL(hqlPre);
			
			int y = 0;
			if(list.size() > 0){//只有原来大于0才执行
				for(int i = 0;i<ids.length;i++){
					int j = 0;
					Iterator<MedicalDoctor> iterator = list.iterator();
					while(iterator.hasNext()){//剔除本次推荐的与已经推荐的重复的
						MedicalDoctor medicalDoctor = iterator.next();
						if(medicalDoctor.getId().equals(ids[i])){//如果存在就把他剔除掉从list中
							j =1;
							y++;
						}
					}
					if(j == 0){
						ids2.add(ids[i]);
					}
				}
			}else{
				for(int i=0;i<ids.length;i++)
				{
					ids2.add(ids[i]);
				}
			}
			//已经存在的数量 +  即将添加的数量
			/*if((list.size()+ids2.size()) > 10){
				return 1001;
			}*/
			
			//如果本次推荐的人已经全部都是推荐人了，提示你所选中的被推荐人已是推荐人
			if(y == ids.length){
				return 1002;
			}
			
		}else{//如果是取消推荐
			for(int i=0;i<ids.length;i++)
			{
				ids2.add(ids[i]);
			}
		}

		String sql="select ifnull(min(recommend_sort),0) from medical_doctor where  deleted=0 and recommend = 1";
		int i = dao.queryForInt(sql,null);//最小的排序

		for(String id:ids2){
			if(id == "" || id == null){
				continue;
			}
			i = i -1;
			String hqlPre="from MedicalDoctor where  deleted = 0 and id = ?";
			MedicalDoctor medicalDoctor= dao.findByHQLOne(hqlPre,new Object[] {id});
			if(medicalDoctor !=null){
				medicalDoctor.setRecommend(isRecommend==1);
				medicalDoctor.setRecommendSort(i);
				dao.update(medicalDoctor);
			}
		}
		return 1000;
	}
	@Override
	public Page<MedicalDoctor> findRecMedicalDoctorPage(MedicalDoctor medicalDoctor, int currentPage, int pageSize) {
		Page<MedicalDoctor> page = doctorDao.findRecMedicalDoctorPage(medicalDoctor, currentPage, pageSize);
		return page;
	}

	public List<MedicalHospital> getMedicalHospitals() {
		String sql="select * from medical_hospital where deleted=0 and status=1 order by  convert(name using gbk) ASC";
		List<MedicalHospital> voList=dao.findEntitiesByJdbc(MedicalHospital.class, sql, null);
		return voList;
	}


	@Override
	public void updateSortUpRec(String id) {
		// TODO Auto-generated method stub
		String hqlPre="from MedicalDoctor where  deleted=0 and id = ?";
		MedicalDoctor coursePre= dao.findByHQLOne(hqlPre,new Object[] {id});
		Integer medicalDoctorPreSort=coursePre.getRecommendSort();

		String hqlNext="from MedicalDoctor where recommendSort > (select recommendSort from MedicalDoctor where id= ? )  and deleted=0 and recommend = 1 order by recommendSort ";
		MedicalDoctor courseNext= dao.findByHQLOne(hqlNext,new Object[] {id});
		Integer medicalDoctorNextSort=courseNext.getRecommendSort();

		coursePre.setRecommendSort(medicalDoctorNextSort);
		courseNext.setRecommendSort(medicalDoctorPreSort);

		dao.update(coursePre);
		dao.update(courseNext);

	}

	@Override
	public void updateSortDownRec(String id) {
		// TODO Auto-generated method stub
		String hqlPre="from MedicalDoctor where  deleted=0 and id = ?";
		MedicalDoctor coursePre= dao.findByHQLOne(hqlPre,new Object[] {id});
		Integer medicalHospitalPreSort=coursePre.getRecommendSort();

		String hqlNext="from MedicalDoctor where recommendSort < (select recommendSort from MedicalDoctor where id= ? )  and deleted=0 and recommend = 1 order by recommendSort desc";
		MedicalDoctor courseNext= dao.findByHQLOne(hqlNext,new Object[] {id});
		Integer medicalHospitalNextSort=courseNext.getRecommendSort();

		coursePre.setRecommendSort(medicalHospitalNextSort);
		courseNext.setRecommendSort(medicalHospitalPreSort);

		dao.update(coursePre);
		dao.update(courseNext);

	}

	@Override
	public List<MedicalDoctor> findAllDoctor(String id) {
		List<MedicalDoctor> list = list();
		List<MedicalDoctorAuthorArticle> mdaa = dao.findEntitiesByProperty(MedicalDoctorAuthorArticle.class, "articleId", id);
		for (int i = 0; i < mdaa.size(); i++) {
			for (int j = 0; j < list.size(); j++) {
				if(mdaa.get(i).getDoctorId().equals(list.get(j).getId())){
					list.get(j).setHas(true);
				}
			}
		}
		return list;
	}

	@Override
	public void addDoctorAuthorArticle(String id, String[] doctor) {
		List<MedicalDoctorAuthorArticle> mdaas = dao.findEntitiesByProperty(MedicalDoctorAuthorArticle.class, "articleId", id);
		for (int i = 0; i < mdaas.size(); i++) {
			dao.delete(mdaas.get(i));
		}
		if(doctor != null){
			for (int i = 0; i < doctor.length; i++) {
				MedicalDoctorAuthorArticle medicalDoctorAuthorArticle = new MedicalDoctorAuthorArticle();
				String mid = UUID.randomUUID().toString().replace("-","");
				medicalDoctorAuthorArticle.setId(mid);
				medicalDoctorAuthorArticle.setDoctorId(doctor[i]);
				medicalDoctorAuthorArticle.setArticleId(id);
				medicalDoctorAuthorArticle.setCreateTime(new Date());
				dao.save(medicalDoctorAuthorArticle);
			}
		}
	}
	@Override
	public void addDoctorReport(String id, String[] doctor) {
		List<MedicalDoctorReport> mdaas = dao.findEntitiesByProperty(MedicalDoctorReport.class, "articleId", id);
		for (int i = 0; i < mdaas.size(); i++) {
			dao.delete(mdaas.get(i));
		}
		if(doctor != null){
			for (int i = 0; i < doctor.length; i++) {
				MedicalDoctorReport medicalDoctorReport= new MedicalDoctorReport();
				String mid = UUID.randomUUID().toString().replace("-","");
				medicalDoctorReport.setId(mid);
				medicalDoctorReport.setDoctorId(doctor[i]);
				medicalDoctorReport.setArticleId(id);
				medicalDoctorReport.setCreateTime(new Date());
				dao.save(medicalDoctorReport);
			}
		}
	}

	@Override
	public List<MedicalDoctor> allListForReport(String articleId) {
		List<MedicalDoctor> list = list();
		List<MedicalDoctorReport> mdr = dao.findEntitiesByProperty(MedicalDoctorReport.class, "articleId", articleId);
		for (int i = 0; i < mdr.size(); i++) {
			for (int j = 0; j < list.size(); j++) {
				if(mdr.get(i).getDoctorId().equals(list.get(j).getId())){
					list.get(j).setHas(true);
				}
			}
		}
		return list;
	}

	public List<MedicalDoctor> list() {
		String sql="select * from medical_doctor where deleted=0 and status=1 order by  convert(name using gbk) ASC";
		List<MedicalDoctor> voList=dao.findEntitiesByJdbc(MedicalDoctor.class, sql, null);
		return voList;
	}

	@Override
	public List<MedicalDoctor> getMedicalDoctor(String writingsId) {
		// TODO Auto-generated method stub
		List<MedicalDoctor> list = list();
		List<MedicalDoctorWritings> mdr = dao.findEntitiesByProperty(MedicalDoctorWritings.class, "writingsId", writingsId);
		for (int i = 0; i < mdr.size(); i++) {
			for (int j = 0; j < list.size(); j++) {
				if(mdr.get(i).getDoctorId().equals(list.get(j).getId())){
					list.get(j).setHas(true);
				}
			}
		}
		return list;
	}
	/**
	 * 获取所有的医师
	 */
	@Override
	public List<MedicalDoctor> getAllMedicalDoctorList() {
		// TODO Auto-generated method stub
		return list();
	}
	
}
