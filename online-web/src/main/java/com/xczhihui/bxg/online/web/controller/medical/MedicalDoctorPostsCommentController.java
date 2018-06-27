package com.xczhihui.bxg.online.web.controller.medical;

import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.medical.doctor.model.MedicalDoctorPostsComment;
import com.xczhihui.medical.doctor.model.MedicalDoctorPostsLike;
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsCommentService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsLikeService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.xczhihui.bxg.online.web.controller.AbstractController.getCurrentUser;

/**
 * Description：医师动态评论控制器
 * creed: Talk is cheap,show me the code
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/6/25 11:36
 **/
@Controller
@RequestMapping("/doctor/posts")
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
    @RequestMapping(value="{postsId}/comment", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject doctorPostsCommentList(@PathVariable("postsId") Integer postsId){
        // 获取当前用户ID
        String userId = getCurrentUser().getId();
        List<MedicalDoctorPostsComment> list = medicalDoctorPostsCommentService.selectMedicalDoctorPostsCommentList(postsId,userId);
        return ResponseObject.newSuccessResponseObject(list);
    }

    /**
     * 添加医师动态评论
     */
    @RequestMapping(value="{postsId}/comment",method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject addDoctorPostsComment(MedicalDoctorPostsComment medicalDoctorPostsComment){
        // 获取当前用户ID
        String userId = getCurrentUser().getId();
        medicalDoctorPostsCommentService.addMedicalDoctorPostsComment(medicalDoctorPostsComment,userId);
        List<MedicalDoctorPostsComment> list = medicalDoctorPostsCommentService.selectMedicalDoctorPostsCommentList(medicalDoctorPostsComment.getPostsId(),userId);
        return ResponseObject.newSuccessResponseObject(list);
    }

    /**
     * 删除医师动态评论
     */
    @RequestMapping(value="{id}/comment/{postsId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseObject deleteDoctorPostsComment(@PathVariable("id") Integer id,@PathVariable("postsId") Integer postsId){
        // 获取当前用户ID
        String userId = getCurrentUser().getId();
        medicalDoctorPostsCommentService.deleteMedicalDoctorPostsComment(id);
        List<MedicalDoctorPostsComment> list = medicalDoctorPostsCommentService.selectMedicalDoctorPostsCommentList(postsId,userId);
        return ResponseObject.newSuccessResponseObject(list);
    }





}