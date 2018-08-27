package com.xczhihui.common.util.vhallyun;

import com.jayway.jsonpath.JsonPath;
import com.xczhihui.common.util.vhallyun.result.VhallYunResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InteractionService {
    private static final Logger logger = LoggerFactory.getLogger(InteractionService.class);

    public static String create() throws Exception {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("join_max_num", "2");
        params = VhallUtil.createRealParam(params);
        String result = VhallUtil.sendPost("http://api.yun.vhall.com/api/v1/inav/create", params);
        String inavId = JsonPath.read(result, "$.data.inav_id");
        return inavId;
    }

    public static List<String> getInavUserList(String inavId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("inav_id", inavId);
        List<String> userIdList = new ArrayList<>();
        try {
            params = VhallUtil.createRealParam(params);
            VhallYunResult vhallYunResult = VhallUtil.sendPostAndRetResult("http://api.yun.vhall.com/api/v1/inav/inav-user-list", params);
            if (vhallYunResult.isOk()) {
                List<Map<String, Object>> userList = (List<Map<String, Object>>) vhallYunResult.getData();
                for (Map<String, Object> user : userList) {
                    if (((int) user.get("status")) == 2) {
                        userIdList.add((String) user.get("third_party_user_id"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userIdList;
    }
}
