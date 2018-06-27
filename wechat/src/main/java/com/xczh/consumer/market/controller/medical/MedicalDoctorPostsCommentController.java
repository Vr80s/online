package com.xczh.consumer.market.controller.medical;

import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.medical.doctor.model.MedicalDoctorPostsComment;
import com.xczhihui.medical.doctor.model.MedicalDoctorPostsLike;
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsCommentService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsLikeService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description：医师动态评论控制器
 * creed: Talk is cheap,show me the code
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/6/21 10:20
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
    @RequestMapping(value="{postsId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject doctorPostsCommentList(@PathVariable("postsId") Integer postsId){
        List<MedicalDoctorPostsComment> list = medicalDoctorPostsCommentService.selectMedicalDoctorPostsCommentList(postsId);
        return ResponseObject.newSuccessResponseObject(list);
    }

    /**
     * 添加医师动态评论
     */
    @RequestMapping( method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject addDoctorPostsComment(@Account String accountId,MedicalDoctorPostsComment medicalDoctorPostsComment){

        medicalDoctorPostsCommentService.addMedicalDoctorPostsComment(medicalDoctorPostsComment,accountId);
        List<MedicalDoctorPostsComment> list = medicalDoctorPostsCommentService.selectMedicalDoctorPostsCommentList(medicalDoctorPostsComment.getPostsId());
        return ResponseObject.newSuccessResponseObject(list);
    }

    /**
     * 删除医师动态评论
     */
    @RequestMapping(value = "{id}/{postsId}",method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseObject deleteDoctorPostsComment(@PathVariable("id") Integer id,@PathVariable("postsId") Integer postsId){
        medicalDoctorPostsCommentService.deleteMedicalDoctorPostsComment(id);
        List<MedicalDoctorPostsComment> list = medicalDoctorPostsCommentService.selectMedicalDoctorPostsCommentList(postsId);
        return ResponseObject.newSuccessResponseObject(list);
    }

    /**
     * 医师动态点赞列表
     */
    @RequestMapping(value="{postsId}/like", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject doctorPostsLikeList(@PathVariable("postsId") Integer postsId){
        List<MedicalDoctorPostsLike> list = medicalDoctorPostsLikeService.getMedicalDoctorPostsLikeList(postsId);
        return ResponseObject.newSuccessResponseObject(list);
    }

    /**
     * 医师动态点赞
     * 1
     * 0
     */
    @RequestMapping(value="{id}/like/{flag}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject addDoctorPostsLike(@Account String accountId,@PathVariable("postsId") Integer postsId,@PathVariable("flag") Integer flag){
        medicalDoctorPostsLikeService.addMedicalDoctorPostsLike(postsId,accountId,flag);
        List<MedicalDoctorPostsLike> list = medicalDoctorPostsLikeService.getMedicalDoctorPostsLikeList(postsId);
        return ResponseObject.newSuccessResponseObject(list);
    }

//    /**
//     * 删除医师动态点赞
//     */
//    @RequestMapping(value="deleteDoctorPostsLike", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseObject deleteDoctorPostsLike(@Account String accountId,@RequestParam("postsId") Integer postsId){
//        medicalDoctorPostsLikeService.deleteMedicalDoctorPostsLike(postsId,accountId);
//        List<MedicalDoctorPostsLike> list = medicalDoctorPostsLikeService.getMedicalDoctorPostsLikeList(postsId);
//        return ResponseObject.newSuccessResponseObject(list);
//    }

}