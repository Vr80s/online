package com.xczhihui.bxg.online.web.controller.medical;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.common.support.domain.Attachment;
import com.xczhihui.common.support.service.AttachmentCenterService;
import com.xczhihui.common.util.JsonUtil;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.medical.anchor.service.ICourseApplyService;
import com.xczhihui.medical.doctor.model.MedicalDoctorAccount;
import com.xczhihui.medical.doctor.model.MedicalDoctorPosts;
import com.xczhihui.medical.doctor.service.IMedicalDoctorAccountService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorPostsService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import static com.xczhihui.bxg.online.web.controller.AbstractController.getCurrentUser;

/**
 * Description：医师控制器
 * creed: Talk is cheap,show me the code
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/6/25 11:32
 **/
@Controller
@RequestMapping("/doctor/posts")
public class MedicalDoctorPostsController {

    @Autowired
    private IMedicalDoctorPostsService medicalDoctorPostsService;
    @Autowired
    private AttachmentCenterService service;
    @Autowired
    private IMedicalDoctorAccountService medicalDoctorAccountService;
    @Autowired
    private ICourseApplyService courseApplyService;


    private static final org.slf4j.Logger LOGGER = LoggerFactory
            .getLogger(MedicalDoctorPostsController.class);

    /**
     * 医师动态列表
     */
    @RequestMapping(value="doctorPostsList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject doctorPostsList(@RequestParam("pageNumber") Integer pageNumber,
                                             @RequestParam("pageSize") Integer pageSize,
                                             @RequestParam(required = false) Integer type, @RequestParam("doctorId") String doctorId){
        // 获取当前用户ID
        String userId = getCurrentUser().getId();
        Page<MedicalDoctorPosts> page = new Page<>();
        page.setCurrent(pageNumber);
        page.setSize(pageSize);
        Page<MedicalDoctorPosts> list = medicalDoctorPostsService.selectMedicalDoctorPostsPage(page,type,doctorId,userId);
        return ResponseObject.newSuccessResponseObject(list);
    }

    /**
     * 添加医师动态
     */
    @RequestMapping(value="addDoctorPosts", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject addDoctorPosts( MedicalDoctorPosts medicalDoctorPosts){
        // 获取当前用户ID
        String userId = getCurrentUser().getId();
        MedicalDoctorAccount mha = medicalDoctorAccountService.getByUserId(userId);
        medicalDoctorPosts.setDoctorId(mha.getDoctorId());
        medicalDoctorPostsService.addMedicalDoctorPosts(medicalDoctorPosts);
        if(!medicalDoctorPosts.getVideo().equals("")){
            //courseApplyService.saveCourseApplyResource(courseApplyResource);
        }
        return ResponseObject.newSuccessResponseObject("添加成功");
    }

    /**
     * 编辑医师动态
     */
    @RequestMapping(value="updateDoctorPosts", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateDoctorPosts(MedicalDoctorPosts medicalDoctorPosts)
            throws Exception {
        // 获取当前用户ID
        String userId = getCurrentUser().getId();
        medicalDoctorPostsService.updateMedicalDoctorPosts(medicalDoctorPosts);
        return ResponseObject.newSuccessResponseObject("编辑成功");
    }

    /**
     * 删除医师动态
     */
    @RequestMapping(value="deleteDoctorPosts", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deleteDoctorPosts(@RequestParam("id") Integer id){
        medicalDoctorPostsService.deleteMedicalDoctorPosts(id);
        return ResponseObject.newSuccessResponseObject("删除成功");
    }

    /**
     * 医师动态置顶/取消置顶
     */
    @RequestMapping(value="updateStickDoctorPosts", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateStickDoctorPosts(@RequestParam("id") Integer id,@RequestParam("stick") Boolean stick){
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
    private String getFilePath(MultipartFile imgFile,String accountId) throws Exception {
            String upload = service.upload(accountId,"other", imgFile.getOriginalFilename(),
                    imgFile.getContentType(), imgFile.getBytes(), "1");
        Attachment att = JsonUtil.getBaseGson().fromJson(upload, Attachment.class);
        return att.getUrl();
    }

}