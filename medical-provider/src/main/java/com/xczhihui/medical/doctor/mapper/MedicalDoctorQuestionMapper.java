package com.xczhihui.medical.doctor.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.doctor.model.MedicalDoctorQuestion;
import com.xczhihui.medical.doctor.vo.DoctorQuestionVO;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface MedicalDoctorQuestionMapper extends BaseMapper<MedicalDoctorQuestion> {

    List<MedicalDoctorQuestion> selectQuestionByDoctorId(@Param("page")Page<MedicalDoctorQuestion> page,
            @Param("doctorId") String doctorId);

    List<MedicalDoctorQuestion> selectDoctorQuestionByUserId(@Param("page")Page<MedicalDoctorQuestion> page,
            @Param("userId") String userId);

    void updateQuestion(@Param("doctorQuestionVO")DoctorQuestionVO doctorQuestionVO);
    

}