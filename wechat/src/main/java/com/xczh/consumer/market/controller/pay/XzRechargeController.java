package com.xczh.consumer.market.controller.pay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.online.api.service.RechargesService;

/**
 * 通用控制器 ClassName: CommonController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>
 * email: 15936216273@163.com <br>
 * Create Time: 2017年8月12日<br>
 */
@Controller
@RequestMapping("/xczh/recharge")
public class XzRechargeController {

    @Autowired
    private RechargesService rechargesService;

    /**
     * Description：获取充值列表
     *
     * @param req
     * @param res
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping(value = "rechargeList")
    @ResponseBody
    public ResponseObject rechargeList(HttpServletRequest req, HttpServletResponse res) throws Exception {
        return ResponseObject.newSuccessResponseObject(rechargesService.getRecharges());
    }
}
