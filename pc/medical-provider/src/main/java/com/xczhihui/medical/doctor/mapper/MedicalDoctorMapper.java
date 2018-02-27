package com.xczhihui.medical.doctor.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.department.vo.MedicalDepartmentVO;
import com.xczhihui.medical.doctor.model.MedicalDoctor;
import com.xczhihui.medical.doctor.vo.MedicalDoctorVO;
import com.xczhihui.medical.doctor.vo.MedicalWritingsVO;
import com.xczhihui.medical.field.vo.MedicalFieldVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface MedicalDoctorMapper extends BaseMapper<MedicalDoctor> {

    List<MedicalDoctorVO> selectDoctorList(@Param("page") Page<MedicalDoctorVO> page, @Param("type") Integer type, @Param("hospitalId") String hospitalId, @Param("name") String name, @Param("field") String field, @Param("department") String department);

    MedicalDoctorVO selectDoctorById(String id);

    List<MedicalFieldVO> getHotField();

    List<MedicalDoctorVO> selectRecDoctor();

    List<MedicalFieldVO> selectMedicalFieldsByDoctorId(String doctorId);

    List<MedicalWritingsVO> getWritingsByDoctorId(String doctorId);

    MedicalWritingsVO getWritingsDetailsById(String writingsId);

    List<MedicalWritingsVO> getRecentlyWritings();

    List<MedicalDoctorVO> getHotSpecialColumnAuthor(String specialColumn);

    List<MedicalWritingsVO> getWritingsByPage(Page<MedicalWritingsVO> page);

    void insertSelective(@Param("medicalDoctor") MedicalDoctor medicalDoctor);

    /**
     * 获取医师的坐诊时间
     * @author zhuwenbao
     */
    String getWorkTimeById(String doctorId);

    void updateSelective(@Param("medicalDoctor") MedicalDoctor medicalDoctor);

    List<MedicalDepartmentVO> getHotDepartment();

    List<MedicalDepartmentVO> selectMedicalDepartmentsByDoctorId(String id);
}