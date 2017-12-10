package com.xczhihui.medical.hospital.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.hospital.model.MedicalHospital;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface IMedicalHospitalBusinessService {

    public Page<MedicalHospital> selectHospitalPage(Page<MedicalHospital> page);

}
