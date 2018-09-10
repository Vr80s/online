package com.xczh.consumer.market.controller.course;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.vhallyun.VhallUtil;
import com.xczhihui.course.service.ICourseLiveAudioContentService;
import com.xczhihui.course.vo.CourseLiveAudioContentVO;
import com.xczhihui.course.vo.CourseLiveAudioDiscussionVO;
import com.xczhihui.course.vo.CourseLiveAudioPPTVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 音频直播
 */
@RestController
@RequestMapping("/xczh/course/live/audio")
public class CourseLiveAudioController {

    @Autowired
    public ICourseLiveAudioContentService courseLiveAudioContentService;

    @RequestMapping(value = "courseLiveAudioAccessToken/{courseId}",method = RequestMethod.GET)
    public ResponseObject getCourseLiveAudioAccessToken(@Account String accountId, @PathVariable Integer courseId) throws Exception {
        Map<String, Object> accessToken = courseLiveAudioContentService.getCourseLiveAudioAccessToken(courseId,accountId);
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("accessToken", accessToken.get("accessToken").toString());
        m.put("channelId", accessToken.get("channelId").toString());
        m.put("appId", VhallUtil.APP_ID);
        m.put("accountId", accountId);
        return ResponseObject.newSuccessResponseObject(m);
    }

    @RequestMapping(value = "courseLiveAudioPPT/{courseId}",method = RequestMethod.POST)
    public ResponseObject saveCourseLiveAudioPPT(@Account String accountId, @PathVariable Integer courseId,@RequestBody List<CourseLiveAudioPPTVO> courseLiveAudioPPTs) throws Exception {
        courseLiveAudioContentService.saveCourseLiveAudioPPTs(courseId,accountId,courseLiveAudioPPTs);
        return ResponseObject.newSuccessResponseObject("课件保存成功");
    }

    @RequestMapping(value = "courseLiveAudioPPT/{courseId}",method = RequestMethod.GET)
    public ResponseObject courseLiveAudioPPT(@PathVariable Integer courseId) throws Exception {
        List<CourseLiveAudioPPTVO> courseLiveAudioPPTVOS = courseLiveAudioContentService.selectCourseLiveAudioPPTsByCourseId(courseId);
        return ResponseObject.newSuccessResponseObject(courseLiveAudioPPTVOS);
    }

    @RequestMapping(value = "courseLiveAudioContent",method = RequestMethod.POST)
    public ResponseObject saveCourseLiveAudioContent(@Account String accountId, @RequestBody CourseLiveAudioContentVO courseLiveAudioContentVO) throws Exception {
        courseLiveAudioContentVO.setUserId(accountId);
        courseLiveAudioContentService.saveCourseLiveAudioContent(courseLiveAudioContentVO);
        return ResponseObject.newSuccessResponseObject("发送成功");
    }

    @RequestMapping(value = "courseLiveAudioContent/{courseLiveAudioContentId}",method = RequestMethod.DELETE)
    public ResponseObject deleteCourseLiveAudioContent(@Account String accountId, @PathVariable Integer courseLiveAudioContentId) throws Exception {
        courseLiveAudioContentService.deleteCourseLiveAudioContent(accountId,courseLiveAudioContentId);
        return ResponseObject.newSuccessResponseObject("撤回成功");
    }

    @RequestMapping(value = "courseLiveAudioContent/like/{courseLiveAudioContentId}",method = RequestMethod.POST)
    public ResponseObject saveCourseLiveAudioContentLike(@Account String accountId, @PathVariable Integer courseLiveAudioContentId) throws Exception {
        courseLiveAudioContentService.saveCourseLiveAudioContentLike(courseLiveAudioContentId,accountId);
        return ResponseObject.newSuccessResponseObject("点赞成功");
    }

