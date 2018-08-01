package com.xczhihui.common.util.vhallyun;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jayway.jsonpath.JsonPath;

public class ChannelService {
    private static final Logger logger = LoggerFactory.getLogger(ChannelService.class);

    public static String create() throws Exception {
        HashMap<String, String> params = new HashMap<String, String>();
        params = VhallUtil.createRealParam(params);
        String result = VhallUtil.sendPost("http://api.yun.vhall.com/api/v1/channel/create", params);
        String channelId = JsonPath.read(result, "$.data.channel_id");
        return channelId;
    }
}
