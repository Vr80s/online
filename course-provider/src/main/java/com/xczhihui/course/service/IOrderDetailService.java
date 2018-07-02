package com.xczhihui.course.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xczhihui.course.model.OrderDetail;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yuxin
 * @since 2018-04-13
 */
public interface IOrderDetailService extends IService<OrderDetail> {

    void saveOrderDetail(OrderDetail orderDetail);

    List<OrderDetail> selectOrderDetailsByOrderId(String id);
}
