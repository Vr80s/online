package com.xczhihui.wechat.course.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.wechat.course.model.Criticize;
import com.xczhihui.wechat.course.model.Reply;
import org.apache.ibatis.annotations.Param;
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
public interface CriticizeMapper extends BaseMapper<Criticize> {

    List<Criticize> selectCourseCriticize(@Param("page") Page<Criticize> page, @Param("courseId") Integer courseId, @Param("userId") String userId);

    List<Reply> selectReplyByCriticizeId(@Param("criticizeId") String criticizeId);

    Integer hasCourse(@Param("courseId") Integer courseId, @Param("userId") String userId);

    List<Criticize> selectCollectionCriticize(@Param("page") Page<Criticize> page, @Param("courseIds") List<Integer> courseIds, @Param("userId") String userId);

    List<Criticize> selectAnchorCriticize(@Param("page") Page<Criticize> page, @Param("anchorUserId") String anchorUserId, @Param("userId") String userId);

    Integer hasCriticizeScore(@Param("courseId") Integer courseId, @Param("userId") String userId);
}