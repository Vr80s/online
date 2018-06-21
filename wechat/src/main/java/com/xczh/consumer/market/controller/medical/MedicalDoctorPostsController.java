package com.xczh.consumer.market.controller.medical;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.service.OLAttachmentCenterService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.medical.doctor.model.MedicalDoctorAccount;
import com.xczhihui.medical.doctor.model.MedicalDoctorPosts;
import com.xczhihui.medical.doctor.service.IMedicalDoctorAccountService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Description：医师控制器
 * creed: Talk is cheap,show me the code
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/6/20 14:48
 **/
@Controller
@RequestMapping("/xczh/medical")
public class MedicalDoctorPostsController {

    @Autowired
    private IMedicalDoctorPostsService medicalDoctorPostsService;
    @Autowired
    private IMedicalDoctorAccountService medicalDoctorAccountService;
    @Autowired
    private OLAttachmentCenterService service;


    private static final org.slf4j.Logger LOGGER = LoggerFactory
            .getLogger(MedicalDoctorPostsController.class);

    /**
     * 医师动态列表
     */
    @RequestMapping("doctorDynamicsList")
    @ResponseBody
    public ResponseObject doctorDynamicsList(@Account String accountId, @RequestParam("pageNumber") Integer pageNumber,
                                              @RequestParam("pageSize") Integer pageSize,
                                              @RequestParam("type") Integer type){
        Page<MedicalDoctorPosts> page = new Page<>();
        page.setCurrent(pageNumber);
        page.setSize(pageSize);
        MedicalDoctorAccount mha = medicalDoctorAccountService.getByUserId(accountId);
        Page<MedicalDoctorPosts> list = medicalDoctorPostsService.selectMedicalDoctorPostsPage(page,type,mha.getDoctorId());
        return ResponseObject.newSuccessResponseObject(list);
    }

    /**
     * 添加医师动态
     */
    @RequestMapping("addDoctorDynamics")
    @ResponseBody
    public ResponseObject addDoctorDynamics(@Account String accountId, MedicalDoctorPosts medicalDoctorPosts,
                                             @RequestParam(value = "coverImg",required = false) MultipartFile coverImg)
            throws Exception {

        MedicalDoctorAccount mha = medicalDoctorAccountService.getByUserId(accountId);
        medicalDoctorPosts.setDoctorId(mha.getDoctorId());
        String cover_img="";
        if(coverImg!=null){
            //封面图片
            String projectName = "other";
            String fileType = "1"; //图片类型了
            cover_img = service.upload(accountId,
                    projectName, coverImg.getOriginalFilename(), coverImg.getContentType(), coverImg.getBytes(), fileType, null);
            medicalDoctorPosts.setCoverImg(cover_img);
        }
        medicalDoctorPostsService.addMedicalDoctorPosts(medicalDoctorPosts);
        return ResponseObject.newSuccessResponseObject("添加成功");
    }

    /**
     * 编辑医师动态
     */
    @RequestMapping("updateDoctorDynamics")
    @ResponseBody
    public ResponseObject updateDoctorDynamics(@Account String accountId,MedicalDoctorPosts medicalDoctorPosts,
                                                @RequestParam(value = "coverImg",required = false) MultipartFile coverImg)
            throws Exception {
        String cover_img="";
        if(coverImg!=null){
            //封面图片
            String projectName = "other";
            String fileType = "1"; //图片类型了
            cover_img = service.upload(accountId,
                    projectName, coverImg.getOriginalFilename(), coverImg.getContentType(), coverImg.getBytes(), fileType, null);
            medicalDoctorPosts.setCoverImg(cover_img);
        }
        medicalDoctorPostsService.updateMedicalDoctorPosts(medicalDoctorPosts);
        return ResponseObject.newSuccessResponseObject("编辑成功");
    }

    /**
     * 删除医师动态
     */
    @RequestMapping("deleteDoctorDynamics")
    @ResponseBody
    public ResponseObject deleteDoctorDynamics(@RequestParam("id") Integer id){
        medicalDoctorPostsService.deleteMedicalDoctorPosts(id);
        return ResponseObject.newSuccessResponseObject("删除成功");
    }

}