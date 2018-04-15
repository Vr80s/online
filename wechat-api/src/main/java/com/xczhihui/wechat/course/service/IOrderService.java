package com.xczhihui.wechat.course.service;

import com.baomidou.mybatisplus.service.IService;
import com.xczhihui.wechat.course.model.Order;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuxin
 * @since 2018-04-13
 */
public interface IOrderService extends IService<Order> {

    public Order createOrder(String userId, int courseId, Integer orderFrom);


}
