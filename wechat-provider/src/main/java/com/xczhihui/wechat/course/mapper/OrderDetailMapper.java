package com.xczhihui.wechat.course.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.wechat.course.model.OrderDetail;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2018-04-13
 */
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

    @Select("SELECT * FROM `oe_order_detail` ood WHERE ood.`order_id` = #{orderId}")
    List<OrderDetail> selectOrderDetailsByOrderId(String orderId);
}