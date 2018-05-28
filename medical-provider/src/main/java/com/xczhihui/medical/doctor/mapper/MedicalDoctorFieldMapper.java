package com.xczhihui.medical.doctor.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctorField;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface MedicalDoctorFieldMapper extends BaseMapper<MedicalDoctorField> {

    /**
     * 批量新增
     * @param fields 领域集合
     */
    void insertBatch(@Param("fields") List<MedicalDoctorField> fields);
}