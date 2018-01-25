package com.xczhihui.medical.hospital.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.medical.hospital.model.MedicalHospitalField;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface MedicalHospitalFieldMapper extends BaseMapper<MedicalHospitalField> {

    /**
     * 根据hospitalId修改删除状态
     * @author zhuwenbao
     */
    @Update("update medical_hospital_field set deleted = #{deleted} where hospital_id = #{hospitalId} ")
    void updateDeletedByHospitalId(@Param("hospitalId") String hospitalId, @Param("deleted") boolean deleted);

    /**
     * 批量新增医馆的医疗领域
     * @param hospitalFields 医疗领域列表
     */
    void insertBatch(@Param("fields") List<MedicalHospitalField> hospitalFields);
}