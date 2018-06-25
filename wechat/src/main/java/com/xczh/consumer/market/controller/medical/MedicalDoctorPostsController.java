package com.xczh.consumer.market.controller.medical;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.service.OLAttachmentCenterService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.medical.doctor.model.MedicalDoctorPosts;
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    private OLAttachmentCenterService service;

    private static final org.slf4j.Logger LOGGER = LoggerFactory
            .getLogger(MedicalDoctorPostsController.class);

    /**
     * 医师动态列表
     */
    @RequestMapping(value="doctorDynamicsList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject doctorDynamicsList(@Account String accountId, @RequestParam("pageNumber") Integer pageNumber,
                                              @RequestParam("pageSize") Integer pageSize,
                                              @RequestParam(required = false) Integer type){
        Page<MedicalDoctorPosts> page = new Page<>();
        page.setCurrent(pageNumber);
        page.setSize(pageSize);
        Page<MedicalDoctorPosts> list = medicalDoctorPostsService.selectMedicalDoctorPostsPage(page,type,accountId);
        return ResponseObject.newSuccessResponseObject(list);
    }

    /**
     * 添加医师动态
     */
    @RequestMapping(value="addDoctorDynamics", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject addDoctorDynamics(@Account String accountId, MedicalDoctorPosts medicalDoctorPosts,
                                             @RequestParam(value = "coverImg",required = false) MultipartFile coverImg)
            throws Exception {
        medicalDoctorPosts.setDoctorId(accountId);
        if(coverImg!=null){
            String cover_img = getFilePath(coverImg,accountId);
            medicalDoctorPosts.setCoverImg(cover_img);
        }
        medicalDoctorPostsService.addMedicalDoctorPosts(medicalDoctorPosts);
        return ResponseObject.newSuccessResponseObject("添加成功");
    }

    /**
     * 编辑医师动态
     */
    @RequestMapping(value="updateDoctorDynamics", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateDoctorDynamics(@Account String accountId,MedicalDoctorPosts medicalDoctorPosts,
                                                @RequestParam(value = "coverImg",required = false) MultipartFile coverImg)
            throws Exception {
        if(coverImg!=null){
            String cover_img = getFilePath(coverImg,accountId);
            medicalDoctorPosts.setCoverImg(cover_img);
        }
        medicalDoctorPostsService.updateMedicalDoctorPosts(medicalDoctorPosts);
        return ResponseObject.newSuccessResponseObject("编辑成功");
    }

    /**
     * 删除医师动态
     */
    @RequestMapping(value="deleteDoctorDynamics", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deleteDoctorDynamics(@RequestParam("id") Integer id){
        medicalDoctorPostsService.deleteMedicalDoctorPosts(id);
        return ResponseObject.newSuccessResponseObject("删除成功");
    }

    /**
     * 医师动态置顶/取消置顶
     */
    @RequestMapping(value="updateStickDoctorDynamics", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateStickDoctorDynamics(@RequestParam("id") Integer id,@RequestParam("stick") Boolean stick){
        medicalDoctorPostsService.updateStickMedicalDoctorPosts(id,stick);
        if(stick){
            return ResponseObject.newSuccessResponseObject("置顶成功");
        }else {
            return ResponseObject.newSuccessResponseObject("取消置顶成功");
        }
    }
    /**
     * 上传图片返回路径
     */
    private String getFilePath(MultipartFile imgFile,String accountId) throws IOException {
            String filePath = service.upload(accountId,"other", imgFile.getOriginalFilename(),
                    imgFile.getContentType(), imgFile.getBytes(), "1", null);
        return filePath;
    }

}