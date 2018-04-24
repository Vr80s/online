package com.xczhihui.medical.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.MedicalHospitalRecruit;
import com.xczhihui.medical.dao.HospitalRecruitDao;
import com.xczhihui.medical.service.HospitalRecruitService;

/**
 * @author hejiwei
 */
@Service("hospitalRecruitService")
public class HospitalRecruitServiceImpl implements HospitalRecruitService {

    @Autowired
    private HospitalRecruitDao hospitalRecruitDao;

    @Override
    public Page<MedicalHospitalRecruit> findMedicalHospitalRecruitPage(
            MedicalHospitalRecruit searchVo, int pageNumber, int pageSize) {
        return hospitalRecruitDao.findMedicalHospitalRecruitPage(searchVo,
                pageNumber, pageSize);
    }

    @Override
    public void updateMedicalHospitalRecruit(
            MedicalHospitalRecruit medicalHospitalRecruit) {
        MedicalHospitalRecruit old = findMedicalHospitalRecruitById(medicalHospitalRecruit
                .getId());
        old.setPosition(medicalHospitalRecruit.getPosition());
        old.setJobRequirements(medicalHospitalRecruit.getJobRequirements());
        old.setPostDuties(medicalHospitalRecruit.getPostDuties());
        old.setYears(medicalHospitalRecruit.getYears());
        old.setUpdateTime(new Date());
        hospitalRecruitDao.update(old);
    }

    @Override
    public void updateRecruitStatus(String id) {
        String hql = "from MedicalHospitalRecruit where 1=1 and deleted=0 and id = ?";
        MedicalHospitalRecruit medicalHospitalRecruit = hospitalRecruitDao
                .findByHQLOne(hql, new Object[]{id});

        if (medicalHospitalRecruit.getStatus()) {
            medicalHospitalRecruit.setStatus(false);
        } else {
            medicalHospitalRecruit.setStatus(true);
        }

        hospitalRecruitDao.update(medicalHospitalRecruit);
    }

    @Override
    public void deleteByIds(String[] ids) {
        for (String id : ids) {
            String hqlPre = "from MedicalHospitalRecruit where id = ?";
            MedicalHospitalRecruit medicalHospitalRecruit = hospitalRecruitDao
                    .findByHQLOne(hqlPre, new Object[]{id});
            if (medicalHospitalRecruit != null) {
                medicalHospitalRecruit.setDeleted(true);
                hospitalRecruitDao.update(medicalHospitalRecruit);
            }
        }
    }

    @Override
    public void updateSortDown(String id) {
        String hqlPre = "from MedicalHospitalRecruit where deleted=0 and id = ?";
        MedicalHospitalRecruit medicalHospitalRecruit = hospitalRecruitDao
                .findByHQLOne(hqlPre, new Object[]{id});
        Integer currentSort = medicalHospitalRecruit.getSort() == null ? 0
                : medicalHospitalRecruit.getSort();

        String hqlNext = "from MedicalHospitalRecruit where sort < (select sort from MedicalHospitalRecruit where id= ? )"
                + "  and deleted=0 and hospital_id = '"
                + medicalHospitalRecruit.getHospitalId()
                + "' order by sort desc";
        MedicalHospitalRecruit medicalHospitalRecruitDown = hospitalRecruitDao
                .findByHQLOne(hqlNext, new Object[]{id});
        Integer downSort = currentSort;
        if (medicalHospitalRecruitDown != null) {
            downSort = medicalHospitalRecruitDown.getSort();
            medicalHospitalRecruitDown.setSort(currentSort);
            hospitalRecruitDao.update(medicalHospitalRecruitDown);
        }

        medicalHospitalRecruit.setSort(downSort);
        hospitalRecruitDao.update(medicalHospitalRecruit);
    }

    @Override
    public void updateSortUp(String id) {
        String hqlPre = "from MedicalHospitalRecruit where deleted=0 and id = ?";
        MedicalHospitalRecruit medicalHospitalRecruit = hospitalRecruitDao
                .findByHQLOne(hqlPre, new Object[]{id});
        Integer currentSort = medicalHospitalRecruit.getSort() == null ? 0
                : medicalHospitalRecruit.getSort();

        String hqlNext = "from MedicalHospitalRecruit where sort > (select sort from MedicalHospitalRecruit where id= ? )  and deleted=0  and hospital_id = '"
                + medicalHospitalRecruit.getHospitalId() + "'order by sort asc";
        MedicalHospitalRecruit medicalHospitalRecruitUp = hospitalRecruitDao
                .findByHQLOne(hqlNext, new Object[]{id});
        Integer upSort = currentSort;
        if (medicalHospitalRecruitUp != null) {
            upSort = medicalHospitalRecruitUp.getSort();
            medicalHospitalRecruitUp.setSort(currentSort);
            hospitalRecruitDao.update(medicalHospitalRecruitUp);
        }
        medicalHospitalRecruit.setSort(upSort);
        hospitalRecruitDao.update(medicalHospitalRecruit);
    }

    @Override
    public MedicalHospitalRecruit findMedicalHospitalRecruitById(String id) {
        return hospitalRecruitDao.findOneEntitiyByProperty(
                MedicalHospitalRecruit.class, "id", id);
    }

    @Override
    public void addMedicalHospitalRecruit(
            MedicalHospitalRecruit medicalHospitalRecruit) {
        String id = UUID.randomUUID().toString().replace("-", "");
        medicalHospitalRecruit.setId(id);
        medicalHospitalRecruit.setCreateTime(new Date());
        medicalHospitalRecruit.setStatus(false);
        medicalHospitalRecruit.setDeleted(false);

        Map<String, Object> params = new HashMap<String, Object>();
        String sql = "SELECT MAX(sort) as sort FROM medical_hospital_recruit where deleted=0 and hospital_id = ?";
        int sort = hospitalRecruitDao.queryForInt(sql,
                medicalHospitalRecruit.getHospitalId());
        medicalHospitalRecruit.setSort(sort + 1);
        hospitalRecruitDao.save(medicalHospitalRecruit);
    }
}
