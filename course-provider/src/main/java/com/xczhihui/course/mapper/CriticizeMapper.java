package com.xczhihui.course.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.course.model.Criticize;
import com.xczhihui.course.model.Reply;

/**
 * <p>
 * Mapper 接口
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

    Integer hasCourseIsBuy(@Param("courseId") Integer courseId, @Param("userId") String userId);

    Integer hasUserAllCourseIsBuy(@Param("userId") String userId, @Param("userLecturerId") String userLecturerId);

    List<Integer> selectPcCourseCommentMeanCount(@Param("collection") Boolean collection, @Param("courseId") Integer courseId);

    List<Double> selectPcUserCommentMeanCount(@Param("userId") String userId);

    List<Integer> selectMobileCourseCommentMeanCount(@Param("courseId") Integer courseId);

    List<Integer> selectMobileUserCommentMeanCount(@Param("userId") String userId);
}