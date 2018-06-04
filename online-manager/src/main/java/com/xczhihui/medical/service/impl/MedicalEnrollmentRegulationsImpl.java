package com.xczhihui.medical.service.impl;

import com.xczhihui.bxg.online.common.domain.MedicalEnrollmentRegulations;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.medical.dao.MedicalEnrollmentRegulationsDao;
import com.xczhihui.medical.service.MedicalEnrollmentRegulationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: MedicalEntryInformationImpl
 * @Description: 师承-招生简章
 * @Author: wangyishuai
 * @email: 15210815880@163.com
 * @CreateDate: 2018/5/21 16:18
 **/
@Service
public class MedicalEnrollmentRegulationsImpl implements MedicalEnrollmentRegulationsService {

    @Autowired
    private MedicalEnrollmentRegulationsDao medicalEnrollmentRegulationsDao;

    @Override
    public Page<MedicalEnrollmentRegulations> findEnrollmentRegulationsPage(MedicalEnrollmentRegulations edicalEnrollmentRegulations, int pageNumber, int pageSize) {
        Page<MedicalEnrollmentRegulations> list = medicalEnrollmentRegulationsDao.findEnrollmentRegulationsPage(edicalEnrollmentRegulations,pageNumber,pageSize);
        return list;
    }

    @Override
    public void update(MedicalEnrollmentRegulations mer) {
        medicalEnrollmentRegulationsDao.update(mer);
    }

    @Override
    public MedicalEnrollmentRegulations findById(Integer id) {
        MedicalEnrollmentRegulations mer = medicalEnrollmentRegulationsDao.findById(id);
        return mer;
    }

    @Override
    public MedicalEnrollmentRegulations enrollmentRegulationsDetail(Integer id) {

        return medicalEnrollmentRegulationsDao.enrollmentRegulationsDetail(id);
    }

    @Override
    public void save(MedicalEnrollmentRegulations mer) {
        medicalEnrollmentRegulationsDao.save(mer);
    }

    @Override
    public void updateStatus(Integer id) {
        String hql = "from MedicalEnrollmentRegulations where 1=1 and id = ?";
        MedicalEnrollmentRegulations medicalEnrollmentRegulations = medicalEnrollmentRegulationsDao
                .findByHQLOne(hql, new Object[] { id });
        if (medicalEnrollmentRegulations.isStatus()) {
            medicalEnrollmentRegulations.setStatus(false);
        } else {
            medicalEnrollmentRegulations.setStatus(true);
        }
        medicalEnrollmentRegulationsDao.update(medicalEnrollmentRegulations);
    }

    @Override
    public List<MedicalEnrollmentRegulations> getAllMedicalEntryInformationList() {
        String sql = "select mer.id as id,mer.title as title " +
                " from " +
                " medical_enrollment_regulations mer" +
                " WHERE  mer.deleted = 0 and mer.status = 1";
        List<MedicalEnrollmentRegulations> list = medicalEnrollmentRegulationsDao.findEntitiesByJdbc(
                MedicalEnrollmentRegulations.class, sql, null);
        return list;
    }


}