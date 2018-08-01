package com.xczhihui.common.util.vhallyun;

import java.util.HashMap;

import com.jayway.jsonpath.JsonPath;

public class ChatService {

    public static String CUSTOM_BROADCAST = "CustomBroadcast";

    /**
     * 发送自定义消息
     * @param channelId
     * @param body
     * @throws Exception
     */
    public static void sentCustomBroadcast(String channelId,String body) throws Exception {
        System.out.println(body);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("type",CUSTOM_BROADCAST);
        params.put("channel_id",channelId);
        params.put("body",body);
        params = VhallUtil.createRealParam(params);
        VhallUtil.sendPost("http://api.yun.vhall.com/api/v1/channel/sent", params);
    }

}
