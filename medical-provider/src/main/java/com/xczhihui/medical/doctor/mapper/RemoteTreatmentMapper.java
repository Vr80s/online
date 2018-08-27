package com.xczhihui.medical.doctor.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.doctor.model.Treatment;
import com.xczhihui.medical.doctor.vo.TreatmentVO;

/**
 * @author hejiwei
 */
public interface RemoteTreatmentMapper extends BaseMapper<Treatment> {

    /**
     * 查询重复的数据条数
     *
     * @param date
     * @param startTime
     * @param endTime
     * @param id
     * @param doctorId
     * @return
     */
    @Select({"<script>" +
            "   select count(id)" +
            "   from medical_treatment" +
            "   where date = #{date} and doctor_id = #{doctorId} " +
            "   and ((start_time &gt;= #{startTime} and start_time &lt;= #{endTime}) or (start_time &lt;= #{startTime} and end_time &gt;= #{endTime}) or (end_time &gt;= #{startTime} and end_time &lt;= #{endTime}))" +
            "   and deleted = false" +
            "   <if test='id!=null'> " +
            "       and id != #{id}" +
            "   </if> " +
            "</script>"})
    Integer countRepeatByDate(@Param("date") Date date, @Param("startTime") Date startTime,
                              @Param("endTime") Date endTime, @Param("id") Integer id, @Param("doctorId") String doctorId);

    @Select({"<script>" +
            "   select count(mt.id)" +
            "   from medical_treatment mt join medical_treatment_appointment_info mtai on mt.info_id = mtai.id" +
            "   where mt.date = #{date} and mtai.user_id = #{userId}" +
            "   and ((mt.start_time &gt;= #{startTime} and mt.start_time &lt;= #{endTime}) or (mt.start_time &lt;= #{startTime}" +
            " and mt.end_time &gt;= #{endTime}) or (mt.end_time &gt;= #{startTime} and mt.end_time &lt;= #{endTime}))" +
            "   and mt.deleted = false" +
            "</script>"})
    Integer countUserAppointRepeatByDate(@Param("date") Date date, @Param("startTime") Date startTime,
                                         @Param("endTime") Date endTime, @Param("userId") String userId);

    /**
     * 查找医师可预约的诊疗
     *
     * @param doctorId          doctorId
     * @param onlyUnAppointment onlyUnAppointment
     * @return
     */
    @Select({"<script>select mt.id, mt.doctor_id as doctorId, mt.date as date, mt.start_time as startTime, mt.end_time as endTime," +
            " mt.create_time as createTime, mt.status, mt.info_id as infoId, mtai.user_id as userId " +
            " from medical_treatment mt left join medical_treatment_appointment_info mtai on mt.info_id = mtai.id" +
            " where mt.deleted = false and mt.doctor_id = #{doctorId} and mt.date &gt; curdate()" +
            " <if test='onlyUnAppointment'>" +
            " and mt.status = 0" +
            " </if>" +
            " <if test='!onlyUnAppointment'>" +
            " and mt.status != 5" +
            " </if>" +
            " order by mt.date, mt.start_time" +
            " </script>"})
    List<TreatmentVO> listByDoctorId(@Param("doctorId") String doctorId, @Param("onlyUnAppointment") boolean onlyUnAppointment);

    @Select({"<script>select mt.*, mtai.name, mtai.tel, mtai.question, mtai.apprentice_id as apprenticeId" +
            " from medical_treatment mt left join medical_treatment_appointment_info mtai on mt.info_id = mtai.id" +
            " where mt.deleted = false and mt.doctor_id = #{doctorId}" +
            " order by mt.create_time desc" +
            " </script>"})
    List<TreatmentVO> listPageByDoctorId(@Param("doctorId") String doctorId, Page<TreatmentVO> page);

    /**
     * 查询医师诊疗预约
     *
     * @param infoId infoId
     * @return
     */
    @Select({"select mtai.name, mtai.tel, mtai.question, mtai.date as date, mtai.start_time as startTime, mtai.end_time as endTime, mtai.id as infoId, " +
            " mt.status as treatmentStatus, mtai.status, mtai.status as infoStatus, mt.course_id as courseId" +
            " from medical_treatment_appointment_info mtai left join medical_treatment mt on mt.info_id = mtai.id" +
            " where mtai.id = #{infoId}"})
    TreatmentVO findByInfoId(@Param("infoId") Integer infoId);

