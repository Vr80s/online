package com.xczhihui.medical.doctor.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctorApply;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2018-01-15
 */
public interface MedicalDoctorApplyMapper extends BaseMapper<MedicalDoctorApply> {

    /**
     * 获取用户最后一条入驻申请信息
     * @param userId 用户id
     * @return 入驻申请信息
     */
    MedicalDoctorApply getLastOne(String userId);

    /**
     * 删除入驻申请信息
     * @param userId 用户id
     */
    void delete(String userId);
}