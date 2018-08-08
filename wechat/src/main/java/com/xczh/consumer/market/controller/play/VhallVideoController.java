package com.xczh.consumer.market.controller.play;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.support.service.CacheService;
import com.xczhihui.common.util.WeihouInterfacesListUtil;
import com.xczhihui.common.util.bean.VhallMessageParamsVo;
import com.xczhihui.common.util.enums.VhallCustomMessageType;
import com.xczhihui.common.util.redis.key.RedisCacheKey;
import com.xczhihui.common.util.vhallyun.BaseService;
import com.xczhihui.common.util.vhallyun.MessageService;


/**
 * 直播控制器
 * ClassName: LiveController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2017年8月10日<br>
 */
@Controller
@RequestMapping("/xczh/vhall")
public class VhallVideoController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(VhallVideoController.class);

    
    @Autowired
    private CacheService cacheService;
    
    /**
     * Description：微吼签名认证得到微吼的视频播放权
     *
     * @param req
     * @param res
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    @RequestMapping("vhallJssdkVerify")
    @ResponseBody
    public ResponseObject getWeihouSign(@RequestParam("video") String video, 
            @Account OnlineUser account) throws Exception {
        String gvhallId = account.getVhallId();
        String email = account.getLoginName();
        if (email != null && email.indexOf("@") == -1) {
            email += "@163.com";
        }

        Date d = new Date();
        String start_time = d.getTime() + "";
        start_time = start_time.substring(0, start_time.length() - 3);

        LOGGER.info("微吼gvhallId:" + gvhallId + ",微吼eamil:" + email
                + ",加密的：start_time:" + start_time);

        Map<String, String> map = new TreeMap<String, String>();
        map.put("app_key", WeihouInterfacesListUtil.APP_KEY);  //微吼key
        map.put("signedat", start_time);        //时间戳，精确到秒
        map.put("email", email);                //email 自己写的
        map.put("roomid", video);                //视频id
        map.put("account", account.getId());        //用户帐号
        map.put("username", account.getName());     //用户名
        map.put("sign", WeihouInterfacesListUtil.getSign(map));
        return ResponseObject.newSuccessResponseObject(map);
    }
    
    
    /**
     * 获取消息列表
     * @param vmpv
     * @return
     * @throws Exception
     */
    @RequestMapping("vhallYunMessageList")
    @ResponseBody
    public ResponseObject getMessageList(
            VhallMessageParamsVo vmpv) throws Exception {

        return ResponseObject.newSuccessResponseObject(MessageService.getMessageList(vmpv));
    }
    
    /**
     * 微吼发送消息
     * @param type
     * @param body
     * @param channel_id
     * @return
     * @throws Exception
     */
    @RequestMapping("vhallYunSendMessage")
    @ResponseBody
    public ResponseObject sendMessage(@Account OnlineUser account,
            String body,String channel_id) throws Exception {
       
       JSONObject jsonObject =  (JSONObject) JSON.parse(body);
       if(jsonObject.get("type")!=null && Integer.parseInt(jsonObject.get("type").toString()) == VhallCustomMessageType.CHAT_MESSAGE.getCode()) {
           Boolean isShutup =  cacheService.sismenber(RedisCacheKey.VHALLYUN_BAN_KEY + channel_id, account.getUserId());
           if(isShutup) {
               return ResponseObject.newErrorResponseObject("你被禁言了");
           } 
       } 
       return ResponseObject.newSuccessResponseObject(MessageService.sendMessage("CustomBroadcast",body,channel_id));
    }

    @RequestMapping(value = "vhallYunToken", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject getAccessToken(@Account String accountId, @RequestParam String roomId, @RequestParam String channelId) throws Exception {
        return ResponseObject.newSuccessResponseObject(BaseService.createAccessToken4Live(accountId, roomId, channelId));
    }
}
