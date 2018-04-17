package com.xczhihui.medical.doctor.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctorReport;

/**
 * @author hejiwei
 */
public interface MedicalDoctorReportMapper extends BaseMapper<MedicalDoctorReport> {

    /**
     * 文章id与医师id查找媒体报道的关系
     *
     * @param articleId 文章id
     * @param doctorId  医师id
     * @return 媒体报道关联关系数据
     */
    @Select({"select * from medical_doctor_report where article_id = #{articleId} and doctor_id = #{doctorId}"})
    MedicalDoctorReport findByArticleIdAndDoctorId(@Param("articleId") String articleId, @Param("doctorId") String doctorId);
}
