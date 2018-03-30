package com.xczh.consumer.market.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xczh.consumer.market.controller.user.XzUserController;
import com.xczh.consumer.market.service.CoreMessageService;
import com.xczh.consumer.market.utils.MessageConstant;
import com.xczh.consumer.market.utils.MessageUtil;
import com.xczh.consumer.market.wxmessage.resp.Article;
import com.xczh.consumer.market.wxmessage.resp.NewsMessage;
import com.xczh.consumer.market.wxmessage.resp.TextMessage;
import com.xczh.consumer.market.wxpay.util.SingleAccessToken;

@Service
public class CoreMessageServiceImpl implements CoreMessageService {

	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(XzUserController.class); 
	
	@Value("${returnOpenidUri}")
	private String returnOpenidUri;
	
	
	//https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
	
	
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
        	
            LOGGER.info("requestMap"+requestMap.toString());
            
            //文本消息
            TextMessage textMessage = new TextMessage();  
            textMessage.setToUserName(fromUserName);  
            textMessage.setFromUserName(toUserName);  
            textMessage.setCreateTime(new Date().getTime());  
            textMessage.setMsgType(MessageConstant.RESP_MESSAGE_TYPE_TEXT);  
            textMessage.setFuncFlag(0);  
            
      	    //图文消息
      	    List<Article> articleList = new ArrayList<Article>();  
      	    NewsMessage newsMessage = new NewsMessage();  
            newsMessage.setToUserName(fromUserName);  
            newsMessage.setFromUserName(toUserName);  
            newsMessage.setCreateTime(new Date().getTime());  
            newsMessage.setFuncFlag(0);  
            
            
            // 文本消息  
            if (msgType.equals(MessageConstant.REQ_MESSAGE_TYPE_TEXT)) {  
            	
                // 接收用户发送的文本消息内容  
//                String content = requestMap.get("Content");  
//                textMessage.setContent("欢迎来到熊猫中医");  
//                // 将文本消息对象转换成xml字符串  
//                respMessage = MessageUtil.textMessageToXml(textMessage);  
//                if ("1".equals(content)) {  
//                    textMessage.setContent("欢迎来到熊猫中医！");  
//                    // 将文本消息对象转换成xml字符串  
//                    respMessage = MessageUtil.textMessageToXml(textMessage);  
//                }else if ("2".equals(content)) {  
//                    textMessage.setContent("我不是2货");  
//                    // 将文本消息对象转换成xml字符串  
//                    respMessage = MessageUtil.textMessageToXml(textMessage);  
//                }else if("3".equals(content)){
//                	  Article article = new Article();  
//                      article.setTitle("啦啦啦啦啦啦啦啦，我是卖报的小画家。");  
//                      article.setDescription("啦啦啦啦啦啦啦啦，我是卖报的小画家。");  
//                      article.setPicUrl("http://test-file.ipandatcm.com/18323230451/3654b4749a2b88f24ee6.jpg");  
//                      article.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx48d230a99f1c20d9&redirect_uri=http://test-wx.ixincheng.com/xczh/wxpublic/publicToRecommended&response_type=code&scope=snsapi_userinfo&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect");  
//                      articleList.add(article);  
//                   
//                      // 设置图文消息个数  
//                      newsMessage.setArticleCount(articleList.size());  
//                      // 设置图文消息包含的图文集合  
//                      newsMessage.setArticles(articleList);  
//                      // 将图文消息对象转换成xml字符串  
//                      respMessage = MessageUtil.newsMessageToXml(newsMessage);  
//                }  
            	
            }else if(msgType.equals(MessageConstant.REQ_MESSAGE_TYPE_EVENT)){ //请求消息类型：事件推送
            	
        	  if(scan.equals(MessageConstant.EVENT_TYPE_SUBSCRIBE)){  // 关注公众号事件
        		  
        		  newsMessage.setMsgType(MessageConstant.RESP_MESSAGE_TYPE_NEWS);  
        		  LOGGER.info("有人关注了~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        		  
                  Article article = new Article();  
                  article.setTitle("欢迎来到熊猫国医学堂");  
                  article.setDescription("");  
                  article.setPicUrl("http://test-file.ipandatcm.com/18323230451/3654b4749a2b88f24ee6.jpg");  
                  article.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx48d230a99f1c20d9&redirect_uri="+returnOpenidUri+"/xczh/wxpublic/publicToRecommended&response_type=code&scope=snsapi_userinfo&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect");  
                  articleList.add(article);
                  // 设置图文消息个数  
                  newsMessage.setArticleCount(articleList.size());  
                  // 设置图文消息包含的图文集合  
                  newsMessage.setArticles(articleList);  
                  // 将图文消息对象转换成xml字符串  
                  respMessage = MessageUtil.newsMessageToXml(newsMessage); 
        		  newsMessage.setMedia_id("6y0EBrCsG4Si29EjR7_uAPKHf5fHnte_6__89Y0IiyA");
        		  
        		  /*
        		   * 保存用户微信信息
        		   */
        		  String token =SingleAccessToken.getInstance().getAccessToken().getToken();
        	      String url = MessageConstant.UNIONID_USERINFO.replace("APPSECRET", token).replace("OPENID", fromUserName);
        	      //保存用户信息
        	      
        		  respMessage = MessageUtil.newsMessageToXml(newsMessage); 

        	  }else if(scan.equals(MessageConstant.EVENT_TYPE_UNSUBSCRIBE)){  //取消公众号事件
              	
             	   LOGGER.info("有人取消关注了~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
              
        	  }else if(scan.equals(MessageConstant.EVENT_TYPE_SCAN)){
             	
             	  LOGGER.info("有人了扫二维码了~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
             	 
                  newsMessage.setMsgType(MessageConstant.RESP_MESSAGE_TYPE_NEWS);   
             	 
//               Article article = new Article();  
//               article.setTitle("扫码关注---》弘扬中医药文化，助力中医药产业。【熊猫中医】与您，一路同行。");  
//               article.setDescription("感谢关注【熊猫国医学堂】点击【国医学堂】进入【熊猫中医课堂】，即可观看现有中医课程。点击【个人中心】 可以查看自己的账户情况。【熊猫中医在线云课堂】，打破时间空间的限制，学习最适合你的中医。");  
//               article.setPicUrl("http://test-file.ipandatcm.com/18323230451/3654b4749a2b88f24ee6.jpg");  
//               article.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx48d230a99f1c20d9&redirect_uri="+returnOpenidUri+"/xczh/wxpublic/publicToRecommended&response_type=code&scope=snsapi_userinfo&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect");  
//              
//               Article article1 = new Article();  
//               article1.setTitle("啦啦啦啦啦啦，我是卖报的小画家！");  
//               article1.setDescription("啦啦啦啦啦啦，我是卖报的小画家！");  
//               article1.setPicUrl("http://test-file.ipandatcm.com/18323230451/3654b4749a2b88f24ee6.jpg");  
//               article1.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx48d230a99f1c20d9&redirect_uri="+returnOpenidUri+"/xczh/wxpublic/publicToRecommended&response_type=code&scope=snsapi_userinfo&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect");  
//              
//               articleList.add(article); 
//               articleList.add(article1); 
//               
//               // 设置图文消息个数  
//               newsMessage.setArticleCount(articleList.size());  
//               // 设置图文消息包含的图文集合  
//               newsMessage.setArticles(articleList);  
//               // 将图文消息对象转换成xml字符串  
//               respMessage = MessageUtil.newsMessageToXml(newsMessage); 
             	 
             	 
              }
            }
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
        return respMessage;  
	}
	
	
	public static void main(String[] args) {
		
		
 	    NewsMessage newsMessage = new NewsMessage();  
        newsMessage.setToUserName("1");  
        newsMessage.setFromUserName("2");  
        newsMessage.setCreateTime(new Date().getTime());  
        newsMessage.setFuncFlag(0);  
        newsMessage.setMsgType(MessageConstant.RESP_MESSAGE_TYPE_NEWS);   
        
//		List<Article> articleList = new ArrayList<Article>();  
//        Article article = new Article();  
//        article.setTitle("弘扬中医药文化，助力中医药产业。【熊猫中医】与您，一路同行。");  
//        article.setDescription("感谢关注【熊猫国医学堂】点击【国医学堂】进入【熊猫中医课堂】，即可观看现有中医课程。点击【个人中心】 可以查看自己的账户情况。【熊猫中医在线云课堂】，打破时间空间的限制，学习最适合你的中医。");  
//        article.setPicUrl("http://test-file.ipandatcm.com/18323230451/3654b4749a2b88f24ee6.jpg");  
//        article.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx48d230a99f1c20d9&redirect_uri=/xczh/wxpublic/publicToRecommended&response_type=code&scope=snsapi_userinfo&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect");  
//       
//        articleList.add(article); 
//        // 设置图文消息个数  
//        newsMessage.setArticleCount(articleList.size());  
//        // 设置图文消息包含的图文集合  
//        newsMessage.setArticles(articleList);  

		newsMessage.setMedia_id("6y0EBrCsG4Si29EjR7_uAPKHf5fHnte_6__89Y0IiyA");
		
        
        // 将图文消息对象转换成xml字符串  
        String respMessage = MessageUtil.newsMessageToXml(newsMessage); 
		
        
        System.out.println("respMessage:"+respMessage);
	}
	

}
