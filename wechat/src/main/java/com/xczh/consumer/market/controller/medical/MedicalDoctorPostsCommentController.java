package com.xczh.consumer.market.controller.medical;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.service.OLAttachmentCenterService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.medical.doctor.model.MedicalDoctorAccount;
import com.xczhihui.medical.doctor.model.MedicalDoctorPosts;
import com.xczhihui.medical.doctor.model.MedicalDoctorPostsComment;
import com.xczhihui.medical.doctor.model.MedicalDoctorPostsLike;
import com.xczhihui.medical.doctor.service.IMedicalDoctorAccountService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsCommentService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsLikeService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Description：医师动态评论控制器
 * creed: Talk is cheap,show me the code
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/6/21 10:20
 **/
@Controller
@RequestMapping("/xczh/medical")
public class MedicalDoctorPostsCommentController {

    @Autowired
    private IMedicalDoctorPostsCommentService medicalDoctorPostsCommentService;
    @Autowired
    private IMedicalDoctorPostsLikeService medicalDoctorPostsLikeService;

    private static final org.slf4j.Logger LOGGER = LoggerFactory
            .getLogger(MedicalDoctorPostsCommentController.class);

    /**
     * 医师动态评论列表
     */
    @RequestMapping("doctorDynamicsCommentList")
    @ResponseBody
    public ResponseObject doctorDynamicsCommentList(@RequestParam("postsId") Integer postsId){
        List<MedicalDoctorPostsComment> list = medicalDoctorPostsCommentService.selectMedicalDoctorPostsCommentList(postsId);
        return ResponseObject.newSuccessResponseObject(list);
    }

    /**
     * 添加医师动态评论
     */
    @RequestMapping("addDoctorDynamicsComment")
    @ResponseBody
    public ResponseObject addDoctorDynamicsComment(@Account String accountId,MedicalDoctorPostsComment medicalDoctorPostsComment){

        medicalDoctorPostsCommentService.addMedicalDoctorPostsComment(medicalDoctorPostsComment,accountId);
        return ResponseObject.newSuccessResponseObject("添加成功");
    }

    /**
     * 删除医师动态评论
     */
    @RequestMapping("deleteDoctorDynamicsComment")
    @ResponseBody
    public ResponseObject deleteDoctorDynamicsComment(@RequestParam("id") Integer id){
        medicalDoctorPostsCommentService.deleteMedicalDoctorPostsComment(id);
        return ResponseObject.newSuccessResponseObject("删除成功");
    }

    /**
     * 医师动态点赞列表
     */
    @RequestMapping("doctorDynamicsLikeList")
    @ResponseBody
    public ResponseObject doctorDynamicsLikeList(@RequestParam("postsId") Integer postsId){
        List<MedicalDoctorPostsLike> list = medicalDoctorPostsLikeService.getMedicalDoctorPostsLikeList(postsId);
        return ResponseObject.newSuccessResponseObject(list);
    }

    /**
     * 添加医师动态点赞
     */
    @RequestMapping("addDoctorDynamicsLike")
    @ResponseBody
    public ResponseObject addDoctorDynamicsLike(@Account String accountId,@RequestParam("postsId") Integer postsId){
        medicalDoctorPostsLikeService.addMedicalDoctorPostsLike(postsId,accountId);
        return ResponseObject.newSuccessResponseObject("点赞成功");
    }

    /**
     * 删除医师动态点赞
     */
    @RequestMapping("deleteDoctorDynamicsLike")
    @ResponseBody
    public ResponseObject deleteDoctorDynamicsLike(@RequestParam("id") Integer id){
        medicalDoctorPostsLikeService.deleteMedicalDoctorPostsLike(id);
        return ResponseObject.newSuccessResponseObject("取消成功");
    }

}