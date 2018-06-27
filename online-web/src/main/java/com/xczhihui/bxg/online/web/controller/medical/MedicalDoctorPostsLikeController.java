package com.xczhihui.bxg.online.web.controller.medical;

import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsCommentService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsLikeService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.xczhihui.bxg.online.web.controller.AbstractController.getCurrentUser;

/**
 * Description：医师动态点赞控制器
 * creed: Talk is cheap,show me the code
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/6/25 11:36
 **/
@Controller
@RequestMapping("/doctor/posts")
public class MedicalDoctorPostsLikeController {

    @Autowired
    private IMedicalDoctorPostsCommentService medicalDoctorPostsCommentService;
    @Autowired
    private IMedicalDoctorPostsLikeService medicalDoctorPostsLikeService;

    private static final org.slf4j.Logger LOGGER = LoggerFactory
            .getLogger(MedicalDoctorPostsLikeController.class);

    /**
     * 医师动态点赞列表
     */
    @RequestMapping(value="{postsId}/like", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject doctorPostsLikeList(@PathVariable("postsId") Integer postsId){
        // 获取当前用户ID
        String userId = getCurrentUser().getId();
        Map<String, Object> map  = medicalDoctorPostsLikeService.getMedicalDoctorPostsLikeList(postsId,userId);
        return ResponseObject.newSuccessResponseObject(map);
    }

    /**
     * 添加医师动态点赞
     */
    @RequestMapping(value="{postsId}/like/{flag}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject addDoctorPostsLike(@PathVariable("postsId") Integer postsId,@PathVariable("flag") Integer flag){
        // 获取当前用户ID
        String userId = getCurrentUser().getId();
        if(flag==0){
            medicalDoctorPostsLikeService.deleteMedicalDoctorPostsLike(postsId,userId);
        }else {
            medicalDoctorPostsLikeService.addMedicalDoctorPostsLike(postsId,userId,flag);
        }
        Map<String, Object> map = medicalDoctorPostsLikeService.getMedicalDoctorPostsLikeList(postsId,userId);
        return ResponseObject.newSuccessResponseObject(map);
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
        Map<String, Object> map  = medicalDoctorPostsLikeService.getMedicalDoctorPostsLikeList(postsId,userId);
        return ResponseObject.newSuccessResponseObject(map);
    }

}