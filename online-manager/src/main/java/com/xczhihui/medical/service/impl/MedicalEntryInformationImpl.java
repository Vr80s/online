package com.xczhihui.medical.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.online.common.domain.MedicalEntryInformation;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.medical.dao.MedicalEntryInformationDao;
import com.xczhihui.medical.service.MedicalEntryInformationService;

/**
 * @ClassName: MedicalEntryInformationImpl
 * @Description: 师承-报名
 * @Author: wangyishuai
 * @email: 15210815880@163.com
 * @CreateDate: 2018/5/21 16:18
 **/
@Service
public class MedicalEntryInformationImpl implements MedicalEntryInformationService {

    @Autowired
    private MedicalEntryInformationDao medicalEntryInformationDao;

    @Override
    public Page<MedicalEntryInformation> findEntryInformationPage(MedicalEntryInformation edicalEnrollmentRegulations, int pageNumber, int pageSize) {
        Page<MedicalEntryInformation> list = medicalEntryInformationDao.findEntryInformationPage(edicalEnrollmentRegulations, pageNumber, pageSize);
        return list;
    }

    @Override
    public MedicalEntryInformation entryInformationDetail(Integer id) {
        return medicalEntryInformationDao.entryInformationDetail(id);
    }

    @Override
    public void update(MedicalEntryInformation mer) {
        medicalEntryInformationDao.update(mer);
    }

    @Override
    public List<MedicalEntryInformation> getAllMedicalEntryInformationList(Integer merId) {
        String sql = "select mei.id as id, mei.mer_id as merId,mei.name as name,mei.age as age,mei.sex as sex, " +
                " mei.native_place as nativePlace,mei.tel as tel,mer.deadline as deadline,mei.create_time createTime," +
                " mei.education,mei.apprentice as apprentice ,\n" +
                "  mei.`education_experience`  educationExperience,\n" +
                "  mei.`medical_experience` medicalExperience,\n" +
                "  mei.`goal`" +
                " from " +
                " medical_entry_information mei ,medical_enrollment_regulations mer" +
                " WHERE mei.mer_id = mer.id and mei.deleted = 0 order by mei.create_time desc ";
        if (merId != null && merId != -1) {
            sql += "and mei.mer_id = " + merId;
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", "remId");
        List<MedicalEntryInformation> list = medicalEntryInformationDao.findEntitiesByJdbc(
                MedicalEntryInformation.class, sql, null);
        return list;
    }

    @Override
    public void updateIsApprentice(Integer id, Integer apprentice) {
        String hql = "from MedicalEntryInformation where 1=1  and id = ?";
        MedicalEntryInformation medicalEntryInformation = medicalEntryInformationDao
                .findByHQLOne(hql, new Object[]{id});
        medicalEntryInformation.setApprentice(apprentice);
        medicalEntryInformationDao.update(medicalEntryInformation);
    }
}