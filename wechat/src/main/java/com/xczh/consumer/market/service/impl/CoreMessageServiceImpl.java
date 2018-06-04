package com.xczh.consumer.market.service.impl;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xczh.consumer.market.bean.WxcpClientUserWxMapping;
import com.xczh.consumer.market.controller.user.XzUserController;
import com.xczh.consumer.market.service.CoreMessageService;
import com.xczh.consumer.market.service.WxcpClientUserWxMappingService;
import com.xczh.consumer.market.utils.DateUtil;
import com.xczh.consumer.market.utils.MessageConstant;
import com.xczh.consumer.market.utils.MessageUtil;
import com.xczh.consumer.market.wxmessage.resp.Article;
import com.xczh.consumer.market.wxmessage.resp.NewsMessage;
import com.xczh.consumer.market.wxmessage.resp.TextMessage;
import com.xczh.consumer.market.wxpay.consts.WxPayConst;
import com.xczh.consumer.market.wxpay.util.CommonUtil;
import com.xczhihui.common.util.SLEmojiFilter;

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

            //文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(System.currentTimeMillis());
            textMessage.setMsgType(MessageConstant.RESP_MESSAGE_TYPE_TEXT);
            textMessage.setFuncFlag(0);

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
            } else if (msgType.equals(MessageConstant.REQ_MESSAGE_TYPE_EVENT)) { //请求消息类型：事件推送


                if (scan.equals(MessageConstant.EVENT_TYPE_SUBSCRIBE)) {  // 关注公众号事件
                    LOGGER.info("有人关注了~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                   /*
                    * 保存用户微信信息
        		    */
                    JSONObject jsonObject = serviceToken(fromUserName);

                    LOGGER.info("jsonObject~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + jsonObject.toString());

                    String openid_ = (String) jsonObject.get("openid");
                    String nickname_ = (String) jsonObject.get("nickname");
                    //nickname需要过滤啦
                    nickname_ = SLEmojiFilter.filterEmoji(nickname_);

                    String subscribe = String.valueOf(jsonObject.get("subscribe"));
                    String sex_ = String.valueOf(jsonObject.get("sex"));
                    String language_ = (String) jsonObject.get("language");
                    String city_ = (String) jsonObject.get("city");
                    String province_ = (String) jsonObject.get("province");
                    String country_ = (String) jsonObject.get("country");
                    String headimgurl_ = (String) jsonObject.get("headimgurl");

                    Integer subscribe_time = (Integer) jsonObject.get("subscribe_time");
                    String unionid_ = (String) jsonObject.get("unionid");

                    String remark = (String) jsonObject.get("remark");
                    Integer groupid = (Integer) jsonObject.get("groupid");

                    String subscribe_scene = (String) jsonObject.get("subscribe_scene");
                    Integer qr_scene = (Integer) jsonObject.get("qr_scene");
                    String qr_scene_str = (String) jsonObject.get("qr_scene_str");

                    WxcpClientUserWxMapping m = wxcpClientUserWxMappingService.getWxcpClientUserWxMappingByOpenId(openid_);

                    LOGGER.info("WxcpClientUserWxMapping~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + m);
                    if (null == m) {

                        String public_id = WxPayConst.gzh_appid;
                        String public_name = WxPayConst.appid4name;

                        WxcpClientUserWxMapping wxcpClientUserWxMapping = new WxcpClientUserWxMapping();
                        wxcpClientUserWxMapping.setWx_id(UUID.randomUUID().toString().replace("-", ""));
                        wxcpClientUserWxMapping.setWx_public_id(public_id);
                        wxcpClientUserWxMapping.setWx_public_name(public_name);
                        wxcpClientUserWxMapping.setOpenid(openid_);
                        wxcpClientUserWxMapping.setNickname(nickname_);
                        wxcpClientUserWxMapping.setSex(sex_);
                        wxcpClientUserWxMapping.setLanguage(language_);
                        wxcpClientUserWxMapping.setCity(city_);
                        wxcpClientUserWxMapping.setProvince(province_);
                        wxcpClientUserWxMapping.setCountry(country_);

                        if (!StringUtils.isNotBlank(headimgurl_)) {
                            headimgurl_ = webdomain + "/web/images/defaultHead/18.png";
                        }

                        wxcpClientUserWxMapping.setHeadimgurl(headimgurl_);
                        wxcpClientUserWxMapping.setProvince(province_);
                        wxcpClientUserWxMapping.setUnionid(unionid_);
                        wxcpClientUserWxMapping.setSubscribe(subscribe);
                        wxcpClientUserWxMapping.setSubscribe_time(DateUtil.parseDate(subscribe_time + "", DateUtil.FORMAT_CHINA_DAY_TIME));
                        wxcpClientUserWxMapping.setRemark(remark);
                        wxcpClientUserWxMapping.setGroupid(groupid + "");
                        wxcpClientUserWxMapping.setSubscribe_scene(subscribe_scene);
                        wxcpClientUserWxMapping.setQr_scene(qr_scene + "");
                        wxcpClientUserWxMapping.setQr_scene_str(qr_scene_str);
                        wxcpClientUserWxMappingService.insert(wxcpClientUserWxMapping);
                    } else if (m != null && (qr_scene != null || qr_scene_str != null)) {

                        m.setGroupid(groupid + "");
                        m.setSubscribe(subscribe);
                        m.setSubscribe_scene(subscribe_scene);
                        m.setQr_scene(qr_scene + "");
                        m.setQr_scene_str(qr_scene_str);
                        m.setLast_update_time(new Date());

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


                } else if (scan.equals(MessageConstant.EVENT_TYPE_UNSUBSCRIBE)) {  //取消公众号事件

                    LOGGER.info("有人取消关注了~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    JSONObject jsonObject = serviceToken(fromUserName);
                    WxcpClientUserWxMapping m = wxcpClientUserWxMappingService.getWxcpClientUserWxMappingByOpenId(fromUserName);

                    if (null != m) {
                        String subscribe = String.valueOf(jsonObject.get("subscribe"));
                        m.setSubscribe(subscribe);
                        m.setLast_update_time(new Date());
                        wxcpClientUserWxMappingService.update(m);
                    }

                } else if (scan.equals(MessageConstant.EVENT_TYPE_SCAN)) {

                    LOGGER.info("有人了扫二维码了~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    newsMessage.setMsgType(MessageConstant.RESP_MESSAGE_TYPE_NEWS);
                    JSONObject jsonObject = serviceToken(fromUserName);
                    LOGGER.info("jsonObject~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + jsonObject.toString());
                    String openid_ = (String) jsonObject.get("openid");
                    String nickname_ = (String) jsonObject.get("nickname");
                    nickname_ = SLEmojiFilter.filterEmoji(nickname_); //nickname需要过滤啦

                    String subscribe = String.valueOf(jsonObject.get("subscribe"));
                    String sex_ = String.valueOf(jsonObject.get("sex"));
                    String language_ = (String) jsonObject.get("language");
                    String city_ = (String) jsonObject.get("city");
                    String province_ = (String) jsonObject.get("province");
                    String country_ = (String) jsonObject.get("country");
                    String headimgurl_ = (String) jsonObject.get("headimgurl");

                    Integer subscribe_time = (Integer) jsonObject.get("subscribe_time");
                    String unionid_ = (String) jsonObject.get("unionid");

                    String remark = (String) jsonObject.get("remark");
                    Integer groupid = (Integer) jsonObject.get("groupid");
                    //String tagid_list = (String)jsonObject.get("tagid_list");

                    String subscribe_scene = (String) jsonObject.get("subscribe_scene");
                    Integer qr_scene = (Integer) jsonObject.get("qr_scene");
                    String qr_scene_str = (String) jsonObject.get("qr_scene_str");

                    WxcpClientUserWxMapping m = wxcpClientUserWxMappingService.getWxcpClientUserWxMappingByOpenId(openid_);

                    String public_id = WxPayConst.gzh_appid;
                    String public_name = WxPayConst.appid4name;

                    LOGGER.info("WxcpClientUserWxMapping~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + m);

                    if (null == m) {  //

                        WxcpClientUserWxMapping wxcpClientUserWxMapping = new WxcpClientUserWxMapping();
                        wxcpClientUserWxMapping.setWx_id(UUID.randomUUID().toString().replace("-", ""));
                        wxcpClientUserWxMapping.setWx_public_id(public_id);
                        wxcpClientUserWxMapping.setWx_public_name(public_name);
                        wxcpClientUserWxMapping.setOpenid(openid_);
                        wxcpClientUserWxMapping.setNickname(nickname_);
                        wxcpClientUserWxMapping.setSex(sex_);
                        wxcpClientUserWxMapping.setLanguage(language_);
                        wxcpClientUserWxMapping.setCity(city_);
                        wxcpClientUserWxMapping.setProvince(province_);
                        wxcpClientUserWxMapping.setCountry(country_);
                        wxcpClientUserWxMapping.setHeadimgurl(headimgurl_);
                        wxcpClientUserWxMapping.setProvince(province_);
                        wxcpClientUserWxMapping.setUnionid(unionid_);
                        wxcpClientUserWxMapping.setSubscribe(subscribe);
                        wxcpClientUserWxMapping.setSubscribe_time(DateUtil.parseDate(subscribe_time + "", DateUtil.FORMAT_CHINA_DAY_TIME));
                        wxcpClientUserWxMapping.setRemark(remark);
                        wxcpClientUserWxMapping.setGroupid(groupid + "");
                        //wxcpClientUserWxMapping.setTagid_list(tagid_list);
                        wxcpClientUserWxMapping.setSubscribe_scene(subscribe_scene);
                        wxcpClientUserWxMapping.setQr_scene(qr_scene + "");
                        wxcpClientUserWxMapping.setQr_scene_str(qr_scene_str);
                        wxcpClientUserWxMapping.setCreate_time(new Date());
                        wxcpClientUserWxMapping.setLast_update_time(new Date());

                        wxcpClientUserWxMappingService.insert(wxcpClientUserWxMapping);

                    } else if (m != null && (qr_scene != null || qr_scene_str != null)) {

                        m.setGroupid(groupid + "");
                        m.setSubscribe_scene(subscribe_scene);
                        m.setQr_scene(qr_scene + "");
                        m.setQr_scene_str(qr_scene_str);
                        m.setLast_update_time(new Date());

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
