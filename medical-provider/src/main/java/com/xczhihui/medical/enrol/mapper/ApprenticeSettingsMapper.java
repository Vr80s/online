package com.xczhihui.medical.enrol.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.medical.enrol.model.ApprenticeSettings;

/**
 * @author hejiwei
 */
public interface ApprenticeSettingsMapper extends BaseMapper<ApprenticeSettings> {

    /**
     * 通过医师id查询收徒设置
     *
     * @param doctorId 医师id
     * @return
     */
    @Select({"select * from apprentice_settings where doctor_id = #{doctorId}"})
    ApprenticeSettings findByDoctorId(@Param("doctorId") String doctorId);
}
