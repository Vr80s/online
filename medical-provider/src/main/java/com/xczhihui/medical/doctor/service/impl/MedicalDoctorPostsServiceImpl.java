package com.xczhihui.medical.doctor.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorPostsCommentMapper;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorPostsLikeMapper;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorPostsMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctorPosts;
import com.xczhihui.medical.doctor.model.MedicalDoctorPostsComment;
import com.xczhihui.medical.doctor.model.MedicalDoctorPostsLike;
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description：医师动态 服务实现类
 * creed: Talk is cheap,show me the code
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/6/20 14:35
 **/
@Service
public class MedicalDoctorPostsServiceImpl extends ServiceImpl<MedicalDoctorPostsMapper, MedicalDoctorPosts> implements IMedicalDoctorPostsService {

    @Autowired
    private MedicalDoctorPostsMapper medicalDoctorPostsMapper;
    @Autowired
    private MedicalDoctorPostsCommentMapper medicalDoctorPostsCommentMapper;
    @Autowired
    private MedicalDoctorPostsLikeMapper medicalDoctorPostsLikeMapper;

    @Override
    public Page<MedicalDoctorPosts> selectMedicalDoctorPostsPage(Page<MedicalDoctorPosts> page, Integer type, String doctorId) {
        List<MedicalDoctorPosts> list = medicalDoctorPostsMapper.selectMedicalDoctorPostsPage(page,type,doctorId);
        //评论列表和点赞列表
        list.forEach(MedicalDoctorPosts -> {
            Integer postsId = MedicalDoctorPosts.getId();
            List<MedicalDoctorPostsComment> commentList = medicalDoctorPostsCommentMapper.selectMedicalDoctorPostsCommentList(postsId);
            List<MedicalDoctorPostsLike> likeList = medicalDoctorPostsLikeMapper.getMedicalDoctorPostsLikeList(postsId);
            MedicalDoctorPosts.setDoctorPostsCommentList(commentList);
            MedicalDoctorPosts.setDoctorPostsLikeList(likeList);
        });
        page.setRecords(list);
        return page;
    }

    @Override
    public void addMedicalDoctorPosts(MedicalDoctorPosts medicalDoctorPosts) {
        medicalDoctorPostsMapper.addMedicalDoctorPosts(medicalDoctorPosts);
    }

    @Override
    public void updateMedicalDoctorPosts(MedicalDoctorPosts medicalDoctorPosts) {
        medicalDoctorPostsMapper.updateMedicalDoctorPosts(medicalDoctorPosts);
    }

    @Override
    public void deleteMedicalDoctorPosts(Integer id) {
        medicalDoctorPostsMapper.deleteMedicalDoctorPosts(id);
    }

    @Override
    public void updateStickMedicalDoctorPosts(Integer id, Boolean stick) {
        medicalDoctorPostsMapper.updateStickMedicalDoctorPosts(id,stick);
    }
}
