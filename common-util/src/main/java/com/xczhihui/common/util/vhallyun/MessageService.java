package com.xczhihui.common.util.vhallyun;

import java.util.HashMap;
import java.util.Iterator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
            params = VhallUtil.createRealParam(params);
            VhallUtil.sendPost("http://api.yun.vhall.com/api/v1/channel/save-user-info", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public static void main(String[] args) throws Exception {
		
    	VhallMessageParamsVo vmpv = new VhallMessageParamsVo();
    	//getMessageList
    	vmpv.setChannel_id("ch_c07e626f");
    	vmpv.setType("2");
    	vmpv.setLimit("1");
    	vmpv.setStart_time("2017/01/01");
    	
    	JSONObject  obj = (JSONObject) getMessageList(vmpv);
    	System.out.println(obj);
    	JSONArray  arr = (JSONArray) obj.get("data");
    	
    	for (int i = 0; i < arr.size(); i++) {
    		JSONObject lal = (JSONObject)arr.get(i);
    		String str = (String) JSON.parse(lal.get("data").toString());
			System.out.println(str);
			lal.put("data", str);
		}
    	System.out.println(obj);
    
    }
    
}
