package com.xczhihui.course.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.course.model.WatchHistory;
import com.xczhihui.course.vo.WatchHistoryVO;

public interface IWatchHistoryService {
	
	/**
	 * 查询学习中心的最近观看历史
	 * Description：
	 * @param page
	 * @param userId
	 * @return
	 * @return Page<WatchHistoryVO>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	public Page<WatchHistoryVO> selectWatchHistory(Page<WatchHistoryVO> page, String userId);

    /**
     * 增加观看记录
     * Description：
     * @param target
     * @return void
     * @author name：yangxuan <br>email: 15936216273@163.com
     *
     */
	void addOrUpdate(String lockId,WatchHistory target);
	/**
	 * 逻辑删除
	 * Description：
	 * @param target
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	void deleteBatch(String userId);

	void addLearnRecord(String lockId, Integer courseId, String userId, String loginName);
	
}
