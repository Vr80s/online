package com.xczh.consumer.market.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.course.service.ICommonMessageService;

/**
 * @author hejiwei
 */
@RequestMapping("app")
@RestController
public class AppController {

    @Autowired
    private ICommonMessageService commonMessageService;

    @RequestMapping(value = "xg/bind", method = RequestMethod.POST)
    public ResponseObject bindXgAccountId(@RequestParam String xgAccountId, @Account String accountId) {
        commonMessageService.bindXgAccountId(accountId, xgAccountId);
        return ResponseObject.newSuccessResponseObject(null);
    }

    @RequestMapping(value = "xg/unBind", method = RequestMethod.POST)
    public ResponseObject unBindXgAccountId(@RequestParam String xgAccountId, @Account String accountId) {
        commonMessageService.unBindXgAccountId(accountId, xgAccountId);
        return ResponseObject.newSuccessResponseObject(null);
    }
}
