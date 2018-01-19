package com.xczhihui.bxg.online.web.controller.medical;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.common.support.domain.Attachment;
import com.xczhihui.bxg.common.support.service.AttachmentCenterService;
import com.xczhihui.bxg.common.support.service.AttachmentType;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.MedicalDepartment;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.base.common.OnlineResponse;
import com.xczhihui.bxg.online.web.service.UserService;
import com.xczhihui.bxg.online.web.vo.UserDataVo;
import com.xczhihui.medical.department.service.IMedicalDepartmentService;
import com.xczhihui.medical.doctor.model.MedicalDoctorApply;
import com.xczhihui.medical.doctor.service.IMedicalDoctorApplyService;
import com.xczhihui.medical.hospital.service.IMedicalHospitalBusinessService;
import com.xczhihui.medical.hospital.vo.MedicalHospitalVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;

/**
 *  医师入驻申请控制层
 *  @author zhuwenbao
 */
@RestController
@RequestMapping(value = "/medical/doctor/apply")
public class DoctorApplyController {

    @Autowired
    private IMedicalDoctorApplyService applyService;
    @Autowired
    private UserService userService;
    @Autowired
    private AttachmentCenterService attachmentCenterService;
    @Autowired
    private IMedicalHospitalBusinessService hospitalBusinessService;
    @Autowired
    private IMedicalDepartmentService medicalDepartmentService;

    /**
     * 默认分页时每页显示的列数
     */
    private int size = 10;

    /**
     * 添加医师入驻申请
     * @param target 医师入驻申请信息
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseObject add(MedicalDoctorApply target, HttpServletRequest request){

        ResponseObject responseObj = new ResponseObject();

        if(target == null){
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("请求参数不能为空");
            return responseObj;
        }

        // 获取发起申请的医师的id
        OnlineUser loginUser = (OnlineUser)UserLoginUtil.getLoginUser(request);
        if (loginUser == null) {
            return OnlineResponse.newErrorOnlineResponse("请登录！");
        }
        UserDataVo currentUser = userService.getUserData(loginUser);

        target.setUserId(currentUser.getUid());
        applyService.add(target);

        responseObj.setSuccess(true);
        responseObj.setErrorMessage("入驻申请信息提交成功");
        return responseObj;
    }

    /**
     * 根据用户id获取医师入驻申请信息
     */
    @RequestMapping(value = "getLastOne", method = RequestMethod.GET)
    public ResponseObject getLastOne(HttpServletRequest request){

        // 获取当前用户
        OnlineUser loginUser = (OnlineUser)UserLoginUtil.getLoginUser(request);
        if (loginUser == null) {
            return OnlineResponse.newErrorOnlineResponse("请登录！");
        }
        return ResponseObject.newSuccessResponseObject(applyService.getLastOne(userService.getUserData(loginUser).getUid()));
    }

    /**
     * 获取科室列表
     * @param currentPage 当前页
     */
    @RequestMapping(value = "/listDepartment/{currentPage}", method = RequestMethod.GET)
    public ResponseObject listDepartment(@PathVariable Integer currentPage){
        Page<MedicalDepartment> page = new Page();
        page.setCurrent(currentPage);
        page.setSize(size);
        return ResponseObject.newSuccessResponseObject(medicalDepartmentService.page(page));
    }

    /**
     * 获取医馆列表
     * @param currentPage 当前页
     */
    @RequestMapping(value = "/listHospital/{currentPage}", method = RequestMethod.GET)
    public ResponseObject listHospital(@PathVariable Integer currentPage){
        Page<MedicalHospitalVo> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(size);
        return ResponseObject.newSuccessResponseObject(hospitalBusinessService.selectHospitalPage(page, null, null));
    }

    /**
     * 上传图片
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseObject upload(HttpServletRequest request) throws ServletRequestBindingException, IOException {

        // 获取当前用户
        OnlineUser loginUser = (OnlineUser)UserLoginUtil.getLoginUser(request);
        if (loginUser == null) {
            return OnlineResponse.newErrorOnlineResponse("请登录！");
        }
        UserDataVo currentUser = userService.getUserData(loginUser);

        String content = ServletRequestUtils.getRequiredStringParameter(request, "image");
        int i = content.indexOf(',');
        if (i > 0) {
            content = content.substring(i + 1);
        }
        byte[] image = Base64.getDecoder().decode(content);

        Attachment attachment = attachmentCenterService.addAttachment(currentUser.getUid(),
                AttachmentType.ONLINE,
                currentUser.getUid() + "_medicalDoctorApply.png", image,
                org.springframework.util.StringUtils.getFilenameExtension(currentUser.getUid() + "_medicalDoctorApply.png"),
                null);

        return ResponseObject.newSuccessResponseObject(attachment.getUrl());

    }


}
