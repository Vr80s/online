package com.xczh.consumer.market.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.bean.OnlineOrder;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.OnlineOrderService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.utils.ResponseObject;

@Controller
@RequestMapping("/bxg/order")
public class OnlineOrderController {
    @Autowired
    private OnlineOrderService onlineOrderService;
    @Autowired
    private OnlineUserService onlineUserService;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(OnlineOrderController.class);

    /**
     * 获取订单列表
     *
     * @param req
     * @param res
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "list")
    @ResponseBody
    public ResponseObject getOnlineOrderList(HttpServletRequest req,
                                             HttpServletResponse res, Map<String, String> params) throws Exception {

        int status = -1;   //支付状态 0:未支付 1:已支付 2:已关闭
        if (null != req.getParameter("status")) {
            status = Integer.valueOf(req.getParameter("status"));
        }
        int pageNumber = 0;
        if (null != req.getParameter("pageNumber")) {
            pageNumber = Integer.valueOf(req.getParameter("pageNumber"));
        }
        int pageSize = 10;
        if (null != req.getParameter("pageSize")) {
            pageSize = Integer.valueOf(req.getParameter("pageSize"));
        }
        String userId = req.getParameter("userId");
        if (null == userId) {
            return ResponseObject.newErrorResponseObject("参数异常");
        }
        List<OnlineOrder> lists = onlineOrderService.getOnlineOrderList(status, userId, pageNumber, pageSize);
        OnlineUser user = onlineUserService.findUserById(userId);
        /**判断是否是合伙人，如果是合伙人将状态设置为10，可以去推荐，如果不是合伙人，默认为1，只能学习，不能推荐 **/
        if (null != user && StringUtils.isNotBlank(user.getShareCode())) {
            for (OnlineOrder onlineOrder : lists) {
                if (onlineOrder.getOrderStatus() == 1) {
                    onlineOrder.setOrderStatus(10);
                }
            }
        }
        return ResponseObject.newSuccessResponseObject(lists);
    }
}