    /**
     * 医师诊疗
     *
     * @param doctorId doctorId
     * @param page     page
     * @return
     */
    @Select({"(select mt.status, mt.date as date, mt.start_time as startTime, mt.end_time as endTime, mtai.name as nickname,ou.`small_head_photo` avatar, mt.id, mt.info_id as infoId \n" +
            "             from medical_treatment_appointment_info mtai join medical_treatment mt on mtai.id = mt.info_id join oe_user ou on mtai.user_id = ou.id \n" +
            "             where mt.doctor_id = #{doctorId} and mt.status = 2 and (CURRENT_TIMESTAMP >= mt.treatment_start_time - INTERVAL 10 MINUTE) and (CURRENT_TIMESTAMP <= mt.treatment_start_time)" +
            " and mtai.deleted is false and mt.deleted is false\n" +
            "             order by mt.date asc, mt.start_time asc)\n" +
            " union all \n" +
            "(select mt.status, mt.date as date, mt.start_time as startTime, mt.end_time as endTime, mtai.name as nickname,ou.`small_head_photo` avatar, mt.id, mt.info_id as infoId \n" +
            "             from medical_treatment_appointment_info mtai join medical_treatment mt on mtai.id = mt.info_id join oe_user ou on mtai.user_id = ou.id \n" +
            "             where mt.doctor_id = #{doctorId} and mt.status = 3 and mtai.deleted is false and mt.deleted is false\n" +
            "             order by mt.date asc, mt.start_time asc)\n" +
            "             union all \n" +
            "(select mt.status, mt.date as date, mt.start_time as startTime, mt.end_time as endTime, mtai.name as nickname,ou.`small_head_photo` avatar, mt.id, mt.info_id as infoId \n" +
            "             from medical_treatment_appointment_info mtai join medical_treatment mt on mtai.id = mt.info_id join oe_user ou on mtai.user_id = ou.id \n" +
            "             where mt.doctor_id = #{doctorId} and mt.status = 2 and mtai.deleted is false and mt.deleted is false\n" +
            "             order by mt.date asc, mt.start_time asc)\n" +
            " union all \n" +
            "(select mt.status, mt.date as date, mt.start_time as startTime, mt.end_time as endTime, mtai.name as nickname,ou.`small_head_photo` avatar, mt.id, mt.info_id as infoId \n" +
            "             from medical_treatment_appointment_info mtai join medical_treatment mt on mtai.id = mt.info_id join oe_user ou on mtai.user_id = ou.id \n" +
            "             where mt.doctor_id = #{doctorId} and mt.status = 1 and mtai.deleted is false and mt.deleted is false\n" +
            "             order by mt.date asc, mt.start_time asc)\n" +
            " union all \n" +
            " (select mt.status, mt.date as date, mt.start_time as startTime, mt.end_time as endTime, mtai.name as nickname,ou.`small_head_photo` avatar, mt.id, mt.info_id as infoId \n" +
            "             from medical_treatment_appointment_info mtai join medical_treatment mt on mtai.id = mt.info_id join oe_user ou on mtai.user_id = ou.id \n" +
            "             where mt.doctor_id = #{doctorId} and mt.status = 5 and mtai.deleted is false and mt.deleted is false\n" +
            "             order by mt.date desc, mt.start_time desc)\n" +
            "  union all \n" +
            "(select mt.status, mt.date as date, mt.start_time as startTime, mt.end_time as endTime, mtai.name as nickname,ou.`small_head_photo` avatar, mt.id, mt.info_id as infoId \n" +
            "             from medical_treatment_appointment_info mtai join medical_treatment mt on mtai.id = mt.info_id join oe_user ou on mtai.user_id = ou.id \n" +
            "             where mt.doctor_id = #{doctorId} and mt.status = 4 and mtai.deleted is false and mt.deleted is false\n" +
            "             order by mt.date desc, mt.start_time desc)"})
    List<TreatmentVO> selectTreatmentByDoctorId(@Param("doctorId") String doctorId, Page<TreatmentVO> page);


