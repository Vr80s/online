package com.xczhihui.medical.hospital.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.field.vo.MedicalFieldVO;
import com.xczhihui.medical.hospital.model.MedicalHospital;
import com.xczhihui.medical.hospital.vo.MedicalHospitalVo;
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
public interface MedicalHospitalMapper extends BaseMapper<MedicalHospital> {

    List<String> selectHospitalIdList(@Param("page") Page<MedicalHospitalVo> page, @Param("name") String name, @Param("field") String field);

    List<MedicalHospitalVo> selectHospitalAndPictureList(List<String> mhIds);

    MedicalHospitalVo selectHospitalById(String id);

    List<MedicalHospitalVo> selectRecHospital();

    List<MedicalFieldVO> getHotField();

    List<MedicalFieldVO> selectMedicalFieldsByHospitalId(String hospitalId);

    boolean getAuthenticationById(String id);

    /**
     * 获取医疗领域
     * @return 医疗领域集合
     */
    List<MedicalFieldVO> getFieldsPage(Page page);

    /**
     * 修改医馆信息
     * @param medicalHospital 医馆信息的封装
     */
    void updateSelective(@Param("medicalHospital") MedicalHospital medicalHospital);

    /**
     * 根据医馆名称获取医馆信息
     * @param name 医馆名称
     * @return 医馆信息
     */
    MedicalHospital findByName(String name);
}