package com.xczhihui.bxg.online.web.controller;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.xczhihui.bxg.common.util.HttpUtil;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;

/**
 * 短链接转换
 * @author Haicheng Jiang
 *
 */
@RestController
@RequestMapping(value = "/short")
public class ShortUrlController {
	
	@Value("${online.web.url}")
	private String weburl;
	
    /**
     * 获取短链接
     * @param req
     * @param url
     * @return
     */
    @RequestMapping(value = "/url",method= RequestMethod.POST)
    public ResponseObject  url(HttpServletRequest req,String url) {
    	if (UserLoginUtil.getLoginUser(req) == null) {
    		return ResponseObject.newErrorResponseObject("请登录！");
		}
    	String msg = HttpUtil.sendGetRequest("https://api.t.sina.com.cn/short_url/shorten.json?source=305153524&url_long="+url);
    	Gson g = new GsonBuilder().create();
    	ArrayList<Map<String, Object>> array = g.fromJson(msg, ArrayList.class);
    	return ResponseObject.newSuccessResponseObject(array.get(0).get("url_short"));
    }
}
