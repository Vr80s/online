package com.xczhihui.medical.service.impl;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.MedicalHospital;
import com.xczhihui.bxg.online.common.domain.MedicalHospitalPicture;
import com.xczhihui.medical.dao.HospitalDao;
import com.xczhihui.medical.enums.MedicalExceptionEnum;
import com.xczhihui.medical.exception.MedicalException;
import com.xczhihui.medical.service.HospitalService;

/**
 * MedicalHospitalServiceImpl:医馆业务层接口实现类
 *
 * @author Rongcai Kang
 */
@Service
public class HospitalServiceImpl extends OnlineBaseServiceImpl implements HospitalService {

    @Autowired
    private HospitalDao hospitalDao;
    @Value("${env.flag}")
    private String envFlag;

    @Override
    public Page<MedicalHospital> findMedicalHospitalPage(MedicalHospital medicalHospital, int pageNumber, int pageSize) {
        Page<MedicalHospital> page = hospitalDao.findMedicalHospitalPage(medicalHospital, pageNumber, pageSize);
        return page;

    }

    @Override
    public Page<MedicalHospital> findRecMedicalHospitalPage(MedicalHospital medicalHospital, int pageNumber, int pageSize) {
        Page<MedicalHospital> page = hospitalDao.findRecMedicalHospitalPage(medicalHospital, pageNumber, pageSize);
        return page;

    }

    @Override
    public void addMedicalHospital(MedicalHospital medicalHospital) {
        String id = UUID.randomUUID().toString().replace("-", "");
        medicalHospital.setId(id);
        medicalHospital.setCreateTime(new Date());
        medicalHospital.setDeleted(false);
        medicalHospital.setStatus(false);
        dao.save(medicalHospital);
    }

    @Override
    public void updateMedicalHospital(MedicalHospital medicalHospital) {
        dao.update(medicalHospital);
    }


    @Override
    public void updateStatus(String id) {
        String hql = "from MedicalHospital where 1=1 and deleted=0 and id = ?";
        MedicalHospital medicalHospital = dao.findByHQLOne(hql, new Object[]{id});
        medicalHospital.setStatus(!medicalHospital.getStatus());
        dao.update(medicalHospital);
    }


    @Override
    public void deleteMedicalHospitalById(String id) {
        hospitalDao.deleteById(id);
    }

    @Override
    public void deletes(String[] ids) {
        for (String id : ids) {
            String hqlPre = "from MedicalHospital where id = ?";
            MedicalHospital medicalHospital = dao.findByHQLOne(hqlPre, new Object[]{id});
            if (medicalHospital != null) {
                medicalHospital.setDeleted(true);
                dao.update(medicalHospital);
            }
        }
    }

    /**
     * 修改医馆图片
     *
     * @param medicalHospitalId 医馆id
     */
    @Override
    public void updateMedicalHospitalDetail(String medicalHospitalId, String picture1, String picture2, String picture3, String picture4, String picture5, String picture6, String picture7, String picture8, String picture9) {

        // 根据医馆id获取医馆详情
        List<MedicalHospital> hospitals = dao.findEntitiesByProperty(MedicalHospital.class, "id", medicalHospitalId);
        if (CollectionUtils.isEmpty(hospitals)) {
            throw new MedicalException(MedicalExceptionEnum.HOSPITAL_NOT_EXIT);
        } else {
            if (StringUtils.isNotBlank(hospitals.get(0).getSourceId()) || StringUtils.isNotBlank(hospitals.get(0).getCreatePerson())) {
                throw new MedicalException(MedicalExceptionEnum.MUST_NOT_HANDLE);
            }
        }

        List<MedicalHospitalPicture> mhps = dao.findEntitiesByProperty(MedicalHospitalPicture.class, "hospitalId", medicalHospitalId);
        for (int i = 0; i < mhps.size(); i++) {
            dao.delete(mhps.get(i));
        }
        savePicture(medicalHospitalId, picture1, "1");
        savePicture(medicalHospitalId, picture2, "2");
        savePicture(medicalHospitalId, picture3, "3");
        savePicture(medicalHospitalId, picture4, "4");
        savePicture(medicalHospitalId, picture5, "5");
        savePicture(medicalHospitalId, picture6, "6");
        savePicture(medicalHospitalId, picture7, "7");
        savePicture(medicalHospitalId, picture8, "8");
        savePicture(medicalHospitalId, picture9, "9");
    }

