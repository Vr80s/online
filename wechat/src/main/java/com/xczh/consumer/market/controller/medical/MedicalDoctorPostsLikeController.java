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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

/**
 * Description：医师动态点赞控制器
 * creed: Talk is cheap,show me the code
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/6/21 10:20
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
    public ResponseObject doctorPostsLikeList(@Account(optional = true) Optional<String> accountIdOpt, @PathVariable("postsId") Integer postsId){
        String  userId = accountIdOpt.isPresent() ? accountIdOpt.get() : "";
        List<MedicalDoctorPostsLike> list = medicalDoctorPostsLikeService.getMedicalDoctorPostsLikeList(postsId,userId);
        return ResponseObject.newSuccessResponseObject(list);
    }

    /**
     * 医师动态点赞
     * flag:0:删除1:添加
     * 0
     */
    @RequestMapping(value="{postsId}/like/{flag}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject addOrDeleteDoctorPostsLike(@Account String accountId,@PathVariable("postsId") Integer postsId,@PathVariable("flag") Integer flag){
        if(flag==0){
            medicalDoctorPostsLikeService.deleteMedicalDoctorPostsLike(postsId,accountId);
        }else {
            medicalDoctorPostsLikeService.addMedicalDoctorPostsLike(postsId,accountId,flag);
        }
        List<MedicalDoctorPostsLike> list = medicalDoctorPostsLikeService.getMedicalDoctorPostsLikeList(postsId,accountId);
        return ResponseObject.newSuccessResponseObject(list);
    }

}