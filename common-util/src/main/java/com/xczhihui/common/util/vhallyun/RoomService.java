package com.xczhihui.common.util.vhallyun;

import java.util.HashMap;

import com.jayway.jsonpath.JsonPath;

public class RoomService {

    public static String create() throws Exception {
        HashMap<String, String> params = new HashMap<String, String>();
        params = VhallUtil.createRealParam(params);
        String result = VhallUtil.sendPost("http://api.yun.vhall.com/api/v1/room/create", params);
        String roomId = JsonPath.read(result, "$.data.room_id");
        return roomId;
    }

}
