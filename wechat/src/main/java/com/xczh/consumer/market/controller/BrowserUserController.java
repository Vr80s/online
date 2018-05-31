package com.xczh.consumer.market.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.enums.VCodeType;
import com.xczhihui.user.center.service.UserCenterService;
import com.xczhihui.user.center.service.VerificationCodeService;


/**
 * 用户调用这个接口，进入h5页面模式 ClassName: BrowserUserController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>
 * email: 15936216273@163.com <br>
 * Create Time: 2017年8月2日<br>
 */
@Controller
@RequestMapping(value = "/bxg/bs")
public class BrowserUserController {

    @Autowired
    private UserCenterService userCenterService;
    @Autowired
    private OnlineUserService onlineUserService;
    @Autowired
    private VerificationCodeService verificationCodeService;

    @Value("${returnOpenidUri}")
    private String returnOpenidUri;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(BrowserUserController.class);

    /**
     * Description：更换手机号 --
     *
     * @param req
     * @param res
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    @RequestMapping(value = "updatePhone")
    @ResponseBody
    public ResponseObject updatePhone(HttpServletRequest req) throws Exception {

        String oldUsername = req.getParameter("oldUsername");
        String newUsername = req.getParameter("newUsername");
        String code = req.getParameter("code");
        String vtype = req.getParameter("vtype");
        if (null == vtype || null == oldUsername || null == newUsername) {
            return ResponseObject.newErrorResponseObject("参数异常");
        }
        // 短信验证码
        if (verificationCodeService.checkCode(newUsername, VCodeType.getType(Integer.parseInt(vtype)), code)) {
            // 更新用户信息
            userCenterService.updateLoginName(oldUsername, newUsername);
            return ResponseObject.newSuccessResponseObject("更改手机号成功");
        } else {
            return ResponseObject.newErrorResponseObject("验证码错误");
        }
    }

    /**
     * Description：通过用户id得到用户信息，返回给前台
     *
     * @param request
     * @return ResponseObject
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    @RequestMapping(value = "getUserInfoById")
    @ResponseBody
    public ResponseObject getUserInfoById(HttpServletRequest request) {

        String userId = request.getParameter("userId");
        try {
            OnlineUser onlineUser = onlineUserService.findUserById(userId);
            return ResponseObject.newSuccessResponseObject(onlineUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
