package com.xczhihui.medical.enrol.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.enrol.model.MedicalEnrollmentRegulations;
import com.xczhihui.medical.enrol.vo.MedicalEnrollmenRtegulationsCardInfoVO;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2018-05-22
 */
public interface MedicalEnrollmentRegulationsMapper extends BaseMapper<MedicalEnrollmentRegulations> {

    @Select("SELECT mer.`id`,mer.`cover_img`,mer.`title` FROM medical_enrollment_regulations mer " +
            " WHERE mer.`deleted`=0 AND mer.`status`=1 ORDER BY mer.`create_time` DESC ")
    List<MedicalEnrollmentRegulations> selectListByPage(Page<MedicalEnrollmentRegulations> page);

    @Select("SELECT ou.`small_head_photo` FROM `oe_user` ou where ou.id=#{userId}")
    String selectUserPhoto4CardInfo(String userId);

    @Select("SELECT \n" +
            "  mer.`title`,  mer.`propaganda`,\n" +
            "  mer.`doctor_introduction` doctorIntroduction,\n" +
            "  mer.`tuition`,  mer.`count_limit` countLimit,\n" +
            "  mer.`deadline`,  mer.`study_address` studyAddress \n" +
            "FROM  `medical_enrollment_regulations` mer where mer.id = #{id}")
    MedicalEnrollmenRtegulationsCardInfoVO getMedicalEnrollmentRegulationsCardInfoById(Integer id);
}