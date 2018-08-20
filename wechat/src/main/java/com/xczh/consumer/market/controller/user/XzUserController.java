package com.xczh.consumer.market.controller.user;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.XzStringUtils;
import com.xczhihui.common.util.enums.TokenExpires;
import com.xczhihui.common.util.enums.VCodeType;
import com.xczhihui.user.center.service.UserCenterService;
import com.xczhihui.user.center.service.VerificationCodeService;
import com.xczhihui.user.center.utils.UCCookieUtil;
import com.xczhihui.user.center.vo.Token;

@Controller
@RequestMapping(value = "/xczh/user")
public class XzUserController {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(XzUserController.class);
    @Autowired
    private OnlineUserService onlineUserService;
    @Autowired
    private UserCenterService userCenterService;
    @Autowired
    private VerificationCodeService verificationCodeService;

    /**
     * Description：发送短信验证码
     *
     * @param req
     * @param vtype
     * @param username
     * @return ResponseObject
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping(value = "sendCode")
    @ResponseBody
    public ResponseObject sendCode(@RequestParam("vtype") Integer vtype, @RequestParam("username") String username) {
        if (!XzStringUtils.checkPhone(username)) {
            return ResponseObject.newErrorResponseObject("请输入正确的手机号");
        }
        verificationCodeService.addMessage(username, vtype == null ? VCodeType.RETISTERED : VCodeType.getType(vtype));
        return ResponseObject.newSuccessResponseObject("发送成功");
    }

    /**
     * h5普通浏览器、APP提交注册。
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "phoneRegist")
    @ResponseBody
    public ResponseObject phoneRegist(HttpServletRequest req,
                                      HttpServletResponse res,
                                      @RequestParam("password") String password,
                                      @RequestParam("username") String username,
                                      @RequestParam("code") String code) throws Exception {

        if (!XzStringUtils.checkPhone(username)) {
            return ResponseObject.newErrorResponseObject("请输入正确的手机号");
        }
        if (!XzStringUtils.checkPassword(password)) {
            return ResponseObject.newErrorResponseObject("密码为6-18位英文大小写字母或者阿拉伯数字");
        }
        verificationCodeService.checkCode(username, VCodeType.RETISTERED, code);
        OnlineUser ou = onlineUserService.addPhoneRegistByAppH5(req, password, username);
        Token t = userCenterService.loginMobile(username, password, TokenExpires.TenDay);
        ou.setTicket(t.getTicket());
        ou.setUserCenterId(t.getUserId());
        UCCookieUtil.writeTokenCookie(res, t);
        UCCookieUtil.clearThirdPartyCookie(res);

        return ResponseObject.newSuccessResponseObject(ou);
    }

    /**
     * Description：移动各端端登录
     *
     * @param res
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("login")
    @ResponseBody
    @Transactional
    public ResponseObject login(HttpServletResponse res,
                                @RequestParam("username") String username,
                                @RequestParam("password") String password) throws Exception {

        if (!XzStringUtils.checkPhone(username)) {
            return ResponseObject.newErrorResponseObject("请输入正确的手机号");
        }
        //存储在redis中了，有效期为10天。
        Token t = userCenterService.loginMobile(username, password, TokenExpires.TenDay);

        OnlineUser ou = onlineUserService.findUserByLoginName(username);

        //把用户中心的数据给他   这里im都要用到
        ou.setUserCenterId(ou.getId());
        //把这个票给前端
        ou.setTicket(t.getTicket());
        UCCookieUtil.writeTokenCookie(res, t);
        UCCookieUtil.clearThirdPartyCookie(res);

        return ResponseObject.newSuccessResponseObject(ou);
    }

    /**
     * Description：ios游客登录
     *
     * @param res
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("login4visitor")
    @ResponseBody
    @Transactional
    public ResponseObject login4visitor(HttpServletResponse res, @RequestParam("username") String username) throws Exception {

        //存储在redis中了，有效期为10天。
        Token t = userCenterService.login4visitor(username);

        OnlineUser ou = onlineUserService.findUserByLoginName(username);

        //把用户中心的数据给他   这里im都要用到
        ou.setUserCenterId(ou.getId());
        //把这个票给前端
        ou.setTicket(t.getTicket());
        UCCookieUtil.writeTokenCookie(res, t);
        UCCookieUtil.clearThirdPartyCookie(res);

        return ResponseObject.newSuccessResponseObject(ou);
    }

    /**
     * 忘记密码
     *
     * @return
     */
    @RequestMapping(value = "forgotPassword")
    @ResponseBody
    public ResponseObject forgotPassword(@RequestParam("username") String username,
                                         @RequestParam("password") String password,
                                         @RequestParam("code") String code) throws Exception {
        if (!XzStringUtils.checkPhone(username)) {
            return ResponseObject.newErrorResponseObject("请输入正确的手机号");
        }

        if (!XzStringUtils.checkPassword(password)) {
            return ResponseObject.newErrorResponseObject("密码为6-18位英文大小写字母或者阿拉伯数字");
        }

        if (verificationCodeService.checkCode(username, VCodeType.FORGOT_PASSWORD, code)) {
            userCenterService.resetPassword(username, password);
            return ResponseObject.newSuccessResponseObject("修改密码成功");
        } else {
            return ResponseObject.newErrorResponseObject("修改密码失败");
        }
    }

    /**
     * Description：校验手机号
     * @param username 手机号
     * @return ResponseObject
     * @throws SQLException
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping(value = "verifyPhone")
    @ResponseBody
    public ResponseObject sendCode(@RequestParam("username") String username) throws SQLException {
        if (!XzStringUtils.checkPhone(username)) {
            return ResponseObject.newErrorResponseObject("请输入正确的手机号");
        }
        onlineUserService.verifyPhone(username);
        return ResponseObject.newSuccessResponseObject("验证成功");
    }


    
    @RequestMapping(value = "synchronizationUserNameToVhallYun")
    @ResponseBody
    public ResponseObject synchronizationUserNameToVhallYun() throws SQLException {
    	List<OnlineUser> list =  onlineUserService.selectAllUser();
    	LOGGER.warn("===========================：");
    	LOGGER.warn("一共多少条{}："+list.size());
    	LOGGER.warn("===========================：");
    	try {
//        	for (OnlineUser onlineUser : list) {
//        		if(StringUtils.isNotBlank(onlineUser.getId()) && StringUtils.isNotBlank(onlineUser.getName()) && 
//        						StringUtils.isNotBlank(onlineUser.getSmallHeadPhoto()) ) {
//        			
//        			LOGGER.warn(onlineUser.getId()+"   "+onlineUser.getName() +"   "+onlineUser.getSmallHeadPhoto());
//        			
//        			MessageService.saveUserInfo(onlineUser.getId(), onlineUser.getName(), onlineUser.getSmallHeadPhoto());
//        		}
//    		}
		} catch (Exception e) {
			 e.printStackTrace();
		}

        return ResponseObject.newSuccessResponseObject("验证成功");
    }
    
}
