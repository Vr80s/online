package com.xczhihui.course.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.common.support.service.CacheService;
import com.xczhihui.common.util.enums.CourseLiveAudioMessageType;
import com.xczhihui.common.util.redis.key.CourseRedisCacheKey;
import com.xczhihui.common.util.vhallyun.ChatService;
import com.xczhihui.course.exception.CourseException;
import com.xczhihui.course.mapper.CourseLiveAudioContentMapper;
import com.xczhihui.course.mapper.CourseLiveAudioDiscussionMapper;
import com.xczhihui.course.mapper.CourseLiveAudioPptMapper;
import com.xczhihui.course.model.CourseLiveAudioContent;
import com.xczhihui.course.model.CourseLiveAudioDiscussion;
import com.xczhihui.course.model.CourseLiveAudioPPT;
import com.xczhihui.course.service.ICourseLiveAudioContentService;
import com.xczhihui.course.vo.*;

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
    private CourseLiveAudioContentMapper courseLiveAudioContentMapper;
    @Autowired
    private CourseLiveAudioDiscussionMapper courseLiveAudioDiscussionMapper;
    @Autowired
    private CourseLiveAudioPptMapper courseLiveAudioPptMapper;
    @Autowired
    private CacheService cacheService;


    @Override
    public void saveCourseLiveAudioContentLike(Integer audioContentId, String userId) throws Exception {
        int i = courseLiveAudioContentMapper.insertCourseLiveAudioContentLike(audioContentId,userId);
        if(i > 0){
            courseLiveAudioContentMapper.updateLikeById(audioContentId);
            CourseLiveAudioContent courseLiveAudioContent = courseLiveAudioContentMapper.selectById(audioContentId);
            String channelId = courseLiveAudioContentMapper.selectChannelIdByCourseId(courseLiveAudioContent.getCourseId());
            if(courseLiveAudioContent.getDiscussionId() != null){
                //回答的授课内容被点赞，同步至讨论记录
                int j = courseLiveAudioDiscussionMapper.insertCourseLiveAudioDiscussionLike(courseLiveAudioContent.getDiscussionId(),userId);
                if(j > 0){
                    courseLiveAudioDiscussionMapper.updateLikeById(courseLiveAudioContent.getDiscussionId());
                    CourseLiveAudioDiscussion courseLiveAudioDiscussion = courseLiveAudioDiscussionMapper.selectById(courseLiveAudioContent.getDiscussionId());
                    sentCourseLiveAudioDiscussionLikeCustomBroadcast(channelId,courseLiveAudioDiscussion.getId(),courseLiveAudioDiscussion.getLikes());
                }
            }
            sentCourseLiveAudioContentLikeCustomBroadcast(channelId,audioContentId,courseLiveAudioContent.getLikes());
        }
    }

    private void sentCourseLiveAudioDiscussionLikeCustomBroadcast(String channelId, Integer discussionId, Integer likes) throws Exception {
        CourseLiveAudioMessageVO message = new CourseLiveAudioMessageVO(CourseLiveAudioMessageType.CourseLiveAudioDiscussionLike.getCode(),new CourseLiveAudioLikeMessageVO(discussionId,likes));
        ChatService.sentCustomBroadcast(channelId, message.toJson());
    }

    private void sentCourseLiveAudioContentLikeCustomBroadcast(String channelId, Integer audioContentId, Integer likes) throws Exception {
        CourseLiveAudioMessageVO message = new CourseLiveAudioMessageVO(CourseLiveAudioMessageType.CourseLiveAudioContentLike.getCode(),new CourseLiveAudioLikeMessageVO(audioContentId,likes));
        ChatService.sentCustomBroadcast(channelId, message.toJson());
    }

    @Override
    public void saveCourseLiveAudioDiscussionLike(Integer discussionId, String userId) throws Exception {
        int count = courseLiveAudioDiscussionMapper.insertCourseLiveAudioDiscussionLike(discussionId,userId);
        if(count > 0){
            courseLiveAudioDiscussionMapper.updateLikeById(discussionId);
            CourseLiveAudioDiscussion courseLiveAudioDiscussion = courseLiveAudioDiscussionMapper.selectById(discussionId);
            String channelId = courseLiveAudioContentMapper.selectChannelIdByCourseId(courseLiveAudioDiscussion.getCourseId());
            if(courseLiveAudioDiscussion.getSourceAudioLiveContentId() != null){
                //讨论记录中主播回答的记录被点赞，同步至授课内容
                int i = courseLiveAudioContentMapper.insertCourseLiveAudioContentLike(courseLiveAudioDiscussion.getSourceAudioLiveContentId(),userId);
                if(i > 0){
                    courseLiveAudioContentMapper.updateLikeById(courseLiveAudioDiscussion.getSourceAudioLiveContentId());
                    CourseLiveAudioContent courseLiveAudioContent = courseLiveAudioContentMapper.selectById(courseLiveAudioDiscussion.getSourceAudioLiveContentId());
                    sentCourseLiveAudioContentLikeCustomBroadcast(channelId,courseLiveAudioContent.getId(),courseLiveAudioContent.getLikes());
                }
            }
            sentCourseLiveAudioDiscussionLikeCustomBroadcast(channelId,courseLiveAudioDiscussion.getId(),courseLiveAudioDiscussion.getLikes());
        }
    }

    @Override
    public void saveCourseLiveAudioContent(CourseLiveAudioContentVO courseLiveAudioContentVO) throws Exception {
        verifyCourseLiveAudioContent(courseLiveAudioContentVO);
        String channelId = courseLiveAudioContentMapper.selectChannelIdByCourseId(courseLiveAudioContentVO.getCourseId());
        if(channelId == null){
            throw new CourseException("课程信息有误");
        }
        courseLiveAudioContentVO.setLikes(0);
        CourseLiveAudioContent courseLiveAudioContent = new CourseLiveAudioContent();
        BeanUtils.copyProperties(courseLiveAudioContentVO,courseLiveAudioContent);
        Integer insertCount = courseLiveAudioContentMapper.insert(courseLiveAudioContent);
        if(insertCount == 0){
            throw new CourseException("该问题已回复");
        }
        //当主播发出的内容为回复某一讨论时，将内容复制一份至讨论记录
        if(courseLiveAudioContent.getDiscussionId() != null){
            CourseLiveAudioDiscussionVO courseLiveAudioDiscussionVO = new CourseLiveAudioDiscussionVO();
            courseLiveAudioDiscussionVO.setContent(courseLiveAudioContent.getContent());
            courseLiveAudioDiscussionVO.setDiscussionId(courseLiveAudioContent.getDiscussionId());
            courseLiveAudioDiscussionVO.setContentType(courseLiveAudioContent.getContentType());
            courseLiveAudioDiscussionVO.setCourseId(courseLiveAudioContent.getCourseId());
            courseLiveAudioDiscussionVO.setSourceAudioLiveContentId(courseLiveAudioContent.getId());
            courseLiveAudioDiscussionVO.setUserId(courseLiveAudioContent.getUserId());
            courseLiveAudioDiscussionVO.setAnchor(true);
            saveCourseLiveAudioDiscussion(courseLiveAudioDiscussionVO);
        }
        courseLiveAudioContentVO.setId(courseLiveAudioContent.getId());
        sentCourseLiveAudioContentCustomBroadcast(channelId,courseLiveAudioContentVO);
    }

    private void sentCourseLiveAudioContentCustomBroadcast(String channelId, CourseLiveAudioContentVO courseLiveAudioContentVO) throws Exception {
        courseLiveAudioContentVO = courseLiveAudioContentMapper.selectCourseLiveAudioContentById(courseLiveAudioContentVO.getId());
        if(courseLiveAudioContentVO.getDiscussionId() != null){
            CourseLiveAudioDiscussionVO courseLiveAudioDiscussionVO = courseLiveAudioDiscussionMapper.selectCourseLiveAudioDiscussionById(courseLiveAudioContentVO.getDiscussionId());
            courseLiveAudioContentVO.setCourseLiveAudioDiscussionVO(courseLiveAudioDiscussionVO);
        }

        ChatService.sentCustomBroadcast(channelId, courseLiveAudioContentVO.toJson());
    }

    @Override
    public void saveCourseLiveAudioDiscussion(CourseLiveAudioDiscussionVO courseLiveAudioDiscussionVO) throws Exception {
        String channelId = courseLiveAudioContentMapper.selectChannelIdByCourseId(courseLiveAudioDiscussionVO.getCourseId());
        if(channelId == null){
            throw new CourseException("课程信息有误");
        }
        verifyCourseLiveAudioDiscussion(courseLiveAudioDiscussionVO);
        String ban = cacheService.get(CourseRedisCacheKey.getLiveAudioBanCacheKey(courseLiveAudioDiscussionVO.getCourseId(), courseLiveAudioDiscussionVO.getUserId()));
        if(ban != null){
            throw new CourseException("你已被禁言");
        }
        CourseLiveAudioDiscussion courseLiveAudioDiscussion = new CourseLiveAudioDiscussion();
        BeanUtils.copyProperties(courseLiveAudioDiscussionVO,courseLiveAudioDiscussion);
        courseLiveAudioDiscussion.setLikes(0);
        courseLiveAudioDiscussionMapper.insert(courseLiveAudioDiscussion);
        courseLiveAudioDiscussionVO.setId(courseLiveAudioDiscussion.getId());
        sentCourseLiveAudioDiscussionCustomBroadcast(channelId,courseLiveAudioDiscussionVO);
    }

    private void sentCourseLiveAudioDiscussionCustomBroadcast(String channelId, CourseLiveAudioDiscussionVO courseLiveAudioDiscussionVO) throws Exception {
        courseLiveAudioDiscussionVO = courseLiveAudioDiscussionMapper.selectCourseLiveAudioDiscussionById(courseLiveAudioDiscussionVO.getId());
        if(courseLiveAudioDiscussionVO.getDiscussionId() != null){
            CourseLiveAudioDiscussionVO alad = courseLiveAudioDiscussionMapper.selectCourseLiveAudioDiscussionById(courseLiveAudioDiscussionVO.getDiscussionId());
            courseLiveAudioDiscussionVO.setCourseLiveAudioDiscussionVO(alad);
        }
        ChatService.sentCustomBroadcast(channelId, courseLiveAudioDiscussionVO.toJson());
    }

    @Override
    public Page<CourseLiveAudioContentVO> selectCourseLiveAudioContentByCourseId(Page page, String endTime, Integer courseId){
        if(endTime==null){
            endTime = LocalDateTime.now().toString();
        }
        List<CourseLiveAudioContentVO> courseLiveAudioContentVOList = courseLiveAudioContentMapper.selectCourseLiveAudioContentByCourseId(page,endTime,courseId);
        courseLiveAudioContentVOList.forEach(courseLiveAudioContentVO -> {
            if(courseLiveAudioContentVO.getDiscussionId()!=null){
                CourseLiveAudioDiscussionVO courseLiveAudioDiscussionVO = courseLiveAudioDiscussionMapper.selectCourseLiveAudioDiscussionById(courseLiveAudioContentVO.getDiscussionId());
                courseLiveAudioContentVO.setCourseLiveAudioDiscussionVO(courseLiveAudioDiscussionVO);
            }
        });
        page.setRecords(courseLiveAudioContentVOList);
        return page;
    }

    @Override
    public Page<CourseLiveAudioDiscussionVO> selectCourseLiveAudioDiscussionByCourseId(Page page, String endTime, Integer courseId){
        if(endTime==null){
            endTime = LocalDateTime.now().toString();
        }
        List<CourseLiveAudioDiscussionVO> courseLiveAudioDiscussionVOList = courseLiveAudioDiscussionMapper.selectCourseLiveAudioDiscussionByCourseId(page,endTime,courseId);
        courseLiveAudioDiscussionVOList.forEach(courseLiveAudioDiscussionVO -> {
            if(courseLiveAudioDiscussionVO.getDiscussionId()!=null){
                CourseLiveAudioDiscussionVO cld = courseLiveAudioDiscussionMapper.selectCourseLiveAudioDiscussionById(courseLiveAudioDiscussionVO.getDiscussionId());
                courseLiveAudioDiscussionVO.setCourseLiveAudioDiscussionVO(cld);
            }
        });
        page.setRecords(courseLiveAudioDiscussionVOList);
        return page;
    }

    @Override
    public void deleteCourseLiveAudioContent(String userId, Integer courseLiveAudioContentId) throws Exception {
        int i = courseLiveAudioContentMapper.deleteByUserIdAndId(userId, courseLiveAudioContentId);
        if(i>0){
            String channelId = courseLiveAudioContentMapper.selectChannelIdByCourseLiveAudioContentId(courseLiveAudioContentId);
            CourseLiveAudioContentVO courseLiveAudioContentVO = courseLiveAudioContentMapper.selectCourseLiveAudioContentById(courseLiveAudioContentId);
            sentCourseLiveAudioContentDeleteCustomBroadcast(channelId,courseLiveAudioContentId);
            if(courseLiveAudioContentVO.getDiscussionId() != null){
                int j = courseLiveAudioDiscussionMapper.deleteByUserIdAndId(userId,courseLiveAudioContentVO.getDiscussionId());
                if(j>0){
                    sentCourseLiveAudioDiscussionDeleteCustomBroadcast(channelId,courseLiveAudioContentVO.getDiscussionId());
                }
            }
        }else{
            throw new CourseException("操作失败");
        }
    }

    private void sentCourseLiveAudioContentDeleteCustomBroadcast(String channelId, Integer courseLiveAudioContentId) throws Exception {
        Map map = new HashMap<>();
        map.put("id",courseLiveAudioContentId);
        CourseLiveAudioMessageVO message = new CourseLiveAudioMessageVO(CourseLiveAudioMessageType.CourseLiveAudioContentDelete.getCode(),map);
        ChatService.sentCustomBroadcast(channelId, message.toJson());
    }

    @Override
    public void deleteCourseLiveAudioDiscussion(String userId, Integer courseLiveAudioDiscussionId) throws Exception {
        int i = courseLiveAudioDiscussionMapper.deleteByUserIdAndId(userId,courseLiveAudioDiscussionId);
        if(i>0){
            String channelId = courseLiveAudioDiscussionMapper.selectChannelIdByCourseLiveAudioDiscussionId(courseLiveAudioDiscussionId);
            sentCourseLiveAudioDiscussionDeleteCustomBroadcast(channelId,courseLiveAudioDiscussionId);
            CourseLiveAudioDiscussionVO courseLiveAudioDiscussionVO = courseLiveAudioDiscussionMapper.selectCourseLiveAudioDiscussionById(courseLiveAudioDiscussionId);
            if(courseLiveAudioDiscussionVO.getSourceAudioLiveContentId()!=null){
                int j = courseLiveAudioContentMapper.deleteByUserIdAndId(userId, courseLiveAudioDiscussionVO.getSourceAudioLiveContentId());
                if(j>0){
                    sentCourseLiveAudioContentDeleteCustomBroadcast(channelId,courseLiveAudioDiscussionVO.getSourceAudioLiveContentId());
                }
            }
        }
    }

    @Override
    public void saveCourseLiveAudioDiscussionBan(String accountId, Integer courseId, String userId) throws Exception {
        if(StringUtils.isBlank(userId)||courseId==null){
            throw new CourseException("参数有误");
        }
        int count = courseLiveAudioDiscussionMapper.selectCourseCount(accountId,courseId);
        if(count > 0){
            cacheService.set(CourseRedisCacheKey.getLiveAudioBanCacheKey(courseId,userId),"禁言");
            List<Integer> ids = courseLiveAudioDiscussionMapper.selectCourseLiveAudioDiscussionIdsByCourseIdAndUserId(courseId,userId);
            for (Integer id : ids) {
                deleteCourseLiveAudioDiscussion(userId,id);
            }
        }else{
            throw new CourseException("不具有该权限");
        }
    }

    @Override
    public void saveCourseLiveAudioPPTs(Integer courseId, String accountId, List<CourseLiveAudioPPTVO> courseLiveAudioPPTs) {
        int courseCount = courseLiveAudioDiscussionMapper.selectCourseCount(accountId,courseId);
        if(courseCount == 0){
            throw new CourseException("不具有该权限");
        }
        courseLiveAudioPptMapper.deleteByCourseId(courseId);
        for (CourseLiveAudioPPTVO courseLiveAudioPPT : courseLiveAudioPPTs) {
            courseLiveAudioPPT.setCourseId(courseId);
            courseLiveAudioPPT.setUserId(accountId);
            verifyCourseLiveAudioPPT(courseLiveAudioPPT);
            CourseLiveAudioPPT ppt = new CourseLiveAudioPPT();
            BeanUtils.copyProperties(courseLiveAudioPPT,ppt);
            courseLiveAudioPptMapper.insert(ppt);
        }
    }

    @Override
    public List<CourseLiveAudioPPTVO> selectCourseLiveAudioPPTsByCourseId(Integer courseId) {
        return courseLiveAudioPptMapper.selectCourseLiveAudioPPTsByCourseId(courseId);
    }

    private void verifyCourseLiveAudioPPT(CourseLiveAudioPPTVO courseLiveAudioPPT) {
        boolean blank = courseLiveAudioPPT.getCourseId() == null
                ||courseLiveAudioPPT.getSort() == null
                ||StringUtils.isBlank(courseLiveAudioPPT.getImgUrl())
                ||StringUtils.isBlank(courseLiveAudioPPT.getUserId());
        if(blank){
            throw new CourseException("参数有误");
        }
    }

    private void sentCourseLiveAudioDiscussionDeleteCustomBroadcast(String channelId, Integer courseLiveAudioDiscussionId) throws Exception {
        Map map = new HashMap<>();
        map.put("id",courseLiveAudioDiscussionId);
        CourseLiveAudioMessageVO message = new CourseLiveAudioMessageVO(CourseLiveAudioMessageType.CourseLiveAudioDiscussionDelete.getCode(),map);
        ChatService.sentCustomBroadcast(channelId, message.toJson());
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
