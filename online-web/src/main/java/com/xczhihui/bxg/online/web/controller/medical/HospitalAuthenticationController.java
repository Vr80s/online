package com.xczhihui.bxg.online.web.controller.medical;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.controller.AbstractController;
import com.xczhihui.bxg.online.web.service.UserService;
import com.xczhihui.bxg.online.web.vo.UserDataVo;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.medical.hospital.service.IMedicalHospitalAuthenticationService;

/**
 * 医馆认证信息控制层
 *
 * @author zhuwenbao
 */
@RestController
@RequestMapping(value = "/hospital/authentication")
public class HospitalAuthenticationController extends AbstractController {

    @Autowired
    private UserService userService;
    @Autowired
    private IMedicalHospitalAuthenticationService hospitalAuthenticationService;

    /**
     * 根据用户id获取其医馆认证信息
     *
     * @author zhuwenbao
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject getHospitalAuthentication(HttpServletRequest request) {

        // 获取当前用户
        OnlineUser loginUser = getCurrentUser();
        UserDataVo currentUser = userService.getUserData(loginUser);
        return ResponseObject.newSuccessResponseObject(hospitalAuthenticationService.selectHospitalAuthentication(currentUser.getUid()));
    }

}
