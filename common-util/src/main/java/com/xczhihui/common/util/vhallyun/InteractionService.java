package com.xczhihui.common.util.vhallyun;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jayway.jsonpath.JsonPath;

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
    
    public static void main(String[] args) throws Exception {
    	
    	System.out.println(create());
	}
}
