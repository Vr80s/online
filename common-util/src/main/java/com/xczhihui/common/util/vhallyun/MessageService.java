package com.xczhihui.common.util.vhallyun;

import java.util.HashMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.JsonPath;
import com.xczhihui.common.util.IStringUtil;
import com.xczhihui.common.util.bean.VhallMessageParamsVo;

public class MessageService {

//    channel_id  string  是   频道ID
//    type    int 否   查询类型 ，1 聊天列表（ 默认），2 自定义聊天列表
//    pos int 否   获取条目节点，默认为 0。eg : 10 从第10条开始查询
//    limit   int 否   获取条目数量，默认为 10 条，最大为1000条
//    start_time  date    是   查询开始时间，格式为：2017/01/01
//    end_time    date    否   查询结束时间，默认为当前时间，格式为：2017/01/01
    
    @SuppressWarnings("unchecked")
    private static Object getMessageList(VhallMessageParamsVo vmpv) throws Exception{
        
        HashMap<String, String> params = vmpv.bulidMap();
        params = VhallUtil.createRealParam(params);
        String result = VhallUtil.sendPost("http://api.yun.vhall.com/api/v1/channel/get-message-list", params);
        System.out.println(result);
        return JSON.parse(result);
    }
    
    private static Object sendMessage(String type,String body,String channel_id) throws Exception {
        
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("type", type);
        if(IStringUtil.isNotBlank(body)) {
            params.put("body", body);
        }
        params.put("channel_id", channel_id);
        params = VhallUtil.createRealParam(params);
        String result = VhallUtil.sendPost("http://api.yun.vhall.com/api/v1/channel/sent", params);
        System.out.println(result);
        
        return JSON.parse(result);
        
    }
    
    
    public static void main(String[] args) throws Exception {
        VhallMessageParamsVo vhallMessageParamsVo = new VhallMessageParamsVo();
        vhallMessageParamsVo.setChannel_id("ch_fc6f51f4");
        vhallMessageParamsVo.setType("2");
        vhallMessageParamsVo.setPos("0");
        vhallMessageParamsVo.setLimit("10");
        vhallMessageParamsVo.setStart_time("1980/01/01");
        //vhallMessageParamsVo.setEnd_time("1980/01/02");
        System.out.println(getMessageList(vhallMessageParamsVo));
        //sendMessage("CustomBroadcast","啦啦啦啦","ch_fc6f51f4");
    }
    
}
