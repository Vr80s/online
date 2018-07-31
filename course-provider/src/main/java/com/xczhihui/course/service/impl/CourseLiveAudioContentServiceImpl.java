package com.xczhihui.course.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.course.exception.CourseException;
import com.xczhihui.course.mapper.CourseLiveAudioContentMapper;
import com.xczhihui.course.mapper.CourseLiveAudioDiscussionMapper;
import com.xczhihui.course.mapper.CourseLiveAudioPptMapper;
import com.xczhihui.course.model.CourseLiveAudioContent;
import com.xczhihui.course.model.CourseLiveAudioDiscussion;
import com.xczhihui.course.model.CourseLiveAudioPPT;
import com.xczhihui.course.service.ICourseLiveAudioContentService;
import com.xczhihui.course.vo.CourseLiveAudioContentVO;
import com.xczhihui.course.vo.CourseLiveAudioDiscussionVO;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
@Service
public class CourseLiveAudioContentServiceImpl implements ICourseLiveAudioContentService {

    @Autowired
    private CourseLiveAudioPptMapper courseLiveAudioPptMapper;
    @Autowired
    private CourseLiveAudioContentMapper courseLiveAudioContentMapper;
    @Autowired
    private CourseLiveAudioDiscussionMapper courseLiveAudioDiscussionMapper;

    @Override
    public void saveCourseLiveAudioPPT(Integer courseId, String imgUrl, Integer sort) {
        courseLiveAudioPptMapper.deleteByCourseId(courseId);
        CourseLiveAudioPPT courseLiveAudioPPT = new CourseLiveAudioPPT();
        courseLiveAudioPPT.setCourseId(courseId);
        courseLiveAudioPPT.setImgUrl(imgUrl);
        courseLiveAudioPPT.setSort(sort);
        courseLiveAudioPptMapper.insert(courseLiveAudioPPT);
    }

    @Override
    public void saveCourseLiveAudioContentLike(Integer audioContentId, String userId) {
        courseLiveAudioContentMapper.insertCourseLiveAudioContentLike(audioContentId,userId);
    }

    @Override
    public void saveCourseLiveAudioDiscussionLike(Integer discussionId, String userId) {
        courseLiveAudioDiscussionMapper.insertCourseLiveAudioDiscussionLike(discussionId,userId);
    }

    @Override
    public void saveCourseLiveAudioContent(CourseLiveAudioContentVO courseLiveAudioContentVO) {
        verifyCourseLiveAudioContent(courseLiveAudioContentVO);
        CourseLiveAudioContent courseLiveAudioContent = new CourseLiveAudioContent();
        BeanUtils.copyProperties(courseLiveAudioContentVO,courseLiveAudioContent);
        courseLiveAudioContent.setLike(0);
        courseLiveAudioContentMapper.insert(courseLiveAudioContent);
        //当主播发出的内容为回复某一讨论时，将内容复制一份至讨论记录
        if(courseLiveAudioContent.getDiscussionId() != null){
            CourseLiveAudioDiscussionVO courseLiveAudioDiscussionVO = new CourseLiveAudioDiscussionVO();
            courseLiveAudioDiscussionVO.setContent(courseLiveAudioContent.getContent());
            courseLiveAudioDiscussionVO.setDiscussionId(courseLiveAudioContent.getDiscussionId());
            courseLiveAudioDiscussionVO.setContentType(courseLiveAudioContent.getContentType());
            courseLiveAudioDiscussionVO.setCourseId(courseLiveAudioContent.getCourseId());
            courseLiveAudioDiscussionVO.setSourceAudioLiveContentId(courseLiveAudioContent.getId());
            courseLiveAudioDiscussionVO.setUserId(courseLiveAudioContent.getUserId());
            saveCourseLiveAudioDiscussion(courseLiveAudioDiscussionVO);
        }
    }

    @Override
    public void saveCourseLiveAudioDiscussion(CourseLiveAudioDiscussionVO courseLiveAudioDiscussionVO) {
        verifyCourseLiveAudioDiscussion(courseLiveAudioDiscussionVO);
        CourseLiveAudioDiscussion courseLiveAudioDiscussion = new CourseLiveAudioDiscussion();
        BeanUtils.copyProperties(courseLiveAudioDiscussionVO,courseLiveAudioDiscussion);
        courseLiveAudioDiscussion.setLike(0);
        courseLiveAudioDiscussionMapper.insert(courseLiveAudioDiscussion);
    }

    @Override
    public Page<CourseLiveAudioContentVO> selectCourseLiveAudioContentByCourseId(Page page, Date closingDateTime, Integer courseId){
        if(closingDateTime==null){
            closingDateTime = new Date();
        }
        List<CourseLiveAudioContentVO> courseLiveAudioContentVOList = courseLiveAudioContentMapper.selectCourseLiveAudioContentByCourseId(page,closingDateTime,courseId);
        courseLiveAudioContentVOList.forEach(courseLiveAudioContentVO -> {
            if(courseLiveAudioContentVO.getDiscussionId()!=null){
                CourseLiveAudioDiscussionVO courseLiveAudioDiscussionVO = courseLiveAudioDiscussionMapper.selectCourseLiveAudioDiscussionById(courseLiveAudioContentVO.getDiscussionId());

            }
        });
        page.setItems(courseLiveAudioContentVOList);
        return page;
    }

    private void verifyCourseLiveAudioDiscussion(CourseLiveAudioDiscussionVO courseLiveAudioDiscussionVO) {
        boolean blank = courseLiveAudioDiscussionVO.getCourseId()==null
                ||courseLiveAudioDiscussionVO.getContentType()==null
                ||StringUtils.isBlank(courseLiveAudioDiscussionVO.getContent());
        if(blank){
            throw new CourseException("参数有误");
        }
    }

    private void verifyCourseLiveAudioContent(CourseLiveAudioContentVO courseLiveAudioContentVO) {
        boolean blank = courseLiveAudioContentVO.getCourseId()==null
                ||courseLiveAudioContentVO.getContentType()==null
                ||StringUtils.isBlank(courseLiveAudioContentVO.getContent());
        if(blank){
            throw new CourseException("参数有误");
        }
    }

}
