package com.xczhihui.medical.doctor.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
    @Select({"select mtai.name, mtai.tel, mtai.question, mt.date as date, mt.start_time as startTime, mt.end_time as endTime" +
            " from medical_treatment_appointment_info mtai left join medical_treatment mt on mt.info_id = mtai.id" +
            " where mtai.id = #{infoId}"})
    TreatmentVO findByInfoId(@Param("infoId") Integer infoId);
}
