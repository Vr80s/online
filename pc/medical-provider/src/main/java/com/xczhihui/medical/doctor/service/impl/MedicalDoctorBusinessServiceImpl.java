package com.xczhihui.medical.doctor.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorAuthenticationInformationMapper;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctor;
import com.xczhihui.medical.doctor.model.MedicalDoctorAuthenticationInformation;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.field.model.MedicalField;
import com.xczhihui.medical.hospital.model.MedicalHospital;
import com.xczhihui.medical.hospital.service.IMedicalHospitalBusinessService;
import com.xczhihui.medical.hospital.service.IMedicalHospitalService;
import com.xczhihui.medical.hospital.service.impl.MedicalHospitalBusinessServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: MedicalDoctorBusinessServiceImpl.java <br>
 * Description:医师业务接口类 <br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 下午 11:24 2017/12/9 0009<br>
 */
@Service
public class MedicalDoctorBusinessServiceImpl implements IMedicalDoctorBusinessService{

    @Autowired
    private MedicalDoctorMapper medicalDoctorMapper;
    @Autowired
    private IMedicalHospitalBusinessService iMedicalHospitalBusinessService;
    @Autowired
    private MedicalDoctorAuthenticationInformationMapper medicalDoctorAuthenticationInformationMapper;

    @Override
    public Page<MedicalDoctor> selectDoctorPage(Page<MedicalDoctor> page, Integer type, String hospitalId, String name, String field) {
        List<MedicalDoctor> records = medicalDoctorMapper.selectDoctorList(page, type, hospitalId, name, field);
        if(field!=null){
            for (int i = 0; i < records.size(); i++) {
                List<MedicalField> medicalFields = medicalDoctorMapper.selectMedicalFieldsByDoctorId(records.get(i).getId());
                records.get(i).setFields(medicalFields);
            }
        }
        page.setRecords(records);
        return page;
    }

    @Override
    public MedicalDoctor selectDoctorById(String id) {
        MedicalDoctor medicalDoctor = medicalDoctorMapper.selectDoctorById(id);
        List<MedicalField> medicalFields = medicalDoctorMapper.selectMedicalFieldsByDoctorId(medicalDoctor.getId());
        medicalDoctor.setFields(medicalFields);
        if(medicalDoctor.getHospitalId() != null){
            MedicalHospital medicalHospital = iMedicalHospitalBusinessService.selectHospitalById(medicalDoctor.getHospitalId());
            medicalDoctor.setMedicalHospital(medicalHospital);
        }
        if(medicalDoctor.getAuthenticationInformationId()!=null){
            MedicalDoctorAuthenticationInformation medicalDoctorAuthenticationInformation = medicalDoctorAuthenticationInformationMapper.selectByDoctorId(medicalDoctor.getAuthenticationInformationId());
            medicalDoctor.setMedicalDoctorAuthenticationInformation(medicalDoctorAuthenticationInformation);
        }
        return medicalDoctor;
    }

    @Override
    public List<MedicalField> getHotField() {
        return medicalDoctorMapper.getHotField();
    }

    @Override
    public List<MedicalDoctor> selectRecDoctor() {
        return medicalDoctorMapper.selectRecDoctor();
    }
}
