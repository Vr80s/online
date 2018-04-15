package com.xczhihui.wechat.course.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.wechat.course.model.Order;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2018-04-13
 */
public interface OrderMapper extends BaseMapper<Order> {

    @Select("")
    Order selectByUserIdAndCourseId(String userId, int courseId);
}