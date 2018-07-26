package com.xczhihui.course.service.impl;

import static com.xczhihui.common.util.RedisCacheKey.XG_ACCOUNT_KEY;

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
import com.google.common.collect.ImmutableList;
import com.xczhihui.common.support.service.CacheService;
import com.xczhihui.common.util.CodeUtil;
import com.xczhihui.common.util.SmsUtil;
import com.xczhihui.common.util.enums.MessageStatusEnum;
import com.xczhihui.common.util.enums.RouteTypeEnum;
import com.xczhihui.course.config.Env;
import com.xczhihui.course.consts.MultiUrlHelper;
import com.xczhihui.course.mapper.*;
import com.xczhihui.course.model.Message;
import com.xczhihui.course.model.Notice;
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
    private static final int V2_LIMIT = 100;

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
    @Autowired
    private CacheService cacheService;

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

    @Override
    public void bindXgAccountId(String userId, String xgAccountId) {
        cacheService.sadd(XG_ACCOUNT_KEY + userId, xgAccountId);
    }

    @Override
    public void unBindXgAccountId(String userId, String xgAccountId) {
        cacheService.srem(XG_ACCOUNT_KEY + userId, xgAccountId);
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
            batchAppPush(ImmutableList.of(userId), baseMessage);
        }
    }

    private void batchAppPush(List<String> userIds, BaseMessage baseMessage) {
        List<String> xgAccountIds = getXgAccountId(userIds);
        if (xgAccountIds.isEmpty()) {
            if (!userIds.isEmpty() && userIds.size() == 1) {
                LOGGER.error("can't find xgAccountId userId: {}", userIds.get(0));
            }
            return ;
        }
        SubMessage appPushMessage = baseMessage.getAppPushMessage();
        String content = appPushMessage.getContent();
        String routeType = baseMessage.getRouteType();
        String url = MultiUrlHelper.getUrl(routeType, MultiUrlHelper.URL_TYPE_APP, baseMessage.getDetailId(), null);
        if (StringUtils.isBlank(url)) {
            url = MultiUrlHelper.getUrl(RouteTypeEnum.MESSAGE_LIST.name(), MultiUrlHelper.URL_TYPE_APP, null, null);
        }
        Map<String, Object> customParams = new HashMap<>(1);
        customParams.put("url", url);


        int firstIndex = 0;
        int endIndex = V2_LIMIT > xgAccountIds.size() ? xgAccountIds.size() : V2_LIMIT;

        XgMessage xgMessage = new XgMessage();
        xgMessage.setType(PushConst.MESSAGE_TYPE_ANDROID_UT_MESSAGE);
        xgMessage.setContent(content);
        xgMessage.setTitle(StringUtils.isNotBlank(baseMessage.getTitle()) ? baseMessage.getTitle() : PushConst.TITLE);
        xgMessage.setCustom(customParams);

        XgMessageIOS xgMessageIOS = new XgMessageIOS();
        xgMessageIOS.setAlert(content);
        xgMessageIOS.setBadge(1);
        xgMessageIOS.setSound(PushConst.IOS_SOUND_FILE);
        xgMessageIOS.setCustom(customParams);
        List<String> onceUserIds;
        while (true) {
            onceUserIds = xgAccountIds.subList(firstIndex, endIndex);
            JSONObject androidRet = androidXgPushService.pushAccountList(PushConst.DEVICE_ALL, onceUserIds, xgMessage);
            JSONObject iosRet = iosXgPushService.pushAccountList(PushConst.DEVICE_ALL, onceUserIds, xgMessageIOS, PushConst.IOSENV_PROD);
            if (androidRet != null && androidRet.getInt("ret_code") != 0 && iosRet != null && iosRet.getInt("ret_code") != 0) {
                LOGGER.error(androidRet.toString());
                LOGGER.error(iosRet.toString());
            }
            if (onceUserIds.size() < V2_LIMIT) {
                break;
            } else {
                firstIndex = endIndex;
                endIndex = endIndex + V2_LIMIT;
                if (endIndex >= xgAccountIds.size()) {
                    endIndex = xgAccountIds.size();
                }
            }
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

    private List<String> getXgAccountId(List<String> userIds) {
        Set<String> totalIds = new HashSet<>();
        Set<String> userIdSet;
        for (String userId : userIds) {
            userIdSet = cacheService.smembers(XG_ACCOUNT_KEY + userId);
            if (userIdSet != null && !userIdSet.isEmpty()) {
                totalIds.addAll(userIdSet);
            }
        }
        return new ArrayList<>(totalIds);
    }
}
