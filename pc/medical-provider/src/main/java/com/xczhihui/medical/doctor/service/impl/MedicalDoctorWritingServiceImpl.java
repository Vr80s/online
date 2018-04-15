package com.xczhihui.medical.doctor.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.common.util.CodeUtil;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorWritingMapper;
import com.xczhihui.medical.doctor.mapper.MedicalWritingMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctorWriting;
import com.xczhihui.medical.doctor.model.MedicalWriting;
import com.xczhihui.medical.doctor.service.IMedicalDoctorWritingService;
import com.xczhihui.medical.doctor.vo.MedicalWritingVO;
import com.xczhihui.medical.headline.mapper.OeBxsArticleMapper;
import com.xczhihui.medical.headline.model.OeBxsArticle;

/**
 * @author hejiwei
 */
@Service
public class MedicalDoctorWritingServiceImpl implements IMedicalDoctorWritingService {

    @Autowired
    private MedicalWritingMapper medicalWritingMapper;
    @Autowired
    private OeBxsArticleMapper oeBxsArticleMapper;
    @Autowired
    private MedicalDoctorWritingMapper medicalDoctorWritingMapper;

    @Override
    public Page<MedicalWritingVO> list(int page, int size, String doctorId) {
        Page<MedicalWritingVO> medicalWritingVOPage = new Page<>(page, size);
        medicalWritingVOPage.setRecords(medicalWritingMapper.listWritingByDoctorId(medicalWritingVOPage, doctorId));
        return medicalWritingVOPage;
    }

    @Override
    public Page<MedicalWritingVO> list(int page, int size) {
        Page<MedicalWritingVO> medicalWritingVOPage = new Page<>(page, size);
        medicalWritingVOPage.setRecords(medicalWritingMapper.listWriting(medicalWritingVOPage));
        return medicalWritingVOPage;
    }

    @Override
    public MedicalWritingVO get(String id) {
        return medicalWritingMapper.get(id);
    }

    @Override
    public boolean updateStatus(String doctorId, String id, boolean status) {
        return medicalWritingMapper.updateStatus(doctorId, id, status) == 1;
    }

    @Override
    public void save(String doctorId, MedicalWriting medicalWriting, String userId) {
        OeBxsArticle oeBxsArticle = new OeBxsArticle();
        oeBxsArticle.setTitle(medicalWriting.getTitle());
        oeBxsArticle.setImgPath(medicalWriting.getImgPath());
        oeBxsArticle.setContent(medicalWriting.getRemark());
        oeBxsArticle.setUserId(userId);
        oeBxsArticle.setCreateTime(new Date());
        oeBxsArticle.setDelete(false);
        oeBxsArticle.setStatus(0);
        oeBxsArticle.setUserId(userId);
        oeBxsArticleMapper.insert(oeBxsArticle);
        Integer articleId = oeBxsArticle.getId();
        oeBxsArticle.setSort(articleId);
        oeBxsArticleMapper.updateById(oeBxsArticle);

        medicalWriting.setArticleId(articleId);
        medicalWriting.setId(CodeUtil.getRandomUUID());
        medicalWritingMapper.insert(medicalWriting);

        MedicalDoctorWriting medicalDoctorWriting = new MedicalDoctorWriting();
        medicalDoctorWriting.setId(CodeUtil.getRandomUUID());
        medicalDoctorWriting.setCreateTime(new Date());
        medicalDoctorWriting.setDoctorId(doctorId);
        medicalDoctorWriting.setWritingId(medicalWriting.getId());
        medicalDoctorWritingMapper.insert(medicalDoctorWriting);
    }

    @Override
    public boolean update(String id, String doctorId, MedicalWriting medicalWriting) {
        MedicalWriting oldMedicalWriting = medicalWritingMapper.selectById(id);
        oldMedicalWriting.setRemark(medicalWriting.getRemark());
        oldMedicalWriting.setTitle(medicalWriting.getTitle());
        oldMedicalWriting.setStatus(medicalWriting.getStatus());
        oldMedicalWriting.setImgPath(medicalWriting.getImgPath());
        oldMedicalWriting.setBuyLink(medicalWriting.getBuyLink());
        oldMedicalWriting.setUpdatePerson(oldMedicalWriting.getCreatePerson());
        oldMedicalWriting.setUpdateTime(new Date());
        medicalWritingMapper.updateById(oldMedicalWriting);

        OeBxsArticle oldOeBxsArticle = oeBxsArticleMapper.selectById(oldMedicalWriting.getArticleId());
        oldOeBxsArticle.setTitle(medicalWriting.getTitle());
        oldOeBxsArticle.setImgPath(medicalWriting.getImgPath());
        oldOeBxsArticle.setContent(medicalWriting.getRemark());
        oeBxsArticleMapper.updateById(oldOeBxsArticle);
        return true;
    }
}