    @RequestMapping(value = "courseLiveAudioContent/{courseId}",method = RequestMethod.GET)
    public ResponseObject courseLiveAudioContentList(String endTime, Integer pageNumber, @PathVariable Integer courseId) throws Exception {
        Page page = new Page(pageNumber == null?1:pageNumber,10);
        return ResponseObject.newSuccessResponseObject(courseLiveAudioContentService.selectCourseLiveAudioContentByCourseId(page,endTime,courseId));
    }

    @RequestMapping(value = "courseLiveAudioDiscussion",method = RequestMethod.POST)
    public ResponseObject saveCourseLiveAudioDiscussion(@Account String accountId, @RequestBody CourseLiveAudioDiscussionVO courseLiveAudioDiscussionVO) throws Exception {
        courseLiveAudioDiscussionVO.setUserId(accountId);
        courseLiveAudioContentService.saveCourseLiveAudioDiscussion(courseLiveAudioDiscussionVO);
        return ResponseObject.newSuccessResponseObject("添加讨论成功");
    }

    @RequestMapping(value = "courseLiveAudioDiscussion/{courseLiveAudioDiscussionId}",method = RequestMethod.DELETE)
    public ResponseObject deleteCourseLiveAudioDiscussion(@Account String accountId, @PathVariable Integer courseLiveAudioDiscussionId) throws Exception {
        courseLiveAudioContentService.deleteCourseLiveAudioDiscussion(accountId,courseLiveAudioDiscussionId);
        return ResponseObject.newSuccessResponseObject("撤回讨论成功");
    }

    @RequestMapping(value = "courseLiveAudioDiscussion/like/{courseLiveAudioDiscussionId}",method = RequestMethod.POST)
    public ResponseObject saveCourseLiveAudioDiscussionLike(@Account String accountId, @PathVariable Integer courseLiveAudioDiscussionId) throws Exception {
        courseLiveAudioContentService.saveCourseLiveAudioDiscussionLike(courseLiveAudioDiscussionId,accountId);
        return ResponseObject.newSuccessResponseObject("点赞成功");
    }

    @RequestMapping(value = "courseLiveAudioDiscussion/{courseId}",method = RequestMethod.GET)
    public ResponseObject courseLiveAudioDiscussionList(String endTime,Integer pageNumber,Integer pageSize,@PathVariable Integer courseId,Boolean question) throws Exception {
        Page page = new Page(pageNumber == null?1:pageNumber,pageSize == null?10:pageSize);
        return ResponseObject.newSuccessResponseObject(courseLiveAudioContentService.selectCourseLiveAudioDiscussionByCourseId(page,endTime,courseId,question));
    }


    @RequestMapping(value = "courseLiveAudioDiscussion/ban/{courseId}/{userId}",method = RequestMethod.POST)
    public ResponseObject saveCourseLiveAudioDiscussionBan(@Account String accountId, @PathVariable Integer courseId, @PathVariable String userId) throws Exception {
        courseLiveAudioContentService.saveCourseLiveAudioDiscussionBan(accountId,courseId,userId);
        return ResponseObject.newSuccessResponseObject("禁言成功");
    }

    @RequestMapping(value = "stop/{courseId}",method = RequestMethod.POST)
    public ResponseObject stop(@Account String accountId, @PathVariable Integer courseId) throws Exception {
        courseLiveAudioContentService.stop(accountId,courseId);
        return ResponseObject.newSuccessResponseObject("关闭成功");
    }

    @RequestMapping(value = "push/{courseId}",method = RequestMethod.POST)
    public ResponseObject push(@Account String accountId, @PathVariable Integer courseId) throws Exception {
        courseLiveAudioContentService.push(accountId,courseId);
        return ResponseObject.newSuccessResponseObject("发送成功");
    }

    @RequestMapping(value = "push/{courseId}",method = RequestMethod.GET)
    public ResponseObject pushCount(@PathVariable Integer courseId) throws Exception {
        return ResponseObject.newSuccessResponseObject(courseLiveAudioContentService.getPushCount(courseId));
    }

}
