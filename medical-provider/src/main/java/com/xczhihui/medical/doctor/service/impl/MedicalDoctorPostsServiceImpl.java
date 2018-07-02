package com.xczhihui.medical.doctor.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
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
            Date d = MedicalDoctorPosts.getCreateTime();
            Date currentDate = new Date();
            Date createTime = MedicalDoctorPosts.getCreateTime();
            int second = (int)(currentDate.getTime() - createTime.getTime())/1000;
            int min = (int)(currentDate.getTime() - d.getTime())/1000/60+1;
            int hour = (int)(currentDate.getTime() - d.getTime())/1000/60/60+1;
            if(second<60){
                MedicalDoctorPosts.setDateStr("刚刚");
            } else if (min<=60){
                MedicalDoctorPosts.setDateStr(min+"分钟前");
            } else if (hour<=12){
                MedicalDoctorPosts.setDateStr(hour+"小时前");
            } else {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String dateString = formatter.format(createTime);
                MedicalDoctorPosts.setDateStr(dateString);
            }

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
