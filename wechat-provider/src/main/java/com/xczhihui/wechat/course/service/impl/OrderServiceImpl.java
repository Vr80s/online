package com.xczhihui.wechat.course.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
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

import java.util.ArrayList;
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
            order = saveOrder(userId,course,orderFrom);
        }else if(order==null){
            order = saveOrder(userId,course,orderFrom);
        }
        String courseNames = getCourseNames(order);
        order.setCourseNames(courseNames);
        return order;
    }

    @Override
    public Order getOrderByOrderId(String orderId){
        Order o = new Order();
        o.setDelete(false);
        o.setId(orderId);
        o.setOrderStatus(0);
        return this.baseMapper.selectOne(o);
    }

    @Override
    public Order getOrderNo4PayByOrderNo(String orderNo){
        Order order = new Order();
        order.setDelete(false);
        order.setOrderNo(orderNo);
        order.setOrderStatus(0);
        order = this.baseMapper.selectOne(order);
        if(order==null){
            throw new RuntimeException(orderNo+"该单号下不存在订单信息，下单失败");
        }
        String courseNames = getCourseNames(order);
        order.setCourseNames(courseNames);
        return order;
    }

    @Override
    public Order getOrderNo4PayByOrderId(String orderId){
        Order order = new Order();
        order.setDelete(false);
        order.setId(orderId);
        order.setOrderStatus(0);
        order = this.baseMapper.selectOne(order);
        if(order==null){
            throw new RuntimeException(orderId+"该单id下不存在订单信息，下单失败");
        }
        String courseNames = getCourseNames(order);
        order.setCourseNames(courseNames);
        List<Integer> courseIds = getCourseIds(order);
        order.setCourseIds(courseIds);
        return order;
    }

    private List<Integer> getCourseIds(Order order) {
        List<OrderDetail> orderDetailList = this.orderDetailService.selectOrderDetailsByOrderId(order.getId());
        List<Integer> courseIds = new ArrayList<>();
        orderDetailList.forEach(orderDetail -> {
            Integer count = this.baseMapper.selectCountByUserIdAndCourseId(order.getUserId(),orderDetail.getCourseId());
            if(count!=null && count >0){
                throw new RuntimeException("订单中含有已购课程");
            }
            Course course = courseMapper.selectById(orderDetail.getCourseId());
            if(course.getDelete() || "0".equals(course.getStatus())){
                throw new RuntimeException("《"+course.getGradeName()+"》已下架");
            }
            courseIds.add(course.getId());
        });
        return courseIds;
    }

    private String getCourseNames(Order order){
        List<OrderDetail> orderDetailList = this.orderDetailService.selectOrderDetailsByOrderId(order.getId());
        StringBuilder courseNames = new StringBuilder();
        orderDetailList.forEach(orderDetail -> {
            Integer count = this.baseMapper.selectCountByUserIdAndCourseId(order.getUserId(),orderDetail.getCourseId());
            if(count!=null && count >0){
                throw new RuntimeException("订单中含有已购课程");
            }
            Course course = courseMapper.selectById(orderDetail.getCourseId());
            if(course.getDelete() || "0".equals(course.getStatus())){
                throw new RuntimeException("《"+course.getGradeName()+"》已下架");
            }
            courseNames.append("《"+course.getGradeName()+"》");
        });
        return courseNames.toString();
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
