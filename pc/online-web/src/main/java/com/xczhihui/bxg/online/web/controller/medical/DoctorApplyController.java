package com.xczhihui.bxg.online.web.controller.medical;

import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.medical.doctor.model.MedicalDoctorApply;
import com.xczhihui.medical.doctor.service.IMedicalDoctorApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 *  医师入驻申请控制层
 *  @author zhuwenbao
 */
@RestController
@RequestMapping(value = "/medical/doctor/apply")
public class DoctorApplyController {

    @Autowired
    private IMedicalDoctorApplyService applyService;


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
        BxgUser loginUser = UserLoginUtil.getLoginUser(request);
        if(loginUser == null){
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("您尚未登陆");
            return responseObj;
        }

        target.setUserId(loginUser.getId());
        applyService.add(target);

        responseObj.setSuccess(true);
        responseObj.setErrorMessage("入驻申请信息提交成功");
        return responseObj;
    }

}
