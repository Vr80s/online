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
     * @param type     收徒类型(null -> 全部 1 -> 发现页收徒 2->师承页收徒)
     * @param doctorId 医师
     * @param status   状态 null -> 全部 0 -> 待审核 1->审核不通过 2->弟子
     * @param page     分页参数
     * @return
     */
    @Select({"<script> " +
            " SELECT mei.id, mei.name, mei.age, mei.sex, mei.native_place as nativePlace," +
            "   mei.education, mei.education_experience as educationExperience, mei.medical_experience as medicalExperience," +
            "   mei.goal, mei.tel, mei.wechat, mei.create_time as createTime, mei.apprentice, mei.type, mei.applied, mer.title as regulationName, mei.user_id userId " +
            " FROM medical_entry_information mei left join medical_enrollment_regulations mer on mei.mer_id = mer.id" +
            " WHERE mei.doctor_id = #{doctorId} AND mei.deleted=0 " +
            " <if test='type != null'>" +
            " AND mei.type = #{type}" +
            " </if>" +
            " <if test='status != null'>" +
            " <if test='status == 0'>" +
            " AND mei.applied = false" +
            " </if>" +
            " <if test='status == 1'>" +
            " AND mei.applied = true AND mei.apprentice = 0" +
            " </if>" +
            " <if test='status == 2'>" +
            " AND mei.apprentice = 1" +
            " </if>" +
            " </if>" +
            " ORDER BY mei.create_time DESC " +
            " </script>"})
    List<MedicalEntryInformationVO> listEntryInformationByDoctorId(@Param("doctorId") String doctorId, @Param("type") Integer type,
                                                                   @Param("status") Integer status, Page<MedicalEntryInformationVO> page);

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
            " FROM (select * from medical_entry_information where deleted = 0 AND apprentice = 1 order by `create_time` ) mei" +
            "  LEFT JOIN `course_teaching` ct" +
            "  ON mei.`user_id` = ct.`user_id` AND ct.`course_id` = #{courseId} AND ct.`deleted`=0" +
            " WHERE mei.`doctor_id`=#{doctorId} " +
            "  group by mei.user_id " +
            " ORDER BY mei.create_time DESC"})
    List<Map<String, String>> listByDoctorIdAndCourseId(@Param("doctorId") String doctorId, @Param("courseId") String courseId);

    @Select("UPDATE `course_teaching` ct SET ct.`deleted`=1 WHERE ct.`course_id`=#{courseId}")
    void deleteCourseTeachingByCourseId(@Param("courseId") String courseId);

    @Insert("<script>" +
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

    /**
     * 查询用户在该医师下的弟子信息
     *
     * @param doctorId  doctorId
     * @param accountId accountId
     * @return
     */
    @Select({"select name, tel, id as apprenticeId" +
            " from medical_entry_information" +
            " where doctor_id = #{doctorId} and user_id = #{accountId} and apprentice = 1" +
            " order by id desc" +
            " limit 1"})
    Map<String, Object> findApprenticeInfo(@Param("doctorId") String doctorId, @Param("accountId") String accountId);

    /**
     * 查询在医师下弟子申请的待审核数
     *
     * @param doctorId  doctorId
     * @param accountId accountId
     * @return
     */
    @Select({"SELECT count(id) FROM medical_entry_information where doctor_id = #{doctorId} and user_id = #{accountId} and applied = false"})
    Integer countApplyingEntryInformation(@Param("doctorId") String doctorId, @Param("accountId") String accountId);

    /**
     * 查询用户是否有权限参与跟师直播
     *
     * @param courseId courseId
     * @param userId   userId
     * @return
     */
    @Select({"select count(id) from course_teaching where course_id = #{courseId} and user_id = #{userId} and deleted = false"})
    Integer countCourseTeaching(@Param("courseId") Integer courseId, @Param("userId") String userId);

    /**
     * 查询医师的弟子数
     *
     * @param doctorId doctorId
     * @return
     */
    @Select({"select count(distinct user_id)" +
            " from medical_entry_information" +
            " where doctor_id = #{doctorId} and apprentice = 1"})
    Integer countApprenticeByDoctorId(@Param("doctorId") String doctorId);

    @Select({"<script> " +
            " SELECT mei.id, mei.name, mei.age, mei.sex, mei.native_place as nativePlace," +
            "   mei.education, mei.education_experience as educationExperience, mei.medical_experience as medicalExperience," +
            "   mei.goal, mei.tel, mei.wechat, mei.create_time as createTime, mei.apprentice, mei.type, mei.applied, mer.title as regulationName, mei.user_id userId " +
            " FROM medical_entry_information mei left join medical_enrollment_regulations mer on mei.mer_id = mer.id" +
            " WHERE mei.id = #{id}" +
            " </script>"})
    MedicalEntryInformationVO findById(@Param("id") Integer id);
}