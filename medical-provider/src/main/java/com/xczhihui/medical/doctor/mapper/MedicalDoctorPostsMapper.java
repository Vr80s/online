package com.xczhihui.medical.doctor.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.common.util.bean.ProductPostsVO;
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

    /**
     * 通过课程id获取医师动态
     *
     * @param courseId 课程id
     * @return
     */
    List<MedicalDoctorPosts> getMedicalDoctorPostsByCourseId(@Param("courseId") Integer courseId);
    
    
    
    @Select({" select \r\n" + 
    		"	 mdp.id,mdp.content,mdp.doctor_id doctorId,md.name doctorName,mdai.head_portrait doctorHeadImg,\r\n" + 
    		"	 mdp.product_id productId,mdp.level\r\n" + 
    		"from medical_doctor_posts mdp \r\n" + 
    		"	inner join medical_doctor md on mdp.doctor_id = md.id\r\n" + 
    		"	left join medical_doctor_authentication_information mdai on md.`authentication_information_id` = mdai.`id`\r\n" + 
    		"where mdp.product_id = #{infoId} order by mdp.create_time limit #{pageNumber},#{pageSize}  "})
    Set<Map<String,Object>> getProductPostsByProductId(@Param("productId") Integer productId,@Param("pageNumber") Integer pageNumber,
    		@Param("pageSize") Integer pageSize);
}