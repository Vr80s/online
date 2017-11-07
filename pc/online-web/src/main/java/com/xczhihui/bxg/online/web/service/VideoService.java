package com.xczhihui.bxg.online.web.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.vo.CriticizeVo;

/**
 * 视频相关功能业务层
 * @Author Fudong.Sun【】
 * @Date 2016/11/2 15:16
 */
public interface VideoService {
    /**
     * 获取节下的所有视频
     * @param id 节ID
     * @param courseId
     * @param user
     * @param isTryLearn
     * @return
     */
    public List<Map<String,Object>> getVideos(String id,String courseId,OnlineUser user,Boolean isTryLearn);

    /**
     * 新版本要求查询课程中所有章、节、知识点下的视频列表
     * @param courseId
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getvideos(Integer courseId, String userId, Boolean isTryLearn);

    /**
     * 获取所有对该视频的评论
     * @param videoId 视频ID
     * @return
     */
    public Page<CriticizeVo> getVideoCriticize(String videoId, String name, Integer pageNumber, Integer pageSize);
    /**
     * 提交评论
     */
    public void saveCriticize(CriticizeVo criticizeVo) ;
    /**
     * 根据ID查询评论
     */
    public CriticizeVo findCriticizeById(String id);
    /**
     * 点赞、取消点赞
     */
    public Map<String, Object> updatePraise(Boolean isPraise,String id,OnlineUser user) ;
    /**
     * 修改学员视频学习状态
     */
    public void updateStudyStatus(String studyStatus,String videoId,String userId);
    /**
     * 根据视频id获取学习过的学员
     * @param id 节ID
     * @return
     */
    public List<Map<String, Object>> getLearnedUser(String id) ;
    /**
     * 根据课程id获取购买过该课程的学员
     * @param id 节ID
     * @return
     */
    public List<Map<String, Object>> getPurchasedUser(String id) ;


    /**
     *免费课程  用户报名时，将课程下所有视频插入用户视频中间表中
     * @param courseId 课程id
     * @param password 
     * @return
     */
    public  String saveEntryVideo(Integer  courseId,String password, HttpServletRequest request);

    public String findVideosByCourseId(Integer courseId);
}
