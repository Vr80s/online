package com.xczhihui.course.service.impl;

import static com.xczhihui.common.util.enums.RouteTypeEnum.COMMON_LEARNING_AUDIO_LIVE_COURSE_DETAIL_PAGE;
import static com.xczhihui.common.util.redis.key.CourseRedisCacheKey.COURSE_LIVE_TOKEN_SECONDS;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.common.support.service.CacheService;
import com.xczhihui.common.util.enums.CourseLiveAudioMessageType;
import com.xczhihui.common.util.enums.MessageTypeEnum;
import com.xczhihui.common.util.redis.key.CourseRedisCacheKey;
import com.xczhihui.common.util.vhallyun.BaseService;
import com.xczhihui.common.util.vhallyun.ChatService;
import com.xczhihui.course.exception.CourseException;
import com.xczhihui.course.mapper.CourseLiveAudioContentMapper;
import com.xczhihui.course.mapper.CourseLiveAudioDiscussionMapper;
import com.xczhihui.course.mapper.CourseLiveAudioPptMapper;
import com.xczhihui.course.model.CourseLiveAudioContent;
import com.xczhihui.course.model.CourseLiveAudioDiscussion;
import com.xczhihui.course.model.CourseLiveAudioPPT;
import com.xczhihui.course.params.BaseMessage;
import com.xczhihui.course.service.ICommonMessageService;
import com.xczhihui.course.service.ICourseLiveAudioContentService;
import com.xczhihui.course.util.TextStyleUtil;
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

    private static final String WEB_AUDIO_LIVE_COURSE_REMIND_FANCES = "【上课提醒】您好，您关注的主播{0},有新的直播课程" + TextStyleUtil.LEFT_TAG + "《{1}》" + TextStyleUtil.RIGHT_TAG + "开播了，快登录熊猫中医APP观看该直播吧！";
    private static final String WEB_AUDIO_LIVE_COURSE_REMIND_BUYER = "【上课提醒】您好，您购买的直播课程" + TextStyleUtil.LEFT_TAG + "《{0}》" + TextStyleUtil.RIGHT_TAG + "开播了，快登录熊猫中医APP观看该直播吧！";
    private static final Logger loggger = LoggerFactory.getLogger(CourseLiveAudioContentServiceImpl.class);

    @Autowired
    private CourseLiveAudioContentMapper courseLiveAudioContentMapper;
    @Autowired
    private CourseLiveAudioDiscussionMapper courseLiveAudioDiscussionMapper;
    @Autowired
    private CourseLiveAudioPptMapper courseLiveAudioPptMapper;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private ICommonMessageService commonMessageService;

    @Value("${weixin.course.remind.code}")
    private String weixinTemplateMessageRemindCode;




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
        int contentCount = courseLiveAudioContentMapper.selectContentCountByCourseId(courseLiveAudioContentVO.getCourseId());
        if(contentCount == 0){
            courseLiveAudioContentMapper.start(courseLiveAudioContentVO.getUserId(),courseLiveAudioContentVO.getCourseId());
        }
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
        CourseLiveAudioContentVO courseLiveAudioContentVO = courseLiveAudioContentMapper.selectCourseLiveAudioContentById(courseLiveAudioContentId);
        int i = courseLiveAudioContentMapper.deleteByUserIdAndId(userId, courseLiveAudioContentId);
        if(i>0){
            String channelId = courseLiveAudioContentMapper.selectChannelIdByCourseLiveAudioContentId(courseLiveAudioContentId);
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
        CourseLiveAudioDiscussionVO courseLiveAudioDiscussionVO = courseLiveAudioDiscussionMapper.selectCourseLiveAudioDiscussionById(courseLiveAudioDiscussionId);
        int i = courseLiveAudioDiscussionMapper.deleteByUserIdAndId(userId,courseLiveAudioDiscussionId);
        if(i>0){
            String channelId = courseLiveAudioDiscussionMapper.selectChannelIdByCourseLiveAudioDiscussionId(courseLiveAudioDiscussionId);
            sentCourseLiveAudioDiscussionDeleteCustomBroadcast(channelId,courseLiveAudioDiscussionId);
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

    @Override
    public String getCourseLiveAudioAccessToken(Integer courseId, String accountId) throws Exception {
        String accessToken = cacheService.get(CourseRedisCacheKey.getLiveAudioTokenCacheKey(courseId,accountId));
        if(StringUtils.isNotBlank(accessToken)){
            return accessToken;
        }
        String channelId = courseLiveAudioContentMapper.selectChannelIdByCourseId(courseId);
        if(StringUtils.isBlank(channelId)){
            throw new RuntimeException("课程信息有误");
        }
        accessToken = BaseService.createAccessToken4Live(accountId, null, channelId);
        cacheService.set(CourseRedisCacheKey.getLiveAudioTokenCacheKey(courseId,accountId),accessToken,COURSE_LIVE_TOKEN_SECONDS);
        return accessToken;
    }

    @Override
    public void stop(String accountId, Integer courseId) {
        courseLiveAudioContentMapper.stop(accountId,courseId);
    }

    @Override
    public void push(String accountId, Integer courseId) {
        List<String> fansList = courseLiveAudioContentMapper.getFansListByUserId(accountId);
        List<String> buyerList = courseLiveAudioContentMapper.getBuyerListByCourseId(courseId);
        fansList.removeAll(buyerList);
        String anchorName = courseLiveAudioContentMapper.getAnchorNameByUserId(accountId);
        String courseName = courseLiveAudioContentMapper.getCourseNameByCourseIdAndUserId(courseId,accountId);
        if(courseName==null){
            throw new CourseException("不具有该课程权限");
        }

        doPush(accountId,courseId,anchorName,courseName,fansList,WEB_AUDIO_LIVE_COURSE_REMIND_FANCES);
        doPush(accountId,courseId,anchorName,courseName,buyerList,WEB_AUDIO_LIVE_COURSE_REMIND_BUYER);

        count(courseId);
    }

    @Override
    public Integer getPushCount(Integer courseId) {
        Integer count = cacheService.get(CourseRedisCacheKey.getLiveAudioPushCacheKey(courseId));
        count = count == null ? 0:count;
        return count;
    }

    private void count(Integer courseId) {
        Integer count = cacheService.get(CourseRedisCacheKey.getLiveAudioPushCacheKey(courseId));
        count = count == null ? 0:count;
        count ++;
        cacheService.set(CourseRedisCacheKey.getLiveAudioPushCacheKey(courseId),count);
    }

    private void doPush(String accountId, Integer courseId, String anchorName, String courseName, List<String> fansList, String webAudioLiveCourseRemind) {
        String commonContent = MessageFormat.format(webAudioLiveCourseRemind, anchorName, courseName);
        Map<String, String> weixinParams = new HashMap<>(4);
        weixinParams.put("first", TextStyleUtil.clearStyle(commonContent));
        weixinParams.put("keyword1", courseName);
        weixinParams.put("keyword2", "随到随学");
        weixinParams.put("remark", "");
        for (String userId : fansList) {
            loggger.info("推送给:{}", userId);
            commonMessageService.saveMessage(new BaseMessage.Builder(MessageTypeEnum.COURSE.getVal())
                    .buildWeb(commonContent)
                    .buildAppPush(commonContent)
                    .buildWeixin(weixinTemplateMessageRemindCode, weixinParams)
                    .detailId(String.valueOf(courseId))
                    .build(userId, COMMON_LEARNING_AUDIO_LIVE_COURSE_DETAIL_PAGE, accountId)
            );
        }
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
