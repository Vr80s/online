package com.xczhihui.common.util.vhallyun;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.jayway.jsonpath.JsonPath;

/**
 * 微吼云点播服务
 *
 * @author hejiwei
 */
public class VideoService {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static Date DEFAULT_START_TIME;
    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, Calendar.JANUARY, 1);
        DEFAULT_START_TIME = calendar.getTime();
    }

    public static String createRecord(String roomId, Date startTime, Date endTime) throws Exception {
        if (startTime == null) {
            startTime = DEFAULT_START_TIME;
        }
        if (endTime == null) {
            endTime = new Date();
        }
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("room_id", roomId);
        params.put("start_time", SDF.format(startTime));
        params.put("end_time", SDF.format(endTime));
        params = VhallUtil.createRealParam(params);
        String result = VhallUtil.sendPost("http://api.yun.vhall.com/api/v1/record/create", params);
        String recordId = JsonPath.read(result, "$.data.record_id");
        return recordId;
    }
}
