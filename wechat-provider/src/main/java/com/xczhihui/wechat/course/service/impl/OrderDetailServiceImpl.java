package com.xczhihui.wechat.course.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.wechat.course.mapper.OrderDetailMapper;
import com.xczhihui.wechat.course.model.OrderDetail;
import com.xczhihui.wechat.course.service.IOrderDetailService;
import org.springframework.stereotype.Service;

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
}
