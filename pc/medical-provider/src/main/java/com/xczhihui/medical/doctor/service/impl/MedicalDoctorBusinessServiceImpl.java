package com.xczhihui.medical.doctor.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorAuthenticationInformationMapper;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorMapper;
import com.xczhihui.medical.doctor.mapper.OeBxsArticleMapper;
import com.xczhihui.medical.doctor.vo.MedicalDoctorVO;
import com.xczhihui.medical.doctor.vo.MedicalDoctorAuthenticationInformationVO;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.doctor.vo.MedicalWritingsVO;
import com.xczhihui.medical.doctor.vo.OeBxsArticleVO;
import com.xczhihui.medical.field.vo.MedicalFieldVO;
import com.xczhihui.medical.hospital.vo.MedicalHospitalVo;
import com.xczhihui.medical.hospital.service.IMedicalHospitalBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${specialColumn}")
    private String specialColumn;
    @Value("${doctorReport}")
    private String doctorReport;

    @Override
    public Page<MedicalDoctorVO> selectDoctorPage(Page<MedicalDoctorVO> page, Integer type, String hospitalId, String name, String field) {
        List<MedicalDoctorVO> records = medicalDoctorMapper.selectDoctorList(page, type, hospitalId, name, field);
        if(field!=null){
            for (int i = 0; i < records.size(); i++) {
                List<MedicalFieldVO> medicalFields = medicalDoctorMapper.selectMedicalFieldsByDoctorId(records.get(i).getId());
                records.get(i).setFields(medicalFields);
            }
        }
        page.setRecords(records);
        return page;
    }

    @Override
    public MedicalDoctorVO selectDoctorById(String id) {
        MedicalDoctorVO medicalDoctorVO = medicalDoctorMapper.selectDoctorById(id);
        List<MedicalFieldVO> medicalFields = medicalDoctorMapper.selectMedicalFieldsByDoctorId(medicalDoctorVO.getId());
        medicalDoctorVO.setFields(medicalFields);
        if(medicalDoctorVO.getHospitalId() != null){
            MedicalHospitalVo medicalHospital = iMedicalHospitalBusinessService.selectHospitalById(medicalDoctorVO.getHospitalId());
            medicalDoctorVO.setMedicalHospital(medicalHospital);
        }
        if(medicalDoctorVO.getAuthenticationInformationId()!=null){
            MedicalDoctorAuthenticationInformationVO medicalDoctorAuthenticationInformation = medicalDoctorAuthenticationInformationMapper.selectByDoctorId(medicalDoctorVO.getAuthenticationInformationId());
            medicalDoctorVO.setMedicalDoctorAuthenticationInformation(medicalDoctorAuthenticationInformation);
        }
        return medicalDoctorVO;
    }

    @Override
    public List<MedicalFieldVO> getHotField() {
        return medicalDoctorMapper.getHotField();
    }

    @Override
    public List<MedicalDoctorVO> selectRecDoctor() {
        return medicalDoctorMapper.selectRecDoctor();
    }

    @Override
    public List<OeBxsArticleVO> getNewsReports(String doctorId) {
        return oeBxsArticleMapper.getNewsReports(doctorId);
    }

    @Override
    public OeBxsArticleVO getNewsReportByArticleId(String articleId) {
        return oeBxsArticleMapper.getNewsReportByArticleId(articleId);
    }

    @Override
    public Page<OeBxsArticleVO> getSpecialColumns(Page<OeBxsArticleVO> page, String doctorId) {
        List<OeBxsArticleVO> specialColumns = oeBxsArticleMapper.getSpecialColumns(page, doctorId);
        page.setRecords(specialColumns);
        return page;
    }

    @Override
    public OeBxsArticleVO getSpecialColumnDetailsById(String articleId) {
        return oeBxsArticleMapper.getSpecialColumnDetailsById(articleId);
    }

    @Override
    public List<MedicalWritingsVO> getWritingsByDoctorId(String doctorId) {
        return medicalDoctorMapper.getWritingsByDoctorId(doctorId);
    }

    @Override
    public MedicalWritingsVO getWritingsDetailsById(String writingsId) {
        return medicalDoctorMapper.getWritingsDetailsById(writingsId);
    }

    @Override
    public List<MedicalWritingsVO> getRecentlyWritings() {
        return medicalDoctorMapper.getRecentlyWritings();
    }

    @Override
    public List<OeBxsArticleVO> getRecentlyNewsReports() {
        return oeBxsArticleMapper.getRecentlyNewsReports();
    }

    @Override
    public Page<OeBxsArticleVO> getNewsReportsByPage(Page<OeBxsArticleVO> page, String doctorId) {
        List<OeBxsArticleVO> records = oeBxsArticleMapper.getNewsReportsByPage(page,doctorId,doctorReport);
        page.setRecords(records);
        return page;
    }

    @Override
    public List<OeBxsArticleVO> getHotSpecialColumn() {
        return oeBxsArticleMapper.getHotSpecialColumn(specialColumn);
    }

    @Override
    public List<MedicalDoctorVO> getHotSpecialColumnAuthor() {
        return medicalDoctorMapper.getHotSpecialColumnAuthor(specialColumn);
    }

    @Override
    public Page<MedicalWritingsVO> getWritingsByPage(Page<MedicalWritingsVO> page) {
        List<MedicalWritingsVO> writings = medicalDoctorMapper.getWritingsByPage(page);
        page.setRecords(writings);
        return page;
    }
}
