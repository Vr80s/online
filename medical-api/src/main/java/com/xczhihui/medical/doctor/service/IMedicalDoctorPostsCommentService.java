package com.xczhihui.medical.doctor.service;

import com.xczhihui.medical.doctor.model.MedicalDoctorPostsComment;

import java.util.List;

/**
 * Description：医师动态评论service
 * creed: Talk is cheap,show me the code
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/6/21 9:47
 **/
public interface IMedicalDoctorPostsCommentService {

    /**
     * 通过医师动态id获取评论列表
     *
     * @param postsId   医师动态评论id
     * @return 列表数据
     */
     List<MedicalDoctorPostsComment> selectMedicalDoctorPostsCommentList(Integer postsId,String userId);

    /**
     * 添加医师动态评论
     *
     * @param medicalDoctorPostsComment   医师动态评论实体类
     * @return
     */
    void addMedicalDoctorPostsComment(MedicalDoctorPostsComment medicalDoctorPostsComment,String userId);

    /**
     * 删除医师动态评论
     *
     * @param id   医师动态评论id
     * @return
     */
    void deleteMedicalDoctorPostsComment(Integer id);


}
