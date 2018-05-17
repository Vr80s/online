package com.xczhihui.course.service.impl;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.common.util.CodeUtil;
import com.xczhihui.common.util.SmsUtil;
import com.xczhihui.course.enums.MessageStatusEnum;
import com.xczhihui.course.mapper.MessageMapper;
import com.xczhihui.course.mapper.OnlineUserMapper;
import com.xczhihui.course.mapper.WechatUserMapper;
import com.xczhihui.course.model.Message;
import com.xczhihui.course.model.OnlineUser;
import com.xczhihui.course.model.WechatUser;
import com.xczhihui.course.params.BaseMessage;
import com.xczhihui.course.params.SubMessage;
import com.xczhihui.course.service.ICommonMessageService;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

/**
 * @author hejiwei
 */
@Service
public class CommonMessageServiceImpl implements ICommonMessageService {

    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private OnlineUserMapper onlineUserMapper;
    @Autowired
    private WechatUserMapper wechatUserMapper;
    @Autowired
    private WxMpService wxMpService;

    @Override
    public void saveMessage(BaseMessage baseMessage) {
        insertMessage(baseMessage);
        pushAppMessage(baseMessage);
        pushWeixinMessage(baseMessage);
        sendSMS(baseMessage);
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

                    wxMpTemplateMessage.setUrl("");
                    wxMpTemplateMessage.setToUser(wechatUser.getOpenid());
                }

            }
        }
    }

    private void pushAppMessage(BaseMessage baseMessage) {
        if (baseMessage.getAppPushMessage() != null) {
            //TODO push message to APP
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
