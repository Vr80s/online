package com.xczh.consumer.market.controller.threeparties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.bean.WxcpClientUserWxMapping;
import com.xczh.consumer.market.interceptor.HeaderInterceptor;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.service.WxcpClientUserWxMappingService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.XzStringUtils;
import com.xczhihui.common.util.enums.*;
import com.xczhihui.course.model.QQClientUserMapping;
import com.xczhihui.course.model.WeiboClientUserMapping;
import com.xczhihui.course.service.IMyInfoService;
import com.xczhihui.course.service.IThreePartiesLoginService;
import com.xczhihui.course.vo.OnlineUserVO;
import com.xczhihui.user.center.service.UserCenterService;
import com.xczhihui.user.center.service.VerificationCodeService;
import com.xczhihui.user.center.utils.UCCookieUtil;
import com.xczhihui.user.center.vo.OeUserVO;
import com.xczhihui.user.center.vo.Token;

import weibo4j.http.HttpClient;

/**
 * ClassName: ThirdPartyCertificationController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>
 * email: 15936216273@163.com <br>
 * Create Time: 2018年2月2日<br>
 */
@Controller
@RequestMapping(value = "/xczh/third")
public class ThirdPartyCertificationController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory
            .getLogger(ThirdPartyCertificationController.class);

    public HttpClient client = new HttpClient();
    // 手机端登录使用
    @Value("${mobile.authorizeURL}")
    public String weiboMobileAuthorizeURL;
    @Autowired
    private OnlineUserService onlineUserService;
    @Autowired
    private IThreePartiesLoginService threePartiesLoginService;
    @Autowired
    private WxcpClientUserWxMappingService wxcpClientUserWxMappingService;
    @Autowired
    private UserCenterService userCenterService;
    @Autowired
    private VerificationCodeService verificationCodeService;
    @Autowired
    private IMyInfoService myInfoService;

    /**
     * Description：微信、微博、qq绑定 已经注册的手机号
     *
     * @param req
     * @param res     手机号
     * @param unionId 第三方唯一标识（微信的、微博的、qq的）
     * @param code    短信验证码
     * @param type    绑定类型 1 微信 2qq 3微博
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    @RequestMapping("thirdPartyBindIsNoMobile")
    @ResponseBody
    @Transactional
    public ResponseObject thirdPartyBindThereAreMobile(HttpServletResponse res, @RequestParam("userName") String userName,
                                                       @RequestParam("unionId") String unionId,
                                                       @RequestParam("code") String code,
                                                       @RequestParam("type") Integer type) throws Exception {

        OnlineUser ou = onlineUserService.findUserByLoginName(userName);
        if (ou == null) {
            return ResponseObject.newErrorResponseObject("该手机号暂未注册,请输入密码");
        }
        if (ou.getStatus() == 1) {
            return ResponseObject.newErrorResponseObject("账号已被系统禁用");
        }

        try {
            verificationCodeService.checkCode(userName, VCodeType.BIND, code);
        } catch (Exception e) {
            //兼容老版本的传参, 之后可移除该catch中的代码
            verificationCodeService.checkCode(userName, VCodeType.FORGOT_PASSWORD, code);
            LOGGER.error("兼容性检验通过, username: {}, code: {}", userName, code);
        }

        switch (type) {
            case 1: // 微信
                WxcpClientUserWxMapping m = wxcpClientUserWxMappingService.getWxcpClientUserByUnionId(unionId);

                if (m == null) {
                    return ResponseObject.newErrorResponseObject
                            (CommonEnumsType.WECHAT_USERINFO_NOFOUND.getText(),
                                    CommonEnumsType.WECHAT_USERINFO_NOFOUND.getCode());
                }
                m.setClient_id(ou.getId());
                wxcpClientUserWxMappingService.update(m);

                // 清理cookie
                UCCookieUtil.clearThirdPartyCookie(res);
                break;
            case 2: // qq
                QQClientUserMapping qq = threePartiesLoginService.selectQQClientUserMappingByOpenId(unionId);
                if (qq == null) {
                    return ResponseObject.newErrorResponseObject
                            (CommonEnumsType.QQ_USERINFO_NOFOUND.getText(),
                                    CommonEnumsType.QQ_USERINFO_NOFOUND.getCode());
                }
                qq.setUserId(ou.getId());
                threePartiesLoginService.updateQQInfoAddUserId(qq);
                break;
            case 3: // 微博
                WeiboClientUserMapping weibo = threePartiesLoginService
                        .selectWeiboClientUserMappingByUid(unionId);
                if (weibo == null) {
                    return ResponseObject.newErrorResponseObject
                            (CommonEnumsType.WEIBO_USERINFO_NOFOUND.getText(),
                                    CommonEnumsType.WEIBO_USERINFO_NOFOUND.getCode());
                }
                weibo.setUserId(ou.getId());
                threePartiesLoginService.updateWeiboInfoAddUserId(weibo);
                break;
            default:
                LOGGER.info(">>>>>>>>>>>>>>>>>>第三方登录类型有误");
                break;
        }
        Token t = userCenterService.loginThirdPart(userName, TokenExpires.TenDay);
        ou.setTicket(t.getTicket());
        // 把用户中心的数据给他 --这些数据是IM的
        ou.setUserCenterId(ou.getId());
        return ResponseObject.newSuccessResponseObject(ou);
    }

    /**
     * 微博、qq绑定 未被注册的 --》增加用户信息，并且用户信息存在用默认的第三方登录的名字和头像 Description：
     *
     * @return void
     * @throws Exception
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    @RequestMapping("thirdPartyBindMobile")
    @ResponseBody
    @Transactional
    public ResponseObject thirdPartyBindMobile(@RequestParam("userName") String userName,
                                               @RequestParam("passWord") String passWord,
                                               @RequestParam("unionId") String unionId,
                                               @RequestParam("code") String code,
                                               @RequestParam("type") Integer type) throws Exception {

        LOGGER.info("三方绑定未注册手机认证参数信息：" + "username:" + userName + ",unionId:"
                + unionId + ",code:" + code + ",type:" + type + "password:"
                + passWord);

        if (!XzStringUtils.checkPhone(userName)) {
            return ResponseObject.newErrorResponseObject("请输入正确的手机号");
        }
        if (!XzStringUtils.checkPassword(passWord)) {
            return ResponseObject.newErrorResponseObject("密码为6-18位英文大小写字母或者阿拉伯数字");
        }

        try {
            verificationCodeService.checkCode(userName, VCodeType.BIND, code);
        } catch (Exception e) {
            //兼容老版本的传参, 之后可移除该catch中的代码
            verificationCodeService.checkCode(userName, VCodeType.RETISTERED, code);
            LOGGER.error("兼容性检验通过, username: {}, code: {}", userName, code);
        }
        Integer sex = 0;
        /**
         * 注册用户
         */
        OeUserVO userVO = userCenterService.getUserVO(userName);
        if (userVO == null) {
            userCenterService.regist(userName, passWord, userName, HeaderInterceptor.getClientType());
            userVO = userCenterService.getUserVO(userName);
        } else {
            return ResponseObject.newErrorResponseObject("该手机号已经注册不用重新输入密码");
        }

        OnlineUserVO ouv = new OnlineUserVO();
        ouv.setId(userVO.getId());

        switch (type) {
            case 1: // 微信
                WxcpClientUserWxMapping m = wxcpClientUserWxMappingService
                        .getWxcpClientUserByUnionId(unionId);

                if ("1".equals(m.getSex())) { // 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
                    sex = 1;
                } else if ("2".equals(m.getSex())) {
                    sex = 0;
                } else {
                    sex = 2;
                }
                ouv.setSex(sex);
                ouv.setName(m.getNickname()); // 微信名字
                ouv.setSmallHeadPhoto(m.getHeadimgurl());// 微信头像

                m.setClient_id(userVO.getId());
                wxcpClientUserWxMappingService.update(m);
                break;
            case 2: // qq
                QQClientUserMapping qq = threePartiesLoginService
                        .selectQQClientUserMappingByUnionId(unionId);

                if ("男".equals(qq.getGender())) { // 性别。 如果获取不到则默认返回"男"
                    sex = 1;
                } else {
                    sex = 0;
                }
                ouv.setSex(sex);
                ouv.setName(qq.getNickname()); // qq名字
                ouv.setSmallHeadPhoto(qq.getFigureurl1()); // qq头像

                qq.setUserId(userVO.getId());
                threePartiesLoginService.updateQQInfoAddUserId(qq);
                break;
            case 3: // 微博
                WeiboClientUserMapping weibo = threePartiesLoginService
                        .selectWeiboClientUserMappingByUid(unionId);

                if ("m".equals(weibo.getGender())) { // 性别，m：男、f：女、n：未知
                    sex = 1;
                } else if ("f".equals(weibo.getGender())) {
                    sex = 0;
                } else {
                    sex = 2;
                }
                ouv.setSex(sex);
                ouv.setName(weibo.getName()); // 微博名字
                ouv.setSmallHeadPhoto(weibo.getProfileImageUrl()); // 微博头像

                weibo.setUserId(userVO.getId());
                threePartiesLoginService.updateWeiboInfoAddUserId(weibo);
                break;
            default:
                LOGGER.info("第三方登录类型有误");
                break;
        }

        //更新用户信息
        myInfoService.updateUserSetInfo(ouv);
        //完善信息后，返回用户信息和token
        Token t = userCenterService.loginThirdPart(userName, TokenExpires.TenDay);
        OnlineUser user = onlineUserService.findUserByLoginName(userName);
        user.setTicket(t.getTicket());
        user.setUserCenterId(userVO.getId());
        user.setPassword(userVO.getPassword());

        return ResponseObject.newSuccessResponseObject(user);
    }

    /**
     * Description：验证-->手机号 是否已经绑定了微信号、qq 或者 微博号
     *
     * @return ResponseObject
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    @RequestMapping(value = "thirdCertificationMobile")
    @ResponseBody
    public ResponseObject thirdCertificationMobile(@RequestParam("userName") String userName,
                                                   @RequestParam("unionId") String unionId,
                                                   @RequestParam("type") Integer type) {
        try {
            OeUserVO userVO = userCenterService.getUserVO(userName);
            OnlineUser ou = onlineUserService.findUserByLoginName(userName);
            int code;
            if (null == userVO && null == ou) {
                code = UserUnitedStateType.PNHONE_NOT_THERE_ARE.getCode();
            } else {
                Object obj = null;
                if (type == ThirdPartyType.WECHAT.getCode()) { // 微信
                    obj = wxcpClientUserWxMappingService
                            .getWxcpClientUserWxMappingByUserId(ou.getId());
                } else if (type == ThirdPartyType.QQ.getCode()) {// QQ
                    obj = threePartiesLoginService
                            .selectQQClientUserMappingByUserId(ou.getId());
                } else if (type == ThirdPartyType.WEIBO.getCode()) {// 微博
                    obj = threePartiesLoginService
                            .selectWeiboClientUserMappingByUserId(ou.getId());
                }
                if (obj == null) { // 已注册手机号,但是未绑定,可进行判断操作
                    code = UserUnitedStateType.PNHONE_IS_WRONG.getCode();
                } else {
                    code = UserUnitedStateType.PNHONE_BINDING.getCode();
                }
            }
            return ResponseObject.newSuccessResponseObject(UserUnitedStateType.valueOf(code), code);
        } catch (Exception e) {
            return ResponseObject.newErrorResponseObject("数据有误");
        }
    }
}
