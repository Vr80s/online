package com.xczhihui.medical.enrol.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
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
            "   goal, tel, wechat, create_time as createTime, apprentice, type, applied," +
            "  user_id userId " +
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

    @Select({"select count(id)" +
            " from medical_entry_information" +
            " where doctor_id = #{doctorId} and user_id = #{accountId} and apprentice = 1"})
    Integer countByDoctorIdAndAccountId(@Param("doctorId") String doctorId, @Param("accountId") String accountId);

    @Select({"SELECT mei.name,mei.user_id userId,(ISNULL(ct.id)=0) selected " +
            " FROM  medical_entry_information mei" +
            "  LEFT JOIN `course_teaching` ct" +
            "  ON mei.`user_id` = ct.`user_id` AND ct.`course_id` = #{courseId} AND ct.`deleted`=0" +
            " WHERE mei.deleted = 0 AND mei.`doctor_id`=#{doctorId} " +
            " ORDER BY mei.create_time DESC"})
    List<Map<String,String>> listByDoctorIdAndCourseId(@Param("doctorId") String doctorId, @Param("courseId") String courseId);

    @Select("UPDATE `course_teaching` ct SET ct.`deleted`=1 WHERE ct.`course_id`=#{courseId}")
    void deleteCourseTeachingByCourseId(@Param("courseId") String courseId);

    @Insert("<script>"+
            "insert into course_teaching(course_id, user_id) "
            + "values "
            + "<foreach collection =\"userIds\" item=\"userId\" index= \"index\" separator =\",\"> "
            + "(#{courseId},#{userId}) "
            + "</foreach > "
            + "</script>")
    void saveCourseTeaching(@Param("courseId") String courseId, @Param("userIds") List<String> userIds);

    @Select("SELECT COUNT(oe.id) FROM `oe_course` oe JOIN `medical_doctor_account` mda ON oe.`user_lecturer_id`=mda.`account_id` " +
            "AND mda.`doctor_id`=#{doctorId} AND oe.id=#{courseId} ")
    int checkCourseDoctor(@Param("doctorId") String doctorId, @Param("courseId") String courseId);
}