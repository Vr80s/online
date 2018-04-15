package com.xczhihui.wechat.course.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.wechat.course.mapper.OrderMapper;
import com.xczhihui.wechat.course.model.Order;
import com.xczhihui.wechat.course.service.IOrderService;
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
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {


    @Override
    public String saveOrder(int courseId, String userId, Integer orderFrom) {
        //1.判断该用户该课程的未支付订单
        Order order = this.baseMapper.selectByUserIdAndCourseId(userId,courseId);
        return null;
    }
}
