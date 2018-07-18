package com.xczhihui.medical.doctor.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
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

    /**
     * 查找医师可预约的诊疗
     *
     * @param doctorId          doctorId
     * @param onlyUnAppointment onlyUnAppointment
     * @return
     */
    @Select({"<script>select * " +
            " from medical_treatment" +
            " where deleted = false and doctor_id = #{doctorId}" +
            " <if test='onlyUnAppointment'>" +
            " and status = 0" +
            " </if>" +
            " </script>"})
    List<TreatmentVO> listByDoctorId(@Param("doctorId") String doctorId, @Param("onlyUnAppointment") boolean onlyUnAppointment);
}
