package com.xczhihui.medical.doctor.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorBannerMapper;
import com.xczhihui.medical.doctor.model.DoctorBanner;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBannerService;
import com.xczhihui.medical.doctor.vo.DoctorBannerVO;
import com.xczhihui.medical.exception.MedicalException;

/**
 * @author hejiwei
 */
@Service
public class MedicalDoctorBannerServiceImpl implements IMedicalDoctorBannerService {

    private static final Object UPDATE_STATUS_LOCK = new Object();
    private static final int MAX_ONSHELF_BANNER_COUNT = 3;
    private static final Logger logger = LoggerFactory.getLogger(MedicalDoctorBannerServiceImpl.class);

    @Autowired
    private MedicalDoctorBannerMapper medicalDoctorBannerMapper;

    @Override
    public void addBanner(DoctorBanner doctorBanner, String userId) {
        doctorBanner.setUserId(userId);
        medicalDoctorBannerMapper.insert(doctorBanner);
    }

    @Override
    public void updateBanner(DoctorBanner doctorBanner, int id, String userId) {
        DoctorBanner oldBanner = medicalDoctorBannerMapper.selectById(id);
        oldBanner.setLinkParam(doctorBanner.getLinkParam());
        oldBanner.setType(doctorBanner.getType());
        oldBanner.setImgUrl(doctorBanner.getImgUrl());
        oldBanner.setStartTime(doctorBanner.getStartTime());
        oldBanner.setEndTime(doctorBanner.getEndTime());
        oldBanner.setRouteType(doctorBanner.getRouteType());
        medicalDoctorBannerMapper.updateAllColumnById(oldBanner);
    }

    @Override
    public void updateStatus(int id, boolean status, String userId) {
        synchronized (UPDATE_STATUS_LOCK) {
            DoctorBanner doctorBanner = medicalDoctorBannerMapper.selectById(id);
            if (doctorBanner == null) {
                throw new MedicalException("数据不存在");
            } else if (!doctorBanner.getUserId().equals(userId)) {
                throw new MedicalException("非法请求");
            } else {
                if (status) {
                    int count = medicalDoctorBannerMapper.countOnShelf(doctorBanner.getUserId());
                    if (count >= MAX_ONSHELF_BANNER_COUNT) {
                        throw new MedicalException("最多只能上架三个轮播图");
                    }
                    if (doctorBanner.getEndTime() != null && doctorBanner.getEndTime().before(new Date())) {
                        throw new MedicalException("期限已过期, 请先修改时间再上架");
                    }
                }
                doctorBanner.setStatus(status);
                medicalDoctorBannerMapper.updateById(doctorBanner);
            }
        }
    }

    @Override
    public Page<DoctorBanner> list(int page, int size, String userId) {
        Page<DoctorBanner> bannerPage = new Page<>(page, size);
        return bannerPage.setRecords(medicalDoctorBannerMapper.list(userId, bannerPage));
    }

    @Override
    public DoctorBanner get(int id, String userId) {
        DoctorBanner doctorBanner = medicalDoctorBannerMapper.selectById(id);
        if (doctorBanner == null) {
            throw new MedicalException("数据未找到");
        }
        if (!doctorBanner.getUserId().equals(userId)) {
            throw new MedicalException("非法请求");
        }
        return doctorBanner;
    }

    @Override
    public List<DoctorBannerVO> listByUserId(String userId) {
        return medicalDoctorBannerMapper.listByUserId(userId);
    }

    @Override
    public Integer updateAllUnShelves() {
        return medicalDoctorBannerMapper.updateAllUnShelves();
    }
}
