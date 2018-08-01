package com.xczh.consumer.market.controller.course;

import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.course.model.CourseLiveAudioDiscussion;
import com.xczhihui.course.service.ICourseLiveAudioContentService;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.course.vo.CourseLiveAudioContentVO;
import com.xczhihui.course.vo.CourseLiveAudioDiscussionVO;

/**
 * 点播控制器 ClassName: BunchPlanController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>
 * email: 15936216273@163.com <br>
 * Create Time: 2017年8月11日<br>
 */
@RestController
@RequestMapping("/xczh/course")
public class CourseLiveAudioController {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CourseLiveAudioController.class);
    @Autowired
    public ICourseLiveAudioContentService courseLiveAudioContentService;


    @RequestMapping(value = "courseLiveAudioContent",method = RequestMethod.GET)
    public ResponseObject courseLiveAudioContentList(String closingTime,Integer pageSize) throws Exception {
        Page<CourseLecturVo> courseLiveAudioContentList = courseLiveAudioContentService.selectCourseLiveAudioContentByCourseId(pageSize,closingTime);
        return ResponseObject.newSuccessResponseObject("发送成功");
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

    @RequestMapping(value = "courseLiveAudioDiscussion",method = RequestMethod.POST)
    public ResponseObject saveCourseLiveAudioDiscussion(@Account String accountId, CourseLiveAudioDiscussionVO courseLiveAudioDiscussionVO) throws Exception {
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

}
