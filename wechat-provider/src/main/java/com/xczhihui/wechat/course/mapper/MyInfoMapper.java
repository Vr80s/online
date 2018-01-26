package com.xczhihui.wechat.course.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.wechat.course.model.OnlineUser;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface MyInfoMapper extends BaseMapper<OnlineUser> {


	List<BigDecimal> selectCollegeCourseXmbNumber(String userId);
}