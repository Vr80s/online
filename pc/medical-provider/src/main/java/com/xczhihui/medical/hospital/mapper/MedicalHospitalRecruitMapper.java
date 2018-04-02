package com.xczhihui.medical.hospital.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.hospital.model.MedicalHospitalRecruit;
import com.xczhihui.medical.hospital.vo.MedicalHospitalRecruitVO;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2017-12-20
 */
public interface MedicalHospitalRecruitMapper extends BaseMapper<MedicalHospitalRecruit> {

    List<MedicalHospitalRecruitVO> selectRecHospitalRecruit();

    List<MedicalHospitalRecruitVO> selectHospitalRecruitByHospitalId(String hospitalId);

    /**
     * 分页查询医馆招聘信息
     *
     * @param hospitalId 医馆id
     * @param keyword    关键字
     * @param page       分页参数
     * @return
     */
    @Select("select * from" +
            " medical_hospital_recruit" +
            " where" +
            " hospital_id = #{hospitalId} and deleted = false and (#{keyword} is null or position like #{keyword})" +
            " order by sort desc")
    List<MedicalHospitalRecruitVO> selectPageByHospitalId(@Param("hospitalId") String hospitalId, @Param("keyword") String keyword,
                                                          Page<MedicalHospitalRecruitVO> page);

    /**
     * 查询当前最大sort值
     *
     * @return max sort
     */
    @Select("select max(sort) from medical_hospital_recruit")
    Integer maxSort();

    /**
     * 更新招聘信息的状态
     *
     * @param id     id
     * @param status 状态
     * @return 更新的行数
     */
    @Update("update medical_hospital_recruit set status = #{status} where id = #{id} and deleted = false and status != #{status}")
    int updateStatus(@Param("id") String id, @Param("status") boolean status);

    /**
     * 招聘信息标记删除
     *
     * @param id id
     * @return 更新行数
     */
    @Update("update medical_hospital_recruit set deleted = true where id = #{id} and deleted != true")
    int markDeleted(@Param("id") String id);
}