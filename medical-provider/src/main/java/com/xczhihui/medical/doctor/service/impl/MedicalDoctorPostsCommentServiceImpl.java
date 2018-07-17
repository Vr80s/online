package com.xczhihui.medical.doctor.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.common.util.StringLegalUtil;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorPostsCommentMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctorPostsComment;
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description：医师动态评论 服务实现类
 * creed: Talk is cheap,show me the code
 *
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/6/21 9:47
 **/
@Service
public class MedicalDoctorPostsCommentServiceImpl extends ServiceImpl<MedicalDoctorPostsCommentMapper, MedicalDoctorPostsComment> implements IMedicalDoctorPostsCommentService {

    @Autowired
    private MedicalDoctorPostsCommentMapper medicalDoctorPostsCommentMapper;


    @Override
    public List<MedicalDoctorPostsComment> selectMedicalDoctorPostsCommentList(Integer postsId, String userId) {
        List<MedicalDoctorPostsComment> list = medicalDoctorPostsCommentMapper.selectMedicalDoctorPostsCommentList(postsId);
        list.forEach(medicalDoctorPostsComment -> {
            if (medicalDoctorPostsComment.getReplyUserId() != null) {
                if (medicalDoctorPostsComment.getReplyUserId().equals(userId)) {
                    medicalDoctorPostsComment.setSelf(true);
                }
                medicalDoctorPostsComment.setUserName(StringLegalUtil.isPhoneLegal(medicalDoctorPostsComment.getUserName()));
                if(medicalDoctorPostsComment.getReplyUserName() != null){
                    medicalDoctorPostsComment.setReplyUserName(StringLegalUtil.isPhoneLegal(medicalDoctorPostsComment.getReplyUserName()));
                }
            } else {
                if (medicalDoctorPostsComment.getUserId().equals(userId)) {
                    medicalDoctorPostsComment.setSelf(true);
                }
                medicalDoctorPostsComment.setUserName(StringLegalUtil.isPhoneLegal(medicalDoctorPostsComment.getUserName()));
            }
        });
        return list;
    }

    @Override
    public void addMedicalDoctorPostsComment(MedicalDoctorPostsComment medicalDoctorPostsComment, String userId) {
        if (medicalDoctorPostsComment.getCommentId() != null && !medicalDoctorPostsComment.getCommentId().equals("")) {
            MedicalDoctorPostsComment mdpc = medicalDoctorPostsCommentMapper.getMedicalDoctorPostsCommentById(medicalDoctorPostsComment.getCommentId());
            if(mdpc.getReplyUserId() != null && !mdpc.getReplyUserId().equals("")){
                medicalDoctorPostsComment.setUserId(mdpc.getReplyUserId());
            }else {
                medicalDoctorPostsComment.setUserId(mdpc.getUserId());
            }
            medicalDoctorPostsComment.setReplyUserId(userId);
        } else {
            medicalDoctorPostsComment.setUserId(userId);
        }
        medicalDoctorPostsCommentMapper.addMedicalDoctorPostsComment(medicalDoctorPostsComment);
    }

    @Override
    public void deleteMedicalDoctorPostsComment(Integer id) {
        medicalDoctorPostsCommentMapper.deleteMedicalDoctorPostsComment(id);
    }
}
