package com.xczhihui.wechat.course.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.wechat.course.model.WatchHistory;
import com.xczhihui.wechat.course.vo.WatchHistoryVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
  *  Mapper 接口
 * </p>
 * @author yuxin
 * @since 2017-12-09
 */
public interface WatchHistoryMapper extends BaseMapper<WatchHistory> {

	List<WatchHistoryVO> selectWatchHistory(@Param("page")Page<WatchHistoryVO> page,@Param("userId") String userId);

	WatchHistory findWatchHistoryByUserIdAndCourseId(@Param("userId") String userId,
			@Param("courseId")Integer courseId);

	void deleteBatch(@Param("list") List<WatchHistory> list);

	List<WatchHistory> findWatchHistoryByUserId(@Param("userId") String userId);
}