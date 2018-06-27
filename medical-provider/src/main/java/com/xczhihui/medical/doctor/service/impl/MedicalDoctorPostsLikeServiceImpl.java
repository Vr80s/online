package com.xczhihui.medical.doctor.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorPostsLikeMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctorPostsLike;
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description：医师动态点赞 服务实现类
 * creed: Talk is cheap,show me the code
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/6/21 9:47
 **/
@Service
public class MedicalDoctorPostsLikeServiceImpl extends ServiceImpl<MedicalDoctorPostsLikeMapper, MedicalDoctorPostsLike> implements IMedicalDoctorPostsLikeService {

    @Autowired
    private MedicalDoctorPostsLikeMapper medicalDoctorPostsLikeMapper;

    @Override
    public void addMedicalDoctorPostsLike(Integer postsId, String userId, Integer flag) {
        medicalDoctorPostsLikeMapper.addMedicalDoctorPostsLike(postsId,userId,flag);
    }

    @Override
    public void deleteMedicalDoctorPostsLike(Integer id,String userId) {
        medicalDoctorPostsLikeMapper.deleteMedicalDoctorPostsLike(id,userId);
    }

    @Override
    public List<MedicalDoctorPostsLike> getMedicalDoctorPostsLikeList(Integer postsId) {
        return medicalDoctorPostsLikeMapper.getMedicalDoctorPostsLikeList(postsId);
    }
}
