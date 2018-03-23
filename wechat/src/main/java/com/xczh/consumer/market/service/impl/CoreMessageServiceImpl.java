package com.xczh.consumer.market.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xczh.consumer.market.controller.user.XzUserController;
import com.xczh.consumer.market.service.CoreMessageService;
import com.xczh.consumer.market.utils.MessageConstant;
import com.xczh.consumer.market.utils.MessageUtil;
import com.xczh.consumer.market.wxmessage.resp.Article;
import com.xczh.consumer.market.wxmessage.resp.NewsMessage;
import com.xczh.consumer.market.wxmessage.resp.TextMessage;

@Service
public class CoreMessageServiceImpl implements CoreMessageService {

	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(XzUserController.class); 
	
	@Override
	public String processRequest(HttpServletRequest request) {
		String respMessage = null;  
        try {  
            // xml请求解析  
            Map<String, String> requestMap = MessageUtil.parseXml(request);  
  
            // 发送方帐号（open_id）  
            String fromUserName = requestMap.get("FromUserName");  
            // 公众帐号  
            String toUserName = requestMap.get("ToUserName");  
            // 消息类型  
            String msgType = requestMap.get("MsgType");  
  
            TextMessage textMessage = new TextMessage();  
            textMessage.setToUserName(fromUserName);  
            textMessage.setFromUserName(toUserName);  
            textMessage.setCreateTime(new Date().getTime());  
            textMessage.setMsgType(MessageConstant.RESP_MESSAGE_TYPE_TEXT);  
            textMessage.setFuncFlag(0);  
            
            
            
            // 文本消息  
            if (msgType.equals(MessageConstant.REQ_MESSAGE_TYPE_TEXT)) {  
                // 接收用户发送的文本消息内容  
                String content = requestMap.get("Content");  
                
                NewsMessage newsMessage = new NewsMessage();  
                newsMessage.setToUserName(fromUserName);  
                newsMessage.setFromUserName(toUserName);  
                newsMessage.setCreateTime(new Date().getTime());  
                newsMessage.setMsgType(MessageConstant.RESP_MESSAGE_TYPE_NEWS);  
                newsMessage.setFuncFlag(0);  
                
                List<Article> articleList = new ArrayList<Article>();  
                
                if ("1".equals(content)) {  
                    textMessage.setContent("1是很好的");  
                    // 将文本消息对象转换成xml字符串  
                    respMessage = MessageUtil.textMessageToXml(textMessage);  
                }else if ("2".equals(content)) {  
                    textMessage.setContent("我不是2货");  
                    // 将文本消息对象转换成xml字符串  
                    respMessage = MessageUtil.textMessageToXml(textMessage);  
                }else if("3".equals(content)){
                	  Article article = new Article();  
                      article.setTitle("啦啦啦啦啦啦啦啦，我是卖报的小画家。");  
                      article.setDescription("啦啦啦啦啦啦啦啦，我是卖报的小画家。");  
                      article.setPicUrl("http://test-file.ipandatcm.com/18323230451/3654b4749a2b88f24ee6.jpg");  
                      article.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx48d230a99f1c20d9&redirect_uri=http://test-wx.ixincheng.com/xczh/wxpublic/publicToRecommended&response_type=code&scope=snsapi_userinfo&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect");  
                      articleList.add(article);  
                      // 设置图文消息个数  
                      newsMessage.setArticleCount(articleList.size());  
                      // 设置图文消息包含的图文集合  
                      newsMessage.setArticles(articleList);  
                      // 将图文消息对象转换成xml字符串  
                      respMessage = MessageUtil.newsMessageToXml(newsMessage);  
                }  
            }else if(msgType.equals(MessageConstant.EVENT_TYPE_SUBSCRIBE)){
            	
            	
            	
            	
            	
            }   
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return respMessage;  
	}

}
