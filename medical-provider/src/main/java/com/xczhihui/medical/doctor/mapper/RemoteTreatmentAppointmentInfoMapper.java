package com.xczhihui.medical.doctor.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.medical.doctor.model.TreatmentAppointmentInfo;

/**
 * @author mike
 */
public interface RemoteTreatmentAppointmentInfoMapper extends BaseMapper<TreatmentAppointmentInfo> {

    /**
     * 更新预约过期状态
     *
     * @param id id
     */
    @Update({"update medical_treatment_appointment_info set status = 5 where id = #{id}"})
    void updateRemoteTreatmentAppointmentInfoExpired(@Param("id") Integer id);
}
