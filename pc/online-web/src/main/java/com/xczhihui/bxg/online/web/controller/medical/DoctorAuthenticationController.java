package com.xczhihui.bxg.online.web.controller.medical;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.base.common.OnlineResponse;
import com.xczhihui.bxg.online.web.service.UserService;
import com.xczhihui.bxg.online.web.vo.UserDataVo;
import com.xczhihui.medical.doctor.service.IMedicalDoctorAuthenticationInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 医师认证信息控制层
 * @author zhuwenbao
 */
@RestController
@RequestMapping(value = "/medical/doctor/authentication")
public class DoctorAuthenticationController {

    @Autowired
    private UserService userService;
    @Autowired
    private IMedicalDoctorAuthenticationInformationService medicalDoctorAuthenticationInformationService;

    /**
     * 根据用户id获取其医师认证信息
     * @author zhuwenbao
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject getHospitalAuthentication(HttpServletRequest request){

        // 获取当前用户
        OnlineUser loginUser = (OnlineUser) UserLoginUtil.getLoginUser(request);
        if (loginUser == null) {
            return OnlineResponse.newErrorOnlineResponse("请登录！");
        }
        UserDataVo currentUser = userService.getUserData(loginUser);
        return ResponseObject.newSuccessResponseObject(medicalDoctorAuthenticationInformationService.selectDoctorAuthenticationVO(currentUser.getUid()));

//        return ResponseObject.newSuccessResponseObject(medicalDoctorAuthenticationInformationService.selectDoctorAuthentication("2c9aec355f7be767015f7fce0db20001"));
    }

}
