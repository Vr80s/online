package com.xczhihui.medical.doctor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.doctor.model.MedicalDoctorPosts;

/**
 * Description：医师动态 Mapper 接口
 * creed: Talk is cheap,show me the code
 *
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/6/20 14:35
 **/
public interface MedicalDoctorPostsMapper extends BaseMapper<MedicalDoctorPosts> {

    /**
     * 医师动态列表
     *
     * @param page     分页参数
     * @param type     类型
     * @param doctorId 医师
     * @return 列表数据
     */
    List<MedicalDoctorPosts> selectMedicalDoctorPostsPage(@Param("page") Page<MedicalDoctorPosts> page, @Param("type") Integer type, @Param("doctorId") String doctorId);

    /**
     * 添加医师动态
     *
     * @param medicalDoctorPosts 医师动态实体类
     * @return
     */
    void addMedicalDoctorPosts(@Param("medicalDoctorPosts") MedicalDoctorPosts medicalDoctorPosts);

    /**
     * 编辑医师动态
     *
     * @param medicalDoctorPosts 医师动态实体类
     * @return
     */
    void updateMedicalDoctorPosts(@Param("medicalDoctorPosts") MedicalDoctorPosts medicalDoctorPosts);

    /**
     * 删除医师动态
     *
     * @param id 医师动态id
     * @return
     */
    void deleteMedicalDoctorPosts(@Param("id") Integer id);

    /**
     * 置顶/取消置顶
     *
     * @param id    医师动态id
     * @param stick 置顶状态
     * @return
     */
    void updateStickMedicalDoctorPosts(@Param("id") Integer id, @Param("stick") Boolean stick);

    /**
     * 获取医师动态
     *
     * @param id 医师动态id
     * @return
     */
    MedicalDoctorPosts getMedicalDoctorPostsById(@Param("id") Integer id);

}