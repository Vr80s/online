package com.xczh.consumer.market.controller.pay;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.interceptor.HeaderInterceptor;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.enums.OrderStatus;
import com.xczhihui.course.model.Order;
import com.xczhihui.course.service.IOrderService;

@Controller
@RequestMapping("/xczh/order")
public class MyOrderController {


    @Autowired
    private IOrderService orderService;

    @Value("${rate}")
    private int rate;

    /**
     * 生成订单  	订单来源，1：pc 2：h5 3:android 4 ios 5 线下 6 工作人员
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "save")
    @ResponseBody
    @Transactional
    public ResponseObject saveOnlineOrder(@Account String accountId,
                                          @RequestParam("courseId") Integer courseId,
                                          @RequestParam("orderFrom") Integer orderFrom ) throws Exception {
        Order order = orderService.createOrder(accountId, courseId, HeaderInterceptor.getClientTypeCode());
        Map returnMap = new HashMap();
        returnMap.put("orderNo", order.getOrderNo());
        returnMap.put("orderId", order.getId());
        returnMap.put("currentPrice", order.getPrice());
        return ResponseObject.newSuccessResponseObject(returnMap);
    }

    /**
     * 根据订单id获取信息
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getByOrderId")
    @ResponseBody
    public ResponseObject getOnlineOrderByOrderId(@RequestParam("orderId") String orderId) throws Exception {
        Order order = orderService.getOrderIncludeCourseInfoByOrderId(orderId);
        if (order != null) {
            order.setActualPay(order.getActualPay() * 10);
        }
        return ResponseObject.newSuccessResponseObject(order);
    }

    /**
     * 判断订单是否支付成功
     *
     * @param orderId
     * @return
     */
    @RequestMapping(value = "checkOrderPay", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject checkOrderPay(@RequestParam("orderId") String orderId) {
        try {
            Order order = orderService.getOrderById(orderId);
            if (order != null && order.getOrderStatus() == OrderStatus.PAID.getCode()) {
                return ResponseObject.newSuccessResponseObject(true);
            }
            return ResponseObject.newSuccessResponseObject(false);
        } catch (Exception e) {
            return ResponseObject.newSuccessResponseObject(false);
        }
    }

    /**
     * 查看此订单是否已经支付,并提示信息
     *
     * @param orderId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "orderIsExitCourseIsBuy")
    @ResponseBody
    public ResponseObject orderIsExitCourseIsBuy(
            String orderId) throws Exception {

        ResponseObject ro = new ResponseObject();

        // 支付状态 0:未支付 1:已支付 2:已关闭
        Order order = orderService.getOrderById(orderId);
        if (order != null && order.getOrderStatus() != null && order.getOrderStatus().equals(OrderStatus.PAID.getCode())) {
            ro.setSuccess(false);
            ro.setErrorMessage("已支付,请您查看已购课程");
            return ro;
        } else if (order != null && order.getOrderStatus() != null && order.getOrderStatus().equals(OrderStatus.CLOSED.getCode())) {
            ro.setSuccess(false);
            ro.setErrorMessage("已关闭,请您在PC端查看订单");
            return ro;
        }
        ro.setSuccess(true);
        ro.setErrorMessage("请继续支付");
        return ro;
    }
}
