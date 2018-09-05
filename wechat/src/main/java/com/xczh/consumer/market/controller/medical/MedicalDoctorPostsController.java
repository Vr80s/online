package com.xczh.consumer.market.controller.medical;

import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.medical.doctor.model.MedicalDoctorPosts;
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsService;

/**
 * Description：医师控制器
 * creed: Talk is cheap,show me the code
 *
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/6/20 14:48
 **/
@Controller
@RequestMapping("/doctor/posts")
public class MedicalDoctorPostsController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory
            .getLogger(MedicalDoctorPostsController.class);
    @Autowired
    private IMedicalDoctorPostsService medicalDoctorPostsService;

    /**
     * 医师动态列表
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject doctorPostsList(@Account(optional = true) Optional<String> accountIdOpt, @RequestParam("pageNumber") Integer pageNumber,
                                          @RequestParam("pageSize") Integer pageSize,
                                          @RequestParam(required = false) Integer type, @RequestParam("doctorId") String doctorId) {
        String userId = accountIdOpt.isPresent() ? accountIdOpt.get() : "";
        Page<MedicalDoctorPosts> page = new Page<>();
        page.setCurrent(pageNumber);
        page.setSize(pageSize);
        Page<MedicalDoctorPosts> list = medicalDoctorPostsService.selectMedicalDoctorPostsPage(page, type, doctorId, userId);
        //过滤ios师承直播
//        List<MedicalDoctorPosts> list1 = new ArrayList<MedicalDoctorPosts>();
//        list.getRecords().forEach(medicalDoctorPosts -> {
//            if(Integer.valueOf(HeaderInterceptor.CLIENT.get()) == ClientType.IOS.getCode()){
//                if(!medicalDoctorPosts.getTeaching()){
//                    list1.add(medicalDoctorPosts);
//                }
//            } else {
//                list1.add(medicalDoctorPosts);
//            }
//
//        });
//        list.setRecords(list1);
        return ResponseObject.newSuccessResponseObject(list);
    }

}