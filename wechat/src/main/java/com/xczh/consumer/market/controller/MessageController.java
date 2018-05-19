package com.xczh.consumer.market.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.course.consts.MultiUrlHelper;
import com.xczhihui.course.model.Message;
import com.xczhihui.course.service.ICommonMessageService;

/**
 * @author liutao
 * @create 2017-09-07 20:08
 **/
@Controller
@RequestMapping("/bxg/message")
public class MessageController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(LearningCenterController.class);
    @Autowired
    private ICommonMessageService commonMessageService;
    @Autowired
    private AppBrowserService appBrowserService;

    @ResponseBody
    @RequestMapping
    public ResponseObject list(@RequestParam(name = "page", defaultValue = "1") int page, HttpServletRequest request) {
        OnlineUser user = appBrowserService.getOnlineUserByReq(request);
        if (user == null) {
            return ResponseObject.newErrorResponseObject("登录失效");
        }
        List<Message> messages = commonMessageService.list(page, user.getId());
        messages.forEach(message -> {
            String routeType = message.getRouteType();
            if (routeType != null) {
                message.setUrl(MultiUrlHelper.getUrl(routeType, MultiUrlHelper.URL_TYPE_APP));
            }
        });
        return ResponseObject.newSuccessResponseObject(messages);
    }
}
