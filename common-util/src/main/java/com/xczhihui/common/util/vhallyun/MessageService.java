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

	// 
	/**
	 * 微吼自定义消息类型   CustomBroadcast
	 *  http://www.vhallyun.com/docs/show/1041.html  发送消息:接口
	 *   
	 */
	public static String  CustomBroadcast  = "CustomBroadcast";
	
    
    @SuppressWarnings("unchecked")
    public static Object getMessageList(VhallMessageParamsVo vmpv) throws Exception{
        
        HashMap<String, String> params = vmpv.bulidMap();
        params = VhallUtil.createRealParam(params);
        String result = VhallUtil.sendPost("http://api.yun.vhall.com/api/v1/channel/get-message-list", params);
        return JSON.parse(result);
    }
    
    @SuppressWarnings("unchecked")
	public static Object sendMessage(String type,String body,String channel_id) throws Exception {
        
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("type", type);
        if(IStringUtil.isNotBlank(body)) {
            params.put("body", body);
        }
        params.put("channel_id", channel_id);
        params = VhallUtil.createRealParam(params);
        String result = VhallUtil.sendPost("http://api.yun.vhall.com/api/v1/channel/sent", params);
        return JSON.parse(result);
        
    }

    public static void saveUserInfo(String accountId, String nickname, String headImg) {
        HashMap<String, String> params = new HashMap<>(2);
        params.put("third_party_user_id", accountId);
        params.put("nick_name", nickname);
        params.put("avatar", headImg);
        try {
            VhallUtil.sendPost("http://api.yun.vhall.com/api/v1/channel/save-user-info", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
