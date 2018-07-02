package com.xczhihui.medical.doctor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorPostsLikeMapper;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorPostsMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctorPosts;
import com.xczhihui.medical.doctor.model.MedicalDoctorPostsComment;
import com.xczhihui.medical.doctor.model.MedicalDoctorPostsLike;
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsCommentService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsService;

/**
 * Description：医师动态 服务实现类
 * creed: Talk is cheap,show me the code
 *
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/6/20 14:35
 **/
@Service
public class MedicalDoctorPostsServiceImpl extends ServiceImpl<MedicalDoctorPostsMapper, MedicalDoctorPosts> implements IMedicalDoctorPostsService {

    @Autowired
    private MedicalDoctorPostsMapper medicalDoctorPostsMapper;
    @Autowired
    private MedicalDoctorPostsLikeMapper medicalDoctorPostsLikeMapper;
    @Autowired
    private IMedicalDoctorPostsCommentService medicalDoctorPostsCommentService;

    @Override
    public Page<MedicalDoctorPosts> selectMedicalDoctorPostsPage(Page<MedicalDoctorPosts> page, Integer type, String doctorId, String accountId) {
        List<MedicalDoctorPosts> list = medicalDoctorPostsMapper.selectMedicalDoctorPostsPage(page, type, doctorId);
        //评论列表和点赞列表
        list.forEach(MedicalDoctorPosts -> {
            Integer postsId = MedicalDoctorPosts.getId();
            List<MedicalDoctorPostsComment> commentList = medicalDoctorPostsCommentService.selectMedicalDoctorPostsCommentList(postsId, accountId);
            List<MedicalDoctorPostsLike> likeList = medicalDoctorPostsLikeMapper.getMedicalDoctorPostsLikeList(postsId);
            likeList.forEach(MedicalDoctorPostsLike -> {
                String userId = MedicalDoctorPostsLike.getUserId();
                if (userId.equals(accountId)) {
                    MedicalDoctorPosts.setPraise(true);
                }
            });
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
        medicalDoctorPostsMapper.updateStickMedicalDoctorPosts(id, stick);
    }

    @Override
    public MedicalDoctorPosts getMedicalDoctorPostsById(Integer id) {
        MedicalDoctorPosts mdp = medicalDoctorPostsMapper.getMedicalDoctorPostsById(id);
        return mdp;
    }
}
