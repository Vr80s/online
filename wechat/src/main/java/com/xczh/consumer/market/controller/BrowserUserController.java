package com.xczh.consumer.market.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
     * h5、APP提交注册。微信公众号是没有注册的，直接就登录了
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "phoneRegist")
    @ResponseBody
    @Transactional
    public ResponseObject phoneRegist(HttpServletRequest req,
                                      HttpServletResponse res) throws Exception {
        LOGGER.info("老版本方法----》》》》phoneRegist");
        return ResponseObject.newErrorResponseObject("请使用最新版本");
    }

    /**
     * 忘记密码
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "forgotPassword")
    @ResponseBody
    public ResponseObject forgotPassword(HttpServletRequest req,
                                         HttpServletResponse res) throws Exception {

        LOGGER.info("老版本方法----》》》》forgotPassword");
        return ResponseObject.newErrorResponseObject("请使用最新版本");
    }

    /**
     * 修改密码
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "editPassword")
    @ResponseBody
    public ResponseObject editPassword(HttpServletRequest req,
                                       HttpServletResponse res) throws Exception {

        LOGGER.info("老版本方法----》》》》editPassword");
        return ResponseObject.newErrorResponseObject("请使用最新版本");
    }

    /**
     * 修改密码
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "editNewPassword")
    @ResponseBody
    public ResponseObject editNewPassword(HttpServletRequest req,
                                          HttpServletResponse res) throws Exception {

        LOGGER.info("老版本方法----》》》》");
        return ResponseObject.newErrorResponseObject("请使用最新版本");
    }

    /**
     * 验证密码是否一致
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "checkPassword")
    @ResponseBody
    public ResponseObject checkPassword(HttpServletRequest req,
                                        HttpServletResponse res) throws Exception {

        LOGGER.info("老版本方法----》》》》");
        return ResponseObject.newErrorResponseObject("请使用最新版本");

    }

    /**
     * Description：判断此用户有没有注册过
     *
     * @param req
     * @param res
     * @param params
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    @RequestMapping("isReg")
    @ResponseBody
    public ResponseObject isReg(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        LOGGER.info("老版本方法----》》》》");
        return ResponseObject.newErrorResponseObject("请使用最新版本");
    }

    /**
     * Description：浏览器端登录
     *
     * @param req
     * @param res
     * @param params
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    @RequestMapping("login")
    @ResponseBody
    @Transactional
    public ResponseObject login(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        LOGGER.info("老版本方法----》》》》");
        return ResponseObject.newErrorResponseObject("请使用最新版本");
    }

    @RequestMapping(value = "/index")
    public void index(HttpServletRequest req, HttpServletResponse res)
            throws Exception {
    }

    /**
     * Description：app端第三方登录
     *
     * @param req
     * @param res
     * @param params
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    @RequestMapping(value = "addGetUserInfoByCode")
    @ResponseBody
    @Transactional
    public ResponseObject addGetUserInfoByCode(HttpServletRequest req,
                                               HttpServletResponse res) throws Exception {


        LOGGER.info("WX get openid:" + req.getParameter("openid"));
        LOGGER.info("老版本方法----》》》》");
        return ResponseObject.newErrorResponseObject("请使用最新版本");
    }

    /**
     * Description：app端第三方登录 ---绑定手机号使用
     *
     * @param req
     * @param res
     * @param params
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    @RequestMapping(value = "appThirdPartyLogin")
    @ResponseBody
    @Transactional
    public ResponseObject addAppThirdparty(HttpServletRequest req,
                                           HttpServletResponse res) throws Exception {

        LOGGER.info("WX get access_token	:" + req.getParameter("access_token"));
        LOGGER.info("WX get openid	:" + req.getParameter("openid"));

        LOGGER.info("老版本方法----》》》》");
        return ResponseObject.newErrorResponseObject("请使用最新版本");
    }

    @RequestMapping(value = "getBalanceTotal")
    @ResponseBody
    public ResponseObject getBalanceTotal(HttpServletRequest req,
                                          HttpServletResponse res) throws Exception {

        LOGGER.info("老版本方法----》》》》");
        return ResponseObject.newErrorResponseObject("请使用最新版本");
    }

    /**
     * Description：给 vtype 包含 3 和 4 发送短信。 如果是3的话，需要判断此手机号是否绑定，在发短信。
     * 如果是4的话，需要判断此要更改的手机号是否绑定，如果绑定就不发短信了。
     *
     * @param req
     * @param res
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    @RequestMapping(value = "phoneCheck")
    @ResponseBody
    public ResponseObject phoneCheck(HttpServletRequest req,
                                     HttpServletResponse res) throws Exception {
        LOGGER.info("老版本方法----》》》》");
        return ResponseObject.newErrorResponseObject("请使用最新版本");
    }

    /**
     * Description：验证 3 的手机号是否相同
     *
     * @param req
     * @param res
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    @RequestMapping(value = "phoneCheckAndCode")
    @ResponseBody
    public ResponseObject phoneCheckAndCode(HttpServletRequest req,
                                            HttpServletResponse res) throws Exception {
        LOGGER.info("老版本方法----》》》》");
        return ResponseObject.newErrorResponseObject("请使用最新版本");
    }

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

    /**
     * Description：
     *
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    @RequestMapping("appLogin")
    @ResponseBody
    public ResponseObject appOnlyOneId() throws Exception {
        return ResponseObject.newErrorResponseObject("请使用最新版本");
    }

    /**
     * apple用户提交注册
     *
     * @return
     */
    @RequestMapping(value = "applePhoneRegist")
    @ResponseBody
    public ResponseObject applePhoneRegist() throws Exception {
        return ResponseObject.newErrorResponseObject("请使用最新版本");
    }
}
