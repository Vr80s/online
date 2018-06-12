package com.xczhihui.common.util;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * Created on 17/6/7.
 * 短信API产品的DEMO程序,工程中包含了一个SmsDemo类，直接通过
 * 执行main函数即可体验短信产品API功能(只需要将AK替换成开通了云通信-短信产品功能的AK即可)
 * 工程依赖了2个jar包(存放在工程的libs目录下)
 * 1:aliyun-java-sdk-core.jar
 * 2:aliyun-java-sdk-dysmsapi.jar
 * <p>
 * 备注:Demo工程编码采用UTF-8
 */
public class SmsUtil {

    private static final Logger logger = LoggerFactory.getLogger(SmsUtil.class);
    private static final String PRODUCT = "Dysmsapi";
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";

    private static final String DEFAULT_SIGN = "熊猫中医";

    private static final String ACCESS_KEY_ID = "LTAIwptezJl2QHcu";
    private static final String ACCESS_KEY_SECRET = "0ndfvxDux1DHTVEgLUqoQI6FDasg3p";
    private static final String OK = "OK";
    private static final String BUSINESS_LIMIT_CONTROL_CODE = "isv.BUSINESS_LIMIT_CONTROL";
    private static final String BUSINESS_LIMIT_CONTROL_DAY_MESSAGE = "触发天级流控Permits:10";
    private static final String BUSINESS_LIMIT_CONTROL_HOUR_MESSAGE = "触发小时级流控Permits:5";

    private static IAcsClient aliAcsClient;

    static {
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", PRODUCT, DOMAIN);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        aliAcsClient = new DefaultAcsClient(profile);
    }

    /**
     * @param phoneNumber手机号
     * @param courseName课程名
     * @param startTime开始时间
     * @param m据直播开始分钟数
     * @param advance        是否为订阅成功通知
     * @return
     * @throws ClientException
     */
    public static SendSmsResponse sendSmsSubscribe(String phoneNumber, String courseName, String startTime, String m, boolean advance) throws ClientException {

        String json = null;
        String templateCode = null;

        if (advance) {
            templateCode = "SMS_85500030";
            json = "{ \"courseName\":\"" + courseName + "\",\"startTime\":\"" + startTime + "\"}";
        } else {
            templateCode = "SMS_85385035";
            json = "{ \"courseName\":\"" + courseName + "\",\"m\":\"" + m + "\"}";
        }

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", PRODUCT, DOMAIN);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phoneNumber);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(DEFAULT_SIGN);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(json);
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
//        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
    }

    /**
     * 短信推送通用方法
     *
     * @param templateCode 阿里云模板code
     * @param params       参数
     * @param phoneNumber  电话号码
     */
    public static SendSmsResponse sendSMS(String templateCode, Map<String, String> params, String phoneNumber) {
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(phoneNumber);
        request.setSignName(DEFAULT_SIGN);
        request.setTemplateCode(templateCode);
        request.setTemplateParam(params != null ? JsonUtil.mapToString(params) : null);
        SendSmsResponse acsResponse = null;
        try {
            acsResponse = aliAcsClient.getAcsResponse(request);
        } catch (ClientException e) {
            logger.error("send sms error, params:{}, phoneNumber:{}, templateCode:{}", JsonUtil.mapToString(params), phoneNumber, templateCode);
            e.printStackTrace();
        }
        if (acsResponse != null) {
            String code = acsResponse.getCode();
            if (!OK.equals(code)) {
                //流控提示
                String message = acsResponse.getMessage();
                if (BUSINESS_LIMIT_CONTROL_CODE.equals(code)) {
                    if (BUSINESS_LIMIT_CONTROL_DAY_MESSAGE.equals(message)) {
                        acsResponse.setMessage("该业务今天短信额度超出限制");
                    } else if (BUSINESS_LIMIT_CONTROL_HOUR_MESSAGE.equals(message)) {
                        acsResponse.setMessage("一小时内短信发送超限,请稍后重试");
                    }
                }
                logger.error("sms response code : {}, tel: {}", code, phoneNumber);
            }
        }
        return acsResponse;
    }
}
