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
@RequestMapping("/doctor/dynamics")
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
    @RequestMapping(value="doctorDynamicsCommentList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject doctorDynamicsCommentList(@RequestParam("postsId") Integer postsId){
        List<MedicalDoctorPostsComment> list = medicalDoctorPostsCommentService.selectMedicalDoctorPostsCommentList(postsId);
        return ResponseObject.newSuccessResponseObject(list);
    }

    /**
     * 添加医师动态评论
     */
    @RequestMapping(value="addDoctorDynamicsComment", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject addDoctorDynamicsComment(MedicalDoctorPostsComment medicalDoctorPostsComment){
        // 获取当前用户ID
        String userId = getCurrentUser().getId();
        medicalDoctorPostsCommentService.addMedicalDoctorPostsComment(medicalDoctorPostsComment,userId);
        return ResponseObject.newSuccessResponseObject("添加成功");
    }

    /**
     * 删除医师动态评论
     */
    @RequestMapping(value="deleteDoctorDynamicsComment", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deleteDoctorDynamicsComment(@RequestParam("id") Integer id){
        medicalDoctorPostsCommentService.deleteMedicalDoctorPostsComment(id);
        return ResponseObject.newSuccessResponseObject("删除成功");
    }

    /**
     * 医师动态点赞列表
     */
    @RequestMapping(value="doctorDynamicsLikeList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject doctorDynamicsLikeList(@RequestParam("postsId") Integer postsId){
        List<MedicalDoctorPostsLike> list = medicalDoctorPostsLikeService.getMedicalDoctorPostsLikeList(postsId);
        return ResponseObject.newSuccessResponseObject(list);
    }

    /**
     * 添加医师动态点赞
     */
    @RequestMapping(value="addDoctorDynamicsLike", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject addDoctorDynamicsLike(@RequestParam("postsId") Integer postsId){
        // 获取当前用户ID
        String userId = getCurrentUser().getId();
        medicalDoctorPostsLikeService.addMedicalDoctorPostsLike(postsId,userId);
        return ResponseObject.newSuccessResponseObject("点赞成功");
    }

    /**
     * 删除医师动态点赞
     */
    @RequestMapping(value="deleteDoctorDynamicsLike", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deleteDoctorDynamicsLike(@RequestParam("id") Integer id){
        medicalDoctorPostsLikeService.deleteMedicalDoctorPostsLike(id);
        return ResponseObject.newSuccessResponseObject("取消成功");
    }

}