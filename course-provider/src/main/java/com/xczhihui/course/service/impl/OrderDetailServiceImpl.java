package com.xczhihui.course.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.course.mapper.OrderDetailMapper;
import com.xczhihui.course.model.OrderDetail;
import com.xczhihui.course.service.IOrderDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2018-04-13
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements IOrderDetailService {

    @Override
    public void saveOrderDetail(OrderDetail orderDetail){
        this.baseMapper.insert(orderDetail);
    }

    @Override
    public List<OrderDetail> selectOrderDetailsByOrderId(String orderId) {
        return this.baseMapper.selectOrderDetailsByOrderId(orderId);
    }
}
