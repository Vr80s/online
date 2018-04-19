package com.xczhihui.wechat.course.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.bxg.common.util.IStringUtil;
import com.xczhihui.bxg.common.util.OrderNoUtil;
import com.xczhihui.bxg.common.util.enums.OrderStatus;
import com.xczhihui.wechat.course.mapper.CourseMapper;
import com.xczhihui.wechat.course.mapper.OrderMapper;
import com.xczhihui.wechat.course.model.Course;
import com.xczhihui.wechat.course.model.Order;
import com.xczhihui.wechat.course.model.OrderDetail;
import com.xczhihui.wechat.course.service.IOrderDetailService;
import com.xczhihui.wechat.course.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private IOrderDetailService orderDetailService;

    @Override
    public Order createOrder(String userId, int courseId, Integer orderFrom) {
        Course course = courseMapper.selectById(courseId);
        //获取该用户该课程的未支付订单
        Order order = this.baseMapper.selectByUserIdAndCourseId(userId,courseId);
        //订单不为空且课程现在单价与原订单单价不符：关闭原订单，创建新的订单
        if(order!=null && course.getCurrentPrice().compareTo(order.getPrice())!=0){
            order.setOrderStatus(OrderStatus.CLOSED.getCode());
            this.baseMapper.updateById(order);
            return saveOrder(userId,course,orderFrom);
        }else if(order==null){
            return saveOrder(userId,course,orderFrom);
        }else{
            return order;
        }
    }

    private Order saveOrder(String userId, Course course, Integer orderFrom) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setCourseId(course.getId().toString());
        orderDetail.setActualPay(course.getCurrentPrice());
        orderDetail.setId(IStringUtil.getUuid());
        orderDetail.setPrice(course.getCurrentPrice());

        Order order = new Order();
        order.setId(IStringUtil.getUuid());
        order.setPrice(orderDetail.getActualPay());
        order.setActualPay(orderDetail.getActualPay());
        //订单号
        order.setOrderNo(OrderNoUtil.getCourseOrderNo());
        //优惠金额
        order.setPreferentyMoney(0.0);
        //用户ID
        order.setUserId(userId);
        //订单来源   1：pc 2：h5 3:android 4 ios 5 线下 6 工作人员
        order.setOrderFrom(orderFrom);

        orderDetail.setOrderId(order.getId());
        this.baseMapper.insert(order);
        this.orderDetailService.saveOrderDetail(orderDetail);

        return order;
    }
}
