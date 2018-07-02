package com.xczh.consumer.market.controller.pay;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.enums.BankCardType;
import com.xczhihui.medical.anchor.service.IUserBankService;
import com.xczhihui.medical.anchor.vo.UserBank;

/**
 * 资产控制器 ClassName: BankCardController.java <br>
 * Description:
 * Create by: name：wangyishuai <br>
 * email: 15210815880@163.com <br>
 * Create Time: 2018年1月16日<br>
 */
@Controller
@RequestMapping("/xczh/medical")
public class BankCardController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(BankCardController.class);
    @Autowired
    private IUserBankService userBankService;

    /**
     * 添加银行卡
     */
    @RequestMapping("addBankCard")
    @ResponseBody
    public ResponseObject addCourseApply(@Account String accountId, HttpServletResponse res,
                                         @RequestParam("acctName") String acctName,
                                         @RequestParam("acctPan") String acctPan,
                                         @RequestParam(required = false) String certId,
                                         @RequestParam("tel") String tel,
                                         @RequestParam(required = false) Integer code) throws Exception {
        /**
         * 数据验证
         */
        Integer devCode = userBankService.validateBankInfo(accountId, acctName, acctPan, certId, tel, code);
        if (devCode == 201) { //说明身份证号不一致
            return ResponseObject.newErrorResponseObject("您填写的身份信息与主播认证信息不一致，是否继续添加?", devCode);
        }
        userBankService.addUserBank(accountId, acctName, acctPan, certId, tel);
        return ResponseObject.newSuccessResponseObject("添加成功");
    }

    /**
     * 获取银行卡列表
     */
    @RequestMapping(value = "userBankList")
    @ResponseBody
    public ResponseObject selectUserBankbyUserId(@Account String accountId) throws Exception {
        List<UserBank> userBankList = userBankService.selectUserBankByUserId(accountId, false);
        return ResponseObject.newSuccessResponseObject(userBankList);
    }

    /**
     * 获取银行列表
     */
    @RequestMapping(value = "getBankCardList")
    @ResponseBody
    public ResponseObject getBankCardList(HttpServletRequest req) throws Exception {

        List<Map> getBankCardList = BankCardType.getBankCardList();
        return ResponseObject.newSuccessResponseObject(getBankCardList);
    }

    /**
     * 删除银行卡
     */
    @RequestMapping(value = "deleteBankCard")
    @ResponseBody
    public ResponseObject deleteBankCard(@Account String accountId,
                                         @RequestParam("id") Integer id) throws Exception {
        userBankService.deleteBankCard(accountId, id);
        return ResponseObject.newSuccessResponseObject("删除成功");
    }

    /**
     * 设置默认银行卡
     */
    @RequestMapping(value = "updateDefault")
    @ResponseBody
    public ResponseObject updateDefault(@Account String accountId,
                                        @RequestParam("id") Integer id) throws Exception {
        userBankService.updateDefault(accountId, id);
        return ResponseObject.newSuccessResponseObject("设置成功");
    }

    /**
     * 获取默认银行卡
     */
    @RequestMapping(value = "getDefault")
    @ResponseBody
    public ResponseObject getDefault(@Account String accountId) throws Exception {
        UserBank ub = userBankService.getDefault(accountId);
        return ResponseObject.newSuccessResponseObject(ub);
    }
}
