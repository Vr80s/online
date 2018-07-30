package com.xczhihui.common.util.vhallyun;

import java.util.HashMap;

import com.jayway.jsonpath.JsonPath;

public class BaseService {

    public static String createAccessToken(HashMap params) throws Exception {
        params = VhallUtil.createRealParam(params);
        String result = VhallUtil.sendPost("http://api.yun.vhall.com/api/v1/base/create-access-token", params);
        String accessToken = JsonPath.read(result, "$.data.access_token");
        return accessToken;
    }

    public static String createAccessToken4Live(String thirdPartyUserId, String roomId, String channelId) throws Exception {
        HashMap params = new HashMap();
        params.put("third_party_user_id", thirdPartyUserId);
        if(roomId != null){
            params.put("publish_stream",roomId);
        }
        if(channelId != null){
            params.put("chat",channelId);
            params.put("operate_document",channelId);
        }
        return createAccessToken(params);
    }

}
