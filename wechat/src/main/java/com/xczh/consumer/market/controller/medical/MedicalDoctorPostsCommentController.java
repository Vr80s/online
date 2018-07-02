package com.xczh.consumer.market.controller.medical;

import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.medical.doctor.model.MedicalDoctorPostsComment;
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsCommentService;

/**
 * Description：医师动态评论控制器
 * creed: Talk is cheap,show me the code
 *
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/6/21 10:20
 **/
@Controller
@RequestMapping("/doctor/posts")
public class MedicalDoctorPostsCommentController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory
            .getLogger(MedicalDoctorPostsCommentController.class);
    @Autowired
    private IMedicalDoctorPostsCommentService medicalDoctorPostsCommentService;

    /**
     * 医师动态评论列表
     */
    @RequestMapping(value = "{postsId}/comment", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject doctorPostsCommentList(@Account(optional = true) Optional<String> accountIdOpt, @PathVariable("postsId") Integer postsId) {
        String userId = accountIdOpt.isPresent() ? accountIdOpt.get() : "";
        List<MedicalDoctorPostsComment> list = medicalDoctorPostsCommentService.selectMedicalDoctorPostsCommentList(postsId, userId);
        return ResponseObject.newSuccessResponseObject(list);
    }

    /**
     * 添加医师动态评论
     */
    @RequestMapping(value = "{postsId}/comment", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject addDoctorPostsComment(@Account String accountId, MedicalDoctorPostsComment medicalDoctorPostsComment, @PathVariable("postsId") Integer postsId) {
        medicalDoctorPostsComment.setPostsId(postsId);
        medicalDoctorPostsCommentService.addMedicalDoctorPostsComment(medicalDoctorPostsComment, accountId);
        List<MedicalDoctorPostsComment> list = medicalDoctorPostsCommentService.selectMedicalDoctorPostsCommentList(medicalDoctorPostsComment.getPostsId(), accountId);
        return ResponseObject.newSuccessResponseObject(list);
    }

    /**
     * 删除医师动态评论
     */
    @RequestMapping(value = "{postsId}/comment/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseObject deleteDoctorPostsComment(@Account String accountId, @PathVariable("id") Integer id, @PathVariable("postsId") Integer postsId) {
        medicalDoctorPostsCommentService.deleteMedicalDoctorPostsComment(id);
        List<MedicalDoctorPostsComment> list = medicalDoctorPostsCommentService.selectMedicalDoctorPostsCommentList(postsId, accountId);
        return ResponseObject.newSuccessResponseObject(list);
    }

}