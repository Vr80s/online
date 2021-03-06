package com.xczhihui.medical.hospital.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.common.util.CodeUtil;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalAnnouncementMapper;
import com.xczhihui.medical.hospital.model.MedicalHospitalAnnouncement;
import com.xczhihui.medical.hospital.service.IMedicalHospitalAnnouncementService;
import com.xczhihui.medical.hospital.vo.MedicalHospitalAnnouncementVO;

/**
 * @author hejiwei
 */
@Service
public class MedicalHospitalAnnouncementServiceImpl implements IMedicalHospitalAnnouncementService {

    @Autowired
    private MedicalHospitalAnnouncementMapper medicalHospitalAnnouncementMapper;

    @Override
    public Page<MedicalHospitalAnnouncementVO> list(int page, String hospitalId) {
        Page<MedicalHospitalAnnouncementVO> announcementVOPage = new Page<>(page, 10);
        announcementVOPage.setRecords(medicalHospitalAnnouncementMapper.list(announcementVOPage, hospitalId));
        return announcementVOPage;
    }

    @Override
    public void save(MedicalHospitalAnnouncement announcement) {
        announcement.setId(CodeUtil.getRandomUUID());
        medicalHospitalAnnouncementMapper.insert(announcement);
    }

    @Override
    public boolean deleteById(String id, String hospitalId) {
        return 1 == medicalHospitalAnnouncementMapper.markDeleted(id, hospitalId);
    }

    @Override
    public MedicalHospitalAnnouncement get(String id) {
        return medicalHospitalAnnouncementMapper.selectById(id);
    }

    @Override
    public MedicalHospitalAnnouncement findNewestOne(String hospitalId) {
        return medicalHospitalAnnouncementMapper.findNewestOne(hospitalId);
    }
}
