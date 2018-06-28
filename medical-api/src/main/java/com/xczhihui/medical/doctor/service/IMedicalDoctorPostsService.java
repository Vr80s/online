package com.xczhihui.medical.doctor.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.doctor.model.MedicalDoctorPosts;

/**
 * Description：医师动态service
 * creed: Talk is cheap,show me the code
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/6/20 14:14
 **/
public interface IMedicalDoctorPostsService {

    /**
     * 医师动态列表
     *
     * @param page   分页参数
     * @param type 类型
     * @param doctorId 医师
     * @return 列表数据
     */
     Page<MedicalDoctorPosts> selectMedicalDoctorPostsPage(Page<MedicalDoctorPosts> page, Integer type, String doctorId,String accountId);

    /**
     * 添加医师动态
     *
     * @param medicalDoctorPosts 医师动态实体类
     * @return
     */
     void addMedicalDoctorPosts(MedicalDoctorPosts medicalDoctorPosts);

    /**
     * 编辑医师动态
     *
     * @param medicalDoctorPosts 医师动态实体类
     * @return
     */
    void updateMedicalDoctorPosts(MedicalDoctorPosts medicalDoctorPosts);

    /**
     * 删除医师动态
     *
     * @param id 医师动态id
     * @return
     */
    void deleteMedicalDoctorPosts(Integer id);

    /**
     * 置顶/取消置顶
     * @param id 医师动态id
     * @param stick 置顶状态
     * @return
     */
    void updateStickMedicalDoctorPosts(Integer id,Boolean stick);

    /**
     * 获取医师动态
     *
     * @param id 医师动态id
     * @return
     */
    MedicalDoctorPosts getMedicalDoctorPostsById(Integer id);

}
