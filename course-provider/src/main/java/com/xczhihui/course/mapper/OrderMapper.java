package com.xczhihui.course.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.course.model.Order;
import com.xczhihui.course.vo.OnlineCourseVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
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

    @Select("SELECT \n" +
            "  oo.id,\n" +
            "  oo.`actual_pay`,\n" +
            "  oo.`order_no`,\n" +
            "  ood.`price`\n" +
            "FROM\n" +
            "  oe_order AS oo\n" +
            "  JOIN `oe_order_detail` ood \n" +
            "    ON oo.id = ood.order_id \n" +
            "WHERE oo.id = order_id \n" +
            "  AND oo.user_id = #{userId} \n" +
            "  AND ood.course_id = #{courseId} \n" +
            "  AND oo.order_status = 0 ")
    Order selectByUserIdAndCourseId(@Param("userId") String userId, @Param("courseId") int courseId);

    @Select("SELECT COUNT(*) FROM `apply_r_grade_course` argc WHERE argc.`user_id`=#{userId} AND argc.`course_id`=#{courseId} AND argc.`validity`>NOW()")
    Integer selectCountByUserIdAndCourseId(@Param("userId") String userId, @Param("courseId") String courseId);

    @Select("SELECT id FROM `oe_order` oo WHERE oo.`order_no` = #{orderNo}")
    Order selectByOrderNo(String orderNo);

	List<OnlineCourseVo> getCourseByOrderId(@Param("orderId")String orderId);
}