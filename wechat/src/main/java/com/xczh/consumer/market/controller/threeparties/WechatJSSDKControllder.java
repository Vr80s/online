package com.xczh.consumer.market.controller.threeparties;

import java.security.DigestException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.Sha1SignUtil;
import com.xczh.consumer.market.wxpay.util.CommonUtil;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;

/**
 * ClassName: ThirdPartyCertificationController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年2月2日<br>
 */
@Controller
@RequestMapping(value = "/xczh/wechatJssdk")
public class WechatJSSDKControllder {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(WechatJSSDKControllder.class);

    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WxMpConfigStorage wxMpConfigStorage;

    /**
     * Description：如果是微信公众号的话需要签名下微信提供的jsssdk，这样才能使用微信的内置的分享和其他功能
     *
     * @param req
     * @param res
     * @param params
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("certificationSign")
    @ResponseBody
    public ResponseObject certificationSign(HttpServletRequest req, HttpServletResponse res, Map<String, String> params) throws Exception {
        String url = req.getParameter("url");
        String jsapiTicket = wxMpService.getJsapiTicket();
        String nostr = CommonUtil.CreateNoncestr();
        Map<String, Object> map = new HashMap<>(6);
        map.put("noncestr", nostr);
        map.put("jsapi_ticket", jsapiTicket);
        map.put("timestamp", System.currentTimeMillis() / 1000 + "");
        map.put("url", url);
        try {
            //微信加密sh1 js
            String strSha1 = Sha1SignUtil.SHA1(map);

            map.put("sign", strSha1);
            map.put("appId", wxMpConfigStorage.getAppId());
        } catch (DigestException e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("获取初始化信息数据有误");
        }
        return ResponseObject.newSuccessResponseObject(map);
    }
}
