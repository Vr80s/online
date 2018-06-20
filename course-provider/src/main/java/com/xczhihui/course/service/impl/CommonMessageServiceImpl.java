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
    public void updateMessage() {
        int page = 1;
        int size = 500;
        int offset = 0;
        Page<Message> messagePage;
        List<Message> messages;
        do {
            messages = messageMapper.list(offset, size);
            for (Message message : messages) {
                try {
                    String context = message.getContext();
                    String detailId = null;
                    String routeType = null;
                    if (context != null) {
                        if (context.contains("forumDetail.html")) {
                            String[] split = context.split("<a");
                            String prefixText = split[0];
                            context = split[1];
                            context = context.split(",")[1];
                            context = context.split("articleId=")[1];
                            detailId = context.split("'")[0];
                            context = context.split("\">")[1];
                            String title = context.split("</a>")[0];
                            String tailText = context.split("</a>")[1];
                            context = prefixText + TextStyleUtil.LEFT_TAG + title + TextStyleUtil.RIGHT_TAG + tailText;
                            routeType = RouteTypeEnum.ARTICLE_DETAIL.name();
                        } else if (context.contains("web/qusDetail")) {
                            String[] split = context.split("<a");
                            String prefixText = split[0];
                            context = split[1];
                            context = context.split(",")[1];
                            context = context.split("web/qusDetail/")[1];
                            detailId = context.split("'")[0];
                            context = context.split("\">")[1];
                            String title = context.split("</a>")[0];
                            context = prefixText + TextStyleUtil.LEFT_TAG + title + TextStyleUtil.RIGHT_TAG;
                            routeType = RouteTypeEnum.QUESTION_DETAIL.name();
                        } else if (context.contains("course/courses/") || context.contains("course/courses?")) {
                            String contextCopy = context;
                            String[] split = context.split("<a");
                            String prefixText = split[0];
                            context = split[1];
                            context = context.split(",")[1];
                            context = context.split(contextCopy.contains("course/courses/") ? "course/courses/" : "\\?courseId=")[1];
                            detailId = context.split("'")[0];
                            context = context.split("<p>")[1];
                            String title = context.split("</p>")[0];
                            context = prefixText + TextStyleUtil.LEFT_TAG + title + TextStyleUtil.RIGHT_TAG;
                            Course course = courseMapper.selectById(detailId);
                            if (course == null) {
                                continue;
                            }
                            routeType = CourseUtil.getRouteType(course.getCollection(), course.getType()).name();
                        } else {
                            continue;
                        }
                        message.setContext(context);
                        message.setRouteType(routeType);
                        message.setDetailId(detailId);
                        messageMapper.updateById(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (messages.isEmpty() || messages.size() < size) {
                break;
            } else {
                page = page + 1;
                offset = (page - 1) * size;
            }
        } while (true);
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
                    if (StringUtils.isNotBlank(url)) {
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
            String url = MultiUrlHelper.getUrl(baseMessage.getRouteType(), MultiUrlHelper.URL_TYPE_APP);
            if (StringUtils.isNotBlank(url)) {
                url = MessageFormat.format(url, detailId);
            }
            Map<String, Object> customParams = new HashMap<>(5);
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
            xgMessageIOS.setBadge(1);
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
