package com.xczhihui.medical.doctor.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.medical.doctor.model.DoctorType;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface DoctorTypeMapper extends BaseMapper<DoctorType> {


    @Select("select id as code,title as value from doctor_type where status = 1 and is_delete = 0 ")
    List<Map<String,Object>> getDoctorTypeTitleList();
    
    @Select("select title as value from doctor_type where status = 1 and is_delete = 0  and id = #{id}")
    String getDoctorTypeTitleById(@Param("id") String id);
    
    
    @Select("select * from doctor_type where status = 1 and is_delete = 0 ")
    List<DoctorType> getDoctorTypeList();
    
}