package com.xczhihui.course.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.course.vo.CourseLiveAudioContentVO;
import com.xczhihui.course.vo.CourseLiveAudioDiscussionVO;
import com.xczhihui.course.vo.CourseLiveAudioPPTVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yuxin
 * @since 2018-07-31
 */
public interface ICourseLiveAudioContentService {

    void saveCourseLiveAudioContentLike(Integer audioContentId, String userId) throws Exception;

    void saveCourseLiveAudioDiscussionLike(Integer discussionId, String userId) throws Exception;

    void saveCourseLiveAudioContent(CourseLiveAudioContentVO courseLiveAudioContent) throws Exception;

    void saveCourseLiveAudioDiscussion(CourseLiveAudioDiscussionVO courseLiveAudioDiscussionVO) throws Exception;

    Page<CourseLiveAudioContentVO> selectCourseLiveAudioContentByCourseId(Page page, String endTime, Integer courseId);

    Page<CourseLiveAudioDiscussionVO> selectCourseLiveAudioDiscussionByCourseId(Page page, String endTime, Integer courseId, Boolean question);

    void deleteCourseLiveAudioContent(String accountId, Integer courseLiveAudioContentId) throws Exception;

    void deleteCourseLiveAudioDiscussion(String accountId, Integer courseLiveAudioDiscussionId) throws Exception;

    void saveCourseLiveAudioDiscussionBan(String accountId, Integer courseId, String userId) throws Exception;

    void saveCourseLiveAudioPPTs(Integer courseId, String accountId, List<CourseLiveAudioPPTVO> courseLiveAudioPPTs);

    List<CourseLiveAudioPPTVO> selectCourseLiveAudioPPTsByCourseId(Integer courseId);

    Map<String, Object> getCourseLiveAudioAccessToken(Integer courseId, String accountId) throws Exception;

    void stop(String accountId, Integer courseId);

    void push(String accountId, Integer courseId);

    Integer getPushCount(Integer courseId);
}
