package com.xczhihui.user.center.service.impl;

import static com.xczhihui.common.util.RedisCacheKey.REDIS_SPLIT_CHAR;
import static com.xczhihui.common.util.RedisCacheKey.VCODE_PREFIX;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.xczhihui.common.support.service.impl.RedisCacheService;
import com.xczhihui.common.util.MailUtil;
import com.xczhihui.common.util.SmsUtil;
import com.xczhihui.common.util.enums.VCodeType;
import com.xczhihui.user.center.exception.LoginRegException;
import com.xczhihui.user.center.mapper.SystemVariateMapper;
import com.xczhihui.user.center.model.SystemVariate;
import com.xczhihui.user.center.service.UserCenterService;
import com.xczhihui.user.center.service.VerificationCodeService;
import com.xczhihui.user.center.vo.OeUserVO;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {
    private static final Logger logger = LoggerFactory.getLogger(VerificationCodeServiceImpl.class);
    //一个小时
    private static final int ONE_HOUR_SECOND = 60 * 60;

    @Autowired
    private UserCenterService userCenterService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private RedisCacheService redisCacheService;
    @Autowired
    private SystemVariateMapper systemVariateMapper;
    //数据字典
    private Map<String, String> attrs = new HashMap<String, String>();

    @Value("${web.url}")
    private String weburl;

    @Override
    public boolean addMessage(String username, VCodeType vtype) {
        preCheck(username, vtype);
        String vcode = createVCode(username, vtype);
        //发送，判断邮箱还是手机
        if (isEmail(username)) {
            this.sendEmail(username, vcode, vtype);
        } else {
            this.sendPhone(username, vcode, vtype);
        }
        return true;
    }

    private String createVCode(String username, VCodeType vtype) {
        String vcode;
        String key = VCODE_PREFIX + REDIS_SPLIT_CHAR + vtype.getCode() + REDIS_SPLIT_CHAR + username;
        int vcodeValidTime = 60 * Integer.valueOf(attrs.get("message_provider_valid_time"));
        String vcodeTimeStr = redisCacheService.get(key);
        if (StringUtils.isNotBlank(vcodeTimeStr)) {
            String[] vcodeTimeArr = vcodeTimeStr.split(REDIS_SPLIT_CHAR);
            String preventVCode = vcodeTimeArr[0];
            long createTime = Long.parseLong(vcodeTimeArr[1]);
            vcode = vcodeTimeArr[0];
            long passTime = System.currentTimeMillis() - createTime;
            Integer intervalTime = Integer.valueOf(attrs.get("message_provider_interval_time"));
            if (passTime < 1000 * intervalTime) {
                //发送，判断邮箱还是手机
                if (isEmail(username)) {
                    throw new RuntimeException("同一邮箱两次发送间隔至少" + intervalTime + "秒！");
                } else {
                    throw new RuntimeException("同一手机号两次发送间隔至少" + intervalTime + "秒！");
                }
            }
            if (passTime > vcodeValidTime * 1000) {
                vcode = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 10000));
                redisCacheService.set(key, vcode + REDIS_SPLIT_CHAR + System.currentTimeMillis(), vcodeValidTime + ONE_HOUR_SECOND);
            }
        } else {
            vcode = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 10000));
            redisCacheService.set(key, vcode + REDIS_SPLIT_CHAR + System.currentTimeMillis(), vcodeValidTime + ONE_HOUR_SECOND);
        }
        return vcode;
    }

    private void preCheck(String username, VCodeType vtype) {
        OeUserVO userVO = userCenterService.getUserVO(username);

        if (VCodeType.RETISTERED.equals(vtype) && userVO != null) {
            throw new LoginRegException(String.format("该%s已注册，请直接登录！", isEmail(username) ? "邮箱" : "手机号"));
        }

        if ((VCodeType.WITHDRAWAL.equals(vtype) || VCodeType.FORGOT_PASSWORD.equals(vtype)) || VCodeType.OLD_PHONE.equals(vtype)) {
            if (userVO == null) {
                throw new LoginRegException("用户不存在！");
            } else if (userVO.getStatus() == -1) {
                throw new LoginRegException("用户已禁用！");
            }
        }
        if (VCodeType.NEW_PHONE.equals(vtype) && userVO != null) {
            throw new LoginRegException("此手机号已被绑定");
        }
    }

    private boolean isEmail(String username) {
        return username.contains("@");
    }

    private void sendPhone(String phone, String vcode, VCodeType vtype) {
        //判断发送结果
        SendSmsResponse response;
        Map<String, String> params = new HashMap<>();
        params.put("code", vcode);
        response = SmsUtil.sendSMS(vtype.getSmsCode(), params, phone);
        if (response == null || !"OK".equals(response.getCode())) {
            String message = response != null ? response.getMessage() : null;
            throw new LoginRegException(message != null ? message : "动态码发送失败");
        }
    }

    private void sendEmail(String email, String vcode, VCodeType vCodeType) {
        String content = null;
        //"邮箱"+"!@!"+"UUID"
        vcode = email + "!@!" + vcode;

        if (VCodeType.FORGOT_PASSWORD.equals(vCodeType)) {
            //找回密码邮件内容
            content = MailUtil.getEmailContent(weburl + "/online/user/toResetEmail?vcode=" + vcode);//yuruixin_20170825
        } else if (VCodeType.RETISTERED.equals(vCodeType)) {
            //构建注册邮件验证信息
            content = MailUtil.getRegisterEmailContent(weburl + "/online/user/registEmailValidate?vcode=" + vcode);
        }

        //发送邮件
        try {
            emailService.sendEmail(email, attrs.get("message_provider_email_subject"), content, "text/html;charset=UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            throw new LoginRegException("验证邮件发送失败，请检查邮箱是否存在！");
        }
    }

    @PostConstruct
    public void initSystemVariate() {
        //查数据字典
        List<SystemVariate> lst = systemVariateMapper.listMessageProviderByName();
        for (SystemVariate systemVariate : lst) {
            attrs.put(systemVariate.getName(), systemVariate.getValue());
        }
    }

    @Override
    public boolean checkCode(String phone, VCodeType vtype, String code) {
        String key = VCODE_PREFIX + REDIS_SPLIT_CHAR + vtype.getCode() + REDIS_SPLIT_CHAR + phone;
        String vcodeTimeStr = redisCacheService.get(key);
        if (StringUtils.isNotBlank(vcodeTimeStr)) {
            String[] vcodeTimeArr = vcodeTimeStr.split(REDIS_SPLIT_CHAR);
            long createTime = Long.parseLong(vcodeTimeArr[1]);
            String vcode = vcodeTimeArr[0];
            if (!vcode.equals(code)) {
                throw new LoginRegException("动态码不正确！");
            }
            if (System.currentTimeMillis() - createTime > 1000 * 60
                    * Integer.valueOf(attrs.get("message_provider_valid_time"))) {
                throw new LoginRegException("动态码超时，请重新发送！");
            }
        } else {
            throw new LoginRegException("动态码不正确！");
        }
        return true;
    }
}
