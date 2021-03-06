package com.xczhihui.common.util.vhallyun;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jayway.jsonpath.JsonPath;
import com.xczhihui.common.util.vhallyun.result.VhallYunResult;

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
                    userIdList.add((String) user.get("third_party_user_id"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userIdList;
    }

    public static int getStatus(String inavId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("inav_id", inavId);
        try {
            params = VhallUtil.createRealParam(params);
            VhallYunResult vhallYunResult = VhallUtil.sendPostAndRetResult("http://api.yun.vhall.com/api/v1/inav/get-status", params);
            if (vhallYunResult.isOk()) {
                Map<String, Object> data = (Map<String, Object>) vhallYunResult.getData();
                if (data != null) {
                    return (int) data.get("status");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
