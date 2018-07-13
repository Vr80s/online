package com.xczhihui.medical.enrol.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.enrol.model.MedicalEntryInformation;
import com.xczhihui.medical.enrol.vo.MedicalEntryInformationVO;
import com.xczhihui.medical.headline.vo.SimpleUserVO;

/**
 * <p>
 * Mapper 接口
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

    @Select("SELECT mdai.`head_portrait` headPortrait,md.name  FROM `medical_enrollment_regulations` mer JOIN `medical_doctor` md ON mer.`doctor_id`=md.id" +
            " JOIN `medical_doctor_authentication_information` mdai ON md.`authentication_information_id`=mdai.`id`" +
            " where mer.id=#{id}")
    Map<String, String> getDoctorPhoto(Integer id);


    /**
     * 查询报名信息
     *
     * @param type       收徒类型
     * @param doctorId   医师
     * @param apprentice 是否是弟子
     * @param page       分页参数
     * @return
     */
    @Select({"<script> " +
            " SELECT id, name, age, sex, native_place as nativePlace," +
            "   education, education_experience as educationExperience, medical_experience as medicalExperience," +
            "   goal, tel, wechat, create_time as createTime, apprentice, type, applied" +
            " FROM medical_entry_information " +
            " WHERE doctor_id = #{doctorId} AND deleted=0 " +
            " <if test='type != null'>" +
            " AND type = #{type}" +
            " </if>" +
            " <if test='apprentice != null'>" +
            " AND apprentice = #{apprentice}" +
            " </if>" +
            " ORDER BY create_time DESC " +
            " </script>"})
    List<MedicalEntryInformationVO> listEntryInformationByDoctorId(@Param("doctorId") String doctorId, @Param("type") Integer type,
                                                                   @Param("apprentice") Integer apprentice, Page<MedicalEntryInformationVO> page);

    @Select({"select * from medical_entry_information where user_id = #{userId} and mer_id = #{merId} limit 1"})
    MedicalEntryInformation findOne(@Param("userId") String userId, @Param("merId") Integer merId);

    @Select({"select ou.id, ou.name, ou.small_head_photo as smallHeadPhoto" +
            " from medical_entry_information mei, oe_user ou" +
            " where mei.doctor_id = #{doctorId} and mei.apprentice = 1 and mei.user_id = ou.id" +
            " group by mei.user_id" +
            " order by mei.id desc"})
    List<SimpleUserVO> findApprenticesByDoctorId(@Param("doctorId") String doctorId);
}