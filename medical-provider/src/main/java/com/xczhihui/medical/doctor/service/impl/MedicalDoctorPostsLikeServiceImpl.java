package com.xczhihui.medical.doctor.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorPostsLikeMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctorPostsLike;
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsLikeService;

/**
 * Description：医师动态点赞 服务实现类
 * creed: Talk is cheap,show me the code
 *
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/6/21 9:47
 **/
@Service
public class MedicalDoctorPostsLikeServiceImpl extends ServiceImpl<MedicalDoctorPostsLikeMapper, MedicalDoctorPostsLike> implements IMedicalDoctorPostsLikeService {

    @Autowired
    private MedicalDoctorPostsLikeMapper medicalDoctorPostsLikeMapper;

    @Override
    public void addMedicalDoctorPostsLike(Integer postsId, String userId, Integer flag) {
        medicalDoctorPostsLikeMapper.addMedicalDoctorPostsLike(postsId, userId, flag);
    }

    @Override
    public Map<String, Object> getMedicalDoctorPostsLikeList(Integer postsId, String accountId) {
        Map<String, Object> mapAll = new HashMap<String, Object>();
        List<MedicalDoctorPostsLike> list = medicalDoctorPostsLikeMapper.getMedicalDoctorPostsLikeList(postsId);
        boolean praise = false;
        for (int i = 0; i < list.size(); i++) {
            String userId = list.get(i).getUserId();
            if (userId.equals(accountId)) {
                praise = true;
            }
        }
        mapAll.put("list", list);
        mapAll.put("praise", praise);
        return mapAll;
    }
}
