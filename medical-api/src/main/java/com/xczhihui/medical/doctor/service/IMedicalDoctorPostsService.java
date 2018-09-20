package com.xczhihui.medical.doctor.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.doctor.model.MedicalDoctorPosts;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Description：医师动态service
 * creed: Talk is cheap,show me the code
 *
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/6/20 14:14
 **/
public interface IMedicalDoctorPostsService {

    /**
     * 医师动态列表
     *
     * @param page     分页参数
     * @param type     类型
     * @param doctorId 医师
     * @return 列表数据
     */
    Page<MedicalDoctorPosts> selectMedicalDoctorPostsPage(Page<MedicalDoctorPosts> page, Integer type, String doctorId, String accountId);

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
     *
     * @param id    医师动态id
     * @param stick 置顶状态
     * @return
     */
    void updateStickMedicalDoctorPosts(Integer id, Boolean stick);

    /**
     * 获取医师动态
     *
     * @param id 医师动态id
     * @return
     */
    MedicalDoctorPosts getMedicalDoctorPostsById(Integer id);

    /**
     * 通过课程id获取医师动态
     *
     * @param courseId 课程id
     * @return
     */
    List<MedicalDoctorPosts> getMedicalDoctorPostsByCourseId(Integer courseId);

    /**
     * 上下架时添加动态
     *
     * @param userId 用户id
     * @param courseId 课程id
     * @param articleId 文章id
     * @param courseName 课程名
     * @param subtitle 课程副标题
     * @return
     */
    void addDoctorPosts(String userId,Integer courseId,Integer articleId,String courseName,String subtitle, Integer appointmentInfoId);
    
    /**
     * 
     * <p>Title: 医师推荐商品</p>  
     * <p>Description: </p>  
     * @param userId    医师管理的用户id
     * @param productId 商品id
     * @param level		推荐级别	
     */
    void addDoctorPosts(String userId,String conetent,Long productId,Integer level);

	/**  
	 * <p>Title: getProductPostsByProductId</p>  
	 * <p>Description: </p>  
	 * @param productId
	 * @param pageNumber
	 * @param pageSize
	 * @return  
	 */ 
    Set<Map<String,Object>> getProductPostsByProductId(Long productId, Integer pageNumber, Integer pageSize);

}
