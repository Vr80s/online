package com.xczh.consumer.market.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.auth.Account;
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

    @ResponseBody
    @RequestMapping
    public ResponseObject list(@Account String accountId, @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        Page<Message> messages = commonMessageService.list(page, size, accountId);
        messages.getRecords().forEach(message -> {
                    message.setUrl(MultiUrlHelper.getUrl(message.getRouteType(), MultiUrlHelper.URL_TYPE_APP, message.getDetailId()));
                    message.setTitle(Message.SYSTEM_MESSAGE_TITLE);
                }
        );
        commonMessageService.updateReadStatus(null, accountId);
        return ResponseObject.newSuccessResponseObject(messages.getRecords());
    }

    @ResponseBody
    @RequestMapping(value = "count", method = RequestMethod.GET)
    public ResponseObject count(@Account String accountId) {
        return ResponseObject.newSuccessResponseObject(commonMessageService.countUnReadCntByUserId(accountId));
    }
}
