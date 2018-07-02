package com.xczhihui.medical.doctor.service;

import java.util.Map;

/**
 * Description：医师动态点赞 service
 * creed: Talk is cheap,show me the code
 *
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/6/21 9:47
 **/
public interface IMedicalDoctorPostsLikeService {

    /**
     * 添加医师动态点赞
     *
     * @param postsId 医师动态id
     * @param userId  用户id
     * @param flag
     * @return
     */
    void addMedicalDoctorPostsLike(Integer postsId, String userId, Integer flag);

    /**
     * 删除医师动态点赞
     *
     * @param id 医师动态点赞 id
     * @return
     */
    void deleteMedicalDoctorPostsLike(Integer id, String userId);

    /**
     * 获取医师动态点赞列表
     *
     * @param postsId 医师动态id
     * @return
     */
    Map<String, Object> getMedicalDoctorPostsLikeList(Integer postsId, String userId);


}
