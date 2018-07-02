package com.xczhihui.course.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.course.model.OrderDetail;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2018-04-13
 */
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

    @Select("SELECT * FROM `oe_order_detail` ood WHERE ood.`order_id` = #{orderId}")
    List<OrderDetail> selectOrderDetailsByOrderId(String orderId);
}