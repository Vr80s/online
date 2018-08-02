package com.xczhihui.common.util.vhallyun;

import java.util.HashMap;

import com.alibaba.fastjson.JSON;
import com.xczhihui.common.util.IStringUtil;
import com.xczhihui.common.util.bean.VhallMessageParamsVo;

/**
 * 
 * @author yangxuan
 *
 */
public class MessageService {

    
    @SuppressWarnings("unchecked")
    public static Object getMessageList(VhallMessageParamsVo vmpv) throws Exception{
        
        HashMap<String, String> params = vmpv.bulidMap();
        params = VhallUtil.createRealParam(params);
        String result = VhallUtil.sendPost("http://api.yun.vhall.com/api/v1/channel/get-message-list", params);
        return JSON.parse(result);
    }
    
    public static Object sendMessage(String type,String body,String channel_id) throws Exception {
        
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
