package com.xczhihui.medical.hospital.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.medical.hospital.model.MedicalHospitalApply;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
  *  医馆入驻申请认证Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2018-01-15
 */
public interface MedicalHospitalApplyMapper extends BaseMapper<MedicalHospitalApply> {

    /**
     * 获取用户最后一次申请认证信息
     * @param userId 用户id
     * @return 最后一次申请认证信息
     */
    MedicalHospitalApply getLastOne(String userId);

    /**
     * 根据用户id和审核状态删除入驻申请信息
     * @param userId 用户id
     * @param status 审核状态
     */
    void deleteByUserIdAndStatus(@Param("userId") String userId,
                                 @Param("status") Integer status);

    /**
     * 根据医馆名称获取医馆认证信息
     * @param name 医馆名称
     * @return 医馆认证信息
     */
    MedicalHospitalApply findByName(String name);
}