    /**
     * 用户的预约列表
     *
     * @param userId userId
     * @param page   page
     * @return
     */
    @Select({"(select mtai.status, mtai.date as date, mtai.start_time as startTime, mtai.end_time as endTime, doctor.name as nickname,doctor.`avatar` avatar, doctor.title as title, mtai.id, mt.course_id as courseId, mtai.id as infoId\n" +
            "                        from medical_treatment_appointment_info mtai\n" +
            "                           join medical_treatment mt \n" +
            "                               on mtai.treatment_id = mt.id\n" +
            "                           join (select md.id, md.name, mdai.`head_portrait` as avatar, md.title from medical_doctor md join medical_doctor_authentication_information mdai on md.`authentication_information_id` = mdai.id) as doctor on doctor.id = mt.doctor_id\n" +
            "                        where  mtai.user_id = #{userId} and mtai.status = 2 and mtai.deleted is false \n" +
            "                        order by mtai.date asc, mtai.start_time asc)\n" +
            "             union all\n" +
            "         ( select mtai.status, mtai.date as date, mtai.start_time as startTime, mtai.end_time as endTime, doctor.name as nickname,doctor.`avatar` avatar, doctor.title as title,mtai.id, mt.course_id as courseId, mtai.id as infoId\n" +
            "                        from medical_treatment_appointment_info mtai\n" +
            "                           join medical_treatment mt \n" +
            "                               on mtai.treatment_id = mt.id\n" +
            "                           join (select md.id, md.name, mdai.`head_portrait` as avatar, md.title from medical_doctor md join medical_doctor_authentication_information mdai on md.`authentication_information_id` = mdai.id) as doctor on doctor.id = mt.doctor_id\n" +
            "                        where mtai.user_id = #{userId} and mtai.status = 6 and mtai.deleted is false \n" +
            "                        order by mtai.date asc, mtai.start_time asc\n" +
            "         ) \n" +
            "         union all (\n" +
            "         \t select mtai.status, mtai.date as date, mtai.start_time as startTime, mtai.end_time as endTime, doctor.name as nickname,doctor.`avatar` avatar, doctor.title as title,mtai.id, mt.course_id as courseId, mtai.id as infoId\n" +
            "                        from medical_treatment_appointment_info mtai\n" +
            "                           join medical_treatment mt \n" +
            "                               on mtai.treatment_id = mt.id\n" +
            "                           join (select md.id, md.name, mdai.`head_portrait` as avatar, md.title from medical_doctor md join medical_doctor_authentication_information mdai on md.`authentication_information_id` = mdai.id) as doctor on doctor.id = mt.doctor_id\n" +
            "                        where  mtai.user_id = #{userId} and mtai.status = 1 and mtai.deleted is false \n" +
            "                        order by mtai.date asc, mtai.start_time asc\n" +
            "         )\n" +
            "         union all (\n" +
            "         \t select mtai.status, mtai.date as date, mtai.start_time as startTime, mtai.end_time as endTime, doctor.name as nickname,doctor.`avatar` avatar,doctor.title as title, mtai.id, mt.course_id as courseId, mtai.id as infoId\n" +
            "                        from medical_treatment_appointment_info mtai\n" +
            "                           join medical_treatment mt \n" +
            "                               on mtai.treatment_id = mt.id\n" +
            "                           join (select md.id, md.name, mdai.`head_portrait` as avatar, md.title from medical_doctor md join medical_doctor_authentication_information mdai on md.`authentication_information_id` = mdai.id) as doctor on doctor.id = mt.doctor_id\n" +
            "                        where mtai.user_id = #{userId} and mtai.status = 5 and mtai.deleted is false \n" +
            "                        order by mtai.date desc, mtai.start_time desc\n" +
            "         )\n" +
            "         union all (\n" +
            "         \t select mtai.status, mtai.date as date, mtai.start_time as startTime, mtai.end_time as endTime, doctor.name as nickname,doctor.`avatar` avatar, doctor.title as title, mtai.id, mt.course_id as courseId, mtai.id as infoId\n" +
            "                        from medical_treatment_appointment_info mtai\n" +
            "                           join medical_treatment mt \n" +
            "                               on mtai.treatment_id = mt.id\n" +
            "                           join (select md.id, md.name, mdai.`head_portrait` as avatar, md.title from medical_doctor md join medical_doctor_authentication_information mdai on md.`authentication_information_id` = mdai.id) as doctor on doctor.id = mt.doctor_id\n" +
            "                        where mtai.user_id = #{userId} and mtai.status = 4 and mtai.deleted is false \n" +
            "                        order by mtai.date desc, mtai.start_time desc\n" +
            "         )\n"})
    List<TreatmentVO> selectTreatmentByUserId(@Param("userId") String userId, Page<TreatmentVO> page);

    @Select({"select * from medical_treatment where (status = 0 OR status = 1 OR status = 2) and date <= curdate() and deleted is false"})
    List<Treatment> selectUpcomingExpire();

    /**
     * <p>Title: 取消远程诊疗后，禁用这个课程</p>
     * <p>Description: </p>
     *
     * @param courseId
     */
    @Update({" update oe_course set status = 0 where id = #{courseId} "})
    void updateCourseStatusForDisable(@Param("courseId") Integer courseId);

    /**
     * 查询全部的诊疗数据
     *
     * @return
     */
    @Select({"select * from medical_treatment"})
    List<Treatment> listAll();
}
