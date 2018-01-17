package com.xczhihui.bxg.online.web.controller.medical;

import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.base.common.OnlineResponse;
import com.xczhihui.bxg.online.web.service.UserService;
import com.xczhihui.bxg.online.web.vo.UserDataVo;
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
    @Autowired
    private UserService userService;

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
    @RequestMapping(method = RequestMethod.GET)
    public ResponseObject get(HttpServletRequest request){

        // 获取当前用户
//        OnlineUser loginUser = (OnlineUser)UserLoginUtil.getLoginUser(request);
//        if (loginUser == null) {
//            return OnlineResponse.newErrorOnlineResponse("请登录！");
//        }
//        UserDataVo currentUser = userService.getUserData(loginUser);

//        applyService.get(currentUser.getUid());

        return ResponseObject.newSuccessResponseObject(applyService.get("8a2c9bed59b5fd22015a122dfc420004"));
    }

}
