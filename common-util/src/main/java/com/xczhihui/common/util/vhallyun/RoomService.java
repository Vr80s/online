package com.xczhihui.common.util.vhallyun;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jayway.jsonpath.JsonPath;

public class RoomService {

    public static String create() throws Exception {
        HashMap<String, String> params = new HashMap<String, String>();
        params = VhallUtil.createRealParam(params);
        String result = VhallUtil.sendPost("http://api.yun.vhall.com/api/v1/room/create", params);
        String roomId = JsonPath.read(result, "$.data.room_id");
        return roomId;
    }

    public static List<String> listLiveOpening() {
        HashMap<String, String> params = new HashMap<String, String>();
        try {
            params = VhallUtil.createRealParam(params);
            String result = VhallUtil.sendPost("http://api.yun.vhall.com/api/v1/room/get-pushing-stream-list", params);
            return JsonPath.read(result, "$.data.room_list");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
