package com.xczhihui.bxg.online.web.controller.medical;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.base.common.OnlineResponse;
import com.xczhihui.medical.doctor.model.MedicalHospitalApply;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 *  医馆入驻申请控制层
 *  @author zhuwenbao
 *  @date 2018-01-17
 */
@RestController
@RequestMapping(value = "/medical/hospital/apply")
public class HospitalApplyController {

    /**
     * 添加医馆入驻申请
     * @param target 医师入驻申请信息
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseObject add(MedicalHospitalApply target, HttpServletRequest request){

        ResponseObject responseObj = new ResponseObject();

        responseObj.setSuccess(true);
        responseObj.setErrorMessage("入驻申请信息提交成功");
        return responseObj;
    }

    /**
     * 根据用户id获取医师入驻申请信息
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseObject get(HttpServletRequest request){

        // 获取当前用户
        OnlineUser loginUser = (OnlineUser)UserLoginUtil.getLoginUser(request);
        if (loginUser == null) {
            return OnlineResponse.newErrorOnlineResponse("请登录！");
        }
        return ResponseObject.newSuccessResponseObject(null);
    }

}
