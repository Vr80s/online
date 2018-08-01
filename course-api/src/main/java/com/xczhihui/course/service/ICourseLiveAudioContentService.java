package com.xczhihui.course.service;


import java.util.Date;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.course.vo.CourseLiveAudioContentVO;
import com.xczhihui.course.vo.CourseLiveAudioDiscussionVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yuxin
 * @since 2018-07-31
 */
public interface ICourseLiveAudioContentService {

    void saveCourseLiveAudioPPT(Integer courseId,String imgUrl,Integer sort);

    void saveCourseLiveAudioContentLike(Integer audioContentId, String userId) throws Exception;

    void saveCourseLiveAudioDiscussionLike(Integer discussionId, String userId) throws Exception;

    void saveCourseLiveAudioContent(CourseLiveAudioContentVO courseLiveAudioContent) throws Exception;

    void saveCourseLiveAudioDiscussion(CourseLiveAudioDiscussionVO courseLiveAudioDiscussionVO) throws Exception;

    Page<CourseLiveAudioContentVO> selectCourseLiveAudioContentByCourseId(Page page, String closingDateTime, Integer courseId);

    Page<CourseLiveAudioDiscussionVO> selectCourseLiveAudioDiscussionByCourseId(Page page, String closingDateTime, Integer courseId);

    void deleteCourseLiveAudioContent(String accountId, Integer courseLiveAudioContentId) throws Exception;

    void deleteCourseLiveAudioDiscussion(String accountId, Integer courseLiveAudioDiscussionId) throws Exception;
}
