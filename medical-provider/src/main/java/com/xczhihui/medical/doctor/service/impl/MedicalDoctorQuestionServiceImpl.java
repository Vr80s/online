package com.xczhihui.medical.doctor.service.impl;

import java.util.Date;

import org.apache.commons.io.input.MessageDigestCalculatingInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.anchor.model.CourseAnchor;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorMapper;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorQuestionMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctor;
import com.xczhihui.medical.doctor.model.MedicalDoctorQuestion;
import com.xczhihui.medical.doctor.service.IMedicalDoctorQuestionService;
import com.xczhihui.medical.exception.MedicalException;

import javassist.expr.NewArray;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
@Service
public class MedicalDoctorQuestionServiceImpl implements IMedicalDoctorQuestionService {

    @Autowired
    private MedicalDoctorQuestionMapper medicalDoctorQuestionMapper;
    
    @Autowired
    private MedicalDoctorMapper  medicalDoctorMapper;

    @Override
    public Page<MedicalDoctorQuestion> selectQuestionByDoctorId(Page<MedicalDoctorQuestion> page, 
            String doctorId) {

        return page.setRecords(medicalDoctorQuestionMapper.selectQuestionByDoctorId(page,doctorId));
    }

    
    @Override
    public void addQuestion(String accountId, String doctorId, String question) {
        
        MedicalDoctor medicalDoctor = new MedicalDoctor();
        medicalDoctor.setId(doctorId);
        medicalDoctor.setDeleted(false);
        medicalDoctor.setStatus(true);
        
        MedicalDoctor medicalDoctorold = medicalDoctorMapper.selectOne(medicalDoctor);
        if(medicalDoctorold == null) {
            throw new MedicalException("医师信息有误");
        }
        
        MedicalDoctorQuestion  medicalDoctorQuestion = new MedicalDoctorQuestion();
        medicalDoctorQuestion.setQuestion(question);
        medicalDoctorQuestion.setUserId(accountId);
        medicalDoctorQuestion.setDoctorId(doctorId);
        medicalDoctorQuestion.setCreateTime(new Date());
        
        medicalDoctorQuestionMapper.insert(medicalDoctorQuestion);
    }

}
