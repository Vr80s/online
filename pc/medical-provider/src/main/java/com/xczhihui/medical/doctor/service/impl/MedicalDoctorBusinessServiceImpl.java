package com.xczhihui.medical.doctor.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorAuthenticationInformationMapper;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorMapper;
import com.xczhihui.medical.doctor.mapper.OeBxsArticleMapper;
import com.xczhihui.medical.doctor.vo.MedicalDoctorVo;
import com.xczhihui.medical.doctor.vo.MedicalDoctorAuthenticationInformationVo;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.doctor.vo.OeBxsArticleVo;
import com.xczhihui.medical.field.vo.MedicalFieldVo;
import com.xczhihui.medical.hospital.vo.MedicalHospitalVo;
import com.xczhihui.medical.hospital.service.IMedicalHospitalBusinessService;
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
    @Autowired
    private OeBxsArticleMapper oeBxsArticleMapper;

    @Override
    public Page<MedicalDoctorVo> selectDoctorPage(Page<MedicalDoctorVo> page, Integer type, String hospitalId, String name, String field) {
        List<MedicalDoctorVo> records = medicalDoctorMapper.selectDoctorList(page, type, hospitalId, name, field);
        if(field!=null){
            for (int i = 0; i < records.size(); i++) {
                List<MedicalFieldVo> medicalFields = medicalDoctorMapper.selectMedicalFieldsByDoctorId(records.get(i).getId());
                records.get(i).setFields(medicalFields);
            }
        }
        page.setRecords(records);
        return page;
    }

    @Override
    public MedicalDoctorVo selectDoctorById(String id) {
        MedicalDoctorVo medicalDoctorVo = medicalDoctorMapper.selectDoctorById(id);
        List<MedicalFieldVo> medicalFields = medicalDoctorMapper.selectMedicalFieldsByDoctorId(medicalDoctorVo.getId());
        medicalDoctorVo.setFields(medicalFields);
        if(medicalDoctorVo.getHospitalId() != null){
            MedicalHospitalVo medicalHospital = iMedicalHospitalBusinessService.selectHospitalById(medicalDoctorVo.getHospitalId());
            medicalDoctorVo.setMedicalHospital(medicalHospital);
        }
        if(medicalDoctorVo.getAuthenticationInformationId()!=null){
            MedicalDoctorAuthenticationInformationVo medicalDoctorAuthenticationInformation = medicalDoctorAuthenticationInformationMapper.selectByDoctorId(medicalDoctorVo.getAuthenticationInformationId());
            medicalDoctorVo.setMedicalDoctorAuthenticationInformation(medicalDoctorAuthenticationInformation);
        }
        return medicalDoctorVo;
    }

    @Override
    public List<MedicalFieldVo> getHotField() {
        return medicalDoctorMapper.getHotField();
    }

    @Override
    public List<MedicalDoctorVo> selectRecDoctor() {
        return medicalDoctorMapper.selectRecDoctor();
    }

    @Override
    public List<OeBxsArticleVo> getNewsReports(String doctorId) {
        return oeBxsArticleMapper.getNewsReports(doctorId);
    }

    @Override
    public OeBxsArticleVo getNewsReportByArticleId(String articleId) {
        return oeBxsArticleMapper.getNewsReportByArticleId(articleId);
    }

    @Override
    public List<OeBxsArticleVo> getSpecialColumnByDoctorId(String doctorId) {
        return oeBxsArticleMapper.getSpecialColumnByDoctorId(doctorId);
    }

    @Override
    public OeBxsArticleVo getSpecialColumnDetailsById(String articleId) {
        return oeBxsArticleMapper.getSpecialColumnDetailsById(articleId);
    }
}
