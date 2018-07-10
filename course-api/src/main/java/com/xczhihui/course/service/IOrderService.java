package com.xczhihui.course.service;

import com.baomidou.mybatisplus.service.IService;
import com.xczhihui.course.model.Order;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yuxin
 * @since 2018-04-13
 */
public interface IOrderService extends IService<Order> {

    Order createOrder(String userId, int courseId, Integer orderFrom);

    Order getOrderByOrderId(String orderId);

    Order getOrderNo4PayByOrderNo(String orderNo);

    Order getOrderNo4PayByOrderId(String orderId);

    /**
     * 通过订单id 获取订单数据
     *
     * @param orderId orderId
     * @return
     */
    Order getOrderById(String orderId);

    Order getOrderByOrderNo(String out_trade_no);

    /**
     * 通过订单id得到订单详情包含课程信息
     *
     * @param orderId
     * @return
     */
    Order getOrderIncludeCourseInfoByOrderId(String orderId);

    /**
     * 通过订单状态和订单id  查找订单信息
     * @param orderId
     * @param status
     * @return
     */
    Order getOrderNo4PayByOrderId(String orderId, Integer status);
}
