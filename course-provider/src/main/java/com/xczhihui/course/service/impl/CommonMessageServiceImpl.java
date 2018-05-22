package com.xczhihui.course.service.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.common.util.CodeUtil;
import com.xczhihui.common.util.SmsUtil;
import com.xczhihui.course.config.Env;
import com.xczhihui.course.consts.MultiUrlHelper;
import com.xczhihui.course.enums.MessageStatusEnum;
import com.xczhihui.course.mapper.MessageMapper;
import com.xczhihui.course.mapper.OnlineUserMapper;
import com.xczhihui.course.mapper.WechatUserMapper;
import com.xczhihui.course.model.Message;
import com.xczhihui.course.model.OnlineUser;
import com.xczhihui.course.model.WechatUser;
import com.xczhihui.course.params.BaseMessage;
import com.xczhihui.course.params.SubMessage;
import com.xczhihui.course.push.PushConst;
import com.xczhihui.course.push.XgMessage;
import com.xczhihui.course.push.XgMessageIOS;
import com.xczhihui.course.service.ICommonMessageService;
import com.xczhihui.course.service.IXgPushService;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

/**
 * @author hejiwei
 */
@Service
public class CommonMessageServiceImpl implements ICommonMessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonMessageServiceImpl.class);

    @Value("${mobile.domain}")
    private String mobileDomain;

    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private OnlineUserMapper onlineUserMapper;
    @Autowired
    private WechatUserMapper wechatUserMapper;
    @Autowired
    private WxMpService wxMpService;
    @Qualifier("iosXgPushService")
    @Autowired
    private IXgPushService iosXgPushService;
    @Qualifier("androidXgPushService")
    @Autowired
    private IXgPushService androidXgPushService;
    @Autowired
    private Env env;

    @Override
    public void saveMessage(BaseMessage baseMessage) {
        insertMessage(baseMessage);
        pushAppMessage(baseMessage);
        pushWeixinMessage(baseMessage);
        sendSMS(baseMessage);
    }

    @Override
    public List<Message> list(int page, String userId) {
        Page<Message> messagePage = new Page<Message>(page, 10);
        return messageMapper.findByUserId(messagePage, userId);
    }

    @Override
    public int countUnReadCntByUserId(String userId) {
        return messageMapper.countUnReadByUserId(userId);
    }

    private void sendSMS(BaseMessage baseMessage) {
        SubMessage smsMessage = baseMessage.getSmsMessage();
        if (smsMessage != null) {
            String code = smsMessage.getCode();
            OnlineUser onlineUser = onlineUserMapper.selectById(baseMessage.getUserId());
            String phoneNumber = onlineUser.getLoginName();
            if (StringUtils.isNotBlank(phoneNumber) && StringUtils.isNotBlank(code)) {
                SmsUtil.sendSMS(code, smsMessage.getParams(), phoneNumber);
            }
        }
    }

    private void pushWeixinMessage(BaseMessage baseMessage) {
        SubMessage weixinMessage = baseMessage.getWeixinMessage();
        if (weixinMessage != null) {
            Map<String, String> params = weixinMessage.getParams();
            WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
            if (params != null && !params.isEmpty()) {
                for (String key : params.keySet()) {
                    wxMpTemplateMessage.addData(new WxMpTemplateData(key, params.get(key)));
                }
                WechatUser wechatUser = wechatUserMapper.findByUserId(baseMessage.getUserId());
                if (wechatUser != null && StringUtils.isNotBlank(wechatUser.getOpenid())) {
                    wxMpTemplateMessage.setTemplateId(weixinMessage.getCode());
                    String url = MultiUrlHelper.getUrl(baseMessage.getRouteType(), MultiUrlHelper.URL_TYPE_MOBILE);
                    if (url != null) {
                        wxMpTemplateMessage.setUrl(mobileDomain + MessageFormat.format(url, baseMessage.getDetailId()));
                    }
                    wxMpTemplateMessage.setToUser(wechatUser.getOpenid());
                    try {
                        wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
                    } catch (WxErrorException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void pushAppMessage(BaseMessage baseMessage) {
        SubMessage appPushMessage = baseMessage.getAppPushMessage();
        String userId = baseMessage.getUserId();
        String detailId = baseMessage.getDetailId();
        if (appPushMessage != null) {
            String content = appPushMessage.getContent();
            int badge = countUnReadCntByUserId(userId);
            String url = MultiUrlHelper.getUrl(baseMessage.getRouteType(), MultiUrlHelper.URL_TYPE_APP);
            if (StringUtils.isNotBlank(url)) {
                url = MessageFormat.format(url, detailId);
            }
            Map<String, Object> customParams = new HashMap<>(5);
            customParams.put("unReadCnt", badge);
            customParams.put("url", url);

            XgMessage xgMessage = new XgMessage();
            xgMessage.setType(PushConst.MESSAGE_TYPE_ANDROID_UT_MESSAGE);
            xgMessage.setContent(content);
            xgMessage.setTitle(PushConst.TITLE);
            xgMessage.setCustom(customParams);
            //安卓推送
            JSONObject androidRet = androidXgPushService.pushSingleAccount(PushConst.DEVICE_ALL, userId, xgMessage);

            XgMessageIOS xgMessageIOS = new XgMessageIOS();
            xgMessageIOS.setAlert(content);
            xgMessageIOS.setBadge(badge);
            xgMessageIOS.setSound(PushConst.IOS_SOUND_FILE);
            xgMessageIOS.setCustom(customParams);
            JSONObject iosRet = iosXgPushService.pushSingleAccount(PushConst.DEVICE_ALL, userId, xgMessageIOS, env.isProd() ? PushConst.IOSENV_PROD : PushConst.IOSENV_DEV);

            if (androidRet != null && androidRet.getInt("ret_code") != 0 && iosRet != null && iosRet.getInt("ret_code") != 0) {
                LOGGER.error(androidRet.toString());
                LOGGER.error(iosRet.toString());
            }
        }
    }

    private void insertMessage(BaseMessage baseMessage) {
        if (baseMessage.getWebMessage() != null) {
            Message message = new Message();
            message.setId(CodeUtil.getRandomUUID());
            message.setContext(baseMessage.getWebMessage().getContent());
            message.setCreateTime(new Date());
            message.setDeleted(false);
            message.setOnline(false);
            message.setReadStatus((short) 0);
            message.setStatus(MessageStatusEnum.ENABLE.getVal());
            message.setType(baseMessage.getType());
            message.setRouteType(baseMessage.getRouteType());
            message.setDetailId(baseMessage.getDetailId());
            message.setCreatePerson(baseMessage.getCreatePerson());
            message.setUserId(baseMessage.getUserId());
            messageMapper.insert(message);
        }
    }
}
