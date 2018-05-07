package com.xczhihui.medical.doctor.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.department.vo.MedicalDepartmentVO;
import com.xczhihui.medical.doctor.model.MedicalDoctor;
import com.xczhihui.medical.doctor.vo.MedicalDoctorVO;
import com.xczhihui.medical.doctor.vo.MedicalWritingVO;
import com.xczhihui.medical.field.vo.MedicalFieldVO;

/**
 * <p>
 * Mapper 接口
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

    List<MedicalWritingVO> getWritingsByDoctorId(String doctorId);

    MedicalWritingVO getWritingsDetailsById(String writingsId);

    List<MedicalWritingVO> getRecentlyWritings();

    List<MedicalDoctorVO> getHotSpecialColumnAuthor(String specialColumn);

    List<MedicalWritingVO> getWritingsByPage(Page<MedicalWritingVO> page);

    void insertSelective(@Param("medicalDoctor") MedicalDoctor medicalDoctor);

    /**
     * 获取医师的坐诊时间
     *
     * @author zhuwenbao
     */
    String getWorkTimeById(String doctorId);

    void updateSelective(@Param("medicalDoctor") MedicalDoctor medicalDoctor);

    List<MedicalDepartmentVO> getHotDepartment();

    List<MedicalDepartmentVO> selectMedicalDepartmentsByDoctorId(String id);

    int selectDoctorListCount(Integer type);

    int selectDoctorRecommendListCount(Integer type);

    List<MedicalDoctorVO> selectDoctorList4Random(@Param("type") Integer type, @Param("offset") int offset, @Param("rows") int rows);

    List<MedicalDoctorVO> selectDoctorRecommendList4Random(@Param("type") Integer type, @Param("offset") int offset, @Param("rows") int rows);

    /**
     * 通过类型查询医师数量
     *
     * @param type 类型
     * @return 数量
     */
    @Select("select count(*) from medical_doctor where deleted = 0 AND status = 1 and (#{type} is null OR type = #{type})")
    int countByType(@Param("type") String type);

    /**
     * 随机查询医师
     *
     * @param type   类型
     * @param offset 起始
     * @param row    行数
     * @return 数量
     */
    @Select({"select md.*, mdai.head_portrait headPortrait" +
            " FROM medical_doctor md" +
            " LEFT JOIN `medical_doctor_authentication_information` mdai" +
            " on md.`authentication_information_id` = mdai.`id`" +
            " where md.deleted = 0 AND md.status = 1 and (#{type} is null OR md.type = #{type}) limit #{offset},#{row}"})
    List<MedicalDoctorVO> selectRandomDoctorByType(@Param("type") String type, @Param("offset") int offset, @Param("row") int row);
}