package com.xczhihui.medical.hospital.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.medical.hospital.model.MedicalHospitalPicture;
import com.xczhihui.medical.hospital.vo.MedicalHospitalPictureVO;
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
public interface MedicalHospitalPictureMapper extends BaseMapper<MedicalHospitalPicture> {

    public List<MedicalHospitalPictureVO> getMedicalHospitalPictureByHospitalId(String hospitalId);

    /**
     * 批量修改医馆图片
     */
    void insertBatch(@Param("pictures") List<MedicalHospitalPicture> medicalHospitalPictures);

    /**
     * 根据hospitalId修改删除状态
     */
    @Update("update medical_hospital_picture set deleted = #{deleted} where hospital_id = #{hospitalId}")
    void updateDeletedByHospitalId(@Param("hospitalId") String hospitalId, @Param("deleted") boolean deleted);
}