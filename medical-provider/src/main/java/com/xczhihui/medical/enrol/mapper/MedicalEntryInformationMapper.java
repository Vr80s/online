package com.xczhihui.medical.enrol.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.medical.enrol.model.MedicalEntryInformation;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2018-05-22
 */
public interface MedicalEntryInformationMapper extends BaseMapper<MedicalEntryInformation> {

    @Select("SELECT ou.`small_head_photo` FROM `medical_entry_information` mei JOIN oe_user ou ON mei.`user_id` = ou.`id` and mei.mer_id=#{id} limit 0,7")
    List<String> getAllEnrolledUser(Integer id);

    @Select("SELECT count(*) FROM `medical_entry_information` mei JOIN oe_user ou ON mei.`user_id` = ou.`id` and mei.mer_id=#{id}")
    int getAllEnrolledUserCount(Integer id);

    @Select("SELECT mdai.`head_portrait` FROM `medical_enrollment_regulations` mer JOIN `medical_doctor` md ON mer.`doctor_id`=md.id" +
            " JOIN `medical_doctor_authentication_information` mdai ON md.`authentication_information_id`=mdai.`id`" +
            " where mer.id=#{id}")
    String getDoctorPhoto(Integer id);
}