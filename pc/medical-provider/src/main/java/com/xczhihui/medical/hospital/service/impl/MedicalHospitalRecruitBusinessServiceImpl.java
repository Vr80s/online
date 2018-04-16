package com.xczhihui.medical.hospital.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.common.util.CodeUtil;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalMapper;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalRecruitMapper;
import com.xczhihui.medical.hospital.model.MedicalHospital;
import com.xczhihui.medical.hospital.model.MedicalHospitalRecruit;
import com.xczhihui.medical.hospital.service.IMedicalHospitalRecruitBusinessService;
import com.xczhihui.medical.hospital.vo.MedicalHospitalRecruitVO;

/**
 * <p>
 * 医馆招聘业务接口类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
@Service
public class MedicalHospitalRecruitBusinessServiceImpl implements IMedicalHospitalRecruitBusinessService {

    @Autowired
    private MedicalHospitalRecruitMapper medicalHospitalRecruitMapper;
    @Autowired
    private MedicalHospitalMapper medicalHospitalMapper;

    @Override
    public List<MedicalHospitalRecruitVO> selectRecHospitalRecruit() {
        return medicalHospitalRecruitMapper.selectRecHospitalRecruit();
    }

    @Override
    public List<MedicalHospitalRecruitVO> selectHospitalRecruitByHospitalId(String hospitalId) {
        return medicalHospitalRecruitMapper.selectHospitalRecruitByHospitalId(hospitalId);
    }

    @Override
    public Page<MedicalHospitalRecruitVO> listByPage(String hospitalId, String keyword, int current, int size) {
        if (StringUtils.isNotBlank(keyword)) {
            keyword = "%" + keyword + "%";
        } else {
            keyword = null;
        }
        Page<MedicalHospitalRecruitVO> page = new Page<>(current, size);
        page.setRecords(medicalHospitalRecruitMapper.selectPageByHospitalId(hospitalId, keyword, page));
        return page;
    }

    @Override
    public void save(MedicalHospitalRecruit medicalHospitalRecruit, String userId) {
        MedicalHospital medicalHospital = medicalHospitalMapper.selectById(medicalHospitalRecruit.getHospitalId());
        if (medicalHospital == null || medicalHospital.getDeleted()) {
            throw new RuntimeException("医馆不存在或已被删除");
        }

        medicalHospitalRecruit.setId(CodeUtil.getRandomUUID());
        medicalHospitalRecruit.setCreatePerson(userId);
        Integer sort = medicalHospitalRecruitMapper.maxSort();
        medicalHospitalRecruit.setSort(sort != null ? sort + 1 : 0);
        medicalHospitalRecruitMapper.insert(medicalHospitalRecruit);
    }

    @Override
    public void update(String id, MedicalHospitalRecruit medicalHospitalRecruit) {
        MedicalHospitalRecruit oldRecruit = medicalHospitalRecruitMapper.selectById(id);
        if (oldRecruit == null) {
            throw new RuntimeException("招聘信息不存在");
        }
        if ((oldRecruit.getDeleted() != null && oldRecruit.getDeleted())) {
            throw new RuntimeException("招聘信息已被删除");
        }
        oldRecruit.setYears(medicalHospitalRecruit.getYears());
        oldRecruit.setPostDuties(medicalHospitalRecruit.getPostDuties());
        oldRecruit.setPosition(medicalHospitalRecruit.getPosition());
        oldRecruit.setJobRequirements(medicalHospitalRecruit.getJobRequirements());
        medicalHospitalRecruitMapper.updateById(oldRecruit);
    }

    @Override
    public void updateStatus(String id, boolean status) {
        int updateCnt;
        if (status) {
            updateCnt = medicalHospitalRecruitMapper.publicRecruit(id, new Date());
        } else {
            updateCnt = medicalHospitalRecruitMapper.closeRecruit(id);
        }
        if (updateCnt == 0) {
            if (status) {
                throw new RuntimeException("发布失败");
            } else {
                throw new RuntimeException("关闭失败");
            }
        }
    }

    @Override
    public void delete(String id) {
        int cnt = medicalHospitalRecruitMapper.markDeleted(id);
        if (cnt == 0) {
            throw new RuntimeException("删除失败");
        }
    }

    @Override
    public MedicalHospitalRecruit get(String id) {
        MedicalHospitalRecruit medicalHospitalRecruit = medicalHospitalRecruitMapper.selectById(id);
        if (medicalHospitalRecruit == null || medicalHospitalRecruit.getDeleted()) {
            throw new RuntimeException("招聘信息不存在或已删除");
        }
        return medicalHospitalRecruit;
    }
}
