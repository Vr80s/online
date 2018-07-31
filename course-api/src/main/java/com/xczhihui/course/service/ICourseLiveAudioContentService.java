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

    void saveCourseLiveAudioContentLike(Integer audioContentId, String userId);

    void saveCourseLiveAudioDiscussionLike(Integer discussionId, String userId);

    void saveCourseLiveAudioContent(CourseLiveAudioContentVO courseLiveAudioContent);

    void saveCourseLiveAudioDiscussion(CourseLiveAudioDiscussionVO courseLiveAudioDiscussionVO);

    Page<CourseLiveAudioContentVO> selectCourseLiveAudioContentByCourseId(Page page, Date closingDateTime, Integer courseId);
}
