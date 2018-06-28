package com.xczhihui.course.service.impl;

import java.util.*;

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
import com.xczhihui.course.enums.RouteTypeEnum;
import com.xczhihui.course.mapper.*;
import com.xczhihui.course.model.*;
import com.xczhihui.course.params.BaseMessage;
import com.xczhihui.course.params.SubMessage;
import com.xczhihui.course.push.PushConst;
import com.xczhihui.course.push.XgMessage;
import com.xczhihui.course.push.XgMessageIOS;
import com.xczhihui.course.service.ICommonMessageService;
import com.xczhihui.course.service.IXgPushService;
import com.xczhihui.course.util.CourseUtil;
import com.xczhihui.course.util.TextStyleUtil;

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
    private CourseMapper courseMapper;
    @Autowired
    private NoticeMapper noticeMapper;
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
    public void saveBatchMessage(BaseMessage baseMessage) {
        List<String> userIds = baseMessage.getUserIds();
        if (baseMessage.getAppPushMessage() != null) {
            batchAppPush(userIds, baseMessage);
        }
        List<Message> messages = new ArrayList<>();
        for (String userId : userIds) {
            messages.add(getMessage(baseMessage, userId));
        }
        messageMapper.batchInsert(messages);
    }

    @Override
    public Page<Message> list(int page, int size, String userId) {
        Page<Message> messagePage = new Page<Message>(page, size);
        return messagePage.setRecords(messageMapper.findByUserId(messagePage, userId));
    }

    @Override
    public int countUnReadCntByUserId(String userId) {
        return messageMapper.countUnReadByUserId(userId);
    }

    @Override
    public void deleteById(String messageId, String userId) {
        messageMapper.markDeleted(messageId, userId);
    }

    @Override
    public void updateReadStatus(String messageId, String userId) {
        messageMapper.updateReadStatus(messageId, userId);
    }

    @Override
    public Notice getNewestNotice() {
        return noticeMapper.findNewest();
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
                    String url = MultiUrlHelper.getUrl(baseMessage.getRouteType(), MultiUrlHelper.URL_TYPE_MOBILE, baseMessage.getDetailId(), null);
                    if (StringUtils.isNotBlank(url)) {
                        wxMpTemplateMessage.setUrl(mobileDomain + url);
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
            commonPush(userId, baseMessage);
        }
    }

    private void commonPush(String userId, BaseMessage baseMessage) {
        SubMessage appPushMessage = baseMessage.getAppPushMessage();
        String content = appPushMessage.getContent();
        String url = MultiUrlHelper.getUrl(baseMessage.getRouteType(), MultiUrlHelper.URL_TYPE_APP, baseMessage.getDetailId(), null);
        Map<String, Object> customParams = new HashMap<>(5);
        customParams.put("url", url);

        XgMessage xgMessage = new XgMessage();
        xgMessage.setType(PushConst.MESSAGE_TYPE_ANDROID_UT_MESSAGE);
        xgMessage.setContent(content);
        xgMessage.setTitle(StringUtils.isNotBlank(baseMessage.getTitle()) ? baseMessage.getTitle() : PushConst.TITLE);
        xgMessage.setCustom(customParams);
        //安卓推送
        JSONObject androidRet = androidXgPushService.pushSingleAccount(PushConst.DEVICE_ALL, userId, xgMessage);

        XgMessageIOS xgMessageIOS = new XgMessageIOS();
        xgMessageIOS.setAlert(content);
        xgMessageIOS.setBadge(1);
        xgMessageIOS.setSound(PushConst.IOS_SOUND_FILE);
        xgMessageIOS.setCustom(customParams);
        JSONObject iosRet = iosXgPushService.pushSingleAccount(PushConst.DEVICE_ALL, userId, xgMessageIOS, PushConst.IOSENV_PROD);

        LOGGER.warn("userId: {}, url:{}, content:{}", userId, url, content);
        if (androidRet != null && androidRet.getInt("ret_code") != 0 && iosRet != null && iosRet.getInt("ret_code") != 0) {
            LOGGER.error(androidRet.toString());
            LOGGER.error(iosRet.toString());
        }
    }

    private void batchAppPush(List<String> userIds, BaseMessage baseMessage) {
        SubMessage appPushMessage = baseMessage.getAppPushMessage();
        String content = appPushMessage.getContent();
        String url = MultiUrlHelper.getUrl(baseMessage.getRouteType(), MultiUrlHelper.URL_TYPE_APP, baseMessage.getDetailId(), null);
        Map<String, Object> customParams = new HashMap<>(5);
        customParams.put("url", url);

        XgMessage xgMessage = new XgMessage();
        xgMessage.setType(PushConst.MESSAGE_TYPE_ANDROID_UT_MESSAGE);
        xgMessage.setContent(content);
        xgMessage.setTitle(StringUtils.isNotBlank(baseMessage.getTitle()) ? baseMessage.getTitle() : PushConst.TITLE);
        xgMessage.setCustom(customParams);
        //安卓推送
        JSONObject androidRet = androidXgPushService.pushAccountList(PushConst.DEVICE_ALL, userIds, xgMessage);

        XgMessageIOS xgMessageIOS = new XgMessageIOS();
        xgMessageIOS.setAlert(content);
        xgMessageIOS.setBadge(1);
        xgMessageIOS.setSound(PushConst.IOS_SOUND_FILE);
        xgMessageIOS.setCustom(customParams);
        JSONObject iosRet = iosXgPushService.pushAccountList(PushConst.DEVICE_ALL, userIds, xgMessageIOS, PushConst.IOSENV_PROD);

        if (androidRet != null && androidRet.getInt("ret_code") != 0 && iosRet != null && iosRet.getInt("ret_code") != 0) {
            LOGGER.error(androidRet.toString());
            LOGGER.error(iosRet.toString());
        }
    }

    private void insertMessage(BaseMessage baseMessage) {
        if (baseMessage.getWebMessage() != null) {
            messageMapper.insert(getMessage(baseMessage, baseMessage.getUserId()));
        }
    }

    private Message getMessage(BaseMessage baseMessage, String userId) {
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
        message.setUserId(userId);
        message.setOuterLink(baseMessage.getLink());
        message.setTitle(StringUtils.isNotBlank(baseMessage.getTitle()) ? baseMessage.getTitle() : Message.SYSTEM_MESSAGE_TITLE);
        return message;
    }
}
