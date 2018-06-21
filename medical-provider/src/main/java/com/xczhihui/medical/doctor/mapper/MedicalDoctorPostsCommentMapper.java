package com.xczhihui.medical.doctor.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctorPostsComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description：医师动态评论 Mapper 接口
 * creed: Talk is cheap,show me the code
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/6/21 9:47
 **/
public interface MedicalDoctorPostsCommentMapper extends BaseMapper<MedicalDoctorPostsComment> {

    /**
     * 通过医师动态id获取评论列表
     *
     * @param postsId   医师动态评论id
     * @return 列表数据
     */
    List<MedicalDoctorPostsComment> selectMedicalDoctorPostsCommentList(@Param("postsId") Integer postsId);

    /**
     * 添加医师动态评论
     *
     * @param medicalDoctorPostsComment   医师动态评论实体类
     * @return
     */
    void addMedicalDoctorPostsComment(@Param("medicalDoctorPostsComment") MedicalDoctorPostsComment medicalDoctorPostsComment);

    /**
     * 删除医师动态评论
     *
     * @param id   医师动态评论id
     * @return
     */
    void deleteMedicalDoctorPostsComment(@Param("id") Integer id);

    /**
     * 通过id获取评论
     *
     * @param id   医师动态评论id
     * @return
     */
    MedicalDoctorPostsComment getMedicalDoctorPostsCommentById(@Param("id") Integer id);


}