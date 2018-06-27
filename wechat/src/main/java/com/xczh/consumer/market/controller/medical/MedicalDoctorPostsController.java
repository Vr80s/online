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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

/**
 * Description：医师控制器
 * creed: Talk is cheap,show me the code
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/6/20 14:48
 **/
@Controller
@RequestMapping("/doctor/posts")
public class MedicalDoctorPostsController {

    @Autowired
    private IMedicalDoctorPostsService medicalDoctorPostsService;
    @Autowired
    private OLAttachmentCenterService service;
    @Autowired
    private IMedicalDoctorAccountService medicalDoctorAccountService;


    private static final org.slf4j.Logger LOGGER = LoggerFactory
            .getLogger(MedicalDoctorPostsController.class);

    /**
     * 医师动态列表
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject doctorPostsList(@Account(optional = true) Optional<String> accountIdOpt, @RequestParam("pageNumber") Integer pageNumber,
                                          @RequestParam("pageSize") Integer pageSize,
                                          @RequestParam(required = false) Integer type, @RequestParam("doctorId") String doctorId){
        String  userId = accountIdOpt.isPresent() ? accountIdOpt.get() : "";
        Page<MedicalDoctorPosts> page = new Page<>();
        page.setCurrent(pageNumber);
        page.setSize(pageSize);
        Page<MedicalDoctorPosts> list = medicalDoctorPostsService.selectMedicalDoctorPostsPage(page,type,doctorId,userId);
        return ResponseObject.newSuccessResponseObject(list);
    }

    /**
     * 添加医师动态    --del
     */
    @RequestMapping(value="addDoctorPosts", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject addDoctorPosts(@Account String accountId, MedicalDoctorPosts medicalDoctorPosts,
                                             @RequestParam(value = "coverImg",required = false) MultipartFile coverImg)
            throws Exception {
        MedicalDoctorAccount mha = medicalDoctorAccountService.getByUserId(accountId);
        medicalDoctorPosts.setDoctorId(mha.getDoctorId());
        if(coverImg!=null){
            String cover_img = getFilePath(coverImg,accountId);
            medicalDoctorPosts.setCoverImg(cover_img);
        }
        medicalDoctorPostsService.addMedicalDoctorPosts(medicalDoctorPosts);
        return ResponseObject.newSuccessResponseObject("添加成功");
    }

    /**
     * 编辑医师动态    --del
     */
    @RequestMapping(value="updateDoctorPosts", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateDoctorPosts(@Account String accountId,MedicalDoctorPosts medicalDoctorPosts,
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
     * 删除医师动态   --del
     */
    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseObject deleteDoctorPosts(@PathVariable("id") Integer id){
        medicalDoctorPostsService.deleteMedicalDoctorPosts(id);
        return ResponseObject.newSuccessResponseObject("删除成功");
    }

    /**
     * 医师动态置顶/取消置顶
     */
    @RequestMapping(value = "{id}/{stick}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseObject updateStickDoctorPosts(@PathVariable("id") Integer id,@PathVariable("stick") Boolean stick){
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