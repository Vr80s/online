package com.xczh.consumer.market.controller.medical;

import java.util.Map;
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
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsLikeService;

/**
 * Description：医师动态点赞控制器
 * creed: Talk is cheap,show me the code
 *
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/6/21 10:20
 **/
@Controller
@RequestMapping("/doctor/posts")
public class MedicalDoctorPostsLikeController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory
            .getLogger(MedicalDoctorPostsLikeController.class);
    @Autowired
    private IMedicalDoctorPostsLikeService medicalDoctorPostsLikeService;

    /**
     * 医师动态点赞列表
     */
    @RequestMapping(value = "{postsId}/like", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject doctorPostsLikeList(@Account(optional = true) Optional<String> accountIdOpt, @PathVariable("postsId") Integer postsId) {
        String userId = accountIdOpt.isPresent() ? accountIdOpt.get() : "";
        Map<String, Object> map = medicalDoctorPostsLikeService.getMedicalDoctorPostsLikeList(postsId, userId);
        return ResponseObject.newSuccessResponseObject(map);
    }

    /**
     * 医师动态点赞
     * flag:0:删除1:添加
     * 0
     */
    @RequestMapping(value = "{postsId}/like/{flag}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject addOrDeleteDoctorPostsLike(@Account String accountId, @PathVariable("postsId") Integer postsId, @PathVariable("flag") Integer flag) {
        medicalDoctorPostsLikeService.addMedicalDoctorPostsLike(postsId, accountId, flag);
        Map<String, Object> map = medicalDoctorPostsLikeService.getMedicalDoctorPostsLikeList(postsId, accountId);
        return ResponseObject.newSuccessResponseObject(map);
    }

}