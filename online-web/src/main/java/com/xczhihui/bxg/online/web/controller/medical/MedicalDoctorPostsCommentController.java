package com.xczhihui.bxg.online.web.controller.medical;

import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.medical.doctor.model.MedicalDoctorPostsComment;
import com.xczhihui.medical.doctor.model.MedicalDoctorPostsLike;
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsCommentService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsLikeService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @RequestMapping(value="doctorPostsCommentList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject doctorPostsCommentList(@RequestParam("postsId") Integer postsId){
        List<MedicalDoctorPostsComment> list = medicalDoctorPostsCommentService.selectMedicalDoctorPostsCommentList(postsId);
        return ResponseObject.newSuccessResponseObject(list);
    }

    /**
     * 添加医师动态评论
     */
    @RequestMapping(value="addDoctorPostsComment", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject addDoctorPostsComment(MedicalDoctorPostsComment medicalDoctorPostsComment){
        // 获取当前用户ID
        String userId = getCurrentUser().getId();
        medicalDoctorPostsCommentService.addMedicalDoctorPostsComment(medicalDoctorPostsComment,userId);
        List<MedicalDoctorPostsComment> list = medicalDoctorPostsCommentService.selectMedicalDoctorPostsCommentList(medicalDoctorPostsComment.getPostsId());
        return ResponseObject.newSuccessResponseObject(list);
    }

    /**
     * 删除医师动态评论
     */
    @RequestMapping(value="deleteDoctorPostsComment", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deleteDoctorPostsComment(@RequestParam("id") Integer id,@RequestParam("postsId") Integer postsId){
        medicalDoctorPostsCommentService.deleteMedicalDoctorPostsComment(id);
        List<MedicalDoctorPostsComment> list = medicalDoctorPostsCommentService.selectMedicalDoctorPostsCommentList(postsId);
        return ResponseObject.newSuccessResponseObject("删除成功");
    }

    /**
     * 医师动态点赞列表
     */
    @RequestMapping(value="doctorPostsLikeList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject doctorPostsLikeList(@RequestParam("postsId") Integer postsId){
        List<MedicalDoctorPostsLike> list = medicalDoctorPostsLikeService.getMedicalDoctorPostsLikeList(postsId);
        return ResponseObject.newSuccessResponseObject(list);
    }

    /**
     * 添加医师动态点赞
     */
    @RequestMapping(value="addDoctorPostsLike", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject addDoctorPostsLike(@RequestParam("postsId") Integer postsId){
        // 获取当前用户ID
        String userId = getCurrentUser().getId();
        medicalDoctorPostsLikeService.addMedicalDoctorPostsLike(postsId,userId, 0);
        List<MedicalDoctorPostsLike> list = medicalDoctorPostsLikeService.getMedicalDoctorPostsLikeList(postsId);
        return ResponseObject.newSuccessResponseObject(list);
    }

    /**
     * 删除医师动态点赞
     */
    @RequestMapping(value="deleteDoctorPostsLike", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deleteDoctorPostsLike(@RequestParam("postsId") Integer postsId){
        // 获取当前用户ID
        String userId = getCurrentUser().getId();
        medicalDoctorPostsLikeService.deleteMedicalDoctorPostsLike(postsId,userId);
        List<MedicalDoctorPostsLike> list = medicalDoctorPostsLikeService.getMedicalDoctorPostsLikeList(postsId);
        return ResponseObject.newSuccessResponseObject("取消成功");
    }

}