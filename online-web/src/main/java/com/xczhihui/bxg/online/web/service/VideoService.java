package com.xczhihui.bxg.online.web.service;

import java.util.List;
import java.util.Map;

import com.xczhihui.bxg.online.common.domain.Criticize;
import com.xczhihui.common.util.bean.Page;

/**
 * 视频相关功能业务层
 *
 * @Author Fudong.Sun【】
 * @Date 2016/11/2 15:16
 */
public interface VideoService {

    /**
     * 获取所有对该视频的评论
     *
     * @param videoId 视频ID
     * @return
     */
    public Page<Criticize> getVideoCriticize(String videoId, Integer name, Integer pageNumber, Integer pageSize, String userId);

    /**
     * 修改学员视频学习状态
     */
    public void updateStudyStatus(String studyStatus, String videoId, String userId);

    /**
     * 根据视频id获取学习过的学员
     *
     * @param id 节ID
     * @return
     */
    public List<Map<String, Object>> getLearnedUser(String id);

    /**
     * 根据课程id获取购买过该课程的学员
     *
     * @param id 节ID
     * @return
     */
    public List<Map<String, Object>> getPurchasedUser(String id);

    public String findVideosByCourseId(Integer courseId);
}
