package com.xczhihui.course.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.course.vo.WatchHistoryVO;

public interface IWatchHistoryService {

    /**
     * 查询学习中心的最近观看历史
     * Description：
     *
     * @param page
     * @param userId
     * @return Page<WatchHistoryVO>
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    public Page<WatchHistoryVO> selectWatchHistory(Page<WatchHistoryVO> page, String userId);

    /**
     * 增加观看记录
     * Description：
     *
     * @param target
     * @return void
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    void addWatchHistory(Integer courseId, String userId, String userLecturerId, Integer collectionId);

    /**
     * 逻辑删除
     * Description：
     *
     * @param target
     * @return void
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    void deleteBatch(String userId);

    void addLearnRecord(Integer courseId, String userId, Boolean teaching);

    void addLookHistory(Integer courseId, String id, Integer recordType, Integer collectionId);

}
