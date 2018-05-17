package com.xczhihui.course.params;

import java.io.Serializable;
import java.util.Map;

import com.xczhihui.course.enums.RouteTypeEnum;

/**
 * @author hejiwei
 */
public class BaseMessage implements Serializable {

    private SubMessage smsMessage;

    private SubMessage appPushMessage;

    private SubMessage webMessage;

    private SubMessage appMessage;

    private SubMessage weixinMessage;

    private int type;

    private String userId;

    private String createPerson;
    private String routeType;

    private String detailId;

    private BaseMessage() {
    }

    public static class Builder {
        private BaseMessage baseMessage;

        public Builder(int type) {
            this.baseMessage = new BaseMessage();
            this.baseMessage.type = type;
        }

        public Builder buildSms(String code, Map<String, String> params) {
            this.baseMessage.smsMessage = new SubMessage(code, params);
            return this;
        }

        public Builder buildAppPush(String content) {
            this.baseMessage.appMessage = new SubMessage(content);
            return this;
        }

        public Builder buildWeb(String... content) {
            if (content.length > 0) {
                this.baseMessage.webMessage = new SubMessage(content[0]);
            }
            if (content.length > 1) {
                this.baseMessage.webMessage.setLinkContent(content[1]);
            }
            return this;
        }
//
//        public Builder buildApp(String content) {
//            this.baseMessage.setAppMessage(new SubMessage(content));
//            return this;
//        }

        public Builder buildWeixin(String code, Map<String, String> params) {
            this.baseMessage.weixinMessage = new SubMessage(code, params);
            return this;
        }

        public Builder detailId(String detailId) {
            this.baseMessage.detailId = detailId;
            return this;
        }

        public BaseMessage build(String userId, RouteTypeEnum routeTypeEnum, String createPerson) {
            this.baseMessage.userId = userId;
            this.baseMessage.createPerson = createPerson;
            this.baseMessage.routeType = routeTypeEnum.name();
            return this.baseMessage;
        }
    }

    public SubMessage getSmsMessage() {
        return smsMessage;
    }

    public SubMessage getAppPushMessage() {
        return appPushMessage;
    }

    public SubMessage getWebMessage() {
        return webMessage;
    }

    public SubMessage getAppMessage() {
        return appMessage;
    }

    public SubMessage getWeixinMessage() {
        return weixinMessage;
    }

    public int getType() {
        return type;
    }

    public String getUserId() {
        return userId;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public String getRouteType() {
        return routeType;
    }

    public String getDetailId() {
        return detailId;
    }
}
