package com.xczh.consumer.market.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xczh.consumer.market.bean.WxcpClientUserWxMapping;
import com.xczh.consumer.market.controller.user.XzUserController;
import com.xczh.consumer.market.service.CoreMessageService;
import com.xczh.consumer.market.service.WxcpClientUserWxMappingService;
import com.xczh.consumer.market.utils.MessageConstant;
import com.xczh.consumer.market.utils.MessageUtil;
import com.xczh.consumer.market.wxmessage.resp.Article;
import com.xczh.consumer.market.wxmessage.resp.NewsMessage;
import com.xczh.consumer.market.wxpay.util.CommonUtil;

import me.chanjar.weixin.mp.api.WxMpService;
import net.sf.json.JSONObject;

@Service
public class CoreMessageServiceImpl implements CoreMessageService {


    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(XzUserController.class);
    private static String PIC_URL = "https://file.ipandatcm.com/18404195804/daec4a7882a13c1e-jpg";
    private static String TITLE = "欢迎来到熊猫中医!";

    @Value("${returnOpenidUri}")
    private String returnOpenidUri;

    @Value("${wechatpay.h5.appid}")
    private String gzh_appid;
    @Autowired
    private WxMpService wxMpService;

    @Value("${webdomain}")
    private String webdomain;

    @Autowired
    private WxcpClientUserWxMappingService wxcpClientUserWxMappingService;

    @Override
    public String processRequest(HttpServletRequest request) {
        String respMessage = "";
        try {
            // xml请求解析  
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            // 发送方帐号（open_id）  
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号  
            String toUserName = requestMap.get("ToUserName");
            // 消息类型  
            String msgType = requestMap.get("MsgType");
            //事件推送类型
            String scan = requestMap.get("Event");
            LOGGER.info("消息来了~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            LOGGER.info("requestMap" + requestMap.toString());
            //图文消息
            List<Article> articleList = new ArrayList<Article>();
            NewsMessage newsMessage = new NewsMessage();
            newsMessage.setToUserName(fromUserName);
            newsMessage.setFromUserName(toUserName);
            newsMessage.setCreateTime(System.currentTimeMillis());
            newsMessage.setFuncFlag(0);
            // 文本消息  
            if (msgType.equals(MessageConstant.REQ_MESSAGE_TYPE_TEXT)) {
            	
                LOGGER.info("有人给我们发送消息了~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            
              //请求消息类型：事件推送
            } else if (msgType.equals(MessageConstant.REQ_MESSAGE_TYPE_EVENT)) { 

            	// 关注公众号事件 或者 扫码关注
                if (scan.equals(MessageConstant.EVENT_TYPE_SUBSCRIBE)
                		|| scan.equals(MessageConstant.EVENT_TYPE_SCAN )) {  
                	
                    LOGGER.info("有人关注了~~~~~~~~~~~~~~~~~~~~~~~~~~~~关注类型："+scan);
                   /*
                    * 保存用户微信信息
        		    */
                    JSONObject jsonObject = serviceToken(fromUserName);
                    Integer qr_scene = (Integer)jsonObject.get("qr_scene");
                    String qr_scene_str = (String) jsonObject.get("qr_scene_str");	
                    
                    LOGGER.info("获取的微信数据~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + jsonObject.toString());
                    String openid_ = (String) jsonObject.get("openid");
                    WxcpClientUserWxMapping m = wxcpClientUserWxMappingService.getWxcpClientUserWxMappingByOpenId(openid_);
                    LOGGER.info("~~~is exit " + m);
                    if (null == m) {

                        WxcpClientUserWxMapping wxcpClientUserWxMapping = 
                        		new WxcpClientUserWxMapping(jsonObject);
                        
                        wxcpClientUserWxMappingService.insert(wxcpClientUserWxMapping);
                        
                    } else if (m != null && (qr_scene != null || qr_scene_str != null)) {

                    	m.bulidUpdate(jsonObject);
                        wxcpClientUserWxMappingService.update(m);
                    }

                    newsMessage.setMsgType(MessageConstant.RESP_MESSAGE_TYPE_NEWS);
                    Article article = new Article();
                    article.setTitle(TITLE);
                    article.setDescription("");
                    article.setPicUrl(PIC_URL);
                    article.setUrl(returnOpenidUri);
                    articleList.add(article);
                    
                    // 设置图文消息个数
                    newsMessage.setArticleCount(articleList.size());
                    // 设置图文消息包含的图文集合
                    newsMessage.setArticles(articleList);
                    // 将图文消息对象转换成xml字符串
                    respMessage = MessageUtil.newsMessageToXml(newsMessage);


                //取消公众号事件    
                } else if (scan.equals(MessageConstant.EVENT_TYPE_UNSUBSCRIBE)) {  

                    LOGGER.info("有人取消关注了~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    JSONObject jsonObject = serviceToken(fromUserName);
                    WxcpClientUserWxMapping m = wxcpClientUserWxMappingService.getWxcpClientUserWxMappingByOpenId(fromUserName);

                    if (null != m) {
                        String subscribe = String.valueOf(jsonObject.get("subscribe"));
                        m.setSubscribe(subscribe);
                        m.setLast_update_time(new Date());
                        wxcpClientUserWxMappingService.update(m);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respMessage;
    }

    private JSONObject serviceToken(String fromUserName) throws Exception {
        String userInfo = CommonUtil.getUserManagerGetInfo(wxMpService.getAccessToken(), fromUserName);
        return JSONObject.fromObject(userInfo);
    }
}
