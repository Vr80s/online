package com.xczh.consumer.market.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.course.consts.MultiUrlHelper;
import com.xczhihui.course.model.Message;
import com.xczhihui.course.service.ICommonMessageService;

/**
 * @author hejiwei
 */
@RestController
@RequestMapping("/xczh/message")
public class MessageController {

    @Autowired
    private ICommonMessageService commonMessageService;
    @Autowired
    private AppBrowserService appBrowserService;

    @ResponseBody
    @RequestMapping
    public ResponseObject list(@RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
                               HttpServletRequest request) {
        OnlineUser user = appBrowserService.getOnlineUserByReq(request);
        if (user == null) {
            return ResponseObject.newErrorResponseObject("登录失效");
        }
        String userId = user.getId();
        Page<Message> messages = commonMessageService.list(page, size, userId);
        messages.getRecords().forEach(message -> {
                    message.setUrl(MultiUrlHelper.getUrl(message.getRouteType(), MultiUrlHelper.URL_TYPE_APP, message.getDetailId()));
                    message.setTitle(Message.SYSTEM_MESSAGE_TITLE);
                }
        );
        commonMessageService.updateReadStatus(null, userId);
        return ResponseObject.newSuccessResponseObject(messages);
    }

    @ResponseBody
    @RequestMapping(value = "count", method = RequestMethod.GET)
    public ResponseObject count(HttpServletRequest request) {
        OnlineUser user = appBrowserService.getOnlineUserByReq(request);
        if (user == null) {
            return ResponseObject.newErrorResponseObject("登录失效");
        }
        String userId = user.getId();
        return ResponseObject.newSuccessResponseObject(commonMessageService.countUnReadCntByUserId(userId));
    }
}
