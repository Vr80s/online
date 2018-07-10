package com.xczhihui.medical.doctor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctorPostsLike;

/**
 * Description：医师动态点赞 Mapper 接口
 * creed: Talk is cheap,show me the code
 *
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/6/21 9:47
 **/
public interface MedicalDoctorPostsLikeMapper extends BaseMapper<MedicalDoctorPostsLike> {

    /**
     * 添加医师动态点赞
     *
     * @param postsId 医师动态id
     * @param userId  用户id
     * @param flag
     * @return
     */
    void addMedicalDoctorPostsLike(@Param("postsId") Integer postsId, @Param("userId") String userId, @Param("flag") Integer flag);

    /**
     * 获取医师动态点赞列表
     *
     * @param postsId 医师动态id
     * @return
     */
    List<MedicalDoctorPostsLike> getMedicalDoctorPostsLikeList(@Param("postsId") Integer postsId);

}