    public void savePicture(String medicalHospitalId, String picture, String version) {
        MedicalHospitalPicture mhp = new MedicalHospitalPicture();
        String id = UUID.randomUUID().toString().replace("-", "");
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

        String sql = "select * from medical_hospital_picture where hospital_id = '" + medicalHospitalId + "' and deleted = 0 order by version";

        List<MedicalHospitalPicture> voList = dao.findEntitiesByJdbc(MedicalHospitalPicture.class, sql, null);

//		List<MedicalHospitalPicture> c = dao.findEntitiesByProperty(MedicalHospitalPicture.class, "hospitalId", medicalHospitalId);
        Map<String, Object> retn = new HashMap<String, Object>();
        retn.put("hospital", mh);
        retn.put("picture", voList);
            /*2017-08-14---yuruixin*/
        return retn;
    }

    @Override
    public List<MedicalHospital> findByName(String name) {
        List<MedicalHospital> MedicalHospitals = dao.findEntitiesByProperty(MedicalHospital.class, "name", name);
        return MedicalHospitals;
    }

    @Override
    public MedicalHospital findMedicalHospitalById(String id) {
        return dao.findOneEntitiyByProperty(MedicalHospital.class, "id", id);
    }

    @Override
    public boolean updateRec(String[] ids, int isRecommend) {
        List<String> ids2 = new ArrayList();
        if (isRecommend == 1) {//如果是要推荐 那么就验证 推荐数量是否大于4
            //校验是否被引用
            String hqlPre = "from MedicalHospital where deleted=0 and recommend = 1";
            List<MedicalHospital> list = dao.findByHQL(hqlPre);
            if (list.size() > 0) {//只有原来大于0才执行
                for (int i = 0; i < ids.length; i++) {
                    int j = 0;
                    Iterator<MedicalHospital> iterator = list.iterator();
                    while (iterator.hasNext()) {//剔除本次推荐的与已经推荐的重复的
                        MedicalHospital medicalHospital = iterator.next();
                        if (medicalHospital.getId().equals(ids[i])) {//如果存在就把他剔除掉从list中
                            j = 1;
                        }
                    }
                    if (j == 0) {
                        ids2.add(ids[i]);
                    }
                }
            } else {
                for (int i = 0; i < ids.length; i++) {
                    ids2.add(ids[i]);
                }
            }

            //已经存在的数量 +  即将添加的数量
            /*if((list.size()+ids2.size()) > 10){
                return false;
			}*/
        } else {//如果是取消推荐
            for (int i = 0; i < ids.length; i++) {
                ids2.add(ids[i]);
            }
        }

        String sql = "select ifnull(min(recommend_sort),0) from medical_hospital where  deleted=0 and recommend = 1";
        int i = dao.queryForInt(sql, null);//最小的排序

        for (String id : ids2) {
            if (id == "" || id == null) {
                continue;
            }
            i = i - 1;
            String hqlPre = "from MedicalHospital where  deleted = 0 and id = ?";
            MedicalHospital medicalHospital = dao.findByHQLOne(hqlPre, new Object[]{id});
            if (medicalHospital != null) {
                medicalHospital.setRecommend(isRecommend == 1);
                medicalHospital.setRecommendSort(i);
                dao.update(medicalHospital);
            }
        }
        return true;
    }

    @Override
    public void updateSortUpRec(String id) {
        String hqlPre = "from MedicalHospital where  deleted=0 and id = ?";
        MedicalHospital coursePre = dao.findByHQLOne(hqlPre, new Object[]{id});
        Integer medicalHospitalPreSort = coursePre.getRecommendSort();

        String hqlNext = "from MedicalHospital where recommendSort > (select recommendSort from MedicalHospital where id= ? )  and deleted=0 and recommend = 1 order by recommendSort ";
        MedicalHospital courseNext = dao.findByHQLOne(hqlNext, new Object[]{id});
        Integer medicalHospitalNextSort = courseNext.getRecommendSort();

        coursePre.setRecommendSort(medicalHospitalNextSort);
        courseNext.setRecommendSort(medicalHospitalPreSort);

        dao.update(coursePre);
        dao.update(courseNext);

    }

    @Override
    public void updateSortDownRec(String id) {
        String hqlPre = "from MedicalHospital where  deleted=0 and id = ?";
        MedicalHospital coursePre = dao.findByHQLOne(hqlPre, new Object[]{id});
        Integer medicalHospitalPreSort = coursePre.getRecommendSort();

        String hqlNext = "from MedicalHospital where recommendSort < (select recommendSort from MedicalHospital where id= ? )  and deleted=0 and recommend = 1 order by recommendSort desc";
        MedicalHospital courseNext = dao.findByHQLOne(hqlNext, new Object[]{id});
        Integer medicalHospitalNextSort = courseNext.getRecommendSort();

        coursePre.setRecommendSort(medicalHospitalNextSort);
        courseNext.setRecommendSort(medicalHospitalPreSort);

        dao.update(coursePre);
        dao.update(courseNext);
    }
}
