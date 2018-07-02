package com.xczh.consumer.market.controller.user;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/xczh/user/message")
public class MessageController {

    @Autowired
    private ICommonMessageService commonMessageService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseObject list(@Account String accountId, @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        Page<Message> messages = commonMessageService.list(page, size, accountId);
        messages.getRecords().forEach(message -> {
                    message.setUrl(MultiUrlHelper.getUrl(message.getRouteType(),
                            MultiUrlHelper.URL_TYPE_APP, message.getDetailId(), message.getOuterLink()));
                    message.setTitle(StringUtils.isNotBlank(message.getTitle()) ? message.getTitle() : Message.SYSTEM_MESSAGE_TITLE);
                }
        );
        commonMessageService.updateReadStatus(null, accountId);
        return ResponseObject.newSuccessResponseObject(messages.getRecords());
    }

    @RequestMapping(value = "count", method = RequestMethod.GET)
    public ResponseObject count(@Account String accountId) {
        return ResponseObject.newSuccessResponseObject(commonMessageService.countUnReadCntByUserId(accountId));
    }
}
